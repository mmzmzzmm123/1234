package com.ruoyi.business.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 承包商信息对象 bus_cbsxx
 * 
 * @author yaowei
 * @date 2021-02-21
 */
public class BusCbsxx extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 承包商名称 */
    @Excel(name = "承包商名称")
    private String cbsName;

    /** 负责人 */
    @Excel(name = "负责人")
    private String fzr;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String lxfs;

    /** 擅长工作 */
    @Excel(name = "擅长工作")
    private String scgz;

    /** 写入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "写入时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date xrsj;

    /** 写入人id */
    @Excel(name = "写入人id")
    private Long xrrId;

    /** 最后修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date xgsj;

    /** 最后修改人id */
    @Excel(name = "最后修改人id")
    private Long xgrId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCbsName(String cbsName) 
    {
        this.cbsName = cbsName;
    }

    public String getCbsName() 
    {
        return cbsName;
    }
    public void setFzr(String fzr) 
    {
        this.fzr = fzr;
    }

    public String getFzr() 
    {
        return fzr;
    }
    public void setLxfs(String lxfs) 
    {
        this.lxfs = lxfs;
    }

    public String getLxfs() 
    {
        return lxfs;
    }
    public void setScgz(String scgz) 
    {
        this.scgz = scgz;
    }

    public String getScgz() 
    {
        return scgz;
    }
    public void setXrsj(Date xrsj) 
    {
        this.xrsj = xrsj;
    }

    public Date getXrsj() 
    {
        return xrsj;
    }
    public void setXrrId(Long xrrId) 
    {
        this.xrrId = xrrId;
    }

    public Long getXrrId() 
    {
        return xrrId;
    }
    public void setXgsj(Date xgsj) 
    {
        this.xgsj = xgsj;
    }

    public Date getXgsj() 
    {
        return xgsj;
    }
    public void setXgrId(Long xgrId) 
    {
        this.xgrId = xgrId;
    }

    public Long getXgrId() 
    {
        return xgrId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("cbsName", getCbsName())
            .append("fzr", getFzr())
            .append("lxfs", getLxfs())
            .append("scgz", getScgz())
            .append("xrsj", getXrsj())
            .append("xrrId", getXrrId())
            .append("xgsj", getXgsj())
            .append("xgrId", getXgrId())
            .toString();
    }
}
