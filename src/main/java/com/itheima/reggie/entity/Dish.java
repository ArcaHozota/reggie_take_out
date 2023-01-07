package com.itheima.reggie.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 菜品實體類
 *
 * @author Administrator
 */
public record Dish(Long id, String name, Long categoryId, BigDecimal price, String code, String image,
		String description, Integer status, Integer sort, LocalDateTime createTime, Long createUser,
		LocalDateTime updateTime, Long updateUser, Integer isDeleted) implements Serializable {
	private static final long serialVersionUID = 6089472680388107154L;
}
