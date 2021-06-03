package com.ruoyi.qtjskhgl.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 群体教师考核文件管理对象 tsbz_qtjskhwj
 *
 * @author tsbz
 * @date 2021-06-03
 */
public class TsbzQtjskhwj extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 标识
     */
    private Long id;

    /**
     * 方案编号
     */
    @Excel(name = "方案编号")
    private Long faid;

    /**
     * 考核年份
     */
    @Excel(name = "考核年份")
    private String nf;

    /**
     * 考核类型
     */
    @Excel(name = "考核类型")
    private String khlx;

    /**
     * 附件类型
     */
    @Excel(name = "附件类型")
    private String fjlx;

    /**
     * 文件名称
     */
    @Excel(name = "文件名称")
    private String filename;

    /**
     * 文件路径
     */
    @Excel(name = "文件路径")
    private String filepath;

    /**
     * 部门
     */
    @Excel(name = "部门")
    private Long deptId;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createUserid;

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

    public void setNf(String nf) {
        this.nf = nf;
    }

    public String getNf() {
        return nf;
    }

    public void setKhlx(String khlx) {
        this.khlx = khlx;
    }

    public String getKhlx() {
        return khlx;
    }

    public void setFjlx(String fjlx) {
        this.fjlx = fjlx;
    }

    public String getFjlx() {
        return fjlx;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setCreateUserid(Long createUserid) {
        this.createUserid = createUserid;
    }

    public Long getCreateUserid() {
        return createUserid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("faid", getFaid())
                .append("nf", getNf())
                .append("khlx", getKhlx())
                .append("fjlx", getFjlx())
                .append("filename", getFilename())
                .append("filepath", getFilepath())
                .append("deptId", getDeptId())
                .append("createUserid", getCreateUserid())
                .append("createTime", getCreateTime())
                .toString();
    }
}
