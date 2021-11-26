package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 福建省泉州市个体工商户信贷_主题库_评分模型对象 ztk_zhd_pfmxb
 * 
 * @author genius
 * @date 2021-11-26
 */
public class ZtkZhdPfmxb extends BaseEntity
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

    /** 证件类型 */
    @Excel(name = "证件类型")
    private String zjlx;

    /** 证件号码 */
    @Excel(name = "证件号码")
    private String zjhm;

    /** 生产经营地址 */
    @Excel(name = "生产经营地址")
    private String scjydz;

    /** 注册(机构住所生产经营地址)地址 */
    @Excel(name = "注册(机构住所生产经营地址)地址")
    private String zcjgzsscjydzdz;

    /** 不动产登记证明 */
    @Excel(name = "不动产登记证明")
    private String bdcdjzm;

    /** 抵押物价值 */
    @Excel(name = "抵押物价值")
    private String dywjz;

    /** 个体店名 */
    @Excel(name = "个体店名")
    private String gtdm;

    /** 经营时间 */
    @Excel(name = "经营时间")
    private String jysj;

    /** 女方身份证件类型 */
    @Excel(name = "女方身份证件类型")
    private String nfsfzjlx;

    /** 女方姓名 */
    @Excel(name = "女方姓名")
    private String nfxm;

    /** 女方身份证件号码 */
    @Excel(name = "女方身份证件号码")
    private String nfsfzjhm;

    /** 档案号 */
    @Excel(name = "档案号")
    private String dah;

    /** 结婚登记时间 */
    @Excel(name = "结婚登记时间")
    private String jhdjsj;

    /** 结婚登记类型 */
    @Excel(name = "结婚登记类型")
    private String jhdjlx;

    /** 学历(文化程度) */
    @Excel(name = "学历(文化程度)")
    private String xlwhcd;

    /** 专业技术职务系列名称 */
    @Excel(name = "专业技术职务系列名称")
    private String zyjszwxlmc;

    /** 专业技术职务等级 */
    @Excel(name = "专业技术职务等级")
    private String zyjszwdj;

    /** 国家职业资格等级 */
    @Excel(name = "国家职业资格等级")
    private String gjzyzgdj;

    /** 欠费金额(元) */
    @Excel(name = "欠费金额(元)")
    private String qfjey;

    /** 欠费时段 */
    @Excel(name = "欠费时段")
    private String qfsd;

    /** 当期欠税金额(元) */
    @Excel(name = "当期欠税金额(元)")
    private String dqqsjey;

    /** 欠税统计截止日期 */
    @Excel(name = "欠税统计截止日期")
    private String qstjjzrq;

    /** 欠税统计开始日期 */
    @Excel(name = "欠税统计开始日期")
    private String qstjksrq;

    /** 失信严重程度 */
    @Excel(name = "失信严重程度")
    private String sxyzcd;

    /** 是否补缴 */
    @Excel(name = "是否补缴")
    private String sfbj;

    /** 欠税税种 */
    @Excel(name = "欠税税种")
    private String qssz;

    /** 欠税余额 */
    @Excel(name = "欠税余额")
    private String qsye;

    /** 逃避追缴欠费行政处罚主要违法事实 */
    @Excel(name = "逃避追缴欠费行政处罚主要违法事实")
    private String tbzjqfxzcfzywfss;

    /** 逃避追缴欠费行政处罚处罚情况 */
    @Excel(name = "逃避追缴欠费行政处罚处罚情况")
    private String tbzjqfxzcfcfqk;

    /** 自然人欠税信息欠税税种 */
    @Excel(name = "自然人欠税信息欠税税种")
    private String zrrqsxxqssz;

    /** 自然人欠税统计截止日期 */
    @Excel(name = "自然人欠税统计截止日期")
    private String zrrqstjjzrq;

    /** 自然人欠税统计开始日期 */
    @Excel(name = "自然人欠税统计开始日期")
    private String zrrqstjksrq;

    /** 自然人欠税余额 */
    @Excel(name = "自然人欠税余额")
    private String zrrqsye;

    /** 自然人是否补缴 */
    @Excel(name = "自然人是否补缴")
    private String zrrsfbj;

    /** 国家联合奖惩信息更新时间 */
    @Excel(name = "国家联合奖惩信息更新时间")
    private String gjlhjcxxgxsj;

    /** 国家联合奖惩信息名单类型 */
    @Excel(name = "国家联合奖惩信息名单类型")
    private String gjlhjcxxmdlx;

    /** 联合奖惩黑名单名称 */
    @Excel(name = "联合奖惩黑名单名称")
    private String lhjchmdmc;

    /** 荣誉表彰事由 */
    @Excel(name = "荣誉表彰事由")
    private String rybzsy;

    /** 荣誉表彰名称 */
    @Excel(name = "荣誉表彰名称")
    private String rybzmc;

    /** 荣誉表彰级别 */
    @Excel(name = "荣誉表彰级别")
    private String rybzjb;

    /** 守信企业注册号 */
    @Excel(name = "守信企业注册号")
    private String sxqyzch;

    /** 守信企业或法人组织名称 */
    @Excel(name = "守信企业或法人组织名称")
    private String sxqyhfrzzmc;

    /** 支付工资评级级别(字典转后) */
    @Excel(name = "支付工资评级级别(字典转后)")
    private String zfgzpjjbzdzh;

    /** 支付工资决定书文号 */
    @Excel(name = "支付工资决定书文号")
    private String zfgzjdswh;

    /** 纳税人信用等级 */
    @Excel(name = "纳税人信用等级")
    private String nsrxydj;

    /** 百强企业企业名称 */
    @Excel(name = "百强企业企业名称")
    private String bqqyqymc;

    /** 机动车所有人 */
    @Excel(name = "机动车所有人")
    private String jdcsyr;

    /** 车辆类型 */
    @Excel(name = "车辆类型")
    private String cllx;

    /** 机动车状态 */
    @Excel(name = "机动车状态")
    private String jdczt;

    /** 所有权 */
    @Excel(name = "所有权")
    private String syq;

    /** 使用性质 */
    @Excel(name = "使用性质")
    private String syxz;

    /** 不动产登记证号 */
    @Excel(name = "不动产登记证号")
    private String bdcdjzh;

    /** 查封类型 */
    @Excel(name = "查封类型")
    private String cflx;

    /** 查封文件 */
    @Excel(name = "查封文件")
    private String cfwj;

    /** 查封起始时间 */
    @Excel(name = "查封起始时间")
    private String cfqssj;

    /** 查封结束时间 */
    @Excel(name = "查封结束时间")
    private String cfjssj;

    /** 解封机关 */
    @Excel(name = "解封机关")
    private String jfjg;

    /** 解封文件 */
    @Excel(name = "解封文件")
    private String jfwj;

    /** 缴纳单位统一社会信用代码 */
    @Excel(name = "缴纳单位统一社会信用代码")
    private String jndwtyshxydm;

    /** 缴费ID */
    @Excel(name = "缴费ID")
    private String jfid;

    /** 缴费时间 */
    @Excel(name = "缴费时间")
    private String jfsj;

    /** 拖欠农民工工资决定书文号 */
    @Excel(name = "拖欠农民工工资决定书文号")
    private String tqnmggzjdswh;

    /** 拖欠农民工工资黑名单ID */
    @Excel(name = "拖欠农民工工资黑名单ID")
    private String tqnmggzhmdid;

    /** 失信债务案号 */
    @Excel(name = "失信债务案号")
    private String sxzwah;

    /** 支付工资失信信用评价ID */
    @Excel(name = "支付工资失信信用评价ID")
    private String zfgzsxxypjid;

    /** 支付工资失信决定机关 */
    @Excel(name = "支付工资失信决定机关")
    private String zfgzsxjdjg;

    /** 政务失信案号 */
    @Excel(name = "政务失信案号")
    private String zwsxah;

    /** 政务失信核查情况 */
    @Excel(name = "政务失信核查情况")
    private String zwsxhcqk;

    /** 联合惩戒人员类别 */
    @Excel(name = "联合惩戒人员类别")
    private String lhcjrylb;

    /** 联合惩戒注册号 */
    @Excel(name = "联合惩戒注册号")
    private String lhcjzch;

    /** 环境处罚事由 */
    @Excel(name = "环境处罚事由")
    private String hjcfsy;

    /** 环境处罚依据 */
    @Excel(name = "环境处罚依据")
    private String hjcfyj;

    /** 环境处罚日期 */
    @Excel(name = "环境处罚日期")
    private String hjcfrq;

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
    public void setScjydz(String scjydz) 
    {
        this.scjydz = scjydz;
    }

    public String getScjydz() 
    {
        return scjydz;
    }
    public void setZcjgzsscjydzdz(String zcjgzsscjydzdz) 
    {
        this.zcjgzsscjydzdz = zcjgzsscjydzdz;
    }

    public String getZcjgzsscjydzdz() 
    {
        return zcjgzsscjydzdz;
    }
    public void setBdcdjzm(String bdcdjzm) 
    {
        this.bdcdjzm = bdcdjzm;
    }

    public String getBdcdjzm() 
    {
        return bdcdjzm;
    }
    public void setDywjz(String dywjz) 
    {
        this.dywjz = dywjz;
    }

    public String getDywjz() 
    {
        return dywjz;
    }
    public void setGtdm(String gtdm) 
    {
        this.gtdm = gtdm;
    }

    public String getGtdm() 
    {
        return gtdm;
    }
    public void setJysj(String jysj) 
    {
        this.jysj = jysj;
    }

    public String getJysj() 
    {
        return jysj;
    }
    public void setNfsfzjlx(String nfsfzjlx) 
    {
        this.nfsfzjlx = nfsfzjlx;
    }

    public String getNfsfzjlx() 
    {
        return nfsfzjlx;
    }
    public void setNfxm(String nfxm) 
    {
        this.nfxm = nfxm;
    }

    public String getNfxm() 
    {
        return nfxm;
    }
    public void setNfsfzjhm(String nfsfzjhm) 
    {
        this.nfsfzjhm = nfsfzjhm;
    }

    public String getNfsfzjhm() 
    {
        return nfsfzjhm;
    }
    public void setDah(String dah) 
    {
        this.dah = dah;
    }

    public String getDah() 
    {
        return dah;
    }
    public void setJhdjsj(String jhdjsj) 
    {
        this.jhdjsj = jhdjsj;
    }

    public String getJhdjsj() 
    {
        return jhdjsj;
    }
    public void setJhdjlx(String jhdjlx) 
    {
        this.jhdjlx = jhdjlx;
    }

    public String getJhdjlx() 
    {
        return jhdjlx;
    }
    public void setXlwhcd(String xlwhcd) 
    {
        this.xlwhcd = xlwhcd;
    }

    public String getXlwhcd() 
    {
        return xlwhcd;
    }
    public void setZyjszwxlmc(String zyjszwxlmc) 
    {
        this.zyjszwxlmc = zyjszwxlmc;
    }

    public String getZyjszwxlmc() 
    {
        return zyjszwxlmc;
    }
    public void setZyjszwdj(String zyjszwdj) 
    {
        this.zyjszwdj = zyjszwdj;
    }

    public String getZyjszwdj() 
    {
        return zyjszwdj;
    }
    public void setGjzyzgdj(String gjzyzgdj) 
    {
        this.gjzyzgdj = gjzyzgdj;
    }

    public String getGjzyzgdj() 
    {
        return gjzyzgdj;
    }
    public void setQfjey(String qfjey) 
    {
        this.qfjey = qfjey;
    }

    public String getQfjey() 
    {
        return qfjey;
    }
    public void setQfsd(String qfsd) 
    {
        this.qfsd = qfsd;
    }

    public String getQfsd() 
    {
        return qfsd;
    }
    public void setDqqsjey(String dqqsjey) 
    {
        this.dqqsjey = dqqsjey;
    }

    public String getDqqsjey() 
    {
        return dqqsjey;
    }
    public void setQstjjzrq(String qstjjzrq) 
    {
        this.qstjjzrq = qstjjzrq;
    }

    public String getQstjjzrq() 
    {
        return qstjjzrq;
    }
    public void setQstjksrq(String qstjksrq) 
    {
        this.qstjksrq = qstjksrq;
    }

    public String getQstjksrq() 
    {
        return qstjksrq;
    }
    public void setSxyzcd(String sxyzcd) 
    {
        this.sxyzcd = sxyzcd;
    }

    public String getSxyzcd() 
    {
        return sxyzcd;
    }
    public void setSfbj(String sfbj) 
    {
        this.sfbj = sfbj;
    }

    public String getSfbj() 
    {
        return sfbj;
    }
    public void setQssz(String qssz) 
    {
        this.qssz = qssz;
    }

    public String getQssz() 
    {
        return qssz;
    }
    public void setQsye(String qsye) 
    {
        this.qsye = qsye;
    }

    public String getQsye() 
    {
        return qsye;
    }
    public void setTbzjqfxzcfzywfss(String tbzjqfxzcfzywfss) 
    {
        this.tbzjqfxzcfzywfss = tbzjqfxzcfzywfss;
    }

    public String getTbzjqfxzcfzywfss() 
    {
        return tbzjqfxzcfzywfss;
    }
    public void setTbzjqfxzcfcfqk(String tbzjqfxzcfcfqk) 
    {
        this.tbzjqfxzcfcfqk = tbzjqfxzcfcfqk;
    }

    public String getTbzjqfxzcfcfqk() 
    {
        return tbzjqfxzcfcfqk;
    }
    public void setZrrqsxxqssz(String zrrqsxxqssz) 
    {
        this.zrrqsxxqssz = zrrqsxxqssz;
    }

    public String getZrrqsxxqssz() 
    {
        return zrrqsxxqssz;
    }
    public void setZrrqstjjzrq(String zrrqstjjzrq) 
    {
        this.zrrqstjjzrq = zrrqstjjzrq;
    }

    public String getZrrqstjjzrq() 
    {
        return zrrqstjjzrq;
    }
    public void setZrrqstjksrq(String zrrqstjksrq) 
    {
        this.zrrqstjksrq = zrrqstjksrq;
    }

    public String getZrrqstjksrq() 
    {
        return zrrqstjksrq;
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
    public void setGjlhjcxxgxsj(String gjlhjcxxgxsj) 
    {
        this.gjlhjcxxgxsj = gjlhjcxxgxsj;
    }

    public String getGjlhjcxxgxsj() 
    {
        return gjlhjcxxgxsj;
    }
    public void setGjlhjcxxmdlx(String gjlhjcxxmdlx) 
    {
        this.gjlhjcxxmdlx = gjlhjcxxmdlx;
    }

    public String getGjlhjcxxmdlx() 
    {
        return gjlhjcxxmdlx;
    }
    public void setLhjchmdmc(String lhjchmdmc) 
    {
        this.lhjchmdmc = lhjchmdmc;
    }

    public String getLhjchmdmc() 
    {
        return lhjchmdmc;
    }
    public void setRybzsy(String rybzsy) 
    {
        this.rybzsy = rybzsy;
    }

    public String getRybzsy() 
    {
        return rybzsy;
    }
    public void setRybzmc(String rybzmc) 
    {
        this.rybzmc = rybzmc;
    }

    public String getRybzmc() 
    {
        return rybzmc;
    }
    public void setRybzjb(String rybzjb) 
    {
        this.rybzjb = rybzjb;
    }

    public String getRybzjb() 
    {
        return rybzjb;
    }
    public void setSxqyzch(String sxqyzch) 
    {
        this.sxqyzch = sxqyzch;
    }

    public String getSxqyzch() 
    {
        return sxqyzch;
    }
    public void setSxqyhfrzzmc(String sxqyhfrzzmc) 
    {
        this.sxqyhfrzzmc = sxqyhfrzzmc;
    }

    public String getSxqyhfrzzmc() 
    {
        return sxqyhfrzzmc;
    }
    public void setZfgzpjjbzdzh(String zfgzpjjbzdzh) 
    {
        this.zfgzpjjbzdzh = zfgzpjjbzdzh;
    }

    public String getZfgzpjjbzdzh() 
    {
        return zfgzpjjbzdzh;
    }
    public void setZfgzjdswh(String zfgzjdswh) 
    {
        this.zfgzjdswh = zfgzjdswh;
    }

    public String getZfgzjdswh() 
    {
        return zfgzjdswh;
    }
    public void setNsrxydj(String nsrxydj) 
    {
        this.nsrxydj = nsrxydj;
    }

    public String getNsrxydj() 
    {
        return nsrxydj;
    }
    public void setBqqyqymc(String bqqyqymc) 
    {
        this.bqqyqymc = bqqyqymc;
    }

    public String getBqqyqymc() 
    {
        return bqqyqymc;
    }
    public void setJdcsyr(String jdcsyr) 
    {
        this.jdcsyr = jdcsyr;
    }

    public String getJdcsyr() 
    {
        return jdcsyr;
    }
    public void setCllx(String cllx) 
    {
        this.cllx = cllx;
    }

    public String getCllx() 
    {
        return cllx;
    }
    public void setJdczt(String jdczt) 
    {
        this.jdczt = jdczt;
    }

    public String getJdczt() 
    {
        return jdczt;
    }
    public void setSyq(String syq) 
    {
        this.syq = syq;
    }

    public String getSyq() 
    {
        return syq;
    }
    public void setSyxz(String syxz) 
    {
        this.syxz = syxz;
    }

    public String getSyxz() 
    {
        return syxz;
    }
    public void setBdcdjzh(String bdcdjzh) 
    {
        this.bdcdjzh = bdcdjzh;
    }

    public String getBdcdjzh() 
    {
        return bdcdjzh;
    }
    public void setCflx(String cflx) 
    {
        this.cflx = cflx;
    }

    public String getCflx() 
    {
        return cflx;
    }
    public void setCfwj(String cfwj) 
    {
        this.cfwj = cfwj;
    }

    public String getCfwj() 
    {
        return cfwj;
    }
    public void setCfqssj(String cfqssj) 
    {
        this.cfqssj = cfqssj;
    }

    public String getCfqssj() 
    {
        return cfqssj;
    }
    public void setCfjssj(String cfjssj) 
    {
        this.cfjssj = cfjssj;
    }

    public String getCfjssj() 
    {
        return cfjssj;
    }
    public void setJfjg(String jfjg) 
    {
        this.jfjg = jfjg;
    }

    public String getJfjg() 
    {
        return jfjg;
    }
    public void setJfwj(String jfwj) 
    {
        this.jfwj = jfwj;
    }

    public String getJfwj() 
    {
        return jfwj;
    }
    public void setJndwtyshxydm(String jndwtyshxydm) 
    {
        this.jndwtyshxydm = jndwtyshxydm;
    }

    public String getJndwtyshxydm() 
    {
        return jndwtyshxydm;
    }
    public void setJfid(String jfid) 
    {
        this.jfid = jfid;
    }

    public String getJfid() 
    {
        return jfid;
    }
    public void setJfsj(String jfsj) 
    {
        this.jfsj = jfsj;
    }

    public String getJfsj() 
    {
        return jfsj;
    }
    public void setTqnmggzjdswh(String tqnmggzjdswh) 
    {
        this.tqnmggzjdswh = tqnmggzjdswh;
    }

    public String getTqnmggzjdswh() 
    {
        return tqnmggzjdswh;
    }
    public void setTqnmggzhmdid(String tqnmggzhmdid) 
    {
        this.tqnmggzhmdid = tqnmggzhmdid;
    }

    public String getTqnmggzhmdid() 
    {
        return tqnmggzhmdid;
    }
    public void setSxzwah(String sxzwah) 
    {
        this.sxzwah = sxzwah;
    }

    public String getSxzwah() 
    {
        return sxzwah;
    }
    public void setZfgzsxxypjid(String zfgzsxxypjid) 
    {
        this.zfgzsxxypjid = zfgzsxxypjid;
    }

    public String getZfgzsxxypjid() 
    {
        return zfgzsxxypjid;
    }
    public void setZfgzsxjdjg(String zfgzsxjdjg) 
    {
        this.zfgzsxjdjg = zfgzsxjdjg;
    }

    public String getZfgzsxjdjg() 
    {
        return zfgzsxjdjg;
    }
    public void setZwsxah(String zwsxah) 
    {
        this.zwsxah = zwsxah;
    }

    public String getZwsxah() 
    {
        return zwsxah;
    }
    public void setZwsxhcqk(String zwsxhcqk) 
    {
        this.zwsxhcqk = zwsxhcqk;
    }

    public String getZwsxhcqk() 
    {
        return zwsxhcqk;
    }
    public void setLhcjrylb(String lhcjrylb) 
    {
        this.lhcjrylb = lhcjrylb;
    }

    public String getLhcjrylb() 
    {
        return lhcjrylb;
    }
    public void setLhcjzch(String lhcjzch) 
    {
        this.lhcjzch = lhcjzch;
    }

    public String getLhcjzch() 
    {
        return lhcjzch;
    }
    public void setHjcfsy(String hjcfsy) 
    {
        this.hjcfsy = hjcfsy;
    }

    public String getHjcfsy() 
    {
        return hjcfsy;
    }
    public void setHjcfyj(String hjcfyj) 
    {
        this.hjcfyj = hjcfyj;
    }

    public String getHjcfyj() 
    {
        return hjcfyj;
    }
    public void setHjcfrq(String hjcfrq) 
    {
        this.hjcfrq = hjcfrq;
    }

    public String getHjcfrq() 
    {
        return hjcfrq;
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
            .append("zjlx", getZjlx())
            .append("zjhm", getZjhm())
            .append("scjydz", getScjydz())
            .append("zcjgzsscjydzdz", getZcjgzsscjydzdz())
            .append("bdcdjzm", getBdcdjzm())
            .append("dywjz", getDywjz())
            .append("gtdm", getGtdm())
            .append("jysj", getJysj())
            .append("nfsfzjlx", getNfsfzjlx())
            .append("nfxm", getNfxm())
            .append("nfsfzjhm", getNfsfzjhm())
            .append("dah", getDah())
            .append("jhdjsj", getJhdjsj())
            .append("jhdjlx", getJhdjlx())
            .append("xlwhcd", getXlwhcd())
            .append("zyjszwxlmc", getZyjszwxlmc())
            .append("zyjszwdj", getZyjszwdj())
            .append("gjzyzgdj", getGjzyzgdj())
            .append("qfjey", getQfjey())
            .append("qfsd", getQfsd())
            .append("dqqsjey", getDqqsjey())
            .append("qstjjzrq", getQstjjzrq())
            .append("qstjksrq", getQstjksrq())
            .append("sxyzcd", getSxyzcd())
            .append("sfbj", getSfbj())
            .append("qssz", getQssz())
            .append("qsye", getQsye())
            .append("tbzjqfxzcfzywfss", getTbzjqfxzcfzywfss())
            .append("tbzjqfxzcfcfqk", getTbzjqfxzcfcfqk())
            .append("zrrqsxxqssz", getZrrqsxxqssz())
            .append("zrrqstjjzrq", getZrrqstjjzrq())
            .append("zrrqstjksrq", getZrrqstjksrq())
            .append("zrrqsye", getZrrqsye())
            .append("zrrsfbj", getZrrsfbj())
            .append("gjlhjcxxgxsj", getGjlhjcxxgxsj())
            .append("gjlhjcxxmdlx", getGjlhjcxxmdlx())
            .append("lhjchmdmc", getLhjchmdmc())
            .append("rybzsy", getRybzsy())
            .append("rybzmc", getRybzmc())
            .append("rybzjb", getRybzjb())
            .append("sxqyzch", getSxqyzch())
            .append("sxqyhfrzzmc", getSxqyhfrzzmc())
            .append("zfgzpjjbzdzh", getZfgzpjjbzdzh())
            .append("zfgzjdswh", getZfgzjdswh())
            .append("nsrxydj", getNsrxydj())
            .append("bqqyqymc", getBqqyqymc())
            .append("jdcsyr", getJdcsyr())
            .append("cllx", getCllx())
            .append("jdczt", getJdczt())
            .append("syq", getSyq())
            .append("syxz", getSyxz())
            .append("bdcdjzh", getBdcdjzh())
            .append("cflx", getCflx())
            .append("cfwj", getCfwj())
            .append("cfqssj", getCfqssj())
            .append("cfjssj", getCfjssj())
            .append("jfjg", getJfjg())
            .append("jfwj", getJfwj())
            .append("jndwtyshxydm", getJndwtyshxydm())
            .append("jfid", getJfid())
            .append("jfsj", getJfsj())
            .append("tqnmggzjdswh", getTqnmggzjdswh())
            .append("tqnmggzhmdid", getTqnmggzhmdid())
            .append("sxzwah", getSxzwah())
            .append("zfgzsxxypjid", getZfgzsxxypjid())
            .append("zfgzsxjdjg", getZfgzsxjdjg())
            .append("zwsxah", getZwsxah())
            .append("zwsxhcqk", getZwsxhcqk())
            .append("lhcjrylb", getLhcjrylb())
            .append("lhcjzch", getLhcjzch())
            .append("hjcfsy", getHjcfsy())
            .append("hjcfyj", getHjcfyj())
            .append("hjcfrq", getHjcfrq())
            .toString();
    }
}
