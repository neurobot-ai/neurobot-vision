package com.nbai.common.enums;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 内置角色枚举
 * 
 * @author zhenmeng
 * @date 2021-02-26
 */

@Getter
@AllArgsConstructor
public enum InnerRoleEnum {
	//管理员
	ADMIN("Admin", "admin"),
	//新注册用户
	NEW_REGISTER("New registered user", "new_register"),
	//已激活用户
	ACTIVATED("Activated user", "activated"),
	//销售
	SALES("Sale", "sales"),
	//销售总监
	SALES_MANAGER("Director of Sales", "sales_manager"),
	//技术支持
	TECH_SUPPORT("Technical Support", "tech_support"),
	//推荐管理员
	RECOMMEND_ADMIN("Recommended Admin", "recommend_admin");
	
	
	private String name;
	private String code;
	
 
	/**
	 * 校验code
	 * @param name
	 * @return
	 */
	public static boolean checkCode(String code) {
		if (StringUtils.isBlank(code)) return false;
		for (InnerRoleEnum e : values()) {
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
		for (InnerRoleEnum e : values()) {
			list.add(e.getCode()); 
		}
		return list;
	}
}
