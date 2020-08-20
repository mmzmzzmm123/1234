package com.ruoyi.jxjs.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 基地校对象 tsbz_jdx
 * 
 * @author ruoyi
 * @date 2020-08-20
 */
public class TsbzJdx extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 标识 */
    private String id;

    /** 基地校其他系统id */
    @Excel(name = "基地校其他系统id")
    private String otherid;

    /** 基地校名称 */
    @Excel(name = "基地校名称")
    private String jdxmc;

    /** 学校类别 */
    @Excel(name = "学校类别")
    private String xxlb;

    /** 办学类型 */
    @Excel(name = "办学类型")
    private String bxlx;

    /** 学校办别 */
    @Excel(name = "学校办别")
    private String xxbb;

    /** 基地校年份 */
    @Excel(name = "基地校年份")
    private String jdxnf;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setOtherid(String otherid) 
    {
        this.otherid = otherid;
    }

    public String getOtherid() 
    {
        return otherid;
    }
    public void setJdxmc(String jdxmc) 
    {
        this.jdxmc = jdxmc;
    }

    public String getJdxmc() 
    {
        return jdxmc;
    }
    public void setXxlb(String xxlb) 
    {
        this.xxlb = xxlb;
    }

    public String getXxlb() 
    {
        return xxlb;
    }
    public void setBxlx(String bxlx) 
    {
        this.bxlx = bxlx;
    }

    public String getBxlx() 
    {
        return bxlx;
    }
    public void setXxbb(String xxbb) 
    {
        this.xxbb = xxbb;
    }

    public String getXxbb() 
    {
        return xxbb;
    }
    public void setJdxnf(String jdxnf) 
    {
        this.jdxnf = jdxnf;
    }

    public String getJdxnf() 
    {
        return jdxnf;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("otherid", getOtherid())
            .append("jdxmc", getJdxmc())
            .append("xxlb", getXxlb())
            .append("bxlx", getBxlx())
            .append("xxbb", getXxbb())
            .append("jdxnf", getJdxnf())
            .append("createTime", getCreateTime())
            .toString();
    }
}
