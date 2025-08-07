package com.nbai.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VerificationCodeEnum {

    CODE_LENGTH(6L, "验证码长度"), // 验证码长度
    CODE_EXPIRATION_TIME(5 * 60 * 1000L, "验证码长度"), // 验证码长度
    PROFILE_UPDATE_EXPIRATION_TIME(15 * 60 * 1000L,"信息补充有效时间"),
    Verification_Code_Default_Values(78653L,"验证码默认值"),
    REQUEST_LIMIT_TIME(60 * 1000L, "每分钟请求限制时间"); // 每分钟请求限制时间



    private final Long code;
    private final String name;


}
