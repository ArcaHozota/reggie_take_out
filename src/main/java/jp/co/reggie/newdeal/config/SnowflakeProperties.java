package com.itheima.reggie.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "snowflake")
public class SnowflakeProperties {

	/**
	 * ワークノードID
	 */
	private Long workerId;

	/**
	 * データセンターID
	 */
	private Long dataCenterId;
}