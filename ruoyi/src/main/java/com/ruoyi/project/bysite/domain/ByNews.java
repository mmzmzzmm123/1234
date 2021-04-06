package com.ruoyi.project.bysite.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 新闻中心对象 by_news
 *
 * @author tsbz
 * @date 2020-06-28
 */
public class ByNews extends BaseEntity {
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
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createuserid;

    /**
     * 是否第三方平台资源
     */
    @Excel(name = "是否第三方平台资源")
    private String isthirdparty;

    /**
     * 第三方路径
     */
    @Excel(name = "第三方路径")
    private String url;

    /**
     * 是否发布
     */
    @Excel(name = "是否发布")
    private String isrelease;

    /**
     * 是否删除
     */
    @Excel(name = "是否删除")
    private String isdel;

    /**
     * 摘要
     */
    @Excel(name = "摘要")
    private String abstractcontent;

    /**
     * 编号
     */
    private Long schoolid;

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

    public void setCreateuserid(Long createuserid) {
        this.createuserid = createuserid;
    }

    public Long getCreateuserid() {
        return createuserid;
    }

    public void setIsthirdparty(String isthirdparty) {
        this.isthirdparty = isthirdparty;
    }

    public String getIsthirdparty() {
        return isthirdparty;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setIsrelease(String isrelease) {
        this.isrelease = isrelease;
    }

    public String getIsrelease() {
        return isrelease;
    }

    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }

    public String getIsdel() {
        return isdel;
    }

    public void setAbstractcontent(String abstractcontent) {
        this.abstractcontent = abstractcontent;
    }

    public String getAbstractcontent() {
        return abstractcontent;
    }

    public Long getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(Long schoolid) {
        this.schoolid = schoolid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("title", getTitle())
                .append("imgurl", getImgurl())
                .append("type", getType())
                .append("content", getContent())
                .append("createuserid", getCreateuserid())
                .append("isthirdparty", getIsthirdparty())
                .append("url", getUrl())
                .append("isrelease", getIsrelease())
                .append("isdel", getIsdel())
                .append("createTime", getCreateTime())
                .append("abstractcontent", getAbstractcontent())
                .append("schoolid", getSchoolid())
                .toString();
    }
}
