package com.nbai.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 模型训练分析枚举
 * 
 * @author lushujie
 * @date 2021-08-03
 */

@Getter
@AllArgsConstructor
public enum TrainAnalysisEnum {

	TRAININGAMOUNT("trainingAmount"),
	QUEUINGAMOUNT("queuingAmount"),
	DATE("date");

	private String code;

}
