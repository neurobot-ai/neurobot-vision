package com.nbai.common.enums;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 图片类型枚举
 * 
 * @author zhenmeng
 * @date 2021-03-10
 */

@Getter
@AllArgsConstructor
public enum PictureTypeEnum {
	JPG(".jpg"),
	JPEG(".jpeg"),
	PNG(".png"),
	BMP(".bmp");
	
	private String code;
 
	
 
	/**
	 * 校验code
	 * @param name
	 * @return
	 */
	public static boolean checkCode(String code) {
		if (StringUtils.isBlank(code)) return false;
		for (PictureTypeEnum e : values()) {
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
		for (PictureTypeEnum e : values()) {
			list.add(e.getCode()); 
		}
		return list;
	}
}
