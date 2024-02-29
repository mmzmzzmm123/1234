package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 出口国家政策对象 vehicle_policy
 * 
 * @author carol
 * @date 2024-02-29
 */
public class VehiclePolicy extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 政策id */
    private Long id;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 图片 */
    @Excel(name = "图片")
    private String pictureUrl;

    /** 文件 */
    @Excel(name = "文件")
    private String fileUrl;

    /** 国家id */
    @Excel(name = "国家id")
    private Long region;

    /** 发布日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date pushDate;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setPictureUrl(String pictureUrl) 
    {
        this.pictureUrl = pictureUrl;
    }

    public String getPictureUrl() 
    {
        return pictureUrl;
    }
    public void setFileUrl(String fileUrl) 
    {
        this.fileUrl = fileUrl;
    }

    public String getFileUrl() 
    {
        return fileUrl;
    }
    public void setRegion(Long region) 
    {
        this.region = region;
    }

    public Long getRegion() 
    {
        return region;
    }
    public void setPushDate(Date pushDate) 
    {
        this.pushDate = pushDate;
    }

    public Date getPushDate() 
    {
        return pushDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("pictureUrl", getPictureUrl())
            .append("fileUrl", getFileUrl())
            .append("region", getRegion())
            .append("pushDate", getPushDate())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
