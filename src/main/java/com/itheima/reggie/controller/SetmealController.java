package com.itheima.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.CustomMessage;
import com.itheima.reggie.common.RestDto;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.SetmealDishService;
import com.itheima.reggie.service.SetmealService;
import com.itheima.reggie.utils.ComparisonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 套餐管理控制器
 *
 * @author Administrator
 * @date 2022-11-29
 */
@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Resource
    private SetmealService setmealService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐
     *
     * @param setmealDto 數據傳輸類
     * @return R.success(新增成功的信息)
     */
    @PostMapping
    public RestDto<String> save(@RequestBody SetmealDto setmealDto) {
        log.info("套餐信息：{}" + setmealDto);
        // 儲存套餐；
        setmealService.saveWithDish(setmealDto);
        return RestDto.success(CustomMessage.SRP010);
    }

    /**
     * 分頁信息顯示
     *
     * @param pageNum  頁碼
     * @param pageSize 頁面大小
     * @param name     檢索文
     * @return R.success(分頁信息)
     */
    @GetMapping("/page")
    public RestDto<Page<SetmealDto>> pagination(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, @Param("name") String name) {
        // 聲明分頁構造器；
        final Page<Setmeal> pageInfo = new Page<>(pageNum, pageSize);
        final Page<SetmealDto> dtoPage = new Page<>();
        // 聲明條件構造器；
        final LambdaQueryWrapper<Setmeal> queryWrapper = Wrappers.lambdaQuery(new Setmeal());
        // 添加查詢條件，根據檢索文進行模糊查詢；
        queryWrapper.like(ComparisonUtils.isNotEqual(name, null), Setmeal::getName, name);
        // 添加排序條件；
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        // 執行查詢；
        setmealService.page(pageInfo, queryWrapper);
        // 拷貝屬性；
        BeanUtils.copyProperties(pageInfo, dtoPage, "records");
        // 獲取分頁數據；
        final List<Setmeal> records = pageInfo.getRecords();
        // 獲取數據傳輸類分頁；
        final List<SetmealDto> list = records.stream().map((item) -> {
            // 聲明套餐數據傳輸類；
            SetmealDto setmealDto = new SetmealDto();
            // 對象拷貝；
            BeanUtils.copyProperties(item, setmealDto);
            // 獲取分類ID；
            final Long categoryId = item.getCategoryId();
            // 根據分類ID獲取分類對象；
            final Category category = categoryService.getById(categoryId);
            // 分類對象存在；
            if (ComparisonUtils.isNotEqual(null, category)) {
                // 獲取分類名稱並設置到數據傳輸類中；
                final String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());
        // 設置分頁數據於構造器中並返回；
        dtoPage.setRecords(list);
        return RestDto.success(dtoPage);
    }
}
