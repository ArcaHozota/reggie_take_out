package com.itheima.reggie.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.itheima.reggie.common.R;

import lombok.extern.slf4j.Slf4j;

/**
 * 通用模塊控制器
 * 
 * @author Administrator
 * @date 2022-11-22
 */
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

	/**
	 * 文件上傳
	 * 
	 * @param file
	 * @return
	 */
	@PostMapping
	public R<String> upload(MultipartFile file) {
		log.info(file.toString());
		return null;
	}

}
