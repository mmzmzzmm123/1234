package com.ruoyi.gauge.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PsyGaugeQuestionsResultAll extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Integer id;

    @Excel(name = "用户ID")
    private Integer userId;


    @Excel(name = "问题")
    @NotNull(message = "问题id不能为空")
    private Integer questionId;

    @Excel(name = "问题选项集合")
    @NotNull(message = "问题选项id集不能为空")
    private Integer optionId;

    /** 得分 */
    @Excel(name = "得分")
    @NotBlank(message = "得分不能为空")
    private int value;

    /**
     * 订单id
     */
    @NotBlank(message = "订单id不能为空")
    private Integer orderId;
}
