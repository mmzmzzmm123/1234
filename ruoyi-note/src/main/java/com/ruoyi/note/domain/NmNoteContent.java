package com.ruoyi.note.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NmNoteContent
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


}
