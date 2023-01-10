package jp.co.reggie.newdeal.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jp.co.reggie.newdeal.dto.DishDto;
import jp.co.reggie.newdeal.entity.Dish;
import jp.co.reggie.newdeal.entity.DishFlavor;
import jp.co.reggie.newdeal.mapper.DishMapper;
import jp.co.reggie.newdeal.service.DishFlavorService;
import jp.co.reggie.newdeal.service.DishService;

/**
 * @author Administrator
 * @date 2022-11-19
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

	/**
	 * 菜品口味服務類
	 */
	private final DishFlavorService dishFlavorService;

	// 構造器模式導入；
	private DishServiceImpl(final DishFlavorService dishFlavorService) {
		this.dishFlavorService = dishFlavorService;
	}

	/**
	 * 新增菜品，同時插入菜品所對應的口味數據
	 *
	 * @param dishDto 菜品及口味數據傳輸類
	 */
	@Override
	@Transactional
	public void saveWithFlavour(final DishDto dishDto) {
		// 保存菜品的基本信息到菜品表；
		final Dish dish = new Dish(dishDto.id(), dishDto.name(), dishDto.categoryId(), dishDto.price(), dishDto.code(),
				dishDto.image(), dishDto.description(), dishDto.status(), dishDto.sort(), null, null, null, null, 0);
		this.save(dish);
		// 獲取菜品口味的集合；
		List<DishFlavor> flavors = dishDto.flavors();
		// 將菜品ID設置到口味集合中；
		flavors = flavors.stream().map((item) -> {
			final DishFlavor dishFlavor = new DishFlavor(item.id(), dishDto.id(), item.name(), item.value(),
					item.createTime(), item.updateTime(), item.createUser(), item.updateUser(), item.isDeleted());
			return dishFlavor;
		}).collect(Collectors.toList());
		// 保存 菜品的口味數據到口味表；
		this.dishFlavorService.saveBatch(flavors);
	}

	/**
	 * 根據ID查詢菜品信息以及對應的口味信息
	 *
	 * @param id 菜品ID
	 * @return dishDto 菜品及口味數據傳輸類
	 */
	@Override
	public DishDto getByIdWithFlavour(final Long id) {
		// 查詢菜品的基本信息；
		final Dish dish = this.getById(id);
		// 查詢當前菜品所對應的口味信息；
		final LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(DishFlavor::id, dish.id());
		// 獲取菜品口味列表；
		final List<DishFlavor> flavors = this.dishFlavorService.list(queryWrapper);
		// 聲明一個菜品及口味數據傳輸類對象並拷貝屬性；
		final DishDto dishDto = new DishDto(dish.id(), dish.name(), dish.categoryId(), dish.price(), dish.code(),
				dish.image(), dish.description(), dish.status(), dish.sort(), dish.createTime(), dish.updateTime(),
				dish.createUser(), dish.updateUser(), dish.isDeleted(), flavors, null, null);
		return dishDto;
	}

	/**
	 * 修改菜品信息並同時插入菜品所對應的口味數據
	 *
	 * @param dishDto 菜品及口味數據傳輸類
	 */
	@Override
	@Transactional
	public void updateWithFlavour(final DishDto dishDto) {
		// 聲明一個菜品實體類對象；
		final Dish dish = new Dish(dishDto.id(), dishDto.name(), dishDto.categoryId(), dishDto.price(), dishDto.code(),
				dishDto.image(), dishDto.description(), dishDto.status(), dishDto.sort(), null, null, null, null, 0);
		// 更新菜品信息；
		this.updateById(dish);
		// 清理當前菜品所對應的口味信息；
		final LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(DishFlavor::id, dishDto.id());
		this.dishFlavorService.remove(queryWrapper);
		// 添加當前菜品的口味數據；
		List<DishFlavor> flavors = dishDto.flavors();
		// 將菜品ID設置到口味集合中；
		flavors = flavors.stream().map((item) -> {
			final DishFlavor dishFlavor = new DishFlavor(item.id(), dishDto.id(), item.name(), item.value(),
					item.createTime(), item.updateTime(), item.createUser(), item.updateUser(), item.isDeleted());
			return dishFlavor;
		}).collect(Collectors.toList());
		this.dishFlavorService.saveBatch(flavors);
	}
}
