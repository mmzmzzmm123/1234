package com.ruoyi.robot;

import com.ruoyi.common.agent.AliSmsClient;
import com.ruoyi.common.agent.VoiceSmsAgent;
import com.ruoyi.driver.DriverDto;
import com.ruoyi.driver.binance.BinanceAgent;
import com.ruoyi.driver.binance.Const;
import com.ruoyi.udef.service.impl.RobotServiceImpl;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.ruoyi.driver.utils.Konsts.GSON;

@Slf4j
@Component
public class MacdTaRobot {
    @Autowired
    public BinanceAgent binanceAgent;

    @Autowired
    RobotServiceImpl robotService;

    public static Boolean isLogDetail = false;

    //    public static BinanceAgent binanceAgent = new BinanceAgent();
    public static Core ta = new Core();
    public static List<String> phones_1 = Arrays.asList("18502887156");//18080855055
    public static List<String> phones_2 = Arrays.asList("13096371756");
    public static List<String> phones_3 = Arrays.asList("18328419756");

    public static Map<String, Long> ALARM_CACHE = new ConcurrentHashMap<>();

    public static Map<String, Double> TA_CACHE = new ConcurrentHashMap<>();

    public void execute() {
        try {
            log.info("MacdTaRobot execute ....");
            batchCross("BTCUSD_PERP", 3600);
            batchCross("BTCUSD_PERP", 1800);

            batchCross("ETHUSD_PERP", 3600);
            batchCross("ETHUSD_PERP", 1800);

            batchCross("BTCUSD_PERP", 14400);
            batchCross("ETHUSD_PERP", 14400);
        } catch (Exception e) {
            log.warn("MacdTaRobot 币安刘哥异常报警");
            e.printStackTrace();
        }

    }


    public static void sendAlarm(List<String> phones, String code) {
        phones.forEach(m -> {
            try {
                VoiceSmsAgent.send(m, "定制", code);
                AliSmsClient.send(m, code);
            } catch (Exception e) {
                e.printStackTrace();
                log.info("MacdTaRobot 短信发送异常 {} {}", m, code);
            }
        });
    }


    public static Double emaL(Core core, Integer period, double[] close) {
        MInteger outBegIdx = new MInteger();
        MInteger outNbElement = new MInteger();
        double[] ema = new double[close.length];//DIF
        core.ema(0, close.length - 1, close, period, outBegIdx, outNbElement, ema);
        return ema[ema.length - period - 1];
    }

    public static double[] emaData(Core core, Integer period, double[] close) {
        MInteger outBegIdx = new MInteger();
        MInteger outNbElement = new MInteger();
        double[] ema = new double[close.length];//DIF
        core.ema(0, close.length - 1, close, period, outBegIdx, outNbElement, ema);
        return ema;
    }

    public static Double eGet(double[] collection, Integer period, Integer index) {
        return collection[collection.length - period + index + 1];
    }

    public double[] getClose(String symbol, Integer interval) {
        List<DriverDto.KlineItem> klines = binanceAgent.getKline(interval + "", symbol, null, null);
        return klines.stream().mapToDouble(n -> n.getClose()).toArray();
    }

    public static Boolean hasEmaGoldCrossed(KI ki) {
        double[] close = ki.close;

        double[] ema5 = emaData(ta, 5, close);
        double[] ema10 = emaData(ta, 10, close);
        if(isLogDetail){
            log.info("MacdTaRobot ema5 : {}", ema5);
            log.info("MacdTaRobot ema10 : {}", ema10);
        }
        double fastNew = eGet(ema5, 5, -1);
        double fastOld = eGet(ema5, 5, -2);
        double slowNew = eGet(ema10, 10, -1);
        double slowOld = eGet(ema10, 10, -2);
        return fastNew > slowNew;
    }

    public static Boolean emaGoldCross(KI ki) {
        double[] ema5 = emaData(ta, 5, ki.close);
        double[] ema10 = emaData(ta, 10, ki.close);
        if(isLogDetail){
            log.info("MacdTaRobot ema5 : {}", ema5);
            log.info("MacdTaRobot ema10 : {}", ema10);
        }
        double fastNew = eGet(ema5, 5, -1);
        double slowNew = eGet(ema10, 10, -1);


        double fastOld = TA_CACHE.getOrDefault(ki.tk("ema5"), eGet(ema5, 5, -2));
        double slowOld = TA_CACHE.getOrDefault(ki.tk("ema10"), eGet(ema10, 5, -2));
        TA_CACHE.put(ki.tk("ema5"), eGet(ema5, 5, -2));
        TA_CACHE.put(ki.tk("ema10"), eGet(ema10, 5, -2));

        return hasKdjGoldCrossed(ki) && goldCross(fastOld, slowOld, fastNew, slowNew);
    }


    public static Boolean emaDeadCross(KI ki) {
        double[] high = ki.high;
        double[] low = ki.low;
        double[] close = ki.close;

        double[] ema5 = emaData(ta, 5, close);
        double[] ema10 = emaData(ta, 10, close);
        if(isLogDetail){
            log.info("MacdTaRobot ema5 : {}", ema5);
            log.info("MacdTaRobot ema10 : {}", ema10);
        }
        double fastNew = eGet(ema5, 5, -1);
        double slowNew = eGet(ema10, 10, -1);

        double fastOld = TA_CACHE.getOrDefault(ki.tk("ema5"), eGet(ema5, 5, -2));
        double slowOld = TA_CACHE.getOrDefault(ki.tk("ema10"), eGet(ema10, 5, -2));
        TA_CACHE.put(ki.tk("ema5"), eGet(ema5, 5, -2));
        TA_CACHE.put(ki.tk("ema10"), eGet(ema10, 5, -2));

        return !hasKdjGoldCrossed(ki) && deadCross(fastOld, slowOld, fastNew, slowNew);
    }

    public static boolean goldCross(double fastOld, double slowOld, double fastNew, double slowNew) {
        log.info("MacdTaRobot 快线-2 {} 慢线-2 {} 快线-1 {} 慢线-1 {}", fastOld, slowOld, fastNew, slowNew);
        if(fastNew > 0 && fastOld > 0 && slowNew > 0 && slowOld > 0){
            if(fastOld < slowOld && fastNew > slowNew){
                return true;
            }
        }
        return false;
    }

    public static boolean deadCross(double fastOld, double slowOld, double fastNew, double slowNew) {
        log.info("MacdTaRobot 快线-2 {} 慢线-2 {} 快线-1 {} 慢线-1 {}", fastOld, slowOld, fastNew, slowNew);
        if(fastNew > 0 && fastOld > 0 && slowNew > 0 && slowOld > 0){
            if(fastOld > slowOld && fastNew < slowNew){
                return true;
            }
        }
        return false;
    }

    public static boolean cross(double fastOld, double slowOld, double fastNew, double slowNew) {
        log.info("MacdTaRobot 快线-2 {} 慢线-2 {} 快线-1 {} 慢线-1 {}", fastOld, slowOld, fastNew, slowNew);
        if(fastNew > 0 && fastOld > 0 && slowNew > 0 && slowOld > 0){
            if((fastOld > slowOld && fastNew < slowNew) || (fastOld < slowOld && fastNew > slowNew)){
                return true;
            }
        }
        return false;
    }

    public static Boolean hasKdjGoldCrossed(KI ki) {
        double[] high = ki.high;
        double[] low = ki.low;
        double[] close = ki.close;

        List<Double> kData = new ArrayList<>();
        List<Double> dData = new ArrayList<>();
        List<Double> rsvData = new ArrayList<>();

        Integer N = 40;
        Integer K = 2;
        Integer D = 20;

        for (int i = 0; i < close.length; i++) {
            if(i < N) continue;

            double H = 0d;
            double L = 0d;
            for (int j = i - N + 1; j <= i; j++) {
                if(j == i - N + 1){
                    H = high[j];
                    L = low[j];
                } else {
                    H = Math.max(H, high[j]);
                    L = Math.min(L, low[j]);
                }
            }

            rsvData.add((close[i] - L) * 100d / (H - L));

            Double preK = kData.size() == 0 ? 0d : kData.get(kData.size() - 1);
            Double preD = dData.size() == 0 ? 0d : dData.get(dData.size() - 1);

            BigDecimal kbg = new BigDecimal((K - 1d) * preK / K + 1d / K * rsvData.get(rsvData.size() - 1))
                    .setScale(4, BigDecimal.ROUND_HALF_UP);
            kData.add(kbg.doubleValue());

            BigDecimal dbg = new BigDecimal((D - 1d) * preD / D + 1d / D * kData.get(kData.size() - 1))
                    .setScale(4, BigDecimal.ROUND_HALF_UP);
            dData.add(dbg.doubleValue());
        }

        if(isLogDetail){
            log.info("MacdTaRobot outSlowK : {}", kData);
            log.info("MacdTaRobot outSlowD : {}", dData);
        }

        double fastNew = kData.get(kData.size() - 1);
        double fastOld = kData.get(kData.size() - 2);
        double slowNew = dData.get(dData.size() - 1);
        double slowOld = dData.get(dData.size() - 2);
        return fastNew > slowNew;
    }


    public static Boolean kdjGoldCross(KI ki) {
        double[] high = ki.high;
        double[] low = ki.low;
        double[] close = ki.close;

        List<Double> kData = new ArrayList<>();
        List<Double> dData = new ArrayList<>();
        List<Double> rsvData = new ArrayList<>();

        Integer N = 40;
        Integer K = 2;
        Integer D = 20;

        for (int i = 0; i < close.length; i++) {
            if(i < N) continue;

            double H = 0d;
            double L = 0d;
            for (int j = i - N + 1; j <= i; j++) {
                if(j == i - N + 1){
                    H = high[j];
                    L = low[j];
                } else {
                    H = Math.max(H, high[j]);
                    L = Math.min(L, low[j]);
                }
            }

            rsvData.add((close[i] - L) * 100d / (H - L));

            Double preK = kData.size() == 0 ? 0d : kData.get(kData.size() - 1);
            Double preD = dData.size() == 0 ? 0d : dData.get(dData.size() - 1);

            BigDecimal kbg = new BigDecimal((K - 1d) * preK / K + 1d / K * rsvData.get(rsvData.size() - 1))
                    .setScale(4, BigDecimal.ROUND_HALF_UP);
            kData.add(kbg.doubleValue());

            BigDecimal dbg = new BigDecimal((D - 1d) * preD / D + 1d / D * kData.get(kData.size() - 1))
                    .setScale(4, BigDecimal.ROUND_HALF_UP);
            dData.add(dbg.doubleValue());
        }

        if(isLogDetail){
            log.info("MacdTaRobot outSlowK : {}", kData);
            log.info("MacdTaRobot outSlowD : {}", dData);
        }

        double fastNew = kData.get(kData.size() - 1);
        double slowNew = dData.get(dData.size() - 1);

        double fastOld = TA_CACHE.getOrDefault(ki.tk("k"), kData.get(kData.size() - 2));
        double slowOld = TA_CACHE.getOrDefault(ki.tk("d"), dData.get(dData.size() - 2));
        TA_CACHE.put(ki.tk("k"), kData.get(kData.size() - 2));
        TA_CACHE.put(ki.tk("d"), dData.get(dData.size() - 2));

        return hasEmaGoldCrossed(ki) && goldCross(fastOld, slowOld, fastNew, slowNew);
    }


    public static Boolean kdjDeadCross(KI ki) {
        double[] high = ki.high;
        double[] low = ki.low;
        double[] close = ki.close;
        List<Double> kData = new ArrayList<>();
        List<Double> dData = new ArrayList<>();
        List<Double> rsvData = new ArrayList<>();

        Integer N = 40;
        Integer K = 2;
        Integer D = 20;

        for (int i = 0; i < close.length; i++) {
            if(i < N) continue;

            double H = 0d;
            double L = 0d;
            for (int j = i - N + 1; j <= i; j++) {
                if(j == i - N + 1){
                    H = high[j];
                    L = low[j];
                } else {
                    H = Math.max(H, high[j]);
                    L = Math.min(L, low[j]);
                }
            }

            rsvData.add((close[i] - L) * 100d / (H - L));

            Double preK = kData.size() == 0 ? 0d : kData.get(kData.size() - 1);
            Double preD = dData.size() == 0 ? 0d : dData.get(dData.size() - 1);

            BigDecimal kbg = new BigDecimal((K - 1d) * preK / K + 1d / K * rsvData.get(rsvData.size() - 1))
                    .setScale(4, BigDecimal.ROUND_HALF_UP);
            kData.add(kbg.doubleValue());

            BigDecimal dbg = new BigDecimal((D - 1d) * preD / D + 1d / D * kData.get(kData.size() - 1))
                    .setScale(4, BigDecimal.ROUND_HALF_UP);
            dData.add(dbg.doubleValue());
        }

        if(isLogDetail){
            log.info("MacdTaRobot outSlowK : {}", kData);
            log.info("MacdTaRobot outSlowD : {}", dData);
        }

        double fastNew = kData.get(kData.size() - 1);
        double slowNew = dData.get(dData.size() - 1);

        double fastOld = TA_CACHE.getOrDefault(ki.tk("k"), kData.get(kData.size() - 2));
        double slowOld = TA_CACHE.getOrDefault(ki.tk("d"), dData.get(dData.size() - 2));
        TA_CACHE.put(ki.tk("k"), kData.get(kData.size() - 2));
        TA_CACHE.put(ki.tk("d"), dData.get(dData.size() - 2));

        return !hasEmaGoldCrossed(ki) && deadCross(fastOld, slowOld, fastNew, slowNew);
    }

    static class KI {
        double[] close;
        double[] high;
        double[] low;
        String symbol;
        Integer interval;


        public KI extract(List<DriverDto.KlineItem> klines, String symbol, Integer interval) {
            close = klines.stream().mapToDouble(n -> n.getClose()).toArray();
            high = klines.stream().mapToDouble(n -> n.getHigh()).toArray();
            low = klines.stream().mapToDouble(n -> n.getLow()).toArray();
            this.symbol = symbol;
            this.interval = interval;
            return this;
        }

        public String tk(String tag) {
            return symbol + "_" + interval + "_" + tag;
        }
    }


    public void batchCross(String symbol, Integer interval) {
        List<DriverDto.KlineItem> klinesBit = binanceAgent.getKline(interval + "", symbol, null, null);
        KI bigKi = new KI().extract(klinesBit, symbol, interval);
        List<DriverDto.KlineItem> klinesLit = binanceAgent.getKline((interval / 2) + "", symbol, null, null);
        KI litKi = new KI().extract(klinesLit, symbol, (interval / 2));

        Boolean result;
        Boolean result3;

        if(interval.intValue() == 1800){
            result = (emaGoldCross(bigKi) || kdjGoldCross(bigKi)) && (hasEmaGoldCrossed(litKi) && hasKdjGoldCrossed(litKi));
            result3 = (emaDeadCross(bigKi) || kdjDeadCross(bigKi)) && (!hasEmaGoldCrossed(litKi) && !hasEmaGoldCrossed(litKi));
        } else {
            result = (emaGoldCross(bigKi) || kdjGoldCross(bigKi));
            result3 = (emaDeadCross(bigKi) || kdjDeadCross(bigKi));
        }


        if(interval.intValue() == 3600){
            log.info("MacdTaRobot  币对 {} 周期 {} 是否金叉 {}", symbol, interval, result);
            if(assertConfirm(result, symbol, "ema", interval, klinesBit.get(klinesBit.size() - 2).getTimestamp().getTime())){
                robotService.triggerRobot(symbol, interval * 1l, Const.P_Side_LONG);
                sendCodeByResult(phones_3, result, symbol, isBtcSwap(symbol) ? "343434" : "787878");
            }

            log.info("MacdTaRobot  币对 {} 周期 {} 是否死叉 {}", symbol, interval, result3);
            if(assertConfirm(result3, symbol, "kdj", interval, klinesBit.get(klinesBit.size() - 2).getTimestamp().getTime())){
                robotService.triggerRobot(symbol, interval * 1l, Const.P_Side_SHORT);
                sendCodeByResult(phones_3, result3, symbol, isBtcSwap(symbol) ? "434343" : "878787");
            }
        } else if(interval.intValue() == 1800){
            log.info("MacdTaRobot  币对 {} 周期 {} 是否金叉 {}", symbol, interval, result);
            if(assertConfirm(result, symbol, "ema", interval, klinesBit.get(klinesBit.size() - 2).getTimestamp().getTime())){
                robotService.triggerRobot(symbol, interval * 1l, Const.P_Side_LONG);
                sendCodeByResult(phones_1, result, symbol, isBtcSwap(symbol) ? "121212" : "565656");
            }

            log.info("MacdTaRobot  币对 {} 周期 {} 是否死叉 {}", symbol, interval, result3);
            if(assertConfirm(result3, symbol, "kdj", interval, klinesBit.get(klinesBit.size() - 2).getTimestamp().getTime())){
                robotService.triggerRobot(symbol, interval * 1l, Const.P_Side_SHORT);
                sendCodeByResult(phones_1, result3, symbol, isBtcSwap(symbol) ? "212121" : "656565");
            }

        } else if(interval.intValue() == 7200){//2h
            log.info("MacdTaRobot  币对 {} 周期 {} 是否金叉 {}", symbol, interval, result);
            if(assertConfirm(result, symbol, "ema", interval, klinesBit.get(klinesBit.size() - 2).getTimestamp().getTime())){
                robotService.triggerRobot(symbol, interval * 1l, Const.P_Side_LONG);
                sendCodeByResult(phones_2, result, symbol, isBtcSwap(symbol) ? "222222" : "444444");
            }

            log.info("MacdTaRobot  币对 {} 周期 {} 是否死叉 {}", symbol, interval, result3);
            if(assertConfirm(result3, symbol, "kdj", interval, klinesBit.get(klinesBit.size() - 2).getTimestamp().getTime())){
                robotService.triggerRobot(symbol, interval * 1l, Const.P_Side_SHORT);
                sendCodeByResult(phones_2, result3, symbol, isBtcSwap(symbol) ? "111111" : "333333");
            }


        } else if(interval.intValue() == 14400){//4h
            log.info("MacdTaRobot  币对 {} 周期 {} 是否金叉 {}", symbol, interval, result);
            if(assertConfirm(result, symbol, "ema", interval, klinesBit.get(klinesBit.size() - 2).getTimestamp().getTime())){
                robotService.triggerRobot(symbol, interval * 1l, Const.P_Side_LONG);
                sendCodeByResult(phones_2, result, symbol, isBtcSwap(symbol) ? "666666" : "888888");
            }

            log.info("MacdTaRobot  币对 {} 周期 {} 是否死叉 {}", symbol, interval, result3);
            if(assertConfirm(result3, symbol, "kdj", interval, klinesBit.get(klinesBit.size() - 2).getTimestamp().getTime())){
                robotService.triggerRobot(symbol, interval * 1l, Const.P_Side_SHORT);
                sendCodeByResult(phones_2, result3, symbol, isBtcSwap(symbol) ? "555555" : "777777");
            }


        }
    }


    public static Boolean assertConfirm(Boolean condition, String bit, String taTag, Integer interval, Long time) {
        if(condition){
            String key = bit + interval + taTag + time;
            Long hasAlarm = ALARM_CACHE.get(key);
            if(hasAlarm == null){
                log.info("MacdTaRobot {} {} {} {}触发报警", bit, taTag, interval, time);
                ALARM_CACHE.put(key, time);
                return true;
            } else {
                log.info("MacdTaRobot {} {} {} {}重复报警", bit, taTag, interval, time);
            }
        }
        return false;
    }

    public static void sendCodeByResult(List<String> phones, Boolean result, String symbol, String code) {
        if(result){
            if(isBtcSwap(symbol)){
                log.info("MacdTaRobot {} {} {} {} 发送报警", symbol, phones.get(0), code);
                sendAlarm(phones, code);
            } else {
                log.info("MacdTaRobot {} {} {} {} 发送报警", symbol, phones.get(0), code);
                sendAlarm(phones, code);
            }
        }
    }

    public static Boolean isBtcSwap(String symbol) {
        return symbol.equals("BTCUSD_PERP");
    }

    public static void main(String[] args) {
        MacdTaRobot robot = new MacdTaRobot();
        robot.binanceAgent = new BinanceAgent();
        robot.batchCross("ETHUSD_PERP", 1800);
    }


}
