package com.nbai.utils.coco;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nbai.common.entity.dto.LabelDataDTO;
import com.nbai.common.enums.VisionTypeEnum;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author wangc
 * @date 2021/6/18 11:30
 */
public class LabelMeRectangle2CocoUtil extends BaseMLJson2Coco{

    /**
     * 矩形框
     * @param projectName
     * @param projectType
     * @param imageLocalPath
     * @param mljson
     * @param colorList
     * @param labelDataMap
     * @return
     */
    public static String transformRectangleMark2Coco(String projectName,
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
        JSONArray annotations = getRectangleAnnotations(shapesArray, labelDataMap);

        cocoJsonObject.put("annotations", annotations);
        return cocoJsonObject.toJSONString();
    }

    /**
     * 获取标注数据
     * @param shapesArray
     * @param labelDataMap
     * @return
     */
    private static JSONArray getRectangleAnnotations(JSONArray shapesArray, Map<String, LabelDataDTO> labelDataMap){
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
            JSONArray jsonArray0 = pointsJo.getJSONArray(0);
            JSONArray jsonArray1 = pointsJo.getJSONArray(1);
            Double leftX = jsonArray0.getDouble(0);
            Double leftY = jsonArray0.getDouble(1);

            Double rightX = jsonArray1.getDouble(0);
            Double rightY = jsonArray1.getDouble(1);

            Double width = rightX - leftX;
            Double height = rightY - leftY;

            JSONObject annotationJo = new JSONObject();
            annotationJo.put("id", ++id);
            annotationJo.put("image_id", "");
            annotationJo.put("area", Math.round(width * height));
            annotationJo.put("category_id", laelId);
            annotationJo.put("category_name", labelName);
            annotationJo.put("iscrowd", 0);
            annotationJo.put("bbox", new double[]{leftX, leftY, width, height});
            annotationJo.put("angle", 0);
            annotationJo.put("segmentation", new JSONArray());
            annotationjArray.add(annotationJo);
        }
        return  annotationjArray;
    }
}
