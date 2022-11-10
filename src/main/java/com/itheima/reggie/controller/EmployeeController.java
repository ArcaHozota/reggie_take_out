package com.itheima.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import com.itheima.reggie.utils.ObUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * @author Administrator
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        // 將頁面提交的密碼進行MD5加密；
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        // 根據頁面提交的用戶名查詢數據庫；
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        // 獲取One對象；
        final Employee aEmployee = employeeService.getOne(queryWrapper);
        // 如果沒有查詢到或者密碼錯誤則返回登錄失敗；
        if (aEmployee == null || ObUtils.isNotEqual(password, aEmployee.getPassword())) {
            return R.error("登錄失敗");
        }
        // 查看用戶狀態，如果已被禁用，則返回賬號已禁用；
        if (aEmployee.getStatus().equals(0)) {
            return R.error("賬號已禁用");
        }
        // 登錄成功，將員工ID存入Session並返回登錄成功；
        request.getSession().setAttribute("employee", aEmployee.getId());
        return R.success(aEmployee);
    }
}
