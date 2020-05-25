package com.ruoyi.project.benyi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 培训对象 by_train_video
 * 
 * @author tsbz
 * @date 2020-05-25
 */
public class ByTrainVideo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 培训视频标题 */
    @Excel(name = "培训视频标题")
    private String title;

    /** 简介 */
    @Excel(name = "简介")
    private String information;

    /** 讲师 */
    @Excel(name = "讲师")
    private String lecturer;

    /** 视频路径 */
    @Excel(name = "视频路径")
    private String videourl;

    /** 所属类别 */
    @Excel(name = "所属类别")
    private String type;

    /** 适用班级 */
    @Excel(name = "适用班级")
    private String classtype;

    /** 上传人员 */
    @Excel(name = "上传人员")
    private Long createuserid;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

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
    public void setInformation(String information) 
    {
        this.information = information;
    }

    public String getInformation() 
    {
        return information;
    }
    public void setLecturer(String lecturer) 
    {
        this.lecturer = lecturer;
    }

    public String getLecturer() 
    {
        return lecturer;
    }
    public void setVideourl(String videourl) 
    {
        this.videourl = videourl;
    }

    public String getVideourl() 
    {
        return videourl;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setClasstype(String classtype) 
    {
        this.classtype = classtype;
    }

    public String getClasstype() 
    {
        return classtype;
    }
    public void setCreateuserid(Long createuserid) 
    {
        this.createuserid = createuserid;
    }

    public Long getCreateuserid() 
    {
        return createuserid;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("information", getInformation())
            .append("lecturer", getLecturer())
            .append("videourl", getVideourl())
            .append("type", getType())
            .append("classtype", getClasstype())
            .append("createuserid", getCreateuserid())
            .append("createtime", getCreatetime())
            .toString();
    }
}
