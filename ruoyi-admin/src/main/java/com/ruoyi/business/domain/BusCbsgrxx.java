package com.ruoyi.business.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;

/**
 * 承包商工人信息对象 bus_cbsgrxx
 * 
 * @author yaowei
 * @date 2021-02-21
 */
public class BusCbsgrxx implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 承包商主键 */
    @Excel(name = "承包商主键")
    private Long cbsId;

    /** 承包商主键 */
    @Excel(name = "承包商主键")
    private String xm;

    /** 身份证号码 */
    @Excel(name = "身份证号码")
    private String sfzhm;

    /** 岗位 */
    @Excel(name = "岗位")
    private String gw;

    /** 定薪 */
    @Excel(name = "定薪")
    private String dx;

    /** 工作地点 */
    @Excel(name = "工作地点")
    private String gzdd;

    /** 入职时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入职时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date rzjs;

    /** 离职时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "离职时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lzsj;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCbsId(Long cbsId) 
    {
        this.cbsId = cbsId;
    }

    public Long getCbsId() 
    {
        return cbsId;
    }
    public void setXm(String xm) 
    {
        this.xm = xm;
    }

    public String getXm() 
    {
        return xm;
    }
    public void setSfzhm(String sfzhm) 
    {
        this.sfzhm = sfzhm;
    }

    public String getSfzhm() 
    {
        return sfzhm;
    }
    public void setGw(String gw) 
    {
        this.gw = gw;
    }

    public String getGw() 
    {
        return gw;
    }
    public void setDx(String dx) 
    {
        this.dx = dx;
    }

    public String getDx() 
    {
        return dx;
    }
    public void setGzdd(String gzdd) 
    {
        this.gzdd = gzdd;
    }

    public String getGzdd() 
    {
        return gzdd;
    }
    public void setRzjs(Date rzjs) 
    {
        this.rzjs = rzjs;
    }

    public Date getRzjs() 
    {
        return rzjs;
    }
    public void setLzsj(Date lzsj) 
    {
        this.lzsj = lzsj;
    }

    public Date getLzsj() 
    {
        return lzsj;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("cbsId", getCbsId())
            .append("xm", getXm())
            .append("sfzhm", getSfzhm())
            .append("gw", getGw())
            .append("dx", getDx())
            .append("gzdd", getGzdd())
            .append("rzjs", getRzjs())
            .append("lzsj", getLzsj())
            .toString();
    }
}
