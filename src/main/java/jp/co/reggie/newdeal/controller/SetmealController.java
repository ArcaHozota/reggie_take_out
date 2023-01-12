package jp.co.reggie.newdeal.controller;

import java.util.List;
import java.util.stream.Collectors;

import jp.co.reggie.newdeal.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import jakarta.annotation.Resource;
import jp.co.reggie.newdeal.common.CustomMessage;
import jp.co.reggie.newdeal.dto.SetmealDto;
import jp.co.reggie.newdeal.entity.Category;
import jp.co.reggie.newdeal.entity.Setmeal;
import jp.co.reggie.newdeal.service.CategoryService;
import jp.co.reggie.newdeal.service.SetmealDishService;
import jp.co.reggie.newdeal.service.SetmealService;
import jp.co.reggie.newdeal.utils.Reggie;

/**
 * 套餐管理控制器
 *
 * @author Administrator
 * @date 2022-11-29
 */
@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

	@Resource
	private SetmealService setmealService;

	@Resource
	private CategoryService categoryService;

	@Resource
	private SetmealDishService setmealDishService;

	/**
	 * 新增套餐
	 *
	 * @param setmealDto 數據傳輸類
	 * @return R.success(新增成功的信息)
	 */
	@PostMapping
	public Reggie<String> save(@RequestBody final SetmealDto setmealDto) {
		log.info("套餐信息：{}", setmealDto);
		// 儲存套餐；
		this.setmealService.saveWithDish(setmealDto);
		return Reggie.success(CustomMessage.SRP010);
	}

	/**
	 * 刪除套餐
	 *
	 * @param ids 套餐ID的集合
	 * @return R.success(新增成功的信息)
	 */
	@DeleteMapping
	public Reggie<String> delete(@RequestParam("ids") final List<Long> ids) {
		log.info("套餐ID：{}", ids);
		this.setmealService.removeWithDish(ids);
		return Reggie.success(CustomMessage.SRP011);
	}

	/**
	 * 分頁信息顯示
	 *
	 * @param pageNum  頁碼
	 * @param pageSize 頁面大小
	 * @param name     檢索文
	 * @return R.success(分頁信息)
	 */
	@GetMapping("/page")
	public Reggie<Page<SetmealDto>> pagination(@RequestParam("pageNum") final Integer pageNum,
			@RequestParam("pageSize") final Integer pageSize,
			@RequestParam(name = "name", required = false) final String name) {
		// 聲明分頁構造器；
		final Page<Setmeal> pageInfo = Page.of(pageNum, pageSize);
		final Page<SetmealDto> dtoPage = new Page<>();
		// 聲明條件構造器；
		final LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
		// 添加查詢條件，根據檢索文進行模糊查詢；
		queryWrapper.like(StringUtils.isNotEmpty(name), Setmeal::getName, name);
		// 添加排序條件；
		queryWrapper.orderByDesc(Setmeal::getUpdateTime);
		// 執行查詢；
		this.setmealService.page(pageInfo, queryWrapper);
		// 拷貝屬性；
		BeanUtils.copyProperties(pageInfo, dtoPage, "records");
		// 獲取分頁數據；
		final List<Setmeal> records = pageInfo.getRecords();
		// 獲取數據傳輸類分頁；
		final List<SetmealDto> list = records.stream().map((item) -> {
			// 獲取分類ID；
			final Long categoryId = item.getCategoryId();
			// 根據分類ID獲取分類對象；
			final Category category = this.categoryService.getById(categoryId);
			// 分類對象存在；
			if (category != null) {
				// 獲取分類名稱並設置到數據傳輸類中；
				final String categoryName = category.getName();
				// 聲明套餐數據傳輸類並拷貝除分類ID以外的屬性；
				return new SetmealDto(item.getId(), item.getCategoryId(), item.getName(), item.getPrice(),
						item.getStatus(), item.getCode(), item.getDescription(), item.getImage(), item.getCreateTime(),
						item.getUpdateTime(), item.getCreateUser(), item.getUpdateUser(), item.getIsDeleted(), null,
						categoryName);
			} else {
				// 聲明套餐數據傳輸類並拷貝除分類ID以外的屬性；
				return new SetmealDto(item.getId(), item.getCategoryId(), item.getName(), item.getPrice(),
						item.getStatus(), item.getCode(), item.getDescription(), item.getImage(), item.getCreateTime(),
						item.getUpdateTime(), item.getCreateUser(), item.getUpdateUser(), item.getIsDeleted(), null,
						null);
			}
		}).collect(Collectors.toList());
		// 設置分頁數據於構造器中並返回；
		dtoPage.setRecords(list);
		return Reggie.success(dtoPage);
	}
}
