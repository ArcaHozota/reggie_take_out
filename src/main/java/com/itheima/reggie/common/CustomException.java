package com.itheima.reggie.common;

/**
 * 自定義業務異常
 *
 * @author Administrator
 * @date 2022-11-21
 */
public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1692201139310590555L;

	public CustomException(final String message) {
		super(message);
	}
}
