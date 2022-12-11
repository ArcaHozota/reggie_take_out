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
import com.itheima.reggie.common.CustomMessage;
import com.itheima.reggie.common.Reggie;
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
	public Reggie<String> save(@RequestBody final DishDto dishDto) {
		log.info("新增菜品：{}" + dishDto.toString());
		this.dishService.saveWithFlavour(dishDto);
		return Reggie.success(CustomMessage.SRP004);
	}

	/**
	 * 菜品信息分頁查詢
	 *
	 * @param pageNum  頁碼
	 * @param pageSize 頁面大小
	 * @return R.success(分頁信息)
	 */
	@GetMapping("/page")
	public Reggie<Page<DishDto>> pagination(@Param("pageNum") final Integer pageNum,
			@Param("pageSize") final Integer pageSize, @Param("name") final String name) {
		// 聲明分頁構造器對象；
		final Page<Dish> pageInfo = Page.of(pageNum, pageSize);
		final Page<DishDto> dtoPage = new Page<>();
		// 創建條件構造器；
		final LambdaQueryWrapper<Dish> queryWrapper = Wrappers.lambdaQuery(new Dish());
		// 添加過濾條件；
		queryWrapper.like(ComparisonUtils.isNotEqual(name, null), Dish::getName, name);
		// 添加排序條件；
		queryWrapper.orderByDesc(Dish::getUpdateTime);
		// 執行分頁查詢；
		this.dishService.page(pageInfo, queryWrapper);
		// 對象拷貝；
		BeanUtils.copyProperties(pageInfo, dtoPage, "records");
		// 獲取分頁數據；
		final List<Dish> records = pageInfo.getRecords();
		// 獲取數據傳輸類分頁；
		final List<DishDto> list = records.stream().map((item) -> {
			// 聲明菜品及口味數據傳輸類對象；
			final DishDto dishDto = new DishDto();
			// 拷貝除分類ID以外的屬性；
			BeanUtils.copyProperties(item, dishDto);
			// 獲取分類ID；
			final Long categoryId = item.getCategoryId();
			// 根據ID查詢分類對象；
			final Category category = this.categoryService.getById(categoryId);
			if (ComparisonUtils.isNotEqual(category, null)) {
				// 獲取分類名稱；
				final String categoryName = category.getName();
				// 存儲於DTO對象中並返回；
				dishDto.setCategoryName(categoryName);
			}
			return dishDto;
		}).collect(Collectors.toList());
		// 設置分頁數據於構造器中並返回；
		dtoPage.setRecords(list);
		return Reggie.success(dtoPage);
	}

	/**
	 * 根據ID顯示菜品信息
	 *
	 * @param id 菜品ID
	 * @return R.success(菜品信息)
	 */
	@GetMapping("/{id}")
	public Reggie<DishDto> getDishInfo(@PathVariable("id") final Long id) {
		// 根據ID查詢菜品信息以及對應的口味信息；
		return Reggie.success(this.dishService.getByIdWithFlavour(id));
	}

	/**
	 * 修改菜品信息
	 *
	 * @param dishDto 數據傳輸類對象
	 * @return R.success(菜品更新成功的信息)
	 */
	@PutMapping
	public Reggie<String> update(@RequestBody final DishDto dishDto) {
		log.info(dishDto.toString());
		this.dishService.updateWithFlavour(dishDto);
		return Reggie.success(CustomMessage.SRP005);
	}

	/**
	 * 回顯菜品表單數據
	 *
	 * @param dish 實體類對象
	 * @return R.success(菜品信息)
	 */
	@GetMapping("/list")
	public Reggie<List<Dish>> list(@RequestBody final Dish dish) {
		// 創建條件構造器；
		final LambdaQueryWrapper<Dish> queryWrapper = Wrappers.lambdaQuery(new Dish());
		// 添加搜索條件；
		queryWrapper.eq(ComparisonUtils.isNotEqual(null, dish.getCategoryId()), Dish::getCategoryId,
				dish.getCategoryId());
		queryWrapper.eq(Dish::getStatus, 1);
		// 添加排序條件；
		queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
		// 查詢菜品信息並返回；
		final List<Dish> list = this.dishService.list(queryWrapper);
		return Reggie.success(list);
	}
}
