package com.nbai.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否删除枚举
 * 
 * @author zhenmeng
 * @date 2021-03-23
 */

@Getter
@AllArgsConstructor
public enum RunTestEnum {
	//训练
	TRAIN(0, "training"),
	//训练并测试
	TRAIN_TEST(1, "Train and test");

	private Integer code;
	private String name;

	/**
	 * 校验code
	 * 
	 * @param name
	 * @return
	 */
	public static boolean checkCode(Integer code) {
		if (code == null) return false;
		for (RunTestEnum e : values()) {
			if (code.equals(e.getCode())) return true;
		}
		return false;
	}
}
