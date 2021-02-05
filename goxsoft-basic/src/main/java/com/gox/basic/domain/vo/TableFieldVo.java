package com.gox.basic.domain.vo;

public class TableFieldVo {
    private Long id;
    private boolean isTableFiled;
    private Integer tableFieldSort;

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
