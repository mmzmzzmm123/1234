package com.ruoyi.project.benyi.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 新闻中心对象 by_school_news
 *
 * @author tsbz
 * @date 2020-12-25
 */
public class BySchoolNews extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;

    /**
     * 封面图片
     */
    @Excel(name = "封面图片")
    private String imgurl;

    /**
     * 摘要
     */
    @Excel(name = "摘要")
    private String abstractcontent;

    /**
     * 类型
     */
    @Excel(name = "类型")
    private String type;

    /**
     * 内容
     */
    @Excel(name = "内容")
    private String content;

    /**
     * 是否删除
     */
    @Excel(name = "是否删除")
    private String isdel;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createuserid;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 所属学校
     */
    @Excel(name = "所属学校")
    private Long deptId;

    /**
     * 是否置顶
     */
    @Excel(name = "是否置顶")
    private String istop;

    /**
     * 是否审核
     */
    @Excel(name = "是否审核")
    private String ischeck;

    /**
     * 审核人
     */
    @Excel(name = "审核人")
    private Long checkuserid;

    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkTime;

    private String istb;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setAbstractcontent(String abstractcontent) {
        this.abstractcontent = abstractcontent;
    }

    public String getAbstractcontent() {
        return abstractcontent;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }

    public String getIsdel() {
        return isdel;
    }

    public void setCreateuserid(Long createuserid) {
        this.createuserid = createuserid;
    }

    public Long getCreateuserid() {
        return createuserid;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setIstop(String istop) {
        this.istop = istop;
    }

    public String getIstop() {
        return istop;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setCheckuserid(Long checkuserid) {
        this.checkuserid = checkuserid;
    }

    public Long getCheckuserid() {
        return checkuserid;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public String getIstb() {
        return istb;
    }

    public void setIstb(String istb) {
        this.istb = istb;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("title", getTitle())
                .append("imgurl", getImgurl())
                .append("abstractcontent", getAbstractcontent())
                .append("type", getType())
                .append("content", getContent())
                .append("isdel", getIsdel())
                .append("createuserid", getCreateuserid())
                .append("createTime", getCreateTime())
                .append("deptId", getDeptId())
                .append("istop", getIstop())
                .append("ischeck", getIscheck())
                .append("checkuserid", getCheckuserid())
                .append("checkTime", getCheckTime())
                .append("istb", getIstb())
                .toString();
    }


    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
