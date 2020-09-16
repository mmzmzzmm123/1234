package com.ruoyi.qtjs.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 群体教师评选结果对象 tsbz_qtjspxjg
 * 
 * @author ruoyi
 * @date 2020-09-16
 */
public class TsbzQtjspxjg extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 评选方案编号 */
    @Excel(name = "评选方案编号")
    private Long faid;

    /** 教师编号 */
    @Excel(name = "教师编号")
    private Long jsid;

    /** 评选类型 */
    @Excel(name = "评选类型")
    private String pxlx;

    /** 年份 */
    @Excel(name = "年份")
    private String nf;

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
    public void setJsid(Long jsid) 
    {
        this.jsid = jsid;
    }

    public Long getJsid() 
    {
        return jsid;
    }
    public void setPxlx(String pxlx) 
    {
        this.pxlx = pxlx;
    }

    public String getPxlx() 
    {
        return pxlx;
    }
    public void setNf(String nf) 
    {
        this.nf = nf;
    }

    public String getNf() 
    {
        return nf;
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
            .append("jsid", getJsid())
            .append("pxlx", getPxlx())
            .append("nf", getNf())
            .append("createuserid", getCreateuserid())
            .append("createTime", getCreateTime())
            .toString();
    }
}
