package jp.co.reggie.newdeal.dto;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jp.co.reggie.newdeal.entity.SetmealDish;

/**
 * 套餐數據傳輸專用類
 *
 * @author Administrator
 */
public record SetmealDto(Long id, Long categoryId, String name, BigDecimal price, Integer status, String code,
		String description, String image, LocalDateTime createTime, LocalDateTime updateTime, Long createUser,
		Long updateUser, Integer isDeleted, List<SetmealDish> setmealDishes, String categoryName)
		implements Serializable {
	@Serial
	private static final long serialVersionUID = 5174917893420797875L;
}