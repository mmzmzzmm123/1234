package com.ruoyi.note.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 便签标签对象 nm_note_tag
 * 
 * @author wang
 * @date 2020-09-12
 */
public class NmNoteTag extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 便签id */
    @Excel(name = "便签id")
    private Long noteId;

    /** 标签Id */
    @Excel(name = "标签Id")
    private Long tagId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setNoteId(Long noteId) 
    {
        this.noteId = noteId;
    }

    public Long getNoteId() 
    {
        return noteId;
    }
    public void setTagId(Long tagId) 
    {
        this.tagId = tagId;
    }

    public Long getTagId() 
    {
        return tagId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("noteId", getNoteId())
            .append("tagId", getTagId())
            .toString();
    }
}
