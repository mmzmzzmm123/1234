package com.ruoyi.jxjs.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 基地培训信息发布对象 tsbz_jxzxjdpxxxfb
 * 
 * @author ruoyi
 * @date 2020-11-09
 */
public class TsbzJxzxjdpxxxfb extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 基地校ID */
    @Excel(name = "基地校ID")
    private String jdxid;

    /** 培训信息名称 */
    @Excel(name = "培训信息名称")
    private String name;

    /** 培训计划 */
    @Excel(name = "培训计划")
    private String pxjh;

    /** 培训方案 */
    @Excel(name = "培训方案")
    private String pxfa;

    /** 培训总结 */
    @Excel(name = "培训总结")
    private String pxzj;

    /** 备用字段 */
    @Excel(name = "备用字段")
    private String pxBeiyong;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createUserid;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setJdxid(String jdxid) 
    {
        this.jdxid = jdxid;
    }

    public String getJdxid() 
    {
        return jdxid;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setPxjh(String pxjh) 
    {
        this.pxjh = pxjh;
    }

    public String getPxjh() 
    {
        return pxjh;
    }
    public void setPxfa(String pxfa) 
    {
        this.pxfa = pxfa;
    }

    public String getPxfa() 
    {
        return pxfa;
    }
    public void setPxzj(String pxzj) 
    {
        this.pxzj = pxzj;
    }

    public String getPxzj() 
    {
        return pxzj;
    }
    public void setPxBeiyong(String pxBeiyong) 
    {
        this.pxBeiyong = pxBeiyong;
    }

    public String getPxBeiyong() 
    {
        return pxBeiyong;
    }
    public void setCreateUserid(Long createUserid) 
    {
        this.createUserid = createUserid;
    }

    public Long getCreateUserid() 
    {
        return createUserid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("jdxid", getJdxid())
            .append("name", getName())
            .append("pxjh", getPxjh())
            .append("pxfa", getPxfa())
            .append("pxzj", getPxzj())
            .append("pxBeiyong", getPxBeiyong())
            .append("createUserid", getCreateUserid())
            .append("createTime", getCreateTime())
            .toString();
    }
}
