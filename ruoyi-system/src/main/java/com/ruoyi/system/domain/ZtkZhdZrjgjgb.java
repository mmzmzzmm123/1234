package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 个体工商户信贷_主题库_准入监管结果对象 ztk_zhd_zrjgjgb
 * 
 * @author genius
 * @date 2021-11-30
 */
public class ZtkZhdZrjgjgb extends BaseEntity
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

    /** 经营年限大于1年(0否1是) */
    @Excel(name = "经营年限大于1年(0否1是)")
    private Long jynx;

    /** 准入年龄_18至60男_55女(0否1是) */
    @Excel(name = "准入年龄_18至60男_55女(0否1是)")
    private Long zrnl;

    /** 非异地贷款_泉州市内(0否1是) */
    @Excel(name = "非异地贷款_泉州市内(0否1是)")
    private Long fyddk;

    /** 营业执照状态非吊销_停业_清算_注销(0否1是) */
    @Excel(name = "营业执照状态非吊销_停业_清算_注销(0否1是)")
    private Long regstate;

    /** 不存在重大税收违法案件(0否1是) */
    @Excel(name = "不存在重大税收违法案件(0否1是)")
    private Long zdsffwaj;

    /** 非失信被执行人(0否1是) */
    @Excel(name = "非失信被执行人(0否1是)")
    private Long sxbzxr;

    /** 不存在失信黑名单(0否1是) */
    @Excel(name = "不存在失信黑名单(0否1是)")
    private Long sxhmd;

    /** 不存在国家下发工商异常名录(0否1是) */
    @Excel(name = "不存在国家下发工商异常名录(0否1是)")
    private Long gjxfgsycml;

    /** 不存在不动产查封(0否1是) */
    @Excel(name = "不存在不动产查封(0否1是)")
    private Long bdccf;

    /** 是否准入(0否1是) */
    @Excel(name = "是否准入(0否1是)")
    private Long sfzr;

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
    public void setJynx(Long jynx) 
    {
        this.jynx = jynx;
    }

    public Long getJynx() 
    {
        return jynx;
    }
    public void setZrnl(Long zrnl) 
    {
        this.zrnl = zrnl;
    }

    public Long getZrnl() 
    {
        return zrnl;
    }
    public void setFyddk(Long fyddk) 
    {
        this.fyddk = fyddk;
    }

    public Long getFyddk() 
    {
        return fyddk;
    }
    public void setRegstate(Long regstate) 
    {
        this.regstate = regstate;
    }

    public Long getRegstate() 
    {
        return regstate;
    }
    public void setZdsffwaj(Long zdsffwaj) 
    {
        this.zdsffwaj = zdsffwaj;
    }

    public Long getZdsffwaj() 
    {
        return zdsffwaj;
    }
    public void setSxbzxr(Long sxbzxr) 
    {
        this.sxbzxr = sxbzxr;
    }

    public Long getSxbzxr() 
    {
        return sxbzxr;
    }
    public void setSxhmd(Long sxhmd) 
    {
        this.sxhmd = sxhmd;
    }

    public Long getSxhmd() 
    {
        return sxhmd;
    }
    public void setGjxfgsycml(Long gjxfgsycml) 
    {
        this.gjxfgsycml = gjxfgsycml;
    }

    public Long getGjxfgsycml() 
    {
        return gjxfgsycml;
    }
    public void setBdccf(Long bdccf) 
    {
        this.bdccf = bdccf;
    }

    public Long getBdccf() 
    {
        return bdccf;
    }
    public void setSfzr(Long sfzr) 
    {
        this.sfzr = sfzr;
    }

    public Long getSfzr() 
    {
        return sfzr;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("tyshxydm", getTyshxydm())
            .append("sbgtgshxm", getSbgtgshxm())
            .append("zjlx", getZjlx())
            .append("zjhm", getZjhm())
            .append("jynx", getJynx())
            .append("zrnl", getZrnl())
            .append("fyddk", getFyddk())
            .append("regstate", getRegstate())
            .append("zdsffwaj", getZdsffwaj())
            .append("sxbzxr", getSxbzxr())
            .append("sxhmd", getSxhmd())
            .append("gjxfgsycml", getGjxfgsycml())
            .append("bdccf", getBdccf())
            .append("sfzr", getSfzr())
            .toString();
    }
}
