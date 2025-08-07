/**
 * Copyright 2020 mcu32.com(http://mcu32.com)
 */

package com.nbai.utils.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;

/**
 * 文件上传配置
 * @author lideguang
 * @since 2020/5/13
 */
@Configuration
public class MultipartConfig {

    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        //factory.setMaxFileSize("100MB"); //KB,MB
        factory.setMaxFileSize(DataSize.of(1000, DataUnit.MEGABYTES));
        ///设置总上传数据总大小
//        factory.setMaxRequestSize("100MB");
        factory.setMaxRequestSize(DataSize.of(1000,DataUnit.MEGABYTES));
        return factory.createMultipartConfig();
    }

}
