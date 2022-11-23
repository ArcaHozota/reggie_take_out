package com.itheima.reggie.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import com.itheima.reggie.mapper.DishDao;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;

/**
 * @author Administrator
 * @date 2022-11-19
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishDao, Dish> implements DishService {

	@Autowired
	private DishFlavorService dishFlavorService;

	/**
	 * 新增菜品，同時插入菜品所對應的口味數據
	 * 
	 * @param dishDto
	 */
	@Transactional
	public void saveWithFlavour(DishDto dishDto) {
		// 保存菜品的基本信息到菜品表；
		this.save(dishDto);
		// 獲取菜品ID；
		final Long dishId = dishDto.getId();
		// 獲取菜品口味的集合；
		List<DishFlavor> flavors = dishDto.getFlavors();
		// 將菜品ID設置到口味集合中；
		flavors = flavors.stream().map((item) -> {
			item.setDishId(dishId);
			return item;
		}).collect(Collectors.toList());
		// 保存 菜品的口味數據到口味表；
		dishFlavorService.saveBatch(flavors);
	}
}
