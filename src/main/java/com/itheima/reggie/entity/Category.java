package com.itheima.reggie.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 分類管理實體類
 *
 * @author Administrator
 */
@Getter
@Setter
@Entity
@Table(name = "category")
public class Category implements Serializable {

	private static final long serialVersionUID = -5583580956537498025L;

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 類型：1、菜品分類；2、套餐分類；
	 */
	private Integer type;

	/**
	 * 分類名稱
	 */
	private String name;

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
