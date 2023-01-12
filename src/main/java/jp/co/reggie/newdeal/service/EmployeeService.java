package jp.co.reggie.newdeal.service;

import com.baomidou.mybatisplus.extension.service.IService;

import jp.co.reggie.newdeal.entity.Employee;

/**
 * @author Administrator
 */
public interface EmployeeService extends IService<Employee> {

    /**
     * get one by username
     *
     * @param username 用戸名
     */
    Employee findOneByUsernameProvided(final String username);
}
