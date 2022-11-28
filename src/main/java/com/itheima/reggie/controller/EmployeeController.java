package com.itheima.reggie.controller;

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
import com.itheima.reggie.common.CustomMessage;
import com.itheima.reggie.common.RestDto;
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
	public RestDto<Employee> login(@NonNull HttpServletRequest request, @NonNull @RequestBody Employee employee) {
		// 將頁面提交的密碼進行MD5加密；
		final String password = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes()).toUpperCase();
		// 根據頁面提交的用戶名查詢數據庫；
		final LambdaQueryWrapper<Employee> queryWrapper = Wrappers.lambdaQuery(new Employee());
		queryWrapper.eq(Employee::getUsername, employee.getUsername());
		// 獲取One對象；
		final Employee aEmployee = employeeService.getOne(queryWrapper);
		// 如果沒有查詢到或者密碼錯誤則返回登錄失敗；
		if (aEmployee == null || ComparisonUtils.isNotEqual(password, aEmployee.getPassword())) {
			return RestDto.error(Constants.LOGIN_FAILED);
		}
		// 查看用戶狀態，如果已被禁用，則返回賬號已禁用；
		if (ComparisonUtils.isEqual(0, aEmployee.getStatus())) {
			return RestDto.error(Constants.FORBIDDEN);
		}
		// 登錄成功，將員工ID存入Session並返回登錄成功；
		request.getSession().setAttribute(Constants.getEntityName(employee), aEmployee.getId());
		return RestDto.success(aEmployee);
	}

	/**
	 * 員工退出登錄
	 *
	 * @param request 請求
	 * @return R.success(退出登錄的信息)
	 */
	@PostMapping("/logout")
	public RestDto<String> logout(@NonNull HttpServletRequest request) {
		// 清除Session中保存的當前登錄員工的ID；
		request.getSession().removeAttribute(Constants.getEntityName(new Employee()));
		return RestDto.success(CustomMessage.SRP007);
	}

	/**
	 * 保存新增員工
	 *
	 * @param request  請求
	 * @param employee 實體類對象
	 * @return R.success(成功增加員工的信息)
	 */
	@PostMapping
	public RestDto<String> save(@NonNull HttpServletRequest request, @RequestBody @NonNull Employee employee) {
		log.info("員工信息：{}", employee.toString());
		// 設置初始密碼，需進行MD5加密；
		employee.setPassword(DigestUtils.md5DigestAsHex(Constants.PRIMARY_CODE.getBytes()).toUpperCase());
		employeeService.save(employee);
		return RestDto.success(CustomMessage.SRP006);
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
	public RestDto<Page<Employee>> pagination(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize,
			@Param("name") String name) {
		// 聲明分頁構造器；
		final Page<Employee> pageInfo = new Page<>(pageNum, pageSize);
		// 聲明條件構造器；
		final LambdaQueryWrapper<Employee> queryWrapper = Wrappers.lambdaQuery(new Employee());
		// 添加過濾條件；
		queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
		// 添加排序條件；
		queryWrapper.orderByDesc(Employee::getUpdateTime);
		// 執行查詢；
		employeeService.page(pageInfo, queryWrapper);
		return RestDto.success(pageInfo);
	}

	/**
	 * 根據ID修改員工信息
	 *
	 * @param request  請求
	 * @param employee 實體類對象
	 * @return R.success(成功修改員工的信息)
	 */
	@PutMapping
	public RestDto<String> update(@NonNull HttpServletRequest request, @RequestBody @NonNull Employee employee) {
		employeeService.updateById(employee);
		return RestDto.success(CustomMessage.SRP008);
	}

	/**
	 * 根據ID查詢員工信息
	 *
	 * @param id 員工ID
	 * @return R.success(查詢到的員工的信息)
	 */
	@GetMapping("/{id}")
	public RestDto<Employee> getById(@PathVariable Long id) {
		log.info("根據ID查詢員工信息...");
		final Employee employee = employeeService.getById(id);
		// 如果沒有相對應的結果，則返回錯誤信息；
		if (employee == null) {
			return RestDto.error(Constants.NO_CONSEQUENCE);
		}
		return RestDto.success(employee);
	}
}
