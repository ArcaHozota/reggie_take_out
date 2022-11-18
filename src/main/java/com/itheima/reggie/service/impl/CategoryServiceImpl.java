package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.mapper.CategoryDao;
import com.itheima.reggie.service.CategoryService;
import org.springframework.stereotype.Service;


/**
 * @author Administrator
 * @date 2022-11-19
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {
}
