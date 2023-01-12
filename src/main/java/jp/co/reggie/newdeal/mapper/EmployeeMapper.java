package jp.co.reggie.newdeal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import jp.co.reggie.newdeal.entity.Employee;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Administrator
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
    Employee selectByUsername(@Param("username") final String userName);
}
