package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 分類管理控制器
 *
 * @author Administrator
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    /**
     * 分頁信息顯示
     *
     * @param pageNum  頁碼
     * @param pageSize 頁面大小
     * @return R.success(分頁信息)
     */
    @GetMapping("/page")
    public R<Page<Category>> page(Integer pageNum, Integer pageSize) {
        // 聲明分頁構造器；
        Page<Category> pageInfo = new Page<>(pageNum, pageSize);
        // 聲明條件構造器；
        LambdaQueryWrapper<Category> queryWrapper = Wrappers.lambdaQuery(new Category());
        // 添加排序條件，根據sort進行排序；
        queryWrapper.orderByAsc(Category::getSort);
        // 執行查詢；
        categoryService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 新增分類
     *
     * @param category 實體類對象
     * @return R.success(分類新增成功的信息);
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("category:{}", category);
        categoryService.save(category);
        return R.success("新增分類成功");
    }
}
