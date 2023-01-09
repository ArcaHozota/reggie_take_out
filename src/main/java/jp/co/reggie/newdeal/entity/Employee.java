package jp.co.reggie.newdeal.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 員工管理實體類
 *
 * @author Administrator
 */
public record Employee(@TableId Long id, String username, String name, String password,
		@TableField(value = "phone_num") String phoneNo, @TableField(value = "sex") String gender, String idNumber,
		Integer status, @TableField(fill = FieldFill.INSERT) LocalDateTime createTime,
		@TableField(fill = FieldFill.INSERT_UPDATE) LocalDateTime updateTime,
		@TableField(fill = FieldFill.INSERT) Long createUser,
		@TableField(fill = FieldFill.INSERT_UPDATE) Long updateUser) implements Serializable {
	private static final long serialVersionUID = -6540113185665801143L;
}
