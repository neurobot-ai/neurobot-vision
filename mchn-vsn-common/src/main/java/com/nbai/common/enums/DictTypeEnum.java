package com.nbai.common.enums;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 字典类型枚举
 * 
 * @author zhenmeng
 * @date 2021-03-08
 */

@Getter
@AllArgsConstructor
public enum DictTypeEnum {
	//视觉识别类型
	VISION_TYPE("视觉识别类型", "vision_type"),
	//训练状态
	TRAIN_STATUS("Training status", "train_status"),
	//测试状态
	TEST_STATUS("Test status", "test_status");

	private String name;
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
		for (DictTypeEnum e : values()) {
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
		for (DictTypeEnum e : values()) {
			list.add(e.getCode());
		}
		return list;
	}
}
