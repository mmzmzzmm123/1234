package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 通知公告对象 qz_notice
 * 
 * @author zlx
 * @date 2021-08-10
 */
public class QzNotice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 消息ID */
    private Long noticeId;

    /** 圈子ID */
    @Excel(name = "圈子ID")
    private Long qzId;

    /** 碎片ID */
    @Excel(name = "碎片ID")
    private Long 
fragmentId;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long 
createUserId;

    /** 接收人 */
    @Excel(name = "接收人")
    private Long 
receiveUserId;

    /** 类型 */
    @Excel(name = "类型")
    private String noticeType;

    /** 消息内容 */
    @Excel(name = "消息内容")
    private String noticeContent;

    /** 帖子的图片 */
    @Excel(name = "帖子的图片")
    private String picUrl;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 扩展字段1 */
    private String ext1;

    /** 扩展字段2 */
    private String ext2;

    /** 扩展字段3 */
    private String ext3;

    public void setNoticeId(Long noticeId) 
    {
        this.noticeId = noticeId;
    }

    public Long getNoticeId() 
    {
        return noticeId;
    }
    public void setQzId(Long qzId) 
    {
        this.qzId = qzId;
    }

    public Long getQzId() 
    {
        return qzId;
    }
    public void setfragmentId(Long fragmentId)
    {
        this.fragmentId =
fragmentId;
    }

    public Long getfragmentId()
    {
        return 
fragmentId;
    }
    public void setcreateUserId(Long
createUserId) 
    {
        this.
createUserId = 
createUserId;
    }

    public Long getcreateUserId()
    {
        return 
createUserId;
    }
    public void setreceiveUserId(Long
receiveUserId) 
    {
        this.
receiveUserId = 
receiveUserId;
    }

    public Long getreceiveUserId()
    {
        return 
receiveUserId;
    }
    public void setNoticeType(String noticeType) 
    {
        this.noticeType = noticeType;
    }

    public String getNoticeType() 
    {
        return noticeType;
    }
    public void setNoticeContent(String noticeContent) 
    {
        this.noticeContent = noticeContent;
    }

    public String getNoticeContent() 
    {
        return noticeContent;
    }
    public void setPicUrl(String picUrl) 
    {
        this.picUrl = picUrl;
    }

    public String getPicUrl() 
    {
        return picUrl;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setExt1(String ext1) 
    {
        this.ext1 = ext1;
    }

    public String getExt1() 
    {
        return ext1;
    }
    public void setExt2(String ext2) 
    {
        this.ext2 = ext2;
    }

    public String getExt2() 
    {
        return ext2;
    }
    public void setExt3(String ext3) 
    {
        this.ext3 = ext3;
    }

    public String getExt3() 
    {
        return ext3;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("noticeId", getNoticeId())
            .append("qzId", getQzId())
            .append("fragmentId", getfragmentId())
            .append("createUserId", getcreateUserId())
            .append("receiveUserId", getreceiveUserId())
            .append("noticeType", getNoticeType())
            .append("noticeContent", getNoticeContent())
            .append("picUrl", getPicUrl())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("status", getStatus())
            .append("ext1", getExt1())
            .append("ext2", getExt2())
            .append("ext3", getExt3())
            .toString();
    }
}
