package com.nbai.utils.aop;

import cn.hutool.core.date.DateUnit;

import java.lang.annotation.*;

/**
 * * LD大法师
 **/
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RecordMethodInvokeTime {

    DateUnit value();

}
