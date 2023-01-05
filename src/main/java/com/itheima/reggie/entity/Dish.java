package com.itheima.reggie.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * 菜品實體類
 *
 * @author Administrator
 */
@Getter
@Setter
@Entity
@Table(name = "dish")
public class Dish implements Serializable {

	private static final long serialVersionUID = 6089472680388107154L;

	/**
	 * ID
	 */
	@Id
	@GenericGenerator(name = "snowflakeId", strategy = "com.itheima.reggie.utils.SnowflakeIdGenerator")
	@GeneratedValue(generator = "snowflakeId")
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
