/**
 * Copyright 2020 nb-ai.com(http://nb-ai.com)
 */

package com.nbai;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * nb-ai代码生成器入口类
 * @author 周展
 * @since 2020-10-27
 */
public class MBPCodeGenerator {

    // ############################ 配置部分 start ############################
    // 配置数据库
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
//    private static final String USER_NAME = "NeuroBot.I18N";
//    private static final String PASSWORD = "2020MmHF769MzW";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "neurobot";
    private static final String DRIVER_URL = "jdbc:mysql://192.168.22.111:3306/mchn_vsn_public" +
            "?characterEncoding=utf-8" +
            "&autoReconnect=false&failOverReadOnly=false&useSSL=false&serverTimezone=UTC";
    // 作者
    private static final String AUTHOR = "ZZ";
    // 生成的表名称
    private static final String TABLE_NAME = "xsy_image_count";
    // 主键数据库列名称
    private static final String PK_ID_COLUMN_NAME = "id";

    // -----------------以下采用默认即可
    // 模块名称
    private static final String MODULE_NAME = "common";

    // 代码生成策略 true：All/false:SIMPLE
    private static final boolean GENERATOR_STRATEGY = true;
    // 分页列表查询是否排序 true：有排序参数/false：无
    private static final boolean PAGE_LIST_ORDER = false;
    // ############################ 配置部分 end ############################

    private static final String ROOT_PACKAGE ="com.nbai";
    private static final String PARENT_PACKAGE = "com.nbai";
    private static final String COMMON_PATH = "mcu32.czxk.common";
//    private static final String SUPER_ENTITY = "mcu32.czxk.common.core.BaseEntity";
    private static final String[] SUPER_ENTITY_COMMON_COLUMNS = new String[]{};
//    private static final String SUPER_CONTROLLER = "mcu32.czxk.common.core.web.controller.BaseController";
//    private static final String SUPER_SERVICE = "mcu32.czxk.common.core.service.BaseService";
//    private static final String SUPER_SERVICE_IMPL = "mcu32.czxk.common.core.service.impl.BaseServiceImpl";

    private static final String PROJECT_PACKAGE_PATH = "com/nbai";


    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator generator = new AutoGenerator();

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        globalConfig.setOutputDir(projectPath + "/mchn-vsn-common/src/main/java");
        globalConfig.setAuthor(AUTHOR);
        globalConfig.setOpen(false);                  // 是否打开输出目录
        globalConfig.setSwagger2(true);               // 启用swagger注解
        globalConfig.setIdType(IdType.AUTO);     // 主键类型:AUTO
        globalConfig.setServiceName("%sService");     // 自定义文件命名，注意 %s 会自动填充表实体属性！
        globalConfig.setFileOverride(true);           // 是否覆盖已有文件
        globalConfig.setDateType(DateType.ONLY_DATE); // 设置日期类型为Date

        generator.setGlobalConfig(globalConfig);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(DRIVER_URL);
        // dsc.setSchemaName("public");
        dataSourceConfig.setDriverName(DRIVER_NAME);
        dataSourceConfig.setUsername(USER_NAME);
        dataSourceConfig.setPassword(PASSWORD);
        generator.setDataSource(dataSourceConfig);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setModuleName(MODULE_NAME);
        packageConfig.setParent(PARENT_PACKAGE);
        packageConfig.setController("controller");

        generator.setPackageInfo(packageConfig);

        // 自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("customField", "Hello " + this.getConfig().getGlobalConfig().getAuthor());
                // 查询参数包路径
                map.put("queryParamPath",PARENT_PACKAGE + StringPool.DOT + packageConfig.getModuleName() + ".web.param." + underlineToPascal(TABLE_NAME) + "QueryParam");
                // 查询参数共公包路径
                map.put("queryParamCommonPath",COMMON_PATH + StringPool.DOT + "core.web.param." + "QueryParam");
                // 响应结果包路径
                map.put("queryVoPath",PARENT_PACKAGE + StringPool.DOT + packageConfig.getModuleName() + ".web.vo." + underlineToPascal(TABLE_NAME) + "QueryVO");
                // 实体对象名称
                map.put("entityObjectName",underlineToCamel(TABLE_NAME));
                // service对象名称
                map.put("serviceObjectName",underlineToCamel(TABLE_NAME) + "Service");
                // mapper对象名称
                map.put("mapperObjectName",underlineToCamel(TABLE_NAME) + "Mapper");
                // 主键ID列名
                map.put("pkIdColumnName",PK_ID_COLUMN_NAME);
                // 主键ID驼峰名称
                map.put("pkIdCamelName",underlineToCamel(PK_ID_COLUMN_NAME));
                // 导入分页类
                map.put("paging",COMMON_PATH + ".core.web.vo.Paging");
                // 分页列表查询是否排序
                map.put("pageListOrder",PAGE_LIST_ORDER);
                // 导入排序查询参数类
                map.put("orderQueryParamPath",COMMON_PATH + ".core.web.param." + "OrderQueryParam");
                // 代码生成策略
                map.put("generatorStrategy",GENERATOR_STRATEGY);
                this.setMap(map);
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/resources/mapper/" + packageConfig.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        // 自定义queryParam模板
        focList.add(new FileOutConfig("/templates/queryParam.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/java/"+ PROJECT_PACKAGE_PATH +"/" + packageConfig.getModuleName() + "/web/param/" + tableInfo.getEntityName() + "QueryParam" + StringPool.DOT_JAVA;
            }
        });

        // 自定义queryVo模板
        focList.add(new FileOutConfig("/templates/queryVO.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/java/"+ PROJECT_PACKAGE_PATH +"/" + packageConfig.getModuleName() + "/web/vo/" + tableInfo.getEntityName() + "QueryVO" + StringPool.DOT_JAVA;
            }
        });


        injectionConfig.setFileOutConfigList(focList);
        generator.setCfg(injectionConfig);
        generator.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass(SUPER_ENTITY);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
//        strategy.setSuperControllerClass(SUPER_CONTROLLER);
//        strategy.setSuperServiceClass(SUPER_SERVICE);
//        strategy.setSuperServiceImplClass(SUPER_SERVICE_IMPL);
        strategy.setInclude(TABLE_NAME);
//        strategy.setSuperEntityColumns(SUPER_ENTITY_COMMON_COLUMNS);
        strategy.setControllerMappingHyphenStyle(true);
        /**
         * 注意，根据实际情况，进行设置
         * 当表名称的前缀和模块名称一样时，会去掉表的前缀
         * 比如模块名称为user,表明为user_info,则生成的实体名称是Info.java,一定要注意
         */
        //strategy.setTablePrefix(pc.getModuleName() + "_");
        generator.setStrategy(strategy);
        generator.execute();
    }

    public static String underlineToCamel(String underline){
        if (StringUtils.isNotBlank(underline)){
            return NamingStrategy.underlineToCamel(underline);
        }
        return null;
    }

    public static String underlineToPascal(String underline){
        if (StringUtils.isNotBlank(underline)){
            return NamingStrategy.capitalFirst(NamingStrategy.underlineToCamel(underline));
        }
        return null;
    }
}
