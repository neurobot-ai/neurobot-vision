package com.nbai.common.enums;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 模型打包状态枚举
 * 
 * @author zhenmeng
 * @date 2021-03-31
 * 
 * 打包状态:未打包 打包中 打包失败 打包完成
 */

@Getter
@AllArgsConstructor
public enum ModelPackStatusEnum {
	//未打包
	NOT_PACKED("notPacked", "未打包"),
	//打包中
	PACKING("packing", "打包中"),
	//打包失败
	FAILURE("failure", "打包失败"),
	//打包成功
	SUCCESS("success", "打包成功");

	private String code;
	private String name;
 
	/**
	 * 状态值转换
	 * @param status:  	状态接口返回的打包状态值
	 * @return
	 */
	public static String transformPackageStatus(String status) {
		if(StringUtils.isBlank(status)) return Strings.EMPTY;
		if ("成功".equals(status)) return ModelPackStatusEnum.SUCCESS.getCode();
		if ("失败".equals(status)) return ModelPackStatusEnum.FAILURE.getCode();
		if ("导出中".equals(status)) return ModelPackStatusEnum.PACKING.getCode();
		return Strings.EMPTY;
	}
}
