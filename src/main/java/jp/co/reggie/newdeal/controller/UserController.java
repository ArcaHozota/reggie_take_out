package jp.co.reggie.newdeal.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jp.co.reggie.newdeal.common.CustomMessage;
import jp.co.reggie.newdeal.entity.User;
import jp.co.reggie.newdeal.service.UserService;
import jp.co.reggie.newdeal.utils.Reggie;
import jp.co.reggie.newdeal.utils.SMSUtils;
import jp.co.reggie.newdeal.utils.ValidateCodeUtils;

@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Resource
	private UserService userService;

	/**
	 * 用戸登錄
	 *
	 * @param user    用戸實體類
	 * @param session 本次會話
	 * @return R.success(登錄成功的信息)
	 */
	@PostMapping("/login")
	public Reggie<User> login(@RequestBody final Map<String, String> userMap, final HttpSession session) {
		// 獲取手機號；
		final String phoneNo = userMap.get("phoneNo").toString();
		// 獲取驗證碼；
		final String code = userMap.get("code").toString();
		// 獲取Session中保存的驗證碼；
		final Object codeInSession = session.getAttribute(phoneNo);
		// 進行驗證碼的比對；
		if (codeInSession != null && code.equals(codeInSession)) {
			// 認證成功，放行登錄並驗證是否為新注冊手機號；
			final LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery(new User());
			queryWrapper.eq(User::getPhoneNo, phoneNo);
			User user = this.userService.getOne(queryWrapper);
			// 如果是新用戸則自動完成注冊；
			if (user == null) {
				user = new User();
				user.setPhoneNo(phoneNo);
				user.setStatus(1);
				this.userService.save(user);
			}
			return Reggie.success(user);
		}
		return Reggie.error(CustomMessage.ERP014);
	}

	/**
	 * 用戸登錄認證訊息發送
	 *
	 * @param user    用戸實體類
	 * @param session 本次會話
	 * @return R.success(訊息發送成功的信息)
	 */
	@PostMapping("/sendMsg")
	public Reggie<String> sendMsg(@RequestBody final User user, final HttpSession session) {
		// 獲取手機號；
		final String phoneNo = user.getPhoneNo();
		if (!phoneNo.isBlank()) {
			// 生成隨機的6位數驗證碼；
			final String code = ValidateCodeUtils.generateValidateCode(6).toString();
			// 將生成的驗證碼保存到Session中；
			session.setAttribute(phoneNo, code);
			LOGGER.info("本次的驗證碼為：{}", code);
			// 調用阿里雲提供的訊息服務API完成送訊；
			SMSUtils.sendMessage("瑞吉外賣", "00024", phoneNo, code);
			return Reggie.success(CustomMessage.SRP015);
		}
		return Reggie.error(CustomMessage.ERP013);
	}
}
