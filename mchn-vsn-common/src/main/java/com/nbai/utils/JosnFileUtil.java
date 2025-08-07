
package com.nbai.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * json文件工具
 * 
 * @author zhenmeng
 * @date 2021-03-25
 */
public class JosnFileUtil {

	public static boolean writeJsonData(JSONObject jo, String path) {
		File filePath = new File(path);
		return writeJsonData(jo, filePath);
	}
	public static boolean writeJsonData(JSONObject jo, File filePath) {
		try {
			String pretty = JSON.toJSONString(jo, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect);
			Files.write(filePath.toPath(), pretty.getBytes(StandardCharsets.UTF_8));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
 
	public static String readJsonData(File filePath) throws IOException {
		return FileUtils.readFileToString(filePath, "UTF-8");
	}
}
