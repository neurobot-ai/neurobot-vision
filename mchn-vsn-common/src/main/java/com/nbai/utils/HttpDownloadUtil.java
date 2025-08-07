package com.nbai.utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import lombok.extern.slf4j.Slf4j;
 
/**
 * HttpClient实现文件下载
 * 
 * @author zhenmeng
 * @date 2021-3-26
 */
@Slf4j
public class HttpDownloadUtil {

	/**
	 * 文件下载
	 * @param url
	 * @param filepath
	 * @return
	 */
	public static boolean download(String url, File pathFile) {
		log.info("========================= http download start ==========================");
		try {
			HttpClient client = new DefaultHttpClient();
			log.info("   url: " + url);
			log.info("   local file path: " + pathFile.getPath());
			HttpGet httpget = new HttpGet(url);
			log.info("========================= http download doing ==========================");
			HttpResponse response = client.execute(httpget);
			
			if (response.getStatusLine().getStatusCode() != 200){
				throw new RuntimeException(String.format("response status code = %s;  %s", response.getStatusLine().getStatusCode(), url ));
			}
 
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			
			if(! pathFile.getParentFile().exists()) {
				pathFile.getParentFile().mkdirs();
			}
			FileOutputStream fileout = new FileOutputStream(pathFile);
			/**
			 * 根据实际运行效果 设置缓冲区大小
			 */
			int cache = 10 * 1024;
			byte[] buffer=new byte[cache];
			int ch = 0;
			while ((ch = is.read(buffer)) != -1) {
				fileout.write(buffer,0,ch);
			}
			is.close();
			fileout.flush();
			fileout.close();
			log.info("========================= http download finish ==========================");
			return true;
		} catch (Exception e) {
			log.error("========================= http download error ==========================", e);
		}
		return false;
	}
 
 
	public static void main(String[] args) {
		String url="https://www.baidu.com/img/flexible/logo/pc/result.png";
		String filepath = "D:/b/a/result.png";
		HttpDownloadUtil.download(url, new File(filepath));
	}
}