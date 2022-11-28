package com.itheima.reggie.common;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * 通用返回結果類
 *
 * @author Administrator
 */
@Data
public class RestDto<T> {

	/**
	 * 處理成功的信息
	 */
	private static final String SUCCESS = "SUCCESS";

	/**
	 * 處理失敗的信息
	 */
	private static final String ERROR = "ERROR";

	/**
	 * 編碼： 成功：1 失敗：0或其它字符
	 */
	private String code;

	/**
	 * 錯誤信息
	 */
	private String msg;

	/**
	 * 數據
	 */
	private T data;

	/**
	 * 動態數據
	 */
	private Map<String, Object> map = new HashMap<>();

	/**
	 * 處理成功
	 *
	 * @param object 對象
	 * @param <T>    汎型
	 * @return 返回的對象
	 */
	public static <T> RestDto<T> success(T object) {
		RestDto<T> r = new RestDto<T>();
		r.data = object;
		r.code = SUCCESS;
		return r;
	}

	/**
	 * 請求失敗
	 *
	 * @param msg 請求失敗的信息
	 * @param <T> 汎型
	 * @return 失敗的信息
	 */
	public static <T> RestDto<T> error(String msg) {
		RestDto<T> r = new RestDto<>();
		r.msg = msg;
		r.code = ERROR;
		return r;
	}

	/**
	 * 增加數據
	 * 
	 * @param key   鍵
	 * @param value 値
	 * @return this 本數據汎型
	 */
	public RestDto<T> add(String key, Object value) {
		this.map.put(key, value);
		return this;
	}

	/**
	 * 無參數構造器
	 */
	public RestDto(){
		super();
	}
}
