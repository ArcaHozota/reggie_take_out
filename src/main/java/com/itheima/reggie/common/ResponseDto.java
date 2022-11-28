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
public class ResponseDto<T> {

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
	public static <T> ResponseDto<T> success(T object) {
		ResponseDto<T> r = new ResponseDto<T>();
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
	public static <T> ResponseDto<T> error(String msg) {
		ResponseDto<T> r = new ResponseDto<>();
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
	public ResponseDto<T> add(String key, Object value) {
		this.map.put(key, value);
		return this;
	}

	/**
	 * 無參數構造器
	 */
	public ResponseDto(){
		super();
	}

	/**
	 * 全參數構造器
	 *
	 * @param code  當前請求的處理結果
	 * @param msg 請求成功與否的信息
	 * @param data    返回的數據
	 */
	public ResponseDto(String code, String msg, T data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
}
