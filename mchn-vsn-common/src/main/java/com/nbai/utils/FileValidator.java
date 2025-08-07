package com.nbai.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

/**
 * 文件校验器
 * @author zhenmeng
 * @date 2021-03-16
 *
 */
@Slf4j
public class FileValidator {

	private static final char[] hexCode = "0123456789ABCDEF".toCharArray();

	/**
	 * 计算文件 MD5
	 * 
	 * @param file
	 * @return 返回文件的md5字符串，如果计算过程中任务的状态变为取消或暂停，返回null， 如果有其他异常，返回空字符串
	 */
	public static String calcMD5(InputStream stream) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] buf = new byte[8192];
			int len;
			while ((len = stream.read(buf)) > 0) {
				digest.update(buf, 0, len);
			}
			return toHexString(digest.digest());
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}

	private static String toHexString(byte[] data) {
		StringBuilder r = new StringBuilder(data.length * 2);
		for (byte b : data) {
			r.append(hexCode[(b >> 4) & 0xF]);
			r.append(hexCode[(b & 0xF)]);
		}
		return r.toString();
	}

	/**
	 * 获取文件sign
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getFileSign(MultipartFile file) throws IOException {
		String md5 = calcMD5(file.getInputStream());
		long size = file.getSize();
		return md5 + size;
	}
	
	/**
	 * 获取文件sign
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getFileSign(File file) throws IOException {
		try(InputStream in = new FileInputStream(file)) {
			String md5 = calcMD5(in);
			long size = file.length();
			return md5 + size;
		} catch (Exception e) {
			log.error("计算sign失败: {}",file, e);
		}
		return Strings.EMPTY;
	}

	/**
	 * 检验文件
	 * 
	 * @param md5Size
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static boolean checkFile(String md5Size, MultipartFile file) throws IOException {
		if (StringUtils.isBlank(md5Size))
			return false;
		if (md5Size.equals(getFileSign(file))) {
			return true;
		}
		return false;
	}

}