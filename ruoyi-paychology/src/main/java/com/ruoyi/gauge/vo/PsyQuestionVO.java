package com.ruoyi.gauge.vo;

import com.ruoyi.gauge.domain.PsyGaugeQuestionsOptions;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @User hogan
 * @Time 2022/10/20 14:33
 * @e-mail hkcugwh@163.com
 **/
@Data
@ApiModel("测评问题")
public class PsyQuestionVO {

    @ApiModelProperty("问题id")
    private Long id;

    @ApiModelProperty("问题描述")
    private String title;

    @ApiModelProperty("问题序号")
    private Long no;

    @ApiModelProperty("问题选择类型(0-单选，1-多选)")
    private Long selectType;

    @ApiModelProperty("问题选项")
    private List<PsyGaugeQuestionsOptions> options;

    @ApiModelProperty("答案")
    private List<Long> answers;

}
