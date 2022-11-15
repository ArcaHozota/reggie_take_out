package com.itheima.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.Constants;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import com.itheima.reggie.utils.HikakuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Locale;

/**
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
    public R<Employee> login(HttpServletRequest request, @RequestBody @NonNull Employee employee) {
        // 將頁面提交的密碼進行MD5加密；
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        // 根據頁面提交的用戶名查詢數據庫；
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        // 獲取One對象；
        final Employee aEmployee = employeeService.getOne(queryWrapper);
        // 如果沒有查詢到或者密碼錯誤則返回登錄失敗；
        if (aEmployee == null || HikakuUtils.isNotEqual(password, aEmployee.getPassword())) {
            return R.error(Constants.LOGIN_FAILED);
        }
        // 查看用戶狀態，如果已被禁用，則返回賬號已禁用；
        if (HikakuUtils.isEqual(0, aEmployee.getStatus())) {
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
     * @param employee 請求
     * @return R.success(退出登錄的信息)
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody @NonNull Employee employee) {
        log.info("員工信息：{}", employee.toString());
        // 設置初始密碼，需進行MD5加密；
        employee.setPassword(DigestUtils.md5DigestAsHex(Constants.PRIMARY_CODE.getBytes()));
        // 設置創建時間和更新時間；
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        // 設置創建人；
        Long employeeId = (Long) request.getSession().getAttribute(Constants.getEntityName(employee));
        employee.setCreateUser(employeeId);
        employee.setUpdateUser(employeeId);
        employeeService.save(employee);
        return R.success("成功增加員工信息");
    }
}
