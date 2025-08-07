package com.nbai.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.nbai.common.entity.ResultCode;

import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 文件尺寸工具类
 * 
 * @author zhenmeng
 * @date 2021-4-21
 *
 */

public class FileSizeUtil {

	/** 单张图片大小限制 单位:MB */
	public static final long IMAGE_MAX_SIZE_MB = 100;

	/** 单个压缩包大小限制 单位:MB */
	public static final long ARCHIVE_MAX_SIZE_MB = 500;

	private static I18nUtil i18nUtil=SpringContextUtil.getBean(I18nUtil.class);


	/**
	 * 校验文件大小是否超过限制
	 * 
	 * @param size    文件大小
	 * @param maxSize 最大限制
	 */
	public static void checkSize(long size, long maxSize) {
		maxSize = maxSize * 1024 * 1024;
//		Assert.isTrue(size <= maxSize, ResultCode.LARGE_FILE_ERROR.getMessage());
		Assert.isTrue(size <= maxSize, i18nUtil.getMessage("LARGE_FILE_ERROR.message"));
	}

	/**
	 * 文件大小转换 B -> MB
	 * 
	 * @param size
	 * @return
	 */
	public static BigDecimal convertSizeB2M(long size) {
		BigDecimal mbSize = new BigDecimal(size).divide(new BigDecimal(1024 * 1024), 3, RoundingMode.HALF_UP);
		return mbSize;
	}

}
