package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 个体工商户信贷_标准库_准入监管对象 bzk_zbh_zrjgb
 * 
 * @author genius
 * @date 2021-11-26
 */
public class BzkZbhZrjgb extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 标准库主记录ID */
    private String zjlid;

    /** 来源应用系统编号 */
    @Excel(name = "来源应用系统编号")
    private String lyyyxtid;

    /** 来源表 */
    @Excel(name = "来源表")
    private String dataFrom;

    /** 信息入库时间 */
    @Excel(name = "信息入库时间")
    private String xxrksj;

    /** 信息入库时间戳 */
    @Excel(name = "信息入库时间戳")
    private Long xxrksjTmsp;

    /** 标准库_备注 */
    @Excel(name = "标准库_备注")
    private String bz;

    /** 来源表信息入库时间 */
    @Excel(name = "来源表信息入库时间")
    private String sourceXxrksj;

    /** 来源表主记录ID */
    @Excel(name = "来源表主记录ID")
    private String sourceZjlid;

    /** 业务标签ID */
    @Excel(name = "业务标签ID")
    private String businessFlagId;

    /** 业务标签值 */
    @Excel(name = "业务标签值")
    private String businessFlagValue;

    /** 首次采集时间 */
    @Excel(name = "首次采集时间")
    private Long firstCollectTime;

    /** 最近采集时间 */
    @Excel(name = "最近采集时间")
    private Long lastCollectTime;

    /** 发现次数 */
    @Excel(name = "发现次数")
    private Long foundTimes;

    /** 错误数据标识 */
    @Excel(name = "错误数据标识")
    private String errorDataFlag;

    /** 错误数据 */
    @Excel(name = "错误数据")
    private String errorData;

    /** 异常字段编码 */
    @Excel(name = "异常字段编码")
    private String errorElement;

    /** 数据批次标识 */
    @Excel(name = "数据批次标识")
    private String batchDataFlag;

    /** 数据安全级别代码 */
    @Excel(name = "数据安全级别代码")
    private String sjaqjbdm;

    /** 采集地 */
    @Excel(name = "采集地")
    private String cjd;

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

    public void setZjlid(String zjlid) 
    {
        this.zjlid = zjlid;
    }

    public String getZjlid() 
    {
        return zjlid;
    }
    public void setLyyyxtid(String lyyyxtid) 
    {
        this.lyyyxtid = lyyyxtid;
    }

    public String getLyyyxtid() 
    {
        return lyyyxtid;
    }
    public void setDataFrom(String dataFrom) 
    {
        this.dataFrom = dataFrom;
    }

    public String getDataFrom() 
    {
        return dataFrom;
    }
    public void setXxrksj(String xxrksj) 
    {
        this.xxrksj = xxrksj;
    }

    public String getXxrksj() 
    {
        return xxrksj;
    }
    public void setXxrksjTmsp(Long xxrksjTmsp) 
    {
        this.xxrksjTmsp = xxrksjTmsp;
    }

    public Long getXxrksjTmsp() 
    {
        return xxrksjTmsp;
    }
    public void setBz(String bz) 
    {
        this.bz = bz;
    }

    public String getBz() 
    {
        return bz;
    }
    public void setSourceXxrksj(String sourceXxrksj) 
    {
        this.sourceXxrksj = sourceXxrksj;
    }

    public String getSourceXxrksj() 
    {
        return sourceXxrksj;
    }
    public void setSourceZjlid(String sourceZjlid) 
    {
        this.sourceZjlid = sourceZjlid;
    }

    public String getSourceZjlid() 
    {
        return sourceZjlid;
    }
    public void setBusinessFlagId(String businessFlagId) 
    {
        this.businessFlagId = businessFlagId;
    }

    public String getBusinessFlagId() 
    {
        return businessFlagId;
    }
    public void setBusinessFlagValue(String businessFlagValue) 
    {
        this.businessFlagValue = businessFlagValue;
    }

    public String getBusinessFlagValue() 
    {
        return businessFlagValue;
    }
    public void setFirstCollectTime(Long firstCollectTime) 
    {
        this.firstCollectTime = firstCollectTime;
    }

    public Long getFirstCollectTime() 
    {
        return firstCollectTime;
    }
    public void setLastCollectTime(Long lastCollectTime) 
    {
        this.lastCollectTime = lastCollectTime;
    }

    public Long getLastCollectTime() 
    {
        return lastCollectTime;
    }
    public void setFoundTimes(Long foundTimes) 
    {
        this.foundTimes = foundTimes;
    }

    public Long getFoundTimes() 
    {
        return foundTimes;
    }
    public void setErrorDataFlag(String errorDataFlag) 
    {
        this.errorDataFlag = errorDataFlag;
    }

    public String getErrorDataFlag() 
    {
        return errorDataFlag;
    }
    public void setErrorData(String errorData) 
    {
        this.errorData = errorData;
    }

    public String getErrorData() 
    {
        return errorData;
    }
    public void setErrorElement(String errorElement) 
    {
        this.errorElement = errorElement;
    }

    public String getErrorElement() 
    {
        return errorElement;
    }
    public void setBatchDataFlag(String batchDataFlag) 
    {
        this.batchDataFlag = batchDataFlag;
    }

    public String getBatchDataFlag() 
    {
        return batchDataFlag;
    }
    public void setSjaqjbdm(String sjaqjbdm) 
    {
        this.sjaqjbdm = sjaqjbdm;
    }

    public String getSjaqjbdm() 
    {
        return sjaqjbdm;
    }
    public void setCjd(String cjd) 
    {
        this.cjd = cjd;
    }

    public String getCjd() 
    {
        return cjd;
    }
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("zjlid", getZjlid())
            .append("lyyyxtid", getLyyyxtid())
            .append("dataFrom", getDataFrom())
            .append("xxrksj", getXxrksj())
            .append("xxrksjTmsp", getXxrksjTmsp())
            .append("bz", getBz())
            .append("sourceXxrksj", getSourceXxrksj())
            .append("sourceZjlid", getSourceZjlid())
            .append("businessFlagId", getBusinessFlagId())
            .append("businessFlagValue", getBusinessFlagValue())
            .append("firstCollectTime", getFirstCollectTime())
            .append("lastCollectTime", getLastCollectTime())
            .append("foundTimes", getFoundTimes())
            .append("errorDataFlag", getErrorDataFlag())
            .append("errorData", getErrorData())
            .append("errorElement", getErrorElement())
            .append("batchDataFlag", getBatchDataFlag())
            .append("sjaqjbdm", getSjaqjbdm())
            .append("cjd", getCjd())
            .append("tyshxydm", getTyshxydm())
            .append("sbgtgshxm", getSbgtgshxm())
            .append("zjlx", getZjlx())
            .append("zjhm", getZjhm())
            .append("zcrq", getZcrq())
            .append("zcdz", getZcdz())
            .append("zcdzXzqh", getZcdzXzqh())
            .append("regstate", getRegstate())
            .append("zywfss", getZywfss())
            .append("sxbzxrah", getSxbzxrah())
            .append("sxhmdxxmc", getSxhmdxxmc())
            .append("gsycxx", getGsycxx())
            .toString();
    }
}
