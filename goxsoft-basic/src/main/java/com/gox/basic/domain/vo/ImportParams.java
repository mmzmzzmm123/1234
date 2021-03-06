package com.gox.basic.domain.vo;

import java.io.Serializable;
import java.util.List;

public class ImportParams implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long deptId;
    private Long nodeId;
    private Long parentId;
    private String filename;
    private List<ImportFieldMap> list;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public List<ImportFieldMap> getList() {
        return list;
    }

    public void setList(List<ImportFieldMap> list) {
        this.list = list;
    }
}
