package com.nbai.advice;
import com.nbai.utils.I18nUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nbai.common.entity.RestResult;
import com.nbai.common.exception.NeurobotRuntimeException;

import lombok.extern.slf4j.Slf4j;

/**
 * controller 全局统一异常处理
 * @author zhenmeng
 * @date 2021-01-22
 */
@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @Autowired
    private I18nUtil i18nUtil;

    @ExceptionHandler(value = IllegalArgumentException.class)
    public RestResult<?> ExcepitonHandler(IllegalArgumentException ie) {
        log.error("IllegalArgumentException", ie);
//        return RestResult.fail(ResultCode.PARAM_NOT_VALID.getCode(),String.format("%s : %s",  ResultCode.PARAM_NOT_VALID.getMessage() , ie.getMessage()));
        return RestResult.fail(i18nUtil.getCode("PARAM_NOT_VALID.code"),String.format("%s : %s",  i18nUtil.getMessage("PARAM_NOT_VALID.message") , ie.getMessage()));
//        return RestResult.fail(ie.getMessage());
    }

    @ExceptionHandler(value = NeurobotRuntimeException.class)
    public RestResult<?> ExcepitonHandler(NeurobotRuntimeException ne) {
        log.error("NeurobotRuntimeException", ne);
        if(ne.getCode()!=0){
            return RestResult.fail(ne.getCode());
        }
        return RestResult.fail(ne.getMessage());
    }

    @ExceptionHandler(value = Throwable.class)
    public RestResult<?> ExcepitonHandler(Throwable e) {
        log.error("全局异常",e);
        return RestResult.fail();
    }



}