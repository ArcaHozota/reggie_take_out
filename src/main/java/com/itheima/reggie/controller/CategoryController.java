package com.itheima.reggie.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.CustomMessage;
import com.itheima.reggie.common.RestDto;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.utils.ComparisonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 分類管理控制器
 *
 * @author Administrator
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

	@Resource
	private CategoryService categoryService;

	/**
	 * 分頁信息顯示
	 *
	 * @param pageNum  頁碼
	 * @param pageSize 頁面大小
	 * @return R.success(分頁信息)
	 */
	@GetMapping("/page")
	public RestDto<Page<Category>> pagination(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize) {
		// 聲明分頁構造器；
		final Page<Category> pageInfo = new Page<>(pageNum, pageSize);
		// 聲明條件構造器；
		final LambdaQueryWrapper<Category> queryWrapper = Wrappers.lambdaQuery(new Category());
		// 添加排序條件，根據sort進行排序；
		queryWrapper.orderByAsc(Category::getSort);
		// 執行查詢；
		categoryService.page(pageInfo, queryWrapper);
		return RestDto.success(pageInfo);
	}

	/**
	 * 新增分類
	 *
	 * @param category 實體類對象
	 * @return R.success(分類新增成功的信息);
	 */
	@PostMapping
	public RestDto<String> save(@RequestBody Category category) {
		log.info("category:{}", category);
		categoryService.save(category);
		return RestDto.success(CustomMessage.SRP001);
	}

	/**
	 * 刪除分類
	 *
	 * @param id 分類ID
	 * @return R.success(分類刪除成功的信息);
	 */
	@DeleteMapping
	public RestDto<String> delete(Long id) {
		log.info("刪除ID={}的分類", id);
		// 實施刪除；
		categoryService.remove(id);
		return RestDto.success(CustomMessage.SRP003);
	}

	/**
	 * 更新分類
	 *
	 * @param category 實體類對象
	 * @return R.success(分類更新成功的信息);
	 */
	@PutMapping
	public RestDto<String> update(@RequestBody Category category) {
		log.info("修改分類信息：{}", category);
		// 執行修改操作；
		categoryService.updateById(category);
		return RestDto.success(CustomMessage.SRP002);
	}

	/**
	 * 根據條件查詢分類數據
	 * 
	 * @param category 實體類對象
	 * @return R.success(分類結果的集合)
	 */
	@GetMapping("/list")
	public RestDto<List<Category>> queryList(@NonNull Category category) {
		// 聲明條件構造器；
		final LambdaQueryWrapper<Category> queryWrapper = Wrappers.lambdaQuery(new Category());
		// 添加條件；
		queryWrapper.eq(ComparisonUtils.isNotEqual(category.getType(), null), Category::getType, category.getType());
		// 添加排序條件；
		queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
		// 查詢分類結果集並返回；
		final List<Category> list = categoryService.list(queryWrapper);
		return RestDto.success(list);
	}
}
