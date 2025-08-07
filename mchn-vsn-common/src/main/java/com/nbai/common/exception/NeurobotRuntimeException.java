package com.nbai.common.exception;

import com.nbai.common.entity.ResultCode;

import com.nbai.utils.I18nUtil;
import com.nbai.utils.SpringContextUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常
 * 
 * @author zhenmeng
 * @since 2021-02-24
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class NeurobotRuntimeException extends RuntimeException {

	private final I18nUtil i18nUtil= SpringContextUtil.getBean(I18nUtil.class);

	private static final long serialVersionUID = 1L;

	private int code;
	private String message;
	
	public NeurobotRuntimeException(int code) {

		super();
		this.code = code;
//		this.message = ResultCode.getErrorMsgByCode(code);
		this.message = i18nUtil.getMessage(ResultCode.getErrorMsgNameByCode(code)+".message");
	}

	public NeurobotRuntimeException(String message) {
		super(message);
		this.message = message;
	}
}
