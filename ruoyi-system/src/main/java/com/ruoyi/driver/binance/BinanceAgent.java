package com.ruoyi.driver.binance;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.driver.DriverDto;
import com.ruoyi.driver.okex.OkAgent;
import com.ruoyi.driver.utils.APIConstants;
import com.ruoyi.driver.utils.ContentTypeEnum;
import com.ruoyi.driver.utils.Utils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.*;
import java.util.stream.Collectors;

import static com.ruoyi.driver.binance.Const.RESP_CODE_SUCC;
import static com.ruoyi.driver.utils.Konsts.Binance.*;
import static com.ruoyi.driver.utils.Konsts.*;

/**
 * Created by Administrator on 2018/8/2.
 */
@Slf4j
@Service
public class BinanceAgent {
    static Logger LOGGER = LoggerFactory.getLogger(BinanceAgent.class);
    private static CloseableHttpClient HTTP_CLIENT = HttpClients.custom().build();

    @Value("${model:debug}")
    public String model = "debug";

    public Map<String, Long> INSTRUCT_TIME_MAP = new HashMap<>();
    public Map<String, List<DriverDto.KlineItem>> INSTRUCT_DATA_MAP = new HashMap<>();

    //最近价格
    public List<DriverDto.TkPrice> getTkPrice(String symbol) {
        LOGGER.info("[Method] getTkPrice");
        return call(TKPRICE + "?symbol=" + symbol, null, new TypeReference<List<DriverDto.TkPrice>>() {
        });
    }

    //查看持仓模式
    public DriverDto.DualModel getDual(OkAgent.Credential credential) {
        LOGGER.info("[Method] getDual");
        DriverDto.DualModel dual = call(DUAL, credential, new TypeReference<DriverDto.DualModel>() {
        });
        return dual;

    }

    //更改持仓模式
    public DriverDto.SampleResponse postDual(OkAgent.Credential credential, DriverDto.DualModelReq req) {
        LOGGER.info("[Method] postDual ");
        return post(DUAL, credential, new TypeReference<DriverDto.SampleResponse>() {
        }, req);
    }


    //账户余额
    public List<DriverDto.UserBalance> getBalance(OkAgent.Credential credential) {
        LOGGER.info("[Method] getBalance ");
        return call(GET_BALANCE, credential, new TypeReference<List<DriverDto.UserBalance>>() {
        });
    }

    //账户信息
    public DriverDto.Account getAccount(OkAgent.Credential credential) {
        LOGGER.info("[Method] getAccount ");
        return call(GET_ACCOUNT, credential, new TypeReference<DriverDto.Account>() {
        });
    }

    //调整逐全模式
    public DriverDto.SampleResponse switchMargin(OkAgent.Credential credential, DriverDto.MarginReq req) {
        LOGGER.info("[Method] switchMargin ");
        return post(MARGIN, credential, new TypeReference<DriverDto.SampleResponse>() {
        }, req);
    }

    //调整杠杆
    public DriverDto.LeverageResp changLeverage(OkAgent.Credential credential, DriverDto.LeverageReq req) {
        LOGGER.info("[Method] changLeverage ");
        return post(LEVERAGE, credential, new TypeReference<DriverDto.LeverageResp>() {
        }, req);
    }

    //下单
    public DriverDto.OrderResp postOrder(OkAgent.Credential credential, DriverDto.OrderReq req) {
        LOGGER.info("[Method] postOrder {}", JSON.toJSONString(req));
        DriverDto.OrderResp op = post(POST_ORDER, credential, new TypeReference<DriverDto.OrderResp>() {
        }, req);
        return op;
    }

    //查询所有订单(包括历史订单) 订单生成时间 + 7天 < 当前时间
    public List<DriverDto.Order> getLast7Orders(OkAgent.Credential credential, String symbol, String orderId) {
        LOGGER.info("[Method] getLast7Orders {}", symbol);
        return call(ALL_ORDERS + "?symbol=" + symbol + queryString(orderId), credential, new TypeReference<List<DriverDto.Order>>() {
        });
    }

    private String queryString(String orderId) {
        if(StringUtils.isEmpty(orderId)) return "";
        return "&orderId=" + orderId;
    }

    //查看当前全部挂单
    public List<DriverDto.Order> getFlyOrders(OkAgent.Credential credential, String symbol) {
        LOGGER.info("[Method] getFlyOrders {}", symbol);
        return call(OPEN_ORDERS + "?symbol=" + symbol, credential, new TypeReference<List<DriverDto.Order>>() {
        });
    }

    //撤销订单
    public DriverDto.Order cancelOrder(OkAgent.Credential credential, String symbol, String orderId) {
        LOGGER.info("[Method] cancelOrder {} {}", symbol, orderId);
        return del(ORDER + "?symbol=" + symbol + "&orderId=" + orderId, credential, new TypeReference<DriverDto.Order>() {
        });
    }

    //查询持仓风险
    public List<DriverDto.PositionRisk> positionRisk(OkAgent.Credential credential, String pair) {
        LOGGER.info("[Method] positionRisk {}", pair);
        return call(POSITION_RISK + "?pair=" + pair, credential, new TypeReference<List<DriverDto.PositionRisk>>() {
        });
    }


    public List<DriverDto.KlineItem> getKline(String granularity, String instrumentId, String defaultStartDate, String endUtc) {
        LOGGER.info("[Method] getKline {} {}", granularity, instrumentId);
        String key = granularity + "_" + instrumentId;
        if(INSTRUCT_TIME_MAP.containsKey(key) && System.currentTimeMillis() - INSTRUCT_TIME_MAP.get(key) < 5000 && INSTRUCT_DATA_MAP.containsKey(key)){
            return INSTRUCT_DATA_MAP.get(key);
        }


        String binSize = "5m";
        switch (granularity) {
            case "3600":
                binSize = "1h";
                break;
            case "7200":
                binSize = "2h";
                break;
            case "14400":
                binSize = "4h";
                break;
            case "86400":
                binSize = "1d";
                break;
            case "1800":
                binSize = "30m";
                break;
            case "300":
                binSize = "5m";
                break;
            case "900":
                binSize = "15m";
                break;
            default:
                throw new ServiceException("BitMex不支持的K周期");
        }


        List<DriverDto.KlineItem> result = new ArrayList<>();

        List<List<String>> klines = call(KLINE + "?symbol=" + instrumentId + "&interval=" + binSize, null,
                new TypeReference<List<List<String>>>() {
                });

        if(!klines.isEmpty()){
            result = klines.stream().map(n -> new DriverDto.KlineItem(n)).collect(Collectors.toList());

            INSTRUCT_TIME_MAP.put(key, System.currentTimeMillis());
            INSTRUCT_DATA_MAP.put(key, result);
        }


        return result;
    }

    public <T> T post(String uri, OkAgent.Credential credential, TypeReference<T> ref, Object body) {
        try {
            log.info("post uri is {} and body is {}", uri, GSON.toJson(body));

            HttpPost httpPost = new HttpPost(ENDPOINT_BINANCE + uri);
            httpPost.setConfig(Utils.isDebug(model) ? HTTP_CONFIG : HTTP_CONFIG_PROD);
            this.extractPath(httpPost, credential, body);
            return HTTP_CLIENT.<T>execute(httpPost, response -> {
                        String result = EntityUtils.toString(response.getEntity());
                        if(response.getStatusLine().getStatusCode() >= 400){
                            LOGGER.warn("query warn result: {}", result);
                        }

                        try {
                            return JSON.parseObject(result, ref.getType());
                        } catch (Exception e) {
                            LOGGER.warn("query result : {}", result);
                            throw e;
                        }

                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(" to be redfine");
        }
    }


    public <T> T call(String uri, OkAgent.Credential credential, TypeReference<T> ref) {
        try {
            HttpGet httpGet = new HttpGet(ENDPOINT_BINANCE + uri);
            httpGet.setConfig(Utils.isDebug(model) ? HTTP_CONFIG : HTTP_CONFIG_PROD);
            this.headers(httpGet, credential);
            return HTTP_CLIENT.<T>execute(httpGet, response -> {
                        String result = EntityUtils.toString(response.getEntity());
                        if(response.getStatusLine().getStatusCode() >= 400){
                            LOGGER.warn("query warn result: {}", result);
                        }
                        try {
                            return JSON.parseObject(result, ref.getType());
                        } catch (Exception e) {
                            LOGGER.warn("query result : {}", result);
                            throw e;
                        }
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException(" to be redfine");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new ServiceException(" to be redfine");
        }
    }

    public <T> T del(String uri, OkAgent.Credential credential, TypeReference<T> ref) {
        try {
            HttpDelete httpDelete = new HttpDelete(ENDPOINT_BINANCE + uri);
            httpDelete.setConfig(Utils.isDebug(model) ? HTTP_CONFIG : HTTP_CONFIG_PROD);
            this.headers(httpDelete, credential);
            return HTTP_CLIENT.<T>execute(httpDelete, response -> {
                        String result = EntityUtils.toString(response.getEntity());
                        if(response.getStatusLine().getStatusCode() >= 400){
                            LOGGER.warn("query warn result: {}", result);
                        }
                        try {
                            return JSON.parseObject(result, ref.getType());
                        } catch (Exception e) {
                            LOGGER.warn("query result : {}", result);
                            throw e;
                        }
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException(" to be redfine");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new ServiceException(" to be redfine");
        }
    }

    private void headers(final HttpRequestBase request, final OkAgent.Credential credential) throws URISyntaxException {
        request.setHeader(APIConstants.ACCEPT, ContentTypeEnum.APPLICATION_JSON.contentType());
        request.setHeader(APIConstants.CONTENT_TYPE, ContentTypeEnum.APPLICATION_JSON_UTF8.contentType());
        String getPath = getPath(queryParaMap(request));
        if(credential != null){
            request.setHeader(HttpHeadersEnum.BINANCE_ACCESS_KEY.header(), credential.getApiKey());
            LOGGER.info("get path is {}", getPath);
            request.setURI(new URI(requestPath(request) + "?" + getPath + "&signature=" + this.sign(getPath, credential)));
        }

    }

    private String requestPath(final HttpRequestBase request) {
        String url = this.url(request);
        String requestPath = url;
        if(requestPath.contains(APIConstants.QUESTION)){
            requestPath = requestPath.substring(0, url.lastIndexOf(APIConstants.QUESTION));
        }
        return requestPath;
    }

    @SneakyThrows
    private void extractPath(final HttpRequestBase request, final OkAgent.Credential credential, Object body) {
        request.setHeader(APIConstants.ACCEPT, ContentTypeEnum.APPLICATION_JSON.contentType());
        request.setHeader(APIConstants.CONTENT_TYPE, ContentTypeEnum.APPLICATION_JSON_UTF8.contentType());
        if(credential != null){
            request.setHeader(HttpHeadersEnum.BINANCE_ACCESS_KEY.header(), credential.getApiKey());
            String param = extractPathFromBody(body);
            request.setURI(new URI(request.getURI().toString() + "?" + param + "&signature=" + this.sign(param, credential)));
        }
    }

    private String getPath(Map<String, String> paraMaps) {
        StringBuffer sb = new StringBuffer();
        paraMaps.put("timestamp", System.currentTimeMillis() + "");
        for (Map.Entry<String, String> entry : paraMaps.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append('=').append(value).append('&');
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    private Map<String, String> queryParaMap(final HttpRequestBase request) {
        final String url = this.url(request);
        Map<String, String> param = new HashMap<>();
        if(url.contains(APIConstants.QUESTION)){
            String query = url.substring(url.lastIndexOf(APIConstants.QUESTION) + 1);
            if(query.contains("&")){
                String[] pairs = query.split("&");
                for (int i = 0; i < pairs.length; i++) {
                    String result[] = pairs[i].split("=");
                    param.put(result[0], result[1]);
                }
            } else {
                String result[] = query.split("=");
                param.put(result[0], result[1]);
            }

        }
        return param;
    }

    private String url(final HttpRequestBase request) {
        return request.getURI().toString();
    }

    private String extractPathFromBody(Object obj) throws IllegalAccessException {
        StringBuffer sb = new StringBuffer();
        if(obj != null){
            Field[] fields = obj.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                Object val = field.get(obj);
                if(val != null){
                    sb.append(field.getName()).append('=').append(val).append('&');
                }
            }
        }

        sb.append("timestamp").append('=').append(System.currentTimeMillis()).append('&');
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    private String sign(final String requestPath, final OkAgent.Credential credential) {
        final String sign;
        try {
            LOGGER.info("current request path {}", requestPath);
            sign = HmacSHA256Base64Utils.sign(requestPath, credential.getSecretKey());
            LOGGER.info("current sign {}", sign);
        } catch (final IOException e) {
            throw new CustomException("Request get body io exception.", e);
        } catch (final CloneNotSupportedException e) {
            throw new CustomException("Hmac SHA256 Base64 Signature clone not supported exception.", e);
        } catch (final InvalidKeyException e) {
            throw new CustomException("Hmac SHA256 Base64 Signature invalid key exception.", e);
        }
        return sign;
    }

    public static void main(String[] args) {
        OkAgent.Credential credential = new OkAgent.Credential();
        credential.setApiKey("mh4iSin4EMXszeUf1252BykVcCwRGcECN5Sei5KGzVlzQ1H6esoQL4bQyXvaLkuc");
        credential.setSecretKey("7xvHT6lrZF9xARl0WGplGC3gJf7uD3DKJjGea546TbrDZtXogsRieSHWJ5rJThe4");

        //刘哥
//        credential.setApiKey("kjxJ6QkxRyXCMOwjp1XHMl2S4C1Ow88vLsEcx8TUX5ug5RzdcCLLoRXzEMRqKU4L");
//        credential.setSecretKey("3WDiJL7eVYfg3vOV2C2g7XSDU43jdmeKqzuVfw61s1zdEpkejCEa09Ap2Cfult9M");
        String symbol = "ETHUSD_PERP";
        String pair = "ETHUSD";


//        String symbol = "BTCUSD_PERP";
//        String pair = "BTCUSD";

        BinanceAgent agent = new BinanceAgent();

//        log.info(">>>>>>>>>>>>>>>>>修改持仓模式");//如果相同回报不需重复修改的异常code
//        log.info("修改持仓结果:{}",JSON.toJSONString(agent.postDual(credential,new DriverDto.DualModelReq().setDualSidePosition(Const.C_TRUE))));
//
//        log.info(">>>>>>>>>>>>>>>>>查看持仓模式");
//        log.info("查询持仓模式：{}", GSON.toJson(agent.getDual(credential)));
//
//        log.info(">>>>>>>>>>>>>>>>>查询账户余额");
//        log.info(">>>>>>>>>>>.>>>>{}", JSON.toJSONString(agent.getBalance(credential)));
//
//        log.info("》》》》》》》》》》查询账户信息");
//        log.info(">>>>>>>>>>>.>>>>{}", JSON.toJSONString(agent.getAccount(credential)));

//        log.info("》》》》》》》》》》变化逐全模式");
//        log.info(">>>>>>>>>>>.>>>>{}", JSON.toJSONString(agent.switchMargin(credential,new DriverDto.MarginReq()
//                .setMarginType(Const.MARGIN_ISOLATED).setSymbol(symbol))));

//        log.info("》》》》》》》》》》调整杠杆");
//        log.info(">>>>>>>>>>>.>>>>{}", JSON.toJSONString(agent.changLeverage(credential,new DriverDto.LeverageReq()
//                .setLeverage(18).setSymbol(symbol))));
//
        log.info("》》》》》》》》》》查询最近七日订单");
        log.info(">>>>>>>>>>>.>>>>{}", JSON.toJSONString(agent.getLast7Orders(credential, symbol, null)));

//        log.info("》》》》》》》》》》查看当前全部挂单");
//        log.info(">>>>>>>>>>>.>>>>{}", JSON.toJSONString(agent.getFlyOrders(credential, symbol)));


        //***************************** 现价挂单， 查询挂单， 撤销订单 ************************************//

//        log.info("》》》》》》》》》》限价挂单");
//        DriverDto.OrderReq req = new DriverDto.OrderReq().setSide(Const.O_SIDE_BUY).setSymbol(symbol).setPrice(1000d).setQuantity(1d)
//                .setPositionSide(Const.P_Side_LONG).setType(Const.O_TYPE_LIMIT).setTimeInForce(Const.Time_In_Force_GTC);
//        DriverDto.OrderResp reps = agent.postOrder(credential, req);
//        log.info(">>>>>>>>>>  限价挂单返回 {}", JSON.toJSONString(reps));
        //orderId 37514906822

//        log.info("》》》》》》》》》》查询当前挂单");
//        log.info(">>>>>>>>>>>.>>>>{}", JSON.toJSONString(agent.getFlyOrders(credential, symbol)));


//        log.info("》》》》》》》》》》撤销挂单");
//        log.info(">>>>>>>>>>>.>>>>{}", JSON.toJSONString(agent.cancelOrder(credential, symbol,"37514906822")));


        //***************************** 市价开单， 查询订单， 查询持仓， 止盈挂单， 止损挂单， 查询挂单， 撤销挂单， 平仓 ************************************//

//        log.info("》》》》》》》》》》市价开单");
//        DriverDto.OrderReq req = new DriverDto.OrderReq().setSide(Const.O_SIDE_BUY).setSymbol(symbol).setQuantity(2d)
//                .setPositionSide(Const.P_Side_LONG).setType(Const.O_TYPE_MARKET);
//        DriverDto.OrderResp reps = agent.postOrder(credential, req);
//        log.info(">>>>>>>>>>  市价开单 {}", JSON.toJSONString(reps));

//        log.info("》》》》》》》》》》查询持仓风险");
//        log.info(">>>>>>>>>>>.>>>>{}", JSON.toJSONString(agent.positionRisk(credential, pair)));

//        log.info("》》》》》》》》》》止损挂单");
//        Double avg =1437.9d;
//        DriverDto.OrderReq req = new DriverDto.OrderReq().setSide(Const.O_SIDE_SELL).setSymbol(symbol).setQuantity(2d)
//                .setPositionSide(Const.P_Side_LONG).setType(Const.O_TYPE_STOP_MARKET).setStopPrice(new BigDecimal(avg*1)
//                        .setScale(2,BigDecimal.ROUND_HALF_DOWN)+"");
//        DriverDto.OrderResp reps = agent.postOrder(credential, req);
//
//        log.info(">>>>>>>>>>  止损挂单返回 {}", JSON.toJSONString(reps));


//        log.info("》》》》》》》》》》止盈挂单");
//        Double avg =1437.9d;
//        DriverDto.OrderReq req = new DriverDto.OrderReq().setSide(Const.O_SIDE_SELL).setSymbol(symbol).setQuantity(1d)
//                .setPositionSide(Const.P_Side_LONG).setType(Const.O_TYPE_TAKE_PROFIT_MARKET).setStopPrice(new BigDecimal(avg*1.02)
//                        .setScale(2,BigDecimal.ROUND_HALF_DOWN)+"");
//        DriverDto.OrderResp reps = agent.postOrder(credential, req);
//
//        log.info(">>>>>>>>>>  止盈挂单返回 {}", JSON.toJSONString(reps));

//        log.info("》》》》》》》》》》止盈清仓");
//        DriverDto.OrderReq req = new DriverDto.OrderReq().setSide(Const.O_SIDE_SELL).setSymbol(symbol).setRecvWindow(5000l)
//                .setPositionSide(Const.P_Side_LONG).setType(Const.O_TYPE_MARKET).setQuantity(1d);
//        DriverDto.OrderResp reps = agent.postOrder(credential, req);
//
//        log.info(">>>>>>>>>>  止盈清仓 {}", JSON.toJSONString(reps));

//        log.info("》》》》》》》》》》查看当前全部挂单");
//        log.info(">>>>>>>>>>>.>>>>{}", JSON.toJSONString(agent.getFlyOrders(credential, symbol)));


//        log.info("》》》》》》》》》》撤销订单");
//        log.info(">>>>>>>>>>>{}", JSON.toJSONString(agent.cancelOrder(credential, symbol, 37522808068l + "")));
//        log.info(">>>>>>>>>>>{}", JSON.toJSONString(agent.cancelOrder(credential, symbol, 37522756386l + "")));

//        log.info("》》》》》》》》》》获取当前价格");
//        log.info(">>>>>>>>>>>.>>>>{}", JSON.toJSONString(agent.getTkPrice(symbol)));
    }

    static String getPair(String symbol) {
        return symbol.split("_")[0];
    }


}

