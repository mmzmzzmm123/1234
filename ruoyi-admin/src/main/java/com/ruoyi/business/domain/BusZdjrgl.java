package com.ruoyi.business.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 重大节日管理对象 bus_zdjrgl
 * 
 * @author yaowei
 * @date 2021-02-24
 */
public class BusZdjrgl extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 节日日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "节日日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date jrrq;

    /** 节日名称 */
    @Excel(name = "节日名称")
    private String jrmc;

    /** 节日样式 */
    @Excel(name = "节日样式")
    private String jrys;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setJrrq(Date jrrq) 
    {
        this.jrrq = jrrq;
    }

    public Date getJrrq() 
    {
        return jrrq;
    }
    public void setJrmc(String jrmc) 
    {
        this.jrmc = jrmc;
    }

    public String getJrmc() 
    {
        return jrmc;
    }
    public void setJrys(String jrys) 
    {
        this.jrys = jrys;
    }

    public String getJrys() 
    {
        return jrys;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("jrrq", getJrrq())
            .append("jrmc", getJrmc())
            .append("jrys", getJrys())
            .toString();
    }
}
