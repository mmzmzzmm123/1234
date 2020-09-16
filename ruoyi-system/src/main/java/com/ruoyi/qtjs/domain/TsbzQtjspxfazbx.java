package com.ruoyi.qtjs.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 群体教师评选方案指标对象 tsbz_qtjspxfazbx
 * 
 * @author ruoyi
 * @date 2020-09-16
 */
public class TsbzQtjspxfazbx extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 方案编号 */
    @Excel(name = "方案编号")
    private Long faid;

    /** 评选条件 */
    @Excel(name = "评选条件")
    private String pxtj;

    /** 所属类型 */
    @Excel(name = "所属类型")
    private String sslx;

    /** 指标值 */
    @Excel(name = "指标值")
    private String zbb;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createuserid;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFaid(Long faid) 
    {
        this.faid = faid;
    }

    public Long getFaid() 
    {
        return faid;
    }
    public void setPxtj(String pxtj) 
    {
        this.pxtj = pxtj;
    }

    public String getPxtj() 
    {
        return pxtj;
    }
    public void setSslx(String sslx) 
    {
        this.sslx = sslx;
    }

    public String getSslx() 
    {
        return sslx;
    }
    public void setZbb(String zbb) 
    {
        this.zbb = zbb;
    }

    public String getZbb() 
    {
        return zbb;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }
    public void setCreateuserid(Long createuserid) 
    {
        this.createuserid = createuserid;
    }

    public Long getCreateuserid() 
    {
        return createuserid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("faid", getFaid())
            .append("pxtj", getPxtj())
            .append("sslx", getSslx())
            .append("zbb", getZbb())
            .append("remarks", getRemarks())
            .append("createuserid", getCreateuserid())
            .append("createTime", getCreateTime())
            .toString();
    }
}
