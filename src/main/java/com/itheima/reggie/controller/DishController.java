package com.itheima.reggie.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.service.CategoryService;
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
	private CategoryService categoryService;

	@Resource
	private DishFlavorService dishFlavorService;

	/**
	 * 新增菜品
	 * 
	 * @param dishDto 數據傳輸類對象
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
	 * @param pageNum  頁碼
	 * @param pageSize 頁面大小
	 * @return R.success(分頁信息)
	 */
	@GetMapping("/page")
	public R<Page<DishDto>> pagination(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize,
			@Param("name") String name) {
		// 聲明分頁構造器對象；
		final Page<Dish> pageInfo = new Page<>(pageNum, pageSize);
		final Page<DishDto> dishDtoPage = new Page<>();
		// 創建條件構造器；
		final LambdaQueryWrapper<Dish> queryWrapper = Wrappers.lambdaQuery(new Dish());
		// 添加過濾條件；
		queryWrapper.like(ComparisonUtils.isNotEqual(name, null), Dish::getName, name);
		// 添加排序條件；
		queryWrapper.orderByDesc(Dish::getUpdateTime);
		// 執行分頁查詢；
		dishService.page(pageInfo, queryWrapper);
		// 對象拷貝；
		BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");
		// 獲取分頁數據；
		final List<Dish> records = pageInfo.getRecords();
		final List<DishDto> list = records.stream().map((item) -> {
			// 聲明菜品及口味數據傳輸類對象；
			final DishDto dishDto = new DishDto();
			// 拷貝除分類ID以外的屬性；
			BeanUtils.copyProperties(item, dishDto);
			// 獲取分類ID；
			final Long categoryId = item.getCategoryId();
			// 根據ID查詢分類對象；
			final Category category = categoryService.getById(categoryId);
			if (ComparisonUtils.isNotEqual(category, null)) {
				// 獲取分類名稱；
				final String categoryName = category.getName();
				// 存儲於DTO對象中並返回；
				dishDto.setCategoryName(categoryName);
			}
			return dishDto;
		}).collect(Collectors.toList());
		dishDtoPage.setRecords(list);
		return R.success(dishDtoPage);
	}

	/**
	 * 根據ID顯示菜品信息
	 * 
	 * @param id 菜品ID
	 * @return R.success(菜品信息)
	 */
	@GetMapping("/{id}")
	public R<DishDto> getDishInfo(@PathVariable("id") Long id) {
		// 根據ID查詢菜品信息以及對應的口味信息；
		final DishDto dishDto = dishService.getByIdWithFlavour(id);
		return R.success(dishDto);
	}

	/**
	 * 修改菜品信息
	 * 
	 * @param dishDto
	 * @return R.success(菜品更新成功的信息)
	 */
	@PutMapping
	public R<String> update(@RequestBody DishDto dishDto) {
		log.info(dishDto.toString());
		dishService.updateWithFlavour(dishDto);
		return R.success("菜品信息修改成功");
	}
}
