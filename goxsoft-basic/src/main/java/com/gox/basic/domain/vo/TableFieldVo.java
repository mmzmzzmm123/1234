package com.gox.basic.domain.vo;

import java.io.Serializable;

public class TableFieldVo implements Serializable {
    private Long id;
    private boolean isTableFiled;
    private Integer tableFieldSort;
    private String tableFieldName;
    private Integer tableTitleWidth;

    public Integer getTableTitleWidth() {
        return tableTitleWidth;
    }

    public void setTableTitleWidth(Integer tableTitleWidth) {
        this.tableTitleWidth = tableTitleWidth;
    }

    public String getTableFieldName() {

        return tableFieldName;
    }

    public void setTableFieldName(String tableFieldName) {
        this.tableFieldName = tableFieldName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isTableFiled() {
        return isTableFiled;
    }

    public void setTableFiled(boolean tableFiled) {
        isTableFiled = tableFiled;
    }

    public Integer getTableFieldSort() {
        return tableFieldSort;
    }

    public void setTableFieldSort(Integer tableFieldSort) {
        this.tableFieldSort = tableFieldSort;
    }
}
