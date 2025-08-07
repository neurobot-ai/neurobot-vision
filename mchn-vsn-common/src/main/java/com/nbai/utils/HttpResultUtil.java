package com.nbai.utils;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * http结果处理工具类
 * 
 * @author zhenmeng
 * @date 2021-01-22
 */
public class HttpResultUtil {
	
	/**
	 * 校验响应码并获取数据
	 * @param jsonStr
	 * @return
	 */
	public static String checkAndGetData(String jsonStr) {
		JSONObject jo = JSONObject.parseObject(jsonStr);
		if (StringUtils.isBlank(jo.getString("code")) || jo.getInteger("code") != 200 ) {
			throw new RuntimeException("interface error");
		}
		return jo.getString("data");
	}

}
