package com.itheima.reggie.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import lombok.Data;

/**
 * 菜品口味實體類
 *
 * @author Administrator
 */
@Data
public class DishFlavor implements Serializable {

	private static final long serialVersionUID = 6752106293794210881L;

	/**
	 * ID
	 */
	@TableId
	private Long id;

	/**
	 * 菜品ID
	 */
	private Long dishId;

	/**
	 * 口味名稱
	 */
	private String name;

	/**
	 * 口味數據list
	 */
	private String value;

	/**
	 * 創建時間
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	/**
	 * 更新時間
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;

	/**
	 * 創建人
	 */
	@TableField(fill = FieldFill.INSERT)
	private Long createUser;

	/**
	 * 修改者
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Long updateUser;

	/**
	 * 邏輯刪除字段
	 */
	@TableLogic
	private Integer isDeleted;

}
