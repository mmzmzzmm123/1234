package com.ruoyi.business.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ruoyi.common.annotation.Excel;

/**
 * 重大节日值班人员对象 bus_zdjrzbry
 * 
 * @author yaowei
 * @date 2021-02-24
 */
public class BusZdjrzbry implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 重大节日主键 */
    @Excel(name = "重大节日主键")
    private String zdjrId;

    /** 承包商ID */
    @Excel(name = "承包商ID")
    private String cbsId;

    /** 承包商名称 */
    @Excel(name = "承包商名称")
    private String cbsmc;

    /** 工人ID */
    @Excel(name = "工人ID")
    private String grId;

    /** 工人姓名 */
    @Excel(name = "工人姓名")
    private String grmc;

    /** 工人联系方式 */
    @Excel(name = "工人联系方式")
    private String grlxfs;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setZdjrId(String zdjrId) 
    {
        this.zdjrId = zdjrId;
    }

    public String getZdjrId() 
    {
        return zdjrId;
    }
    public void setCbsId(String cbsId) 
    {
        this.cbsId = cbsId;
    }

    public String getCbsId() 
    {
        return cbsId;
    }
    public void setCbsmc(String cbsmc) 
    {
        this.cbsmc = cbsmc;
    }

    public String getCbsmc() 
    {
        return cbsmc;
    }
    public void setGrId(String grId) 
    {
        this.grId = grId;
    }

    public String getGrId() 
    {
        return grId;
    }
    public void setGrmc(String grmc) 
    {
        this.grmc = grmc;
    }

    public String getGrmc() 
    {
        return grmc;
    }
    public void setGrlxfs(String grlxfs) 
    {
        this.grlxfs = grlxfs;
    }

    public String getGrlxfs() 
    {
        return grlxfs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("zdjrId", getZdjrId())
            .append("cbsId", getCbsId())
            .append("cbsmc", getCbsmc())
            .append("grId", getGrId())
            .append("grmc", getGrmc())
            .append("grlxfs", getGrlxfs())
            .toString();
    }
}
