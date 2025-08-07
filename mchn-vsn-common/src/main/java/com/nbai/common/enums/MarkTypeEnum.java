package com.nbai.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 标注类型枚举
 * 
 * @author zhenmeng
 * @date 2021-03-11
 */

@Getter
@AllArgsConstructor
public enum MarkTypeEnum {
	//手动标注
	MANUAL(0, "手动标注"),
	//测试结果转标注
	TESTRESULT2MARK(1, "测试结果转标注");

	private Integer code;
	private String name;
 
 
	/**
	 * 校验code
	 * @param name
	 * @return
	 */
	public static boolean checkCode(Integer code) {
		if (code == null) return false;
		for (MarkTypeEnum e : values()) {
			if(code.equals(e.getCode())) return true;
		}
		return false;
	}
 

}
