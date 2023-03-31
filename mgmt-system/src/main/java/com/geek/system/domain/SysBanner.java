package com.geek.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.geek.common.annotation.Excel;
import com.geek.common.core.domain.BaseEntity;

/**
 * banner配置对象 sys_banner
 * 
 * @author xuek
 * @date 2023-03-30
 */
public class SysBanner extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** appid */
    @Excel(name = "appid")
    private String appid;

    /** 广告图片地址 */
    @Excel(name = "广告图片地址")
    private String picUrl;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    /** 修改时间 */
    private Date modifyTime;

    /** 修改人 */
    private String modifyBy;

    public void setId(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setAppid(String appid){
        this.appid = appid;
    }

    public String getAppid() {
        return appid;
    }
    public void setPicUrl(String picUrl){
        this.picUrl = picUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    public void setStatus(Long status){
        this.status = status;
    }

    public Long getStatus() {
        return status;
    }
    public void setModifyTime(Date modifyTime){
        this.modifyTime = modifyTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }
    public void setModifyBy(String modifyBy){
        this.modifyBy = modifyBy;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("appid", getAppid())
            .append("picUrl", getPicUrl())
            .append("title", getTitle())
            .append("remark", getRemark())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("modifyTime", getModifyTime())
            .append("modifyBy", getModifyBy())
            .toString();
    }
}
