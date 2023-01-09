package jp.co.reggie.newdeal.listener;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import jp.co.reggie.newdeal.common.BaseContext;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyMetaObjectHandler.class);

	/**
	 * 插入操作，自動填充
	 *
	 * @param metaObject 任意填充類
	 */
	@Override
	public void insertFill(final MetaObject metaObject) {
		LOGGER.info("公共字段自動填充[insert]...");
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
	public void updateFill(final MetaObject metaObject) {
		LOGGER.info("公共字段自動填充[update]...");
		metaObject.setValue("updateTime", LocalDateTime.now());
		metaObject.setValue("updateUser", BaseContext.getCurrentId());
	}
}