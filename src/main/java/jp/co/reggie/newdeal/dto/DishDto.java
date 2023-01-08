package jp.co.reggie.newdeal.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.reggie.newdeal.entity.DishFlavor;

/**
 * 菜品以及口味數據傳輸專用類
 *
 * @author Administrator
 */
public class DishDto implements Serializable {

	private static final long serialVersionUID = 363782191754121480L;

	/**
	 * 口味集合
	 */
	private final List<DishFlavor> flavors = new ArrayList<>();

	/**
	 * 分類名稱
	 */
	private String categoryName;

	/**
	 * 複製品
	 */
	private Integer copy;

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

	/**
	 * getter of copy
	 *
	 * @return
	 */
	public Integer getCopy() {
		return this.copy;
	}

	/**
	 * setter for copy
	 *
	 * @param copy
	 */
	public void setCopy(final Integer copy) {
		this.copy = copy;
	}

	/**
	 * getter of flavors
	 *
	 * @return
	 */
	public List<DishFlavor> getFlavors() {
		return this.flavors;
	}

	@Override
	public String toString() {
		return "DishDto [flavors=" + this.flavors + ", categoryName=" + this.categoryName + ", copy=" + this.copy + "]";
	}
}
