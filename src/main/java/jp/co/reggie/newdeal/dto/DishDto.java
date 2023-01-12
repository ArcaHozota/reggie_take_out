package jp.co.reggie.newdeal.dto;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jp.co.reggie.newdeal.entity.DishFlavor;

/**
 * 菜品以及口味數據傳輸專用類
 *
 * @author Administrator
 */
public record DishDto(Long id, String name, Long categoryId, BigDecimal price, String code, String image,
		String description, Integer status, Integer sort, LocalDateTime createTime, LocalDateTime updateTime,
		Long createUser, Long updateUser, Integer isDeleted, List<DishFlavor> flavors, String categoryName,
		Integer copy) implements Serializable {
	@Serial
	private static final long serialVersionUID = 363782191754121480L;
}