package com.ruoyi.jxjs.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 基地区级审核对象 tsbz_jdcx
 * 
 * @author ruoyi
 * @date 2020-08-20
 */
public class TsbzJdcx extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 方案编号 */
//    @Excel(name = "方案编号")
    private Long faid;

    /** 教师编号 */
//    @Excel(name = "教师编号")
    private Long jsid;



    /** 创建人 */
//    @Excel(name = "创建人")
    private Long createuserid;

    /** 基地校审核人 */
//    @Excel(name = "基地校审核人")
    private Long jdxshr;

    /** 基地校审核状态 */
//    @Excel(name = "基地校审核状态")
    private String jdxshzt;

    /** 上报理由 */
//    @Excel(name = "上报理由")
    private String sbly;

    /** 区级审核人 */
//    @Excel(name = "区级审核人")
    private Long qjshr;

    /** 区级审核状态 */
//    @Excel(name = "区级审核状态")
    private String qjshzt;

    /** 区级审核意见 */
//    @Excel(name = "区级审核意见")
    private String qjshyj;

    /** 基地排序 */
//    @Excel(name = "基地排序")
    private Long jdpx;

    /** 案例分析得分 */
//    @Excel(name = "案例分析得分")
    private BigDecimal alfxdf;

    /** 教案设计得分 */
//    @Excel(name = "教案设计得分")
    private BigDecimal jasjdf;

    /** 钢笔字得分 */
//    @Excel(name = "钢笔字得分")
    private BigDecimal gbzdf;

    /** 综合得分 */
//    @Excel(name = "综合得分")
    private BigDecimal zhdf;

    /** 成绩导入创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
//    @Excel(name = "成绩导入创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date cjdrcreateTime;

    /** 面试确认 */
//    @Excel(name = "面试确认")
    private String msqr;

    /** 面试确认时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
//    @Excel(name = "面试确认时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date msqrcreateTime;

    /** 面试结果模拟课堂教学 */
//    @Excel(name = "面试结果模拟课堂教学")
    private BigDecimal msjgmnktjxdf;

    /** 演讲得分 */
//    @Excel(name = "演讲得分")
    private BigDecimal yjdf;

    /** 综合得分2 */
//    @Excel(name = "综合得分2")
    private BigDecimal zhdf2;

    @Excel(name = "评选名称")
    private String faname;
    @Excel(name = "基地校")
    private String jdxmc;
    @Excel(name = "姓名")
    private String jsname;
    @Excel(name = "性别", readConverterExp = "0=男,1=女,2=未知")
    private String xb;
    @Excel(name = "学段", readConverterExp = "1=学前教育,2=小学,3=普通初中,4=普通高中")
    private String rjxd;
    @Excel(name = "学科", readConverterExp = "11=品德与生活（社会） ,12=思想品德（政治） ,13=语文 ,14=数学 ,15=科学 ,16=物理 ,17=化学 ,18=生物 ,19=历史与社会 ,20=地理 ,21=历史 ,22=体育与健康 ,23=艺术 ,24=音乐 ,25=美术 ,41=英语")
    private String rjxk;
    @Excel(name = "联系方式")
    private String phone;
    @Excel(name = "聘任校")
    private String prdwmc;
    /** 当前状态 */
    @Excel(name = "当前状态")
    private String dqzt;

    public String getFaname() { return faname; }
    public void setFaname(String faname) { this.faname = faname;}

    public String getJdxmc() { return jdxmc; }
    public void setJdxmc(String jdxmc) { this.jdxmc = jdxmc; }

    public String getJsname() { return jsname; }
    public void setJsname(String jsname) { this.jsname = jsname; }

    public String getXb() { return xb; }
    public void setXb(String xb) { this.xb = xb; }

    public String getRjxd() { return rjxd; }
    public void setRjxd(String rjxd) { this.rjxd = rjxd; }

    public String getRjxk() { return rjxk; }
    public void setRjxk(String rjxk) { this.rjxk = rjxk; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPrdwmc() { return prdwmc; }
    public void setPrdwmc(String prdwmc) { this.prdwmc = prdwmc; }

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
    public void setDqzt(String dqzt) 
    {
        this.dqzt = dqzt;
    }

    public String getDqzt() 
    {
        return dqzt;
    }
    public void setCreateuserid(Long createuserid) 
    {
        this.createuserid = createuserid;
    }

    public Long getCreateuserid() 
    {
        return createuserid;
    }
    public void setJdxshr(Long jdxshr) 
    {
        this.jdxshr = jdxshr;
    }

    public Long getJdxshr() 
    {
        return jdxshr;
    }
    public void setJdxshzt(String jdxshzt) 
    {
        this.jdxshzt = jdxshzt;
    }

    public String getJdxshzt() 
    {
        return jdxshzt;
    }
    public void setSbly(String sbly) 
    {
        this.sbly = sbly;
    }

    public String getSbly() 
    {
        return sbly;
    }
    public void setQjshr(Long qjshr) 
    {
        this.qjshr = qjshr;
    }

    public Long getQjshr() 
    {
        return qjshr;
    }
    public void setQjshzt(String qjshzt) 
    {
        this.qjshzt = qjshzt;
    }

    public String getQjshzt() 
    {
        return qjshzt;
    }
    public void setQjshyj(String qjshyj) 
    {
        this.qjshyj = qjshyj;
    }

    public String getQjshyj() 
    {
        return qjshyj;
    }
    public void setJdpx(Long jdpx) 
    {
        this.jdpx = jdpx;
    }

    public Long getJdpx() 
    {
        return jdpx;
    }
    public void setAlfxdf(BigDecimal alfxdf) 
    {
        this.alfxdf = alfxdf;
    }

    public BigDecimal getAlfxdf() 
    {
        return alfxdf;
    }
    public void setJasjdf(BigDecimal jasjdf) 
    {
        this.jasjdf = jasjdf;
    }

    public BigDecimal getJasjdf() 
    {
        return jasjdf;
    }
    public void setGbzdf(BigDecimal gbzdf) 
    {
        this.gbzdf = gbzdf;
    }

    public BigDecimal getGbzdf() 
    {
        return gbzdf;
    }
    public void setZhdf(BigDecimal zhdf) 
    {
        this.zhdf = zhdf;
    }

    public BigDecimal getZhdf() 
    {
        return zhdf;
    }
    public void setCjdrcreateTime(Date cjdrcreateTime) 
    {
        this.cjdrcreateTime = cjdrcreateTime;
    }

    public Date getCjdrcreateTime() 
    {
        return cjdrcreateTime;
    }
    public void setMsqr(String msqr) 
    {
        this.msqr = msqr;
    }

    public String getMsqr() 
    {
        return msqr;
    }
    public void setMsqrcreateTime(Date msqrcreateTime) 
    {
        this.msqrcreateTime = msqrcreateTime;
    }

    public Date getMsqrcreateTime() 
    {
        return msqrcreateTime;
    }
    public void setMsjgmnktjxdf(BigDecimal msjgmnktjxdf) 
    {
        this.msjgmnktjxdf = msjgmnktjxdf;
    }

    public BigDecimal getMsjgmnktjxdf() 
    {
        return msjgmnktjxdf;
    }
    public void setYjdf(BigDecimal yjdf) 
    {
        this.yjdf = yjdf;
    }

    public BigDecimal getYjdf() 
    {
        return yjdf;
    }
    public void setZhdf2(BigDecimal zhdf2) 
    {
        this.zhdf2 = zhdf2;
    }

    public BigDecimal getZhdf2() 
    {
        return zhdf2;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("faid", getFaid())
            .append("jsid", getJsid())
            .append("dqzt", getDqzt())
            .append("createuserid", getCreateuserid())
            .append("createTime", getCreateTime())
            .append("jdxshr", getJdxshr())
            .append("jdxshzt", getJdxshzt())
            .append("sbly", getSbly())
            .append("qjshr", getQjshr())
            .append("qjshzt", getQjshzt())
            .append("qjshyj", getQjshyj())
            .append("jdpx", getJdpx())
            .append("alfxdf", getAlfxdf())
            .append("jasjdf", getJasjdf())
            .append("gbzdf", getGbzdf())
            .append("zhdf", getZhdf())
            .append("cjdrcreateTime", getCjdrcreateTime())
            .append("msqr", getMsqr())
            .append("msqrcreateTime", getMsqrcreateTime())
            .append("msjgmnktjxdf", getMsjgmnktjxdf())
            .append("yjdf", getYjdf())
            .append("zhdf2", getZhdf2())
            .toString();
    }
}
