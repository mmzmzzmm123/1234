package com.gox.basic.domain.vo;

import java.io.Serializable;

public class ImportFieldMap implements Serializable {
    private String src;
    private String dest;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }
}
