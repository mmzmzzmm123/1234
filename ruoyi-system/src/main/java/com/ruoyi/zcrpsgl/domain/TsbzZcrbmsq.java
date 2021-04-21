package com.ruoyi.zcrpsgl.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 主持人报名申请对象 tsbz_zcrbmsq
 * 
 * @author tsbz
 * @date 2021-04-15
 */
public class TsbzZcrbmsq extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 方案编号 */
    @Excel(name = "方案编号")
    private Long faid;

    /** 专家组id */
    @Excel(name = "专家组id")
    private String zjzid;


    public String getZjz() {
        return zjz;
    }

    public void setZjz(String zjz) {
        this.zjz = zjz;
    }

    private String zjz;


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

    /** 学段 */
    @Excel(name = "学段")
    private String xd;

    /** 学科 */
    @Excel(name = "学科")
    private String xk;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String dwmc;

    /** 单位地址 */
    @Excel(name = "单位地址")
    private String dwdz;

    /** 基地类别 */
    @Excel(name = "基地类别")
    private String jdtype;

    /** 身份 */
    @Excel(name = "身份")
    private String sf;

    /** 电话 */
    @Excel(name = "电话")
    private String phone;

    /** 电子邮箱 */
    @Excel(name = "电子邮箱")
    private String email;

    /** 单位负责人 */
    @Excel(name = "单位负责人")
    private String dwfzr;

    /** 单位负责人电话 */
    @Excel(name = "单位负责人电话")
    private String dwfzrphone;

    /** 上传方案路径 */
    @Excel(name = "上传方案路径")
    private String scfapath;

    /** 上传方案名称 */
    @Excel(name = "上传方案名称")
    private String scfaname;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createUser;

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
    public void setZjzid(String zjzid) 
    {
        this.zjzid = zjzid;
    }

    public String getZjzid() 
    {
        return zjzid;
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
    public void setXd(String xd) 
    {
        this.xd = xd;
    }

    public String getXd() 
    {
        return xd;
    }
    public void setXk(String xk) 
    {
        this.xk = xk;
    }

    public String getXk() 
    {
        return xk;
    }
    public void setDwmc(String dwmc) 
    {
        this.dwmc = dwmc;
    }

    public String getDwmc() 
    {
        return dwmc;
    }
    public void setDwdz(String dwdz) 
    {
        this.dwdz = dwdz;
    }

    public String getDwdz() 
    {
        return dwdz;
    }
    public void setJdtype(String jdtype) 
    {
        this.jdtype = jdtype;
    }

    public String getJdtype() 
    {
        return jdtype;
    }
    public void setSf(String sf) 
    {
        this.sf = sf;
    }

    public String getSf() 
    {
        return sf;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }
    public void setDwfzr(String dwfzr) 
    {
        this.dwfzr = dwfzr;
    }

    public String getDwfzr() 
    {
        return dwfzr;
    }
    public void setDwfzrphone(String dwfzrphone) 
    {
        this.dwfzrphone = dwfzrphone;
    }

    public String getDwfzrphone() 
    {
        return dwfzrphone;
    }
    public void setScfapath(String scfapath) 
    {
        this.scfapath = scfapath;
    }

    public String getScfapath() 
    {
        return scfapath;
    }
    public void setScfaname(String scfaname) 
    {
        this.scfaname = scfaname;
    }

    public String getScfaname() 
    {
        return scfaname;
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
            .append("faid", getFaid())
            .append("zjzid", getZjzid())
            .append("zjz", getZjzid())
            .append("name", getName())
            .append("jxbh", getJxbh())
            .append("xb", getXb())
            .append("csrq", getCsrq())
            .append("xd", getXd())
            .append("xk", getXk())
            .append("dwmc", getDwmc())
            .append("dwdz", getDwdz())
            .append("jdtype", getJdtype())
            .append("sf", getSf())
            .append("phone", getPhone())
            .append("email", getEmail())
            .append("dwfzr", getDwfzr())
            .append("dwfzrphone", getDwfzrphone())
            .append("scfapath", getScfapath())
            .append("scfaname", getScfaname())
            .append("createTime", getCreateTime())
            .append("createUser", getCreateUser())
            .toString();
    }
}
