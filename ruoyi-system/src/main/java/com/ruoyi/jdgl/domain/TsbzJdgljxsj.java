package com.ruoyi.jdgl.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 基地管理教学实践对象 tsbz_jdgljxsj
 * 
 * @author tsbz
 * @date 2021-05-17
 */
public class TsbzJdgljxsj extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String rwmc;

    /** 课程名称 */
    @Excel(name = "课程名称")
    private String kcmc;

    /** 主讲人 */
    @Excel(name = "主讲人")
    private String zjr;

    /** 上课时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上课时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sktime;

    /** 科目 */
    @Excel(name = "科目")
    private String km;

    /** 年级 */
    @Excel(name = "年级")
    private String nj;

    /** 学校名称 */
    @Excel(name = "学校名称")
    private String xxmc;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createUser;

    // 基地id
    private Long jdid;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setRwmc(String rwmc) 
    {
        this.rwmc = rwmc;
    }

    public String getRwmc() 
    {
        return rwmc;
    }
    public void setKcmc(String kcmc) 
    {
        this.kcmc = kcmc;
    }

    public String getKcmc() 
    {
        return kcmc;
    }
    public void setZjr(String zjr) 
    {
        this.zjr = zjr;
    }

    public String getZjr() 
    {
        return zjr;
    }
    public void setSktime(Date sktime) 
    {
        this.sktime = sktime;
    }

    public Date getSktime() 
    {
        return sktime;
    }
    public void setKm(String km) 
    {
        this.km = km;
    }

    public String getKm() 
    {
        return km;
    }
    public void setNj(String nj) 
    {
        this.nj = nj;
    }

    public String getNj() 
    {
        return nj;
    }
    public void setXxmc(String xxmc) 
    {
        this.xxmc = xxmc;
    }

    public String getXxmc() 
    {
        return xxmc;
    }
    public void setCreateUser(Long createUser) 
    {
        this.createUser = createUser;
    }

    public Long getCreateUser() 
    {
        return createUser;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("rwmc", getRwmc())
            .append("kcmc", getKcmc())
            .append("zjr", getZjr())
            .append("sktime", getSktime())
            .append("km", getKm())
            .append("nj", getNj())
            .append("xxmc", getXxmc())
            .append("createUser", getCreateUser())
            .append("createTime", getCreateTime())
            .append("jdid", getJdid())
            .toString();
    }

    public Long getJdid() {
        return jdid;
    }

    public void setJdid(Long jdid) {
        this.jdid = jdid;
    }
}
