package com.ruoyi.udef.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 成交订单对象 df_trade_order
 * 
 * @author ruoyi
 * @date 2022-06-09
 */
@Data
@Accessors(chain = true)
public class DfTradeOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;

    /** orderId */
    @Excel(name = "orderId")
    private String orderId;

    /** 成交价 */
    @Excel(name = "成交价")
    private String price;

    /** 成交量 */
    @Excel(name = "成交量")
    private String qty;

    /** 成交额 */
    @Excel(name = "成交额")
    private String quoteQty;

    /** 实现盈亏 */
    @Excel(name = "实现盈亏")
    private String realizedPnl;

    /** 买卖方向 */
    @Excel(name = "买卖方向")
    private String side;

    /** 持仓方向 */
    @Excel(name = "持仓方向")
    private String positionSide;

    /** 交易对 */
    @Excel(name = "交易对")
    private String symbol;

    /** 时间 */
    @Excel(name = "时间")
    private String time;

    /** 机器人 */
    @Excel(name = "机器人")
    private Long robotId;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderId", getOrderId())
            .append("price", getPrice())
            .append("qty", getQty())
            .append("quoteQty", getQuoteQty())
            .append("realizedPnl", getRealizedPnl())
            .append("side", getSide())
            .append("positionSide", getPositionSide())
            .append("symbol", getSymbol())
            .append("time", getTime())
            .append("robotId", getRobotId())
            .toString();
    }
}
