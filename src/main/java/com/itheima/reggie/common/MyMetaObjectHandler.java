package com.itheima.reggie.common;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * 自定義元數據對象處理器
 *
 * @author Administrator
 * @date 2022-11-17
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

	/**
	 * 插入操作，自動填充
	 *
	 * @param metaObject 任意填充類
	 */
	@Override
	public void insertFill(MetaObject metaObject) {
		metaObject.setValue("createTime", LocalDateTime.now());
		metaObject.setValue("updateTime", LocalDateTime.now());
		metaObject.setValue("createUser", BaseContext.getCurrentId());
		metaObject.setValue("updateUser", BaseContext.getCurrentId());
	}

	/**
	 * 更新操作，自動填充
	 *
	 * @param metaObject 任意填充類
	 */
	@Override
	public void updateFill(MetaObject metaObject) {
		metaObject.setValue("updateTime", LocalDateTime.now());
		metaObject.setValue("updateUser", BaseContext.getCurrentId());
	}
}
