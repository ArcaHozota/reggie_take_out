package com.itheima.reggie.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
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

	@Value("${reggie.path}")
	private static String basePath;

	/**
	 * 文件上傳
	 * 
	 * @param file
	 * @return R.success(上傳的文件名)
	 */
	@PostMapping
	public R<String> upload(MultipartFile file) {
		log.info(file.toString());
		// 獲取文件的原始名稱；
		String fileName = file.getOriginalFilename();
		// 獲取後綴；
		final String suffix = fileName.substring(fileName.lastIndexOf("."));
		// 合成文件名；
		fileName = UUID.randomUUID().toString() + suffix;
		// 判斷根目錄文件夾是否存在；
		final File dir = new File(basePath);
		if (!dir.exists()) {
			// 不存在則創建該文件夾；
			dir.mkdirs();
		}
		try {
			// 將臨時文件轉存到指定位置；
			file.transferTo(new File(basePath + fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return R.success(fileName);
	}

}
