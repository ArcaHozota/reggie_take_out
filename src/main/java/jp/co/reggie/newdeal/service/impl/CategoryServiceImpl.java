package jp.co.reggie.newdeal.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jp.co.reggie.newdeal.common.CustomException;
import jp.co.reggie.newdeal.common.CustomMessage;
import jp.co.reggie.newdeal.entity.Category;
import jp.co.reggie.newdeal.entity.Dish;
import jp.co.reggie.newdeal.entity.Setmeal;
import jp.co.reggie.newdeal.mapper.CategoryDao;
import jp.co.reggie.newdeal.service.CategoryService;
import jp.co.reggie.newdeal.service.DishService;
import jp.co.reggie.newdeal.service.SetmealService;

/**
 * @author Administrator
 * @date 2022-11-19
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {

	/**
	 * 菜品服務類；
	 */
	private final DishService dishService;

	/**
	 * 套餐服務類；
	 */
	private final SetmealService setmealService;

	// 構造器模式導入；
	private CategoryServiceImpl(final DishService dishService, final SetmealService setmealService) {
		this.dishService = dishService;
		this.setmealService = setmealService;
	}

	/**
	 * 根據ID刪除分類
	 *
	 * @param id 分類ID
	 */
	@Override
	public void remove(final Long id) {
		final LambdaQueryWrapper<Dish> dishQueryWrapper = new LambdaQueryWrapper<>();
		// 添加查詢條件，根據ID進行查詢；
		dishQueryWrapper.eq(Dish::categoryId, id);
		final long count1 = this.dishService.count(dishQueryWrapper);
		// 查詢當前分類是否已經關聯了菜品，如果已經關聯抛出一個異常；
		if (count1 > 0) {
			throw new CustomException(CustomMessage.ERP009);
		}
		final LambdaQueryWrapper<Setmeal> setMealQueryWrapper = new LambdaQueryWrapper<>();
		// 添加查詢條件，根據ID進行查詢；
		setMealQueryWrapper.eq(Setmeal::categoryId, id);
		final long count2 = this.setmealService.count(setMealQueryWrapper);
		// 查詢當前分類是否已經關聯了套餐，如果已經關聯抛出一個異常；
		if (count2 > 0) {
			throw new CustomException(CustomMessage.ERP009);
		}
		// 正常刪除分類；
		super.removeById(id);
	}
}
