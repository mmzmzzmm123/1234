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
    @Excel(name = "方案编号")
    private Long faid;

    /** 教师编号 */
    @Excel(name = "教师编号")
    private Long jsid;

    /** 当前状态 */
    @Excel(name = "当前状态")
    private String dqzt;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createuserid;

    /** 基地校审核人 */
    @Excel(name = "基地校审核人")
    private Long jdxshr;

    /** 基地校审核状态 */
    @Excel(name = "基地校审核状态")
    private String jdxshzt;

    /** 上报理由 */
    @Excel(name = "上报理由")
    private String sbly;

    /** 区级审核人 */
    @Excel(name = "区级审核人")
    private Long qjshr;

    /** 区级审核状态 */
    @Excel(name = "区级审核状态")
    private String qjshzt;

    /** 区级审核意见 */
    @Excel(name = "区级审核意见")
    private String qjshyj;

    /** 基地排序 */
    @Excel(name = "基地排序")
    private Long jdpx;

    /** 案例分析得分 */
    @Excel(name = "案例分析得分")
    private BigDecimal alfxdf;

    /** 教案设计得分 */
    @Excel(name = "教案设计得分")
    private BigDecimal jasjdf;

    /** 钢笔字得分 */
    @Excel(name = "钢笔字得分")
    private BigDecimal gbzdf;

    /** 综合得分 */
    @Excel(name = "综合得分")
    private BigDecimal zhdf;

    /** 成绩导入创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "成绩导入创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date cjdrcreateTime;

    /** 面试确认 */
    @Excel(name = "面试确认")
    private String msqr;

    /** 面试确认时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "面试确认时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date msqrcreateTime;

    /** 面试结果模拟课堂教学 */
    @Excel(name = "面试结果模拟课堂教学")
    private BigDecimal msjgmnktjxdf;

    /** 演讲得分 */
    @Excel(name = "演讲得分")
    private BigDecimal yjdf;

    /** 综合得分2 */
    @Excel(name = "综合得分2")
    private BigDecimal zhdf2;

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
