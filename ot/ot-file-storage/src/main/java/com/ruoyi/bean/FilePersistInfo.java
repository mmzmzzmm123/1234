package com.ruoyi.bean;

import lombok.Data;

/**
 * @author yangyouqi
 * @date 2024/5/15
 */
@Data
public class FilePersistInfo {
    private FileInfo fileInfo;
    private String relationType;
    private String relationTarget;

    public FilePersistInfo() {
    }

    public FilePersistInfo(FileInfo fileInfo, String relationType, String relationTarget) {
        this.fileInfo = fileInfo;
        this.relationType = relationType;
        this.relationTarget = relationTarget;
    }
}
