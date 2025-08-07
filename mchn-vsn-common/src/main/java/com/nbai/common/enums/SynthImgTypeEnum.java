package com.nbai.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;



/**
 * 图片生成类型
 *
 * @author yangqt
 * @date 2024-12-11
 */
@Getter
@AllArgsConstructor
public enum SynthImgTypeEnum {


    REFERENCE(0, "常规图片"),

    SYNTHETIC(1, "生成图"),

    BACKGROUND(2, "背景图"),

    TEST(3,"测试图");

    private Integer code;
    private String name;

}
