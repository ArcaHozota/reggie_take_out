package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.mapper.CategoryDao;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.service.SetMealService;
import org.springframework.stereotype.Service;


/**
 * @author Administrator
 * @date 2022-11-19
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {

    private final DishService dishService;

    private final SetMealService setMealService;

    public CategoryServiceImpl(DishService dishService, SetMealService setMealService) {
        this.dishService = dishService;
        this.setMealService = setMealService;
    }

    /**
     * 根據ID刪除分類
     *
     * @param id 分類ID
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> queryWrapper = Wrappers.lambdaQuery(new Dish());
        // 添加查詢條件，根據ID進行查詢；
        queryWrapper.eq(Dish::getCategoryId, id);
        final int count = dishService.count(queryWrapper);
        // 查詢當前分類是否已經關聯了菜品，如果已經關聯抛出一個異常；
    }
}
