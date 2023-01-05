package com.itheima.reggie.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	@GenericGenerator(name = "snowflakeId", strategy = "com.itheima.reggie.utils.SnowflakeIdGenerator")
	@GeneratedValue(generator = "snowflakeId")
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "create_time", updatable = false)
	private LocalDateTime createTime;

	/**
	 * 創建人
	 */
	@Column(name = "create_user", updatable = false)
	private Long createUser;

	/**
	 * 更新時間
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "update_time")
	private LocalDateTime updateTime;

	/**
	 * 修改者
	 */
	@Column(name = "update_user")
	private Long updateUser;

	/**
	 * 邏輯刪除字段
	 */
	@Column(name = "is_deleted")
	private Integer isDeleted;
}
