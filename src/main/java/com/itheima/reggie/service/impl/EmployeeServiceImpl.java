package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.mapper.EmployeeDao;
import com.itheima.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;


/**
 * @author Administrator
 * @date 2022-11-09
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeDao, Employee> implements EmployeeService {
}
