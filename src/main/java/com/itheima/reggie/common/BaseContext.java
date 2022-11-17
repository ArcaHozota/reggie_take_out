package com.itheima.reggie.common;

/**
 * 基於ThreadLocal封裝工具類，用於獲取和保存當前用戶ID；
 *
 * @author Administrator
 * @date 2022-11-18
 */
public class BaseContext {

    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }
}
