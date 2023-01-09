package jp.co.reggie.newdeal.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 客戸信息實體類
 *
 * @author Administrator
 */
public record User(@TableId Long id, String name, @TableField(value = "phone_num") String phoneNo,
		@TableField(value = "sex") String gender, String idNumber, String avatar, Integer status)
		implements Serializable {
	private static final long serialVersionUID = 2324630650798877027L;
}
