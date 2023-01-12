package jp.co.reggie.newdeal.controller;

import jp.co.reggie.newdeal.utils.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jp.co.reggie.newdeal.common.Constants;
import jp.co.reggie.newdeal.common.CustomMessage;
import jp.co.reggie.newdeal.entity.Employee;
import jp.co.reggie.newdeal.service.EmployeeService;
import jp.co.reggie.newdeal.utils.Reggie;

/**
 * 員工管理控制器
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

	@Resource
	private EmployeeService employeeService;

	/**
	 * 員工登錄
	 *
	 * @param request  請求
	 * @param employee 員工信息對象
	 * @return R.success(實體類對象)
	 */
	@PostMapping("/login")
	public Reggie<Employee> login(final HttpServletRequest request, @RequestBody final Employee employee) {
		// 將頁面提交的密碼進行MD5加密；
		final String password = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes()).toUpperCase();
		// 根據頁面提交的用戸名查詢數據庫；
		// 獲取One對象；
		final Employee aEmployee = this.employeeService.findOneByUsernameProvided(employee.getUsername());
		// 如果沒有查詢到或者密碼錯誤則返回登錄失敗；
		if (aEmployee == null || !password.equals(aEmployee.getPassword())) {
			return Reggie.error(Constants.LOGIN_FAILED);
		}
		// 查看用戸狀態，如果已被禁用，則返回賬號已禁用；
		if (aEmployee.getStatus() == 0) {
			return Reggie.error(Constants.FORBIDDEN);
		}
		// 登錄成功，將員工ID存入Session並返回登錄成功；
		request.getSession().setAttribute(Constants.getEntityName(employee), aEmployee.getId());
		return Reggie.success(aEmployee);
	}

	/**
	 * 員工退出登錄
	 *
	 * @param request 請求
	 * @return R.success(退出登錄的信息)
	 */
	@PostMapping("/logout")
	public Reggie<String> logout(final HttpServletRequest request) {
		// 清除Session中保存的當前登錄員工的ID；
		request.getSession().removeAttribute("employee");
		return Reggie.success(CustomMessage.SRP007);
	}

	/**
	 * 保存新增員工
	 *
	 * @param employee 實體類對象
	 * @return R.success(成功增加員工的信息)
	 */
	@PostMapping
	public Reggie<String> save(@RequestBody Employee employee) {
		LOGGER.info("員工信息：{}", employee.toString());
		// 設置初始密碼，需進行MD5加密；
		final String password = DigestUtils.md5DigestAsHex(Constants.PRIMARY_CODE.getBytes()).toUpperCase();
		employee.setPassword(password);
		this.employeeService.save(employee);
		return Reggie.success(CustomMessage.SRP006);
	}

	/**
	 * 員工信息分頁查詢
	 *
	 * @param pageNum  頁碼
	 * @param pageSize 頁面大小
	 * @param name     檢索文
	 * @return R.success(分頁信息)
	 */
	@GetMapping("/page")
	public Reggie<Page<Employee>> pagination(@RequestParam(name = "pageNum") final Integer pageNum,
			@RequestParam(name = "pageSize") final Integer pageSize, @RequestParam(name = "name") final String name) {
		// 聲明分頁構造器；
		final Page<Employee> pageInfo = Page.of(pageNum, pageSize);
		// 聲明條件構造器；
		final LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
		// 添加過濾條件；
		queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
		// 添加排序條件；
		queryWrapper.orderByDesc(Employee::getUpdateTime);
		// 執行查詢；
		this.employeeService.page(pageInfo, queryWrapper);
		return Reggie.success(pageInfo);
	}

	/**
	 * 根據ID修改員工信息
	 *
	 * @param employee 實體類對象
	 * @return R.success(成功修改員工的信息)
	 */
	@PutMapping
	public Reggie<String> update(@RequestBody final Employee employee) {
		this.employeeService.updateById(employee);
		return Reggie.success(CustomMessage.SRP008);
	}

	/**
	 * 根據ID查詢員工信息
	 *
	 * @param id 員工ID
	 * @return R.success(查詢到的員工的信息)
	 */
	@GetMapping("/{id}")
	public Reggie<Employee> getById(@PathVariable final Long id) {
		LOGGER.info("根據ID查詢員工信息...");
		final Employee employee = this.employeeService.getById(id);
		// 如果沒有相對應的結果，則返回錯誤信息；
		if (employee == null) {
			return Reggie.error(Constants.NO_CONSEQUENCE);
		}
		return Reggie.success(employee);
	}
}
