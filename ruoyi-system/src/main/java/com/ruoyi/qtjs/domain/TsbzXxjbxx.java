package com.ruoyi.qtjs.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学校信息对象 tsbz_xxjbxx
 *
 * @author ruoyi
 * @date 2020-09-14
 */
public class TsbzXxjbxx extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 其他系统编号
     */
    @Excel(name = "其他系统编号")
    private String xxdm;

    /**
     * 区县ID
     */
    @Excel(name = "区县ID")
    private String countyid;

    /**
     * 乡镇(街道)ID
     */
    @Excel(name = "乡镇(街道)ID")
    private String townid;

    /**
     * 村委(居委)ID
     */
    @Excel(name = "村委(居委)ID")
    private String villageid;

    /**
     * 教育局学校代码
     */
    @Excel(name = "教育局学校代码")
    private String jyjxxdm;

    /**
     * 学校名称
     */
    @Excel(name = "学校名称")
    private String xxmc;

    /**
     * 学校英文名称
     */
    @Excel(name = "学校英文名称")
    private String xxywmc;

    /**
     * 学校地址
     */
    @Excel(name = "学校地址")
    private String xxdz;

    /**
     * 学校校长
     */
    @Excel(name = "学校校长")
    private String xxxz;

    /**
     * 党组织负责人
     */
    @Excel(name = "党组织负责人")
    private String dzzfzr;

    /**
     * 建校年月
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "建校年月", width = 30, dateFormat = "yyyy-MM-dd")
    private Date jxny;

    /**
     * 学校办别码
     */
    @Excel(name = "学校办别码")
    private String xxbbm;

    /**
     * 学校类别码
     */
    @Excel(name = "学校类别码")
    private String xxlbm;

    /**
     * 所在地区类别码
     */
    @Excel(name = "所在地区类别码")
    private String szdqlbm;

    /**
     * 所在地经济属性码
     */
    @Excel(name = "所在地经济属性码")
    private String szdjjsxm;

    /**
     * 所在地民族属性码
     */
    @Excel(name = "所在地民族属性码")
    private String szdmzsxm;

    /**
     * 入学年龄
     */
    @Excel(name = "入学年龄")
    private Long rxnl;

    /**
     * 主教学语言码
     */
    @Excel(name = "主教学语言码")
    private String zjxyym;

    /**
     * 辅教学语言码
     */
    @Excel(name = "辅教学语言码")
    private String fjxyym;

    /**
     * 招生区域
     */
    @Excel(name = "招生区域")
    private String zsqy;

    /**
     * 邮政编码
     */
    @Excel(name = "邮政编码")
    private String yzbm;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    private String lxdh;

    /**
     * 传真电话
     */
    @Excel(name = "传真电话")
    private String czdh;

    /**
     * 电子信箱
     */
    @Excel(name = "电子信箱")
    private String dzxx;

    /**
     * 主页地址
     */
    @Excel(name = "主页地址")
    private String zydz;

    /**
     * 组织机构代码
     */
    @Excel(name = "组织机构代码")
    private String zzjgdm;

    /**
     * 历史沿革
     */
    @Excel(name = "历史沿革")
    private String lsyg;

    /**
     * 当前学年
     */
    @Excel(name = "当前学年")
    private String dqxn;

    /**
     * 当前学期
     */
    @Excel(name = "当前学期")
    private String dqxq;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setXxdm(String xxdm) {
        this.xxdm = xxdm;
    }

    public String getXxdm() {
        return xxdm;
    }

    public void setCountyid(String countyid) {
        this.countyid = countyid;
    }

    public String getCountyid() {
        return countyid;
    }

    public void setTownid(String townid) {
        this.townid = townid;
    }

    public String getTownid() {
        return townid;
    }

    public void setVillageid(String villageid) {
        this.villageid = villageid;
    }

    public String getVillageid() {
        return villageid;
    }

    public void setJyjxxdm(String jyjxxdm) {
        this.jyjxxdm = jyjxxdm;
    }

    public String getJyjxxdm() {
        return jyjxxdm;
    }

    public void setXxmc(String xxmc) {
        this.xxmc = xxmc;
    }

    public String getXxmc() {
        return xxmc;
    }

    public void setXxywmc(String xxywmc) {
        this.xxywmc = xxywmc;
    }

    public String getXxywmc() {
        return xxywmc;
    }

    public void setXxdz(String xxdz) {
        this.xxdz = xxdz;
    }

    public String getXxdz() {
        return xxdz;
    }

    public void setXxxz(String xxxz) {
        this.xxxz = xxxz;
    }

    public String getXxxz() {
        return xxxz;
    }

    public void setDzzfzr(String dzzfzr) {
        this.dzzfzr = dzzfzr;
    }

    public String getDzzfzr() {
        return dzzfzr;
    }

    public void setJxny(Date jxny) {
        this.jxny = jxny;
    }

    public Date getJxny() {
        return jxny;
    }

    public void setXxbbm(String xxbbm) {
        this.xxbbm = xxbbm;
    }

    public String getXxbbm() {
        return xxbbm;
    }

    public void setXxlbm(String xxlbm) {
        this.xxlbm = xxlbm;
    }

    public String getXxlbm() {
        return xxlbm;
    }

    public void setSzdqlbm(String szdqlbm) {
        this.szdqlbm = szdqlbm;
    }

    public String getSzdqlbm() {
        return szdqlbm;
    }

    public void setSzdjjsxm(String szdjjsxm) {
        this.szdjjsxm = szdjjsxm;
    }

    public String getSzdjjsxm() {
        return szdjjsxm;
    }

    public void setSzdmzsxm(String szdmzsxm) {
        this.szdmzsxm = szdmzsxm;
    }

    public String getSzdmzsxm() {
        return szdmzsxm;
    }

    public void setRxnl(Long rxnl) {
        this.rxnl = rxnl;
    }

    public Long getRxnl() {
        return rxnl;
    }

    public void setZjxyym(String zjxyym) {
        this.zjxyym = zjxyym;
    }

    public String getZjxyym() {
        return zjxyym;
    }

    public void setFjxyym(String fjxyym) {
        this.fjxyym = fjxyym;
    }

    public String getFjxyym() {
        return fjxyym;
    }

    public void setZsqy(String zsqy) {
        this.zsqy = zsqy;
    }

    public String getZsqy() {
        return zsqy;
    }

    public void setYzbm(String yzbm) {
        this.yzbm = yzbm;
    }

    public String getYzbm() {
        return yzbm;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setCzdh(String czdh) {
        this.czdh = czdh;
    }

    public String getCzdh() {
        return czdh;
    }

    public void setDzxx(String dzxx) {
        this.dzxx = dzxx;
    }

    public String getDzxx() {
        return dzxx;
    }

    public void setZydz(String zydz) {
        this.zydz = zydz;
    }

    public String getZydz() {
        return zydz;
    }

    public void setZzjgdm(String zzjgdm) {
        this.zzjgdm = zzjgdm;
    }

    public String getZzjgdm() {
        return zzjgdm;
    }

    public void setLsyg(String lsyg) {
        this.lsyg = lsyg;
    }

    public String getLsyg() {
        return lsyg;
    }

    public void setDqxn(String dqxn) {
        this.dqxn = dqxn;
    }

    public String getDqxn() {
        return dqxn;
    }

    public void setDqxq(String dqxq) {
        this.dqxq = dqxq;
    }

    public String getDqxq() {
        return dqxq;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("xxdm", getXxdm())
                .append("countyid", getCountyid())
                .append("townid", getTownid())
                .append("villageid", getVillageid())
                .append("jyjxxdm", getJyjxxdm())
                .append("xxmc", getXxmc())
                .append("xxywmc", getXxywmc())
                .append("xxdz", getXxdz())
                .append("xxxz", getXxxz())
                .append("dzzfzr", getDzzfzr())
                .append("jxny", getJxny())
                .append("xxbbm", getXxbbm())
                .append("xxlbm", getXxlbm())
                .append("szdqlbm", getSzdqlbm())
                .append("szdjjsxm", getSzdjjsxm())
                .append("szdmzsxm", getSzdmzsxm())
                .append("rxnl", getRxnl())
                .append("zjxyym", getZjxyym())
                .append("fjxyym", getFjxyym())
                .append("zsqy", getZsqy())
                .append("yzbm", getYzbm())
                .append("lxdh", getLxdh())
                .append("czdh", getCzdh())
                .append("dzxx", getDzxx())
                .append("zydz", getZydz())
                .append("zzjgdm", getZzjgdm())
                .append("lsyg", getLsyg())
                .append("dqxn", getDqxn())
                .append("dqxq", getDqxq())
                .append("createTime", getCreateTime())
                .toString();
    }
}
