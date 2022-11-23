package com.itheima.reggie.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;

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
}
