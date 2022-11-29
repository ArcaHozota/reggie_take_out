package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.entity.SetmealDish;
import com.itheima.reggie.mapper.SetmealDao;
import com.itheima.reggie.service.SetmealDishService;
import com.itheima.reggie.service.SetmealService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Administrator
 * @date 2022-11-19
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealDao, Setmeal> implements SetmealService {

    private final SetmealDishService setmealDishService;

    public SetmealServiceImpl(SetmealDishService setmealDishService) {
        this.setmealDishService = setmealDishService;
    }

    /**
     * 新增套餐同時保存套餐和菜品的關聯關係
     *
     * @param setmealDto 數據傳輸類
     */
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        // 保存套餐的基本信息；
        this.save(setmealDto);
        // 獲取套餐菜品關聯集合；
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        // 獲取菜品ID並插入集合；
        setmealDishes = setmealDishes.stream().map((item) -> {
            item.setDishId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        // 保存套餐和菜品的關聯關係；
        setmealDishService.saveBatch(setmealDishes);
    }
}
