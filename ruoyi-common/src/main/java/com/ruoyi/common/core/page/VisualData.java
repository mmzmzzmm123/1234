package com.ruoyi.common.core.page;

import java.awt.*;
import java.io.Serializable;
import java.util.List;

public class VisualData implements Serializable {

    private static final long serialVersionUID = 1L;

    {
        this.hitCount = false;
        this.searchCount = true;
//        this.orders =
    }
    private Integer current;
    private Boolean hitCount;

    private List<?> orders;

    private Integer pages;
    private List<?> records;
    private Boolean searchCount;
    private Long total;

    public VisualData() {
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Boolean getHitCount() {
        return hitCount;
    }

    public void setHitCount(Boolean hitCount) {
        this.hitCount = hitCount;
    }

    public List<?> getOrders() {
        return orders;
    }

    public void setOrders(List<?> orders) {
        this.orders = orders;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public List<?> getRecords() {
        return records;
    }

    public void setRecords(List<?> records) {
        this.records = records;
    }

    public Boolean getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(Boolean searchCount) {
        this.searchCount = searchCount;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
