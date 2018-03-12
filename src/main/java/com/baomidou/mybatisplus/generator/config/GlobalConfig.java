package com.baomidou.mybatisplus.generator.config;

import com.baomidou.mybatisplus.enums.IdType;

public class GlobalConfig {
    private String outputDir = "D://";
    private boolean fileOverride = false;
    private boolean open = true;
    private boolean enableCache = true;
    private String author;
    private boolean kotlin = false;
    private boolean activeRecord = true;
    private boolean baseResultMap = false;
    private boolean baseColumnList = false;
    private String mapperName;
    private String xmlName;
    private String serviceName;
    private String serviceImplName;
    private String controllerName;
    private String listName;
    private String editName;
    private String viewSuffix=".ftl";
    private IdType idType;

    public GlobalConfig() {
    }

    public GlobalConfig setIdType(IdType idType) {
        this.idType = idType;
        return this;
    }

    public IdType getIdType() {
        return this.idType;
    }

    public String getOutputDir() {
        return this.outputDir;
    }

    public GlobalConfig setOutputDir(String outputDir) {
        this.outputDir = outputDir;
        return this;
    }

    public boolean isFileOverride() {
        return this.fileOverride;
    }

    public GlobalConfig setFileOverride(boolean fileOverride) {
        this.fileOverride = fileOverride;
        return this;
    }

    public boolean isOpen() {
        return this.open;
    }

    public GlobalConfig setOpen(boolean open) {
        this.open = open;
        return this;
    }

    public boolean isEnableCache() {
        return this.enableCache;
    }

    public GlobalConfig setEnableCache(boolean enableCache) {
        this.enableCache = enableCache;
        return this;
    }

    public String getAuthor() {
        return this.author;
    }

    public GlobalConfig setAuthor(String author) {
        this.author = author;
        return this;
    }

    public boolean isKotlin() {
        return this.kotlin;
    }

    public GlobalConfig setKotlin(boolean kotlin) {
        this.kotlin = kotlin;
        return this;
    }

    public boolean isActiveRecord() {
        return this.activeRecord;
    }

    public GlobalConfig setActiveRecord(boolean activeRecord) {
        this.activeRecord = activeRecord;
        return this;
    }

    public boolean isBaseResultMap() {
        return this.baseResultMap;
    }

    public GlobalConfig setBaseResultMap(boolean baseResultMap) {
        this.baseResultMap = baseResultMap;
        return this;
    }

    public boolean isBaseColumnList() {
        return this.baseColumnList;
    }

    public GlobalConfig setBaseColumnList(boolean baseColumnList) {
        this.baseColumnList = baseColumnList;
        return this;
    }

    public String getMapperName() {
        return this.mapperName;
    }

    public GlobalConfig setMapperName(String mapperName) {
        this.mapperName = mapperName;
        return this;
    }

    public String getXmlName() {
        return this.xmlName;
    }

    public GlobalConfig setXmlName(String xmlName) {
        this.xmlName = xmlName;
        return this;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public GlobalConfig setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getServiceImplName() {
        return this.serviceImplName;
    }

    public GlobalConfig setServiceImplName(String serviceImplName) {
        this.serviceImplName = serviceImplName;
        return this;
    }

    public String getControllerName() {
        return this.controllerName;
    }

    public GlobalConfig setControllerName(String controllerName) {
        this.controllerName = controllerName;
        return this;
    }

    public String getListName() {
        return listName;
    }

    public GlobalConfig setListName(String listName) {
        this.listName = listName;
        return this;
    }

    public String getEditName() {
        return editName;
    }

    public GlobalConfig setEditName(String editName) {
        this.editName = editName;
        return this;
    }

    public String getViewSuffix() {
        return viewSuffix;
    }

    public GlobalConfig setViewSuffix(String viewSuffix) {
        this.viewSuffix = viewSuffix;
        return this;
    }
}

