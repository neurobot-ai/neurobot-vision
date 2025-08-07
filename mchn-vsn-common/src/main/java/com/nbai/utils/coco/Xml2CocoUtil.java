package com.nbai.utils.coco;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.nbai.common.entity.dto.LabelDataDTO;
import com.nbai.common.enums.VisionTypeEnum;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @Author wangc
 * @date 2021/6/18 10:33
 */
public class Xml2CocoUtil extends BaseMLJson2Coco{
    /**
     * xml文件转coco
     * @param projectName
     * @param projectType
     * @param imageLocalPath
     * @param xmlFile
     * @param colorList
     * @param labelDataMap
     * @return
     */
    public static String transForCoco(String projectName,
                                      String projectType,
                                      String imageLocalPath,
                                      File xmlFile,
                                      String[] colorList,
                                      Map<String, LabelDataDTO> labelDataMap){
        JSONObject cocoJsonObject = new JSONObject();

        //1. info
        JSONObject info = getInfo(String.format("%s模型标注数据集", projectName));
        cocoJsonObject.put("info", info);
        //2. licenses
        JSONArray licenses = getLicenses(VisionTypeEnum.getDesc(projectType));
        cocoJsonObject.put("licenses", licenses);

        //3. images
        JSONArray images = getImages(imageLocalPath);
        cocoJsonObject.put("images", images);


        FileInputStream fileInputStream = null;
        SAXBuilder builder = new SAXBuilder();
        List objectElementList = Lists.newArrayList();
        try {
            fileInputStream = new FileInputStream(xmlFile);
            Document build = builder.build(fileInputStream);
            Element rootElement = build.getRootElement();
            objectElementList = rootElement.getChildren("object");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //4. categories
        getCategories(objectElementList, colorList, labelDataMap);
        Collection<LabelDataDTO> categories = labelDataMap.values();
        cocoJsonObject.put("categories", categories);

        //5. annotations
        JSONArray annotations = getAnnotations(objectElementList, labelDataMap);
        cocoJsonObject.put("annotations", annotations);

        return cocoJsonObject.toJSONString();
    }


    /**
     * 处理标签
     * @param objectElementList
     * @param colorList
     * @param labelDataMap
     */
    private static void  getCategories(List objectElementList,String[] colorList, Map<String, LabelDataDTO> labelDataMap){
        if (objectElementList.isEmpty()){
            return;
        }
        //标签名称集合
        Set<String> labelNameSet = Sets.newLinkedHashSet();
        for (Object obj:objectElementList){
            Element element = (Element) obj;
            labelNameSet.add(element.getChild("name").getText());
        }
        Iterator<String> iterator = labelNameSet.iterator();
        while (iterator.hasNext()){
            String labelName = iterator.next();
            if (labelDataMap.containsKey(labelName)){
                continue;
            }
            int id = labelDataMap.size()+1;
            LabelDataDTO data = new LabelDataDTO();
            data.setId(id);
            data.setName(labelName);
            data.setColor(getColor(id, colorList));
            data.setSupercategory(labelName);
            labelDataMap.put(labelName, data);
        }
    }

    /**
     * 获取标注信息
     * @param objectElementList
     * @param labelDataMap
     * @return
     */
    public static JSONArray getAnnotations(List objectElementList, Map<String, LabelDataDTO> labelDataMap){
        JSONArray annotaionJsonArray = new JSONArray();
        if (objectElementList.isEmpty()){
            return annotaionJsonArray;
        }
        int id = 0;
        for (Object obj : objectElementList) {
            Element element = (Element) obj;
            //标签名称
            String labelName = element.getChildText("name");
            Integer labelId = getLaelId(labelDataMap, labelName);
            //标注信息
            Element bndbox = element.getChild("bndbox");

            int xmin = Integer.valueOf(bndbox.getChildText("xmin"));
            int ymin = Integer.valueOf(bndbox.getChildText("ymin"));
            int xmax = Integer.valueOf(bndbox.getChildText("xmax"));
            int ymax = Integer.valueOf(bndbox.getChildText("ymax"));

            int width = xmax - xmin;
            int height = ymax - ymin;

            JSONObject annotationJo = new JSONObject();
            annotationJo.put("id", ++id);
            annotationJo.put("image_id", "");
            annotationJo.put("area", width * height);
            annotationJo.put("category_id", labelId);
            annotationJo.put("category_name", labelName);
            annotationJo.put("iscrowd", 0);
            annotationJo.put("bbox", new double[]{xmin, ymin, width, height});
            annotationJo.put("angle", 0);
            annotationJo.put("segmentation", new JSONArray());
            annotaionJsonArray.add(annotationJo);
        }
        return annotaionJsonArray;
    }

}
