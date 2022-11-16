package com.itheima.reggie.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Administrator
 */
@Mapper
public interface EmployeeDao extends BaseMapper<Employee> {
}
