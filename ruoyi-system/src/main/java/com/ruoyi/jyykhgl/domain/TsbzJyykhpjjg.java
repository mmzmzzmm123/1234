package com.ruoyi.jyykhgl.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 考核评价结果对象 tsbz_jyykhpjjg
 *
 * @author tsbz
 * @date 2021-04-16
 */
public class TsbzJyykhpjjg extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 任务编号
     */
    @Excel(name = "任务编号")
    private Long rwid;

    /**
     * 考核人员编号
     */
    @Excel(name = "考核人员编号")
    private Long khryid;

    /**
     * 评价状态
     */
    @Excel(name = "评价状态")
    private String pjzt;

    /**
     * 评价结果
     */
    @Excel(name = "评价结果")
    private String pjjg;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setRwid(Long rwid) {
        this.rwid = rwid;
    }

    public Long getRwid() {
        return rwid;
    }

    public void setKhryid(Long khryid) {
        this.khryid = khryid;
    }

    public Long getKhryid() {
        return khryid;
    }

    public void setPjzt(String pjzt) {
        this.pjzt = pjzt;
    }

    public String getPjzt() {
        return pjzt;
    }

    public void setPjjg(String pjjg) {
        this.pjjg = pjjg;
    }

    public String getPjjg() {
        return pjjg;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("rwid", getRwid())
                .append("khryid", getKhryid())
                .append("pjzt", getPjzt())
                .append("remark", getRemark())
                .append("pjjg", getPjjg())
                .append("createTime", getCreateTime())
                .toString();
    }
}
