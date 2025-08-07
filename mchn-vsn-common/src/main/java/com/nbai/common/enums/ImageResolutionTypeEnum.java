package com.nbai.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 图片分辨率类型枚举
 *
 * @author yangqt
 * @since 2023/3/16
 */

@Getter
@AllArgsConstructor
public enum ImageResolutionTypeEnum {

    DEFAULT("default"),
    CUSTOM("custom");

    private String code;

}
