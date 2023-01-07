package com.itheima.reggie.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 分類管理實體類
 *
 * @author Administrator
 */
public record Category(Long id, Integer type, String name, Integer sort, LocalDateTime createTime,
		LocalDateTime updateTime, Long createUser, Long updateUser, Integer isDeleted) implements Serializable {
	private static final long serialVersionUID = -5583580956537498025L;
}
