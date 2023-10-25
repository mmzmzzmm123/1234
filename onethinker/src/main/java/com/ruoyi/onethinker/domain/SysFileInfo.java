package com.ruoyi.onethinker.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 文件信息对象 sys_file_info
 *
 * @author yangyouqi
 * @date 2023-10-25
 */
public class SysFileInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    public static final Integer SOURCE_TYPE_LOCALHOST = 1;
    public static final Integer SOURCE_TYPE_SERVICE = 2;
    public static final Integer SOURCE_TYPE_TX = 3;

    /**
     * 文件id
     */
    private String fileId;

    /**
     * 文件名称
     */
    @Excel(name = "文件名称")
    private String fileName;

    /**
     * 服务器文件id
     */
    @Excel(name = "服务器文件id")
    private String serverFileId;

    /**
     * 文件后缀名
     */
    @Excel(name = "文件后缀名")
    private String suffixName;

    /**
     * 文件大小，单位：Byte
     */
    @Excel(name = "文件大小，单位：Byte")
    private Long size;

    /**
     * 上传人
     */
    @Excel(name = "上传人")
    private Long createUserId;

    /**
     * 上传来源 1: 服务器上传 2:本地上传 3:腾讯云服务
     */
    @Excel(name = "上传来源 1: 服务器上传 2:本地上传 3:腾讯云服务")
    private Integer source;

    /**
     * 文件访问地址
     */
    @Excel(name = "文件访问地址")
    private String fileUrl;

    /**
     * 文件类型 1：图片 2：视频
     */
    @Excel(name = "文件类型 1：图片 2：视频")
    private Integer fileType;

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setServerFileId(String serverFileId) {
        this.serverFileId = serverFileId;
    }

    public String getServerFileId() {
        return serverFileId;
    }

    public void setSuffixName(String suffixName) {
        this.suffixName = suffixName;
    }

    public String getSuffixName() {
        return suffixName;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getSize() {
        return size;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getSource() {
        return source;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public Integer getFileType() {
        return fileType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("fileId", getFileId())
                .append("fileName", getFileName())
                .append("serverFileId", getServerFileId())
                .append("suffixName", getSuffixName())
                .append("size", getSize())
                .append("createTime", getCreateTime())
                .append("createUserId", getCreateUserId())
                .append("source", getSource())
                .append("fileUrl", getFileUrl())
                .append("fileType", getFileType())
                .toString();
    }
}
