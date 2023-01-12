package jp.co.reggie.newdeal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jakarta.annotation.Resource;
import jp.co.reggie.newdeal.entity.Employee;
import jp.co.reggie.newdeal.mapper.EmployeeMapper;
import jp.co.reggie.newdeal.service.EmployeeService;

import org.springframework.stereotype.Service;


/**
 * @author Administrator
 * @date 2022-11-09
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    @Override
    public Employee findOneByUsernameProvided(final String username) {
        return this.employeeMapper.selectByUsername(username);
    }
}
