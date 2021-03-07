package com.ruoyi.business.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ruoyi.common.annotation.Excel;

/**
 * 人员定额信息对象 bus_rydexx
 * 
 * @author yaowei
 * @date 2021-02-23
 */
public class BusRydexx implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 服务项目 */
    @Excel(name = "服务项目")
    private String fwxm;

    /** 服务类别 */
    @Excel(name = "服务类别")
    private String fwlb;

    /** 计价单位 */
    @Excel(name = "计价单位")
    private String jjdw;

    /** 定额 */
    @Excel(name = "定额")
    private BigDecimal de;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFwxm(String fwxm) 
    {
        this.fwxm = fwxm;
    }

    public String getFwxm() 
    {
        return fwxm;
    }
    public void setFwlb(String fwlb) 
    {
        this.fwlb = fwlb;
    }

    public String getFwlb() 
    {
        return fwlb;
    }
    public void setJjdw(String jjdw) 
    {
        this.jjdw = jjdw;
    }

    public String getJjdw() 
    {
        return jjdw;
    }
    public void setDe(BigDecimal de) 
    {
        this.de = de;
    }

    public BigDecimal getDe() 
    {
        return de;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("fwxm", getFwxm())
            .append("fwlb", getFwlb())
            .append("jjdw", getJjdw())
            .append("de", getDe())
            .toString();
    }
}
