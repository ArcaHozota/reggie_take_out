package jp.co.reggie.newdeal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jp.co.reggie.newdeal.common.CustomException;
import jp.co.reggie.newdeal.common.CustomMessage;
import jp.co.reggie.newdeal.entity.Category;
import jp.co.reggie.newdeal.entity.Dish;
import jp.co.reggie.newdeal.entity.Setmeal;
import jp.co.reggie.newdeal.mapper.CategoryMapper;
import jp.co.reggie.newdeal.service.CategoryService;
import jp.co.reggie.newdeal.service.DishService;
import jp.co.reggie.newdeal.service.SetmealService;

/**
 * @author Administrator
 * @date 2022-11-19
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

	/**
	 * 分類mapper
	 */
	@Resource
	private CategoryMapper categoryMapper;

	/**
	 * 菜品服務類；
	 */
	@Resource
	private DishService dishService;

	/**
	 * 套餐服務類；
	 */
	@Resource
	private SetmealService setmealService;

	/**
	 * 根據ID刪除分類
	 *
	 * @param id 分類ID
	 */
	@Override
	public void remove(final Long id) {
		final LambdaQueryWrapper<Dish> dishQueryWrapper = new LambdaQueryWrapper<>();
		// 添加查詢條件，根據ID進行查詢；
		dishQueryWrapper.eq(Dish::getCategoryId, id);
		final long count1 = this.dishService.count(dishQueryWrapper);
		// 查詢當前分類是否已經關聯了菜品，如果已經關聯抛出一個異常；
		if (count1 > 0) {
			throw new CustomException(CustomMessage.ERP009);
		}
		final LambdaQueryWrapper<Setmeal> setMealQueryWrapper = new LambdaQueryWrapper<>();
		// 添加查詢條件，根據ID進行查詢；
		setMealQueryWrapper.eq(Setmeal::getCategoryId, id);
		final long count2 = this.setmealService.count(setMealQueryWrapper);
		// 查詢當前分類是否已經關聯了套餐，如果已經關聯抛出一個異常；
		if (count2 > 0) {
			throw new CustomException(CustomMessage.ERP009);
		}
		// 正常刪除分類；
		super.removeById(id);
	}

	/**
	 * 根據類型查詢數據
	 *
	 * @param categoryType 類型
	 * @return List<Category>
	 */
	@Override
	public List<Category> findByType(final Integer categoryType) {
		return this.categoryMapper.selectByType(categoryType);
	}
}
