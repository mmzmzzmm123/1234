package com.ruoyi.office.task;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.github.binarywang.wxpay.bean.marketing.FavorStocksCreateRequest;
import com.github.binarywang.wxpay.bean.marketing.FavorStocksCreateResult;
import com.github.binarywang.wxpay.bean.marketing.enums.StockTypeEnum;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.MarketingFavorServiceImpl;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.ruoyi.office.domain.vo.TtlockGatewayRes;
import com.ruoyi.office.domain.vo.TtlockTokenRes;
import com.ruoyi.office.ttlock.TtlockConfig;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.github.binarywang.wxpay.service.MarketingFavorService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 定时任务调度测试
 *
 * @author ruoyi
 */
@Component("vxTask")
public class VxTask {

//    @Resource(name = "customerWxMaService")
//    WxMaService customerWxMaService;

   /* @PostConstruct
    private void ttlockTest() {
        try {
            String username = "15958013510";
            String password = "0b1a732c1df34a6adf481977001bc492";
            TtlockTokenRes ttlockTokenRes = TtlockConfig.getTokenTest(username, password);
            String lockId = "11361601";
            TtlockGatewayRes ttlockGatewayRes1 = TtlockConfig.unlockTest(ttlockTokenRes.getAccess_token(), lockId);
            TtlockGatewayRes ttlockGatewayRes = TtlockConfig.lockTest(ttlockTokenRes.getAccess_token(), lockId);

        } catch (Exception e) {
            System.out.println("Failed to send message: " + e.getMessage());
        }
    }*/

    final String MCH_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDKQII/FFXViTY0" +
            "vGVeylQ1qhnvd+lDtcCDK6IBSlKfh2Agnqdy2TiUCslWUldTI14catiuixpHL9Lz" +
            "zm/lqh/lZjbgyKQZosNMt1+nDZGjwshRSnjjD6EuzOJoU/kvXJsQWlHRjLbyf324" +
            "jvtHi7vuyUanC56+YYhh5L01YTI7guVIFrBIRtip4pXZ3e4dwlvaP+faWHNRu6GK" +
            "X0gD6m7BpUmYwcuN3ba8QWDjEyXpCEylrADBte9YIFRzcswBhJN+ORuGoifJskpW" +
            "knF/XJtSc0KzRYGTRObw1f6leIuLze0WYJy3IKxWu0jR25TkA3AJhSLZkj9JaUxF" +
            "HSVVbPnFAgMBAAECggEBAJEIeV3qozKhVnGyt66lwIVLGZYTkv6vEwH9BAuQ1Hb5" +
            "iQFbRBCjnGLs6Twxh9Vig1Y1eHSR46IwtUGIQ82ZlY3XIntG5gZMJWiaBsmwfUPF" +
            "pghFMzMAPkFlQuY1cXd5HOk5G3r9QSPcMLdsMN05Yub1Buk69/bl0fsBIH1q2a66" +
            "BET/NgYyYrOOoZnoT9CHFfBwj5Kl0W2GK7WoHWyjRP+scjKUXQj1du6uj/WhvfO8" +
            "bYkfHX3wbswdqptwvIu4I7oL/qkzgH4DeEx3XoX9hXFG4eqAIXdiWa3kRvGIopW1" +
            "LdPHl7q/Ga41S3xpUIwOlEeFNvWhtx4hYRxRw6N/loECgYEA7E8uaPR5Xc9IurCM" +
            "n+iCn3vX4GdYflqhilkdwIE8IceFyFsjeKh0xY2Bkod28zYxbs9TdJd3lqw9aIMw" +
            "Cyf0UMTYNF2caTWwZFA5b7F5Ao6ndPdDs2EnRLJvF41YAshcmINKt/hesGo16utR" +
            "6rEjwkM2zyTilDL68Qb5Xwiyqo0CgYEA2xrV9Za8i319LOOrVCFL0LjtKQ1jxbVS" +
            "pfoskr/h9CYQVFa0nbwE0mTSVY7e3/qqPE5sCNYgjoJgLBnyZS5BBElXLmHbv2Xb" +
            "u7XyoCjyBjI4sCi5NFLoyhg9FbwdMynD+poow/vAdN42C5gG/Y5qGmG9vixvc6gy" +
            "hxhMWJtIGhkCgYBSL4O7mvMu7i2kSEzQPKRZZVACSs8bOtgd7rqLPic0tFjSAuiG" +
            "SUg7gOiElrqHAd0VZePnk9pSu9qro5ys9JtjD+MF61y/tp1/detT7kxg2E5jPq/8" +
            "DhkDKm+6qDzo0thpbpgo/aepAXd5GZim4C7InGnOh0yKMGTYOQ23wKWq7QKBgQC6" +
            "Z55YDiHiWqTAKAF+1cT2kh3Sqz2S2qyx4KFDSFQnrqp91jZba7Z+eKApgh9DVrK2" +
            "E9H9wo54L5s6aei7BBr0VqEBKOgXccT7cdQ1RiCB/QAVfs/yIAdEyFJPx3bgLEw7" +
            "TCQaeHhH9TTtsC1iUmMahsUyogM/1n0GSjnJuHIjIQKBgQCzVVBcdQWk/RDv1eBr" +
            "/qmPnfZCDsC7WRIkGGoRbM2w/ZNJjj3AQc+5g4CNxxiWibC2h6mKZEDGqB9WZzRe" +
            "Ajrba5Yn4xiXn/BGVp8dcLW3CjT2SV3tkDXNn5V7T4LFFyllRiI5qLF5mjracZSS" +
            "8iwklrF4KAsg2ueRho5yxSXWRQ==";

//    private void sendVxMessage() {
//        try {
//            //todo定时任务检测商家订单到期
//            WxMaSubscribeMessage wxMaSubscribeMessage = new WxMaSubscribeMessage();
//            wxMaSubscribeMessage.setToUser("odCYn44UWnYqWRzvffK6MehE0UHs");
//            wxMaSubscribeMessage.setTemplateId("58BvI5jDnq61I9slOIIjf89J9ionC95R14eUJ9rQLWA");
//            List<WxMaSubscribeMessage.MsgData> msgDataList = new ArrayList<>();
//            WxMaSubscribeMessage.MsgData msgData = new WxMaSubscribeMessage.MsgData();
//            msgData.setName("thing1");
//            msgData.setValue("CESHI");
//            msgDataList.add(msgData);
//            msgData = new WxMaSubscribeMessage.MsgData();
//            msgData.setName("time2");
//            msgData.setValue("2023-10-06 23:56:56");
//            msgDataList.add(msgData);
//            msgData = new WxMaSubscribeMessage.MsgData();
//            msgData.setName("thing6");
//            msgData.setValue("十三将");
//            msgDataList.add(msgData);
//            wxMaSubscribeMessage.setData(msgDataList);
//            customerWxMaService.getMsgService().sendSubscribeMsg(wxMaSubscribeMessage);
//
//            System.out.println("Message sent successfully!");
//        } catch (Exception e) {
//            System.out.println("Failed to send message: " + e.getMessage());
//        }
//    }

//    private void sendVxPublicMessage() {
//        try {
//
//            List<WxMpTemplateData> data = Arrays.asList(
//                    new WxMpTemplateData("thing2", "十三将长大店"),
//                    new WxMpTemplateData("thing3", "长沙大学附近"),
//                    new WxMpTemplateData("time4", "2023-10-14 23:56:56"),
//                    new WxMpTemplateData("phone_number8", "15988466903"),
//                    new WxMpTemplateData("thing15", "房间1")
//            );
//
//            WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
//                    .toUser("oNosp6o1yVW4UQ2Jh6zS9B-B2SM4")//要推送的用户openid
//                    .data(data) //数据
//                    .templateId("WSL_e3y5vGMU0iIMfn3SEgfMoAYOhLzYzHit8nBtWMQ")//模版id
////                    .url("http://www.baidu.com") // 点击详情跳转地址
//                    .build();
//
//            WxMpService wxMpService=new WxMpServiceImpl();
//            WxMpDefaultConfigImpl wxMpConfigStorage=new WxMpDefaultConfigImpl();
//
//
//            wxMpConfigStorage.setAppId("wxec3fc9831532aa21");
//            wxMpConfigStorage.setSecret("d3ee01a185cddd771703c84358710d7f");
//            wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
//            WxMpUserList wxUserList = wxMpService.getUserService().userList("");
//            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
//
//
//            System.out.println("Message sent successfully!");
//        } catch (Exception e) {
//            System.out.println("Failed to send message: " + e.getMessage());
//        }
//    }

//    private void createCoupon() {
//        try {
//            FavorStocksCreateRequest favorStocksCreateRequest = new FavorStocksCreateRequest();
//            favorStocksCreateRequest.setStockName("长大店新人优惠");
//            favorStocksCreateRequest.setComment("20231010001");
//            favorStocksCreateRequest.setBelongMerchant("1648735517");
//            favorStocksCreateRequest.setAvailableBeginTime("2023-10-11T00:00:01.000+08:00");
//            favorStocksCreateRequest.setAvailableEndTime("2023-10-20T00:00:01.000+08:00");
//            FavorStocksCreateRequest.StockUseRule stockUseRule = new FavorStocksCreateRequest.StockUseRule();
//            stockUseRule.setMaxCoupons(20);
//            stockUseRule.setMaxAmount(20 * 40);
//            stockUseRule.setMaxCouponsPerUser(1);
//            stockUseRule.setNaturalPersonLimit(true);
//            stockUseRule.setPreventApiAbuse(false);
//            favorStocksCreateRequest.setStockUseRule(stockUseRule);
//            FavorStocksCreateRequest.PatternInfo patternInfo = new FavorStocksCreateRequest.PatternInfo();
//            patternInfo.setDescription("十三将长大店新人优惠套餐，任意包厢任意时段19.9元4小时");
//            favorStocksCreateRequest.setPatternInfo(patternInfo);
//            FavorStocksCreateRequest.CouponUseRule couponUseRule = new FavorStocksCreateRequest.CouponUseRule();
//            FavorStocksCreateRequest.FixedNormalCoupon fixedNormalCoupon = new FavorStocksCreateRequest.FixedNormalCoupon();
//            fixedNormalCoupon.setCouponAmount(40);
//            fixedNormalCoupon.setTransactionMinimum(59);
//            couponUseRule.setFixedNormalCoupon(fixedNormalCoupon);
//            List<String> merchats = new ArrayList<>();
//            merchats.add("1648735517");
//            couponUseRule.setAvailableMerchants(merchats);
//            favorStocksCreateRequest.setCouponUseRule(couponUseRule);
//            favorStocksCreateRequest.setNoCash(false);
//            favorStocksCreateRequest.setStockType(StockTypeEnum.NORMAL);
//            favorStocksCreateRequest.setOutRequestNo("1648735517-20231010-001");
//
//
//            WxPayConfig wxPayConfig = new WxPayConfig();
//            wxPayConfig.setMchId("1648735517");
//            wxPayConfig.setCertSerialNo("6FBD52B6265965E025F5B6B0DF7557B0F73D34B6");
//            wxPayConfig.setApiV3Key("SyL9815fJqa7etdkz8Z4dTefbD2ZJzr3");
//            wxPayConfig.setPrivateKeyPath("D:\\work\\other\\2023\\01_珏恒\\doc\\02_微信支付\\apiclient_key.pem");
//            wxPayConfig.setPrivateCertPath("D:\\work\\other\\2023\\01_珏恒\\doc\\02_微信支付\\apiclient_cert.pem");
//            wxPayConfig.setMchKey(MCH_KEY);
//            WxPayService wxPayService = new WxPayServiceImpl();
//            wxPayService.setConfig(wxPayConfig);
//            MarketingFavorService marketingFavorService = new MarketingFavorServiceImpl(wxPayService);
//            FavorStocksCreateResult result = marketingFavorService.createFavorStocksV3(favorStocksCreateRequest);
//        } catch (Exception e) {
//            System.out.println("Failed to send message: " + e.getMessage());
//        }
//
//    }
}
