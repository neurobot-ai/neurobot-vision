package com.nbai.common.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import com.nbai.common.entity.dto.KeyValueDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 训练状态枚举
 * 
 * @author zhenmeng
 * @date 2021-03-08
 */

@Slf4j
@Getter
@AllArgsConstructor
public enum TrainStatusEnum {
	//排队中
	QUEUING("queuing", "排队中"),
	//未训练
	NO("no","未训练"),
	//初始化
	INITIALIZATION("initialization", "初始化"),
	//准备数据中
	PREPARING_DATA("preparingData", "准备数据中"),
	//排队等待分配
	QUEUING_FOR_ASSIGNMENT("queuingForAssignment", "排队等待分配"),
	//排队等待传输数据
	QUEUING_FOR_TRANSMITTING_DATA("queuingForTransmittingData", "排队等待传输数据"),
	//传输数据中
	TRANSFERRING_DATA("transferringData", "传输数据中"),
	//传输完毕-待启动
	TO_BE_STARTED("toBeStarted", "传输完毕-待启动"),
	//训练中
	TRAINING("training", "训练中"),
	//测试中
	TESTING("testing", "测试中"),
	//训练完成-待传输结果
	TRAIN_COMPLETED("trainCompleted", "训练完成-待传输结果"),
	//传输结果中
	TRANSFERRING_RESULT("transferringResult", "传输结果中"),
	//传输完毕
	TRANSMISSION_COMPLETED("transmissionCompleted", "传输完毕"),
	//成功
	SUCCESS("success", "成功"),
	//失败
	FAILURE("failure", "失败"),
	//终止
	TERMINATION("termination", "终止");

	private String code;
	private String name;

	public static String getNameBycode(String code) {
		if (StringUtils.isBlank(code)) return Strings.EMPTY;
		for (TrainStatusEnum e : values()) {
			if(code.equals(e.getCode())) return e.getName();
		}
		return Strings.EMPTY;
	}
	
	/**
	 * 校验code
	 * @param code string
	 * @return boolean
	 */
	public static boolean checkCode(String code) {
		if (StringUtils.isBlank(code)) return false;
		for (TrainStatusEnum e : values()) {
			if(code.equals(e.getCode())) return true;
		}
		return false;
	}
	
	/**
	 * 获取code列表
	 * @return
	 */
	public static List<String> getCodeList() {
		List<String> list = new ArrayList<>(values().length);
		for (TrainStatusEnum e : values()) {
			list.add(e.getCode()); 
		}
		return list;
	}

	/**
	 * 
	 * @param status:  	状态接口返回的训练状态值
	 * @param1 isNative: 是否在本地训练
	 * @return
	 */
	public static String transformTrainStatus(String status) {
		if(StringUtils.isBlank(status)) return Strings.EMPTY;
		if ("失败".equals(status)) return TrainStatusEnum.FAILURE.getCode();
		if ("训练中".equals(status)) return TrainStatusEnum.TRAINING.getCode();
		if ("测试中".equals(status)) return TrainStatusEnum.TESTING.getCode();
		if ("成功".equals(status) ) return TrainStatusEnum.TRAIN_COMPLETED.getCode();
		return Strings.EMPTY;
	}
	
	
	public static List<KeyValueDTO> getList(){
		List<KeyValueDTO> list = new ArrayList<>(values().length);
		for (TrainStatusEnum e : values()) {
			list.add(new KeyValueDTO().setStringkey(e.getCode()).setValue(e.getName())); 
		}
		return list;
	}
	
	/**
	 * 状态处理器 
	 * @param status 状态码
	 * @return
	 */
	public static String statusHandler(String status){
		if(StringUtils.isBlank(status)) return status;

		if (TrainStatusEnum.INITIALIZATION.getCode().equals(status)
				|| TrainStatusEnum.PREPARING_DATA.getCode().equals(status)
				|| TrainStatusEnum.QUEUING_FOR_ASSIGNMENT.getCode().equals(status)
				|| TrainStatusEnum.QUEUING_FOR_TRANSMITTING_DATA.getCode().equals(status)) {

			return TrainStatusEnum.QUEUING.getCode();
		}

		if (TrainStatusEnum.TRAINING.getCode().equals(status)
				|| TrainStatusEnum.TESTING.getCode().equals(status)
				|| TrainStatusEnum.TRAIN_COMPLETED.getCode().equals(status)
				|| TrainStatusEnum.TRANSFERRING_RESULT.getCode().equals(status)
				|| TrainStatusEnum.TRANSMISSION_COMPLETED.getCode().equals(status)
				|| TrainStatusEnum.TRANSFERRING_DATA.getCode().equals(status)
				|| TrainStatusEnum.TO_BE_STARTED.getCode().equals(status)) {

			return TrainStatusEnum.TRAINING.getCode();
		}
		return status;
	}

	/**
	 * 获取状态列表，比如：训练中的列表（训练中、测试中、训练完成等待传输结果、传输结果中、传输完成）
	 * @param code
	 * @return
	 */
	public static List<String> getCodesByType(String code){
		if (StringUtils.isBlank(code)){
			return null;
		}
		if (NO.getCode().equals(code)){
			return Collections.emptyList();
		}
		List<String> result = Lists.newArrayList();
		//加入自己
		result.add(code);
		if (TRAINING.getCode().equals(code)){
			result.add(TESTING.getCode());
			result.add(TRAIN_COMPLETED.getCode());
			result.add(TRANSFERRING_RESULT.getCode());
			result.add(TRANSMISSION_COMPLETED.getCode());
			result.add(TRANSFERRING_DATA.getCode());
			result.add(TO_BE_STARTED.getCode());

		}

		if (QUEUING.getCode().equals(code)){
			result.add(INITIALIZATION.getCode());
			result.add(PREPARING_DATA.getCode());
			result.add(QUEUING_FOR_ASSIGNMENT.getCode());
			result.add(QUEUING_FOR_TRANSMITTING_DATA.getCode());
			//result.add(TRANSFERRING_DATA.getCode());
			//result.add(TO_BE_STARTED.getCode());
		}
		return result;
	}
}
