package com.itheima.reggie.listener;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import javax.persistence.PrePersist;

import org.springframework.stereotype.Component;

import com.itheima.reggie.common.BaseContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DatabaseListener {

	@PrePersist
	public void prePersist(final Object object) {
		log.info("公共字段自動填充[insert]...");
		final Class<? extends Object> class1 = object.getClass();
		try {
			this.addUserId(object, class1, "createUser");
			this.addUsingTime(object, class1, "createTime");
		} catch (final ReflectiveOperationException e) {
			log.error("類反射操作異常：", e);
		}
	}

	/**
	 * 獲取用戸ID
	 *
	 * @param object    實體類
	 * @param aClass    類反射
	 * @param fieldName 填充字段名
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	private void addUserId(final Object object, final Class<? extends Object> aClass, final String fieldName)
			throws NoSuchFieldException, IllegalAccessException {
		final Field userId = aClass.getDeclaredField(fieldName);
		userId.setAccessible(true);
		final Object idValue = userId.get(object);
		if (idValue == null) {
			final Long currentId = BaseContext.getCurrentId();
			userId.set(object, currentId);
		}
	}

	/**
	 * 獲取操作時間
	 *
	 * @param object    實體類
	 * @param aClass    類反射
	 * @param fieldName 填充字段名
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	private void addUsingTime(final Object object, final Class<? extends Object> aClass, final String fieldName)
			throws NoSuchFieldException, IllegalAccessException {
		final Field time = aClass.getDeclaredField(fieldName);
		time.setAccessible(true);
		final Object timeVal = time.get(object);
		if (timeVal == null) {
			time.set(object, LocalDateTime.now());
		}
	}
}
