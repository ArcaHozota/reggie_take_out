package com.itheima.reggie.dto;

import java.util.List;

import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.entity.SetmealDish;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 套餐數據傳輸專用類
 *
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SetmealDto extends Setmeal {

	private static final long serialVersionUID = 5174917893420797875L;

	/**
	 * 套餐集合
	 */
	private List<SetmealDish> setmealDishes;

	/**
	 * 分類名稱
	 */
	private String categoryName;
}
