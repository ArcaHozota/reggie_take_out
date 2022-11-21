package com.itheima.reggie.controller;

import java.time.LocalDateTime;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.Constants;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import com.itheima.reggie.utils.ComparisonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 員工管理控制器
 *
 * @author Administrator
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

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
	public R<Employee> login(@NonNull HttpServletRequest request, @RequestBody @NonNull Employee employee) {
		// 將頁面提交的密碼進行MD5加密；
		String password = employee.getPassword();
		password = DigestUtils.md5DigestAsHex(password.getBytes()).toUpperCase();
		// 根據頁面提交的用戶名查詢數據庫；
		LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Employee::getUsername, employee.getUsername());
		// 獲取One對象；
		final Employee aEmployee = employeeService.getOne(queryWrapper);
		// 如果沒有查詢到或者密碼錯誤則返回登錄失敗；
		if (aEmployee == null || ComparisonUtils.isNotEqual(password, aEmployee.getPassword())) {
			return R.error(Constants.LOGIN_FAILED);
		}
		// 查看用戶狀態，如果已被禁用，則返回賬號已禁用；
		if (ComparisonUtils.isEqual(0, aEmployee.getStatus())) {
			return R.error(Constants.FORBIDDEN);
		}
		// 登錄成功，將員工ID存入Session並返回登錄成功；
		request.getSession().setAttribute(Constants.getEntityName(employee), aEmployee.getId());
		return R.success(aEmployee);
	}

	/**
	 * 員工退出登錄
	 *
	 * @param request 請求
	 * @return R.success(退出登錄的信息)
	 */
	@PostMapping("/logout")
	public R<String> logout(@NonNull HttpServletRequest request) {
		// 清除Session中保存的當前登錄員工的ID；
		request.getSession().removeAttribute(Constants.getEntityName(new Employee()));
		return R.success("成功退出登錄");
	}

	/**
	 * 保存新增員工
	 *
	 * @param request  請求
	 * @param employee 實體類對象
	 * @return R.success(成功增加員工的信息)
	 */
	@PostMapping
	public R<String> save(@NonNull HttpServletRequest request, @RequestBody @NonNull Employee employee) {
		log.info("員工信息：{}", employee.toString());
		// 設置初始密碼，需進行MD5加密；
		employee.setPassword(DigestUtils.md5DigestAsHex(Constants.PRIMARY_CODE.getBytes()).toUpperCase());
		employeeService.save(employee);
		return R.success("成功增加員工信息");
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
	public R<Page<Employee>> pagination(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize,
			@Param("name") String name) {
		// 聲明分頁構造器；
		Page<Employee> pageInfo = new Page<>(pageNum, pageSize);
		// 聲明條件構造器；
		LambdaQueryWrapper<Employee> queryWrapper = Wrappers.lambdaQuery(new Employee());
		// 添加過濾條件；
		queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
		// 添加排序條件；
		queryWrapper.orderByDesc(Employee::getUpdateTime);
		// 執行查詢；
		employeeService.page(pageInfo, queryWrapper);
		return R.success(pageInfo);
	}

	/**
	 * 根據ID修改員工信息
	 *
	 * @param request  請求
	 * @param employee 實體類對象
	 * @return R.success(成功修改員工的信息)
	 */
	@PutMapping
	public R<String> update(@NonNull HttpServletRequest request, @RequestBody @NonNull Employee employee) {
		Long empId = (Long) request.getSession().getAttribute(Constants.getEntityName(employee));
		employee.setUpdateUser(empId);
		employee.setUpdateTime(LocalDateTime.now());
		employeeService.updateById(employee);
		return R.success("員工信息修改成功！");
	}

	/**
	 * 根據ID查詢員工信息
	 *
	 * @param id 員工ID
	 * @return R.success(查詢到的員工的信息)
	 */
	@GetMapping("/{id}")
	public R<Employee> getById(@PathVariable Long id) {
		log.info("根據ID查詢員工信息...");
		Employee employee = employeeService.getById(id);
		// 如果沒有相對應的結果，則返回錯誤信息；
		if (employee == null) {
			return R.error(Constants.NO_CONSEQUENCE);
		}
		return R.success(employee);
	}
}
