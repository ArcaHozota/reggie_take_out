package jp.co.reggie.newdeal.listener;

import java.sql.SQLIntegrityConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.reggie.newdeal.common.Constants;
import jp.co.reggie.newdeal.common.CustomException;
import jp.co.reggie.newdeal.utils.Reggie;

/**
 * 全局異常處理類
 *
 * @author Administrator
 * @date 2022-11-12
 */
@ResponseBody
@ControllerAdvice(annotations = { RestController.class, Controller.class })
public class GlobalExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * SQL整合性異常處理方法
	 *
	 * @param exception SQL整合性異常
	 * @return 錯誤信息
	 */
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public Reggie<String> exceptionHandler01(final SQLIntegrityConstraintViolationException exception) {
		LOGGER.error(exception.getMessage());
		if (exception.getMessage().contains(Constants.DUPLICATED_KEY)) {
			final String[] split = exception.getMessage().split(" ");
			final String msg = split[2] + "已存在";
			return Reggie.error(msg);
		}
		return Reggie.error(Constants.ERROR);
	}

	/**
	 * 通用業務異常處理方法
	 *
	 * @param exception 通用業務異常
	 * @return 錯誤信息
	 */
	@ExceptionHandler(CustomException.class)
	public Reggie<String> exceptionHandler02(final CustomException exception) {
		LOGGER.error(exception.getMessage());
		return Reggie.error(exception.getMessage());
	}
}
