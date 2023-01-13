package jp.co.reggie.newdeal.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jp.co.reggie.newdeal.entity.Employee;
import jp.co.reggie.newdeal.mapper.EmployeeMapper;
import jp.co.reggie.newdeal.service.EmployeeService;

/**
 * @author Administrator
 * @date 2022-11-09
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

	@Resource
	private EmployeeMapper employeeMapper;

	/**
	 * 根據所提供的用戸名進行查詢
	 *
	 * @param userName 用戸名
	 * @return Employee
	 */
	@Override
	public Employee findOneByUsernameProvided(final String username) {
		return this.employeeMapper.selectByUsername(username);
	}
}
