package com.nbai.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统运行环境枚举
 * 
 * @author zhenmeng
 * @date 2021-04-23
 */

@Getter
@AllArgsConstructor
public enum EnvEnum {
	DEV("dev"),
	TEST("test"),
	PROD("prod"),
	SANDBOX("sandbox");

	private String code;

}
