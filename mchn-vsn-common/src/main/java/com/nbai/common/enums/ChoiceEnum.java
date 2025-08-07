package com.nbai.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 选择枚举
 * 
 * @author zhenmeng
 * @date 2021-05-08
 */

@Getter
@AllArgsConstructor
public enum ChoiceEnum {
	YES(1),
	NO(0);

	private Integer code;

}
