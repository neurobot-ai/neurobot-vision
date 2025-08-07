package com.nbai.common.entity;

import com.nbai.utils.SpringContextUtil;
import com.nbai.utils.I18nUtil;
import lombok.Getter;

/**
 * restful接口返回值的实体类
 *
 * @author Li Jinhui
 * @update 2021-1-19 by zz，增加errorCode字段
 * @update 2021-2-23 by zhenmeng，添加泛型
 * @since 2018/12/6
 */

@Getter
public final class RestResult<T> {
    /**
     * 返回码，类似于http status code
     */
    private int code;

    /**
     * 报错信息，供调试使用
     */
    private String message;

    /**
     * 报错信息编号，供前端根据编号显示对应的报错文字，适配不同国际化场景，使用不同的报错文字。
     */
    private int errorCode;

    /**
     * 返回的数据
     */
    private T data;

    private static final I18nUtil i18nUtil = SpringContextUtil.getBean(I18nUtil.class);

    private RestResult(int code, String message, int errorCode, T data) {
        this.code = code;
        this.message = message;
        this.errorCode = errorCode;
        this.data = data;
    }

    public static <T> RestResult<T> success() {
        return success(null);
    }

    public static <T> RestResult<T> success(T data) {
//        return new RestResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), ResultCode.NO_ERROR.getCode(), data);
        return new RestResult<T>(i18nUtil.getCode("SUCCESS.code"), i18nUtil.getMessage("SUCCESS.message"), i18nUtil.getCode("NO_ERROR.code"), data);
    }

    public static <T> RestResult<T> success(T data, String message) {
        return new RestResult<T>(i18nUtil.getCode("SUCCESS.code"), message, i18nUtil.getCode("NO_ERROR.code"), data);
    }

    public static <T> RestResult<T> fail() {
//        return new RestResult<T>(ResultCode.COMMON_FAIL.getCode(), null, ResultCode.BUSINESS_FAIL.getCode(), null);
        return new RestResult<T>(i18nUtil.getCode("COMMON_FAIL.code"), null, i18nUtil.getCode("BUSINESS_FAIL.code"), null);
    }

    public static <T> RestResult<T> fail(int errorCode) {
//        return new RestResult<T>(ResultCode.COMMON_FAIL.getCode(), i18nUtil.getMessage(ResultCode.getErrorMsgNameByCode(errorCode)+".message"), errorCode, null);
        return new RestResult<T>(i18nUtil.getCode("COMMON_FAIL.code"), i18nUtil.getMessage(ResultCode.getErrorMsgNameByCode(errorCode) + ".message"), errorCode, null);
    }

    public static <T> RestResult<T> fail(String message) {
//        return new RestResult<T>(ResultCode.COMMON_FAIL.getCode(), message, ResultCode.BUSINESS_FAIL.getCode(), null);
        return new RestResult<T>(i18nUtil.getCode("COMMON_FAIL.code"), message, i18nUtil.getCode("BUSINESS_FAIL.code"), null);
    }

    public static <T> RestResult<T> fail(int errorCode, String message) {
//        return new RestResult<T>(ResultCode.COMMON_FAIL.getCode(), message, errorCode, null);
        return new RestResult<T>(i18nUtil.getCode("COMMON_FAIL.code"), message, errorCode, null);
    }

    public static <T> RestResult<T> fail(int code, String message, int errorCode, T data) {
//        return new RestResult<T>(code, message, errorCode, data);
        return new RestResult<T>(code, message, errorCode, data);
    }
}