package jp.co.reggie.newdeal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import jp.co.reggie.newdeal.entity.Employee;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author Administrator
 */
@Mapper
public interface EmployeeDao extends BaseMapper<Employee> {
}
