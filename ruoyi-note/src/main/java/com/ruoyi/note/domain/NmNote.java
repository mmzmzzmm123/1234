package com.ruoyi.note.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 便签管理对象 nm_note
 *
 * @author wang
 * @date 2020-09-12
 */
public class NmNote
{
    private static final long serialVersionUID = 1L;

    /** Note便签ID */
    @Excel(name = "Note便签ID")
    private Long noteId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 便签标题(自动截取) */
    @Excel(name = "便签标题(自动截取)")
    private String title;

    /** 摘要(前50字) 循环展示 */
    @Excel(name = "摘要(前50字) 循环展示")
    private String description;

    /** 所属分组Id */
    @Excel(name = "所属分组Id")
    private Integer menuId;

    /** 背景颜色 '白','红','黄','蓝','紫' */
    @Excel(name = "背景颜色 '白','红','黄','蓝','紫'")
    private String background;

    /** 字数 */
    @Excel(name = "字数")
    private Integer noteCount;

    /** 排序(小到大) */
    @Excel(name = "排序(小到大)")
    private Integer noteSort;

    /** 便签状态 */
    @Excel(name = "便签状态")
    private Integer isState;

    /** 阅读进度 */
    @Excel(name = "阅读进度")
    private Integer readProgress;

    /** 是否星标 */
    @Excel(name = "是否星标")
    private Integer isStar;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer isDelete;

    /** 是否置顶 */
    @Excel(name = "是否置顶")
    private Integer topFlag;

    /** 是否分享(判断是否可以随意访问) */
    @Excel(name = "是否分享(判断是否可以随意访问)")
    private Integer isShare;

    /** 是否加密 */
    @Excel(name = "是否加密")
    private Integer isEncryption;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public void setNoteId(Long noteId)
    {
        this.noteId = noteId;
    }

    public Long getNoteId()
    {
        return noteId;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setMenuId(Integer menuId)
    {
        this.menuId = menuId;
    }

    public Integer getMenuId()
    {
        return menuId;
    }
    public void setBackground(String background)
    {
        this.background = background;
    }

    public String getBackground()
    {
        return background;
    }
    public void setNoteCount(Integer noteCount)
    {
        this.noteCount = noteCount;
    }

    public Integer getNoteCount()
    {
        return noteCount;
    }
    public void setNoteSort(Integer noteSort)
    {
        this.noteSort = noteSort;
    }

    public Integer getNoteSort()
    {
        return noteSort;
    }
    public void setIsState(Integer isState)
    {
        this.isState = isState;
    }

    public Integer getIsState()
    {
        return isState;
    }
    public void setReadProgress(Integer readProgress)
    {
        this.readProgress = readProgress;
    }

    public Integer getReadProgress()
    {
        return readProgress;
    }
    public void setIsStar(Integer isStar)
    {
        this.isStar = isStar;
    }

    public Integer getIsStar()
    {
        return isStar;
    }
    public void setIsDelete(Integer isDelete)
    {
        this.isDelete = isDelete;
    }

    public Integer getIsDelete()
    {
        return isDelete;
    }
    public void setTopFlag(Integer topFlag)
    {
        this.topFlag = topFlag;
    }

    public Integer getTopFlag()
    {
        return topFlag;
    }
    public void setIsShare(Integer isShare)
    {
        this.isShare = isShare;
    }

    public Integer getIsShare()
    {
        return isShare;
    }
    public void setIsEncryption(Integer isEncryption)
    {
        this.isEncryption = isEncryption;
    }

    public Integer getIsEncryption()
    {
        return isEncryption;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("noteId", getNoteId())
            .append("userId", getUserId())
            .append("title", getTitle())
            .append("description", getDescription())
            .append("menuId", getMenuId())
            .append("background", getBackground())
            .append("noteCount", getNoteCount())
            .append("noteSort", getNoteSort())
            .append("isState", getIsState())
            .append("readProgress", getReadProgress())
            .append("isStar", getIsStar())
            .append("isDelete", getIsDelete())
            .append("topFlag", getTopFlag())
            .append("isShare", getIsShare())
            .append("isEncryption", getIsEncryption())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
