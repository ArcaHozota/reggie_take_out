package jp.co.reggie.newdeal.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jp.co.reggie.newdeal.common.CustomException;
import jp.co.reggie.newdeal.common.CustomMessage;
import jp.co.reggie.newdeal.dto.SetmealDto;
import jp.co.reggie.newdeal.entity.Setmeal;
import jp.co.reggie.newdeal.entity.SetmealDish;
import jp.co.reggie.newdeal.mapper.SetmealMapper;
import jp.co.reggie.newdeal.service.SetmealDishService;
import jp.co.reggie.newdeal.service.SetmealService;

/**
 * @author Administrator
 * @date 2022-11-19
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

	/**
	 * 套餐與菜品服務類
	 */
	@Resource
	private SetmealDishService setmealDishService;

	/**
	 * 新增套餐同時保存套餐和菜品的關聯關係
	 *
	 * @param setmealDto 數據傳輸類
	 */
	@Override
	@Transactional
	public void saveWithDish(final SetmealDto setmealDto) {
		final Setmeal setmeal = new Setmeal();
		setmeal.setId(setmealDto.id());
		setmeal.setCategoryId(setmealDto.categoryId());
		setmeal.setName(setmealDto.name());
		setmeal.setPrice(setmealDto.price());
		setmeal.setStatus(setmealDto.status());
		setmeal.setCode(setmealDto.code());
		setmeal.setDescription(setmealDto.description());
		setmeal.setImage(setmealDto.image());
		setmeal.setIsDeleted(0);
		// 保存套餐的基本信息；
		this.save(setmeal);
		// 獲取套餐菜品關聯集合；
		List<SetmealDish> setmealDishes = setmealDto.setmealDishes();
		// 獲取菜品ID並插入集合；
		setmealDishes = setmealDishes.stream().map((item) -> {
			item.setDishId(setmealDto.id());
			return item;
		}).collect(Collectors.toList());
		// 保存套餐和菜品的關聯關係；
		this.setmealDishService.saveBatch(setmealDishes);
	}

	/**
	 * 刪除套餐同時刪除套餐和菜品的關聯關係
	 *
	 * @param ids 套餐ID的集合
	 */
	@Override
	public void removeWithDish(final List<Long> ids) {
		// 查詢套餐狀態以確認是否可以刪除；
		final LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.in(Setmeal::getId, ids);
		queryWrapper.eq(Setmeal::getStatus, 1);
		final long count = this.count(queryWrapper);
		if (count > 0) {
			// 如果無法刪除，則抛出異常；
			throw new CustomException(CustomMessage.ERP012);
		}
		// 刪除套餐表中的數據；
		this.removeByIds(ids);
		// 刪除套餐口味表中的數據；
		final LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);
		this.setmealDishService.remove(lambdaQueryWrapper);
	}
}
