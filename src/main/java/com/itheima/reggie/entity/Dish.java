package com.itheima.reggie.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

/**
 * 菜品實體類
 *
 * @author Administrator
 */
public record Dish(@TableId Long id, String name, Long categoryId, BigDecimal price, String code, String image,
		String description, Integer status, Integer sort, @TableField(fill = FieldFill.INSERT) LocalDateTime createTime,
		@TableField(fill = FieldFill.INSERT_UPDATE) LocalDateTime updateTime,
		@TableField(fill = FieldFill.INSERT) Long createUser,
		@TableField(fill = FieldFill.INSERT_UPDATE) Long updateUser, @TableLogic Integer isDeleted)
		implements Serializable {
	private static final long serialVersionUID = 6089472680388107154L;
}
