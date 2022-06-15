package com.ruoyi.driver.binance;

public class Const {
    public static final String P_Side_LONG = "LONG";
    public static final String P_Side_SHORT = "SHORT";

    public static final String O_TYPE_LIMIT = "LIMIT";
    public static final String O_TYPE_MARKET = "MARKET";
    public static final String O_TYPE_STOP = "STOP";
    public static final String O_TYPE_TAKE_PROFIT = "TAKE_PROFIT";
    public static final String O_TYPE_STOP_MARKET = "STOP_MARKET";
    public static final String O_TYPE_TAKE_PROFIT_MARKET = "TAKE_PROFIT_MARKET";
    public static final String O_TYPE_TRAILING_STOP_MARKET = "TRAILING_STOP_MARKET";

    //自定义止盈止损，当价格超过1.5个点，进行原地止损
    public static final String STOP_MARKET_PAVG = "STOP_MARKET_PAVG";

    public static final String O_SIDE_BUY = "BUY";
    public static final String O_SIDE_SELL = "SELL";

    public static final String Time_In_Force_GTC = "GTC";//", // 成交为止, 一直有效
    public static final String Time_In_Force_IOC = "IOC";//", // 无法立即成交(吃单)的部分就撤销
    public static final String Time_In_Force_FOK = "FOK";//", // 无法全部立即成交就撤销
    public static final String Time_In_Force_GTX = "GTX";//" // 无法成为挂单方就撤销

    public static final String C_TRUE = "true";
    public static final String C_FALSE = "false";

    public static final String C_BIG_TRUE = "TRUE";
    public static final String C_BIG_FALSE = "FALSE";

    public static final String MARGIN_ISOLATED = "ISOLATED";
    public static final String MARGIN_CROSSED = "CROSSED";

    public static final int RESP_CODE_SUCC = 200;
}
