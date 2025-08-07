package com.nbai.common.enums;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 标注格式枚举
 * 
 * @author zhenmeng
 * @date 2021-03-16
 */

@Getter
@AllArgsConstructor
public enum MarkFormatEnum {
	JSON("json"),
	XML("xml");

	private String code;

	/**
	 * 校验code
	 * 
	 * @param name
	 * @return
	 */
	public static boolean checkCode(String code) {
		if (StringUtils.isBlank(code))
			return false;
		for (MarkFormatEnum e : values()) {
			if (code.equals(e.getCode()))
				return true;
		}
		return false;
	}

	/**
	 * 获取code列表
	 * 
	 * @return
	 */
	public static List<String> getCodeList() {
		List<String> list = new ArrayList<>(values().length);
		for (MarkFormatEnum e : values()) {
			list.add(e.getCode());
		}
		return list;
	}
}
