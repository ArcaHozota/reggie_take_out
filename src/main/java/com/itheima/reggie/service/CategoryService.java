package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Category;

/**
 * @author Administrator
 */
public interface CategoryService extends IService<Category> {

    /**
     * 根據ID刪除分類
     *
     * @param id 分類ID
     */
    public void remove(Long id);
}
