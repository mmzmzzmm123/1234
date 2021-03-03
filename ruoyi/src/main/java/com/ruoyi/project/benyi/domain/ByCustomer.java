package com.ruoyi.project.benyi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 本一-客户关系管理对象 by_customer
 *
 * @author tsbz
 * @date 2021-03-01
 */
public class ByCustomer extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String name;

    /**
     * 身份
     */
    @Excel(name = "身份")
    private String sflx;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    private String lxdh;

    /**
     * 微信
     */
    @Excel(name = "微信")
    private String wx;

    /**
     * 抖音
     */
    @Excel(name = "抖音")
    private String dy;

    /**
     * 其他
     */
    @Excel(name = "其他")
    private String qt;

    /**
     * 幼儿园名称
     */
    @Excel(name = "幼儿园名称")
    private String schoolname;

    /**
     * 幼儿园人数
     */
    @Excel(name = "幼儿园人数")
    private Long rs;

    /**
     * 所在省
     */
    @Excel(name = "所在省")
    private String sheng;

    /**
     * 所在省编号
     */
    @Excel(name = "所在省编号")
    private String shengid;

    /**
     * 所在市
     */
    @Excel(name = "所在市")
    private String shi;

    /**
     * 所在市编号
     */
    @Excel(name = "所在市编号")
    private String shiid;

    /**
     * 客户来源
     */
    @Excel(name = "客户来源")
    private String khly;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createUserid;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String bz;

    /**
     * 转换跟进
     */
    @Excel(name = "转换跟进")
    private String zhgj;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String state;

    /**
     * 消费项目
     */
    @Excel(name = "消费项目")
    private String xfxm;

    /**
     * 创建时间
     */
    @Excel(name = "开通截至日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 消费价值
     */
    @Excel(name = "消费价值")
    private Long xfjz;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSflx(String sflx) {
        this.sflx = sflx;
    }

    public String getSflx() {
        return sflx;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    public String getWx() {
        return wx;
    }

    public void setDy(String dy) {
        this.dy = dy;
    }

    public String getDy() {
        return dy;
    }

    public void setQt(String qt) {
        this.qt = qt;
    }

    public String getQt() {
        return qt;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setRs(Long rs) {
        this.rs = rs;
    }

    public Long getRs() {
        return rs;
    }

    public void setSheng(String sheng) {
        this.sheng = sheng;
    }

    public String getSheng() {
        return sheng;
    }

    public void setShengid(String shengid) {
        this.shengid = shengid;
    }

    public String getShengid() {
        return shengid;
    }

    public void setShi(String shi) {
        this.shi = shi;
    }

    public String getShi() {
        return shi;
    }

    public void setShiid(String shiid) {
        this.shiid = shiid;
    }

    public String getShiid() {
        return shiid;
    }

    public void setKhly(String khly) {
        this.khly = khly;
    }

    public String getKhly() {
        return khly;
    }

    public void setCreateUserid(Long createUserid) {
        this.createUserid = createUserid;
    }

    public Long getCreateUserid() {
        return createUserid;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getBz() {
        return bz;
    }

    public void setZhgj(String zhgj) {
        this.zhgj = zhgj;
    }

    public String getZhgj() {
        return zhgj;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setXfxm(String xfxm) {
        this.xfxm = xfxm;
    }

    public String getXfxm() {
        return xfxm;
    }

    public void setXfjz(Long xfjz) {
        this.xfjz = xfjz;
    }

    public Long getXfjz() {
        return xfjz;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("sflx", getSflx())
                .append("lxdh", getLxdh())
                .append("wx", getWx())
                .append("dy", getDy())
                .append("qt", getQt())
                .append("schoolname", getSchoolname())
                .append("rs", getRs())
                .append("sheng", getSheng())
                .append("shengid", getShengid())
                .append("shi", getShi())
                .append("shiid", getShiid())
                .append("khly", getKhly())
                .append("createUserid", getCreateUserid())
                .append("bz", getBz())
                .append("zhgj", getZhgj())
                .append("state", getState())
                .append("xfxm", getXfxm())
                .append("xfjz", getXfjz())
                .append("createTime", getCreateTime())
                .toString();
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
