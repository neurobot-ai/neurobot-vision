package com.nbai.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import lombok.extern.slf4j.Slf4j;

/**
 * zip解压缩工具类
 * 
 * @author zhenmeng
 * @date 2021-01-28
 */
@Slf4j
public class UnZipFileUtil {

	/**
	 * 解压文件到指定目录 解压后的文件名，和之前一致
	 * 
	 * @param zipFile 待解压的zip文件
	 * @param descDir 指定目录
	 */
	public static boolean unZipFiles(File zipFile, File descDir) {
		try (ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"))) {
			for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				String zipEntryName = entry.getName();
				InputStream in = zip.getInputStream(entry);
				String outPath = (descDir + "/" + zipEntryName).replaceAll("\\*", "/");
				// 判断路径是否存在,不存在则创建文件路径
				File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
				if (!file.exists()) {
					file.mkdirs();
				}
				// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
				if (new File(outPath).isDirectory()) {
					continue;
				}
				// 输出文件路径信息
				FileOutputStream out = new FileOutputStream(outPath);
				byte[] buf1 = new byte[1024];
				int len;
				while ((len = in.read(buf1)) > 0) {
					out.write(buf1, 0, len);
				}
				in.close();
				out.close();
			}
			log.info("****************** 文件解压完毕 ********************");
			return true;
		} catch (IOException e) {
			log.error("****************** 文件解压失败 ********************", e);
			return false;
		}
	}

	// 测试
//	public static void main(String[] args) {
//		File zipFile = new File("D:\\gitlocal\\machine-vision\\mchn-vsn-local-run\\models\\OCR\\测试模型ocr1\\temp.zip");
//		File descDir = new File("D:\\gitlocal\\machine-vision\\mchn-vsn-local-run\\models\\OCR\\测试模型ocr1");
//		unZipFiles(zipFile, descDir);
//	}

}