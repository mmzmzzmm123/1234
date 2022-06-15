package com.ruoyi.driver;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoyi.common.utils.DateToLongSerializer;
import com.ruoyi.driver.utils.DateUtils;
import com.ruoyi.udef.domain.DfTradeOrder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class DriverDto {
    @Data
    public static class KlineItem {
        @JsonSerialize(using = DateToLongSerializer.class)
        private Date timestamp;
        private Double open;
        private Double high;
        private Double low;
        private Double close;
        private Double volume;

        public KlineItem() {
        }

        public KlineItem(List<String> args) {
            try {
                if(args.get(0).indexOf("-") > 0 || args.get(0).indexOf(":") > 0)
                    this.timestamp = DateUtils.parseUTCTime(args.get(0));
                else if(args.get(0).length() == 13){
                    this.timestamp = new Date(Long.parseLong(args.get(0)));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            this.open = Double.parseDouble(args.get(1));
            this.high = Double.parseDouble(args.get(2));
            this.low = Double.parseDouble(args.get(3));
            this.close = Double.parseDouble(args.get(4));
            this.volume = Double.parseDouble(args.get(5));
        }

    }

    @Data
    public static class DualModel {
        private Boolean dualSidePosition;//": true // "true": 双向持仓模式；"false": 单向持仓模式
    }

    @Data
    @Accessors(chain = true)
    public static class DualModelReq {
        private String dualSidePosition;//	STRING	YES	"true": 双向持仓模式；"false": 单向持仓模式
        private Long recvWindow;//	LONG	NO
    }

    @Data
    @Accessors(chain = true)
    public static class MarginReq {
        private String symbol;//	STRING	YES	交易对
        private String marginType;//	ENUM	YES	保证金模式 ISOLATED(逐仓), CROSSED(全仓)
        private String recvWindow;//	LONG	NO
    }

    @Data
    public static class SampleResponse {
        private Integer code;//": 200,
        private String msg;//": "success"
    }

    /***
     * 根据 order type的不同,某些参数强制要求,具体如下:
     *
     * Type	强制要求的参数
     * LIMIT	timeInForce, quantity, price
     * MARKET	quantity
     * STOP, TAKE_PROFIT	quantity, price, stopPrice
     * STOP_MARKET, TAKE_PROFIT_MARKET	stopPrice
     * TRAILING_STOP_MARKET	callbackRate
     * 条件单的触发必须:
     *
     * 如果订单参数priceProtect为true:
     * 达到触发价时，MARK_PRICE(标记价格)与CONTRACT_PRICE(合约最新价)之间的价差不能超过改symbol触发保护阈值
     * 触发保护阈值请参考接口GET /dapi/v1/exchangeInfo 返回内容相应symbol中"triggerProtect"字段
     * STOP, STOP_MARKET 止损单:
     * 买入: 最新合约价格/标记价格高于等于触发价stopPrice
     * 卖出: 最新合约价格/标记价格低于等于触发价stopPrice
     * TAKE_PROFIT, TAKE_PROFIT_MARKET 止盈单:
     * 买入: 最新合约价格/标记价格低于等于触发价stopPrice
     * 卖出: 最新合约价格/标记价格高于等于触发价stopPrice
     * TRAILING_STOP_MARKET 跟踪止损单:
     * 买入: 当合约价格/标记价格区间最低价格低于激活价格activationPrice,且最新合约价格/标记价高于等于最低价设定回调幅度。
     * 卖出: 当合约价格/标记价格区间最高价格高于激活价格activationPrice,且最新合约价格/标记价低于等于最高价设定回调幅度。
     * TRAILING_STOP_MARKET 跟踪止损单如果遇到报错 {"code": -2021, "msg": "Order would immediately trigger."}
     * 表示订单不满足以下条件:
     *
     * 买入: 指定的activationPrice 必须小于 latest price
     * 卖出: 指定的activationPrice 必须大于 latest price
     * newOrderRespType 如果传 RESULT:
     *
     * MARKET 订单将直接返回成交结果；
     * 配合使用特殊 timeInForce 的 LIMIT 订单将直接返回成交或过期拒绝结果。
     * STOP_MARKET, TAKE_PROFIT_MARKET 配合 closePosition=true:
     *
     * 条件单触发依照上述条件单触发逻辑
     * 条件触发后,平掉当时持有所有多头仓位(若为卖单)或当时持有所有空头仓位(若为买单)
     * 不支持 quantity 参数
     * 自带只平仓属性,不支持reduceOnly参数
     * 双开模式下,LONG方向上不支持BUY; SHORT 方向上不支持SELL
     */
    @Data
    @Accessors(chain = true)
    public static class OrderReq {
        private String symbol;//	STRING	YES	交易对
        private String side;//	ENUM	YES	买卖方向 SELL, BUY

        private String positionSide;//	ENUM	NO	持仓方向,单向持仓模式下非必填,默认且仅可填BOTH;在双向持仓模式下必填,且仅可选择 LONG 或 SHORT
        private String type;//	ENUM	YES	订单类型 LIMIT, MARKET, STOP, TAKE_PROFIT, STOP_MARKET, TAKE_PROFIT_MARKET, TRAILING_STOP_MARKET

        private String reduceOnly;//	STRING	NO	true, false; 非双开模式下默认false；双开模式下不接受此参数； 使用closePosition不支持此参数。
        private Double quantity;//	DECIMAL	NO	下单数量,使用closePosition不支持此参数。
        private Double price;//	DECIMAL	NO	委托价格
        private String newClientOrderId;//	STRING	NO	用户自定义的订单号,不可以重复出现在挂单中。如空缺系统会自动赋值。必须满足正则规则 ^[\.A-Z\:/a-z0-9_-]{1,36}$
        private String stopPrice;//	DECIMAL	NO	触发价, 仅 STOP, STOP_MARKET, TAKE_PROFIT, TAKE_PROFIT_MARKET 需要此参数
        private String closePosition;//	STRING	NO	true, false；触发后全部平仓,仅支持STOP_MARKET和TAKE_PROFIT_MARKET；不与quantity合用；自带只平仓效果,不与reduceOnly 合用
        private String activationPrice;//	DECIMAL	NO	追踪止损激活价格,仅TRAILING_STOP_MARKET 需要此参数, 默认为下单当前市场价格(支持不同workingType)
        private String callbackRate;//	DECIMAL	NO	追踪止损回调比例,可取值范围[0.1, 4],其中 1代表1% ,仅TRAILING_STOP_MARKET 需要此参数
        private String timeInForce;//	ENUM	NO	有效方法
        private String workingType = "MARK_PRICE";//	ENUM	NO	stopPrice 触发类型: MARK_PRICE(标记价格), CONTRACT_PRICE(合约最新价). 默认 CONTRACT_PRICE
        private String priceProtect;//	STRING	NO	条件单触发保护："TRUE","FALSE", 默认"FALSE". 仅 STOP, STOP_MARKET, TAKE_PROFIT, TAKE_PROFIT_MARKET 需要此参数
        private String newOrderRespType;//	ENUM	NO	"ACK", "RESULT", 默认 "ACK"
        private Long recvWindow;//	LONG	NO
    }

    @Data
    public static class OrderResp {
        private String clientOrderId;//": "testOrder", // 用户自定义的订单号
        private String cumQty;//": "0",
        private String cumBase;//": "0", // 成交额(标的数量)
        private String executedQty;//": "0", // 成交量(张数)
        private String orderId;//": 22542179, // 系统订单号
        private String avgPrice;//": "0.0",      // 平均成交价
        private String origQty;//": "10", // 原始委托数量
        private String price;//": "0", // 委托价格
        private String reduceOnly;//": false, // 仅减仓
        private String closePosition;//": false,   // 是否条件全平仓
        private String side;//": "SELL", // 买卖方向
        private String positionSide;//": "SHORT", // 持仓方向
        private String status;//": "NEW", // 订单状态
        private String stopPrice;//": "0", // 触发价,对`TRAILING_STOP_MARKET`无效
        private String symbol;//": "BTCUSD_200925", // 交易对
        private String pair;//": "BTCUSD",   // 标的交易对
        private String timeInForce = "5000";//": "GTC", // 有效方法
        private String type;//": "TRAILING_STOP_MARKET", // 订单类型
        private String origType;//": "TRAILING_STOP_MARKET",  // 触发前订单类型
        private String activatePrice;//": "9020", // 跟踪止损激活价格, 仅`TRAILING_STOP_MARKET` 订单返回此字段
        private String priceRate;//": "0.3", // 跟踪止损回调比例, 仅`TRAILING_STOP_MARKET` 订单返回此字段
        private String updateTime;//": 1566818724722, // 更新时间
        private String workingType;//": "CONTRACT_PRICE", // 条件价格触发类型
        private String priceProtect;//": false            // 是否开启条件单触发保护
    }


    @Data
    public class UserBalance {
        private String accountAlias;//": "SgsR",    // 账户唯一识别码
        private String asset;//": "BTC",     // 资产
        private String balance;//": "0.00250000",    // 账户余额
        private String withdrawAvailable;//": "0.00250000", // 最大可提款金额,同`GET /dapi/account`中"maxWithdrawAmount"
        private String crossWalletBalance;//": "0.00241969", // 全仓账户余额
        private String crossUnPnl;//": "0.00000000", // 全仓持仓未实现盈亏
        private String availableBalance;//": "0.00241969",    // 可用下单余额
        private Long updateTime;//": 1592468353979
    }

    @Data
    public class Account {
        private List<Assert> assets;
        private List<Position> positions;

        private Boolean canDeposit;//": true, // 是否可以入金
        private Boolean canTrade;//": true, // 是否可以交易
        private Boolean canWithdraw;//": true, // 是否可以出金
        private Integer feeTier;//": 2, // 手续费等级
        private Long updateTime;//": 0
    }

    @Data
    public class Assert {
        private String asset;//": "BTC",  // 资产名
        private String walletBalance;//": "0.00241969",  // 账户余额
        private String unrealizedProfit;//": "0.00000000",  // 全部持仓未实现盈亏
        private String marginBalance;//": "0.00241969",  // 保证金余额
        private String maintMargin;//": "0.00000000",    // 维持保证金
        private String initialMargin;//": "0.00000000",  // 当前所需起始保证金(按最新标标记价格)
        private String positionInitialMargin;//": "0.00000000",  // 当前所需持仓起始保证金(按最新标标记价格)
        private String openOrderInitialMargin;//": "0.00000000",  // 当前所需挂单起始保证金(按最新标标记价格)
        private String maxWithdrawAmount;//": "0.00241969",  // 最大可提款金额
        private String crossWalletBalance;//": "0.00241969",  // 可用于全仓的账户余额
        private String crossUnPnl;//": "0.00000000",  // 所有全仓持仓的未实现盈亏
        private String availableBalance;//": "0.00241969"  // 可用下单余额
    }

    @Data
    public class Position {
        private String symbol;//": "BTCUSD_201225",  // 交易对
        private String positionAmt;//" "0",  // 持仓数量
        private String initialMargin;//": "0",   // 当前所需起始保证金(按最新标标记价格)
        private String maintMargin;//": "0", // 持仓维持保证金
        private String unrealizedProfit;//": "0.00000000",  // 持仓未实现盈亏
        private String positionInitialMargin;//": "0",  // 当前所需持仓起始保证金(按最新标标记价格)
        private String openOrderInitialMargin;//": "0",  // 当前所需挂单起始保证金(按最新标标记价格)
        private String leverage;//": "125",  // 杠杆倍率
        private String isolated;//": false,  // 是否是逐仓模式
        private String positionSide;//": "BOTH", // 持仓方向
        private String entryPrice;//": "0.0",    // 平均持仓成本
        private String updateTime;//":0,  // 最新更新时间
        private String maxQty;//": "50"  // 当前杠杆下最大可开仓数(标的数量)
    }


    @Data
    public class PositionRisk {
        private String symbol;//": "BTCUSD_201225",  // 交易对
        private String positionAmt;//": "0", // 头寸数量,符号代表多空方向, 正数为多,负数为空
        private String entryPrice;//": "0.0",    // 开仓均价
        private String markPrice;//": "0.00000000",  // 当前标记价格
        private String unRealizedProfit;//": "0.00000000",   // 持仓未实现盈亏
        private String liquidationPrice;//": "0",    // 参考强平价格
        private String leverage;//": "125",  // 当前杠杆倍数
        private String maxQty;//": "50",  // 当前杠杆倍数允许的数量上限(标的数量)
        private String marginType;//": "cross",  // 逐仓模式或全仓模式
        private String isolatedMargin;//": "0.00000000", // 逐仓保证金
        private String isAutoAddMargin;//": "false",
        private String positionSide;//": "BOTH",  // 持仓方向
        private Long updateTime;//":0  // 最新更新时间
    }


    @Data
    @Accessors(chain = true)
    public static class LeverageReq {
        private String symbol;//	STRING	YES	交易对
        private Integer leverage;//	INT	YES	目标杠杆倍数
        private Long recvWindow;//	LONG	NO
    }

    @Data
    @Accessors(chain = true)
    public static class LeverageResp {
        private Double leverage;//": 21, // 杠杆倍数
        private String maxQty;//": "1000", // 当前杠杆倍数下允许的最大base asset数量
        private String symbol;//": "BTCUSD_200925"   // 交易对
    }

    @Data
    public class Order {
        private String avgPrice;//": "0.0",              // 平均成交价
        private String clientOrderId;//": "abc",             // 用户自定义的订单号
        private String cumBase;//": "0",                         // 成交金额(标的数量)
        private String executedQty;//": "0",                 // 成交量(张数)
        private Long orderId;//": 1917641,                 // 系统订单号
        private String origQty;//": "0.40",                  // 原始委托数量
        private String origType;//": "TRAILING_STOP_MARKET", // 触发前订单类型
        private String price;//": "0",                   // 委托价格
        private String reduceOnly;//": false,                // 是否仅减仓
        private String side;//": "BUY",                      // 买卖方向
        private String positionSide;//": "SHORT",            // 持仓方向
        private String status;//": "NEW",                    // 订单状态
        private String stopPrice;//": "9300",                    // 触发价,对`TRAILING_STOP_MARKET`无效
        private Boolean closePosition;//": false,   // 是否条件全平仓
        private String symbol;//": "BTCUSD_200925",              // 交易对
        private String pair;//": "BTCUSD",   // 标的交易对
        private Long time;//": 1579276756075,              // 订单时间
        private String timeInForce;//": "GTC",               // 有效方法
        private String type;//": "TRAILING_STOP_MARKET",     // 订单类型
        private String activatePrice;//": "9020", // 跟踪止损激活价格, 仅`TRAILING_STOP_MARKET` 订单返回此字段
        private String priceRate;//": "0.3", // 跟踪止损回调比例, 仅`TRAILING_STOP_MARKET` 订单返回此字段
        private String updateTime;//": 1579276756075,        // 更新时间
        private String workingType;//": "CONTRACT_PRICE"     // 条件价格触发类型
        private String priceProtect;//": false            // 是否开启条件单触发保护

        public DfTradeOrder extract(DfTradeOrder o) {
            o.setOrderId(orderId + "").setPositionSide(positionSide).setPrice(avgPrice).setQty(executedQty).setSide(side)
            .setTime(time+"");
            return o;
        }
    }

    @Data
    public static class TkPrice {
        private String symbol;//": "BTCUSD_200626",  // 交易对
        private String ps;//": "BTCUSD",             // 标的交易对
        private String price;//": "9647.8",          // 价格
        private Long time;//": 1591257246176       // 时间
    }
}
