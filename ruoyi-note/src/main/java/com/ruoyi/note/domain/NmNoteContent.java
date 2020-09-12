package com.ruoyi.note.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 文章内容对象 nm_note_content
 * 
 * @author wang
 * @date 2020-09-12
 */
public class NmNoteContent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 便签主键ID */
    private Long noteId;

    /** markdown编辑器内容 */
    @Excel(name = "markdown编辑器内容")
    private String markdownContent;

    /** ueditor编辑器内容 */
    @Excel(name = "ueditor编辑器内容")
    private String ueditorContent;

    public void setNoteId(Long noteId) 
    {
        this.noteId = noteId;
    }

    public Long getNoteId() 
    {
        return noteId;
    }
    public void setMarkdownContent(String markdownContent) 
    {
        this.markdownContent = markdownContent;
    }

    public String getMarkdownContent() 
    {
        return markdownContent;
    }
    public void setUeditorContent(String ueditorContent) 
    {
        this.ueditorContent = ueditorContent;
    }

    public String getUeditorContent() 
    {
        return ueditorContent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("noteId", getNoteId())
            .append("markdownContent", getMarkdownContent())
            .append("ueditorContent", getUeditorContent())
            .toString();
    }
}
