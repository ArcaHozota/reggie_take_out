package jp.co.reggie.newdeal.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import jp.co.reggie.newdeal.entity.Category;

/**
 * @author Administrator
 */
public interface CategoryService extends IService<Category> {

	/**
	 * 根據ID刪除分類
	 *
	 * @param id 分類ID
	 */
	void remove(Long id);

	/**
	 * 根據類型查詢數據
	 *
	 * @param categoryType 類型
	 * @return List<Category>
	 */
	List<Category> findByType(Integer categoryType);
}
