package com.nbai.common.enums;

import com.google.common.collect.Lists;
import com.nbai.common.entity.dto.KeyValueDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 测试状态枚举
 *
 * @author zhenmeng
 * @date 2021-03-08
 */

@Getter
@AllArgsConstructor
public enum FinetuneStatusEnum {

    NONE("none", "无微调"),
    INIT("init", "初始化"),
    QUEUING("queuing", "排队中"),

    PENDING("pending", "等待启动"),
    TRAINING("training", "训练中"),
    TERMINATING("terminating", "中止中"),
    TERMINATED("terminated", "已中止"),

    SUCCESS("success", "成功"),
    FAILURE("failure", "失败");

    private String code;
    private String name;



}
