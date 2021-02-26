package com.gox.basic.domain.vo;


import java.io.Serializable;

public class TableFieldVo implements Serializable {
    private Long id;
    private Boolean flag;
    private Integer tableFieldSort;
    private String tableFieldName;
    private Integer tableTitleWidth;
    private String vModel;

    public String getvModel() {
        return vModel;
    }

    public void setvModel(String vModel) {
        this.vModel = vModel;
    }

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

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Integer getTableFieldSort() {
        return tableFieldSort;
    }

    public void setTableFieldSort(Integer tableFieldSort) {
        this.tableFieldSort = tableFieldSort;
    }
}
