package com.ruoyi.note.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NmNoteTag
{


    /** id */
    private Long id;

    /** 便签id */
    @Excel(name = "便签id")
    private Long noteId;

    /** 标签Id */
    @Excel(name = "标签Id")
    private Long tagId;

}
