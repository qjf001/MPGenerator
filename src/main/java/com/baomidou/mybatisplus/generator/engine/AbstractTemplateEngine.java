package com.baomidou.mybatisplus.generator.engine;


import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

public abstract class AbstractTemplateEngine {
    protected static final Logger logger = LoggerFactory.getLogger(AbstractTemplateEngine.class);
    private ConfigBuilder configBuilder;

    public AbstractTemplateEngine() {
    }

    public AbstractTemplateEngine init(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        return this;
    }

    public AbstractTemplateEngine batchOutput() {
        try {
            List e = this.getConfigBuilder().getTableInfoList();
            Iterator i$ = e.iterator();

            while (true) {
                TableInfo tableInfo;
                Map objectMap;
                List focList1;
                do {
                    do {
                        if (!i$.hasNext()) {
                            return this;
                        }

                        tableInfo = (TableInfo) i$.next();
                        objectMap = this.getObjectMap(tableInfo);
                        Map pathInfo = this.getConfigBuilder().getPathInfo();
                        TemplateConfig template = this.getConfigBuilder().getTemplate();
                        String entityName = tableInfo.getEntityName();
                        String focList;
                        if (null != entityName) {
                            focList = String.format((String) pathInfo.get("entity_path") + File.separator + "%s" + this.suffixJavaOrKt(), new Object[]{entityName});
                            if (this.isCreate(focList)) {
                                this.writer(objectMap, this.templateFilePath(template.getEntity(this.getConfigBuilder().getGlobalConfig().isKotlin())), focList);
                            }
                        }

                        if (null != tableInfo.getMapperName()) {
                            focList = String.format((String) pathInfo.get("mapper_path") + File.separator + tableInfo.getMapperName() + this.suffixJavaOrKt(), new Object[]{entityName});
                            if (this.isCreate(focList)) {
                                this.writer(objectMap, this.templateFilePath(template.getMapper()), focList);
                            }
                        }

                        if (null != tableInfo.getXmlName()) {
                            focList = String.format((String) pathInfo.get("xml_path") + File.separator + tableInfo.getXmlName() + ".xml", new Object[]{entityName});
                            if (this.isCreate(focList)) {
                                this.writer(objectMap, this.templateFilePath(template.getXml()), focList);
                            }
                        }

                        if (null != tableInfo.getServiceName()) {
                            focList = String.format((String) pathInfo.get("serivce_path") + File.separator + tableInfo.getServiceName() + this.suffixJavaOrKt(), new Object[]{entityName});
                            if (this.isCreate(focList)) {
                                this.writer(objectMap, this.templateFilePath(template.getService()), focList);
                            }
                        }

                        if (null != tableInfo.getServiceImplName()) {
                            focList = String.format((String) pathInfo.get("serviceimpl_path") + File.separator + tableInfo.getServiceImplName() + this.suffixJavaOrKt(), new Object[]{entityName});
                            if (this.isCreate(focList)) {
                                this.writer(objectMap, this.templateFilePath(template.getServiceImpl()), focList);
                            }
                        }

                        if (null != tableInfo.getControllerName()) {
                            focList = String.format((String) pathInfo.get("controller_path") + File.separator + tableInfo.getControllerName() + this.suffixJavaOrKt(), new Object[]{entityName});
                            if (this.isCreate(focList)) {
                                String templatePath = this.templateFilePath(template.getController());
                                String templatePagePage = templatePath.substring(0,templatePath.lastIndexOf("/")+1);
                                this.writer(objectMap, this.templateFilePath(template.getController()), focList);
//                                this.writer(objectMap, templatePagePage+"list.ftl", pathInfo.get("entity_path")+"/"+entityName+"_list.ftl");
//                                this.writer(objectMap, templatePagePage+"edit.ftl", pathInfo.get("entity_path")+"/"+entityName+"_edit.ftl");
                            }
                        }

                        if(null != tableInfo.getListName()){

                            focList = String.format((String) pathInfo.get("list_path") + File.separator + tableInfo.getListName() + configBuilder.getGlobalConfig().getViewSuffix(), new Object[]{entityName});
                            if (this.isCreate(focList)) {
                                this.writer(objectMap, this.templateFilePath(template.getList()), focList);
                            }
                        }
                        if(null != tableInfo.getEditName()){
                            focList = String.format((String) pathInfo.get("edit_path") + File.separator + tableInfo.getEditName() + configBuilder.getGlobalConfig().getViewSuffix(), new Object[]{entityName});
                            if (this.isCreate(focList)) {
                                this.writer(objectMap, this.templateFilePath(template.getEdit()), focList);
                            }
                        }

                    } while (null == this.getConfigBuilder().getInjectionConfig());

                    focList1 = this.getConfigBuilder().getInjectionConfig().getFileOutConfigList();
                } while (!CollectionUtils.isNotEmpty(focList1));

                Iterator i$1 = focList1.iterator();

                while (i$1.hasNext()) {
                    FileOutConfig foc = (FileOutConfig) i$1.next();
                    if (this.isCreate(foc.outputFile(tableInfo))) {
                        this.writer(objectMap, foc.getTemplatePath(), foc.outputFile(tableInfo));
                    }
                }
            }
        } catch (Exception var11) {
            logger.error("无法创建文件，请检查配置信息！", var11);
            return this;
        }
    }

    public abstract void writer(Map<String, Object> var1, String var2, String var3) throws Exception;

    public AbstractTemplateEngine mkdirs() {
        Map pathInfo = this.getConfigBuilder().getPathInfo();
        Iterator i$ = pathInfo.entrySet().iterator();

        while (i$.hasNext()) {
            Entry entry = (Entry) i$.next();
            File dir = new File((String) entry.getValue());
            if (!dir.exists()) {
                boolean result = dir.mkdirs();
                if (result) {
                    logger.debug("创建目录： [" + (String) entry.getValue() + "]");
                }
            }
        }

        return this;
    }

    public void open() {
        if (this.getConfigBuilder().getGlobalConfig().isOpen()) {
            try {
                String e = System.getProperty("os.name");
                if (e != null) {
                    if (e.contains("Mac")) {
                        Runtime.getRuntime().exec("open " + this.getConfigBuilder().getGlobalConfig().getOutputDir());
                    } else if (e.contains("Windows")) {
                        Runtime.getRuntime().exec("cmd /c start " + this.getConfigBuilder().getGlobalConfig().getOutputDir());
                    } else {
                        logger.debug("文件输出目录:" + this.getConfigBuilder().getGlobalConfig().getOutputDir());
                    }
                }
            } catch (IOException var2) {
                var2.printStackTrace();
            }
        }

    }

    public Map<String, Object> getObjectMap(TableInfo tableInfo) {
        HashMap objectMap = new HashMap();
        ConfigBuilder config = this.getConfigBuilder();
        if (config.getStrategyConfig().isControllerMappingHyphenStyle()) {
            objectMap.put("controllerMappingHyphenStyle", Boolean.valueOf(config.getStrategyConfig().isControllerMappingHyphenStyle()));
            objectMap.put("controllerMappingHyphen", StringUtils.camelToHyphen(tableInfo.getEntityPath()));
        }

        objectMap.put("restControllerStyle", Boolean.valueOf(config.getStrategyConfig().isRestControllerStyle()));
        objectMap.put("package", config.getPackageInfo());
        GlobalConfig globalConfig = config.getGlobalConfig();
        objectMap.put("author", globalConfig.getAuthor());
        objectMap.put("idType", globalConfig.getIdType() == null ? null : globalConfig.getIdType().toString());
        objectMap.put("logicDeleteFieldName", config.getStrategyConfig().getLogicDeleteFieldName());
        objectMap.put("versionFieldName", config.getStrategyConfig().getVersionFieldName());
        objectMap.put("activeRecord", Boolean.valueOf(globalConfig.isActiveRecord()));
        objectMap.put("kotlin", Boolean.valueOf(globalConfig.isKotlin()));
        objectMap.put("date", (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()));
        objectMap.put("table", tableInfo);
        objectMap.put("enableCache", Boolean.valueOf(globalConfig.isEnableCache()));
        objectMap.put("baseResultMap", Boolean.valueOf(globalConfig.isBaseResultMap()));
        objectMap.put("baseColumnList", Boolean.valueOf(globalConfig.isBaseColumnList()));
        objectMap.put("entity", tableInfo.getEntityName());
        objectMap.put("entityColumnConstant", Boolean.valueOf(config.getStrategyConfig().isEntityColumnConstant()));
        objectMap.put("entityBuilderModel", Boolean.valueOf(config.getStrategyConfig().isEntityBuilderModel()));
        objectMap.put("entityLombokModel", Boolean.valueOf(config.getStrategyConfig().isEntityLombokModel()));
        objectMap.put("entityBooleanColumnRemoveIsPrefix", Boolean.valueOf(config.getStrategyConfig().isEntityBooleanColumnRemoveIsPrefix()));
        objectMap.put("superEntityClass", this.getSuperClassName(config.getSuperEntityClass()));
        objectMap.put("superMapperClassPackage", config.getSuperMapperClass());
        objectMap.put("superMapperClass", this.getSuperClassName(config.getSuperMapperClass()));
        objectMap.put("superServiceClassPackage", config.getSuperServiceClass());
        objectMap.put("superServiceClass", this.getSuperClassName(config.getSuperServiceClass()));
        objectMap.put("superServiceImplClassPackage", config.getSuperServiceImplClass());
        objectMap.put("superServiceImplClass", this.getSuperClassName(config.getSuperServiceImplClass()));
        objectMap.put("superControllerClassPackage", config.getSuperControllerClass());
        objectMap.put("superControllerClass", this.getSuperClassName(config.getSuperControllerClass()));
        return objectMap;
    }

    private String getSuperClassName(String classPath) {
        return StringUtils.isEmpty(classPath) ? null : classPath.substring(classPath.lastIndexOf(".") + 1);
    }

    public abstract String templateFilePath(String var1);

    protected boolean isCreate(String filePath) {
        File file = new File(filePath);
        return !file.exists() || this.getConfigBuilder().getGlobalConfig().isFileOverride();
    }

    protected String suffixJavaOrKt() {
        return this.getConfigBuilder().getGlobalConfig().isKotlin() ? ".kt" : ".java";
    }

    public ConfigBuilder getConfigBuilder() {
        return this.configBuilder;
    }

    public AbstractTemplateEngine setConfigBuilder(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        return this;
    }
}
