package com.ruoyi.project.monitor.domain;

/**
 * teemlink配置的表单列
 */
public class TeemLinkColumn {
    /**
     * 中文名称
     */
    private String chineseName;
    /**
     * 数据库名称
     */
    private String dbName;
    /**
     * 字段类型
     * 字符、日期、数值、字典
     */
    private String fieldType;
    /**
     * 字典类型
     */
    private String dictType;

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    @Override
    public String toString() {
        return "TeemLinkColumn{" +
                "chineseName='" + chineseName + '\'' +
                ", dbName='" + dbName + '\'' +
                ", fieldType='" + fieldType + '\'' +
                ", dictType='" + dictType + '\'' +
                '}';
    }
}
