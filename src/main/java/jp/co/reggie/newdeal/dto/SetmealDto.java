package jp.co.reggie.newdeal.dto;

import java.util.List;

import jp.co.reggie.newdeal.entity.Setmeal;
import jp.co.reggie.newdeal.entity.SetmealDish;

/**
 * 套餐數據傳輸專用類
 *
 * @author Administrator
 */
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

	/**
	 * getter of setmealDishes
	 *
	 * @return
	 */
	public List<SetmealDish> getSetmealDishes() {
		return this.setmealDishes;
	}

	/**
	 * setter for setmealDishes
	 *
	 * @param setmealDishes
	 */
	public void setSetmealDishes(final List<SetmealDish> setmealDishes) {
		this.setmealDishes = setmealDishes;
	}

	/**
	 * getter of categoryName
	 *
	 * @return
	 */
	public String getCategoryName() {
		return this.categoryName;
	}

	/**
	 * setter for categoryName
	 *
	 * @param categoryName
	 */
	public void setCategoryName(final String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public String toString() {
		return "SetmealDto [setmealDishes=" + this.setmealDishes + ", categoryName=" + this.categoryName + "]";
	}
}
