package com.ruoyi.jxjs.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 见习之星名单对象 tsbz_jxzxmd
 *
 * @author ruoyi
 * @date 2020-08-23
 */
public class TsbzJxzxmd extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 教师id
     */
    @Excel(name = "教师id")
    private Long jsid;

    /**
     * 年份
     */
    @Excel(name = "年份")
    private String nf;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createuserid;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setJsid(Long jsid) {
        this.jsid = jsid;
    }

    public Long getJsid() {
        return jsid;
    }

    public void setNf(String nf) {
        this.nf = nf;
    }

    public String getNf() {
        return nf;
    }

    public void setCreateuserid(Long createuserid) {
        this.createuserid = createuserid;
    }

    public Long getCreateuserid() {
        return createuserid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("jsid", getJsid())
                .append("nf", getNf())
                .append("createuserid", getCreateuserid())
                .append("createTime", getCreateTime())
                .toString();
    }
}
