package com.ruoyi.robot;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class TaLib {
    public static Core ta = new Core();
    public static Boolean isLogDetail = false;
    public static Map<String, Double> TA_CACHE = new ConcurrentHashMap<>();

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

    public static Boolean kdjGoldCross(MacdTaRobot.KI ki) {
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

    public static Boolean kdjDeadCross(MacdTaRobot.KI ki) {
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

    public static Boolean hasEmaGoldCrossed(MacdTaRobot.KI ki) {
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

    public static Boolean emaGoldCross(MacdTaRobot.KI ki) {
        double[] ema5 = emaData(ta, 5, ki.close);
        double[] ema10 = emaData(ta, 10, ki.close);
        if(isLogDetail){
            log.info("MacdTaRobot ema5 : {}", ema5);
            log.info("MacdTaRobot ema10 : {}", ema10);
        }
        double fastNew = eGet(ema5, 5, -1);
        double slowNew = eGet(ema10, 10, -1);


        double fastOld = TA_CACHE.getOrDefault(ki.tk("ema5"), eGet(ema5, 5, -2));
        double slowOld = TA_CACHE.getOrDefault(ki.tk("ema10"), eGet(ema10, 10, -2));
        TA_CACHE.put(ki.tk("ema5"), eGet(ema5, 5, -2));
        TA_CACHE.put(ki.tk("ema10"), eGet(ema10, 10, -2));

        return hasKdjGoldCrossed(ki) && goldCross(fastOld, slowOld, fastNew, slowNew);
    }

    public static Boolean emaOlGoldCross(MacdTaRobot.KI ki) {
        double[] ema5 = emaData(ta, 5, ki.close);
        double[] ema10 = emaData(ta, 10, ki.close);
        if(isLogDetail){
            log.info("MacdTaRobot ema5 : {}", ema5);
            log.info("MacdTaRobot ema10 : {}", ema10);
        }
        double fastNew = eGet(ema5, 5, -1);
        double slowNew = eGet(ema10, 10, -1);


        double fastOld = TA_CACHE.getOrDefault(ki.tk("ema5"), eGet(ema5, 5, -2));
        double slowOld = TA_CACHE.getOrDefault(ki.tk("ema10"), eGet(ema10, 10, -2));
        TA_CACHE.put(ki.tk("ema5"), eGet(ema5, 5, -2));
        TA_CACHE.put(ki.tk("ema10"), eGet(ema10, 10, -2));

        Boolean result = goldCross(fastOld, slowOld, fastNew, slowNew);
        return result;
    }


    public static Boolean emaDeadCross(MacdTaRobot.KI ki) {
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
        double slowOld = TA_CACHE.getOrDefault(ki.tk("ema10"), eGet(ema10, 10, -2));
        TA_CACHE.put(ki.tk("ema5"), eGet(ema5, 5, -2));
        TA_CACHE.put(ki.tk("ema10"), eGet(ema10, 10, -2));

        return !hasKdjGoldCrossed(ki) && deadCross(fastOld, slowOld, fastNew, slowNew);
    }

    public static Boolean emaOlDeadCross(MacdTaRobot.KI ki) {
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
        double slowOld = TA_CACHE.getOrDefault(ki.tk("ema10"), eGet(ema10, 10, -2));
        TA_CACHE.put(ki.tk("ema5"), eGet(ema5, 5, -2));
        TA_CACHE.put(ki.tk("ema10"), eGet(ema10, 10, -2));

        Boolean result = deadCross(fastOld, slowOld, fastNew, slowNew);
        return result;
    }

    public static Boolean hasKdjGoldCrossed(MacdTaRobot.KI ki) {
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

}
