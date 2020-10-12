package com.ruoyi.qtjs.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 群体教师评选指标数据对象 tsbz_qtjspxfazbxsj
 *
 * @author ruoyi
 * @date 2020-09-17
 */
public class TsbzQtjspxfazbxsj extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 方案编号
     */
    @Excel(name = "方案编号")
    private Long faid;

    /**
     * 指标编号
     */
    @Excel(name = "指标编号")
    private Long zbid;

    /**
     * 教师编号
     */
    @Excel(name = "教师编号")
    private Long jsid;

    /**
     * 佐证内容
     */
    @Excel(name = "佐证内容")
    private String content;

    /**
     * 附件
     */
    @Excel(name = "附件")
    private String filepath;

    /**
     * 附件名称
     */
    @Excel(name = "附件名称")
    private String filename;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createuserid;

    public TsbzQtjspxfazbx getTsbzQtjspxfazbx() {
        return tsbzQtjspxfazbx;
    }

    public void setTsbzQtjspxfazbx(TsbzQtjspxfazbx tsbzQtjspxfazbx) {
        this.tsbzQtjspxfazbx = tsbzQtjspxfazbx;
    }

    private TsbzQtjspxfazbx tsbzQtjspxfazbx;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
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

    public void setJsid(Long jsid) {
        this.jsid = jsid;
    }

    public Long getJsid() {
        return jsid;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
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
                .append("jsid", getJsid())
                .append("content", getContent())
                .append("filepath", getFilepath())
                .append("filename", getFilename())
                .append("createuserid", getCreateuserid())
                .append("createTime", getCreateTime())
                .append("tsbzQtjspxfazbx", getTsbzQtjspxfazbx())
                .toString();
    }
}
