package com.itheima.reggie.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

/**
 * 員工管理實體類
 *
 * @author Administrator
 */
@Data
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;

	/**
	 * 賬號名
	 */
	private String username;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 密碼
	 */
	private String password;

	/**
	 * 手機號
	 */
	@TableField(value = "phone_number")
	private String phoneNo;

	/**
	 * 性別
	 */
	@TableField(value = "sex")
	private String gender;

	/**
	 * 身份證號
	 */
	private String idNumber;

	/**
	 * 賬號狀態：0:禁用，1:正常
	 */
	private Integer status;

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

}
