package com.itheima.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 套餐實體類
 *
 * @author Administrator
 */
@Data
public class Setmeal implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 分類ID
     */
    private Long categoryId;

    /**
     * 套餐名稱
     */
    private String name;

    /**
     * 套餐價格
     */
    private BigDecimal price;

    /**
     * 套餐在售狀態：0停售, 1在售;
     */
    private Integer status;

    /**
     * 編碼
     */
    private String code;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 圖片
     */
    private String image;


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
