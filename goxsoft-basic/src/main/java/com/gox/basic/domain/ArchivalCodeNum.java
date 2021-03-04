package com.gox.basic.domain;

import com.gox.common.core.domain.BaseEntity;
import com.gox.common.plugin.AutoId;

public class ArchivalCodeNum extends BaseEntity {
    @AutoId
    private Long id;
    private int num;
    private String archival;
    private String comment;
    public ArchivalCodeNum(){}
    public ArchivalCodeNum(int num, String archival) {
        this.num = num;
        this.archival = archival;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getArchival() {
        return archival;
    }

    public void setArchival(String archival) {
        this.archival = archival;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
