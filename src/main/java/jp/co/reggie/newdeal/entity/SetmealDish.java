package jp.co.reggie.newdeal.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

/**
 * 套餐與菜品關係實體類
 *
 * @author Administrator
 */
@Data
public class SetmealDish implements Serializable {

	private static final long serialVersionUID = -641135780975738908L;

	/**
	 * ID
	 */
	@TableId
	private Long id;

	/**
	 * 套餐ID
	 */
	private Long setmealId;

	/**
	 * 菜品ID
	 */
	private Long dishId;

	/**
	 * 菜品名稱(冗餘字段)
	 */
	private String name;

	/**
	 * 菜品原價(冗餘字段)
	 */
	private BigDecimal price;

	/**
	 * 份數
	 */
	private Integer copies;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 創建時間
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	/**
	 * 更新時間
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;

	/**
	 * 創建人
	 */
	@TableField(fill = FieldFill.INSERT)
	private Long createUser;

	/**
	 * 修改者
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Long updateUser;

	/**
	 * 邏輯刪除字段
	 */
	@TableLogic
	private Integer isDeleted;
}
