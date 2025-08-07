package com.nbai.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 模型测试分析枚举
 *
 * @author lushujie
 * @date 2021-08-03
 */

@Getter
@AllArgsConstructor
public enum TestAnalysisEnum {

	TESTINGAMOUNT("testingAmount"),
	QUEUINGAMOUNT("queuingAmount"),
	DATE("date");

	private String code;

}
