package com.ruoyi.note.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * share书签分享对象 nm_note_share
 * 
 * @author ruoyi
 * @date 2020-09-20
 */
public class NmNoteShare extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 便签ID */
    private Long noteId;

    /** 是否加密 */
    @Excel(name = "是否加密")
    private Integer isEncryption;

    /** 分享的二维码 */
    @Excel(name = "分享的二维码")
    private String noteQrcode;

    /** 分享的URl */
    @Excel(name = "分享的URl")
    private String shareUrl;

    /** 分享密码(vip) */
    @Excel(name = "分享密码(vip)")
    private String sharePassword;

    /** 生成长图分享 */
    @Excel(name = "生成长图分享")
    private String shareImage;

    /** 到期日(vip)  */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到期日(vip) ", width = 30, dateFormat = "yyyy-MM-dd")
    private Date abortTime;

    public void setNoteId(Long noteId) 
    {
        this.noteId = noteId;
    }

    public Long getNoteId() 
    {
        return noteId;
    }
    public void setIsEncryption(Integer isEncryption) 
    {
        this.isEncryption = isEncryption;
    }

    public Integer getIsEncryption() 
    {
        return isEncryption;
    }
    public void setNoteQrcode(String noteQrcode) 
    {
        this.noteQrcode = noteQrcode;
    }

    public String getNoteQrcode() 
    {
        return noteQrcode;
    }
    public void setShareUrl(String shareUrl) 
    {
        this.shareUrl = shareUrl;
    }

    public String getShareUrl() 
    {
        return shareUrl;
    }
    public void setSharePassword(String sharePassword) 
    {
        this.sharePassword = sharePassword;
    }

    public String getSharePassword() 
    {
        return sharePassword;
    }
    public void setShareImage(String shareImage) 
    {
        this.shareImage = shareImage;
    }

    public String getShareImage() 
    {
        return shareImage;
    }
    public void setAbortTime(Date abortTime) 
    {
        this.abortTime = abortTime;
    }

    public Date getAbortTime() 
    {
        return abortTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("noteId", getNoteId())
            .append("isEncryption", getIsEncryption())
            .append("noteQrcode", getNoteQrcode())
            .append("shareUrl", getShareUrl())
            .append("sharePassword", getSharePassword())
            .append("shareImage", getShareImage())
            .append("abortTime", getAbortTime())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
