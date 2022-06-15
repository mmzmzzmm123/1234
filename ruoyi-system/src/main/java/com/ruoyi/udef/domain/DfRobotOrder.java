package com.ruoyi.udef.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 机器人订单对象 df_robot_order
 * 
 * @author ruoyi
 * @date 2022-06-09
 */
@Data
@Accessors(chain = true)
public class DfRobotOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 前置余额 */
    @Excel(name = "前置余额")
    private String predictBalance;

    /** 当前余额 */
    @Excel(name = "当前余额")
    private String balance;

    /** 最大持仓 */
    @Excel(name = "最大持仓")
    private Integer maxPosition;

    /** 当前持仓 */
    @Excel(name = "当前持仓")
    private Integer position;

    /** 开仓方向 */
    @Excel(name = "开仓方向")
    private String openSide;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 机器人 */
    @Excel(name = "机器人")
    private Long robotId;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("predictBalance", getPredictBalance())
            .append("balance", getBalance())
            .append("maxPosition", getMaxPosition())
            .append("position", getPosition())
            .append("openSide", getOpenSide())
            .append("status", getStatus())
            .append("robotId", getRobotId())
            .toString();
    }
}
