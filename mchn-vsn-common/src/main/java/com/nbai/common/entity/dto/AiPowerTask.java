package com.nbai.common.entity.dto;

import lombok.Data;

/**
 * 算力任务，一个正在进行中的计算任务使用的算力情况
 *
 * @author zz
 * @version 1.0
 * @date 2021-1-18
 */

@Data
public class AiPowerTask {

    /**
     * 使用的gpu编号
     */
    private int gpu;

    /**
     * pid
     */
    private int pid;

    /**
     * 任务类型
     */
    private String type;

    /**
     * 进程
     */
    private String process;

    /**
     * 当前内存用量，单位为MB
     */
    private long memory;

}
