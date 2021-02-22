package com.ruoyi.business.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 承包商站点信息对象 bus_cbszdxx
 * 
 * @author yaowei
 * @date 2021-02-22
 */
public class BusCbszdxx extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 承包商ID */
    @Excel(name = "承包商ID")
    private Long cbsId;

    /** 站点ID */
    @Excel(name = "站点ID")
    private Long zdId;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date kssj;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date jssj;

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
    public void setZdId(Long zdId) 
    {
        this.zdId = zdId;
    }

    public Long getZdId() 
    {
        return zdId;
    }
    public void setKssj(Date kssj) 
    {
        this.kssj = kssj;
    }

    public Date getKssj() 
    {
        return kssj;
    }
    public void setJssj(Date jssj) 
    {
        this.jssj = jssj;
    }

    public Date getJssj() 
    {
        return jssj;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("cbsId", getCbsId())
            .append("zdId", getZdId())
            .append("kssj", getKssj())
            .append("jssj", getJssj())
            .toString();
    }
}
