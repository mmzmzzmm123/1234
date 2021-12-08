package com.ruoyi.system.domain;

import com.ruoyi.system.domain.vo.Bdcq;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Set;

/**
 * 个体工商户信贷_标准库_评分模型明细对象 bzk_zhd_pfmxmxb
 * 
 * @author genius
 * @date 2021-11-30
 */
public class BzkZhdPfmxmxb extends BaseEntity
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

    /** 经营年限 */
    @Excel(name = "经营年限")
    private String jynx;

    /** 不动产权证号 */
    @Excel(name = "不动产权证号")
    private String bdcqzh;

    /** 不动产共有方式 */
    @Excel(name = "不动产共有方式")
    private String bdcgyfs;

    /** 不动产坐落 */
    @Excel(name = "不动产坐落")
    private String bdczl;

    /** 不动产产权状态 */
    @Excel(name = "不动产产权状态")
    private String bdccqzt;

    /** 不动产建筑面积 */
    @Excel(name = "不动产建筑面积")
    private String bdcjzmj;

    /** 不动产用途 */
    @Excel(name = "不动产用途")
    private String bdcyt;

    /** 最高学历 */
    @Excel(name = "最高学历")
    private String zgxl;

    /** 婚姻状况 */
    @Excel(name = "婚姻状况")
    private String hyzk;

    /** 自然人欠税税种 */
    @Excel(name = "自然人欠税税种")
    private String zrrqssz;

    /** 自然人欠税统计开始日期 */
    @Excel(name = "自然人欠税统计开始日期")
    private String zrrqstjksrq;

    /** 自然人欠税统计截止日期 */
    @Excel(name = "自然人欠税统计截止日期")
    private String zrrqstjjzrq;

    /** 自然人欠税余额(元) */
    @Excel(name = "自然人欠税余额(元)")
    private String zrrqsye;

    /** 自然人是否补缴 */
    @Excel(name = "自然人是否补缴")
    private String zrrsfbj;

    /** 法人欠税税种 */
    @Excel(name = "法人欠税税种")
    private String frqssz;

    /** 法人欠税统计开始日期 */
    @Excel(name = "法人欠税统计开始日期")
    private String frqstjksrq;

    /** 法人欠税统计截止日期 */
    @Excel(name = "法人欠税统计截止日期")
    private String frqstjjzrq;

    /** 法人当前欠税金额(元) */
    @Excel(name = "法人当前欠税金额(元)")
    private String frdqqsje;

    /** 法人是否补缴 */
    @Excel(name = "法人是否补缴")
    private String frsfbj;

    /** 激励措施内容 */
    @Excel(name = "激励措施内容")
    private String jlcsnr;

    /** 激励名单名称 */
    @Excel(name = "激励名单名称")
    private String jlmdmc;

    /** 享受扶持政策依据 */
    @Excel(name = "享受扶持政策依据")
    private String xsfczcyj;

    /** 享受扶持政策内容 */
    @Excel(name = "享受扶持政策内容")
    private String xsfczcnr;

    /** 享受扶持政策的数额 */
    @Excel(name = "享受扶持政策的数额")
    private String xsfczcdse;

    /** 享受扶持政策的部门 */
    @Excel(name = "享受扶持政策的部门")
    private String xsfczcdbm;

    /** 实施扶持政策日期 */
    @Excel(name = "实施扶持政策日期")
    private String ssfczcrq;

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
    public void setJynx(String jynx) 
    {
        this.jynx = jynx;
    }

    public String getJynx() 
    {
        return jynx;
    }
    public void setBdcqzh(String bdcqzh) 
    {
        this.bdcqzh = bdcqzh;
    }

    /**
     * 不动产权信息为一对多
     */
    Set<Bdcq> bdcqs;

    public String getBdcqzh() 
    {
        return bdcqzh;
    }
    public void setBdcgyfs(String bdcgyfs) 
    {
        this.bdcgyfs = bdcgyfs;
    }

    public String getBdcgyfs() 
    {
        return bdcgyfs;
    }
    public void setBdczl(String bdczl) 
    {
        this.bdczl = bdczl;
    }

    public String getBdczl() 
    {
        return bdczl;
    }
    public void setBdccqzt(String bdccqzt) 
    {
        this.bdccqzt = bdccqzt;
    }

    public String getBdccqzt() 
    {
        return bdccqzt;
    }
    public void setBdcjzmj(String bdcjzmj) 
    {
        this.bdcjzmj = bdcjzmj;
    }

    public String getBdcjzmj() 
    {
        return bdcjzmj;
    }
    public void setBdcyt(String bdcyt) 
    {
        this.bdcyt = bdcyt;
    }

    public String getBdcyt() 
    {
        return bdcyt;
    }
    public void setZgxl(String zgxl) 
    {
        this.zgxl = zgxl;
    }

    public String getZgxl() 
    {
        return zgxl;
    }
    public void setHyzk(String hyzk) 
    {
        this.hyzk = hyzk;
    }

    public String getHyzk() 
    {
        return hyzk;
    }
    public void setZrrqssz(String zrrqssz) 
    {
        this.zrrqssz = zrrqssz;
    }

    public String getZrrqssz() 
    {
        return zrrqssz;
    }
    public void setZrrqstjksrq(String zrrqstjksrq) 
    {
        this.zrrqstjksrq = zrrqstjksrq;
    }

    public String getZrrqstjksrq() 
    {
        return zrrqstjksrq;
    }
    public void setZrrqstjjzrq(String zrrqstjjzrq) 
    {
        this.zrrqstjjzrq = zrrqstjjzrq;
    }

    public String getZrrqstjjzrq() 
    {
        return zrrqstjjzrq;
    }
    public void setZrrqsye(String zrrqsye) 
    {
        this.zrrqsye = zrrqsye;
    }

    public String getZrrqsye() 
    {
        return zrrqsye;
    }
    public void setZrrsfbj(String zrrsfbj) 
    {
        this.zrrsfbj = zrrsfbj;
    }

    public String getZrrsfbj() 
    {
        return zrrsfbj;
    }
    public void setFrqssz(String frqssz) 
    {
        this.frqssz = frqssz;
    }

    public String getFrqssz() 
    {
        return frqssz;
    }
    public void setFrqstjksrq(String frqstjksrq) 
    {
        this.frqstjksrq = frqstjksrq;
    }

    public String getFrqstjksrq() 
    {
        return frqstjksrq;
    }
    public void setFrqstjjzrq(String frqstjjzrq) 
    {
        this.frqstjjzrq = frqstjjzrq;
    }

    public String getFrqstjjzrq() 
    {
        return frqstjjzrq;
    }
    public void setFrdqqsje(String frdqqsje) 
    {
        this.frdqqsje = frdqqsje;
    }

    public String getFrdqqsje() 
    {
        return frdqqsje;
    }
    public void setFrsfbj(String frsfbj) 
    {
        this.frsfbj = frsfbj;
    }

    public String getFrsfbj() 
    {
        return frsfbj;
    }
    public void setJlcsnr(String jlcsnr) 
    {
        this.jlcsnr = jlcsnr;
    }

    public String getJlcsnr() 
    {
        return jlcsnr;
    }
    public void setJlmdmc(String jlmdmc) 
    {
        this.jlmdmc = jlmdmc;
    }

    public String getJlmdmc() 
    {
        return jlmdmc;
    }
    public void setXsfczcyj(String xsfczcyj) 
    {
        this.xsfczcyj = xsfczcyj;
    }

    public String getXsfczcyj() 
    {
        return xsfczcyj;
    }
    public void setXsfczcnr(String xsfczcnr) 
    {
        this.xsfczcnr = xsfczcnr;
    }

    public String getXsfczcnr() 
    {
        return xsfczcnr;
    }
    public void setXsfczcdse(String xsfczcdse) 
    {
        this.xsfczcdse = xsfczcdse;
    }

    public String getXsfczcdse() 
    {
        return xsfczcdse;
    }
    public void setXsfczcdbm(String xsfczcdbm) 
    {
        this.xsfczcdbm = xsfczcdbm;
    }

    public String getXsfczcdbm() 
    {
        return xsfczcdbm;
    }
    public void setSsfczcrq(String ssfczcrq) 
    {
        this.ssfczcrq = ssfczcrq;
    }

    public String getSsfczcrq() 
    {
        return ssfczcrq;
    }

    public Set<Bdcq> getBdcqs() {
        return bdcqs;
    }

    public void setBdcqs(Set<Bdcq> bdcqs) {
        this.bdcqs = bdcqs;
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
            .append("bdcqzh", getBdcqzh())
            .append("bdcgyfs", getBdcgyfs())
            .append("bdczl", getBdczl())
            .append("bdccqzt", getBdccqzt())
            .append("bdcjzmj", getBdcjzmj())
            .append("bdcyt", getBdcyt())
            .append("zgxl", getZgxl())
            .append("hyzk", getHyzk())
            .append("zrrqssz", getZrrqssz())
            .append("zrrqstjksrq", getZrrqstjksrq())
            .append("zrrqstjjzrq", getZrrqstjjzrq())
            .append("zrrqsye", getZrrqsye())
            .append("zrrsfbj", getZrrsfbj())
            .append("frqssz", getFrqssz())
            .append("frqstjksrq", getFrqstjksrq())
            .append("frqstjjzrq", getFrqstjjzrq())
            .append("frdqqsje", getFrdqqsje())
            .append("frsfbj", getFrsfbj())
            .append("jlcsnr", getJlcsnr())
            .append("jlmdmc", getJlmdmc())
            .append("xsfczcyj", getXsfczcyj())
            .append("xsfczcnr", getXsfczcnr())
            .append("xsfczcdse", getXsfczcdse())
            .append("xsfczcdbm", getXsfczcdbm())
            .append("ssfczcrq", getSsfczcrq())
            .toString();
    }
}
