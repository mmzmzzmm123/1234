package com.ruoyi.qtjskhgl.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 群体教师考核过程数据对象 tsbz_qtjskhgcsj
 *
 * @author ruoyi
 * @date 2020-09-17
 */
public class TsbzQtjskhgcsj extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String id;

    /**
     * 所属方案
     */
    @Excel(name = "所属方案")
    private Long faid;

    /**
     * 指标项
     */
    @Excel(name = "指标项")
    private Long zbid;

    /**
     * 内容
     */
    @Excel(name = "内容")
    private String content;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createuserid;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setFaid(Long faid) {
        this.faid = faid;
    }

    public Long getFaid() {
        return faid;
    }

    public void setZbid(Long zbid) {
        this.zbid = zbid;
    }

    public Long getZbid() {
        return zbid;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
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
                .append("faid", getFaid())
                .append("zbid", getZbid())
                .append("content", getContent())
                .append("createuserid", getCreateuserid())
                .append("createTime", getCreateTime())
                .toString();
    }
}
