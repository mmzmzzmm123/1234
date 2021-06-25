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

    /** 培训领导小组名单路径 */
    @Excel(name = "培训领导小组名单路径")
    private String pxxzmdFilepath;

    /** 培训领导小组名单附件名称 */
    @Excel(name = "培训领导小组名单附件名称")
    private String pxxzmdFilename;

    /** 集中培训安排路径 */
    @Excel(name = "集中培训安排路径")
    private String jzpxapFilepath;

    /** 集中培训安排附件名称 */
    @Excel(name = "集中培训安排附件名称")
    private String jzpxapFilename;

    /** 培训导师带教计划路径 */
    @Excel(name = "培训导师带教计划路径")
    private String pxdsdjjhFilepath;

    /** 培训导师带教计划名称 */
    @Excel(name = "培训导师带教计划名称")
    private String pxdsdjjhFilename;

    /** 培训导师带教总结路径 */
    @Excel(name = "培训导师带教总结路径")
    private String pxdsdjzjFilepath;

    /** 培训导师带教总结名称 */
    @Excel(name = "培训导师带教总结名称")
    private String pxdsdjzjFilename;

    /** 带教导师推荐表 */
    @Excel(name = "带教导师推荐表")
    private String djdstjbFilepath;

    /** 带教导师推荐表名称 */
    @Excel(name = "带教导师推荐表名称")
    private String djdstjbFilename;

    /** 导师带教案例 */
    @Excel(name = "导师带教案例")
    private String djdsalFilepath;

    /** 带教导师案例名称 */
    @Excel(name = "带教导师案例名称")
    private String djdsalFilename;

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

    public void setPxxzmdFilepath(String pxxzmdFilepath)
    {
        this.pxxzmdFilepath = pxxzmdFilepath;
    }

    public String getPxxzmdFilepath()
    {
        return pxxzmdFilepath;
    }
    public void setPxxzmdFilename(String pxxzmdFilename)
    {
        this.pxxzmdFilename = pxxzmdFilename;
    }

    public String getPxxzmdFilename()
    {
        return pxxzmdFilename;
    }
    public void setJzpxapFilepath(String jzpxapFilepath)
    {
        this.jzpxapFilepath = jzpxapFilepath;
    }

    public String getJzpxapFilepath()
    {
        return jzpxapFilepath;
    }
    public void setJzpxapFilename(String jzpxapFilename)
    {
        this.jzpxapFilename = jzpxapFilename;
    }

    public String getJzpxapFilename()
    {
        return jzpxapFilename;
    }
    public void setPxdsdjjhFilepath(String pxdsdjjhFilepath)
    {
        this.pxdsdjjhFilepath = pxdsdjjhFilepath;
    }

    public String getPxdsdjjhFilepath()
    {
        return pxdsdjjhFilepath;
    }
    public void setPxdsdjjhFilename(String pxdsdjjhFilename)
    {
        this.pxdsdjjhFilename = pxdsdjjhFilename;
    }

    public String getPxdsdjjhFilename()
    {
        return pxdsdjjhFilename;
    }
    public void setPxdsdjzjFilepath(String pxdsdjzjFilepath)
    {
        this.pxdsdjzjFilepath = pxdsdjzjFilepath;
    }

    public String getPxdsdjzjFilepath()
    {
        return pxdsdjzjFilepath;
    }
    public void setPxdsdjzjFilename(String pxdsdjzjFilename)
    {
        this.pxdsdjzjFilename = pxdsdjzjFilename;
    }

    public String getPxdsdjzjFilename()
    {
        return pxdsdjzjFilename;
    }
    public void setDjdstjbFilepath(String djdstjbFilepath)
    {
        this.djdstjbFilepath = djdstjbFilepath;
    }

    public String getDjdstjbFilepath()
    {
        return djdstjbFilepath;
    }
    public void setDjdstjbFilename(String djdstjbFilename)
    {
        this.djdstjbFilename = djdstjbFilename;
    }

    public String getDjdstjbFilename()
    {
        return djdstjbFilename;
    }
    public void setDjdsalFilepath(String djdsalFilepath)
    {
        this.djdsalFilepath = djdsalFilepath;
    }

    public String getDjdsalFilepath()
    {
        return djdsalFilepath;
    }
    public void setDjdsalFilename(String djdsalFilename)
    {
        this.djdsalFilename = djdsalFilename;
    }

    public String getDjdsalFilename()
    {
        return djdsalFilename;
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
                .append("pxxzmdFilepath", getPxxzmdFilepath())
                .append("pxxzmdFilename", getPxxzmdFilename())
                .append("jzpxapFilepath", getJzpxapFilepath())
                .append("jzpxapFilename", getJzpxapFilename())
                .append("pxdsdjjhFilepath", getPxdsdjjhFilepath())
                .append("pxdsdjjhFilename", getPxdsdjjhFilename())
                .append("pxdsdjzjFilepath", getPxdsdjzjFilepath())
                .append("pxdsdjzjFilename", getPxdsdjzjFilename())
                .append("djdstjbFilepath", getDjdstjbFilepath())
                .append("djdstjbFilename", getDjdstjbFilename())
                .append("djdsalFilepath", getDjdsalFilepath())
                .append("djdsalFilename", getDjdsalFilename())
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
