package com.nbai.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *  训练完成发送邮件开关
 *   1:开
 *   0:关
 * 
 * @author zhenmeng
 * @date 2021-03-04
 */

@Getter
@AllArgsConstructor
public enum EmailSwitchEnum {
	//关
	OFF("closed", 0),
	//开
	ON("open", 1);
	
	
	private String name;
	private Integer code;
	
 
	/**
	 * 校验code
	 * @param name
	 * @return
	 */
	public static boolean checkCode(Integer code) {
		if (code == null) return false;
		for (EmailSwitchEnum e : values()) {
			if(e.getCode().equals(code)) return true;
		}
		return false;
	}
 
}
