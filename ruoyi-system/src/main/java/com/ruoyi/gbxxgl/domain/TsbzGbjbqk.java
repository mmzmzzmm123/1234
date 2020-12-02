package com.ruoyi.gbxxgl.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 干部基本情况对象 tsbz_gbjbqk
 * 
 * @author ruoyi
 * @date 2020-12-02
 */
public class TsbzGbjbqk extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 其他表Id */
    @Excel(name = "其他表Id")
    private String otherid;

    /** 教师姓名 */
    @Excel(name = "教师姓名")
    private String name;

    /** 性别 */
    @Excel(name = "性别")
    private String xb;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String dwmc;

    /** 单位简称 */
    @Excel(name = "单位简称")
    private String dwjc;

    /** 现任行政职务 */
    @Excel(name = "现任行政职务")
    private String xrxzzw;

    /** 现任党内职务 */
    @Excel(name = "现任党内职务")
    private String xrdnzw;

    /** 现任群团职务 */
    @Excel(name = "现任群团职务")
    private String xrqtzw;

    /** 任现职务年月 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任现职务年月", width = 30, dateFormat = "yyyy-MM-dd")
    private Date rxzwny;

    /** 现任校长职级 */
    @Excel(name = "现任校长职级")
    private String xrxzzj;

    /** 校长职级确定年月 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "校长职级确定年月", width = 30, dateFormat = "yyyy-MM-dd")
    private Date xzzjqdny;

    /** 是否后备干部 */
    @Excel(name = "是否后备干部")
    private String sfhbgb;

    /** 后备干部确定年月 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "后备干部确定年月", width = 30, dateFormat = "yyyy-MM-dd")
    private Date hbgbqdny;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String sfzh;

    /** 民族 */
    @Excel(name = "民族")
    private String mz;

    /** 籍贯 */
    @Excel(name = "籍贯")
    private String jg;

    /** 出生地 */
    @Excel(name = "出生地")
    private String csd;

    /** 出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date csrq;

    /** 参加工作年月 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "参加工作年月", width = 30, dateFormat = "yyyy-MM-dd")
    private Date cjgzny;

    /** 家庭住址 */
    @Excel(name = "家庭住址")
    private String jtzz;

    /** 单位地址 */
    @Excel(name = "单位地址")
    private String dwdz;

    /** 学历 */
    @Excel(name = "学历")
    private String xl;

    /** 学位 */
    @Excel(name = "学位")
    private String xw;

    /** 毕业院校 */
    @Excel(name = "毕业院校")
    private String byyx;

    /** 教育类型 */
    @Excel(name = "教育类型")
    private String jylx;

    /** 职称 */
    @Excel(name = "职称")
    private String zc;

    /** 驾照持有情况 */
    @Excel(name = "驾照持有情况")
    private String jzcyqk;

    /** 能否开车 */
    @Excel(name = "能否开车")
    private String nfkc;

    /** 婚姻状况 */
    @Excel(name = "婚姻状况")
    private String hyzk;

    /** 政治面貌 */
    @Excel(name = "政治面貌")
    private String zzmm;

    /** 入党时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入党时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date rdsj;

    /** 办公室电话 */
    @Excel(name = "办公室电话")
    private String bgsdh;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String phone;

    /** 任教学科 */
    @Excel(name = "任教学科")
    private String rjxk;

    /** 健康状况 */
    @Excel(name = "健康状况")
    private String jkzk;

    /** 出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatetime;

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
    public void setXb(String xb) 
    {
        this.xb = xb;
    }

    public String getXb() 
    {
        return xb;
    }
    public void setDwmc(String dwmc) 
    {
        this.dwmc = dwmc;
    }

    public String getDwmc() 
    {
        return dwmc;
    }
    public void setDwjc(String dwjc) 
    {
        this.dwjc = dwjc;
    }

    public String getDwjc() 
    {
        return dwjc;
    }
    public void setXrxzzw(String xrxzzw) 
    {
        this.xrxzzw = xrxzzw;
    }

    public String getXrxzzw() 
    {
        return xrxzzw;
    }
    public void setXrdnzw(String xrdnzw) 
    {
        this.xrdnzw = xrdnzw;
    }

    public String getXrdnzw() 
    {
        return xrdnzw;
    }
    public void setXrqtzw(String xrqtzw) 
    {
        this.xrqtzw = xrqtzw;
    }

    public String getXrqtzw() 
    {
        return xrqtzw;
    }
    public void setRxzwny(Date rxzwny) 
    {
        this.rxzwny = rxzwny;
    }

    public Date getRxzwny() 
    {
        return rxzwny;
    }
    public void setXrxzzj(String xrxzzj) 
    {
        this.xrxzzj = xrxzzj;
    }

    public String getXrxzzj() 
    {
        return xrxzzj;
    }
    public void setXzzjqdny(Date xzzjqdny) 
    {
        this.xzzjqdny = xzzjqdny;
    }

    public Date getXzzjqdny() 
    {
        return xzzjqdny;
    }
    public void setSfhbgb(String sfhbgb) 
    {
        this.sfhbgb = sfhbgb;
    }

    public String getSfhbgb() 
    {
        return sfhbgb;
    }
    public void setHbgbqdny(Date hbgbqdny) 
    {
        this.hbgbqdny = hbgbqdny;
    }

    public Date getHbgbqdny() 
    {
        return hbgbqdny;
    }
    public void setSfzh(String sfzh) 
    {
        this.sfzh = sfzh;
    }

    public String getSfzh() 
    {
        return sfzh;
    }
    public void setMz(String mz) 
    {
        this.mz = mz;
    }

    public String getMz() 
    {
        return mz;
    }
    public void setJg(String jg) 
    {
        this.jg = jg;
    }

    public String getJg() 
    {
        return jg;
    }
    public void setCsd(String csd) 
    {
        this.csd = csd;
    }

    public String getCsd() 
    {
        return csd;
    }
    public void setCsrq(Date csrq) 
    {
        this.csrq = csrq;
    }

    public Date getCsrq() 
    {
        return csrq;
    }
    public void setCjgzny(Date cjgzny) 
    {
        this.cjgzny = cjgzny;
    }

    public Date getCjgzny() 
    {
        return cjgzny;
    }
    public void setJtzz(String jtzz) 
    {
        this.jtzz = jtzz;
    }

    public String getJtzz() 
    {
        return jtzz;
    }
    public void setDwdz(String dwdz) 
    {
        this.dwdz = dwdz;
    }

    public String getDwdz() 
    {
        return dwdz;
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
    public void setByyx(String byyx) 
    {
        this.byyx = byyx;
    }

    public String getByyx() 
    {
        return byyx;
    }
    public void setJylx(String jylx) 
    {
        this.jylx = jylx;
    }

    public String getJylx() 
    {
        return jylx;
    }
    public void setZc(String zc) 
    {
        this.zc = zc;
    }

    public String getZc() 
    {
        return zc;
    }
    public void setJzcyqk(String jzcyqk) 
    {
        this.jzcyqk = jzcyqk;
    }

    public String getJzcyqk() 
    {
        return jzcyqk;
    }
    public void setNfkc(String nfkc) 
    {
        this.nfkc = nfkc;
    }

    public String getNfkc() 
    {
        return nfkc;
    }
    public void setHyzk(String hyzk) 
    {
        this.hyzk = hyzk;
    }

    public String getHyzk() 
    {
        return hyzk;
    }
    public void setZzmm(String zzmm) 
    {
        this.zzmm = zzmm;
    }

    public String getZzmm() 
    {
        return zzmm;
    }
    public void setRdsj(Date rdsj) 
    {
        this.rdsj = rdsj;
    }

    public Date getRdsj() 
    {
        return rdsj;
    }
    public void setBgsdh(String bgsdh) 
    {
        this.bgsdh = bgsdh;
    }

    public String getBgsdh() 
    {
        return bgsdh;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setRjxk(String rjxk) 
    {
        this.rjxk = rjxk;
    }

    public String getRjxk() 
    {
        return rjxk;
    }
    public void setJkzk(String jkzk) 
    {
        this.jkzk = jkzk;
    }

    public String getJkzk() 
    {
        return jkzk;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("otherid", getOtherid())
            .append("name", getName())
            .append("xb", getXb())
            .append("dwmc", getDwmc())
            .append("dwjc", getDwjc())
            .append("xrxzzw", getXrxzzw())
            .append("xrdnzw", getXrdnzw())
            .append("xrqtzw", getXrqtzw())
            .append("rxzwny", getRxzwny())
            .append("xrxzzj", getXrxzzj())
            .append("xzzjqdny", getXzzjqdny())
            .append("sfhbgb", getSfhbgb())
            .append("hbgbqdny", getHbgbqdny())
            .append("sfzh", getSfzh())
            .append("updatetime", getUpdatetime())
            .append("mz", getMz())
            .append("jg", getJg())
            .append("csd", getCsd())
            .append("csrq", getCsrq())
            .append("cjgzny", getCjgzny())
            .append("jtzz", getJtzz())
            .append("dwdz", getDwdz())
            .append("xl", getXl())
            .append("xw", getXw())
            .append("byyx", getByyx())
            .append("jylx", getJylx())
            .append("zc", getZc())
            .append("jzcyqk", getJzcyqk())
            .append("nfkc", getNfkc())
            .append("hyzk", getHyzk())
            .append("zzmm", getZzmm())
            .append("rdsj", getRdsj())
            .append("bgsdh", getBgsdh())
            .append("phone", getPhone())
            .append("rjxk", getRjxk())
            .append("jkzk", getJkzk())
            .toString();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}
