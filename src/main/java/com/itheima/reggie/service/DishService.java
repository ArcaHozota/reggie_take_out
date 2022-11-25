package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;

/**
 * @author Administrator
 */
public interface DishService extends IService<Dish> {

	/**
	 * 新增菜品，同時插入菜品所對應的口味數據
	 *
	 * @param dishDto 菜品及口味數據傳輸類
	 */
	void saveWithFlavour(DishDto dishDto);

	/**
	 * 修改菜品信息並同時插入菜品所對應的口味數據
	 *
	 * @param dishDto 菜品及口味數據傳輸類
	 */
	void updateWithFlavour(DishDto dishDto);

	/**
	 * 根據ID查詢菜品信息以及對應的口味信息
	 * 
	 * @param id
	 * @return dishDto 菜品及口味數據傳輸類
	 */
	DishDto getByIdWithFlavour(Long id);

}
