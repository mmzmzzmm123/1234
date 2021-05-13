package com.ruoyi.jdgl.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 基地管理活动管理对象 tsbz_jdglhdgl
 * 
 * @author tsbz
 * @date 2021-05-13
 */
public class TsbzJdglhdgl extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 基地编号 */
    @Excel(name = "基地编号")
    private Long jdid;

    /** 活动名称 */
    @Excel(name = "活动名称")
    private String name;

    /** 活动类型 */
    @Excel(name = "活动类型")
    private String hdtype;

    /** 活动时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "活动时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date hdtime;

    /** 活动地点 */
    @Excel(name = "活动地点")
    private String hddd;

    /** 上传路径 */
    @Excel(name = "上传路径")
    private String scpath;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createUserid;

    private Date createTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setJdid(Long jdid) 
    {
        this.jdid = jdid;
    }

    public Long getJdid() 
    {
        return jdid;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setHdtype(String hdtype) 
    {
        this.hdtype = hdtype;
    }

    public String getHdtype() 
    {
        return hdtype;
    }
    public void setHdtime(Date hdtime) 
    {
        this.hdtime = hdtime;
    }

    public Date getHdtime() 
    {
        return hdtime;
    }
    public void setHddd(String hddd) 
    {
        this.hddd = hddd;
    }

    public String getHddd() 
    {
        return hddd;
    }
    public void setScpath(String scpath) 
    {
        this.scpath = scpath;
    }

    public String getScpath() 
    {
        return scpath;
    }
    public void setCreateUserid(Long createUserid) 
    {
        this.createUserid = createUserid;
    }

    public Long getCreateUserid() 
    {
        return createUserid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("jdid", getJdid())
            .append("name", getName())
            .append("hdtype", getHdtype())
            .append("hdtime", getHdtime())
            .append("hddd", getHddd())
            .append("scpath", getScpath())
            .append("createUserid", getCreateUserid())
            .append("createTime", getCreateTime())
            .toString();
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
