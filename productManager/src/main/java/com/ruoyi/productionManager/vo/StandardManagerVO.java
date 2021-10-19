package com.ruoyi.productionManager.vo;

import com.ruoyi.productionManager.domain.StandardInfo;

public class StandardManagerVO extends StandardInfo {
    private static final long serialVersionUID = -5124848255962397905L;

    private String path;

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "StandardManagerVO{" +
                "path='" + path + '\'' +
                '}';
    }
}
