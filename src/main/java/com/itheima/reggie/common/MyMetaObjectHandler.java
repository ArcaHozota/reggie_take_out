package com.itheima.reggie.common;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定義元數據對象處理器
 *
 * @author Administrator
 * @date 2022-11-17
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

	/**
	 * 插入操作，自動填充
	 *
	 * @param metaObject 任意填充類
	 */
	@Override
	public void insertFill(MetaObject metaObject) {
		log.info("公共字段自動填充[insert]...");
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
		log.info("公共字段自動填充[update]...");
		metaObject.setValue("updateTime", LocalDateTime.now());
		metaObject.setValue("updateUser", BaseContext.getCurrentId());
	}
}
