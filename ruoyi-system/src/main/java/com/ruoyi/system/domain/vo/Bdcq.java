package com.ruoyi.system.domain.vo;

import com.ruoyi.common.annotation.Excel;

import java.util.Objects;

/**
 * 不动产权
 */
public class Bdcq {
    /** 不动产权证号 */
    @Excel(name = "不动产权证号")
    private String bdcqzh;

    /** 不动产共有方式 */
    @Excel(name = "不动产共有方式")
    private String bdcgyfs;

    /** 不动产坐落 */
    @Excel(name = "不动产坐落")
    private String bdczl;

    /** 不动产产权状态 */
    @Excel(name = "不动产产权状态")
    private String bdccqzt;

    /** 不动产建筑面积 */
    @Excel(name = "不动产建筑面积")
    private String bdcjzmj;

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getBdcgyfs() {
        return bdcgyfs;
    }

    public void setBdcgyfs(String bdcgyfs) {
        this.bdcgyfs = bdcgyfs;
    }

    public String getBdczl() {
        return bdczl;
    }

    public void setBdczl(String bdczl) {
        this.bdczl = bdczl;
    }

    public String getBdccqzt() {
        return bdccqzt;
    }

    public void setBdccqzt(String bdccqzt) {
        this.bdccqzt = bdccqzt;
    }

    public String getBdcjzmj() {
        return bdcjzmj;
    }

    public void setBdcjzmj(String bdcjzmj) {
        this.bdcjzmj = bdcjzmj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bdcq bdcq = (Bdcq) o;
        return getBdcqzh().equals(bdcq.getBdcqzh());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBdcqzh());
    }
}
