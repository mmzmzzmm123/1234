package com.stdiet.custom.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;

/**
 * 视频分类对象 sys_video_classify
 *
 * @author xzj
 * @date 2021-05-05
 */
@Data
public class SysVideoClassify extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String cateName;

    /** 类型标识，0全部可看 1客户可看 2额外付费观看 */
    @Excel(name = "类型标识，0全部可看 1客户可看 2额外付费观看")
    private Integer payFlag;

    /** 删除标识 0未删除 1已删除 */
    private Integer delFlag;
}