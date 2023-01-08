package jp.co.reggie.newdeal.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jp.co.reggie.newdeal.entity.DishFlavor;
import jp.co.reggie.newdeal.mapper.DishFlavorDao;
import jp.co.reggie.newdeal.service.DishFlavorService;

/**
 * @author Administrator
 * @date 2022-11-23
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorDao, DishFlavor> implements DishFlavorService {
}
