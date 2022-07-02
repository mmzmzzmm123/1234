package com.ruoyi.robot;

import com.ruoyi.common.agent.AliSmsClient;
import com.ruoyi.common.agent.VoiceSmsAgent;
import com.ruoyi.driver.DriverDto;
import com.ruoyi.driver.binance.BinanceAgent;
import com.ruoyi.driver.binance.Const;
import com.ruoyi.udef.Konst;
import com.ruoyi.udef.service.impl.RobotServiceImpl;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.ruoyi.robot.TaLib.*;

@Slf4j
@Component
public class MacdTaRobot {
    @Autowired
    private BinanceAgent binanceAgent;

    @Autowired
    private RobotServiceImpl robotService;

    //    public static BinanceAgent binanceAgent = new BinanceAgent();

    public static Map<String, Long> ALARM_CACHE = new ConcurrentHashMap<>();

    @Data
    @Accessors(chain = true)
    public static class EmaItem {
        Long time;
        String content;//gold or dead;
    }

    public static Map<String, EmaItem> EMA_TEMP_CROSS_CACHE = new ConcurrentHashMap<>();

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


    public double[] getClose(String symbol, Integer interval) {
        List<DriverDto.KlineItem> klines = binanceAgent.getKline(interval + "", symbol, null, null);
        return klines.stream().mapToDouble(n -> n.getClose()).toArray();
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

    private void tempCross(KI big, KI lit, Integer slowLine) {
        String key = big.symbol + "_" + big.interval + "_" + lit.interval;
        EmaItem item = EMA_TEMP_CROSS_CACHE.get(key);
        if(hasEmaGoldCrossed(big, slowLine)){
            if(emaOlDeadCross(lit, slowLine)){
                log.info("瞬时死叉 {}", key);
                EmaItem current = new EmaItem().setTime(System.currentTimeMillis()).setContent("tempDead");
                if(item == null || !item.getContent().equals("tempDead")){
                    EMA_TEMP_CROSS_CACHE.put(key, current);
                }
            } else if(!hasEmaGoldCrossed(lit, slowLine) && item != null){
                log.info("瞬时死叉解除 {}", key);
                EMA_TEMP_CROSS_CACHE.remove(key);
            }
        }

        if(!hasEmaGoldCrossed(big, slowLine)){
            if(emaOlGoldCross(lit, slowLine)){
                log.info("瞬时金叉 {}", key);
                EmaItem current = new EmaItem().setTime(System.currentTimeMillis()).setContent("tempGold");
                if(item == null || !item.getContent().equals("tempGold")){
                    EMA_TEMP_CROSS_CACHE.put(key, current);
                }
            } else if(hasEmaGoldCrossed(lit, slowLine) && item != null){
                log.info("瞬时金叉解除 {}", key);
                EMA_TEMP_CROSS_CACHE.remove(key);
            }
        }
    }

    private boolean isRestore(KI big, KI lit, Integer slowLine) {
        String key = big.symbol + "_" + big.interval + "_" + lit.interval;
        EmaItem item = EMA_TEMP_CROSS_CACHE.get(key);
        if(item != null){
            Long delta = System.currentTimeMillis() - item.getTime();
            if(delta >= 5 * 60 * 1000 && delta <= 6 * 60 * 1000){
                if(hasEmaGoldCrossed(big, slowLine) && hasEmaGoldCrossed(lit, slowLine)){
                    if("tempDead".equals(item.getContent())){
                        return true;
                    }
                }

                if(!hasEmaGoldCrossed(big, slowLine) && !hasEmaGoldCrossed(lit, slowLine)){
                    if("tempGold".equals(item.getContent())){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void batchCrossWithSlowLine(List<DriverDto.KlineItem> klinesBit, List<DriverDto.KlineItem> klinesLit, KI bigKi, KI litKi,
                                       String symbol, Integer interval, Integer slowLine) {
        tempCross(bigKi, litKi, slowLine);

        Boolean eamOlGoldCross = false;
        Boolean eamOlDeadCross = false;

        Long crossLat = null;


        /********************* 策略一 大周期金叉死叉，小周期同相位 ****************************/
        if(emaOlGoldCross(bigKi, slowLine) && hasEmaGoldCrossed(litKi, slowLine)){
            log.info("MacdTaRobot  币对 {} 大周期 {} 大周期_金叉_小周期同相位", symbol, interval);
            eamOlGoldCross = true;
            crossLat = klinesBit.get(klinesBit.size() - 2).getTimestamp().getTime();
        }

        if(emaOlDeadCross(bigKi, slowLine) && !hasEmaGoldCrossed(litKi, slowLine)){
            log.info("MacdTaRobot  币对 {} 大周期 {} 大周期_死叉_小周期同相位", symbol, interval);
            eamOlDeadCross = true;
            crossLat = klinesBit.get(klinesBit.size() - 2).getTimestamp().getTime();
        }

        /********************* 策略二 小周期金叉死叉，大周期同相位 ****************************/
        if(hasEmaGoldCrossed(bigKi, slowLine) && emaOlGoldCross(litKi, slowLine)){
            log.info("MacdTaRobot  币对 {} 大周期 {} 小周期_金叉_大周期同相位", symbol, interval);
            eamOlGoldCross = true;
            crossLat = klinesLit.get(klinesLit.size() - 2).getTimestamp().getTime();
        }

        if(!hasEmaGoldCrossed(bigKi, slowLine) && emaOlDeadCross(litKi, slowLine)){
            log.info("MacdTaRobot  币对 {} 大周期 {} 小周期_死叉_大周期同相位", symbol, interval);
            eamOlDeadCross = true;
            crossLat = klinesLit.get(klinesLit.size() - 2).getTimestamp().getTime();
        }

        /********************* 策略三 大周期已经金叉死叉，小周期恢复同相位 ****************************/
        if(hasEmaGoldCrossed(bigKi, slowLine) && isRestore(bigKi, litKi, slowLine)){
            log.info("MacdTaRobot  币对 {} 大周期 {} 大周期已经_金叉_小周期恢复同相位", symbol, interval);
            eamOlGoldCross = true;
            crossLat = klinesLit.get(klinesLit.size() - 2).getTimestamp().getTime();
        }

        if(!hasEmaGoldCrossed(bigKi, slowLine) && isRestore(bigKi, litKi, slowLine)){
            log.info("MacdTaRobot  币对 {} 大周期 {} 大周期已经_死叉_小周期恢复同相位", symbol, interval);
            eamOlDeadCross = true;
            crossLat = klinesLit.get(klinesLit.size() - 2).getTimestamp().getTime();
        }

        /******************************触发下单*******************************************************/
        if(assertConfirm(eamOlGoldCross, symbol, "ema_gold", interval, crossLat)){
            robotService.triggerRobot(symbol, interval * 1l, Const.P_Side_LONG, Konst.ST_EMA_CROSS_ + slowLine);
        }

        log.info("MacdTaRobot  币对 {} 周期 {} 是否死叉 {}", symbol, interval, eamOlDeadCross);
        if(assertConfirm(eamOlDeadCross, symbol, "ema_dead", interval, crossLat)){
            robotService.triggerRobot(symbol, interval * 1l, Const.P_Side_SHORT, Konst.ST_EMA_CROSS_ + slowLine);
        }
    }


    public void batchCross(String symbol, Integer interval) {
        List<DriverDto.KlineItem> klinesBit = binanceAgent.getKline(interval + "", symbol, null, null);
        KI bigKi = new KI().extract(klinesBit, symbol, interval);
        List<DriverDto.KlineItem> klinesLit = binanceAgent.getKline((interval / 2) + "", symbol, null, null);
        KI litKi = new KI().extract(klinesLit, symbol, (interval / 2));
        batchCrossWithSlowLine(klinesBit, klinesLit, bigKi, litKi, symbol, interval, 10);
        batchCrossWithSlowLine(klinesBit, klinesLit, bigKi, litKi, symbol, interval, 30);
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

    public static void sendAlarmWorkTime(List<String> phones, String code) {
        phones.forEach(m -> {
            try {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
                if(hour > 8 && hour < 23){
                    VoiceSmsAgent.send(m, "定制", code);
                    AliSmsClient.send(m, code);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.info("MacdTaRobot 短信发送异常 {} {}", m, code);
            }
        });
    }

}
