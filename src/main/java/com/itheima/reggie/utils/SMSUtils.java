package com.itheima.reggie.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

import lombok.extern.slf4j.Slf4j;

/**
 * SMS驗證碼工具類
 * 
 * @author Administrator
 */
@Slf4j
public class SMSUtils {

	/**
	 * 發送SMS訊息
	 * 
	 * @param signName     客戶署名
	 * @param templateCode 模板
	 * @param phoneNumbers 手機號
	 * @param param        參數
	 */
	public static void sendMessage(final String signName, final String templateCode, final String phoneNumbers,
			final String param) {
		final DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "", "");
		final IAcsClient client = new DefaultAcsClient(profile);
		final SendSmsRequest request = new SendSmsRequest();
		request.setSysRegionId("cn-hangzhou");
		request.setPhoneNumbers(phoneNumbers);
		request.setSignName(signName);
		request.setTemplateCode(templateCode);
		request.setTemplateParam("{\"code\":\"" + param + "\"}");
		try {
			final SendSmsResponse response = client.getAcsResponse(request);
			log.info("驗證訊息發送成功：", response);
		} catch (final ClientException e) {
			e.printStackTrace();
		}
	}

}
