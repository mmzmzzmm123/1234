package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 个体工商户信贷_主题库_评分模型结果对象 ztk_zhd_pfmxjgb
 * 
 * @author genius
 * @date 2021-11-30
 */
public class ZtkZhdPfmxjgb extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 统一社会信用代码 */
    @Excel(name = "统一社会信用代码")
    private String tyshxydm;

    /** 申报个体工商户姓名 */
    @Excel(name = "申报个体工商户姓名")
    private String sbgtgshxm;

    /** 证件类型 */
    @Excel(name = "证件类型")
    private String zjlx;

    /** 证件号码 */
    @Excel(name = "证件号码")
    private String zjhm;

    /** 营业店名 */
    @Excel(name = "营业店名")
    private String yydm;

    /** 经营场所_地址 */
    @Excel(name = "经营场所_地址")
    private String jycsDz;

    /** 注册日期 */
    @Excel(name = "注册日期")
    private String zcrq;

    /** 经营年限_评分 */
    @Excel(name = "经营年限_评分")
    private Long jynx;

    /** 申报人固定资产情况_评分 */
    @Excel(name = "申报人固定资产情况_评分")
    private Long sbrgdzcqkpf;

    /** 申报人最高学历_评分 */
    @Excel(name = "申报人最高学历_评分")
    private Long sbrzgxlpf;

    /** 申报人婚姻状况_评分 */
    @Excel(name = "申报人婚姻状况_评分")
    private Long sbrhyzkpf;

    /** 申报人欠税情况_评分 */
    @Excel(name = "申报人欠税情况_评分")
    private Long sbrqsqkpf;

    /** 个体工商户欠税情况_评分 */
    @Excel(name = "个体工商户欠税情况_评分")
    private Long gtgshqsqkpf;

    /** 个体工商户激励情况_评分 */
    @Excel(name = "个体工商户激励情况_评分")
    private Long gtgshjlqkpf;

    /** 个体工商户享受扶持情况_评分 */
    @Excel(name = "个体工商户享受扶持情况_评分")
    private Long gtgshxsfcqkpf;

    /** 总评分 */
    @Excel(name = "总评分")
    private Long zpf;

    /** 风险等级 */
    @Excel(name = "风险等级")
    private String fxdj;

    public void setTyshxydm(String tyshxydm) 
    {
        this.tyshxydm = tyshxydm;
    }

    public String getTyshxydm() 
    {
        return tyshxydm;
    }
    public void setSbgtgshxm(String sbgtgshxm) 
    {
        this.sbgtgshxm = sbgtgshxm;
    }

    public String getSbgtgshxm() 
    {
        return sbgtgshxm;
    }
    public void setZjlx(String zjlx) 
    {
        this.zjlx = zjlx;
    }

    public String getZjlx() 
    {
        return zjlx;
    }
    public void setZjhm(String zjhm) 
    {
        this.zjhm = zjhm;
    }

    public String getZjhm() 
    {
        return zjhm;
    }
    public void setYydm(String yydm) 
    {
        this.yydm = yydm;
    }

    public String getYydm() 
    {
        return yydm;
    }
    public void setJycsDz(String jycsDz) 
    {
        this.jycsDz = jycsDz;
    }

    public String getJycsDz() 
    {
        return jycsDz;
    }
    public void setZcrq(String zcrq) 
    {
        this.zcrq = zcrq;
    }

    public String getZcrq() 
    {
        return zcrq;
    }
    public void setJynx(Long jynx) 
    {
        this.jynx = jynx;
    }

    public Long getJynx() 
    {
        return jynx;
    }
    public void setSbrgdzcqkpf(Long sbrgdzcqkpf) 
    {
        this.sbrgdzcqkpf = sbrgdzcqkpf;
    }

    public Long getSbrgdzcqkpf() 
    {
        return sbrgdzcqkpf;
    }
    public void setSbrzgxlpf(Long sbrzgxlpf) 
    {
        this.sbrzgxlpf = sbrzgxlpf;
    }

    public Long getSbrzgxlpf() 
    {
        return sbrzgxlpf;
    }
    public void setSbrhyzkpf(Long sbrhyzkpf) 
    {
        this.sbrhyzkpf = sbrhyzkpf;
    }

    public Long getSbrhyzkpf() 
    {
        return sbrhyzkpf;
    }
    public void setSbrqsqkpf(Long sbrqsqkpf) 
    {
        this.sbrqsqkpf = sbrqsqkpf;
    }

    public Long getSbrqsqkpf() 
    {
        return sbrqsqkpf;
    }
    public void setGtgshqsqkpf(Long gtgshqsqkpf) 
    {
        this.gtgshqsqkpf = gtgshqsqkpf;
    }

    public Long getGtgshqsqkpf() 
    {
        return gtgshqsqkpf;
    }
    public void setGtgshjlqkpf(Long gtgshjlqkpf) 
    {
        this.gtgshjlqkpf = gtgshjlqkpf;
    }

    public Long getGtgshjlqkpf() 
    {
        return gtgshjlqkpf;
    }
    public void setGtgshxsfcqkpf(Long gtgshxsfcqkpf) 
    {
        this.gtgshxsfcqkpf = gtgshxsfcqkpf;
    }

    public Long getGtgshxsfcqkpf() 
    {
        return gtgshxsfcqkpf;
    }
    public void setZpf(Long zpf) 
    {
        this.zpf = zpf;
    }

    public Long getZpf() 
    {
        return zpf;
    }
    public void setFxdj(String fxdj) 
    {
        this.fxdj = fxdj;
    }

    public String getFxdj() 
    {
        return fxdj;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("tyshxydm", getTyshxydm())
            .append("sbgtgshxm", getSbgtgshxm())
            .append("zjlx", getZjlx())
            .append("zjhm", getZjhm())
            .append("yydm", getYydm())
            .append("jycsDz", getJycsDz())
            .append("zcrq", getZcrq())
            .append("jynx", getJynx())
            .append("sbrgdzcqkpf", getSbrgdzcqkpf())
            .append("sbrzgxlpf", getSbrzgxlpf())
            .append("sbrhyzkpf", getSbrhyzkpf())
            .append("sbrqsqkpf", getSbrqsqkpf())
            .append("gtgshqsqkpf", getGtgshqsqkpf())
            .append("gtgshjlqkpf", getGtgshjlqkpf())
            .append("gtgshxsfcqkpf", getGtgshxsfcqkpf())
            .append("zpf", getZpf())
            .append("fxdj", getFxdj())
            .toString();
    }
}
