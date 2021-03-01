package com.gox.basic.domain.form;

import com.alibaba.fastjson.annotation.JSONField;
import com.gox.common.plugin.AutoId;

import java.io.Serializable;

public class ChildrenItem implements Serializable {
    @AutoId
    @JSONField(name = "id")
    private int id;

    @JSONField(name = "label")
    private String label;

    @JSONField(name = "value")
    private int value;

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public int getValue() {
        return value;
    }
}