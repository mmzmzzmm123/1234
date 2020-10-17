package com.ruoyi.note.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

/**
 * 便签管理对象 nm_note
 *
 * @author wang
 * @date 2020-09-12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="nm_note")
public class NmNote
{
    private static final long serialVersionUID = 1L;

    /** Note便签ID */
    @Id
    @GeneratedValue(generator = "JDBC")//返回自增长主键
    @Column(name = "note_id")
    @Excel(name = "Note便签ID")
    private Long noteId;

    /** 用户ID */
    @Column(name = "user_id")
    @Excel(name = "用户ID")
    private Long userId;

    /** 便签标题(自动截取) */
    @Column(name = "title")
    @Excel(name = "便签标题(自动截取)")
    private String title;

    /** 摘要(前50字) 循环展示 */
    @Column(name = "description")
    @Excel(name = "摘要(前50字) 循环展示")
    private String description;

    /** 所属分组Id */
    @Column(name = "menu_id")
    @Excel(name = "所属分组Id")
    private Long menuId;

    /** 背景颜色 '白','红','黄','蓝','紫' */
    @Excel(name = "背景颜色 '白','红','黄','蓝','紫'")
    @Column(name = "background")
    private String background;

    /** 字数 */
    @Excel(name = "字数")
    @Column(name = "note_count")
    private Integer noteCount;

    /** 排序(小到大) */
    @Excel(name = "排序(小到大)")
    @Column(name = "note_sort")
    private Integer noteSort;

    /** 便签状态 */
    @Excel(name = "便签状态")
    @Column(name = "is_state")
    private Integer isState;

    /** 阅读进度 */
    @Excel(name = "阅读进度")
    @Column(name = "read_progress")
    private Integer readProgress;

    /** 是否星标 */
    @Excel(name = "是否星标")
    @Column(name = "is_star")
    private Integer isStar;

    /** 是否删除 */
    @Excel(name = "是否删除")
    @Column(name = "is_delete")
    private Integer isDelete;

    /** 是否置顶 */
    @Excel(name = "是否置顶")
    @Column(name = "top_flag")
    private Integer topFlag;

    /** 是否分享(判断是否可以随意访问) */
    @Excel(name = "是否分享(判断是否可以随意访问)")
    @Column(name = "is_share")
    private Integer isShare;

    /** 是否加密 */
    @Excel(name = "是否加密")
    @Column(name = "is_encryption")
    private Integer isEncryption;

    /** 创建时间 */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    private Date updateTime;


    @Column(name = "tiymce_ueditor")
    private  Long tiymceUeditor;

    /**富文本文章内容*/
    @Transient
    private  String ueditorContent;


}
