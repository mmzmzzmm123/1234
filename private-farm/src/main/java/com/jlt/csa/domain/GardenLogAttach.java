package com.jlt.csa.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 菜地日志附件对象 csa_garden_log_attach
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
public class GardenLogAttach extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 附件id */
    private Long id;

    /** 菜地日志 */
    private Long logId;

    /** 附件类型 */
    @Excel(name = "附件类型")
    private String attachType;

    /** 文件名 */
    @Excel(name = "文件名")
    private String filename;

    /** URL */
    @Excel(name = "URL")
    private String url;

    /** 附件宽度 */
    @Excel(name = "附件宽度")
    private Long width;

    /** 附件高度 */
    @Excel(name = "附件高度")
    private Long height;

    /** 字节数 */
    @Excel(name = "字节数")
    private Long bytes;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date publishTime;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setLogId(Long logId) 
    {
        this.logId = logId;
    }

    public Long getLogId() 
    {
        return logId;
    }
    public void setAttachType(String attachType) 
    {
        this.attachType = attachType;
    }

    public String getAttachType() 
    {
        return attachType;
    }
    public void setFilename(String filename) 
    {
        this.filename = filename;
    }

    public String getFilename() 
    {
        return filename;
    }
    public void setUrl(String url) 
    {
        this.url = url;
    }

    public String getUrl() 
    {
        return url;
    }
    public void setWidth(Long width) 
    {
        this.width = width;
    }

    public Long getWidth() 
    {
        return width;
    }
    public void setHeight(Long height) 
    {
        this.height = height;
    }

    public Long getHeight() 
    {
        return height;
    }
    public void setBytes(Long bytes) 
    {
        this.bytes = bytes;
    }

    public Long getBytes() 
    {
        return bytes;
    }
    public void setPublishTime(Date publishTime) 
    {
        this.publishTime = publishTime;
    }

    public Date getPublishTime() 
    {
        return publishTime;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("logId", getLogId())
            .append("attachType", getAttachType())
            .append("filename", getFilename())
            .append("url", getUrl())
            .append("width", getWidth())
            .append("height", getHeight())
            .append("bytes", getBytes())
            .append("publishTime", getPublishTime())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
