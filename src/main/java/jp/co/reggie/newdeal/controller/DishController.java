package jp.co.reggie.newdeal.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jp.co.reggie.newdeal.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import jakarta.annotation.Resource;
import jp.co.reggie.newdeal.common.CustomException;
import jp.co.reggie.newdeal.common.CustomMessage;
import jp.co.reggie.newdeal.dto.DishDto;
import jp.co.reggie.newdeal.entity.Category;
import jp.co.reggie.newdeal.entity.Dish;
import jp.co.reggie.newdeal.service.CategoryService;
import jp.co.reggie.newdeal.service.DishFlavorService;
import jp.co.reggie.newdeal.service.DishService;
import jp.co.reggie.newdeal.utils.Reggie;

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
	public Reggie<Page<DishDto>> pagination(@RequestParam("pageNum") final Integer pageNum,
			@RequestParam("pageSize") final Integer pageSize, @RequestParam("name") final String name) {
		// 聲明分頁構造器對象；
		final Page<Dish> pageInfo = Page.of(pageNum, pageSize);
		final Page<DishDto> dtoPage = new Page<>();
		// 創建條件構造器；
		final LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
		// 添加過濾條件；
		queryWrapper.like(StringUtils.isNotEmpty(name), Dish::getName, name);
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
			// 獲取分類ID；
			final Long categoryId = item.getCategoryId();
			// 根據ID查詢分類對象；
			final Category category = this.categoryService.getById(categoryId);
			if (category != null) {
				// 獲取分類名稱；
				final String categoryName = category.getName();
				// 聲明菜品及口味數據傳輸類對象並拷貝除分類ID以外的屬性；
				return new DishDto(item.getId(), item.getName(), categoryId, item.getPrice(), item.getCode(),
						item.getImage(), item.getDescription(), item.getStatus(), item.getSort(), item.getCreateTime(),
						item.getUpdateTime(), item.getCreateUser(), item.getUpdateUser(), item.getIsDeleted(), null,
						categoryName, null);
			} else {
				// 聲明菜品及口味數據傳輸類對象並拷貝除分類ID以外的屬性；
				return new DishDto(item.getId(), item.getName(), categoryId, item.getPrice(), item.getCode(),
						item.getImage(), item.getDescription(), item.getStatus(), item.getSort(), item.getCreateTime(),
						item.getUpdateTime(), item.getCreateUser(), item.getUpdateUser(), item.getIsDeleted(), null,
						null, null);
			}
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
	public Reggie<DishDto> getDishInfo(@PathVariable final Long id) {
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
		final LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
		// 添加搜索條件；
		queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
		queryWrapper.eq(Dish::getStatus, 1);
		// 添加排序條件；
		queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
		// 查詢菜品信息並返回；
		final List<Dish> list = this.dishService.list(queryWrapper);
		return Reggie.success(list);
	}

	/**
	 * 修改菜品在售狀態
	 *
	 * @param status 菜品狀態
	 * @return R.success(修改成功信息)
	 */
	@PostMapping("/status/{status}")
	public Reggie<String> changeStatus(@PathVariable Integer status, @RequestParam("ids") final Long[] ids) {
		switch (status) {
			case 0 -> status = 1;
			case 1 -> status = 0;
			default -> throw new CustomException((CustomMessage.ERP017));
		}
		if (ids.length == 1) {
			final Dish dish = this.dishService.getById(ids[0]);
			dish.setStatus(status);
			this.dishService.updateById(dish);
		} else {
			final List<Dish> dList = new ArrayList<>();
			for (final Long id : ids) {
				final Dish dish = this.dishService.getById(id);
				dish.setStatus(status);
				dList.add(dish);
			}
			this.dishService.updateBatchById(dList);
		}
		return Reggie.success(CustomMessage.SRP016);
	}
}
