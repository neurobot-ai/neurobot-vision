package com.nbai.utils.coco;

import com.nbai.common.entity.dto.LabelDataDTO;

import java.util.Map;

/**
 * @Author lxl
 * @date 2021/6/18 17:04
 */
public class CocoUtils extends BaseMLJson2Coco{

    /**
     * 判断是否是coco
     * @param mljson
     * @return
     */
    public static boolean isCoCo(String mljson){
        return isCoco(mljson);
    }

    /**
     * 处理coco
     * @param imageLocalPath
     * @param mljson
     * @param colorList
     * @param labelDataMap
     * @return
     */
    public static String dealCoCo(String imageLocalPath,
                                  String mljson,
                                  String[] colorList,
                                  Map<String, LabelDataDTO> labelDataMap){
        if (!isCoco(mljson)){
            return mljson;
        }
        mljson = imageFileNameHandler(mljson, imageLocalPath);
        return cocoMarkLablesHandler(mljson, colorList, labelDataMap);
    }
}
