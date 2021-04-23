package com.ruoyi.zcrpsgl.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 主持人基地对象 tsbz_zcrjdcj
 * 
 * @author tsbz
 * @date 2021-04-23
 */
public class TsbzZcrjdcj extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 基地名称 */
    @Excel(name = "基地名称")
    private String name;

    /** 主持人id */
    @Excel(name = "主持人id")
    private Long zcrid;

    /** 基地类型 */
    @Excel(name = "基地类型")
    private String jdtype;

    /** 当前报名状态 */
    @Excel(name = "当前报名状态")
    private String status;

    /** 招生名额 */
    @Excel(name = "招生名额")
    private Integer zsme;

    /** 报名开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "报名开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date starttime;

    /** 报名结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "报名结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endtime;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createUserid;

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
    public void setZcrid(Long zcrid) 
    {
        this.zcrid = zcrid;
    }

    public Long getZcrid() 
    {
        return zcrid;
    }
    public void setJdtype(String jdtype) 
    {
        this.jdtype = jdtype;
    }

    public String getJdtype() 
    {
        return jdtype;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setZsme(Integer zsme) 
    {
        this.zsme = zsme;
    }

    public Integer getZsme() 
    {
        return zsme;
    }
    public void setStarttime(Date starttime) 
    {
        this.starttime = starttime;
    }

    public Date getStarttime() 
    {
        return starttime;
    }
    public void setEndtime(Date endtime) 
    {
        this.endtime = endtime;
    }

    public Date getEndtime() 
    {
        return endtime;
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
            .append("name", getName())
            .append("zcrid", getZcrid())
            .append("jdtype", getJdtype())
            .append("status", getStatus())
            .append("zsme", getZsme())
            .append("starttime", getStarttime())
            .append("endtime", getEndtime())
            .append("createUserid", getCreateUserid())
            .append("createTime", getCreateTime())
            .toString();
    }
}
