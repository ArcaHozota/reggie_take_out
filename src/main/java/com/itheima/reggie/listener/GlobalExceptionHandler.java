package com.itheima.reggie.listener;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.itheima.reggie.common.Constants;
import com.itheima.reggie.utils.CustomException;
import com.itheima.reggie.utils.Reggie;

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
	public Reggie<String> exceptionHandler01(final SQLIntegrityConstraintViolationException exception) {
		log.error(exception.getMessage());
		if (exception.getMessage().contains(Constants.DUPLICATED_KEY)) {
			final String[] split = exception.getMessage().split(" ");
			final String msg = split[2] + "已存在";
			return Reggie.error(msg);
		}
		return Reggie.error(Constants.ERROR);
	}

	/**
	 * 通用業務異常處理方法
	 *
	 * @param exception 通用業務異常
	 * @return 錯誤信息
	 */
	@ExceptionHandler(CustomException.class)
	public Reggie<String> exceptionHandler02(final CustomException exception) {
		log.error(exception.getMessage());
		return Reggie.error(exception.getMessage());
	}
}
