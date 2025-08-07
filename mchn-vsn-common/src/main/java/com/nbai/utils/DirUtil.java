package com.nbai.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

/**
 * 文件目录工具
 * 
 * @author zhenmeng
 * @date 2021-03-22
 */
public class DirUtil {
	
	/**
	 * 获取图片两级目录
	 * @param sign 文件标识(md5+size)
	 * @return
	 */
	public static String getImageLevel2Dirs(String sign) {
		if (StringUtils.isBlank(sign)) {
			return Strings.EMPTY;
		}
		return String.format("%s/%s", sign.substring(0, 2), sign.substring(2, 4));
	}

}
