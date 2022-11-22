package com.itheima.reggie.common;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局異常處理類
 *
 * @author Administrator
 * @date 2022-11-12
 */
@Slf4j
@ControllerAdvice(annotations = { RestController.class, Controller.class })
@ResponseBody
public class GlobalExceptionHandler {

	/**
	 * SQL整合性異常處理方法
	 *
	 * @param exception SQL整合性異常
	 * @return 錯誤信息
	 */
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public R<String> exceptionHandler01(@NonNull SQLIntegrityConstraintViolationException exception) {
		log.error(exception.getMessage());
		if (exception.getMessage().contains(Constants.DUPLICATED_KEY)) {
			final String[] split = exception.getMessage().split(" ");
			final String msg = split[2] + "已存在";
			return R.error(msg);
		}
		return R.error(Constants.ERROR);
	}

	/**
	 * 通用業務異常處理方法
	 *
	 * @param exception 通用業務異常
	 * @return 錯誤信息
	 */
	@ExceptionHandler(CustomException.class)
	public R<String> exceptionHandler02(@NonNull CustomException exception) {
		log.error(exception.getMessage());
		return R.error(exception.getMessage());
	}
}
