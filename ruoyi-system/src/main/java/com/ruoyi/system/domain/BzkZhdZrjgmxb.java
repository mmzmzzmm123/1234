package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 个体工商户信贷_标准库_准入监管明细对象 bzk_zhd_zrjgmxb
 * 
 * @author genius
 * @date 2021-11-30
 */
public class BzkZhdZrjgmxb extends BaseEntity
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

    /** 性别名称 */
    @Excel(name = "性别名称")
    private String xb;

    /** 年龄 */
    @Excel(name = "年龄")
    private String nl;

    /** 注册日期 */
    @Excel(name = "注册日期")
    private String zcrq;

    /** 注册地址 */
    @Excel(name = "注册地址")
    private String zcdz;

    /** 注册地址_行政区划 */
    @Excel(name = "注册地址_行政区划")
    private String zcdzXzqh;

    /** 登记状态名称 */
    @Excel(name = "登记状态名称")
    private String regstate;

    /** 重大税收违法案件_主要违法事实 */
    @Excel(name = "重大税收违法案件_主要违法事实")
    private String zywfss;

    /** 失信被执行人案号 */
    @Excel(name = "失信被执行人案号")
    private String sxbzxrah;

    /** 失信黑名单信息名称 */
    @Excel(name = "失信黑名单信息名称")
    private String sxhmdxxmc;

    /** 工商异常信息 */
    @Excel(name = "工商异常信息")
    private String gsycxx;

    /** 不动产查封 */
    @Excel(name = "不动产查封")
    private String bdccf;

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
    public void setXb(String xb) 
    {
        this.xb = xb;
    }

    public String getXb() 
    {
        return xb;
    }
    public void setNl(String nl) 
    {
        this.nl = nl;
    }

    public String getNl() 
    {
        return nl;
    }
    public void setZcrq(String zcrq) 
    {
        this.zcrq = zcrq;
    }

    public String getZcrq() 
    {
        return zcrq;
    }
    public void setZcdz(String zcdz) 
    {
        this.zcdz = zcdz;
    }

    public String getZcdz() 
    {
        return zcdz;
    }
    public void setZcdzXzqh(String zcdzXzqh) 
    {
        this.zcdzXzqh = zcdzXzqh;
    }

    public String getZcdzXzqh() 
    {
        return zcdzXzqh;
    }
    public void setRegstate(String regstate) 
    {
        this.regstate = regstate;
    }

    public String getRegstate() 
    {
        return regstate;
    }
    public void setZywfss(String zywfss) 
    {
        this.zywfss = zywfss;
    }

    public String getZywfss() 
    {
        return zywfss;
    }
    public void setSxbzxrah(String sxbzxrah) 
    {
        this.sxbzxrah = sxbzxrah;
    }

    public String getSxbzxrah() 
    {
        return sxbzxrah;
    }
    public void setSxhmdxxmc(String sxhmdxxmc) 
    {
        this.sxhmdxxmc = sxhmdxxmc;
    }

    public String getSxhmdxxmc() 
    {
        return sxhmdxxmc;
    }
    public void setGsycxx(String gsycxx) 
    {
        this.gsycxx = gsycxx;
    }

    public String getGsycxx() 
    {
        return gsycxx;
    }
    public void setBdccf(String bdccf) 
    {
        this.bdccf = bdccf;
    }

    public String getBdccf() 
    {
        return bdccf;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("tyshxydm", getTyshxydm())
            .append("sbgtgshxm", getSbgtgshxm())
            .append("zjlx", getZjlx())
            .append("zjhm", getZjhm())
            .append("xb", getXb())
            .append("nl", getNl())
            .append("zcrq", getZcrq())
            .append("zcdz", getZcdz())
            .append("zcdzXzqh", getZcdzXzqh())
            .append("regstate", getRegstate())
            .append("zywfss", getZywfss())
            .append("sxbzxrah", getSxbzxrah())
            .append("sxhmdxxmc", getSxhmdxxmc())
            .append("gsycxx", getGsycxx())
            .append("bdccf", getBdccf())
            .toString();
    }
}
