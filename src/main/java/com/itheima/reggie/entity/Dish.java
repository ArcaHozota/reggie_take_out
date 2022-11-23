package com.itheima.reggie.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;

import lombok.Data;

/**
 * 菜品實體類
 *
 * @author Administrator
 */
@Data
public class Dish implements Serializable {

	private static final long serialVersionUID = 6089472680388107154L;

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
	 * 菜品銷售狀態:0停售, 1在售;
	 */
	private Integer status;

	/**
	 * 順序
	 */
	private Integer sort;

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
