package com.baomidou.mybatisplus.generator.config;

public class TemplateConfig {
    private String entity = "/templates/entity.java";
    private String service = "/templates/service.java";
    private String serviceImpl = "/templates/serviceImpl.java";
    private String mapper = "/templates/mapper.java";
    private String xml = "/templates/mapper.xml";
    private String controller = "/templates/controller.java";
    private String list = "/templates/list";
    private String edit = "/templates/edit";

    public TemplateConfig() {
    }

    public String getEntity(boolean kotlin) {
        return kotlin?"/templates/entity.kt":this.entity;
    }

    public TemplateConfig setEntity(String entity) {
        this.entity = entity;
        return this;
    }

    public String getService() {
        return this.service;
    }

    public TemplateConfig setService(String service) {
        this.service = service;
        return this;
    }

    public String getServiceImpl() {
        return this.serviceImpl;
    }

    public TemplateConfig setServiceImpl(String serviceImpl) {
        this.serviceImpl = serviceImpl;
        return this;
    }

    public String getMapper() {
        return this.mapper;
    }

    public TemplateConfig setMapper(String mapper) {
        this.mapper = mapper;
        return this;
    }

    public String getXml() {
        return this.xml;
    }

    public TemplateConfig setXml(String xml) {
        this.xml = xml;
        return this;
    }

    public String getController() {
        return this.controller;
    }

    public TemplateConfig setController(String controller) {
        this.controller = controller;
        return this;
    }

    public String getList() {
        return list;
    }

    public TemplateConfig setList(String list) {
        this.list = list;
        return this;
    }

    public String getEdit() {
        return edit;
    }

    public TemplateConfig setEdit(String edit) {
        this.edit = edit;
        return this;
    }
}

