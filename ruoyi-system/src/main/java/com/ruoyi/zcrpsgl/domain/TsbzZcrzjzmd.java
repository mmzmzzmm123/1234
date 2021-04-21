package com.ruoyi.zcrpsgl.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 主持人报名专家组名单对象 tsbz_zcrzjzmd
 * 
 * @author tsbz
 * @date 2021-04-21
 */
public class TsbzZcrzjzmd extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 性别 */
    @Excel(name = "性别")
    private String xb;

    /** 年龄 */
    @Excel(name = "年龄")
    private Integer age;

    /** 学历 */
    @Excel(name = "学历")
    private String xl;

    /** 职称 */
    @Excel(name = "职称")
    private String zc;

    /** 专长 */
    @Excel(name = "专长")
    private String zhch;

    /** 单位 */
    @Excel(name = "单位")
    private String dw;

    /** 分工 */
    @Excel(name = "分工")
    private String fg;

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
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setXb(String xb) 
    {
        this.xb = xb;
    }

    public String getXb() 
    {
        return xb;
    }
    public void setAge(Integer age) 
    {
        this.age = age;
    }

    public Integer getAge() 
    {
        return age;
    }
    public void setXl(String xl) 
    {
        this.xl = xl;
    }

    public String getXl() 
    {
        return xl;
    }
    public void setZc(String zc) 
    {
        this.zc = zc;
    }

    public String getZc() 
    {
        return zc;
    }
    public void setZhch(String zhch) 
    {
        this.zhch = zhch;
    }

    public String getZhch() 
    {
        return zhch;
    }
    public void setDw(String dw) 
    {
        this.dw = dw;
    }

    public String getDw() 
    {
        return dw;
    }
    public void setFg(String fg) 
    {
        this.fg = fg;
    }

    public String getFg() 
    {
        return fg;
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
            .append("name", getName())
            .append("xb", getXb())
            .append("age", getAge())
            .append("xl", getXl())
            .append("zc", getZc())
            .append("zhch", getZhch())
            .append("dw", getDw())
            .append("fg", getFg())
            .append("createTime", getCreateTime())
            .append("createUserid", getCreateUserid())
            .toString();
    }
}
