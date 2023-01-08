package com.itheima.reggie.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

/**
 * 分類管理實體類
 *
 * @author Administrator
 */
public record Category(@TableId Long id, Integer type, String name, Integer sort,
		@TableField(fill = FieldFill.INSERT) LocalDateTime createTime,
		@TableField(fill = FieldFill.INSERT_UPDATE) LocalDateTime updateTime,
		@TableField(fill = FieldFill.INSERT) Long createUser,
		@TableField(fill = FieldFill.INSERT_UPDATE) Long updateUser, @TableLogic Integer isDeleted)
		implements Serializable {
	private static final long serialVersionUID = -5583580956537498025L;
}
