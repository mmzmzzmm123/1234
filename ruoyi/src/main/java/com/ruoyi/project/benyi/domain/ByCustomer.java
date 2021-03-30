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
    @Excel(name = "客户序号", cellType = Excel.ColumnType.NUMERIC, prompt = "客户编号")
    private Long id;

    /**
     * 姓名
     */
    @Excel(name = "姓名", type = Excel.Type.EXPORT)
    private String name;

    /**
     * 身份
     */
    @Excel(name = "身份", type = Excel.Type.EXPORT, readConverterExp = "01=园长,02=投资人,03=园长+投资人,04=家长,05=其他")
    private String sflx;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话", type = Excel.Type.EXPORT)
    private String lxdh;

    /**
     * 微信
     */
    @Excel(name = "微信", type = Excel.Type.EXPORT)
    private String wx;

    /**
     * 抖音
     */
    @Excel(name = "抖音", type = Excel.Type.EXPORT)
    private String dy;

    /**
     * 其他
     */
    @Excel(name = "其他", type = Excel.Type.EXPORT)
    private String qt;

    /**
     * 幼儿园名称
     */
    @Excel(name = "幼儿园名称", type = Excel.Type.EXPORT)
    private String schoolname;

    /**
     * 幼儿园人数
     */
    @Excel(name = "幼儿园人数", type = Excel.Type.EXPORT)
    private Long rs;

    /**
     * 所在省
     */
    @Excel(name = "所在省", type = Excel.Type.EXPORT)
    private String sheng;

    /**
     * 所在省编号
     */
    //@Excel(name = "所在省编号")
    private String shengid;

    /**
     * 所在市
     */
    //@Excel(name = "所在市")
    private String shi;

    /**
     * 所在市编号
     */
    //@Excel(name = "所在市编号")
    private String shiid;

    /**
     * 所在区县名称
     */
    //@Excel(name = "所在区县")
    private String quxian;

    /**
     * 所在区县编号
     */
    //@Excel(name = "所在区县编号")
    private String quxianid;

    /**
     * 客户来源
     */
    @Excel(name = "客户来源", type = Excel.Type.EXPORT, readConverterExp = "01=线下教研,02=抖音,03=快手,04=微信群,05=网络,06=来电来访,07=其它")
    private String khly;

    /**
     * 创建人
     */
    //@Excel(name = "创建人", type = Excel.Type.EXPORT, targetAttr = "user_name")
    private Long createUserid;

    // 被分配者
    private Long fpid;

    /**
     * 备注
     */
    @Excel(name = "备注", type = Excel.Type.EXPORT)
    private String bz;

    /**
     * 转换跟进
     */
    @Excel(name = "转换跟进", type = Excel.Type.EXPORT)
    private String zhgj;

    /**
     * 状态
     */
    //@Excel(name = "状态", type = Excel.Type.EXPORT)
    private String state;

    /**
     * 消费项目
     */
    @Excel(name = "消费项目", type = Excel.Type.EXPORT)
    private String xfxm;

    /**
     * 创建时间gbtime
     */
    @Excel(name = "开通截至日期", width = 30, dateFormat = "yyyy-MM-dd", type = Excel.Type.EXPORT)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 过保时间
     */
    @Excel(name = "开通截至日期", width = 30, dateFormat = "yyyy-MM-dd", type = Excel.Type.EXPORT)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date gbtime;

    /**
     * 消费价值
     */
    @Excel(name = "消费价值", type = Excel.Type.EXPORT)
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
                .append("quxian", getQuxian())
                .append("quxianid", getQuxianid())
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
                .append("gbtime", getGbtime())
                .append("fpid", getFpid())
                .toString();
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getGbtime() {
        return gbtime;
    }

    public void setGbtime(Date gbtime) {
        this.gbtime = gbtime;
    }

    public Long getFpid() {
        return fpid;
    }

    public void setFpid(Long fpid) {
        this.fpid = fpid;
    }

    public String getQuxian() {
        return quxian;
    }

    public void setQuxian(String quxian) {
        this.quxian = quxian;
    }

    public String getQuxianid() {
        return quxianid;
    }

    public void setQuxianid(String quxianid) {
        this.quxianid = quxianid;
    }
}
