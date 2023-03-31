package com.geek.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.geek.common.annotation.Excel;
import com.geek.common.core.domain.BaseEntity;

/**
 * 通知公告对象 sys_notice
 * 
 * @author xuek
 * @date 2023-03-29
 */
public class SysNotice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 公告ID */
    private Integer noticeId;

    /** appId */
    @Excel(name = "appId")
    private String appid;

    /** 公告标题 */
    @Excel(name = "公告标题")
    private String noticeTitle;

    /** 公告类型（1通知 2公告） */
    @Excel(name = "公告类型", readConverterExp = "1=通知,2=公告")
    private String noticeType;

    /** 公告内容 */
    @Excel(name = "公告内容")
    private String noticeContent;

    /** 公告状态（0正常 1关闭） */
    @Excel(name = "公告状态", readConverterExp = "0=正常,1=关闭")
    private String status;

    public void setNoticeId(Integer noticeId){
        this.noticeId = noticeId;
    }

    public Integer getNoticeId() {
        return noticeId;
    }
    public void setAppid(String appid){
        this.appid = appid;
    }

    public String getAppid() {
        return appid;
    }
    public void setNoticeTitle(String noticeTitle){
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }
    public void setNoticeType(String noticeType){
        this.noticeType = noticeType;
    }

    public String getNoticeType() {
        return noticeType;
    }
    public void setNoticeContent(String noticeContent){
        this.noticeContent = noticeContent;
    }

    public String getNoticeContent() {
        return noticeContent;
    }
    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("noticeId", getNoticeId())
            .append("appid", getAppid())
            .append("noticeTitle", getNoticeTitle())
            .append("noticeType", getNoticeType())
            .append("noticeContent", getNoticeContent())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
