package com.ruoyi.jxjs.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 见习教师基本信息对象 tsbz_jxjsjbxx
 * 
 * @author ruoyi
 * @date 2020-08-20
 */
public class TsbzJxjsjbxx extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 标识 */
    private Long id;

    /** 其他系统id */
    @Excel(name = "其他系统id")
    private String otherid;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 进修编号 */
    @Excel(name = "进修编号")
    private String jxbh;

    /** 性别 */
    @Excel(name = "性别")
    private String xb;

    /** 出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date csrq;

    /** 电子邮件 */
    @Excel(name = "电子邮件")
    private String email;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String phone;

    /** 邮编 */
    @Excel(name = "邮编")
    private String yzbm;

    /** 政治面貌 */
    @Excel(name = "政治面貌")
    private String zzmm;

    /** 民族 */
    @Excel(name = "民族")
    private String mz;

    /** 聘任单位 */
    @Excel(name = "聘任单位")
    private String prdwid;

    /** 聘任单位名称 */
    @Excel(name = "聘任单位名称")
    private String prdwmc;

    /** 基地校 */
    @Excel(name = "基地校")
    private String jdxid;

    /** 任教学段 */
    @Excel(name = "任教学段")
    private String rjxd;

    /** 任教学科 */
    @Excel(name = "任教学科")
    private String rjxk;

    /** 任教年级 */
    @Excel(name = "任教年级")
    private String rjnj;

    /** 毕业院校 */
    @Excel(name = "毕业院校")
    private String byyx;

    /** 学历 */
    @Excel(name = "学历")
    private String xl;

    /** 学位 */
    @Excel(name = "学位")
    private String xw;

    /** 是否师范生 */
    @Excel(name = "是否师范生")
    private String sfsfs;

    /** 录取年份 */
    @Excel(name = "录取年份")
    private String lqnf;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOtherid(String otherid) 
    {
        this.otherid = otherid;
    }

    public String getOtherid() 
    {
        return otherid;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setJxbh(String jxbh) 
    {
        this.jxbh = jxbh;
    }

    public String getJxbh() 
    {
        return jxbh;
    }
    public void setXb(String xb) 
    {
        this.xb = xb;
    }

    public String getXb() 
    {
        return xb;
    }
    public void setCsrq(Date csrq) 
    {
        this.csrq = csrq;
    }

    public Date getCsrq() 
    {
        return csrq;
    }
    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setYzbm(String yzbm) 
    {
        this.yzbm = yzbm;
    }

    public String getYzbm() 
    {
        return yzbm;
    }
    public void setZzmm(String zzmm) 
    {
        this.zzmm = zzmm;
    }

    public String getZzmm() 
    {
        return zzmm;
    }
    public void setMz(String mz) 
    {
        this.mz = mz;
    }

    public String getMz() 
    {
        return mz;
    }
    public void setPrdwid(String prdwid) 
    {
        this.prdwid = prdwid;
    }

    public String getPrdwid() 
    {
        return prdwid;
    }
    public void setPrdwmc(String prdwmc) 
    {
        this.prdwmc = prdwmc;
    }

    public String getPrdwmc() 
    {
        return prdwmc;
    }
    public void setJdxid(String jdxid) 
    {
        this.jdxid = jdxid;
    }

    public String getJdxid() 
    {
        return jdxid;
    }
    public void setRjxd(String rjxd) 
    {
        this.rjxd = rjxd;
    }

    public String getRjxd() 
    {
        return rjxd;
    }
    public void setRjxk(String rjxk) 
    {
        this.rjxk = rjxk;
    }

    public String getRjxk() 
    {
        return rjxk;
    }
    public void setRjnj(String rjnj) 
    {
        this.rjnj = rjnj;
    }

    public String getRjnj() 
    {
        return rjnj;
    }
    public void setByyx(String byyx) 
    {
        this.byyx = byyx;
    }

    public String getByyx() 
    {
        return byyx;
    }
    public void setXl(String xl) 
    {
        this.xl = xl;
    }

    public String getXl() 
    {
        return xl;
    }
    public void setXw(String xw) 
    {
        this.xw = xw;
    }

    public String getXw() 
    {
        return xw;
    }
    public void setSfsfs(String sfsfs) 
    {
        this.sfsfs = sfsfs;
    }

    public String getSfsfs() 
    {
        return sfsfs;
    }
    public void setLqnf(String lqnf) 
    {
        this.lqnf = lqnf;
    }

    public String getLqnf() 
    {
        return lqnf;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("otherid", getOtherid())
            .append("name", getName())
            .append("jxbh", getJxbh())
            .append("xb", getXb())
            .append("csrq", getCsrq())
            .append("email", getEmail())
            .append("phone", getPhone())
            .append("yzbm", getYzbm())
            .append("zzmm", getZzmm())
            .append("mz", getMz())
            .append("prdwid", getPrdwid())
            .append("prdwmc", getPrdwmc())
            .append("jdxid", getJdxid())
            .append("rjxd", getRjxd())
            .append("rjxk", getRjxk())
            .append("rjnj", getRjnj())
            .append("byyx", getByyx())
            .append("xl", getXl())
            .append("xw", getXw())
            .append("sfsfs", getSfsfs())
            .append("lqnf", getLqnf())
            .append("createTime", getCreateTime())
            .toString();
    }
}
