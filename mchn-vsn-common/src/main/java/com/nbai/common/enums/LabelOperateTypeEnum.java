package com.nbai.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 标签操作类型枚举 1: 保存 2: 删除 3: 修改
 * 
 * @author zhenmeng
 * @date 2021-03-30
 */

@Getter
@AllArgsConstructor
public enum LabelOperateTypeEnum {
	//保存
	SAVE(1, "save"),
	//删除
	DELETE(2, "Delete"),
	//修改
	MIDIFY(3, "Revise");

	private Integer code;
	private String name;

	/**
	 * 校验code
	 * 
	 * @param name
	 * @return
	 */
	public static boolean checkCode(Integer code) {
		if (code == null)
			return false;
		for (LabelOperateTypeEnum e : values()) {
			if (code.equals(e.getCode()))
				return true;
		}
		return false;
	}

}
