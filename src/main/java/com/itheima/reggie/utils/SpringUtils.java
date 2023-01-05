package com.itheima.reggie.utils;

import cn.hutool.extra.spring.SpringUtil;

public class SpringUtils extends SpringUtil {

	/**
	 * 通過類反射獲取Bean
	 *
	 * @param <T>   Bean類型
	 * @param clazz Bean類
	 * @return Bean對象
	 */
	public static <T> T getBean(final Class<T> clazz) {
		return getBeanFactory().getBean(clazz);
	}
}
