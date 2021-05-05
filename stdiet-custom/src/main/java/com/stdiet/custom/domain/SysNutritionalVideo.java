package com.stdiet.custom.domain;

import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 营养视频对象 sys_nutritional_video
 *
 * @author xzj
 * @date 2021-04-29
 */
@Data
public class SysNutritionalVideo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 视频分类ID */
    @Excel(name = "视频分类ID")
    private Long cateId;

    private String cateName;

    /** 阿里云视频ID */
    @Excel(name = "阿里云视频ID")
    private String videoId;

    /** 视频封面URL */
    @Excel(name = "视频封面URL")
    private String coverUrl;

    /** 视频标题 */
    @Excel(name = "视频标题")
    private String title;

    /** 视频描述 */
    @Excel(name = "视频描述")
    private String description;

    /** 视频大小，单位：字节 */
    @Excel(name = "视频大小，单位：字节")
    private Long videoSize;

    /** 标签，使用 , 隔开 */
    @Excel(name = "标签，使用 , 隔开")
    private String tags;

    /** 是否显示，0不显示 1显示，默认0 */
    @Excel(name = "是否显示，0不显示 1显示，默认0")
    private Integer showFlag;

    /** 优先级，默认0 */
    @Excel(name = "优先级，默认0")
    private Long priorityLevel;

    /** 视频付费等级，0无需付费 1客户可看 2付费可看 */
    @Excel(name = "视频付费等级，0无需付费 1客户可看 2付费可看")
    private Long payLevel;

    private String payLevelName;

    /** 删除标识，0未删除 1已删除，默认0 */
    private Integer delFlag;

    //文件名称
    private String fileName;

    private String key;
}