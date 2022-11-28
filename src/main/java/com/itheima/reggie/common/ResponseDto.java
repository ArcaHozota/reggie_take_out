package com.itheima.reggie.common;

import lombok.Data;

/**
 * 統一AJAX請求返回結果類；
 *
 * @author Administrator
 */
@Data
public class ResponseDto<T> {

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
	 * @param <Type> 數據類型
	 * @param data   返回的數據；
	 * @return ResponseDto
	 */
	public static <Type> ResponseDto<Type> succeeded(Type data) {
		return new ResponseDto<>(SUCCESS, null, data);
	}

	/**
	 * 請求成功且不需要返回數據時使用的工具方法；
	 *
	 * @param <Type> 數據類型
	 * @return ResponseDto
	 */
	public static <Type> ResponseDto<Type> success() {
		return new ResponseDto<>(SUCCESS, null, null);
	}

	/**
	 * 請求失敗時使用的工具方法；
	 *
	 * @param <Type>  數據類型
	 * @param message 失敗的處理信息；
	 * @return ResponseDto
	 */
	public static <Type> ResponseDto<Type> failed(String message) {
		return new ResponseDto<Type>(FAILURE, message, null);
	}

	/**
	 * 全參數構造器
	 * 
	 * @param result  當前請求的處理結果
	 * @param message 請求成功與否的信息
	 * @param data    返回的數據
	 */
	public ResponseDto(String result, String message, T data) {
		super();
		this.result = result;
		this.message = message;
		this.data = data;
	}
}
