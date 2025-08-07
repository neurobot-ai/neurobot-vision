package com.nbai.common.entity.dto;

import lombok.Data;

import java.util.Date;

/**
 * 算力管理，一个算力模块的使用状态
 *
 * @author zz
 * @version 1.0
 * @date 2021-1-18
 */

@Data
public class AiPower {

    /**
     * 驱动版本号
     */
    private String driverVersion;

    /**
     * 算力名称（显卡名称）
     */
    private String name;

    /**
     * 运行温度，℃
     */
    private int temperature;

    /**
     * 当前功率，单位为w
     */
    private int power;

    /**
     * 最大功率
     */
    private int maxPower;

    /**
     * 当前内存用量，单位为MB
     */
    private int memory;

    /**
     * 内存总量
     */
    private int totalMemory;

    /**
     * 算力使用率
     */
    private float utilization;

    /**
     * 运行中的任务
     */
    private AiPowerTask[] running;

    /**
     * 时间戳
     */
    private Date time;

}

