package com.nbai.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @uthor czh
 * @date 2025/7/29
 * @
 */
@Getter
@AllArgsConstructor
public enum DatasetSplit {

    TRAIN("train"),
    VALID("valid"),
    TEST("test"),
    RAW("raw");

    private final String code;

    // 静态方法：验证值是否合法
    public static boolean isValid(String value) {
        return Arrays.stream(DatasetSplit.values())
                .anyMatch(split -> split.getCode().equals(value));
    }

    // 静态方法：将字符串转换为枚举（带异常处理）
    public static DatasetSplit fromCode(String code) {
        // 1. 遍历枚举中所有实例（TRAIN、VALID、TEST、RAW）
        return Arrays.stream(DatasetSplit.values())
                // 2. 筛选出枚举实例的code与传入的字符串code匹配的项
                .filter(split -> split.getCode().equals(code))
                // 3. 取第一个匹配的结果（因为枚举值唯一，最多只有一个匹配）
                .findFirst()
                // 4. 如果没找到匹配的，就抛出异常，提示非法值
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid split value: " + code + ". Allowed: train, valid, test, raw"
                ));
    }
}
