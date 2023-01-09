package jp.co.reggie.newdeal.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import jakarta.annotation.Resource;
import jp.co.reggie.newdeal.common.CustomMessage;
import jp.co.reggie.newdeal.entity.Category;
import jp.co.reggie.newdeal.service.CategoryService;
import jp.co.reggie.newdeal.utils.Reggie;

/**
 * 分類管理控制器
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

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
	public Reggie<Page<Category>> pagination(@RequestParam("pageNum") final Integer pageNum,
			@RequestParam("pageSize") final Integer pageSize) {
		// 聲明分頁構造器；
		final Page<Category> pageInfo = Page.of(pageNum, pageSize);
		// 聲明條件構造器；
		final LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
		// 添加排序條件，根據sort進行排序；
		queryWrapper.orderByAsc(Category::sort);
		// 執行查詢；
		this.categoryService.page(pageInfo, queryWrapper);
		return Reggie.success(pageInfo);
	}

	/**
	 * 新增分類
	 *
	 * @param category 實體類對象
	 * @return R.success(分類新增成功的信息);
	 */
	@PostMapping
	public Reggie<String> save(@RequestBody final Category category) {
		LOGGER.info("category:{}", category);
		this.categoryService.save(category);
		return Reggie.success(CustomMessage.SRP001);
	}

	/**
	 * 刪除分類
	 *
	 * @param id 分類ID
	 * @return R.success(分類刪除成功的信息);
	 */
	@DeleteMapping
	public Reggie<String> delete(@RequestParam("ids") final Long id) {
		LOGGER.info("刪除ID={}的分類", id);
		// 實施刪除；
		this.categoryService.remove(id);
		return Reggie.success(CustomMessage.SRP003);
	}

	/**
	 * 更新分類
	 *
	 * @param category 實體類對象
	 * @return R.success(分類更新成功的信息);
	 */
	@PutMapping
	public Reggie<String> update(@RequestBody final Category category) {
		LOGGER.info("修改分類信息：{}", category);
		// 執行修改操作；
		this.categoryService.updateById(category);
		return Reggie.success(CustomMessage.SRP002);
	}

	/**
	 * 根據條件查詢分類數據
	 *
	 * @param category 實體類對象
	 * @return R.success(分類結果的集合)
	 */
	@GetMapping("/list")
	public Reggie<List<Category>> queryList(final Category category) {
		// 聲明條件構造器；
		final LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
		// 添加條件；
		queryWrapper.eq(category.type() != null, Category::type, category.type());
		// 添加排序條件；
		queryWrapper.orderByAsc(Category::sort).orderByDesc(Category::updateTime);
		// 查詢分類結果集並返回；
		final List<Category> list = this.categoryService.list(queryWrapper);
		return Reggie.success(list);
	}
}
