package com.itheima.reggie.controller;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.utils.ComparisonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 菜品管理控制器
 *
 * @author Administrator
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

	@Resource
	private DishService dishService;

	@Resource
	private DishFlavorService dishFlavorService;

	/**
	 * 新增菜品
	 * 
	 * @param dishDto
	 * @return R.success(成功新增菜品的信息)
	 */
	@PostMapping
	public R<String> save(@RequestBody DishDto dishDto) {
		log.info("新增菜品：{}" + dishDto.toString());
		dishService.saveWithFlavour(dishDto);
		return R.success("新增菜品成功");
	}

	/**
	 * 菜品信息分頁查詢
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return R.success(分頁信息)
	 */
	@GetMapping("/page")
	public R<Page<Dish>> pagination(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize,
			@Param("name") String name) {
		// 聲明分頁構造器對象；
		final Page<Dish> pageInfo = new Page<>(pageNum, pageSize);
		// 創建條件構造器；
		final LambdaQueryWrapper<Dish> queryWrapper = Wrappers.lambdaQuery(new Dish());
		// 添加過濾條件；
		queryWrapper.like(ComparisonUtils.isNotEqual(name, null), Dish::getName, name);
		// 添加排序條件；
		queryWrapper.orderByDesc(Dish::getUpdateTime);
		// 執行分頁查詢；
		dishService.page(pageInfo, queryWrapper);
		return R.success(pageInfo);
	}
}
