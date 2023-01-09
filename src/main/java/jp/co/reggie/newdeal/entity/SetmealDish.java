package jp.co.reggie.newdeal.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

/**
 * 套餐與菜品關係實體類
 *
 * @author Administrator
 */
public record SetmealDish(@TableId Long id, Long setmealId, Long dishId, String name, BigDecimal price, Integer copies,
		Integer sort, @TableField(fill = FieldFill.INSERT) LocalDateTime createTime,
		@TableField(fill = FieldFill.INSERT_UPDATE) LocalDateTime updateTime,
		@TableField(fill = FieldFill.INSERT) Long createUser,
		@TableField(fill = FieldFill.INSERT_UPDATE) Long updateUser, @TableLogic Integer isDeleted)
		implements Serializable {
	private static final long serialVersionUID = -641135780975738908L;
}
