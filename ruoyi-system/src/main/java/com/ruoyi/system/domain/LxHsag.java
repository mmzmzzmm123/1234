package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 信号与系统对象 lx_hsag
 * 
 * @author gongyu
 * @date 2022-06-19
 */
public class LxHsag extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sj;

    /** 收盘点位 */
    @Excel(name = "收盘点位")
    private BigDecimal spdw;

    /** pe数值 */
    @Excel(name = "pe数值")
    private BigDecimal peCv;

    /** pb数值 */
    @Excel(name = "pb数值")
    private BigDecimal pbCv;

    /** ps数值 */
    @Excel(name = "ps数值")
    private BigDecimal psCv;

    /** pe10年百分位 */
    @Excel(name = "pe10年百分位")
    private BigDecimal pePos10;

    /** pb10年百分位 */
    @Excel(name = "pb10年百分位")
    private BigDecimal pbPos10;

    /** ps10年百分位 */
    @Excel(name = "ps10年百分位")
    private BigDecimal psPos10;

    /** pe20年百分位 */
    @Excel(name = "pe20年百分位")
    private BigDecimal pePos20;

    /** pb20年百分位 */
    @Excel(name = "pb20年百分位")
    private BigDecimal pbPos20;

    /** ps20年百分位 */
    @Excel(name = "ps20年百分位")
    private BigDecimal psPos20;

    /** 信号 */
    @Excel(name = "信号")
    private BigDecimal zonghePos;

    public void setSj(Date sj) 
    {
        this.sj = sj;
    }

    public Date getSj() 
    {
        return sj;
    }
    public void setSpdw(BigDecimal spdw) 
    {
        this.spdw = spdw;
    }

    public BigDecimal getSpdw() 
    {
        return spdw;
    }
    public void setPeCv(BigDecimal peCv) 
    {
        this.peCv = peCv;
    }

    public BigDecimal getPeCv() 
    {
        return peCv;
    }
    public void setPbCv(BigDecimal pbCv) 
    {
        this.pbCv = pbCv;
    }

    public BigDecimal getPbCv() 
    {
        return pbCv;
    }
    public void setPsCv(BigDecimal psCv) 
    {
        this.psCv = psCv;
    }

    public BigDecimal getPsCv() 
    {
        return psCv;
    }
    public void setPePos10(BigDecimal pePos10) 
    {
        this.pePos10 = pePos10;
    }

    public BigDecimal getPePos10() 
    {
        return pePos10;
    }
    public void setPbPos10(BigDecimal pbPos10) 
    {
        this.pbPos10 = pbPos10;
    }

    public BigDecimal getPbPos10() 
    {
        return pbPos10;
    }
    public void setPsPos10(BigDecimal psPos10) 
    {
        this.psPos10 = psPos10;
    }

    public BigDecimal getPsPos10() 
    {
        return psPos10;
    }
    public void setPePos20(BigDecimal pePos20) 
    {
        this.pePos20 = pePos20;
    }

    public BigDecimal getPePos20() 
    {
        return pePos20;
    }
    public void setPbPos20(BigDecimal pbPos20) 
    {
        this.pbPos20 = pbPos20;
    }

    public BigDecimal getPbPos20() 
    {
        return pbPos20;
    }
    public void setPsPos20(BigDecimal psPos20) 
    {
        this.psPos20 = psPos20;
    }

    public BigDecimal getPsPos20() 
    {
        return psPos20;
    }
    public void setZonghePos(BigDecimal zonghePos) 
    {
        this.zonghePos = zonghePos;
    }

    public BigDecimal getZonghePos() 
    {
        return zonghePos;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("sj", getSj())
            .append("spdw", getSpdw())
            .append("peCv", getPeCv())
            .append("pbCv", getPbCv())
            .append("psCv", getPsCv())
            .append("pePos10", getPePos10())
            .append("pbPos10", getPbPos10())
            .append("psPos10", getPsPos10())
            .append("pePos20", getPePos20())
            .append("pbPos20", getPbPos20())
            .append("psPos20", getPsPos20())
            .append("zonghePos", getZonghePos())
            .toString();
    }
}
