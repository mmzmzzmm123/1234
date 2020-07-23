package com.ruoyi.project.monitor.domain;

import java.util.List;

/**
 * teemlink配置的表单
 *
 * @author lihe
 */
public class TeemLinkTable {
    private String chineseName;
    private String dbName;
    private List<TeemLinkColumn> columns;

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

    public List<TeemLinkColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<TeemLinkColumn> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "TeemLinkTable{" +
                "chineseName='" + chineseName + '\'' +
                ", dbName='" + dbName + '\'' +
                ", columns=" + columns +
                '}';
    }
}

