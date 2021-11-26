package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 个体工商户信贷_主题库_准入监管对象 ztk_zbh_zrjgb
 * 
 * @author genius
 * @date 2021-11-26
 */
public class ZtkZbhZrjgb extends BaseEntity
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

    /** 经营年限 */
    @Excel(name = "经营年限")
    private String jynx;

    /** 法人年龄 */
    @Excel(name = "法人年龄")
    private String frnl;

    /** 注册地址 */
    @Excel(name = "注册地址")
    private String zcdz;

    /** 重大税收违法案件 */
    @Excel(name = "重大税收违法案件")
    private String zdsffwaj;

    /** 失信被执行人 */
    @Excel(name = "失信被执行人")
    private String sxbzxr;

    /** 失信黑名单 */
    @Excel(name = "失信黑名单")
    private String sfhmd;

    /** 国家下发工商异常名录 */
    @Excel(name = "国家下发工商异常名录")
    private String gjxfgsycml;

    /** 不动产查封 */
    @Excel(name = "不动产查封")
    private String bdccf;

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
    public void setJynx(String jynx) 
    {
        this.jynx = jynx;
    }

    public String getJynx() 
    {
        return jynx;
    }
    public void setFrnl(String frnl) 
    {
        this.frnl = frnl;
    }

    public String getFrnl() 
    {
        return frnl;
    }
    public void setZcdz(String zcdz) 
    {
        this.zcdz = zcdz;
    }

    public String getZcdz() 
    {
        return zcdz;
    }
    public void setZdsffwaj(String zdsffwaj) 
    {
        this.zdsffwaj = zdsffwaj;
    }

    public String getZdsffwaj() 
    {
        return zdsffwaj;
    }
    public void setSxbzxr(String sxbzxr) 
    {
        this.sxbzxr = sxbzxr;
    }

    public String getSxbzxr() 
    {
        return sxbzxr;
    }
    public void setSfhmd(String sfhmd) 
    {
        this.sfhmd = sfhmd;
    }

    public String getSfhmd() 
    {
        return sfhmd;
    }
    public void setGjxfgsycml(String gjxfgsycml) 
    {
        this.gjxfgsycml = gjxfgsycml;
    }

    public String getGjxfgsycml() 
    {
        return gjxfgsycml;
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
            .append("jynx", getJynx())
            .append("frnl", getFrnl())
            .append("zcdz", getZcdz())
            .append("zdsffwaj", getZdsffwaj())
            .append("sxbzxr", getSxbzxr())
            .append("sfhmd", getSfhmd())
            .append("gjxfgsycml", getGjxfgsycml())
            .append("bdccf", getBdccf())
            .toString();
    }
}
