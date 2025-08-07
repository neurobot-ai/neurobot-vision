package com.nbai.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 训练服务器状态枚举
 *  1 : 可用 
 *  0 : 不可用
 * 
 * @author zhenmeng
 * @date 2021-03-023
 */

@Getter
@AllArgsConstructor
public enum TrainServerStatusEnum {
	//可用
	YES(1, "可用"),
	//不可用
	NO(0, "不可用");

	private Integer code;
	private String name;

}
