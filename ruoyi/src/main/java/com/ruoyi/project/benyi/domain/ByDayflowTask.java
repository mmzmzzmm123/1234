package com.ruoyi.project.benyi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 一日流程任务对象 by_dayflow_task
 * 
 * @author tsbz
 * @date 2020-05-13
 */
public class ByDayflowTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 标识 */
    private Long id;

    /** 流程id */
    @Excel(name = "流程id")
    private Long lcId;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String rwmc;

    /** 所属流程 */
    @Excel(name = "所属流程")
    private String lcmc;

    /** 任务目的 */
    @Excel(name = "任务目的")
    private String rwmd;

    /** 任务解读 */
    @Excel(name = "任务解读")
    private String rwjd;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createuser;

    /** 创建时间 */
    @Excel(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    /** 标准数量 */
    @Excel(name = "标准数量")
    private Long standardCount;

    /** 更新者 */
    @Excel(name = "更新者")
    private Long updateuser;

    /** 更新时间 */
    @Excel(name = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatetime;

    /** 备用字段 */
    @Excel(name = "备用字段")
    private String beiyong;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setLcId(Long lcId) 
    {
        this.lcId = lcId;
    }

    public Long getLcId() 
    {
        return lcId;
    }
    public void setRwmc(String rwmc) 
    {
        this.rwmc = rwmc;
    }

    public String getRwmc() 
    {
        return rwmc;
    }
    public void setLcmc(String lcmc) 
    {
        this.lcmc = lcmc;
    }

    public String getLcmc() 
    {
        return lcmc;
    }
    public void setRwmd(String rwmd) 
    {
        this.rwmd = rwmd;
    }

    public String getRwmd() 
    {
        return rwmd;
    }
    public void setRwjd(String rwjd) 
    {
        this.rwjd = rwjd;
    }

    public String getRwjd() 
    {
        return rwjd;
    }
    public void setCreateuser(Long createuser) 
    {
        this.createuser = createuser;
    }

    public Long getCreateuser() 
    {
        return createuser;
    }
    public void setStandardCount(Long standardCount) 
    {
        this.standardCount = standardCount;
    }

    public Long getStandardCount() 
    {
        return standardCount;
    }
    public void setUpdateuser(Long updateuser) 
    {
        this.updateuser = updateuser;
    }

    public Long getUpdateuser() 
    {
        return updateuser;
    }
    public void setBeiyong(String beiyong) 
    {
        this.beiyong = beiyong;
    }

    public String getBeiyong() 
    {
        return beiyong;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("lcId", getLcId())
            .append("rwmc", getRwmc())
            .append("lcmc", getLcmc())
            .append("rwmd", getRwmd())
            .append("rwjd", getRwjd())
            .append("createuser", getCreateuser())
            .append("createtime", getCreatetime())
            .append("standardCount", getStandardCount())
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
