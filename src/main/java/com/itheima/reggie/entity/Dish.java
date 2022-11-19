package com.itheima.reggie.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 菜品實體類
 *
 * @author Administrator
 */
@Data
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 菜品名稱
     */
    private String name;

    /**
     * 菜品分類ID
     */
    private Long categoryId;

    /**
     * 菜品價格
     */
    private BigDecimal price;

    /**
     * 商品碼
     */
    private String code;

    /**
     * 圖片
     */
    private String image;

    /**
     * 描述信息
     */
    private String description;


    /**
     * 菜品銷售狀態：0停售, 1在售;
     */
    private Integer status;

    /**
     * 順序
     */
    private Integer sort;


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    /**
     * 邏輯刪除字段
     */
    @TableLogic
    private Integer isDeleted;
}
