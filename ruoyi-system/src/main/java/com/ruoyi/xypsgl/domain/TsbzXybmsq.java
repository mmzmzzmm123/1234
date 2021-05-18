package com.ruoyi.xypsgl.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学员报名申请对象 tsbz_xybmsq
 * 
 * @author tsbz
 * @date 2021-04-23
 */
public class TsbzXybmsq extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 方案编号 */
    @Excel(name = "方案编号")
    private Long faid;

    /** 报名基地id */
    @Excel(name = "报名基地id")
    private Long jdid;



    // 教师id
    private Long jsid;

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

    /** 职称 */
    @Excel(name = "职称")
    private String zc;

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

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createUser;

    // 学校审核状态
    private String xxshstatus;

    // 学校审核建议
    private String xxshjy;

    // 区级审核状态
    private String qjshstatus;

    // 学员选拔状态
    private String xyxbstatus;

    // 已经报名人数
    private Integer countYbm;

    // 学员结果确认状态
    private String xypsjgqrstatus;

    // 部门id
    private String deptid;



    // 所属学年
    private String ssxn;

    // 学员评价结果状态
    private String xypjjgstatus;



    // 区级审核建议
    private String qjshjy;

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
    public void setJdid(Long jdid) 
    {
        this.jdid = jdid;
    }

    public Long getJdid() 
    {
        return jdid;
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
    public void setZc(String zc) 
    {
        this.zc = zc;
    }

    public String getZc() 
    {
        return zc;
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
    public void setCreateUser(Long createUser) 
    {
        this.createUser = createUser;
    }

    public Long getCreateUser() 
    {
        return createUser;
    }

    public String getXxshstatus() {
        return xxshstatus;
    }

    public void setXxshstatus(String xxshstatus) {
        this.xxshstatus = xxshstatus;
    }

    public String getXxshjy() {
        return xxshjy;
    }

    public void setXxshjy(String xxshjy) {
        this.xxshjy = xxshjy;
    }

    public String getQjshstatus() {
        return qjshstatus;
    }

    public void setQjshstatus(String qjshstatus) {
        this.qjshstatus = qjshstatus;
    }

    public String getQjshjy() {
        return qjshjy;
    }

    public void setQjshjy(String qjshjy) {
        this.qjshjy = qjshjy;
    }

    public Long getJsid() {
        return jsid;
    }

    public void setJsid(Long jsid) {
        this.jsid = jsid;
    }

    public String getSsxn() {
        return ssxn;
    }

    public void setSsxn(String ssxn) {
        this.ssxn = ssxn;
    }

    public String getXypjjgstatus() {
        return xypjjgstatus;
    }

    public void setXypjjgstatus(String xypjjgstatus) {
        this.xypjjgstatus = xypjjgstatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("faid", getFaid())
            .append("jdid", getJdid())
            .append("jsid", getJsid())
            .append("name", getName())
            .append("jxbh", getJxbh())
            .append("xb", getXb())
            .append("csrq", getCsrq())
            .append("xd", getXd())
            .append("xk", getXk())
            .append("zc", getZc())
            .append("dwmc", getDwmc())
            .append("dwdz", getDwdz())
            .append("jdtype", getJdtype())
            .append("sf", getSf())
            .append("phone", getPhone())
            .append("email", getEmail())
            .append("createTime", getCreateTime())
            .append("createUser", getCreateUser())
            .append("xxshstatus", getXxshstatus())
            .append("xxshjy", getXxshjy())
            .append("qjshstatus", getQjshstatus())
            .append("qjshjy", getQjshjy())
            .append("countYbm", getCountYbm())
            .append("xyxbstatus", getXyxbstatus())
            .append("xypsjgqrstatus", getXypsjgqrstatus())
            .append("deptid", getDeptid())
            .append("ssxn", getSsxn())
            .append("xypjjgstatus", getXypjjgstatus())
            .toString();
    }

    public Integer getCountYbm() {
        return countYbm;
    }

    public void setCountYbm(Integer countYbm) {
        this.countYbm = countYbm;
    }

    public String getXyxbstatus() {
        return xyxbstatus;
    }

    public void setXyxbstatus(String xyxbstatus) {
        this.xyxbstatus = xyxbstatus;
    }

    public String getXypsjgqrstatus() {
        return xypsjgqrstatus;
    }

    public void setXypsjgqrstatus(String xypsjgqrstatus) {
        this.xypsjgqrstatus = xypsjgqrstatus;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }
}
