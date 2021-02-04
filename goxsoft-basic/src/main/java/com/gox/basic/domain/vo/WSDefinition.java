package com.gox.basic.domain.vo;


import java.io.Serializable;

public class WSDefinition implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String nameCn;
    private String definition;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
