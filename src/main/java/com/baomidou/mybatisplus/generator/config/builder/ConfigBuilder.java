package com.baomidou.mybatisplus.generator.config.builder;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.config.rules.QuerySQL;
import com.baomidou.mybatisplus.toolkit.StringUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ConfigBuilder {
    private final TemplateConfig template;
    private final DataSourceConfig dataSourceConfig;
    private Connection connection;
    private QuerySQL querySQL;
    private String superEntityClass;
    private String superMapperClass;
    private String superServiceClass;
    private String superServiceImplClass;
    private String superControllerClass;
    private List<TableInfo> tableInfoList;
    private Map<String, String> packageInfo;
    private Map<String, String> pathInfo;
    private StrategyConfig strategyConfig;
    private GlobalConfig globalConfig;
    private InjectionConfig injectionConfig;

    public ConfigBuilder(PackageConfig packageConfig, DataSourceConfig dataSourceConfig, StrategyConfig strategyConfig, TemplateConfig template, GlobalConfig globalConfig) {
        if(null == globalConfig) {
            this.globalConfig = new GlobalConfig();
        } else {
            this.globalConfig = globalConfig;
        }

        if(null == template) {
            this.template = new TemplateConfig();
        } else {
            this.template = template;
        }

        if(null == packageConfig) {
            this.handlerPackage(this.template, this.globalConfig.getOutputDir(), new PackageConfig());
        } else {
            this.handlerPackage(this.template, this.globalConfig.getOutputDir(), packageConfig);
        }

        this.dataSourceConfig = dataSourceConfig;
        this.handlerDataSource(dataSourceConfig);
        if(null == strategyConfig) {
            this.strategyConfig = new StrategyConfig();
        } else {
            this.strategyConfig = strategyConfig;
        }

        this.handlerStrategy(this.strategyConfig);
    }

    public Map<String, String> getPackageInfo() {
        return this.packageInfo;
    }

    public Map<String, String> getPathInfo() {
        return this.pathInfo;
    }

    public String getSuperEntityClass() {
        return this.superEntityClass;
    }

    public String getSuperMapperClass() {
        return this.superMapperClass;
    }

    public String getSuperServiceClass() {
        return this.superServiceClass;
    }

    public String getSuperServiceImplClass() {
        return this.superServiceImplClass;
    }

    public String getSuperControllerClass() {
        return this.superControllerClass;
    }

    public List<TableInfo> getTableInfoList() {
        return this.tableInfoList;
    }

    public ConfigBuilder setTableInfoList(List<TableInfo> tableInfoList) {
        this.tableInfoList = tableInfoList;
        return this;
    }

    public TemplateConfig getTemplate() {
        return this.template == null?new TemplateConfig():this.template;
    }

    private void handlerPackage(TemplateConfig template, String outputDir, PackageConfig config) {
        this.packageInfo = new HashMap();
        this.packageInfo.put("ModuleName", config.getModuleName());
        this.packageInfo.put("Entity", this.joinPackage(config.getParent(), config.getEntity()));
        this.packageInfo.put("Mapper", this.joinPackage(config.getParent(), config.getMapper()));
        this.packageInfo.put("Xml", this.joinPackage(config.getParent(), config.getXml()));
        this.packageInfo.put("Service", this.joinPackage(config.getParent(), config.getService()));
        this.packageInfo.put("ServiceImpl", this.joinPackage(config.getParent(), config.getServiceImpl()));
        this.packageInfo.put("Controller", this.joinPackage(config.getParent(), config.getController()));
        this.packageInfo.put("list", this.joinPackage(config.getParent(), config.getList()));
        this.packageInfo.put("edit", this.joinPackage(config.getParent(), config.getEdit()));
        this.pathInfo = new HashMap();
        if(StringUtils.isNotEmpty(template.getEntity(this.getGlobalConfig().isKotlin()))) {
            this.pathInfo.put("entity_path", this.joinPath(outputDir, (String)this.packageInfo.get("Entity")));
        }

        if(StringUtils.isNotEmpty(template.getMapper())) {
            this.pathInfo.put("mapper_path", this.joinPath(outputDir, (String)this.packageInfo.get("Mapper")));
        }

        if(StringUtils.isNotEmpty(template.getXml())) {
            this.pathInfo.put("xml_path", this.joinPath(outputDir, (String)this.packageInfo.get("Xml")));
        }

        if(StringUtils.isNotEmpty(template.getService())) {
            this.pathInfo.put("serivce_path", this.joinPath(outputDir, (String)this.packageInfo.get("Service")));
        }

        if(StringUtils.isNotEmpty(template.getServiceImpl())) {
            this.pathInfo.put("serviceimpl_path", this.joinPath(outputDir, (String)this.packageInfo.get("ServiceImpl")));
        }

        if(StringUtils.isNotEmpty(template.getController())) {
            this.pathInfo.put("controller_path", this.joinPath(outputDir, (String)this.packageInfo.get("Controller")));
        }

        if(StringUtils.isNotEmpty(template.getList())) {
            this.pathInfo.put("list_path", this.joinPath(outputDir, (String)this.packageInfo.get("list")));
        }

        if(StringUtils.isNotEmpty(template.getEdit())) {
            this.pathInfo.put("edit_path", this.joinPath(outputDir, (String)this.packageInfo.get("edit")));
        }
    }

    private void handlerDataSource(DataSourceConfig config) {
        this.connection = config.getConn();
        this.querySQL = this.getQuerySQL(config.getDbType());
    }

    private void handlerStrategy(StrategyConfig config) {
        this.processTypes(config);
        this.tableInfoList = this.getTablesInfo(config);
    }

    private void processTypes(StrategyConfig config) {
        if(StringUtils.isEmpty(config.getSuperServiceClass())) {
            this.superServiceClass = "com.baomidou.mybatisplus.service.IService";
        } else {
            this.superServiceClass = config.getSuperServiceClass();
        }

        if(StringUtils.isEmpty(config.getSuperServiceImplClass())) {
            this.superServiceImplClass = "com.baomidou.mybatisplus.service.impl.ServiceImpl";
        } else {
            this.superServiceImplClass = config.getSuperServiceImplClass();
        }

        if(StringUtils.isEmpty(config.getSuperMapperClass())) {
            this.superMapperClass = "com.baomidou.mybatisplus.mapper.BaseMapper";
        } else {
            this.superMapperClass = config.getSuperMapperClass();
        }

        this.superEntityClass = config.getSuperEntityClass();
        this.superControllerClass = config.getSuperControllerClass();
    }

    private List<TableInfo> processTable(List<TableInfo> tableList, NamingStrategy strategy, StrategyConfig config) {
        String[] tablePrefix = config.getTablePrefix();
        String[] fieldPrefix = config.getFieldPrefix();

        TableInfo tableInfo;
        for(Iterator i$ = tableList.iterator(); i$.hasNext(); this.checkTableIdTableFieldAnnotation(config, tableInfo, fieldPrefix)) {
            tableInfo = (TableInfo)i$.next();
            tableInfo.setEntityName(this.strategyConfig, NamingStrategy.capitalFirst(this.processName(tableInfo.getName(), strategy, tablePrefix)));
            if(StringUtils.isNotEmpty(this.globalConfig.getMapperName())) {
                tableInfo.setMapperName(String.format(this.globalConfig.getMapperName(), new Object[]{tableInfo.getEntityName()}));
            } else {
                tableInfo.setMapperName(tableInfo.getEntityName() + "Mapper");
            }

            if(StringUtils.isNotEmpty(this.globalConfig.getXmlName())) {
                tableInfo.setXmlName(String.format(this.globalConfig.getXmlName(), new Object[]{tableInfo.getEntityName()}));
            } else {
                tableInfo.setXmlName(tableInfo.getEntityName() + "Mapper");
            }

            if(StringUtils.isNotEmpty(this.globalConfig.getServiceName())) {
                tableInfo.setServiceName(String.format(this.globalConfig.getServiceName(), new Object[]{tableInfo.getEntityName()}));
            } else {
                tableInfo.setServiceName("I" + tableInfo.getEntityName() + "Service");
            }

            if(StringUtils.isNotEmpty(this.globalConfig.getServiceImplName())) {
                tableInfo.setServiceImplName(String.format(this.globalConfig.getServiceImplName(), new Object[]{tableInfo.getEntityName()}));
            } else {
                tableInfo.setServiceImplName(tableInfo.getEntityName() + "ServiceImpl");
            }

            if(StringUtils.isNotEmpty(this.globalConfig.getControllerName())) {
                tableInfo.setControllerName(String.format(this.globalConfig.getControllerName(), new Object[]{tableInfo.getEntityName()}));
            } else {
                tableInfo.setControllerName(tableInfo.getEntityName() + "Controller");
            }

            if(StringUtils.isNotEmpty(this.globalConfig.getListName())) {
                tableInfo.setListName(String.format(this.globalConfig.getListName(), new Object[]{tableInfo.getEntityName()}));
            } else {
                tableInfo.setListName(tableInfo.getEntityName() + "_list");
            }

            if(StringUtils.isNotEmpty(this.globalConfig.getEditName())) {
                tableInfo.setEditName(String.format(this.globalConfig.getEditName(), new Object[]{tableInfo.getEntityName()}));
            } else {
                tableInfo.setEditName(tableInfo.getEntityName() + "_edit");
            }
        }

        return tableList;
    }

    private void checkTableIdTableFieldAnnotation(StrategyConfig config, TableInfo tableInfo, String[] fieldPrefix) {
        boolean importTableFieldAnnotaion = false;
        boolean importTableIdAnnotaion = false;
        Iterator i$;
        TableField tf;
        if(config.isEntityTableFieldAnnotationEnable()) {
            for(i$ = tableInfo.getFields().iterator(); i$.hasNext(); importTableIdAnnotaion = true) {
                tf = (TableField)i$.next();
                tf.setConvert(true);
                importTableFieldAnnotaion = true;
            }
        } else if(fieldPrefix != null && fieldPrefix.length != 0) {
            i$ = tableInfo.getFields().iterator();

            while(i$.hasNext()) {
                tf = (TableField)i$.next();
                if(NamingStrategy.isPrefixContained(tf.getName(), fieldPrefix)) {
                    if(tf.isKeyFlag()) {
                        importTableIdAnnotaion = true;
                    }

                    tf.setConvert(true);
                    importTableFieldAnnotaion = true;
                }
            }
        }

        if(importTableFieldAnnotaion) {
            tableInfo.getImportPackages().add(com.baomidou.mybatisplus.annotations.TableField.class.getCanonicalName());
        }

        if(importTableIdAnnotaion) {
            tableInfo.getImportPackages().add(TableId.class.getCanonicalName());
        }

        if(this.globalConfig.getIdType() != null) {
            if(!importTableIdAnnotaion) {
                tableInfo.getImportPackages().add(TableId.class.getCanonicalName());
            }

            tableInfo.getImportPackages().add(IdType.class.getCanonicalName());
        }

    }

    private List<TableInfo> getTablesInfo(StrategyConfig config) {
        boolean isInclude = null != config.getInclude() && config.getInclude().length > 0;
        boolean isExclude = null != config.getExclude() && config.getExclude().length > 0;
        if(isInclude && isExclude) {
            throw new RuntimeException("<strategy> 标签中 <include> 与 <exclude> 只能配置一项！");
        } else {
            ArrayList tableList = new ArrayList();
            ArrayList includeTableList = new ArrayList();
            ArrayList excludeTableList = new ArrayList();
            HashSet notExistTables = new HashSet();
            PreparedStatement preparedStatement = null;

            try {
                String e = this.querySQL.getTableCommentsSql();
                if(QuerySQL.POSTGRE_SQL == this.querySQL) {
                    e = String.format(e, new Object[]{this.dataSourceConfig.getSchemaname()});
                } else if(QuerySQL.ORACLE == this.querySQL) {
                    StringBuilder results;
                    String[] tableInfo;
                    int i$;
                    int ti;
                    String arr$;
                    if(isInclude) {
                        results = new StringBuilder(e);
                        results.append(" WHERE ").append(this.querySQL.getTableName()).append(" IN (");
                        tableInfo = config.getInclude();
                        i$ = tableInfo.length;

                        for(ti = 0; ti < i$; ++ti) {
                            arr$ = tableInfo[ti];
                            results.append("\'").append(arr$.toUpperCase()).append("\',");
                        }

                        results.replace(results.length() - 1, results.length(), ")");
                        e = results.toString();
                    } else if(isExclude) {
                        results = new StringBuilder(e);
                        results.append(" WHERE ").append(this.querySQL.getTableName()).append(" NOT IN (");
                        tableInfo = config.getExclude();
                        i$ = tableInfo.length;

                        for(ti = 0; ti < i$; ++ti) {
                            arr$ = tableInfo[ti];
                            results.append("\'").append(arr$.toUpperCase()).append("\',");
                        }

                        results.replace(results.length() - 1, results.length(), ")");
                        e = results.toString();
                    }
                }

                preparedStatement = this.connection.prepareStatement(e);
                ResultSet var28 = preparedStatement.executeQuery();

                while(true) {
                    while(var28.next()) {
                        String var30 = var28.getString(this.querySQL.getTableName());
                        if(StringUtils.isNotEmpty(var30)) {
                            String var32 = var28.getString(this.querySQL.getTableComment());
                            TableInfo var29 = new TableInfo();
                            var29.setName(var30);
                            var29.setComment(var32);
                            int len$;
                            int i$1;
                            String excludeTab;
                            String[] var34;
                            if(isInclude) {
                                var34 = config.getInclude();
                                len$ = var34.length;

                                for(i$1 = 0; i$1 < len$; ++i$1) {
                                    excludeTab = var34[i$1];
                                    if(excludeTab.equalsIgnoreCase(var30)) {
                                        includeTableList.add(var29);
                                    } else {
                                        notExistTables.add(excludeTab);
                                    }
                                }
                            } else if(isExclude) {
                                var34 = config.getExclude();
                                len$ = var34.length;

                                for(i$1 = 0; i$1 < len$; ++i$1) {
                                    excludeTab = var34[i$1];
                                    if(excludeTab.equalsIgnoreCase(var30)) {
                                        excludeTableList.add(var29);
                                    } else {
                                        notExistTables.add(excludeTab);
                                    }
                                }
                            }

                            tableList.add(var29);
                        } else {
                            System.err.println("当前数据库为空！！！");
                        }
                    }

                    Iterator var31 = tableList.iterator();

                    TableInfo var33;
                    while(var31.hasNext()) {
                        var33 = (TableInfo)var31.next();
                        notExistTables.remove(var33.getName());
                    }

                    if(notExistTables.size() > 0) {
                        System.err.println("表 " + notExistTables + " 在数据库中不存在！！！");
                    }

                    if(isExclude) {
                        tableList.removeAll(excludeTableList);
                        includeTableList = tableList;
                    }

                    if(!isInclude && !isExclude) {
                        includeTableList = tableList;
                    }

                    var31 = includeTableList.iterator();

                    while(var31.hasNext()) {
                        var33 = (TableInfo)var31.next();
                        this.convertTableFields(var33, config.getColumnNaming());
                    }
                    break;
                }
            } catch (SQLException var26) {
                var26.printStackTrace();
            } finally {
                try {
                    if(preparedStatement != null) {
                        preparedStatement.close();
                    }

                    if(this.connection != null) {
                        this.connection.close();
                    }
                } catch (SQLException var25) {
                    var25.printStackTrace();
                }

            }

            return this.processTable(includeTableList, config.getNaming(), config);
        }
    }

    private boolean isKeyIdentity(ResultSet results) throws SQLException {
        if(QuerySQL.MYSQL == this.querySQL) {
            String isIdentity = results.getString("Extra");
            if("auto_increment".equals(isIdentity)) {
                return true;
            }
        } else if(QuerySQL.SQL_SERVER == this.querySQL) {
            int isIdentity1 = results.getInt("isIdentity");
            return 1 == isIdentity1;
        }

        return false;
    }

    private TableInfo convertTableFields(TableInfo tableInfo, NamingStrategy strategy) {
        boolean haveId = false;
        ArrayList fieldList = new ArrayList();
        ArrayList commonFieldList = new ArrayList();

        try {
            String e = this.querySQL.getTableFieldsSql();
            if(QuerySQL.POSTGRE_SQL == this.querySQL) {
                e = String.format(e, new Object[]{this.dataSourceConfig.getSchemaname(), tableInfo.getName()});
            } else {
                e = String.format(e, new Object[]{tableInfo.getName()});
            }

            PreparedStatement preparedStatement = this.connection.prepareStatement(e);
            ResultSet results = preparedStatement.executeQuery();

            label64:
            while(true) {
                while(true) {
                    if(!results.next()) {
                        break label64;
                    }

                    TableField field = new TableField();
                    String key = results.getString(this.querySQL.getFieldKey());
                    boolean isId = StringUtils.isNotEmpty(key) && key.toUpperCase().equals("PRI");
                    if(isId && !haveId) {
                        field.setKeyFlag(true);
                        if(this.isKeyIdentity(results)) {
                            field.setKeyIdentityFlag(true);
                        }

                        haveId = true;
                    } else {
                        field.setKeyFlag(false);
                    }

                    field.setName(results.getString(this.querySQL.getFieldName()));
                    field.setType(results.getString(this.querySQL.getFieldType()));
                    field.setPropertyName(this.strategyConfig, this.processName(field.getName(), strategy));
                    field.setColumnType(this.dataSourceConfig.getTypeConvert().processTypeConvert(field.getType()));
                    field.setComment(results.getString(this.querySQL.getFieldComment()));
                    if(this.strategyConfig.includeSuperEntityColumns(field.getName())) {
                        commonFieldList.add(field);
                    } else {
                        List tableFillList = this.getStrategyConfig().getTableFillList();
                        if(null != tableFillList) {
                            Iterator i$ = tableFillList.iterator();

                            while(i$.hasNext()) {
                                TableFill tableFill = (TableFill)i$.next();
                                if(tableFill.getFieldName().equals(field.getName())) {
                                    field.setFill(tableFill.getFieldFill().name());
                                    break;
                                }
                            }
                        }

                        fieldList.add(field);
                    }
                }
            }
        } catch (SQLException var15) {
            System.err.println("SQL Exception：" + var15.getMessage());
        }

        tableInfo.setFields(fieldList);
        tableInfo.setCommonFields(commonFieldList);
        return tableInfo;
    }

    private String joinPath(String parentDir, String packageName) {
        if(StringUtils.isEmpty(parentDir)) {
            parentDir = System.getProperty("java.io.tmpdir");
        }

        if(!StringUtils.endsWith(parentDir, File.separator)) {
            parentDir = parentDir + File.separator;
        }

        packageName = packageName.replaceAll("\\.", "\\" + File.separator);
        return parentDir + packageName;
    }

    private String joinPackage(String parent, String subPackage) {
        return StringUtils.isEmpty(parent)?subPackage:parent + "." + subPackage;
    }

    private String processName(String name, NamingStrategy strategy) {
        return this.processName(name, strategy, this.strategyConfig.getFieldPrefix());
    }

    private String processName(String name, NamingStrategy strategy, String[] prefix) {
        boolean removePrefix = false;
        if(prefix != null && prefix.length >= 1) {
            removePrefix = true;
        }

        String propertyName;
        if(removePrefix) {
            if(strategy == NamingStrategy.underline_to_camel) {
                propertyName = NamingStrategy.removePrefixAndCamel(name, prefix);
            } else {
                propertyName = NamingStrategy.removePrefix(name, prefix);
            }
        } else if(strategy == NamingStrategy.underline_to_camel) {
            propertyName = NamingStrategy.underlineToCamel(name);
        } else {
            propertyName = name;
        }

        return propertyName;
    }

    private QuerySQL getQuerySQL(DbType dbType) {
        QuerySQL[] arr$ = QuerySQL.values();
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            QuerySQL qs = arr$[i$];
            if(qs.getDbType().equals(dbType.getValue())) {
                return qs;
            }
        }

        return QuerySQL.MYSQL;
    }

    public StrategyConfig getStrategyConfig() {
        return this.strategyConfig;
    }

    public ConfigBuilder setStrategyConfig(StrategyConfig strategyConfig) {
        this.strategyConfig = strategyConfig;
        return this;
    }

    public GlobalConfig getGlobalConfig() {
        return this.globalConfig;
    }

    public ConfigBuilder setGlobalConfig(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
        return this;
    }

    public InjectionConfig getInjectionConfig() {
        return this.injectionConfig;
    }

    public ConfigBuilder setInjectionConfig(InjectionConfig injectionConfig) {
        this.injectionConfig = injectionConfig;
        return this;
    }
}
