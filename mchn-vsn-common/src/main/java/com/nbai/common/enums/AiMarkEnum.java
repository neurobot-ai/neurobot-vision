package com.nbai.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 智能标注枚举
 * 
 * @author zhenmeng
 * @date 2021-05-10
 */

@Getter
@AllArgsConstructor
public enum AiMarkEnum {
	//正常训练
	TRAIN(0, "normal training"),
	//智能标注
	AI_MARK(1, " 智能标注");

	private Integer code;

	private String name;

	/**
	 * 校验code
	 * 
	 * @param code
	 * @return
	 */
	public static boolean checkCode(Integer code) {
		if (code == null) {
			return false;
		}
		for (AiMarkEnum e : values()) {
			if (e.getCode().equals(code))
				return true;
		}
		return false;
	}

	/**
	 * 是否为智能标注
	 * @param code
	 * @return
	 */
	public static boolean isAiMark(Integer code) {
		 return AiMarkEnum.AI_MARK.getCode().equals(code);
	}
	
	/**
	 * 非智能标注
	 * @param code
	 * @return
	 */
	public static boolean isNotAiMark(Integer code) {
		 return !isAiMark(code);
	}

}
