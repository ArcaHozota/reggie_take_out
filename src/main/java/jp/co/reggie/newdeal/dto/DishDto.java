package jp.co.reggie.newdeal.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.co.reggie.newdeal.entity.DishFlavor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜品以及口味數據傳輸專用類
 *
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = false)
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
}