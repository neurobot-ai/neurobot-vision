package com.nbai.common.enums;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.Lists;
import com.nbai.common.entity.dto.KeyValueDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;

/**
 * 视觉识别类型枚举
 * 
 * @author zhenmeng
 * @date 2021-01-27
 */

@Getter
@AllArgsConstructor
public enum VisionTypeEnum {
	
	OCR("OCR", "OCR识别",101),
	SEGMENTATION("SEGMENTATION","像素分割",201),
	DETECTION("DETECTION","目标定位",301),
	CLASSIFICATION("CLASSIFICATION","图像分类",401),

	FAST_OCR("FAST_OCR", "快速OCR",102),
	FAST_SEGMENTATION("FAST_SEGMENTATION","快速像素分割",202),
	FAST_DETECTION("FAST_DETECTION","快速定位",302),
	FAST_CLASSIFICATION("FAST_CLASSIFICATION","快速图像分类",402),

	NEW_OCR("OCR_NEW", "OCR-新普通模型",103),
	NEW_SEGMENTATION("SEGMENTATION_NEW","像素分割-新普通模型",203),
	NEW_DETECTION("DETECTION_NEW","目标定位-新普通模型",303),

	HUGE_OCR("OCR_HUGE", "OCR-大模型",104),
	HUGE_SEGMENTATION("SEGMENTATION_HUGE","像素分割-大模型",204),
	HUGE_DETECTION("DETECTION_HUGE","目标定位-大模型",304),

	MMDET_OCR("OCR_MMDET", "OCR-标准版",105),
	MMDET_SEGMENTATION("SEGMENTATION_MMDET","像素分割-标准版",205),
	MMDET_DETECTION("DETECTION_MMDET","目标定位-标准版",305),

	MMDET101_OCR("OCR_MMDET101", "OCR-高精版",106),
	MMDET101_SEGMENTATION("SEGMENTATION_MMDET101","像素分割-高精版",206),
	MMDET101_DETECTION("DETECTION_MMDET101","目标定位-高精版",306),


	MEASURE("MEASURE","尺寸测量",401),
	SCENE("SCENE","场景分类",501),
	ROTATE_FRAME("ROTATE_FRAME","旋转框",601);

	private String code;
	private String desc;
	private Integer typeNum;//类型码 1开头OCR 2开头像素分割 3开头目标定位 且默认高精度2为高速度 4图像分类
	
 
	/**
	 * 校验code
	 * @param
	 * @return
	 */
	public static boolean checkCode(String code) {
		if (StringUtils.isBlank(code)) return false;
		for (VisionTypeEnum e : values()) {
			if(code.equals(e.getCode())) return true;
		}
		return false;
	}

	/**
	 * 校验code
	 * @param
	 * @return
	 */
	public static boolean checkTypeNum(Integer typeNum) {
		if (ObjectUtil.isNull(typeNum)){
			return false;
		}
		for (VisionTypeEnum e : values()) {
			if(typeNum.equals(e.getTypeNum())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 校验code
	 * @param
	 * @return
	 */
	public static Integer getTypeNum(String code) {
		if (StringUtils.isBlank(code)) return 0;
		for (VisionTypeEnum e : values()) {
			if(code.equals(e.getCode())) return e.getTypeNum();
		}
		return 0;
	}

	/**
	 * 校验code
	 * @param
	 * @return
	 */
	public static String getDesc(String code) {
		if (StringUtils.isBlank(code)) return Strings.EMPTY;
		for (VisionTypeEnum e : values()) {
			if(code.equals(e.getCode())) return e.getDesc();
		}
		return Strings.EMPTY;
	}

	/**
	 * 校验code
	 * @param
	 * @return
	 */
	public static String getCode(Integer typeNum) {
		if (typeNum == null) return Strings.EMPTY;
		for (VisionTypeEnum e : values()) {
			if(typeNum.equals(e.getTypeNum())) return e.getCode();
		}
		return Strings.EMPTY;
	}
	
	/**
	 * 获取code列表
	 * @return
	 */
	public static List<String> getCodeList() {
		List<String> list = new ArrayList<>(values().length);
		for (VisionTypeEnum e : values()) {
			list.add(e.getCode()); 
		}
		return list;
	}



	public static List<Integer> getNormalModelTypeNum() {
		return Lists.newArrayList(OCR.getTypeNum(), SEGMENTATION.getTypeNum(), DETECTION.getTypeNum());
	}
	public static List<String> getNormalModelCode() {
		return Lists.newArrayList(OCR.getCode(), SEGMENTATION.getCode(), DETECTION.getCode());
	}
	public static Boolean isNormalModel(Integer typeNum) {
		return getNormalModelTypeNum().contains(typeNum);
	}

	public static List<Integer> getFastModelTypeNum() {
		return Lists.newArrayList(FAST_OCR.getTypeNum(), FAST_SEGMENTATION.getTypeNum(), FAST_DETECTION.getTypeNum(),FAST_CLASSIFICATION.getTypeNum());
	}
	public static List<String> getFastModelCode() {
		return Lists.newArrayList(FAST_OCR.getCode(), FAST_SEGMENTATION.getCode(), FAST_DETECTION.getCode());
	}
	public static Boolean isFastModel(Integer typeNum) {
		return getFastModelTypeNum().contains(typeNum);
	}

	public static List<Integer> getMmdetModelTypeNum() {
		return Lists.newArrayList(MMDET_OCR.getTypeNum(), MMDET_SEGMENTATION.getTypeNum(), MMDET_DETECTION.getTypeNum());
	}
	public static List<String> getMmdetModelCode() {
		return Lists.newArrayList(MMDET_OCR.getCode(), MMDET_SEGMENTATION.getCode(), MMDET_DETECTION.getCode());
	}
	public static Boolean isMmdetModel(Integer typeNum) {
		return getMmdetModelTypeNum().contains(typeNum);
	}

	public static List<Integer> getMmdet101ModelTypeNum() {
		return Lists.newArrayList(MMDET101_OCR.getTypeNum(), MMDET101_SEGMENTATION.getTypeNum(), MMDET101_DETECTION.getTypeNum());
	}
	public static List<String> getMmdet101ModelCode() {
		return Lists.newArrayList(MMDET101_OCR.getCode(), MMDET101_SEGMENTATION.getCode(), MMDET101_DETECTION.getCode());
	}
	public static Boolean isMmdet101Model(Integer typeNum) {
		return getMmdet101ModelTypeNum().contains(typeNum);
	}



	public static List<Integer> getNewModelTypeNum() {
		return Lists.newArrayList(NEW_OCR.getTypeNum(), NEW_SEGMENTATION.getTypeNum(), NEW_DETECTION.getTypeNum());
	}
	public static List<String> getNewModelCode() {
		return Lists.newArrayList(NEW_OCR.getCode(), NEW_SEGMENTATION.getCode(), NEW_DETECTION.getCode());
	}
	public static Boolean isNewModel(Integer typeNum) {
		return getNewModelTypeNum().contains(typeNum);
	}

	public static List<Integer> getHugeModelTypeNum() {
		return Lists.newArrayList(HUGE_OCR.getTypeNum(), HUGE_SEGMENTATION.getTypeNum(), HUGE_DETECTION.getTypeNum());
	}
	public static List<String> getHugeModelCode() {
		return Lists.newArrayList(HUGE_OCR.getCode(), HUGE_SEGMENTATION.getCode(), HUGE_DETECTION.getCode());
	}
	public static Boolean isHugeModel(Integer typeNum) {
		return getHugeModelTypeNum().contains(typeNum);
	}

	public static Boolean isOCRModel(Integer typeNum) {
		return Lists.newArrayList(OCR.getTypeNum(), HUGE_OCR.getTypeNum(), NEW_OCR.getTypeNum(), FAST_OCR.getTypeNum(),
				MMDET101_OCR.getTypeNum(), MMDET_OCR.getTypeNum()).contains(typeNum);
	}
	public static Boolean isDetectionModel(Integer typeNum) {
		return Lists.newArrayList(DETECTION.getTypeNum(), HUGE_DETECTION.getTypeNum(), NEW_DETECTION.getTypeNum(),
				FAST_DETECTION.getTypeNum(), MMDET101_DETECTION.getTypeNum(), MMDET_DETECTION.getTypeNum()).contains(typeNum);
	}

	public static Boolean isSupportRotate(Integer typeNum) {
		return Lists.newArrayList(DETECTION.getTypeNum(), HUGE_DETECTION.getTypeNum(), NEW_DETECTION.getTypeNum(),
				FAST_DETECTION.getTypeNum(), MMDET101_DETECTION.getTypeNum(), MMDET_DETECTION.getTypeNum(),
				OCR.getTypeNum(), HUGE_OCR.getTypeNum(), NEW_OCR.getTypeNum(), FAST_OCR.getTypeNum(), MMDET101_OCR.getTypeNum(),
				MMDET_OCR.getTypeNum()).contains(typeNum);
	}


	public static List<KeyValueDTO> getList(){
		List<KeyValueDTO> list = new ArrayList<>(values().length);
		for (VisionTypeEnum e : values()) {
			list.add(new KeyValueDTO().setStringkey(e.getCode()).setValue(e.getDesc())); 
		}
		return list;
	}

	
}
