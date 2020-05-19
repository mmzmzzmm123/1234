package com.ruoyi.project.benyi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 一日流程标准对象 by_day_flow_standard
 * 
 * @author tsbz
 * @date 2020-05-18
 */
public class ByDayFlowStandard extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 标识 */
    private Long sid;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskLable;

    /** 标准的名称 */
    @Excel(name = "标准的名称")
    private String sname;

    /** 标准的内容 */
    @Excel(name = "标准的内容")
    private String standardContent;

    /** 标准的解读 */
    @Excel(name = "标准的解读")
    private String standardJiedu;

    /** 标准排序 */
    @Excel(name = "标准排序")
    private Long standardSort;

    /** 照片 */
    @Excel(name = "照片")
    private String picture;

    /** 视频 */
    @Excel(name = "视频")
    private String video;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createuser;

    /** 创建时间 */
    @Excel(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    /** 更新者 */
    @Excel(name = "更新者")
    private Long updateuser;

    /** 更新时间 */
    @Excel(name = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatetime;

    /** 备用字段 */
    @Excel(name = "备用字段")
    private Long beiyong;

    public void setSid(Long sid) 
    {
        this.sid = sid;
    }

    public Long getSid() 
    {
        return sid;
    }
    public void setTaskLable(String taskLable) 
    {
        this.taskLable = taskLable;
    }

    public String getTaskLable() 
    {
        return taskLable;
    }
    public void setSname(String sname) 
    {
        this.sname = sname;
    }

    public String getSname() 
    {
        return sname;
    }
    public void setStandardContent(String standardContent) 
    {
        this.standardContent = standardContent;
    }

    public String getStandardContent() 
    {
        return standardContent;
    }
    public void setStandardJiedu(String standardJiedu) 
    {
        this.standardJiedu = standardJiedu;
    }

    public String getStandardJiedu() 
    {
        return standardJiedu;
    }
    public void setStandardSort(Long standardSort) 
    {
        this.standardSort = standardSort;
    }

    public Long getStandardSort() 
    {
        return standardSort;
    }
    public void setPicture(String picture) 
    {
        this.picture = picture;
    }

    public String getPicture() 
    {
        return picture;
    }
    public void setVideo(String video) 
    {
        this.video = video;
    }

    public String getVideo() 
    {
        return video;
    }
    public void setCreateuser(Long createuser) 
    {
        this.createuser = createuser;
    }

    public Long getCreateuser() 
    {
        return createuser;
    }
    public void setUpdateuser(Long updateuser) 
    {
        this.updateuser = updateuser;
    }

    public Long getUpdateuser() 
    {
        return updateuser;
    }
    public void setBeiyong(Long beiyong) 
    {
        this.beiyong = beiyong;
    }

    public Long getBeiyong() 
    {
        return beiyong;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("sid", getSid())
            .append("taskLable", getTaskLable())
            .append("sname", getSname())
            .append("standardContent", getStandardContent())
            .append("standardJiedu", getStandardJiedu())
            .append("standardSort", getStandardSort())
            .append("picture", getPicture())
            .append("video", getVideo())
            .append("createuser", getCreateuser())
            .append("createtime", getCreatetime())
            .append("updateuser", getUpdateuser())
            .append("updatetime", getUpdatetime())
            .append("beiyong", getBeiyong())
            .toString();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}
