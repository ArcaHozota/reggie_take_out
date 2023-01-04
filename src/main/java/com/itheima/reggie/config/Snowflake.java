package com.itheima.reggie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class Snowflake {

	private final SnowflakeProperties snowflakeProperties;

	/**
	 * 初始化SnowflakeIdWorker Bean
	 *
	 * @return SnowflakeIdWorker
	 */
	@Bean
	public Snowflake snowflake() {
		return new Snowflake(this.snowflakeProperties.getWorkerId(), this.snowflakeProperties.getDataCenterId());
	}
}
