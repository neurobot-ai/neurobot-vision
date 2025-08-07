package com.nbai.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 测试来源枚举
 * 
 * @author mengwenbo
 * @date 2022-02-10
 */

@Getter
@AllArgsConstructor
public enum TestSourceEnum {
	CLOUD(0),
	VISION_SDK(1);

	private Integer code;

}
