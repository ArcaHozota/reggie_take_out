package com.itheima.reggie.common;

/**
 * 基於ThreadLocal封裝工具類，用於獲取和保存當前用戶ID；
 *
 * @author Administrator
 * @date 2022-11-18
 */
public class BaseContext {

	private static final ThreadLocal<Long> THREAD_LOCAL = new ThreadLocal<>();

	public static void setCurrentId(final Long id) {
		THREAD_LOCAL.set(id);
		THREAD_LOCAL.remove();
	}

	public static Long getCurrentId() {
		return THREAD_LOCAL.get();
	}
}
