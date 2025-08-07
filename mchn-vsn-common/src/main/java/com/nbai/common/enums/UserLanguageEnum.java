package com.nbai.common.enums;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户语言枚举
 * 
 * @author zhenmeng
 * @date 2021-03-05
 */

@Getter
@AllArgsConstructor
public enum UserLanguageEnum {
	//中文
	zh("中文", "zh"),
	//英语
	en("英语", "en");
	
	private String name;
	private String code;
	
 
	/**
	 * 校验code
	 * @param name
	 * @return
	 */
	public static boolean checkCode(String code) {
		if (StringUtils.isBlank(code)) return false;
		for (UserLanguageEnum e : values()) {
			if(code.equals(e.getCode())) return true;
		}
		return false;
	}
	
	/**
	 * 获取code列表
	 * @return
	 */
	public static List<String> getCodeList() {
		List<String> list = new ArrayList<>(values().length);
		for (UserLanguageEnum e : values()) {
			list.add(e.getCode()); 
		}
		return list;
	}
}
