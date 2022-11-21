package com.itheima.reggie.common;

/**
 * 自定義業務異常
 *
 * @author Administrator
 * @date 2022-11-21
 */
public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomException(String message) {
		super(message);
	}
}
