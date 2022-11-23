package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;

/**
 * @author Administrator
 */
public interface DishService extends IService<Dish> {

    /**
     * 新增菜品，同時插入菜品所對應的口味數據
     *
     * @param dishDto
     */
    public void saveWithFlavour(DishDto dishDto);
}
