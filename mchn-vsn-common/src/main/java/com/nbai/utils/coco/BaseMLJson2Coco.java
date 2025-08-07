package com.nbai.utils.coco;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nbai.common.entity.dto.LabelDataDTO;

import lombok.extern.slf4j.Slf4j;
/**
 * 离线标注转换coco基类
 * 
 * @author zhenmeng
 * @date 2021-03-17
 *
 */

@Slf4j
public class BaseMLJson2Coco {
	
	public static final int IMAGE_ID = 1;
	
	public static final int LICENSE_VERSION = 1;
	
	public static final String CONTRIBUTOR = "Matrix Vision Co.";
	
	
	/**
	 * 获取图片数据
	 * @param imageLocalPath 图片磁盘存储名称
	 * @return
	 */
	protected static JSONArray getImages(String imageLocalPath){
		JSONObject mo = new JSONObject();
		mo.put("id", IMAGE_ID);
		mo.put("license", LICENSE_VERSION);
		mo.put("file_name", imageLocalPath);
		mo.put("coco_url", "");
		mo.put("thumbnail_url", "");
		mo.put("width", "");
		mo.put("height", "");
		mo.put("flickr_url", "");
		mo.put("date_captured", "");
		
		JSONArray imageoJsonArray = new JSONArray();
		imageoJsonArray.add(mo);
		return imageoJsonArray;
	}
	
	
	
	/**
	 * 获取标签数据
	 * @param annotationsJArray
	 * @param colorList
	 * @return
	 */
	protected static void getCategories(JSONArray annotationsJArray, String[] colorList, Map<String, LabelDataDTO> labelDataMap){
		if (annotationsJArray == null || annotationsJArray.isEmpty()) {
			return;
		}
		//聚集标签
		Iterator<Object> iterator = annotationsJArray.iterator();
		final Set<String> labelNameSet = new HashSet<>();
		while (iterator.hasNext()) {
			JSONObject joo = (JSONObject) iterator.next();
			labelNameSet.add(joo.getString("label"));
		}
				
		//处理标签
		Iterator<String> labelNameIterator = labelNameSet.iterator();
		while (labelNameIterator.hasNext()) {
			String name = labelNameIterator.next();
			
			if(labelDataMap.containsKey(name)) {
				continue;
			}
			int id = labelDataMap.size()+1;
			LabelDataDTO data = new LabelDataDTO();
			data.setId(id);
			data.setName(name);
			data.setColor(getColor(id, colorList));
			data.setSupercategory(name);
			labelDataMap.put(name, data);
		} 
	}
	
	/**
	 * 获取License
	 * @param projectTypeName 项目类型
	 * @return
	 */
	protected static JSONArray getLicenses(String projectTypeName){
		JSONArray jArray = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("id", 1);
		jo.put("name", projectTypeName);
		jo.put("url", "");
		jArray.add(jo);
		return jArray;
	}
	
	/**
	 * 获取基本信息
	 * @param projectName 项目名称
	 * @return
	 */
	protected static JSONObject getInfo(String projectName){
		JSONObject io = new JSONObject();
		io.put("url", "http://nb-ai.com");
		io.put("year", LocalDate.now().getYear());
		io.put("version", "1.0");
		io.put("contributor", CONTRIBUTOR);
		io.put("description", projectName);
		io.put("date_created",  LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss")));
		return io;
	}
	
	/**
	 * 获取标签id
	 * @param labelName  标签名
	 * @return
	 */
	protected static Integer getLaelId(Map<String, LabelDataDTO> labelDataMap, String labelName){
		LabelDataDTO labelDataDTO = labelDataMap.get(labelName);
		if(labelDataDTO != null) {
			return labelDataDTO.getId();
		}
		return null;
	}

	
	/**
	 * 是coco格式标注文件数据
	 * @param mljson
	 * @return
	 */
	protected static boolean isCoco(String mljson) {
		try {
			JSONObject jo = JSONObject.parseObject(mljson);
			JSONObject infojo = jo.getObject("info", JSONObject.class);
			String contributor = infojo.getString("contributor");
			return CONTRIBUTOR.equals(contributor) ? true : false;
		} catch (Exception e) {
			log.info("离线标注文件不是coco数据{}", mljson);
		}
		return false;
	}
	
	/**
	 * 标注数据内标签处理器
	 * 
	 * @param mljson		标注数据
	 * @param colorList		色表
	 * @param labelDataMap	项目标签数据
	 * @return
	 */
	protected static String cocoMarkLablesHandler(String mljson, String[] colorList, Map<String, LabelDataDTO> labelDataMap){
		JSONObject markJo = JSONObject.parseObject(mljson);
		JSONArray annotationsJArray = markJo.getJSONArray("annotations");
		
		if (annotationsJArray == null || annotationsJArray.isEmpty()) {
			return null;
		}
		
		//聚集标签
		Iterator<Object> iterator = annotationsJArray.iterator();
		final Set<String> labelNameSet = new HashSet<>();
		while (iterator.hasNext()) {
			JSONObject joo = (JSONObject) iterator.next();
			labelNameSet.add(joo.getString("category_name"));
		}
				
		//处理标签
		Iterator<String> labelNameIterator = labelNameSet.iterator();
		while (labelNameIterator.hasNext()) {
			String name = labelNameIterator.next();
			
			if(labelDataMap.containsKey(name)) {
				continue;
			}
			int id = labelDataMap.size()+1;
			LabelDataDTO data = new LabelDataDTO();
			data.setId(id);
			data.setName(name);
			data.setColor(getColor(id, colorList));
			data.setSupercategory(name);
			labelDataMap.put(name, data);
		} 
		
		//重新设置标注内的标签数据
		Collection<LabelDataDTO> categories = labelDataMap.values();
		markJo.put("categories", categories);
		
		//更新标注内的标签数据
		JSONArray newAnnotationsJArray = new JSONArray();
		iterator = annotationsJArray.iterator();
		while (iterator.hasNext()) {
			JSONObject joo = (JSONObject) iterator.next();
			LabelDataDTO labelDataDTO = labelDataMap.get(joo.getString("category_name"));
			joo.put("category_id", labelDataDTO.getId());
			newAnnotationsJArray.add(joo);
		}
		markJo.put("annotations", newAnnotationsJArray);
		return markJo.toJSONString();
	}
	
	
	/**
	 * 获取色表坐标
	 * @param size
	 * @param colorListSize
	 * @return
	 */
	protected static int getColorListIndex(int size, int colorListSize) {
		if (size <= 0) {
			return 0;
		}
		if (size % colorListSize == 0) {
			return colorListSize - 1;
		}
		return (size % colorListSize) - 1;
	}
	
	/**
	 * 获取色表中颜色
	 * @param size		
	 * @param colorList
	 * @return
	 */
	public static String getColor(int size, String[] colorList) {
		int index = getColorListIndex(size, colorList.length);
		return colorList[index];
	}
	
	/**
	 * 处理标注内file_name问题
	 * @param mljson
	 * @param imageLocalPath
	 * @return
	 */
	protected static String imageFileNameHandler(String mljson, String imageLocalPath){
		JSONObject markJo = JSONObject.parseObject(mljson);
		JSONArray imagesJarray = markJo.getJSONArray("images");
		if (imagesJarray == null || imagesJarray.isEmpty()) {
			return mljson;
		}
		JSONObject imageJo = (JSONObject)imagesJarray.get(0);
		String fileName = imageJo.getString("file_name");
		if (StringUtils.isBlank(fileName) || !fileName.equals(imageLocalPath) ) {
			imageJo.put("file_name", imageLocalPath);
			imagesJarray.set(0, imageJo);
			markJo.put("images", imagesJarray);
		}
		return markJo.toJSONString();
	}
	

}
