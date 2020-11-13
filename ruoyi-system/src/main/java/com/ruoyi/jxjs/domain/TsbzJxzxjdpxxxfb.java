package com.ruoyi.jxjs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 基地培训信息发布对象 tsbz_jxzxjdpxxxfb
 *
 * @author ruoyi
 * @date 2020-11-09
 */
public class TsbzJxzxjdpxxxfb extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 基地校ID
     */
    @Excel(name = "基地校ID")
    private String jdxid;

    /**
     * 培训信息名称
     */
    @Excel(name = "培训信息名称")
    private String name;

    /**
     * 培训计划
     */
    @Excel(name = "培训计划")
    private String pxjh;

    /**
     * 培训方案
     */
    @Excel(name = "培训方案")
    private String pxfa;

    /**
     * 培训总结
     */
    @Excel(name = "培训总结")
    private String pxzj;

    /**
     * 备用字段
     */
    @Excel(name = "备用字段")
    private String pxBeiyong;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createUserid;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "访问时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 文件路径
     */
    @Excel(name = "文件路径")
    private String filePath;

    /**
     * 文件名称
     */
    @Excel(name = "文件名称")
    private String fileName;

    /** 培训方案文件路径 */
    @Excel(name = "培训方案文件路径")
    private String pxfaFilepath;

    /** 培训方案文件名称 */
    @Excel(name = "培训方案文件名称")
    private String pxfaFilename;



    /** 培训总结文件路径 */
    @Excel(name = "培训总结文件路径")
    private String pxzjFilepath;

    /** 培训总结文件名称 */
    @Excel(name = "培训总结文件名称")
    private String pxzjFilename;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setJdxid(String jdxid) {
        this.jdxid = jdxid;
    }

    public String getJdxid() {
        return jdxid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPxjh(String pxjh) {
        this.pxjh = pxjh;
    }

    public String getPxjh() {
        return pxjh;
    }

    public void setPxfa(String pxfa) {
        this.pxfa = pxfa;
    }

    public String getPxfa() {
        return pxfa;
    }

    public void setPxzj(String pxzj) {
        this.pxzj = pxzj;
    }

    public String getPxzj() {
        return pxzj;
    }

    public void setPxBeiyong(String pxBeiyong) {
        this.pxBeiyong = pxBeiyong;
    }

    public String getPxBeiyong() {
        return pxBeiyong;
    }

    public void setCreateUserid(Long createUserid) {
        this.createUserid = createUserid;
    }

    public Long getCreateUserid() {
        return createUserid;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPxfaFilepath() {
        return pxfaFilepath;
    }

    public void setPxfaFilepath(String pxfaFilepath) {
        this.pxfaFilepath = pxfaFilepath;
    }

    public String getPxfaFilename() {
        return pxfaFilename;
    }

    public void setPxfaFilename(String pxfaFilename) {
        this.pxfaFilename = pxfaFilename;
    }

    public String getPxzjFilepath() {
        return pxzjFilepath;
    }

    public void setPxzjFilepath(String pxzjFilepath) {
        this.pxzjFilepath = pxzjFilepath;
    }

    public String getPxzjFilename() {
        return pxzjFilename;
    }

    public void setPxzjFilename(String pxzjFilename) {
        this.pxzjFilename = pxzjFilename;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("jdxid", getJdxid())
                .append("name", getName())
                .append("pxjh", getPxjh())
                .append("pxfa", getPxfa())
                .append("pxzj", getPxzj())
                .append("pxBeiyong", getPxBeiyong())
                .append("createUserid", getCreateUserid())
                .append("createTime", getCreateTime())
                .append("filePath", getFilePath())
                .append("fileName", getFileName())
                .append("pxfaFilepath", getPxfaFilepath())
                .append("pxfaFilename", getPxfaFilename())
                .append("pxzjFilepath", getPxzjFilepath())
                .append("pxzjFilename", getPxzjFilename())
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
