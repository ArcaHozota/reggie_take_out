package com.itheima.reggie.utils;

import java.util.Random;

/**
 * 驗證碼生成工具類
 */
public class ValidateCodeUtils {

	/**
	 * 隨機生成訊息驗證碼
	 * 
	 * @param length 長度為4位或者6位
	 * @return 驗證碼
	 */
	public static Integer generateValidateCode(final int length) {
		Integer code = null;
		if (length == 4) {
			// 生成隨機數字最大9999；
			code = new Random().nextInt(9999);
			if (code < 1000) {
				// 不足4位數字，則加1000；
				code += 1000;
			}
		} else if (length == 6) {
			// 生成隨機數字最大999999；
			code = new Random().nextInt(999999);
			if (code < 100000) {
				// 不足6位數字，則加100000；
				code += 100000;
			}
		} else {
			throw new CustomException("只能生成4位或6位數字的驗證碼。");
		}
		return code;
	}

	/**
	 * 隨機生成指定長度的字符串
	 * 
	 * @param length 長度
	 * @return 字符串
	 */
	public static String generateValidateCode4String(final int length) {
		// 生成隨機數字；
		final Long rdmNumber = new Random().nextLong();
		if (rdmNumber.toString().length() < length) {
			// 若數字的長度小於指定數，則取指定數的乘方並生成指定長度的字符串；
			return Double.toHexString(Math.pow(rdmNumber, length)).substring(0, length);
		}
		// 生成指定長度的字符串；
		return Long.toHexString(rdmNumber).substring(0, length);
	}
}
