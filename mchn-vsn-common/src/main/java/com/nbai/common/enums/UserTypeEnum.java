package com.nbai.common.enums;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户类型枚举
 * 
 * @author zhenmeng
 * @date 2021-03-03
 */

@Getter
@AllArgsConstructor
public enum UserTypeEnum {
	//内部账号
	INNER("内部账号", "inner"),
	//外部账号
	OUTER("外部账号", "outer");
	
	private String name;
	private String code;
	
 
	/**
	 * 校验code
	 * @param name
	 * @return
	 */
	public static boolean checkCode(String code) {
		if (StringUtils.isBlank(code)) return false;
		for (UserTypeEnum e : values()) {
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
		for (UserTypeEnum e : values()) {
			list.add(e.getCode()); 
		}
		return list;
	}
}
