package jp.co.reggie.newdeal.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

/**
 * 菜品口味實體類
 *
 * @author Administrator
 */
public record DishFlavor(@TableId Long id, Long dishId, String name, String value,
		@TableField(fill = FieldFill.INSERT) LocalDateTime createTime,
		@TableField(fill = FieldFill.INSERT_UPDATE) LocalDateTime updateTime,
		@TableField(fill = FieldFill.INSERT) Long createUser,
		@TableField(fill = FieldFill.INSERT_UPDATE) Long updateUser, @TableLogic Integer isDeleted)
		implements Serializable {
	private static final long serialVersionUID = 6752106293794210881L;
}
