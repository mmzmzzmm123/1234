package com.ruoyi.project.benyi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 培训视频评价反馈对象 by_train_video_feedback
 *
 * @author tsbz
 * @date 2020-06-01
 */
public class ByTrainVideoFeedback extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 视频id
     */
    @Excel(name = "视频id")
    private Long videoid;

    /**
     * 讲师id
     */
    @Excel(name = "讲师id")
    private Long lecturerid;

    /**
     * 反馈意见建议
     */
    @Excel(name = "反馈意见建议")
    private String content;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createuserid;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setVideoid(Long videoid) {
        this.videoid = videoid;
    }

    public Long getVideoid() {
        return videoid;
    }

    public void setLecturerid(Long lecturerid) {
        this.lecturerid = lecturerid;
    }

    public Long getLecturerid() {
        return lecturerid;
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

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("videoid", getVideoid())
                .append("lecturerid", getLecturerid())
                .append("content", getContent())
                .append("createuserid", getCreateuserid())
                .append("createtime", getCreatetime())
                .toString();
    }
}
