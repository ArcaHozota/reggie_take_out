package jp.co.reggie.newdeal.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import jp.co.reggie.newdeal.entity.Employee;

/**
 * @author Administrator
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

	/**
	 * 根據所提供的用戸名進行查詢
	 *
	 * @param userName 用戸名
	 * @return Employee
	 */
	Employee selectByUsername(@Param("username") String userName);
}
