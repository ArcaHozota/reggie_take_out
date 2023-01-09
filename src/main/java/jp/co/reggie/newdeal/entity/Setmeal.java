package jp.co.reggie.newdeal.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

/**
 * 套餐實體類
 *
 * @author Administrator
 */
public record Setmeal(@TableId Long id, Long categoryId, String name, BigDecimal price, Integer status, String code,
		String description, String image, @TableField(fill = FieldFill.INSERT) LocalDateTime createTime,
		@TableField(fill = FieldFill.INSERT_UPDATE) LocalDateTime updateTime,
		@TableField(fill = FieldFill.INSERT) Long createUser,
		@TableField(fill = FieldFill.INSERT_UPDATE) Long updateUser, @TableLogic Integer isDeleted)
		implements Serializable {
	private static final long serialVersionUID = 4020217756505140488L;
}