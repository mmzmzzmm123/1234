package com.stdiet.custom.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stdiet.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class NutritionQuestionResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 标题 */
    private String title;

    /** 内容 */
    private String content;

    /** 关键词，逗号隔开 */
    private String key;

    /** 小程序是否显示，0不显示 1显示 */
    private String showFlag;
}
