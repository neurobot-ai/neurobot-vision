package com.nbai.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

/**
 * 用户类型枚举
 *
 * @author zhenmeng
 * @date 2021-03-03
 */

@Getter
@AllArgsConstructor
public enum GpuTypeEnum {

	GTX1660Ti("GeforceGTX1660Ti", 7.5,"1650"),
	GTX1650Ti("GeForceGTX1650Ti", 7.5,"1650"),
	TITAN_RTX("NVIDIATITANRTX", 7.5,"1650"),
	RTX2080Ti("GeforceRTX2080Ti", 7.5,"1650"),
	RTX2080("GeforceRTX2080", 7.5,"1650"),
	RTX2070("GeforceRTX2070", 7.5,"1650"),
	RTX2060("GeforceRTX2060", 7.5,"1650"),

	TITAN_XP("NVIDIATITANXp", 6.1,"1080"),
	TITAN_X("NVIDIATITANX", 6.1,"1080"),
	GTX1080Ti("GeForceGTX1080Ti", 6.1,"1080"),
	GTX1080("GeForceGTX1080", 6.1,"1080"),
	GTX1070Ti("GeForceGTX1070Ti", 6.1,"1080"),
	GTX1070("GeForceGTX1070", 6.1,"1080"),
	GTX1060("GeForceGTX1060", 6.1,"1080"),
	GTX1050("GeForceGTX1050", 6.1,"1080"),
	GT1030("GeForceGT1030", 6.1,"1080"),

	RTX3090Ti("GeForceRTX3090Ti", 8.6,"3060"),
	RTX3090("GeForceRTX3090", 8.6,"3060"),
	RTX3080Ti("GeForceRTX3080Ti", 8.6,"3060"),
	RTX3080("GeForceRTX3080", 8.6,"3060"),
	RTX3070Ti("GeForceRTX3070Ti", 8.6,"3060"),
	RTX3070("GeForceRTX3070", 8.6,"3060"),
	RTX3060Ti("GeforceRTX3060Ti", 8.6,"3060"),
	RTX3060("GeforceRTX3060", 8.6,"3060"),
	RTX3050Ti("GeForceRTX3050Ti", 8.6,"3060"),
	RTX3050("GeForceRTX3050", 8.6,"3060"),

	CPU("CPU", 0.0,"CPU");


	private final String name;
	private final Double level;
	private final String type;


	/**
	 * 校验name
	 * @param name
	 * @return
	 */
	public static boolean checkName(String name) {
		if (StringUtils.isBlank(name)) return false;
		for (GpuTypeEnum e : values()) {
			if(name.equalsIgnoreCase(e.getName())) return true;
		}
		return false;
	}

	public static String getType(String name) {
		if (StringUtils.isBlank(name)) return Strings.EMPTY;
		for (GpuTypeEnum e : values()) {
			if(name.equalsIgnoreCase(e.getName())) return e.getType();
		}
		return Strings.EMPTY;
	}


}
