package com.nbai.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author liuxuli
 * @date 2021/7/16 15:06
 */
@Getter
@AllArgsConstructor
public enum  ShapeTypeEnum {
    //点
    POINT("point","点",1),
    //线
    LINE("line","线",2),
    //圆
    CIRCLE("circle","圆",2),
    //矩形
    RECTANGLE("rectangle","矩形",4);
    private String code;

    private String value;

    private int pointNum;

    public static ShapeTypeEnum getByCode(String code){
        ShapeTypeEnum[] values = values();
        for (ShapeTypeEnum enums : values) {
            if (enums.getCode().equals(code)){
                return enums;
            }
        }
        return null;
    }
}
