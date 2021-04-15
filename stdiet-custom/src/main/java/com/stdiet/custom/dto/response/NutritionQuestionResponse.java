package com.stdiet.custom.dto.response;

import com.stdiet.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

@Data
public class NutritionQuestionResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    /** 关键词，逗号隔开 */
    @Excel(name = "关键词，逗号隔开")
    private String key;
}
