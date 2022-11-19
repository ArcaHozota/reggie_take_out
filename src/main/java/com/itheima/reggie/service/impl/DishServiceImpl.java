package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.mapper.DishDao;
import com.itheima.reggie.service.DishService;
import org.springframework.stereotype.Service;


/**
 * @author Administrator
 * @date 2022-11-19
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishDao, Dish> implements DishService {
}
