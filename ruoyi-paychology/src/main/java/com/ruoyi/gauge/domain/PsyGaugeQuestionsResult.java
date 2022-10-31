package com.ruoyi.gauge.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 心理测评问题结果对象 psy_gauge_questions_result
 * 
 * @author ruoyi
 * @date 2022-09-10
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PsyGaugeQuestionsResult extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "用户")
    private String userId;

    @Excel(name = "量表")
    @NotNull(message = "量表id不能为空")
    private Long gaugeId;

    @Excel(name = "问题")
    @NotNull(message = "问题id不能为空")
    private Long questionsId;

    @Excel(name = "问题选项集合")
    @NotNull(message = "问题选项id集合不能为空")
    private List<Long> questionsOptionsIdList;

    private Long questionsOptionsId;

    /** 得分 */
    @Excel(name = "得分")
    private String score;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }
    public void setGaugeId(Long gaugeId) 
    {
        this.gaugeId = gaugeId;
    }

    public Long getGaugeId() 
    {
        return gaugeId;
    }
    public void setQuestionsId(Long questionsId) 
    {
        this.questionsId = questionsId;
    }

    public Long getQuestionsId() 
    {
        return questionsId;
    }
    public void setQuestionsOptionsId(Long questionsOptionsId) 
    {
        this.questionsOptionsId = questionsOptionsId;
    }

    public Long getQuestionsOptionsId() 
    {
        return questionsOptionsId;
    }
    public void setScore(String score) 
    {
        this.score = score;
    }

    public String getScore() 
    {
        return score;
    }

    public List<Long> getQuestionsOptionsIdList() {
        return questionsOptionsIdList;
    }

    public void setQuestionsOptionsIdList(List<Long> questionsOptionsIdList) {
        this.questionsOptionsIdList = questionsOptionsIdList;
    }

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
