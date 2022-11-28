package com.itheima.reggie.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.itheima.reggie.common.RestDto;

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
	private String basePath;

	/**
	 * 文件上傳
	 *
	 * @param file 文件
	 * @return R.success(上傳的文件名)
	 */
	@PostMapping("/upload")
	public RestDto<String> upload(@NonNull MultipartFile file) {
		log.info("Input:{}" + file.toString());
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
		return RestDto.success(fileName);
	}

	/**
	 * 文件下載
	 *
	 * @param name     文件名
	 * @param response 頁面響應
	 */
	@GetMapping("/download")
	public void download(String name, HttpServletResponse response) {
		// 輸入流，通過輸入流讀取文件内容；
		try {
			final FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
			// 輸出流，通過輸出流將文件寫回瀏覽器並展示圖片；
			final ServletOutputStream outputStream = response.getOutputStream();
			response.setContentType("image/jpg");
			final byte[] bytes = new byte[1024];
			int length = 0;
			while ((length = fileInputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, length);
				outputStream.flush();
			}
			// 關閉輸入輸出流；
			outputStream.close();
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
