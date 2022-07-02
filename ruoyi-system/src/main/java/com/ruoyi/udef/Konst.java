package com.ruoyi.udef;

import com.ruoyi.driver.binance.Const;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Konst {
    public static final String R_STATUS_INIT = "init";
    public static final String R_STATUS_RUNNING = "running";
    public static final String R_STATUS_STOPPED = "stopped";

    public static final String P_STATUS_RUNNING = "running";
    public static final String P_STATUS_TRIGGERED = "triggered";
    public static final String P_STATUS_CLOSED = "closed";

    public static final String S_PRIORITY_SOLID = "solid";
    public static final String S_PRIORITY_WATCHER = "watcher";

    public static final String DOMAIN = "divine14";

    public static final String A_STATUS_STOP = "stop";
    public static final String A_STATUS_DAILY = "daily";
    public static final String A_STATUS_WORK_TIME = "work_time";

    public static final String ST_EMA_CROSS_ = "ema_cross_";

    public static final String STOP_LOSS = "stop_loss";
    public static final String STOP_REVERT = "stop_revert";

    //自定义止盈止损，当价格超过1.5个点，进行原地止损
    public static final String STOP_MARKET_T_BASE = "STOP_MARKET_T";

    public static String padding(Integer scale, Long id) {
        StringBuilder idStr = new StringBuilder(id + "");
        if(idStr.length() > scale){
            return idStr.substring(idStr.length() - scale);
        } else {
            while (idStr.length() < scale) {
                idStr.append("0");
            }
        }
        return idStr.toString();
    }

    public static Double getTRate(String tRate) {
        return Float.parseFloat(tRate.substring(STOP_MARKET_T_BASE.length())) * 0.001;
    }

    public static String padding(Integer scale, Double stop) {
        StringBuilder idStr = new StringBuilder(stop + "");
        if(idStr.length() > scale){
            return idStr.substring(idStr.length() - scale);
        } else {
            while (idStr.length() < scale) {
                idStr.append("0");
            }
        }
        return idStr.toString();
    }

    public static boolean isLong(String direction) {
        return Const.P_Side_LONG.equals(direction);
    }

    public static void main(String[] args) {
        System.out.println(padding(5, 1l));
        System.out.println(padding(5, 10000l));
        System.out.println(padding(5, 100000l));

        Map<String, String> tt = new ConcurrentHashMap<>();
        System.out.println(tt.putIfAbsent("1", "1"));
    }

    public static Boolean isBtcSwap(String symbol) {
        return symbol.equals("BTCUSD_PERP");
    }
}
