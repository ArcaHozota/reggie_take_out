package jp.co.reggie.newdeal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import jp.co.reggie.newdeal.entity.Category;

/**
 * @author Administrator
 * @date 2022-11-19
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

	/**
	 * 根據類型查詢數據
	 *
	 * @param type 類型
	 * @return List<Category>
	 */
	List<Category> selectByType(@Param("type") Integer categoryType);
}
