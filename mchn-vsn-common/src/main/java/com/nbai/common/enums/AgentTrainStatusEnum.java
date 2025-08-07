package com.nbai.common.enums;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 测试状态枚举
 *
 * @author zhenmeng
 * @date 2021-03-08
 */

@Getter
@AllArgsConstructor
public enum AgentTrainStatusEnum {

    NONE("none", "无任务"),
    INIT("init", "初始化"),
    QUEUING("queuing", "排队中"),

    RUNNING("running", "运行中"),

    TERMINATED("terminated", "已中止"),
    SUCCESS("success", "成功"),
    FAILURE("failure", "失败"),

    // running
    TRAINING("training", "训练中"),
    TESTING("testing", "测试中"),
    TUNING("tuning", "调参"),
    REAINING("retraining", "二次训练中");


    private String code;
    private String name;

    public static List<String> getRunningCodes() {
        return Lists.newArrayList(INIT.getCode(), QUEUING.getCode(), RUNNING.getCode(), TRAINING.getCode(),
                TESTING.getCode(), TUNING.getCode(), REAINING.getCode());
    }


}
