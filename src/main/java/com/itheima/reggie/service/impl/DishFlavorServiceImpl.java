package com.itheima.reggie.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.entity.DishFlavor;
import com.itheima.reggie.mapper.DishFlavorDao;
import com.itheima.reggie.service.DishFlavorService;

/**
 * @author Administrator
 * @date 2022-11-23
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorDao, DishFlavor> implements DishFlavorService {
}
