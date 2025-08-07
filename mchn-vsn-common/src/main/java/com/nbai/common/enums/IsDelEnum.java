package com.nbai.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否删除枚举
 * 
 * @author zhenmeng
 * @date 2021-03-08
 */

@Getter
@AllArgsConstructor
public enum IsDelEnum {
	//删除
	YES(1, "Delete"),
	//未删除
	NO(0, "not deleted");

	private Integer code;
	private String name;

}
