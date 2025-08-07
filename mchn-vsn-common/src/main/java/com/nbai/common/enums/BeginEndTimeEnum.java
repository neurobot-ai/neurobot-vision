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
public enum BeginEndTimeEnum {

	BEGINTIME("beginTime"),
	ENDTIME("endTime");

	private String code;

}
