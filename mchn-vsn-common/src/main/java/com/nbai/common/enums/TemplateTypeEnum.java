package com.nbai.common.enums;

import com.google.common.collect.Lists;
import com.nbai.common.entity.dto.KeyValueDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;

/**
 * 调参模板型枚举
 * 预防客户通过参数名获取实际参数，展示mapping
 *
 * @author 孟文博
 * @date 2021-07-02
 */

@Getter
@AllArgsConstructor
public enum TemplateTypeEnum {

    //训练
    GUGING_OVERLAP("gaugingOverlap", "x_param1", "检测框重叠值", "0.5"),
    TRAIN_ITERATION("trainIteration", "x_param2", "训练迭代次数", "5000"),
    MINIMUM_PIXEL("minimumPixel", "x_param3", "最小像素训练参数", "32/64/128/256/512"),
    ENABLE_INCR("enable_incr", "x_param4", "是否打开增量训练", "false"),
    INCR_TRAIN_ITERATION("incrTrainIteration", "x_param5", "增量训练迭代次数", "2000"),
    MODEL_EXPORT_TYPE("modelExportType", "x_param6", "模型导出分辨率", ModelExportTypeEnum.TENSORRT.getCode()),
    BASE_LR("baseLR", "x_param7", "基础学习率", "0.02"),
    IMS_PER_BATCH("imsPerBatch", "x_param8", "加速训练进度", "2"),
    WARMUP_ITERS_RATIO("warmupItersRatio", "x_param9", "预热迭代数", "0.1"),
    STEPS_RATIO_1("stepsRatio1", "x_param10", "学习率改变步长1", "0.5"),
    STEPS_RATIO_2("stepsRatio2", "x_param11", "学习率改变步长2", "0.8"),
    CUSTOMIZED_MINIMUM_PIXEL("customizedMinimumPixel", "x_param12", "自定义最小像素训练参数", ""),
    RESOLUTION_TYPE("resolutionType", "x_param100", "图片分辨率类型", ImageResolutionTypeEnum.DEFAULT.getCode()),
    RESOLUTION_WIDTH("resolutionWidth", "x_param101", "图片分辨率宽", ""),
    RESOLUTION_HEIGHT("resolutionHeight", "x_param102", "图片分辨率高", ""),



    //yolo高级参数
    HSV_H("hsv_h", "x_param13", "色调", "0.015"),
    HSV_S("hsv_s", "x_param14", "饱和度", "0.7"),
    HSV_V("hsv_v", "x_param15", "v", "0.4"),
    DEGRESS("degrees", "x_param16", "图像旋转角度", "0.0"),
    TRANSLATE("translate", "x_param17", "图像风格迁移", "0.2"),
    SCALE("scale", "x_param18", "图像缩放尺度", "0.9"),
    SHEAR("shear", "x_param19", "图像剪切", "0.6"),
    PERSPECTIVE("perspective", "x_param20", "图像透明度", "0.0"),
    FLIPUD("flipud", "x_param21", "上下翻转", "0.0"),
    FLIPLR("fliplr", "x_param22", "左右翻转", "0.0"),
    MOSAIC("mosaic", "x_param23", "马赛克", "0.0"),
    MIXUP("mixup", "x_param24", "图像混合", "0.0"),
    COPY_PASTE("copy_paste", "x_param25", "图像复制粘贴", "0.0"),
    PASTE_IN("paste_in", "x_param26", "图像复制粘贴在全黑的背景上", "0.0"),
    LOSS_OTA("loss_ota", "x_param27", "loss_ota", "0.0"),
    LRF("lrf", "x_param28", "最后一个epoch的学习率系数", "0.1"),


    //测试
    GAUGING_THRESHOLD("gaugingThreshold", "c_param1", "检测阈值", "0.7"),
    MISS_GAUGING("missGauging", "c_param2", "目标过多时漏检", "1000"),
    MAX_TARGET_NUM("maxTargetNum", "c_param3", "单张图最大检测数量", "100"),
    OCR_ORDER_THRESHOLD("ocrOrderThreshold", "c_param4", "OCR排序阈值", "0.5"),
    GAUGING_THRESHOLD_LIST("gaugingThresholdList", "c_param5", "检测阈值（分标签）", "0.7"),

    //增强
    ENABLE("enable", "z_param1", "是否打开数据增强", "false"),
    BRIGHTNESS_MIN("brightnessMin", "z_param2", "亮度最小值", "0.9"),
    BRIGHTNESS_MAX("brightnessMax", "z_param3", "亮度最大值", "1.1"),
    CONTRAST_MIN("contrastMin", "z_param4", "对比度", "0.9"),
    CONTRAST_Max("contrastMax", "z_param5", "对比度", "1.1"),
    SATURATION_MIN("saturationMin", "z_param6", "饱和度", "0.9"),
    SATURATION_MAX("saturationMax", "z_param7", "饱和度", "1.1");

    private String code;
    private String mapping;
    private String desc;
    private String defaultValue;


    /**
     * 校验code
     *
     * @param code
     * @return
     */
    public static boolean checkCode(String code) {
        if (StringUtils.isBlank(code)) return false;
        for (TemplateTypeEnum e : values()) {
            if (code.equals(e.getCode())) return true;
        }
        return false;
    }

    /**
     * 获取描述
     *
     * @param code
     * @return
     */
    public static String getDesc(String code) {
        if (StringUtils.isBlank(code)) return Strings.EMPTY;
        for (TemplateTypeEnum e : values()) {
            if (code.equals(e.getCode())) return e.getDesc();
        }
        return Strings.EMPTY;
    }

    /**
     * 获取默认值
     *
     * @param code
     * @return
     */
    public static String getDefaulValue(String code) {
        if (StringUtils.isBlank(code)) return Strings.EMPTY;
        for (TemplateTypeEnum e : values()) {
            if (code.equals(e.getCode())) return e.getDefaultValue();
        }
        return Strings.EMPTY;
    }

    /**
     * 获取参数映射名
     *
     * @param code
     * @return
     */
    public static String getMapping(String code) {
        if (StringUtils.isBlank(code)) return Strings.EMPTY;
        for (TemplateTypeEnum e : values()) {
            if (code.equals(e.getCode())) return e.getMapping();
        }
        return Strings.EMPTY;
    }

    /**
     * 获取参数名
     *
     * @param mapping
     * @return
     */
    public static String getCode(String mapping) {
        if (StringUtils.isBlank(mapping)) return Strings.EMPTY;
        for (TemplateTypeEnum e : values()) {
            if (mapping.equals(e.getMapping())) return e.getCode();
        }
        return Strings.EMPTY;
    }

    /**
     * 获取code列表
     *
     * @return
     */
    public static List<String> getCodeList() {
        List<String> list = new ArrayList<>(values().length);
        for (TemplateTypeEnum e : values()) {
            list.add(e.getCode());
        }
        return list;
    }

    public static List<KeyValueDTO> getList() {
        List<KeyValueDTO> list = new ArrayList<>(values().length);
        for (TemplateTypeEnum e : values()) {
            list.add(new KeyValueDTO().setStringkey(e.getCode()).setValue(e.getDesc()));
        }
        return list;
    }

    public static List<TemplateTypeEnum> getTrainParamEnums() {
        List<TemplateTypeEnum> list = Lists.newArrayList(
                GUGING_OVERLAP, TRAIN_ITERATION, MINIMUM_PIXEL, ENABLE_INCR, INCR_TRAIN_ITERATION, MODEL_EXPORT_TYPE,
                BASE_LR, IMS_PER_BATCH, WARMUP_ITERS_RATIO, STEPS_RATIO_1, STEPS_RATIO_2, CUSTOMIZED_MINIMUM_PIXEL,
                HSV_H, HSV_S, HSV_V, DEGRESS, TRANSLATE, SCALE, SHEAR, PERSPECTIVE, FLIPUD, FLIPLR, MOSAIC, MIXUP,
                COPY_PASTE, PASTE_IN, LOSS_OTA, LRF, RESOLUTION_TYPE, RESOLUTION_WIDTH, RESOLUTION_HEIGHT
        );
        return list;
    }

    public static List<TemplateTypeEnum> getTestParamEnums() {
        List<TemplateTypeEnum> list = Lists.newArrayList();
        list.add(GAUGING_THRESHOLD);
        list.add(MISS_GAUGING);
        list.add(MAX_TARGET_NUM);
        list.add(OCR_ORDER_THRESHOLD);
        list.add(GAUGING_THRESHOLD_LIST);
        return list;
    }

    public static List<TemplateTypeEnum> getEnhanceParamEnums() {
        return Lists.newArrayList(
                ENABLE, BRIGHTNESS_MIN, BRIGHTNESS_MAX, CONTRAST_MIN, CONTRAST_Max, SATURATION_MIN,
                SATURATION_MAX
        );
    }
}
