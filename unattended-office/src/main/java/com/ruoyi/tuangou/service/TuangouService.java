package com.ruoyi.tuangou.service;

import com.dianping.openapi.sdk.api.base.response.BaseResponse;
import com.dianping.openapi.sdk.api.customerauth.session.CustomerKeyShopScopeQuery;
import com.dianping.openapi.sdk.api.customerauth.session.entity.CustomerKeyShopScopeRequest;
import com.dianping.openapi.sdk.api.customerauth.session.entity.CustomerKeyShopScopeResponse;
import com.dianping.openapi.sdk.api.customerauth.session.entity.CustomerKeyShopScopeResponseEntity;
import com.dianping.openapi.sdk.api.oauth.DynamicToken;
import com.dianping.openapi.sdk.api.oauth.RefreshToken;
import com.dianping.openapi.sdk.api.oauth.entity.DynamicTokenRequest;
import com.dianping.openapi.sdk.api.oauth.entity.DynamicTokenResponse;
import com.dianping.openapi.sdk.api.oauth.entity.RefreshTokenRequest;
import com.dianping.openapi.sdk.api.oauth.entity.RefreshTokenResponse;
import com.dianping.openapi.sdk.api.oauth.enums.GrantType;
import com.dianping.openapi.sdk.api.tuangou.TuangouDealQueryShopDeal;
import com.dianping.openapi.sdk.api.tuangou.TuangouReceiptConsume;
import com.dianping.openapi.sdk.api.tuangou.TuangouReceiptPrepare;
import com.dianping.openapi.sdk.api.tuangou.TuangouReceiptReverseConsume;
import com.dianping.openapi.sdk.api.tuangou.entity.*;
import com.dianping.openapi.sdk.httpclient.DefaultOpenAPIClient;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.ruoyi.common.config.TuangouConfig;
import com.ruoyi.common.exception.TuangouApiException;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.office.domain.TStoreCoupon;
import com.ruoyi.office.domain.TWxUserCoupon;
import com.ruoyi.office.service.ITStoreCouponService;
import com.ruoyi.office.service.ITWxUserCouponService;
import com.ruoyi.office.service.impl.TStoreCouponServiceImpl;
import com.ruoyi.tuangou.api.TuangouProductQueryProduct;
import com.ruoyi.tuangou.entity.TuangouProductQueryProductRequest;
import com.ruoyi.tuangou.entity.TuangouProductQueryProductResponse;
import com.ruoyi.tuangou.entity.TuangouProductQueryProductResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TuangouService implements ITuangouService {
    @Autowired
    private TuangouConfig config;

    @Autowired
    private ITStoreCouponService itStoreCouponService;

    @Autowired
    private ITWxUserCouponService itWxUserCouponService;

    private DefaultOpenAPIClient dianpingClient = new DefaultOpenAPIClient();

    //@PostConstruct
    public Boolean test(String code, Long userId) {
        //TuangouDealQueryShopDealRequest request = new TuangouDealQueryShopDealRequest(config.getAppKey(), config.getAppSecret(), "3657a0656e454e537eddf139b07d5b2ee4a51ab2", "", 1, 100, "b13c044414484ce4a3a4ffdb1f2e912b");
        //TuangouDealQueryShopDeal tuangouDealQueryShopDeal = new TuangouDealQueryShopDeal(request);
        //TuangouDealQueryShopDealResponse response = dianpingClient.invoke(tuangouDealQueryShopDeal);
        //List<TuangouDealQueryShopDealResponseEntity> list = tuangouDealQueryShopDeal("b13c044414484ce4a3a4ffdb1f2e912b");
        //验券
        String shopId = "b13c044414484ce4a3a4ffdb1f2e912b";
        List<TuangouReceiptConsumeResponseEntity> list =
                tuangouReceiptConsume("123", code.replaceAll(" ", ""), 1, shopId, "userid", "username");
        String dealId = list.get(0).getDeal_id().toString();
        //撤销
        TuangouReceiptReverseConsumeRequest tuangouReceiptReverseConsumeRequest = new TuangouReceiptReverseConsumeRequest(config.getAppKey(), config.getAppSecret(), getShopSession(shopId), dealId, code, null, "userid", "username", shopId);
        DefaultOpenAPIClient openAPIClient = new DefaultOpenAPIClient();
        TuangouReceiptReverseConsume tuangouReceiptReverseConsume = new TuangouReceiptReverseConsume(tuangouReceiptReverseConsumeRequest);
        TuangouReceiptReverseConsumeResponse response = openAPIClient.invoke(tuangouReceiptReverseConsume);

        //生成订单

        TStoreCoupon query = new TStoreCoupon();
        query.setDianpingId("");
        List<TStoreCoupon> tStoreCouponList = itStoreCouponService.selectTStoreCouponList(query);
        if (tStoreCouponList.size() > 0) {
            TStoreCoupon tStoreCoupon = tStoreCouponList.get(0);
            TWxUserCoupon tWxUserCoupon = new TWxUserCoupon();
            BeanUtils.copyBeanProp(tWxUserCoupon, tStoreCoupon);
            itWxUserCouponService.insertTWxUserCoupon(tWxUserCoupon);
        }
        return true;
    }

    /**
     * 获取团购信息
     *
     * @param openShopUuid 店铺open_shop_uuid
     * @return
     */
    public List<TuangouDealQueryShopDealResponseEntity> tuangouDealQueryShopDeal(String openShopUuid) {
        TuangouDealQueryShopDealRequest request = new TuangouDealQueryShopDealRequest(config.getAppKey(), config.getAppSecret(), getShopSession(openShopUuid), "", 1, 100, openShopUuid);
        TuangouDealQueryShopDeal tuangouDealQueryShopDeal = new TuangouDealQueryShopDeal(request);
        TuangouDealQueryShopDealResponse response = dianpingClient.invoke(tuangouDealQueryShopDeal);
        dealResponse(response);
        return response.getData();
    }

    /**
     * 团购验券
     *
     * @param requestId          请求id，用于标识幂等性
     * @param receiptCode        团购券码，必须未验证
     * @param count              验券数量, 不可多于100个
     * @param openShopUuid       美团点评店铺id，必须是团购的适用门店  测试门店id b13c044414484ce4a3a4ffdb1f2e912b
     * @param appShopAccount     商家在自研系统或第三方服务商系统内登录的帐号，仅用于记录验券者的信息，该字段不参与任何验券校验逻辑
     * @param appShopAccountName 商家在自研系统或第三方服务商系统内登陆的用户名，仅用于记录验券者的信息，该字段不参与任何验券校验逻辑
     * @return
     */
    public List<TuangouReceiptConsumeResponseEntity> tuangouReceiptConsume(String requestId, String receiptCode, int count, String openShopUuid, String appShopAccount, String appShopAccountName) {
        TuangouReceiptConsumeRequest request = new TuangouReceiptConsumeRequest(config.getAppKey(), config.getAppSecret(), getShopSession(openShopUuid), requestId, receiptCode, count, null, appShopAccount, appShopAccountName, openShopUuid);
        TuangouReceiptConsume tuangouReceiptConsume = new TuangouReceiptConsume(request);
        TuangouReceiptConsumeResponse response = dianpingClient.invoke(tuangouReceiptConsume);
        dealResponse(response);
        return response.getData();
    }

    /**
     * 根据团购券码，查询券码对应的dealid下，该用户可使用的券数据量
     *
     * @param receiptCode  团购券码，必须未验证
     * @param openShopUuid 美团点评店铺id，必须是团购的适用门店
     * @return
     */
    public TuangouReceiptPrepareResponseEntity tuangouReceiptPrepare(String receiptCode, String openShopUuid) {
        TuangouReceiptPrepareRequest request = new TuangouReceiptPrepareRequest(config.getAppKey(), config.getAppSecret(), getShopSession(openShopUuid), receiptCode, null, openShopUuid);
        TuangouReceiptPrepare tuangouReceiptPrepare = new TuangouReceiptPrepare(request);
        TuangouReceiptPrepareResponse response = dianpingClient.invoke(tuangouReceiptPrepare);
        dealResponse(response);
        return response.getData();
    }

    /**
     * 查询门店消费码商品信息，用于查询除团购券外的消费码（例如：次卡）商品
     *
     * @param openShopUuid 店铺id
     * @return
     */
    public TuangouProductQueryProductResponseEntity tuangouProductQueryProduct(String openShopUuid) {
        TuangouProductQueryProductRequest request = new TuangouProductQueryProductRequest(config, getShopSession(openShopUuid), openShopUuid);
        TuangouProductQueryProduct queryProduct = new TuangouProductQueryProduct(request);
        TuangouProductQueryProductResponse response = dianpingClient.invoke(queryProduct);
        dealResponse(response);
        return response.getData();
    }

    /**
     * 根据有效的session，以及客户标识查询当前session可用的店铺列表
     *
     * @param bid 客户的唯一标识，getSession/refreshSession接口获取
     * @return
     */
    public List<CustomerKeyShopScopeResponseEntity> customerKeyShopScope(String bid) {
        CustomerKeyShopScopeRequest request = new CustomerKeyShopScopeRequest(config.getAppKey(), config.getAppSecret(), getBidSession(bid), bid);
        CustomerKeyShopScopeQuery shopScopeQuery = new CustomerKeyShopScopeQuery(request);
        CustomerKeyShopScopeResponse response = dianpingClient.invoke(shopScopeQuery);
        dealResponse(response);
        return response.getData();
    }

    /**
     * 商家授权后获取session
     *
     * @param authorizationCode
     */
    public void getSession(String authorizationCode) {
        DynamicTokenRequest request = new DynamicTokenRequest(config.getAppKey(), config.getAppSecret(), GrantType.AUTHORIZATION_CODE.getValue(), authorizationCode);
        DynamicToken dynamicToken = new DynamicToken(request);
        DynamicTokenResponse response = dianpingClient.invoke(dynamicToken);
        dealResponse(response);
        // 持久化session todo...
//        response.getAccess_token();
//        response.getRefresh_token();
//        response.getRemain_refresh_count();
//        response.getScope();
//        response.getExpires_in();
//        response.getBid();
    }

    /**
     * 刷新session
     *
     * @param refreshSession
     */
    public void refreshSession(String refreshSession) {
        RefreshTokenRequest request = new RefreshTokenRequest(config.getAppKey(), config.getAppSecret(), refreshSession);
        RefreshToken refreshToken = new RefreshToken(request);
        RefreshTokenResponse response = dianpingClient.invoke(refreshToken);
        dealResponse(response);
        // 持久化session todo...
    }

    private void dealResponse(BaseResponse response) {
        if (response.getCode() != 200) {
            throw new TuangouApiException(response.getMsg());
        }
    }

    private String getShopSession(String openShopUuid) {
        // todo...
        // 判断session是否过期，刷新session
        return "169e804081c8aefb54330194c8512992fe1c3c1e";
    }

    private String getBidSession(String bid) {
        // todo...
        // 判断session是否过期，刷新session
        return "169e804081c8aefb54330194c8512992fe1c3c1e";
    }
}
