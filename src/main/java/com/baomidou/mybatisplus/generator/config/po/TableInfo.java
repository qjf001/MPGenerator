package com.baomidou.mybatisplus.generator.config.po;


import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.toolkit.StringUtils;

import java.util.*;

public class TableInfo {
    private boolean convert;
    private String name;
    private String comment;
    private String entityName;
    private String mapperName;
    private String xmlName;
    private String serviceName;
    private String serviceImplName;
    private String controllerName;
    private String listName;
    private String editName;
    private List<TableField> fields;
    private List<TableField> commonFields;
    private List<String> importPackages = new ArrayList();
    private String fieldNames;

    public TableInfo() {
    }

    public boolean isConvert() {
        return this.convert;
    }

    protected void setConvert(StrategyConfig strategyConfig) {
        if (strategyConfig.containsTablePrefix(this.name)) {
            this.convert = true;
        } else if (strategyConfig.isCapitalModeNaming(this.name)) {
            this.convert = false;
        } else if (StrategyConfig.DB_COLUMN_UNDERLINE) {
            if (StringUtils.containsUpperCase(this.name)) {
                this.convert = true;
            }
        } else if (!this.entityName.equalsIgnoreCase(this.name)) {
            this.convert = true;
        }

    }

    public void setConvert(boolean convert) {
        this.convert = convert;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEntityPath() {
        StringBuilder ep = new StringBuilder();
        ep.append(this.entityName.substring(0, 1).toLowerCase());
        ep.append(this.entityName.substring(1));
        return ep.toString();
    }

    public String getEntityName() {
        return this.entityName;
    }

    public void setEntityName(StrategyConfig strategyConfig, String entityName) {
        this.entityName = entityName;
        this.setConvert(strategyConfig);
    }

    public String getMapperName() {
        return this.mapperName;
    }

    public void setMapperName(String mapperName) {
        this.mapperName = mapperName;
    }

    public String getXmlName() {
        return this.xmlName;
    }

    public void setXmlName(String xmlName) {
        this.xmlName = xmlName;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceImplName() {
        return this.serviceImplName;
    }

    public void setServiceImplName(String serviceImplName) {
        this.serviceImplName = serviceImplName;
    }

    public String getControllerName() {
        return this.controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public List<TableField> getFields() {
        return this.fields;
    }

    public void setFields(List<TableField> fields) {
        if (CollectionUtils.isNotEmpty(fields)) {
            this.fields = fields;
            HashSet pkgSet = new HashSet();
            Iterator i$ = fields.iterator();

            while (i$.hasNext()) {
                TableField field = (TableField) i$.next();
                if (null != field.getColumnType() && null != field.getColumnType().getPkg()) {
                    pkgSet.add(field.getColumnType().getPkg());
                }

                if (!field.isKeyFlag()) {
                    if (field.isConvert()) {
                        pkgSet.add("com.baomidou.mybatisplus.annotations.TableField");
                    }
                } else {
                    if (field.isConvert() || field.isKeyIdentityFlag()) {
                        pkgSet.add("com.baomidou.mybatisplus.annotations.TableId");
                    }

                    if (field.isKeyIdentityFlag()) {
                        pkgSet.add("com.baomidou.mybatisplus.enums.IdType");
                    }
                }

                if (null != field.getFill()) {
                    pkgSet.add("com.baomidou.mybatisplus.annotations.TableField");
                    pkgSet.add("com.baomidou.mybatisplus.enums.FieldFill");
                }
            }

            if (!pkgSet.isEmpty()) {
                this.importPackages = new ArrayList(Arrays.asList(pkgSet.toArray(new String[0])));
            }
        }

    }

    public List<TableField> getCommonFields() {
        return this.commonFields;
    }

    public void setCommonFields(List<TableField> commonFields) {
        this.commonFields = commonFields;
    }

    public List<String> getImportPackages() {
        return this.importPackages;
    }

    public void setImportPackages(String pkg) {
        this.importPackages.add(pkg);
    }

    public boolean isLogicDelete(String logicDeletePropertyName) {
        Iterator i$ = this.fields.iterator();

        TableField tableField;
        do {
            if (!i$.hasNext()) {
                return false;
            }

            tableField = (TableField) i$.next();
        } while (!tableField.getName().equals(logicDeletePropertyName));

        return true;
    }

    public String getFieldNames() {
        if (StringUtils.isEmpty(this.fieldNames)) {
            StringBuilder names = new StringBuilder();

            for (int i = 0; i < this.fields.size(); ++i) {
                TableField fd = (TableField) this.fields.get(i);
                if (i == this.fields.size() - 1) {
                    names.append(this.cov2col(fd));
                } else {
                    names.append(this.cov2col(fd)).append(", ");
                }
            }

            this.fieldNames = names.toString();
        }

        return this.fieldNames;
    }

    private String cov2col(TableField field) {
        return null != field ? (field.isConvert() ? field.getName() + " AS " + field.getPropertyName() : field.getName()) : "";
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getEditName() {
        return editName;
    }

    public void setEditName(String editName) {
        this.editName = editName;
    }
}
