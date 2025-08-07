package com.nbai.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 图片生成状态
 *
 * @author yangqt
 * @date 2024-12-11
 */
@Getter
@AllArgsConstructor
public enum SyntheticStatusEnum {


    QUEUING(0, "排队"),
    RUNNING(1, "生成中"),
    SUCCESS(2, "成功"),
    FAIL(3, "失败"),
    FREE(4, "空闲"),
    TERMINATION(5, "终止");

    private Integer code;
    private String name;

}
