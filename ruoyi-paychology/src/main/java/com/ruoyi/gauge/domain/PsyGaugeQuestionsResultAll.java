package com.ruoyi.gauge.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *
 *
 * @author ruoyi
 * @date 2022-09-10
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PsyGaugeQuestionsResultAll extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "用户")
    private String userId;


    @Excel(name = "问题")
    @NotNull(message = "问题id不能为空")
    private Long questionId;

    @Excel(name = "问题选项集合")
    @NotNull(message = "问题选项id集不能为空")
    private Long optionId;

    /** 得分 */
    @Excel(name = "得分")
    @NotBlank(message = "得分不能为空")
    private int value;

    /**
     * 订单id
     */
    @NotBlank(message = "订单id不能为空")
    private String orderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "PsyGaugeQuestionsResultAll{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", questionId=" + questionId +
                ", optionId=" + optionId +
                ", value='" + value + '\'' +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}
