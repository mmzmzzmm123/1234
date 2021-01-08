package com.ruoyi.gbxxgl.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.entity.SysDept;
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
public class TsbzGbjbqk extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 其他表Id
     */
    @Excel(name = "其他表Id")
    private String otherid;

    /**
     * 教师姓名
     */
    @Excel(name = "教师姓名")
    private String name;

    /**
     * 性别
     */
    @Excel(name = "性别")
    private String xb;

    /**
     * 单位名称
     */
    @Excel(name = "单位名称")
    private String dwmc;

    /**
     * 部门id
     */
    @Excel(name = "部门Id")
    private Long deptId;

    /**
     * 单位简称
     */
    @Excel(name = "单位简称")
    private String dwjc;

    /**
     * 现任行政职务
     */
    @Excel(name = "现任行政职务")
    private String xrxzzw;

    /**
     * 现任党内职务
     */
    @Excel(name = "现任党内职务")
    private String xrdnzw;

    /**
     * 现任群团职务
     */
    @Excel(name = "现任群团职务")
    private String xrqtzw;

    /**
     * 任现职务年月
     */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "任现职务年月", width = 30, dateFormat = "yyyy-MM")
    private Date rxzwny;

    /**
     * 现任校长职级
     */
    @Excel(name = "现任校长职级")
    private String xrxzzj;

    /**
     * 校长职级确定年月
     */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "校长职级确定年月", width = 30, dateFormat = "yyyy-MM")
    private Date xzzjqdny;

    /**
     * 是否后备干部
     */
    @Excel(name = "是否后备干部")
    private String sfhbgb;

    /**
     * 后备干部确定年月
     */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "后备干部确定年月", width = 30, dateFormat = "yyyy-MM")
    private Date hbgbqdny;

    /**
     * 身份证号
     */
    @Excel(name = "身份证号")
    private String sfzh;

    /**
     * 民族
     */
    @Excel(name = "民族")
    private String mz;

    /**
     * 籍贯所在省名称
     */
    @Excel(name = "籍贯所在省名称")
    private String jg;

    /**
     * 籍贯所在省
     */
    @Excel(name = "籍贯所在省")
    private String jgProvince;

    /**
     * 籍贯所在市名称
     */
    @Excel(name = "籍贯所在市名称")
    private String jgCityname;

    /**
     * 籍贯所在市
     */
    @Excel(name = "籍贯所在市")
    private String jgCity;

    /**
     * 籍贯所在区名称
     */
    @Excel(name = "籍贯所在区名称")
    private String jgAreaname;

    /**
     * 籍贯所在区
     */
    @Excel(name = "籍贯所在区")
    private String jgArea;


    /**
     * 出生地所在省名称
     */
    @Excel(name = "出生地所在省名称")
    private String csd;

    /**
     * 出生地所在省
     */
    @Excel(name = "出生地所在省")
    private String csdProvince;

    /**
     * 出生地所在市名称
     */
    @Excel(name = "出生地所在市名称")
    private String csdCityname;

    /**
     * 出生地所在市
     */
    @Excel(name = "出生地所在市")
    private String csdCity;

    /**
     * 出生地所在区名称
     */
    @Excel(name = "出生地所在区名称")
    private String csdAreaname;

    /**
     * 出生地所在区
     */
    @Excel(name = "出生地所在区")
    private String csdArea;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date csrq;


    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;


    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createUser;

    /**
     * 参加工作年月
     */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "参加工作年月", width = 30, dateFormat = "yyyy-MM")
    private Date cjgzny;

    /**
     * 家庭住址
     */
    @Excel(name = "家庭住址")
    private String jtzz;

    /**
     * 单位地址
     */
    @Excel(name = "单位地址")
    private String dwdz;

    /**
     * 第一学历
     */
    @Excel(name = "第一学历")
    private String dyxl;

    /**
     * 学历
     */
    @Excel(name = "学历")
    private String xl;

    /**
     * 学位
     */
    @Excel(name = "学位")
    private String xw;

    /**
     * 毕业院校
     */
    @Excel(name = "毕业院校")
    private String byyx;

    /**
     * 教育类型
     */
    @Excel(name = "教育类型")
    private String jylx;

    /**
     * 职称
     */
    @Excel(name = "职称")
    private String zc;

    /**
     * 驾照持有情况
     */
    @Excel(name = "驾照持有情况")
    private String jzcyqk;

    /**
     * 能否开车
     */
    @Excel(name = "能否开车")
    private String nfkc;

    /**
     * 婚姻状况
     */
    @Excel(name = "婚姻状况")
    private String hyzk;

    /**
     * 政治面貌
     */
    @Excel(name = "政治面貌")
    private String zzmm;

    /**
     * 入党时间
     */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "入党时间", width = 30, dateFormat = "yyyy-MM")
    private Date rdsj;

    /**
     * 办公室电话
     */
    @Excel(name = "办公室电话")
    private String bgsdh;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    private String phone;

    /**
     * 任教学科
     */
    @Excel(name = "任教学科")
    private String rjxk;

    /**
     * 健康状况
     */
    @Excel(name = "健康状况")
    private String jkzk;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatetime;

    /**
     * 当前状态正常00;退休99;
     */
    @Excel(name = "当前状态正常00;退休99;")
    private String dqzt;

    /**
     * 退休时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "退休时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date txTime;

    private SysDept dept;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setOtherid(String otherid) {
        this.otherid = otherid;
    }

    public String getOtherid() {
        return otherid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getXb() {
        return xb;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwjc(String dwjc) {
        this.dwjc = dwjc;
    }

    public String getDwjc() {
        return dwjc;
    }

    public void setXrxzzw(String xrxzzw) {
        this.xrxzzw = xrxzzw;
    }

    public String getXrxzzw() {
        return xrxzzw;
    }

    public void setXrdnzw(String xrdnzw) {
        this.xrdnzw = xrdnzw;
    }

    public String getXrdnzw() {
        return xrdnzw;
    }

    public void setXrqtzw(String xrqtzw) {
        this.xrqtzw = xrqtzw;
    }

    public String getXrqtzw() {
        return xrqtzw;
    }

    public void setRxzwny(Date rxzwny) {
        this.rxzwny = rxzwny;
    }

    public Date getRxzwny() {
        return rxzwny;
    }

    public void setXrxzzj(String xrxzzj) {
        this.xrxzzj = xrxzzj;
    }

    public String getXrxzzj() {
        return xrxzzj;
    }

    public void setXzzjqdny(Date xzzjqdny) {
        this.xzzjqdny = xzzjqdny;
    }

    public Date getXzzjqdny() {
        return xzzjqdny;
    }

    public void setSfhbgb(String sfhbgb) {
        this.sfhbgb = sfhbgb;
    }

    public String getSfhbgb() {
        return sfhbgb;
    }

    public void setHbgbqdny(Date hbgbqdny) {
        this.hbgbqdny = hbgbqdny;
    }

    public Date getHbgbqdny() {
        return hbgbqdny;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    public String getMz() {
        return mz;
    }

    public void setJg(String jg) {
        this.jg = jg;
    }

    public String getJg() {
        return jg;
    }

    public void setCsd(String csd) {
        this.csd = csd;
    }

    public String getCsd() {
        return csd;
    }

    public void setCsrq(Date csrq) {
        this.csrq = csrq;
    }

    public Date getCsrq() {
        return csrq;
    }

    public void setCjgzny(Date cjgzny) {
        this.cjgzny = cjgzny;
    }

    public Date getCjgzny() {
        return cjgzny;
    }

    public void setJtzz(String jtzz) {
        this.jtzz = jtzz;
    }

    public String getJtzz() {
        return jtzz;
    }

    public void setDwdz(String dwdz) {
        this.dwdz = dwdz;
    }

    public String getDwdz() {
        return dwdz;
    }

    public void setXl(String xl) {
        this.xl = xl;
    }

    public String getXl() {
        return xl;
    }

    public void setXw(String xw) {
        this.xw = xw;
    }

    public String getXw() {
        return xw;
    }

    public void setByyx(String byyx) {
        this.byyx = byyx;
    }

    public String getByyx() {
        return byyx;
    }

    public void setJylx(String jylx) {
        this.jylx = jylx;
    }

    public String getJylx() {
        return jylx;
    }

    public void setZc(String zc) {
        this.zc = zc;
    }

    public String getZc() {
        return zc;
    }

    public void setJzcyqk(String jzcyqk) {
        this.jzcyqk = jzcyqk;
    }

    public String getJzcyqk() {
        return jzcyqk;
    }

    public void setNfkc(String nfkc) {
        this.nfkc = nfkc;
    }

    public String getNfkc() {
        return nfkc;
    }

    public void setHyzk(String hyzk) {
        this.hyzk = hyzk;
    }

    public String getHyzk() {
        return hyzk;
    }

    public void setZzmm(String zzmm) {
        this.zzmm = zzmm;
    }

    public String getZzmm() {
        return zzmm;
    }

    public void setRdsj(Date rdsj) {
        this.rdsj = rdsj;
    }

    public Date getRdsj() {
        return rdsj;
    }

    public void setBgsdh(String bgsdh) {
        this.bgsdh = bgsdh;
    }

    public String getBgsdh() {
        return bgsdh;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setRjxk(String rjxk) {
        this.rjxk = rjxk;
    }

    public String getRjxk() {
        return rjxk;
    }

    public void setJkzk(String jkzk) {
        this.jkzk = jkzk;
    }

    public String getJkzk() {
        return jkzk;
    }

    public String getJgProvince() {
        return jgProvince;
    }

    public void setJgProvince(String jgProvince) {
        this.jgProvince = jgProvince;
    }

    public String getJgCityname() {
        return jgCityname;
    }

    public void setJgCityname(String jgCityname) {
        this.jgCityname = jgCityname;
    }

    public String getJgCity() {
        return jgCity;
    }

    public void setJgCity(String jgCity) {
        this.jgCity = jgCity;
    }

    public String getJgAreaname() {
        return jgAreaname;
    }

    public void setJgAreaname(String jgAreaname) {
        this.jgAreaname = jgAreaname;
    }

    public String getJgArea() {
        return jgArea;
    }

    public void setJgArea(String jgArea) {
        this.jgArea = jgArea;
    }

    public String getCsdProvince() {
        return csdProvince;
    }

    public void setCsdProvince(String csdProvince) {
        this.csdProvince = csdProvince;
    }

    public String getCsdCityname() {
        return csdCityname;
    }

    public void setCsdCityname(String csdCityname) {
        this.csdCityname = csdCityname;
    }

    public String getCsdCity() {
        return csdCity;
    }

    public void setCsdCity(String csdCity) {
        this.csdCity = csdCity;
    }

    public String getCsdAreaname() {
        return csdAreaname;
    }

    public void setCsdAreaname(String csdAreaname) {
        this.csdAreaname = csdAreaname;
    }

    public String getCsdArea() {
        return csdArea;
    }

    public void setCsdArea(String csdArea) {
        this.csdArea = csdArea;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setDqzt(String dqzt) {
        this.dqzt = dqzt;
    }

    public String getDqzt() {
        return dqzt;
    }

    public void setTxTime(Date txTime) {
        this.txTime = txTime;
    }

    public Date getTxTime() {
        return txTime;
    }

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
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
                .append("jgProvince", getJgProvince())
                .append("jgCityname", getJgCityname())
                .append("jgCity", getJgCity())
                .append("jgAreaname", getJgAreaname())
                .append("jgArea", getJgArea())
                .append("csdProvince", getCsdProvince())
                .append("csdCityname", getCsdCityname())
                .append("csdCity", getCsdCity())
                .append("csdAreaname", getCsdAreaname())
                .append("csdArea", getCsdArea())
                .append("deptId", getDeptId())
                .append("createTime", getCreateTime())
                .append("createUser", getCreateUser())
                .append("dyxl", getDyxl())
                .append("dqzt", getDqzt())
                .append("txTime", getTxTime())
                .append("dept", getDept())
                .toString();
    }


    public String getDyxl() {
        return dyxl;
    }

    public void setDyxl(String dyxl) {
        this.dyxl = dyxl;
    }
}

