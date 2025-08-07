package com.nbai.utils.coco;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nbai.common.entity.dto.LabelDataDTO;
import com.nbai.common.enums.VisionTypeEnum;

/**
 * 像素分割离线标注(labelimg/labelme json格式)转coco工具类
 * 
 * @author zhenmeng
 * @date 2021-03-17
 *
 */
public class SegmentationMLJson2CocoUtil extends BaseMLJson2Coco {


//	public static void main(String[] args) {
//		String mljson = "{\"version\":\"4.5.7\",\"flags\":{},\"shapes\":[{\"label\":\"cup\",\"points\":[[1049.219512195122,446.56097560975604],[1065.0731707317073,419.73170731707313],[1113.8536585365853,397.780487804878],[1119.951219512195,367.29268292682923],[1135.8048780487804,352.6585365853658],[1163.8536585365853,357.5365853658536],[1177.2682926829268,378.2682926829268],[1169.951219512195,395.3414634146341],[1227.2682926829268,423.390243902439],[1240.6829268292681,440.4634146341463],[1223.6097560975609,456.31707317073165],[1167.5121951219512,461.19512195121945],[1084.5853658536585,456.31707317073165]],\"group_id\":null,\"shape_type\":\"polygon\",\"flags\":{}},{\"label\":\"cup\",\"points\":[[916.2926829268292,667.2926829268292],[966.2926829268292,649],[1072.390243902439,650.2195121951219],[1132.1463414634145,678.2682926829268],[1113.8536585365853,700.2195121951219],[1022.3902439024389,712.4146341463414],[946.780487804878,703.8780487804878],[911.4146341463414,692.9024390243902]],\"group_id\":null,\"shape_type\":\"polygon\",\"flags\":{}},{\"label\":\"bear\",\"points\":[[1284.5853658536585,667.2926829268292],[1341.90243902439,681.9268292682926],[1426.0487804878048,667.2926829268292],[1479.7073170731705,696.560975609756],[1534.5853658536585,727.0487804878048],[1526.0487804878048,778.2682926829268],[1500.4390243902437,850.2195121951219],[1388.2439024390244,877.0487804878048],[1358.9756097560974,874.6097560975609],[1277.2682926829268,820.951219512195],[1227.2682926829268,803.8780487804877],[1267.5121951219512,759.9756097560975],[1271.170731707317,735.5853658536585]],\"group_id\":null,\"shape_type\":\"polygon\",\"flags\":{}},{\"label\":\"bear\",\"points\":[[435.80487804878044,811.1951219512194],[429.7073170731707,868.5121951219511],[467.5121951219512,885.5853658536585],[477.2682926829268,855.0975609756097],[456.5365853658536,817.2926829268292]],\"group_id\":null,\"shape_type\":\"polygon\",\"flags\":{}}],\"imagePath\":\"差不多.jpg\",\"imageData\":\"\",\"imageHeight\":1080,\"imageWidth\":1920}";
//		String[] colorList = new String[] {"red", "blue"};
//		String coco = transformMark2Coco("aa", "OCR", "555.jpg", mljson, colorList);
//		System.err.println(coco);
//	}
	

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
		
		JSONObject cocoJsonObject = new JSONObject();
		JSONObject markJo = (JSONObject) JSONObject.parse(mljson);
		JSONArray shapesArray = markJo.getJSONArray("shapes");
 
	 
		//1. info
		JSONObject info = getInfo(String.format("%s模型标注数据集", projectName));
		cocoJsonObject.put("info", info);
		//2. licenses
		JSONArray licenses = getLicenses(VisionTypeEnum.getDesc(projectType));
		cocoJsonObject.put("licenses", licenses);
		
		//3. images
		JSONArray images = getImages(imageLocalPath);
		cocoJsonObject.put("images", images);
		
		//4. categories
		getCategories(shapesArray, colorList, labelDataMap);
		Collection<LabelDataDTO> categories = labelDataMap.values();
		cocoJsonObject.put("categories", categories);
		
		//5. annotations
		JSONArray annotations = getAnnotations(shapesArray, labelDataMap);
		
		cocoJsonObject.put("annotations", annotations);
		return cocoJsonObject.toJSONString();
	}
	
	/**
	 * 获取标注数据
	 * @param shapesArray
	 * @param labelDataMap
	 * @return
	 */
	private static JSONArray getAnnotations(JSONArray shapesArray, Map<String, LabelDataDTO> labelDataMap){
		final JSONArray annotationjArray = new JSONArray();
		if (shapesArray == null || shapesArray.size()<1) return  annotationjArray;
		
		Iterator<Object> iterator = shapesArray.iterator();
		int id = 0;
		
		while (iterator.hasNext()) {
			JSONObject shapeJo = (JSONObject) iterator.next();
			
			String labelName = shapeJo.getString("label");
			Integer laelId = getLaelId(labelDataMap, labelName);
			
			
			JSONArray segmentationInnerArray = new JSONArray();
			JSONArray pointsJo = shapeJo.getJSONArray("points");
			Double minx = 0d;
			Double miny = 0d;
			Double maxx = 0d;
			Double maxy = 0d;
			for (int i = 0; i < pointsJo.size(); i++) {
				JSONArray pointJo = (JSONArray)pointsJo.get(i);
				Double x = pointJo.getDouble(0);
				Double y = pointJo.getDouble(1);
				//最小X坐标
				if (minx == 0 || minx > x){
					minx = x;
				}
				//最小Y坐标
				if (miny == 0 || miny > y){
					miny = y;
				}
				//最大X坐标
				if (maxx < x){
					maxx = x;
				}
				//最大Y坐标
				if (maxy < y){
					maxy = y;
				}
				segmentationInnerArray.addAll(pointJo);
			}
			double width = maxx-minx;
			double height = maxy - miny;
			JSONArray segmentationArray = new JSONArray();
			segmentationArray.add(segmentationInnerArray);

			JSONObject annotationJo = new JSONObject();
			annotationJo.put("id", ++id);
			annotationJo.put("image_id", "");
			annotationJo.put("area", Math.round(width * height));
			annotationJo.put("category_id", laelId);
			annotationJo.put("category_name", labelName);
			annotationJo.put("iscrowd", 0);
			annotationJo.put("bbox", new double[]{minx, miny, width, height});
			annotationJo.put("angle", 0);
			annotationJo.put("segmentation", segmentationArray);
			annotationjArray.add(annotationJo);
		}
		return  annotationjArray;
	}

}
