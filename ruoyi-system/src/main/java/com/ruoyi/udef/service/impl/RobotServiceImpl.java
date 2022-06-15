package com.ruoyi.udef.service.impl;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.driver.DriverDto;
import com.ruoyi.driver.binance.BinanceAgent;
import com.ruoyi.driver.binance.Const;
import com.ruoyi.driver.okex.OkAgent;
import com.ruoyi.udef.Konst;
import com.ruoyi.udef.domain.*;
import com.ruoyi.udef.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ruoyi.driver.binance.Const.RESP_CODE_SUCC;
import static com.ruoyi.driver.binance.Const.STOP_MARKET_PAVG;
import static com.ruoyi.udef.Konst.*;

@Slf4j
@Service
public class RobotServiceImpl {
    @Autowired
    private BinanceAgent agent;

    @Autowired
    private DfApiRobotMapper dfApiRobotMapper;

    @Autowired
    private DfEscapeScheduleMapper scheduleMapper;

    @Autowired
    private DfApiKeyMapper dfApiKeyMapper;

    @Autowired
    private DfRobotOrderMapper robotOrderMapper;

    @Autowired
    private DfTradeOrderMapper tradeOrderMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private OkAgent.Credential getDefault() {
        OkAgent.Credential credential = new OkAgent.Credential();
        credential.setApiKey("mh4iSin4EMXszeUf1252BykVcCwRGcECN5Sei5KGzVlzQ1H6esoQL4bQyXvaLkuc");
        credential.setSecretKey("7xvHT6lrZF9xARl0WGplGC3gJf7uD3DKJjGea546TbrDZtXogsRieSHWJ5rJThe4");
        return credential;
    }

    /**
     * 0. 初始化机器人时
     * 1. 设置持仓模式
     * 2. 设置杠杆
     * 3. 设置持仓模式
     */
    public void initRobot() {
        List<DfApiRobot> robots = dfApiRobotMapper.selectDfApiRobotList(new DfApiRobot().setStatus(R_STATUS_INIT));
        robots.forEach(n -> {
            try {
                OkAgent.Credential credential = getCredential(n.getApiId());
                checkDuel(credential);
                checkMargin(credential, n);
                dfApiRobotMapper.updateDfApiRobot(n.setStatus(R_STATUS_STOPPED));
            } catch (Exception e) {
                log.warn("Api机器人账户初始化异常 {}", JSON.toJSONString(n));
                e.printStackTrace();
            }
        });
    }

    public void initRobotById(Long id) {
        DfApiRobot robot = dfApiRobotMapper.selectDfApiRobotById(id);
        if(robot == null) throw new CustomException("机器人不存在！！");
        if(!robot.getStatus().equals(R_STATUS_INIT)) throw new CustomException("机器人已初始化！！");
        OkAgent.Credential credential = getCredential(robot.getApiId());
        checkDuel(credential);
        checkMargin(credential, robot);
        dfApiRobotMapper.updateDfApiRobot(robot.setStatus(R_STATUS_STOPPED));
    }

    private void checkDuel(OkAgent.Credential credential) {
        DriverDto.DualModel dualModel = agent.getDual(credential);
        if(Const.C_FALSE.equals(dualModel.getDualSidePosition())){
            DriverDto.SampleResponse response = agent.postDual(credential, new DriverDto.DualModelReq()
                    .setDualSidePosition(Const.C_TRUE));
            if(RESP_CODE_SUCC != response.getCode().intValue()){
                log.warn("设置持仓返回结果未：{}", JSON.toJSONString(response));
                throw new CustomException("无法成功设置持仓模式未双向持仓！！");
            }
        }
    }

    private void checkMargin(OkAgent.Credential credential, DfApiRobot robot) {
        if(StringUtils.isEmpty(robot.getSymbol())) throw new CustomException("合约名称不合法！！");
        List<DriverDto.PositionRisk> risks = agent.positionRisk(credential, getPair(robot.getSymbol()));
        Optional<DriverDto.PositionRisk> lop = risks.stream().filter(n -> n.getSymbol().equals(robot.getSymbol())).findFirst();
        if(lop.isPresent()){
            if(!Const.MARGIN_ISOLATED.equalsIgnoreCase(lop.get().getMarginType())){
                DriverDto.SampleResponse response = agent.switchMargin(credential, new DriverDto.MarginReq()
                        .setSymbol(robot.getSymbol()).setMarginType(Const.MARGIN_ISOLATED));

                if(RESP_CODE_SUCC != response.getCode().intValue()){
                    log.warn("设置逐全模式返回结果异常：{}", JSON.toJSONString(response));
                    throw new CustomException("无法成功设置仓位逐全模式！！");
                }
            }
        } else {
            log.warn("多仓持仓不存在，无法设置");
            throw new CustomException("多仓设置逐全模式异常！！");
        }

        if(robot.getLeverage() != null && robot.getLeverage().doubleValue() > 0){
            if(lop.isPresent()){
                if(Integer.parseInt(lop.get().getLeverage()) != robot.getLeverage().intValue()){
                    DriverDto.LeverageResp resp = agent.changLeverage(credential, new DriverDto.LeverageReq()
                            .setLeverage(robot.getLeverage()).setSymbol(robot.getSymbol()));
                    if(resp.getLeverage() > 0){
                        log.info("杠杆设置成功 {}", resp.getLeverage());
                    } else {
                        throw new CustomException("杠杆设置不成功！！！");
                    }
                }
            }
        } else {
            throw new CustomException("账户杠杆初始化参数缺失!!");
        }


    }

    private String getPair(String symbol) {
        return symbol.split("_")[0];
    }

    private OkAgent.Credential getCredential(Long apiId) {
        DfApiKey api = dfApiKeyMapper.selectDfApiKeyById(apiId);
        if(api != null){
            if(StringUtils.isEmpty(api.getApiKey()) || StringUtils.isEmpty(api.getApiSecret()))
                throw new CustomException("api key或者秘钥未空");
            return new OkAgent.Credential().setApiKey(api.getApiKey()).setSecretKey(api.getApiSecret()).setPassphrase(api.getPassphrase());
        } else {
            throw new CustomException("api账户不存在！！！");
        }
    }

    /***
     * 1. 检查仓位
     *    1> 反向仓位情况
     *    2> 正向仓位如果有，就跳过，如果没有集开仓
     * 2. 止盈止损
     * 3. 跟踪止损
     */
    public void placeIfNotPosition(OkAgent.Credential credential, DfApiRobot robot, String direction) {
        List<DriverDto.PositionRisk> risks = agent.positionRisk(credential, getPair(robot.getSymbol())).stream()
                .filter(n -> n.getSymbol().equals(robot.getSymbol()) && Integer.parseInt(n.getPositionAmt()) != 0)
                .collect(Collectors.toList());

        Optional<DriverDto.PositionRisk> sop = getPos(risks, direction, robot.getSymbol());//正向持仓
        Optional<DriverDto.PositionRisk> rop = getPos(risks, revertDirection(direction), robot.getSymbol());//反向持仓
        if(sop.isPresent()){//同相位有持仓,不管
            log.warn("当前有同向持仓，跳过本次开仓！ {} ", JSON.toJSONString(sop));
        } else {//同相位没有持仓
            cleanBeforeOpen(credential, robot, direction, rop);

            DfRobotOrder dfRobotOrder = new DfRobotOrder();
            Optional<DriverDto.UserBalance> balance = getBalance(credential, robot.getSymbol());
            if(!balance.isPresent()) throw new CustomException("账户不存在！");
            if(Double.parseDouble(balance.get().getBalance()) == 0d) throw new CustomException("账户余额不足！");
            dfRobotOrder.setPredictBalance(balance.get().getBalance()).setRobotId(robot.getId()).setOpenSide(direction);

            //4. 市价开单
            log.info("》》》》》》》》》》市价正向开单");
            DriverDto.OrderReq req = new DriverDto.OrderReq().setSide(sideOpen(direction)).setSymbol(robot.getSymbol())
                    .setQuantity(robot.getQuantity() * 1d).setPositionSide(direction).setType(Const.O_TYPE_MARKET);
            DriverDto.OrderResp reps = agent.postOrder(credential, req);
            log.info(">>>>>>>>>>  市价正向开单 {}", JSON.toJSONString(reps));

            if(reps != null && StringUtils.isNotEmpty(reps.getOrderId())){
                dfRobotOrder.setBalance(dfRobotOrder.getPredictBalance()).setMaxPosition(robot.getQuantity())
                        .setPosition(robot.getQuantity()).setStatus(P_STATUS_RUNNING);
                robotOrderMapper.insertDfRobotOrder(dfRobotOrder);

                checkSingleFromConfig(robot, credential);
            }
        }

    }

    private void freshRobotOrder(OkAgent.Credential credential, DfApiRobot robot) {
        try {
            robotOrderMapper.selectDfRobotOrderList(new DfRobotOrder().setRobotId(robot.getId()).setStatus(P_STATUS_RUNNING)).forEach(order -> {
                Optional<DriverDto.UserBalance> balance = getBalance(credential, robot.getSymbol());
                if(balance.isPresent()){
                    order.setBalance(balance.get().getBalance());
                }

                List<DriverDto.PositionRisk> risks = agent.positionRisk(credential, getPair(robot.getSymbol())).stream()
                        .filter(n -> n.getSymbol().equals(robot.getSymbol()) && Integer.parseInt(n.getPositionAmt()) != 0
                                && n.getPositionSide().equals(order.getOpenSide()))
                        .collect(Collectors.toList());
                if(risks.isEmpty()){
                    order.setPosition(0);
                    order.setStatus(P_STATUS_CLOSED);
                } else {
                    order.setPosition(Math.abs(Integer.parseInt(risks.get(0).getPositionAmt())));
                }

                robotOrderMapper.updateDfRobotOrder(order);

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String genTriggerOrderId(Long robotId, Long robotOrderId, Long scheduleId, Double price) {

        //domain 8 robotId 5, scheduleId 5, robotOrderId 7 ,price 11
        String orderId = DOMAIN + padding(5, robotId) + padding(5, scheduleId) + padding(7, robotOrderId)
                + padding(11, price);
        if(!redisTemplate.opsForSet().isMember(DOMAIN, orderId)){
            redisTemplate.opsForSet().add(DOMAIN, orderId);
            return orderId;
        } else return null;
    }


    private Optional<DriverDto.UserBalance> getBalance(OkAgent.Credential credential, String symbol) {
        List<DriverDto.UserBalance> balances = agent.getBalance(credential);
        return balances.stream().filter(n -> n.getAsset().equals(symbol.substring(0, 3))).findFirst();
    }

    private void cleanBeforeOpen(OkAgent.Credential credential, DfApiRobot robot, String direction,
                                 Optional<DriverDto.PositionRisk> rop) {
        //清理同向的挂单
        cleanPlayOrder(credential, robot, direction);

        if(rop.isPresent() && Long.parseLong(rop.get().getPositionAmt()) != 0){
            //市价平掉反向持仓
            DriverDto.OrderReq req = new DriverDto.OrderReq().setSide(sideClose(revertDirection(direction)))
                    .setSymbol(robot.getSymbol()).setRecvWindow(5000l)
                    .setPositionSide(revertDirection(direction)).setType(Const.O_TYPE_MARKET)
                    .setQuantity(Math.abs(Double.parseDouble(rop.get().getPositionAmt())));
            DriverDto.OrderResp reps = agent.postOrder(credential, req);
            if(reps != null && reps.getOrderId() != null && Long.parseLong(reps.getOrderId()) > 0){
                log.info("市价平掉反向仓位成功 {}", JSON.toJSONString(req));
                //3.清理反向挂单
                cleanPlayOrder(credential, robot, revertDirection(direction));
            } else {
                log.warn("市价平掉反向仓位不成功 {}", JSON.toJSONString(rop));
                throw new CustomException("市价平掉反向仓位不成功");
            }
        } else {
            //3.清理反向挂单
            cleanPlayOrder(credential, robot, revertDirection(direction));
        }
    }

    private Integer getScale(String symbol) {
        return symbol.contains("BTC") ? 1 : 2;
    }

    private String sideOpen(String direct) {
        if(Const.P_Side_LONG.equals(direct)){
            return Const.O_SIDE_BUY;
        } else {
            return Const.O_SIDE_SELL;
        }
    }

    private String sideClose(String direct) {
        if(Const.P_Side_LONG.equals(direct)){
            return Const.O_SIDE_SELL;
        } else {
            return Const.O_SIDE_BUY;
        }
    }

    private Optional<DriverDto.PositionRisk> getPos(List<DriverDto.PositionRisk> risks, String direction, String symbol) {
        return risks.stream().filter(n -> n.getSymbol().equals(symbol) && n.getPositionSide().equals(direction))
                .collect(Collectors.toList()).stream().findFirst();
    }

    private void cleanPlayOrder(OkAgent.Credential credential, DfApiRobot robot, String direction) {
        List<DriverDto.Order> flyOrders = agent.getFlyOrders(credential, robot.getSymbol());
        flyOrders.forEach(n -> {
            if(n.getPositionSide().equals(direction)){
                DriverDto.Order order = agent.cancelOrder(credential, robot.getSymbol(), n.getOrderId() + "");
                if(order.getOrderId() != null && order.getOrderId() > 0){
                    log.info("撤销订单成功:{}", order.getOrderId());
                } else {
                    log.warn("撤销订单失败:{}", JSON.toJSONString(n));
                    throw new CustomException("撤销订单失败！");
                }
            }
        });
    }

    private void cleanPlaceOrderWithEmptyPosition(OkAgent.Credential credential, DfApiRobot robot) {
        log.info("没有有效持仓，清理所有挂单！");
        List<DriverDto.Order> flyOrders = agent.getFlyOrders(credential, robot.getSymbol());
        flyOrders.forEach(n -> {
            DriverDto.Order order = agent.cancelOrder(credential, robot.getSymbol(), n.getOrderId() + "");
            if(order.getOrderId() != null && order.getOrderId() > 0){
                log.info("撤销订单成功:{}", order.getOrderId());
            } else {
                log.warn("撤销订单失败:{}", JSON.toJSONString(n));
                throw new CustomException("撤销订单失败！");
            }
        });
    }

    private String revertDirection(String direction) {
        if(Const.P_Side_LONG.equals(direction)) return Const.P_Side_SHORT;
        return Const.P_Side_LONG;
    }


    public static void main(String[] args) {
        RobotServiceImpl robotService = new RobotServiceImpl();
    }

    public void triggerRobot(String symbol, Long interval, String direction) {
        try {
            List<DfApiRobot> robots = dfApiRobotMapper.selectDfApiRobotList(new DfApiRobot().setStatus(R_STATUS_RUNNING)
                    .setSymbol(symbol).setBigInterval(interval));
            robots.forEach(n -> {
                try {
                    log.info("触发机器人 {}", JSON.toJSONString(n));
                    OkAgent.Credential credential = getCredential(n.getApiId());
                    placeIfNotPosition(credential, n, direction);
                } catch (Exception e) {
                    log.warn("Api机器人账户初始化异常 {}", JSON.toJSONString(n));
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkSingleFromConfig(DfApiRobot robot, OkAgent.Credential credential) {
        try {
            List<DriverDto.PositionRisk> risks = agent.positionRisk(credential, getPair(robot.getSymbol())).stream()
                    .filter(n -> n.getSymbol().equals(robot.getSymbol()) && Integer.parseInt(n.getPositionAmt()) != 0)
                    .collect(Collectors.toList());

            List<DfEscapeSchedule> schedules = scheduleMapper.selectDfEscapeScheduleList(new DfEscapeSchedule().setRobotId(robot.getId()));
            List<DfEscapeSchedule> s1 = schedules.stream().filter(n -> n.getPriority().intValue() == 1).collect(Collectors.toList());
            List<DfEscapeSchedule> s2 = schedules.stream().filter(n -> n.getPriority().intValue() == 2).collect(Collectors.toList());

            if(risks.isEmpty()){
                cleanPlaceOrderWithEmptyPosition(credential, robot);
            } else if(risks.size() > 1){
                log.warn("存在多空双向持仓 {}", JSON.toJSONString(risks));
                throw new CustomException("存在多空双向持仓！！");
            } else {
                DriverDto.PositionRisk position = risks.get(0);
                Double avg = Double.parseDouble(position.getEntryPrice());
                String direction = position.getPositionSide();
                List<DriverDto.Order> flyOrders = agent.getFlyOrders(credential, robot.getSymbol());

                Optional<DfRobotOrder> robotOrder = robotOrderMapper.selectDfRobotOrderList(new DfRobotOrder()
                        .setRobotId(robot.getId()).setStatus(P_STATUS_RUNNING).setOpenSide(direction)).stream().findFirst();
                if(!robotOrder.isPresent()){
                    log.warn("当前无运行的机器人持仓！！！ {} 持仓 {}", robot.getId(), JSON.toJSONString(risks));
                    throw new CustomException("当前无运行的机器人持仓");
                }

                s1.forEach(s -> {
                    if(StringUtils.isNotEmpty(s.getEscapeType()) && s.getPriceRate() > 0 && s.getQuantityRate() > 0){
                        Double stopPrice;
                        Double quality = Math.max(Math.round(robot.getQuantity() * s.getQuantityRate()) * 1d, 1d);
                        switch (s.getEscapeType()) {
                            case Const.O_TYPE_STOP_MARKET:
                                stopPrice = new BigDecimal(avg * (Const.P_Side_LONG.equals(direction) ? 1 - s.getPriceRate() : 1 + s.getPriceRate()))
                                        .setScale(getScale(robot.getSymbol()), BigDecimal.ROUND_HALF_DOWN).doubleValue();
                                break;
                            case Const.O_TYPE_TAKE_PROFIT_MARKET:
                                stopPrice = new BigDecimal(avg * (Const.P_Side_LONG.equals(direction) ? 1 + s.getPriceRate() : 1 - s.getPriceRate()))
                                        .setScale(getScale(robot.getSymbol()), BigDecimal.ROUND_HALF_DOWN).doubleValue();
                                break;
                            default:
                                throw new CustomException("暂不支持的止盈止损类型");
                        }

                        if(!isMatchWithCon(flyOrders, robot.getSymbol(), direction, s.getEscapeType(), stopPrice)){
                            String cltOrderId = genTriggerOrderId(robot.getId(), robotOrder.get().getId(), s.getId(), stopPrice);
                            if(!StringUtils.isEmpty(cltOrderId)){
                                log.info("》》》》》》》》》》止盈止损挂单{} {} {} {} {}", robot.getRobotName(), robot.getSymbol(), direction, stopPrice);
                                DriverDto.OrderReq req = new DriverDto.OrderReq().setSide(sideClose(direction)).setSymbol(robot.getSymbol())
                                        .setQuantity(quality).setPositionSide(direction).setType(s.getEscapeType()).setStopPrice(
                                                stopPrice + "").setNewClientOrderId(cltOrderId);
                                DriverDto.OrderResp resps = agent.postOrder(credential, req);
                                log.info(">>>>>>>>>>  止盈止损挂单挂单返回 {}", JSON.toJSONString(resps));
                            }
                        }

                    }
                });

                s2.forEach(s -> {
                    if(StringUtils.isNotEmpty(s.getEscapeType()) && s.getPriceRate() > 0 && s.getQuantityRate() > 0
                            && STOP_MARKET_PAVG.equals(s.getEscapeType())){
                        Double stopPrice = avg;

                        if(!isMatchWithCon(flyOrders, robot.getSymbol(), direction, Const.O_TYPE_STOP_MARKET, stopPrice)){
                            Double quality = Math.max(Math.round(robot.getQuantity() * s.getQuantityRate()) * 1d, 1d);
                            Double monitorPrice = new BigDecimal(avg * (Const.P_Side_LONG.equals(direction) ? 1 + s.getPriceRate() : 1 - s.getPriceRate()))
                                    .setScale(getScale(robot.getSymbol()), BigDecimal.ROUND_HALF_DOWN).doubleValue();
                            Optional<DriverDto.TkPrice> price = agent.getTkPrice(robot.getSymbol()).stream().findFirst();

                            if((direction.equals(Const.P_Side_SHORT) && price.isPresent() && Double.parseDouble(price.get().getPrice()) < monitorPrice) ||
                                    (direction.equals(Const.P_Side_LONG) && price.isPresent() && Double.parseDouble(price.get().getPrice()) > monitorPrice)){
                                String cltOrderId = genTriggerOrderId(robot.getId(), robotOrder.get().getId(), s.getId(), stopPrice);
                                if(!StringUtils.isEmpty(cltOrderId)){
                                    log.info("》》》》》》》》》》原地止损挂单 {}", robot.getId());
                                    DriverDto.OrderReq stopReq = new DriverDto.OrderReq().setSide(sideClose(direction))
                                            .setSymbol(robot.getSymbol()).setQuantity(quality)
                                            .setPositionSide(direction).setType(Const.O_TYPE_STOP_MARKET).setStopPrice(
                                                    stopPrice + "").setNewClientOrderId(cltOrderId);
                                    DriverDto.OrderResp stopResp = agent.postOrder(credential, stopReq);
                                    log.info(">>>>>>>>>>  原地止损挂单返回 {}", JSON.toJSONString(stopResp));
                                }
                            }
                        }

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void checkPositionTriggerFromConfig() {
        for (DfApiRobot robot : dfApiRobotMapper.selectDfApiRobotList(new DfApiRobot().setStatus(R_STATUS_RUNNING))) {
            OkAgent.Credential credential = getCredential(robot.getApiId());
            checkSingleFromConfig(robot, credential);
            freshRobotOrder(credential, robot);
        }
    }

    private Boolean isMatchWithCon(List<DriverDto.Order> flyOrders, String symbol, String direction, String type, Double price) {
        return flyOrders.stream().anyMatch(n -> n.getPositionSide().equals(direction) && n.getSide().equals(sideClose(direction))
                && n.getSymbol().equals(symbol) && n.getType().equals(type)
                && n.getStopPrice().equals(price + ""));
    }

    public void syncOrder() {
        for (DfApiRobot robot : dfApiRobotMapper.selectDfApiRobotList(new DfApiRobot().setStatus(R_STATUS_RUNNING))) {
            OkAgent.Credential credential = getCredential(robot.getApiId());
            syncOrderByRobot(robot, credential);
        }
    }

    private void syncOrderByRobot(DfApiRobot robot, OkAgent.Credential credential) {
        List<DfTradeOrder> orders = tradeOrderMapper.selectDfTradeOrderLatest(new DfTradeOrder().setRobotId(robot.getId()));
        List<DriverDto.Order> ops;
        if(orders.isEmpty()){
            ops = agent.getLast7Orders(credential, robot.getSymbol(), null);
        } else {
            ops = agent.getLast7Orders(credential, robot.getSymbol(), orders.get(0).getOrderId());
        }
        ops.stream().filter(n -> n.getStatus().equals("FILLED")).forEach(n -> {
            try {
                tradeOrderMapper.insertDfTradeOrder(n.extract(new DfTradeOrder().setRobotId(robot.getId()).setSymbol(robot.getSymbol())));
            } catch (Exception e) {
                log.warn("order may be exists");
            }
        });
    }
}
