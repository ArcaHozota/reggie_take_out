package com.itheima.reggie.service.impl;

import com.itheima.reggie.common.CustomMessage;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.mapper.CategoryDao;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.service.SetmealService;

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
    private CategoryServiceImpl(DishService dishService, SetmealService setmealService) {
        this.dishService = dishService;
        this.setmealService = setmealService;
    }

    /**
     * 根據ID刪除分類
     *
     * @param id 分類ID
     */
    @Override
    public void remove(Long id) {
        final LambdaQueryWrapper<Dish> dishQueryWrapper = Wrappers.lambdaQuery(new Dish());
        // 添加查詢條件，根據ID進行查詢；
        dishQueryWrapper.eq(Dish::getCategoryId, id);
        final long count1 = dishService.count(dishQueryWrapper);
        // 查詢當前分類是否已經關聯了菜品，如果已經關聯抛出一個異常；
        if (count1 > 0) {
            throw new CustomException(CustomMessage.ERP090);
        }
        final LambdaQueryWrapper<Setmeal> setMealQueryWrapper = Wrappers.lambdaQuery(new Setmeal());
        // 添加查詢條件，根據ID進行查詢；
        setMealQueryWrapper.eq(Setmeal::getCategoryId, id);
        final long count2 = setmealService.count(setMealQueryWrapper);
        // 查詢當前分類是否已經關聯了套餐，如果已經關聯抛出一個異常；
        if (count2 > 0) {
            throw new CustomException(CustomMessage.ERP090);
        }
        // 正常刪除分類；
        super.removeById(id);
    }
}
