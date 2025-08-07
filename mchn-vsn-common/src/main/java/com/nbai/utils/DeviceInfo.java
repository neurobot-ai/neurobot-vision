/**
 * Copyright 2020 mcu32.com(http://mcu32.com)
 */
package com.nbai.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * 设备信息
 * @author lideguang
 * @since 2019/10/10
 */

@Data
public class DeviceInfo implements Serializable {
    private static final long serialVersionUID = -5912785220335057555L;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 设备型号
     */
    private String model;
}
