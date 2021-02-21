package com.ruoyi.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工人证书信息对象 bus_grzfxx
 * 
 * @author yaowei
 * @date 2021-02-21
 */
public class BusGrzfxx extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 承包商主键 */
    @Excel(name = "承包商主键")
    private Long cbsId;

    /** 资质名称 */
    @Excel(name = "资质名称")
    private String zzmc;

    /** 有效期 */
    @Excel(name = "有效期")
    private String yxq;

    /** 资质图片 */
    @Excel(name = "资质图片")
    private String zztp;

    /** 工人ID */
    @Excel(name = "工人ID")
    private Long grId;

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
    public void setZzmc(String zzmc) 
    {
        this.zzmc = zzmc;
    }

    public String getZzmc() 
    {
        return zzmc;
    }
    public void setYxq(String yxq) 
    {
        this.yxq = yxq;
    }

    public String getYxq() 
    {
        return yxq;
    }
    public void setZztp(String zztp) 
    {
        this.zztp = zztp;
    }

    public String getZztp() 
    {
        return zztp;
    }
    public void setGrId(Long grId) 
    {
        this.grId = grId;
    }

    public Long getGrId() 
    {
        return grId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("cbsId", getCbsId())
            .append("zzmc", getZzmc())
            .append("yxq", getYxq())
            .append("zztp", getZztp())
            .append("grId", getGrId())
            .toString();
    }
}
