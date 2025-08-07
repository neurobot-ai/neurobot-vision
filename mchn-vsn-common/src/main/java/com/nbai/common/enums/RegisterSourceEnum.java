package com.nbai.common.enums;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 注册来源枚举项
 */
@Getter
@AllArgsConstructor
public enum RegisterSourceEnum {
    //后台添加
    ADD(0, "后台添加"),
    //用户注册-百度推广
    RE_BD(1, "用户注册-百度推广"),
    //用户注册-360推广
    RE_360(2, "用户注册-360推广"),
    ;


    private Integer code;
    private String name;


    public static RegisterSourceEnum getByCode(Integer code){
        RegisterSourceEnum[] values = values();
        for (RegisterSourceEnum enums : values) {
            if (enums.getCode().equals(code)){
                return enums;
            }
        }
        return null;
    }

}
