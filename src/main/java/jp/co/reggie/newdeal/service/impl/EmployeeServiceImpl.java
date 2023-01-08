package jp.co.reggie.newdeal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jp.co.reggie.newdeal.entity.Employee;
import jp.co.reggie.newdeal.mapper.EmployeeDao;
import jp.co.reggie.newdeal.service.EmployeeService;

import org.springframework.stereotype.Service;


/**
 * @author Administrator
 * @date 2022-11-09
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeDao, Employee> implements EmployeeService {
}
