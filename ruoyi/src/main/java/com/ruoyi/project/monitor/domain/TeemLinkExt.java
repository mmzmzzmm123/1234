package com.ruoyi.project.monitor.domain;

/**
 * 展示用的model
 * @author lihe
 */
public class TeemLinkExt {
    private String tableChineseName;
    private String tableDbName;
    private String columnChineseName;
    private String columnDbName;
    private String fieldType;
    private String dictType;
    private Float stat;

    public String getTableChineseName() {
        return tableChineseName;
    }

    public void setTableChineseName(String tableChineseName) {
        this.tableChineseName = tableChineseName;
    }

    public String getTableDbName() {
        return tableDbName;
    }

    public void setTableDbName(String tableDbName) {
        this.tableDbName = tableDbName;
    }

    public String getColumnChineseName() {
        return columnChineseName;
    }

    public void setColumnChineseName(String columnChineseName) {
        this.columnChineseName = columnChineseName;
    }

    public String getColumnDbName() {
        return columnDbName;
    }

    public void setColumnDbName(String columnDbName) {
        this.columnDbName = columnDbName;
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

    public Float getStat() {
        return stat;
    }

    public void setStat(Float stat) {
        this.stat = stat;
    }
}
