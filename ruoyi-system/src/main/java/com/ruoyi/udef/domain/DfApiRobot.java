package com.ruoyi.udef.domain;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 机器人对象 df_api_robot
 * 
 * @author ruoyi
 * @date 2022-06-09
 */
@Data
@Accessors(chain = true)
public class DfApiRobot extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 机器人 */
    @Excel(name = "机器人")
    private String robotName;

    /** 合约名 */
    @Excel(name = "合约名")
    private String symbol;

    /** 固定开仓比 */
    @Excel(name = "固定开仓比")
    private Double quantityBase;

    /** 固定开仓张数 */
    @Excel(name = "固定开仓张数")
    private Integer quantity;

    /** 小周期 */
    @Excel(name = "小周期")
    private Long litInterval;

    /** 大周期 */
    @Excel(name = "大周期")
    private Long bigInterval;

    /** 持仓模式 */
    @Excel(name = "持仓模式")
    private String margin;

    /** 杠杆 */
    @Excel(name = "杠杆")
    private Integer leverage;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** api */
    @Excel(name = "api")
    private Long apiId;

    /** 计划信息 */
    private List<DfEscapeSchedule> dfEscapeScheduleList;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("robotName", getRobotName())
            .append("symbol", getSymbol())
            .append("quantityBase", getQuantityBase())
            .append("quantity", getQuantity())
            .append("litInterval", getLitInterval())
            .append("bigInterval", getBigInterval())
            .append("margin", getMargin())
            .append("leverage", getLeverage())
            .append("status", getStatus())
            .append("apiId", getApiId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("dfEscapeScheduleList", getDfEscapeScheduleList())
            .toString();
    }
}
