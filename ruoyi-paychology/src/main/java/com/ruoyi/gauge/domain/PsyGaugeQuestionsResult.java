package com.ruoyi.gauge.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 心理测评问题结果对象 psy_gauge_questions_result
 * 
 * @author ruoyi
 * @date 2022-09-10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PsyGaugeQuestionsResult extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Integer id;

    @Excel(name = "用户ID")
    private Integer userId;

    @Excel(name = "量表")
    @NotNull(message = "量表id不能为空")
    private Integer gaugeId;

    @Excel(name = "问题")
    @NotNull(message = "问题id不能为空")
    private Integer questionsId;

    @Excel(name = "问题选项集合")
    @NotNull(message = "问题选项id集合不能为空")
    private List<Integer> questionsOptionsIdList;

    private Integer questionsOptionsId;

    /** 得分 */
    @Excel(name = "得分")
    private Integer score;

    /**
     * 订单id
     */
    private Integer orderId;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("gaugeId", getGaugeId())
            .append("questionsId", getQuestionsId())
            .append("questionsOptionsId", getQuestionsOptionsId())
            .append("score", getScore())
            .append("createTime", getCreateTime())
            .toString();
    }
}
