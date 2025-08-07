package com.nbai.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 权限枚举
 * 
 * @author zhenmeng
 * @date 2021-03-31
 */

@Getter
@AllArgsConstructor
public enum PermissionEnum {
	//有权限
	YES(1, "有权限"),
	//没权限
	NO(0, "没权限");

	private Integer code;
	private String name;
	
	
	/**
	 * 有权限
	 * @param code 权限值
	 * @return
	 */
	public static boolean hasPermission(Integer code) {
		if(code == null) return false;
		if (PermissionEnum.YES.getCode().equals(code)) return true;
		return  false;
	}
	
	/**
	 * 无权限
	 * @param code 权限值
	 * @return
	 */
	public static boolean noPermission(Integer code) {
		return !hasPermission(code);
	}

}
