package com.ruoyi.project.benyi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 讲师对象 by_lecturer
 * 
 * @author tsbz
 * @date 2020-05-25
 */
public class ByLecturer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 简介 */
    @Excel(name = "简介")
    private String information;

    /** 照片 */
    @Excel(name = "照片")
    private String imgurl;

    /** 创建人 */
    @Excel(name = "创建人")
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
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setInformation(String information) 
    {
        this.information = information;
    }

    public String getInformation() 
    {
        return information;
    }
    public void setImgurl(String imgurl) 
    {
        this.imgurl = imgurl;
    }

    public String getImgurl() 
    {
        return imgurl;
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
            .append("name", getName())
            .append("information", getInformation())
            .append("imgurl", getImgurl())
            .append("createuserid", getCreateuserid())
            .append("createtime", getCreatetime())
            .toString();
    }
}
