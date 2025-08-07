/**
 * Copyright 2020 mcu32.com(http://mcu32.com)
 */

package com.nbai.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;

/**
 * 枚举工具类
 * @author lideguang
 * @since 2020/5/13
 */
public class EnumUtil {

    /**
     * 通过key获取name
     * @param clazz
     * @param key
     * @return
     */
    public static String getEnum(Class<?> clazz,Integer key){
       try{
           Object[] objects = clazz.getEnumConstants();
           Method keyMethod = clazz.getDeclaredMethod("getKey");
           Method valueMethod = clazz.getDeclaredMethod("getValue");
           for (Object object : objects) {
               Integer k = (Integer) keyMethod.invoke(object);
               if (k.equals(key)){
                   String value = (String) valueMethod.invoke(object);
                   return value;
               }
           }
       }catch (Exception e){
           e.printStackTrace();
       }
        return null;
    }

    /**
     * 判断key在枚举中是否存在
     * @param clazz
     * @param key
     * @return
     */
    public static boolean exists(Class<?> clazz,Integer key){
        String name = getEnum(clazz,key);
        if (StringUtils.isBlank(name)){
            return false;
        }
        return true;
    }

    /**
     * 判断key在枚举中是否不存在
     * @param clazz
     * @param key
     * @return
     */
    public static boolean notExists(Class<?> clazz,Integer key){
        return !exists(clazz,key);
    }

}
