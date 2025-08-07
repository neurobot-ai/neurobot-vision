package com.nbai.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 图片是否标注枚举
 * 
 * @author zhenmeng
 * @date 2021-03-09
 */

@Getter
@AllArgsConstructor
public enum ImageMarkEnum {
	//已标注
	YES(1, "Labeled"),
	//未标注
	NO(0, "Unlabeled"),
	//已测试
	TESTYES(2,"Tested"),
	//未处理
	NOHANDLE(3,"Untreated"),
	//已测试有结果
	TESTYES_RESULT(4,"Tested with result"),
	//已测试无结果
	TESTYES_NO_RESULT(5,"Tested no result");

	private Integer code;
	private String name;
	
	/**
	 * 校验code
	 * @param name
	 * @return
	 */
	public static boolean checkCode(Integer code) {
		if (code == null) return false;
		for (ImageMarkEnum e : values()) {
			if(code.equals(e.getCode())) return true;
		}
		return false;
	}

}
