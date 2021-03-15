package com.ruoyi.jyykhgl.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 教研员考核任务对象 tsbz_jyykhrw
 *
 * @author tsbz
 * @date 2021-01-15
 */
public class TsbzJyykhrw extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String rwmc;

    /**
     * 任务类型
     */
    @Excel(name = "任务类型")
    private String rwlx;

    /**
     * 任务内容
     */
    @Excel(name = "任务内容")
    private String rwnr;

    /**
     * 考核部门
     */
    @Excel(name = "考核部门")
    private String khbm;

    /**
     * 考核周期
     */
    @Excel(name = "考核周期")
    private String khzq;

    /**
     * 数量要求
     */
    @Excel(name = "数量要求")
    private String slyq;

    /**
     * 是否必选
     */
    @Excel(name = "是否必选")
    private String fsbx;

    /**
     * 是否删除
     */
    @Excel(name = "是否删除")
    private String isdel;

    /**
     * 任务说明
     */
    @Excel(name = "任务说明")
    private String rwsm;

    /**
     * 所属部门
     */
    @Excel(name = "所属部门")
    private Long deptId;

    private Long userId;

    private String nickName;

    private String wcsl;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setRwmc(String rwmc) {
        this.rwmc = rwmc;
    }

    public String getRwmc() {
        return rwmc;
    }

    public void setRwlx(String rwlx) {
        this.rwlx = rwlx;
    }

    public String getRwlx() {
        return rwlx;
    }

    public void setRwnr(String rwnr) {
        this.rwnr = rwnr;
    }

    public String getRwnr() {
        return rwnr;
    }

    public void setKhbm(String khbm) {
        this.khbm = khbm;
    }

    public String getKhbm() {
        return khbm;
    }

    public void setKhzq(String khzq) {
        this.khzq = khzq;
    }

    public String getKhzq() {
        return khzq;
    }

    public void setSlyq(String slyq) {
        this.slyq = slyq;
    }

    public String getSlyq() {
        return slyq;
    }

    public void setFsbx(String fsbx) {
        this.fsbx = fsbx;
    }

    public String getFsbx() {
        return fsbx;
    }

    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }

    public String getIsdel() {
        return isdel;
    }

    public void setRwsm(String rwsm) {
        this.rwsm = rwsm;
    }

    public String getRwsm() {
        return rwsm;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getWcsl() {
        return wcsl;
    }

    public void setWcsl(String wcsl) {
        this.wcsl = wcsl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("rwmc", getRwmc())
                .append("rwlx", getRwlx())
                .append("rwnr", getRwnr())
                .append("khbm", getKhbm())
                .append("khzq", getKhzq())
                .append("slyq", getSlyq())
                .append("fsbx", getFsbx())
                .append("isdel", getIsdel())
                .append("createTime", getCreateTime())
                .append("rwsm", getRwsm())
                .append("deptId", getDeptId())
                .append("userId", getUserId())
                .append("nickName", getNickName())
                .append("wcsl", getWcsl())
                .toString();
    }
}
