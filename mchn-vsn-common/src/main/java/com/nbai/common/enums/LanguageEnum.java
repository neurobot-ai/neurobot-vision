package com.nbai.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 开始时间结束时间枚举
 * 
 * @author lushujie
 * @date 2021-08-04
 */

@Getter
@AllArgsConstructor
public enum LanguageEnum {
	//中文
	EN("en"),
	//英文
	ZH("zh");

	private String code;

}
