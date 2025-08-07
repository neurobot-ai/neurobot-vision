package com.nbai.common.enums;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 模型导出类型枚举
 * 
 * @author zhenmeng
 * @date 2021-03-22
 */

@Getter
@AllArgsConstructor
public enum ModelExportTypeEnum {
	ONNX("onnx"),
	WTS("wts"),
	TENSORRT("TensorRT");

	private String code;
 
 
	/**
	 * 校验code
	 * @param code
	 * @return
	 */
	public static boolean checkCode(String code) {
		if (StringUtils.isBlank(code)) return false;
		for (ModelExportTypeEnum e : values()) {
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
		for (ModelExportTypeEnum e : values()) {
			list.add(e.getCode()); 
		}
		return list;
	}

}
