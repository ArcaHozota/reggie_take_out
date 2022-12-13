package com.itheima.reggie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

/**
 * MyBatisPlus分頁組件
 *
 * @author Administrator
 */
@Configuration
public class MyBatisPlusConfig {

	/**
	 * 分頁插件屬性設置；
	 *
	 * @return
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		final MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
		final PaginationInnerInterceptor innerInterceptor = new PaginationInnerInterceptor();
		innerInterceptor.setDbType(DbType.POSTGRE_SQL);
		innerInterceptor.setOverflow(true);
		mybatisPlusInterceptor.addInnerInterceptor(innerInterceptor);
		return mybatisPlusInterceptor;
	}
}
