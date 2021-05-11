package com.ruoyi.jdgl.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 基地管理制度建设对象 tsbz_jdglzdjs
 * 
 * @author tsbz
 * @date 2021-05-11
 */
public class TsbzJdglzdjs extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 基地编号 */
    @Excel(name = "基地编号")
    private Long jdid;

    /** 信息名称 */
    @Excel(name = "信息名称")
    private String name;

    /** 任务类型 */
    @Excel(name = "任务类型")
    private String rwtype;

    /** 上传时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sctime;

    /** 上传路径 */
    @Excel(name = "上传路径")
    private String scpath;

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
    public void setRwtype(String rwtype) 
    {
        this.rwtype = rwtype;
    }

    public String getRwtype() 
    {
        return rwtype;
    }
    public void setSctime(Date sctime) 
    {
        this.sctime = sctime;
    }

    public Date getSctime() 
    {
        return sctime;
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
            .append("rwtype", getRwtype())
            .append("sctime", getSctime())
            .append("scpath", getScpath())
            .append("createUserid", getCreateUserid())
            .append("createTime", getCreateTime())
            .toString();
    }
}
