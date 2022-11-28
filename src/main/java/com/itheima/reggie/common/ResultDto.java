package com.itheima.reggie.common;

import lombok.Data;

/**
 * 統一AJAX請求返回結果類；
 *
 * @author Administrator
 */
@Data
public class ResultDto<T> {

	/**
	 * 處理成功的信息
	 */
	private static final String SUCCESS = "SUCCESS";

	/**
	 * 處理失敗的信息
	 */
	private static final String FAILURE = "FAILED";

	/**
	 * 封裝當前請求的處理結果；
	 */
	private String result;

	/**
	 * 請求成功與否的信息；
	 */
	private String message;

	/**
	 * 返回的數據；
	 */
	private T data;

	/**
	 * 請求成功時使用的工具方法；
	 *
	 * @param <Type>
	 * @param data   返回的數據；
	 * @return
	 */
	public static <Type> ResultDto<Type> successWithData(Type data) {
		return new ResultDto<>(SUCCESS, null, data);
	}

	/**
	 * 請求成功且不需要返回數據時使用的工具方法；
	 *
	 * @param <Type>
	 * @return ResultDto
	 */
	public static <Type> ResultDto<Type> success() {
		return new ResultDto<>(SUCCESS, null, null);
	}

	/**
	 * 請求失敗時使用的工具方法；
	 *
	 * @param <Type>
	 * @param message 失敗的處理信息；
	 * @return
	 */
	public static <Type> ResultDto<Type> failed(String message) {
		return new ResultDto<Type>(FAILURE, message, null);
	}

	/**
	 * 全參數構造器
	 * 
	 * @param result
	 * @param message
	 * @param data
	 */
	public ResultDto(String result, String message, T data) {
		super();
		this.result = result;
		this.message = message;
		this.data = data;
	}
}
