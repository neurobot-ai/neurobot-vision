package com.nbai.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 文件下载工具类
 * @author zhenmeng
 * @date 2021-3-30
 *
 */
@Slf4j
public class DownloadFileUtil {

	public static void download(String fileUrl, String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
		File file = new File(fileUrl);
		download(file, fileName, request, response);
	}
	
	public static void download(File file, String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[inputStream.available()]; 
			inputStream.read(buffer);
			inputStream.close();
			response.reset();
			String header = request.getHeader("User-Agent").toUpperCase();
			if (header.contains("MSIE") || header.contains("TRIDENT") || header.contains("EDGE")) {
				fileName = URLEncoder.encode(fileName, "utf-8");
			} else {
				fileName = new String(fileName.getBytes(), "ISO8859-1");
			}
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.addHeader("Content-Length", "" + file.length());

			OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");

			outputStream.write(buffer);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			log.error("文件下载失败:  fileName = {}", fileName, e);
			throw e;
		}
	}

	public static void download(byte[] buffer, String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			response.reset();
			String header = request.getHeader("User-Agent").toUpperCase();
			if (header.contains("MSIE") || header.contains("TRIDENT") || header.contains("EDGE")) {
				fileName = URLEncoder.encode(fileName, "utf-8");
			} else {
				fileName = new String(fileName.getBytes(), "ISO8859-1");
			}
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.addHeader("Content-Length", "" + buffer.length);

			OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");

			outputStream.write(buffer);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			log.error("文件下载失败:  fileName = {}", fileName, e);
			throw e;
		}
	}

	public static void download2(String filePath, String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
		File file = new File(filePath);
		try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			 OutputStream outputStream = new BufferedOutputStream(response.getOutputStream())) {

			// 设置响应头（含编码逻辑）
			String encodedFileName = encodeFileName(fileName, request);
			response.setHeader("Content-Disposition", "attachment;filename=" + encodedFileName);
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setContentType("application/octet-stream");

			// 分块读写（缓冲区大小可调整）
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.flush();
		} catch (Exception e) {
			log.error("文件下载失败: fileName={}", fileName, e);
			throw e;
		}
	}

	// 文件名编码逻辑抽离
	private static String encodeFileName(String fileName, HttpServletRequest request) throws UnsupportedEncodingException {
		String header = request.getHeader("User-Agent").toUpperCase();
		if (header.contains("MSIE") || header.contains("TRIDENT") || header.contains("EDGE")) {
			return URLEncoder.encode(fileName, "UTF-8");
		} else {
			return new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
		}
	}

}