package com.nbai.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 响应状态码
 * 
 * @author zhenmeng
 * @date 2021-01-27
 */

@Getter
@AllArgsConstructor
public enum ResultCode {

	/* 成功 */
	SUCCESS(200, "Success"),
	/* 默认失败 */
	COMMON_FAIL(500, "Error"),

	NO_PERMITION(406, "无权限访问"),

	/* 业务失败 */
	NO_ERROR(0, "无错误"),

	/* 业务失败 */
	BUSINESS_FAIL(400, "业务异常"),
	TEST_FAIL(401, "不允许测试"),

	/* 参数错误：1000～1999 */
	PARAM_NOT_VALID(1001, "参数校验失败"),
	COMPRESSED_PACKAGE_FILE_FORMAT_ERROR(1002,"压缩包文件格式错误"),
	ENUM_CODE_CAN_NOT_CONTAINS_CHINESE(1003,"枚举编码不可包含中文"),
	ENUM_CODE_EXIST(1004,"枚举编码已存在"),
	PROJECT_TYPE_SHOULD_BE_OCR(1005,"项目类型需为OCR类型"),

	/* 用户相关错误：2000～2999 */
	USER_MAIL_EXISTS(2001, "邮箱已注册"), 
	USER_MOBIL_EXISTS(2002, "手机号已注册"),
	USER_REGISTER_FAIL(2003, "注册失败"),
	USER_USERNAME_PASSORD_ERROR(2004, "用户名或密码错误"),
	USER_MAIL_NOT_EXISTS(2005, "邮箱不存在"),

	USER_PASSORD_SAME_ERROR(2100, "输入的密码相同"),
	USER_PASSORD_NOT_SAME_ERROR(2101, "确认密码错误"),
	USER_ORIGINAL_PASSORD_ERROR(2102, "原密码错误"),
	USER_CLAIM_CUSTOMERS_ERROR(2103, "客户已被认领"),
	

	/* 角色相关错误：3000～3999 */
	ROLE_NAME_EXISTS(3001, "角色名称已存在"),
	ROLE_CODE_CONFLICT(3002, "角色编码与内置角色编码冲突"),
	ROLE_INNER_MODIFY_ERROR(3003, "内置角色禁止操作"),
	ROLE_EMPTY_ERROR(3004, "无角色数据"),
	RECOMMEND_ADMIN_EMPTY_ERROR(3005, "无推荐管理员"),
	
	/* 权限相关错误：4000～4999 */
	PERMISSION_EMPTY_ERROR(4001, "无权限数据"),
	
	/* 组织机构相关错误：5000～5999 */
	COMPANY_NAME_EXISTS_ERROR(5001, "公司名已存在"),
	
	
	/* 项目相关错误：6000～6999 */
	PROGECT_NAME_EXISTS_ERROR(6001, "模型名称已存在"),
	PROGECT_LOCKED_ERROR(6002, "模型锁定中 离线标注处理未完成"),
	PROGECT_TYPE_INCONSISTENT_ERROR(6003, "模型类型不一致"),
	PROJECT_USER_ALL_NOT_TECH_SUPPORT(6004,"标签名已存在"),
	
	
	/* 图库相关错误：7000～7099 */
	IMAGE_COMPRESS_ERROR(7001, "图片压缩失败"),
	IMAGE_UPLOAD_ERROR(7002, "压缩图片上传失败"),
	NOT_SUPPORTED_ERROR(7003, "暂不支持离线标注"),
	IMAGE_EXISTS_ERROR(7004, "图片已存在"),
	LARGE_FILE_ERROR(7005, "文件过大"),
	IMAGE_FORMAT_ERROR(7006, "上传图片格式不正确"),

	/* 标签相关错误：7100～7199 */
	LABEL_DATA_MISSING_ERROR(7100, "标签数据缺失错误"),
	LABEL_DATA_FORMAT_ERROR(7101, "标签数据格式错误"),
	LABEL_DATA_DEL_ERROR(7102, "标签数据删除错误"),
	DUPLICATE_LABEL_NAME_ERROR(7103, "标签重名错误"),
	LABEL_NAME_NULL_ERROR(7104, "标签名不能为空"),

	/* 标注相关错误：7200～7299 */
	MARK_DATA_MISSING_ERROR(7200, "标注数据缺失错误"),
	MARK_DATA_FORMAT_ERROR(7201, "标注数据格式错误"),
	MARK_DATA_INFO_ERROR(7202, "标注数据格式错误: info"),
	MARK_DATA_IMAGES_ERROR(7203, "标注数据格式错误: images"),
	MARK_DATA_LICENSES_ERROR(7204, "标注数据格式错误: licenses"),
	MARK_DATA_CATEGORIES_ERROR(7205, "标注数据格式错误: categories"),
	MARK_DATA_ANNOTATIONS_ERROR(7206, "标注数据格式错误: annotations"),
	MARK_DATA_CATEGORIES_LABEL_NAME_MISSING_ERROR(7207, "标注数据缺失: categorie.name"),
	MARK_DATA_ANNOTATIONS_LABEL_NAME_MISSING_ERROR(7208, "标注数据缺失: annotation.category_name"),
	MARK_DATA_ANNOTATIONS_LABEL_NAME_UNKNOWN_ERROR(7209, "标注数据缺失: unknown category_name"),

	/* 文件上传相关错误：7300～7399 */
	FIle_UPLOAD_ERROR(7301, "文件上传失败"),
	INCORRECT_SUFFIX_ERROR(7302, "暂不支持离线标注"),
	File_EXISTS_ERROR(7303, "文件已存在"),
	FILE_DAMAGE_ERROR(7304, "文件已损坏，上传失败。"),

	/* 训练相关错误：8000～8999 */
	NO_IMAGE_ERROR(8001, "无训练图片"),
	NO_MARKE_ERROR(8002, "无图片标注"),
	EXISTS_UNFINISHED_TRAINING_ERROR(8003, "有未结束训练"),
	PREPARE_DATA_ERROR(8004, "准备数据出错"),
	NO_TEST_IMAGE_ERROR(8005, "无测试图片"),
	NO_SUCCESS_TRAIN_ERROR(8006, "无成功训练"),
	EXISTS_UNFINISHED_TESTING_ERROR(8007, "有未结束测试任务"),
	NO_MODEL_DOWNLOAD_PERMISSION_ERROR(8008, "没有模型下载权限"),
	MODEL_ZIP_NOT_EXISTS_ERROR(8009, "模型文件不存在"),
	NO_LABEL_ERROR(8010, "无标签数据"),
	NO_SUCCESS_TRAIN_ERROR_NOT_ALLOW_TEST(80066, "无成功训练不能测试"),
	
	//无报告原因
	MARKED_IMAGE_LESS(8011, "标注图片数不足"),
	LABEL_MARK_LESS(8012, "标签标注数不足"),
	IS_AI_MARK_ERROR(8013, "智能标注无模型"),
	
	/**测试结果相关*/
	NO_TESTRESULT_NOT_MARK(9001,"无测试结果，不能转标注"),
	TEST_NOT_ALLOWED(9002,"不允许测试"),


	/**
	 * 注册校验结果
	 */
	VALIDATE_NOT_BLANK(10001, "请输入验证码"),
	VALIDATE_FAILD(10002, "图片验证码不匹配"),
	VALIDATE_GET_FAILD(10003,"图片验证码获取失败"),

	/**
	 * 短信注册校验结果
	 */
	MESSAGE_VALIDATE_NOT_BLANK(11001, "请输入短信验证码"),

	MESSAGE_VALIDATE_FAILD(11002, "短信验证码获取失败"),

	MESSAGE_VALIDATE_NOT_EXIST(11003, "短信验证码不存在"),

	MESSAGE_VALIDATE_EXPIRED(11004, "短信验证码已过期"),

	MESSAGE_VALIDATE_SEND_FAILD(11005, "短信验证码发送失败"),




	/** */
	NOT_LOGGED_IN(99990, "未登录"), 
	NO_PERMISSION(99991, "无访问权限"), 
	NO_OPERATION_PERMISSION(99992, "无操作权限"), 
	REPEAT_LOGIN(99993, "您的账号已在别的地方登录，请及时修改密码"),
	ALREADY_LOGIN(99994, "当前账号已在其他设备登录"),
	UNKNOWN(-1, "未知错误");

	/** 状态码 */
	Integer code;
	/** 信息 */
	String message;

	public static String getErrorMsgByCode(Integer code) {
		for (ResultCode ele : values()) {
			if (ele.getCode().equals(code)) {
				return ele.getMessage();
			}
		}
		return "UNKNOWN";
	}

	public static String getErrorMsgNameByCode(Integer code) {
		for (ResultCode ele : values()) {
			if (ele.getCode().equals(code)) {
//				return ele.getMessage();
				return ele.toString();
			}
		}
		return "UNKNOWN";
	}
}
