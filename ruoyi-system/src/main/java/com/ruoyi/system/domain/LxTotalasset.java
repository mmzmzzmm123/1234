package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 总资产走势对象 lx_totalasset
 * 
 * @author ruoyi
 * @date 2022-06-19
 */
public class LxTotalasset extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date riqi;

    /** 总资产 */
    @Excel(name = "总资产")
    private BigDecimal totalasset;

    public void setRiqi(Date riqi) 
    {
        this.riqi = riqi;
    }

    public Date getRiqi() 
    {
        return riqi;
    }
    public void setTotalasset(BigDecimal totalasset) 
    {
        this.totalasset = totalasset;
    }

    public BigDecimal getTotalasset() 
    {
        return totalasset;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("riqi", getRiqi())
            .append("totalasset", getTotalasset())
            .toString();
    }
}
