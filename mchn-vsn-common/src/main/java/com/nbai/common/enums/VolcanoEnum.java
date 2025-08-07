package com.nbai.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 注册来源枚举项TOTAL_MODEL_TRAINING
 */
@Getter
@AllArgsConstructor
public enum VolcanoEnum {
    //公司总数
    TOTAL_NUMBER_OF_COMPANIES("total_number_of_companies", "公司总数"),
    //新增公司数
    NEW_NUMBER_OF_COMPANIES("new_number_of_companies", "新增公司数"),
    //用户总数
    TOTAL_NUMBER_OF_USERS("total_number_of_users", "用户总数"),
    //新增用户总数
    NEW_NUMBER_OF_USERS("new_number_of_users", "新增用户总数"),
    //模型总数
    TOTAL_NUMBER_OF_MODELS("total_number_of_models", "模型总数"),
    //新增模型总数
    NEW_NUMBER_OF_MODELS("new_number_of_models", "新增模型总数"),
    //图片总数
    TOTAL_NUMBER_OF_PICTURES("total_number_of_pictures", "图片总数"),
    //新增图片总数
    NEW_NUMBER_OF_PICTURES("new_number_of_pictures", "新增图片总数"),
    //标签总数
    TOTAL_NUMBER_OF_LABELS("total_number_of_labels", "标签总数"),
    //新增标签总数
    NEW_NUMBER_OF_LABELS("new_number_of_labels", "新增标签总数"),
    //标注总数
    TOTAL_NUMBER_OF_MARKS("total_number_of_marks", "标注总数"),
    //新增标注总数
    NEW_NUMBER_OF_MARKS("new_number_of_marks", "新增标注总数"),
    //训练总数
    TOTAL_MODEL_TRAINING("total_model_training", "训练总数"),
    //新增训练总数
    NEW_MODEL_TRAINING("new_model_training", "新增训练总数"),
    //新增训练排队总数
    NEW_MODEL_TRAINING_QUEUE("new_model_training_queue", "新增训练排队总数"),
    //训练成功总数
    SUCCESSFUL_TRAINING("successful_training", "训练成功总数"),
    //训练失败总数
    TRAINING_FAILED("training_failed", "训练失败总数"),
    //测试总数
    TOTAL_NUMBER_OF_MODEL_TESTS("total_number_of_model_tests", "测试总数"),
    //新增测试总数
    NEW_MODEL_TEST("new_model_test", "新增测试总数"),
    //新增测试排队总数
    NEW_MODEL_TEST_QUEUE("new_model_test_queue", "新增测试排队总数"),
    //测试成功总数
    TESTING_SUCCESSFULLY("testing_successfully", "测试成功总数"),
    //测试失败总数
    TEST_FAILED("test_failed", "测试失败总数"),

    //模型训练类型
    TRAINING_MODEL_TYPE("training_model_type", "模型训练类型"),
    //模型测试类型
    TEST_MODEL_TYPE("test_model_type", "模型测试类型"),
    //OCR数量
    OCR_RECOGNITION("ocr_recognition", "OCR数量"),
    //目标定位数量
    TARGET_DETECTION("target_detection", "目标定位数量"),
    //像素分割数量
    PIXEL_SEGMENTATION("pixel_segmentation", "像素分割数量"),
    //尺寸测量数量
    SIZE_MEASUREMENT("size_measurement", "尺寸测量数量"),

    //图片上传数量范围
    PICTURES_UPLOAD_RANGE("pictures_upload_range", "图片上传数量范围"),
    PICTURES_10_OR_LESS("10_or_less_pictures", "图片上传数量范围"),
    PICTURES_BETWEEN_10_AND_50("10_50_sheets_pictures", "图片上传数量范围"),
    PICTURES_BETWEEN_50_AND_100("50_100_sheets_pictures", "图片上传数量范围"),
    PICTURES_BETWEEN_100_AND_200("100_200_sheets_pictures", "图片上传数量范围"),
    PICTURES_MORE_THAN_200("200_or_more_pictures", "图片上传数量范围"),

    //训练时长
    TRAINING_TIME_STATISTICS("training_time_statistics", "训练时长"),
    //训练排队时长
    TRAIN_QUEUE_TIME_STATISTICS("train_queue_time_statistics", "训练排队时长"),
    //测试时长
    TEST_DURATION_STATISTICS("test_duration_statistics", "测试时长"),
    //测试排队时长
    TEST_QUEUE_TIME_STATISTICS("test_queue_time_statistics", "测试排队时长"),
    //时长范围
    LESS_THAN_30_MINUTES("less_than_30_minutes", "时长范围"),
    MINUTES_30_AND_60("30_60_minutes", "时长范围"),
    MINUTES_60_AND_90("60_90_minutes", "时长范围"),
    MINUTES_90_AND_120("90_120_minutes", "时长范围"),
    MINUTES_MORE_THAN_120("120_minutes_or_more", "时长范围"),
    //sdk下载
    DOWNLOAD_SDK("download_sdk", "sdk下载"),
    //模型下载
    DOWNLOAD_MODEL("download_model", "模型下载"),
    //模型下载类型
    DOWNLOAD_MODEL_TYPE("download_model_type", "模型下载类型"),

    ;


    private String code;
    private String des;
}
