package com.ruoyi.note.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 便签的标签对象 nm_note_tag
 * 
 * @author ruoyi
 * @date 2020-09-20
 */
public class NmNoteTag extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long noteTagId;

    /** 便签id */
    @Excel(name = "便签id")
    private Long noteId;

    /** tagId */
    @Excel(name = "tagId")
    private Long tagId;

    public void setNoteTagId(Long noteTagId) 
    {
        this.noteTagId = noteTagId;
    }

    public Long getNoteTagId() 
    {
        return noteTagId;
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
            .append("noteTagId", getNoteTagId())
            .append("noteId", getNoteId())
            .append("tagId", getTagId())
            .toString();
    }
}
