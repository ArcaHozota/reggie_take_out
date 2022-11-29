package com.itheima.reggie.controller;


import com.itheima.reggie.common.CustomMessage;
import com.itheima.reggie.common.RestDto;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.service.SetmealDishService;
import com.itheima.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
}
