package com.nbai.common.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import com.nbai.common.entity.dto.KeyValueDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 测试状态枚举
 *
 * @author zhenmeng
 * @date 2021-03-08
 */

@Getter
@AllArgsConstructor
public enum TestStatusEnum {

    QUEUING("queuing", "排队中"),

    NO("no", "未测试"),
    TO_BE_INITIALIZED("toBeInitialized", "待测试"),
    INITIALIZATION("initialization", "初始化"),
    PREPARING_DATA("preparingData", "准备数据中"),
    QUEUING_FOR_ASSIGNMENT("queuingForAssignment", "排队等待分配"),
    QUEUING_FOR_TRANSMITTING_DATA("queuingForTransmittingData", "排队等待传输数据"),
    TRANSFERRING_DATA("transferringData", "传输数据中"),
    TO_BE_STARTED("toBeStarted", "传输完毕-待启动"),
    TESTING("testing", "测试中"),
    TEST_COMPLETED("testCompleted", "测试完成-待传输结果"),
    TRANSFERRING_RESULT("transferringResult", "传输结果中"),
    TRANSMISSION_COMPLETED("transmissionCompleted", "传输完毕"),
    SUCCESS("success", "成功"),
    FAILURE("failure", "失败"),
    TERMINATION("termination", "终止");

    private String code;
    private String name;


    /**
     * 校验code
     *
     * @return
     */
    public static boolean checkCode(String code) {
        if (StringUtils.isBlank(code)) return false;
        for (TestStatusEnum e : values()) {
            if (code.equals(e.getCode())) return true;
        }
        return false;
    }

    public static String getNameBycode(String code) {
        if (StringUtils.isBlank(code)) return Strings.EMPTY;
        for (TestStatusEnum e : values()) {
            if (code.equals(e.getCode())) return e.getName();
        }
        return Strings.EMPTY;
    }

    /**
     * 获取code列表
     *
     * @return
     */
    public static List<String> getCodeList() {
        List<String> list = new ArrayList<>(values().length);
        for (TestStatusEnum e : values()) {
            list.add(e.getCode());
        }
        return list;
    }

    public static List<KeyValueDTO> getList() {
        List<KeyValueDTO> list = new ArrayList<>(values().length);
        for (TestStatusEnum e : values()) {
            list.add(new KeyValueDTO().setStringkey(e.getCode()).setValue(e.getName()));
        }
        return list;
    }


    /**
     * @param status:   状态接口返回的测试状态值
     * @return
     */
    public static String transformTestStatus(String status) {
        if (StringUtils.isBlank(status)) return Strings.EMPTY;
        if ("失败".equals(status)) return TestStatusEnum.FAILURE.getCode();
        if ("测试中".equals(status)) return TestStatusEnum.TESTING.getCode();
        if ("成功".equals(status)) return TestStatusEnum.TEST_COMPLETED.getCode();
        return Strings.EMPTY;
    }


    /**
     * 状态处理器
     *
     * @param status 状态码
     * @return
     */
    public static String statusHandler(String status) {
        if (StringUtils.isBlank(status)) return status;

        if (TestStatusEnum.INITIALIZATION.getCode().equals(status)
                || TestStatusEnum.PREPARING_DATA.getCode().equals(status)
                || TestStatusEnum.QUEUING_FOR_ASSIGNMENT.getCode().equals(status)
                || TestStatusEnum.QUEUING_FOR_TRANSMITTING_DATA.getCode().equals(status)) {

            return TestStatusEnum.QUEUING.getCode();
        }

        if (TestStatusEnum.TESTING.getCode().equals(status)
                || TestStatusEnum.TEST_COMPLETED.getCode().equals(status)
                || TestStatusEnum.TRANSFERRING_RESULT.getCode().equals(status)
                || TestStatusEnum.TRANSMISSION_COMPLETED.getCode().equals(status)
                || TestStatusEnum.TRANSFERRING_DATA.getCode().equals(status)
                || TestStatusEnum.TO_BE_STARTED.getCode().equals(status)) {

            return TestStatusEnum.TESTING.getCode();
        }
        return status;
    }

    /**
     * 获取状态列表	比如：测试中、测试完成-待传输结果、传输结果中，传输完成 都属于测试中
     *
     * @param code
     * @return
     */
    public static List<String> getCodesByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        if (NO.getCode().equals(code)) {
            return Collections.emptyList();
        }
        List<String> result = Lists.newArrayList();
        result.add(code);
        if (QUEUING.getCode().equals(code)) {
            result.add(INITIALIZATION.getCode());
            result.add(PREPARING_DATA.getCode());
            result.add(QUEUING_FOR_ASSIGNMENT.getCode());
            result.add(QUEUING_FOR_TRANSMITTING_DATA.getCode());
            //result.add(TRANSFERRING_DATA.getCode());
            //result.add(TO_BE_STARTED.getCode());
        }

        if (TESTING.getCode().equals(code)) {
            result.add(TEST_COMPLETED.getCode());
            result.add(TRANSFERRING_RESULT.getCode());
            result.add(TRANSMISSION_COMPLETED.getCode());
            result.add(TRANSFERRING_DATA.getCode());
            result.add(TO_BE_STARTED.getCode());
        }
        return result;
    }

}
