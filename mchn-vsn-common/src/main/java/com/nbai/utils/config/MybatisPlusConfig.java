/**
 * Copyright 2020 mcu32.com(http://mcu32.com)
 */

package com.nbai.utils.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *     MybatisPlus配置
 * </p>
 * @author lideguang
 * @since 2020/5/13
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * mybatis-plus分页插件<br>
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        //设置单页最大查询数量，默认500条，-1不受限制
        paginationInterceptor.setLimit(-1);
        return paginationInterceptor;
    }

}
