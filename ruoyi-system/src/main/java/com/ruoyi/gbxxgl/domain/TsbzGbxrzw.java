package com.ruoyi.gbxxgl.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 干部任职情况-现任职务对象 tsbz_gbxrzw
 * 
 * @author ruoyi
 * @date 2020-12-07
 */
public class TsbzGbxrzw extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 干部ID */
    @Excel(name = "干部ID")
    private Long gbid;

    /** 任职年月 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任职年月", width = 30, dateFormat = "yyyy-MM-dd")
    private Date rzny;

    /** 行政职务 */
    @Excel(name = "行政职务")
    private String xzzw;

    /** 行政职务级别 */
    @Excel(name = "行政职务级别")
    private String xxzwjb;

    /** 党内职务 */
    @Excel(name = "党内职务")
    private String dnzw;

    /** 党内职务属性 */
    @Excel(name = "党内职务属性")
    private String dnzwsx;

    /** 群团职务 */
    @Excel(name = "群团职务")
    private String qtzw;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createuser;

    /** 创建时间 */
    @Excel(name = "创建时间")
    private Long createtime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setGbid(Long gbid) 
    {
        this.gbid = gbid;
    }

    public Long getGbid() 
    {
        return gbid;
    }
    public void setRzny(Date rzny) 
    {
        this.rzny = rzny;
    }

    public Date getRzny() 
    {
        return rzny;
    }
    public void setXzzw(String xzzw) 
    {
        this.xzzw = xzzw;
    }

    public String getXzzw() 
    {
        return xzzw;
    }
    public void setXxzwjb(String xxzwjb) 
    {
        this.xxzwjb = xxzwjb;
    }

    public String getXxzwjb() 
    {
        return xxzwjb;
    }
    public void setDnzw(String dnzw) 
    {
        this.dnzw = dnzw;
    }

    public String getDnzw() 
    {
        return dnzw;
    }
    public void setDnzwsx(String dnzwsx) 
    {
        this.dnzwsx = dnzwsx;
    }

    public String getDnzwsx() 
    {
        return dnzwsx;
    }
    public void setQtzw(String qtzw) 
    {
        this.qtzw = qtzw;
    }

    public String getQtzw() 
    {
        return qtzw;
    }
    public void setCreateuser(Long createuser) 
    {
        this.createuser = createuser;
    }

    public Long getCreateuser() 
    {
        return createuser;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("gbid", getGbid())
            .append("rzny", getRzny())
            .append("xzzw", getXzzw())
            .append("xxzwjb", getXxzwjb())
            .append("dnzw", getDnzw())
            .append("dnzwsx", getDnzwsx())
            .append("qtzw", getQtzw())
            .append("createuser", getCreateuser())
            .append("createtime", getCreatetime())
            .toString();
    }

    public Long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }
}
