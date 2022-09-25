package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 资产细分对象 getzichanxifen
 * 
 * @author gongyu
 * @date 2022-05-19
 */
public class Getzichanxifen extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @Excel(name = "${名称}", readConverterExp = "$column.readConverterExp()")
    private String name;

    /** $column.columnComment */
    @Excel(name = "${货币}", readConverterExp = "$column.readConverterExp()")
    private BigDecimal huobi;

    /** $column.columnComment */
    @Excel(name = "${债券}", readConverterExp = "$column.readConverterExp()")
    private BigDecimal zhaiquan;

    /** $column.columnComment */
    @Excel(name = "${已投入}", readConverterExp = "$column.readConverterExp()")
    private BigDecimal yitouru;

    /** $column.columnComment */
    @Excel(name = "${国内资产}", readConverterExp = "$column.readConverterExp()")
    private BigDecimal guoneiZichan;

    /** $column.columnComment */
    @Excel(name = "${轮动资产}", readConverterExp = "$column.readConverterExp()")
    private BigDecimal lundongZichan;

    /** $column.columnComment */
    @Excel(name = "${海外资产}", readConverterExp = "$column.readConverterExp()")
    private BigDecimal haiwaiZichan;

    /** $column.columnComment */
    @Excel(name = "${总计}", readConverterExp = "$column.readConverterExp()")
    private BigDecimal zongji;

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setHuobi(BigDecimal huobi) 
    {
        this.huobi = huobi;
    }

    public BigDecimal getHuobi() 
    {
        return huobi;
    }
    public void setZhaiquan(BigDecimal zhaiquan) 
    {
        this.zhaiquan = zhaiquan;
    }

    public BigDecimal getZhaiquan() 
    {
        return zhaiquan;
    }
    public void setYitouru(BigDecimal yitouru) 
    {
        this.yitouru = yitouru;
    }

    public BigDecimal getYitouru() 
    {
        return yitouru;
    }
    public void setGuoneiZichan(BigDecimal guoneiZichan) 
    {
        this.guoneiZichan = guoneiZichan;
    }

    public BigDecimal getGuoneiZichan() 
    {
        return guoneiZichan;
    }
    public void setLundongZichan(BigDecimal lundongZichan) 
    {
        this.lundongZichan = lundongZichan;
    }

    public BigDecimal getLundongZichan() 
    {
        return lundongZichan;
    }
    public void setHaiwaiZichan(BigDecimal haiwaiZichan) 
    {
        this.haiwaiZichan = haiwaiZichan;
    }

    public BigDecimal getHaiwaiZichan() 
    {
        return haiwaiZichan;
    }
    public void setZongji(BigDecimal zongji) 
    {
        this.zongji = zongji;
    }

    public BigDecimal getZongji() 
    {
        return zongji;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("name", getName())
            .append("huobi", getHuobi())
            .append("zhaiquan", getZhaiquan())
            .append("yitouru", getYitouru())
            .append("guoneiZichan", getGuoneiZichan())
            .append("lundongZichan", getLundongZichan())
            .append("haiwaiZichan", getHaiwaiZichan())
            .append("zongji", getZongji())
            .toString();
    }
}
