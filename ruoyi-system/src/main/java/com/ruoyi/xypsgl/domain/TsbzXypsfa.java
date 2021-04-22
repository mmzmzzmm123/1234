package com.ruoyi.xypsgl.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学员评审方案对象 tsbz_xypsfa
 * 
 * @author tsbz
 * @date 2021-04-22
 */
public class TsbzXypsfa extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 评审方案名称 */
    @Excel(name = "评审方案名称")
    private String name;

    /** 评审方案状态 */
    @Excel(name = "评审方案状态")
    private String status;

    /** 基地类型 */
    @Excel(name = "基地类型")
    private String jdtype;

    /** 报名时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "报名时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date starttime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
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
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setJdtype(String jdtype) 
    {
        this.jdtype = jdtype;
    }

    public String getJdtype() 
    {
        return jdtype;
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
            .append("status", getStatus())
            .append("jdtype", getJdtype())
            .append("starttime", getStarttime())
            .append("endtime", getEndtime())
            .append("createUserid", getCreateUserid())
            .append("createTime", getCreateTime())
            .toString();
    }
}
