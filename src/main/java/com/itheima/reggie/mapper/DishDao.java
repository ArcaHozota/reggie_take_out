package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Administrator
 * @date 2022-11-19
 */
@Mapper
public interface DishDao extends BaseMapper<Dish> {
}
