package com.ruoyi.note.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;

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


}
