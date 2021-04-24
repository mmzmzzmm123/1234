package com.stdiet.custom.domain;

import lombok.Data;
import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;

/**
 * 营养小知识提问对象 sys_ask_nutrition_question
 *
 * @author xzj
 * @date 2021-04-23
 */
@Data
public class SysAskNutritionQuestion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 提问者微信openid */
    @Excel(name = "提问者微信openid")
    private String openid;

    /** 问题内容 */
    @Excel(name = "问题内容")
    private String question;

    /** 问题状态，0未回答 1已回答  2已关闭 */
    @Excel(name = "问题状态")
    private Integer replyFlag;

    /** 解答之后对应的营养知识ID */
    @Excel(name = "解答之后对应的营养知识ID")
    private Long nutritionQuestionId;

    /** 删除标识 0未删除 1已删除 */
    private Integer delFlag;
}