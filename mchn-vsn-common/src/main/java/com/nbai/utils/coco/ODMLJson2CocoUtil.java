package com.nbai.utils.coco;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nbai.common.entity.dto.LabelDataDTO;
import com.nbai.common.enums.VisionTypeEnum;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 *  OCR和目标定位离线标注(labelimg/labelme json格式)转coco工具类
 *  
 * @author zhenmeng
 * @date 2021-03-17
 *
 */
public class ODMLJson2CocoUtil extends BaseMLJson2Coco {
	
	public static void main(String[] args) {
//		String mljson = "[{\"image\":\"bpm.jpg\",\"annotations\":[{\"label\":\"e\",\"coordinates\":{\"x\":190.31137724550896,\"y\":172.29041916167662,\"width\":14.0,\"height\":21.0}},{\"label\":\"e\",\"coordinates\":{\"x\":231.81137724550896,\"y\":171.79041916167662,\"width\":15.0,\"height\":18.0}},{\"label\":\"u\",\"coordinates\":{\"x\":147.31137724550896,\"y\":171.79041916167662,\"width\":16.0,\"height\":20.0}}]}]";
//		String[] colorList = new String[] {"red", "blue"};
//		String coco = transformMark2Coco("xxx", "OCR", "555.jpg", mljson, colorList);
//		System.err.println(coco);
		String jsonStr = "[{\"image\": \"B89EBE5133B8279C830FD699837068E1.jpg\", \"annotations\": [{\"label\": \"123\", \"coordinates\": {\"x\": 162.35256410256412, \"y\": 108.05128205128207, \"width\": 123.0, \"height\": 81.00000000000001}}, {\"label\": \"323\", \"coordinates\": {\"x\": 284.8525641025641, \"y\": 162.05128205128207, \"width\": 50.0, \"height\": 45.0}}, {\"label\": \"321\", \"coordinates\": {\"x\": 94.85256410256412, \"y\": 206.05128205128207, \"width\": 56.0, \"height\": 59.0}}]}]";
//		JSONObject jsonObject = JSONObject.parseObject(jsonStr);
		Object parse = JSONObject.parse(jsonStr);
		System.out.println(parse.getClass().getName());

	}
	

	/**
	 * labelimg标注转coco
	 * 
	 * @param projectName   项目名
	 * @param projectType 	识别类型 
	 * @param imageLocalPath 图片本地存储名
	 * @param mljson		离线标注数据
	 * @param colorList		色表
	 * @return
	 */
	public static String transformMark2Coco(String projectName, 
											String projectType,
											String imageLocalPath,
											String mljson,
											String[] colorList,
											Map<String, LabelDataDTO> labelDataMap) {
		if (isCoco(mljson)) {
			mljson = imageFileNameHandler(mljson, imageLocalPath);
			return cocoMarkLablesHandler(mljson, colorList, labelDataMap);
		}


		//一下是labelImg的json格式

		JSONArray markArray = JSONArray.parseArray(mljson);
		JSONObject markjo = (JSONObject) markArray.get(0);
		JSONArray annotationsJArray = JSONArray.parseArray(markjo.getString("annotations"));
	 
		JSONObject cocoJsonObject = new JSONObject();
//		//1. info
		JSONObject info = getInfo(String.format("%s模型标注数据集", projectName));
		cocoJsonObject.put("info", info);
//		//2. licenses
		JSONArray licenses = getLicenses(VisionTypeEnum.getDesc(projectType));
		cocoJsonObject.put("licenses", licenses);
//		
//		//3. images
		JSONArray images = getImages(imageLocalPath);
		cocoJsonObject.put("images", images);
		
		//4. categories
		getCategories(annotationsJArray, colorList, labelDataMap);
		Collection<LabelDataDTO> categories = labelDataMap.values();
		cocoJsonObject.put("categories", categories);
		
		//5. annotations
		JSONArray annotations = getAnnotations(annotationsJArray, labelDataMap);;
		
		cocoJsonObject.put("annotations", annotations);
		return cocoJsonObject.toJSONString();
	}
	
	/**
	 * 获取ocr标注数据
	 * @param annotationsJArray
	 * @param labelDataMap
	 * @return
	 */
	private static JSONArray getAnnotations(JSONArray annotationsJArray, Map<String, LabelDataDTO> labelDataMap){
		final JSONArray annotationjArray = new JSONArray();
		if (annotationsJArray == null || annotationsJArray.size()<1) return  annotationjArray;
		
		Iterator<Object> iterator = annotationsJArray.iterator();
		int id = 0;
		while (iterator.hasNext()) {
			JSONObject joo = (JSONObject) iterator.next();
			
			String labelName = joo.getString("label");
			Integer laelId = getLaelId(labelDataMap, labelName);
			
			JSONObject coordinatesJo = joo.getJSONObject("coordinates");
			double leftx = coordinatesJo.getDoubleValue("x");
			double lefty = coordinatesJo.getDoubleValue("y");
			double width = coordinatesJo.getDoubleValue("width");
			double height = coordinatesJo.getDoubleValue("height");

			JSONObject annotationJo = new JSONObject();
			annotationJo.put("id", ++id);
			annotationJo.put("image_id", IMAGE_ID);
			annotationJo.put("area", Math.round(width * height));
			annotationJo.put("category_id", laelId);
			annotationJo.put("category_name", labelName);
			annotationJo.put("iscrowd", 0);
			//减去长度、宽度的一半，计算左上角的坐标
			annotationJo.put("bbox", new double[] {leftx - (width/2), lefty - (height/2), width, height});
			annotationJo.put("angle", 0);
			annotationJo.put("segmentation", new JSONArray());
			annotationjArray.add(annotationJo);
		}
		return  annotationjArray;
	}

	public static String xmlToCoco(String projectName,String projectType,String imageLocalPath ,File markFile, String[] colorList){
		SAXBuilder saxBuilder = new SAXBuilder();
		JSONObject cocoJsonObject = new JSONObject();

		//构建info
		JSONObject info = getInfo(String.format("%s模型标注数据集", projectName));
		cocoJsonObject.put("info", info);
		//2. licenses
		JSONArray licenses = getLicenses(VisionTypeEnum.getDesc(projectType));
		cocoJsonObject.put("licenses", licenses);
//
//		//3. images
		JSONArray images = getImages(imageLocalPath);
		cocoJsonObject.put("images", images);

		try {
			//读取xml文件
			Document document = saxBuilder.build(new FileInputStream(markFile));
			Element rootElement = document.getRootElement();

//			rootElement.getChild()
		}catch (Exception e){

		}
		return cocoJsonObject.toJSONString();
	}
}
