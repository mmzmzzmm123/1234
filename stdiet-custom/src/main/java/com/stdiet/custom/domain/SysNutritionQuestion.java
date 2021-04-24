package com.stdiet.custom.domain;

import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 营养知识小问答对象 sys_nutrition_question
 *
 * @author xzj
 * @date 2021-04-13
 */
@Data
public class SysNutritionQuestion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    private Long[] ids;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    /** 关键词，逗号隔开 */
    @Excel(name = "关键词，逗号隔开")
    private String key;

    /** 标题内容索引分词之后的关键词 */
    @Excel(name = "标题内容索引分词之后的关键词")
    private String titleContentIndex;

    /** 是否在小程序显示，0 不显示 1显示，默认0 */
    @Excel(name = "是否在小程序显示，0 不显示 1显示，默认0")
    private Integer showFlag;

    /** 删除标识 0未删除 1已删除 */
    private Integer delFlag;

    //解答问题对应的ID
    private Long askQuestionId;
}