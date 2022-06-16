package com.ruoyi.udef.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 计划对象 df_escape_schedule
 * 
 * @author ruoyi
 * @date 2022-06-09
 */
@Data
@Accessors(chain = true)
public class DfEscapeSchedule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 计划类型 */
    @Excel(name = "计划类型")
    private String escapeType;

    /** 平仓价格比 */
    @Excel(name = "平仓价格比")
    private Double priceRate;

    /** 平仓数量比 */
    @Excel(name = "平仓数量比")
    private Double quantityRate;

    /** 平仓数量 */
    @Excel(name = "平仓数量")
    private Integer quantity;

    /** 优先级 */
    @Excel(name = "优先级")
    private String priority;

    /** api */
    @Excel(name = "api")
    private Long robotId;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("escapeType", getEscapeType())
            .append("priceRate", getPriceRate())
            .append("quantityRate", getQuantityRate())
            .append("quantity", getQuantity())
            .append("priority", getPriority())
            .append("robotId", getRobotId())
            .toString();
    }
}
