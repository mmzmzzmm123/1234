package com.ruoyi.office.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.office.domain.*;
import com.ruoyi.office.domain.enums.OfficeEnum;
import com.ruoyi.office.domain.vo.BuyStorePackReq;
import com.ruoyi.office.domain.vo.PrepayResp;
import com.ruoyi.office.service.ITStorePackageService;
import com.ruoyi.office.service.ITWxUserAmountService;
import com.ruoyi.office.service.ITWxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TWxUserPackageMapper;
import com.ruoyi.office.service.ITWxUserPackageService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户套餐购买记录Service业务层处理
 *
 * @author ruoyi
 * @date 2023-05-30
 */
@Service
public class TWxUserPackageServiceImpl extends ServiceImpl<TWxUserPackageMapper, TWxUserPackage> implements ITWxUserPackageService {
    @Autowired
    private TWxUserPackageMapper tWxUserPackageMapper;

    /**
     * 查询用户套餐购买记录
     *
     * @param id 用户套餐购买记录主键
     * @return 用户套餐购买记录
     */
    @Override
    public TWxUserPackage selectTWxUserPackageById(Long id) {
        return tWxUserPackageMapper.selectTWxUserPackageById(id);
    }

    /**
     * 查询用户套餐购买记录列表
     *
     * @param tWxUserPackage 用户套餐购买记录
     * @return 用户套餐购买记录
     */
    @Override
    public List<TWxUserPackage> selectTWxUserPackageList(TWxUserPackage tWxUserPackage) {
        return tWxUserPackageMapper.selectTWxUserPackageList(tWxUserPackage);
    }

    /**
     * 新增用户套餐购买记录
     *
     * @param tWxUserPackage 用户套餐购买记录
     * @return 结果
     */
    @Override
    public int insertTWxUserPackage(TWxUserPackage tWxUserPackage) {
        tWxUserPackage.setCreateTime(DateUtils.getNowDate());
        return tWxUserPackageMapper.insertTWxUserPackage(tWxUserPackage);
    }

    /**
     * 修改用户套餐购买记录
     *
     * @param tWxUserPackage 用户套餐购买记录
     * @return 结果
     */
    @Override
    public int updateTWxUserPackage(TWxUserPackage tWxUserPackage) {
        tWxUserPackage.setUpdateTime(DateUtils.getNowDate());
        return tWxUserPackageMapper.updateTWxUserPackage(tWxUserPackage);
    }

    /**
     * 批量删除用户套餐购买记录
     *
     * @param ids 需要删除的用户套餐购买记录主键
     * @return 结果
     */
    @Override
    public int deleteTWxUserPackageByIds(Long[] ids) {
        return tWxUserPackageMapper.deleteTWxUserPackageByIds(ids);
    }

    /**
     * 删除用户套餐购买记录信息
     *
     * @param id 用户套餐购买记录主键
     * @return 结果
     */
    @Override
    public int deleteTWxUserPackageById(Long id) {
        return tWxUserPackageMapper.deleteTWxUserPackageById(id);
    }

    @Autowired
    private WxPayService wxPayService;
    @Autowired
    ITWxUserService wxUserService;
    @Autowired
    ITStorePackageService storePackageService;

    @Override
    public PrepayResp buy(BuyStorePackReq storePack, Long userId) {
        PrepayResp resp = new PrepayResp();
        TWxUser wxUser = wxUserService.selectTWxUserById(userId);
        TStorePackage storePackage = storePackageService.selectTStorePackageById(storePack.getPackId());
        BigDecimal totalPrice = storePackage.getPayAmount();//.add(storePackage.getGiftAmont());
        TWxUserPackage userPackage = new TWxUserPackage();
        // 计算订单号
        long orderNo = 0l;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        final String prefix = storePack.getPackId() + sdf.format(new Date());
        Long maxId = tWxUserPackageMapper.getMaxOrder(prefix);
        if (maxId == null)
            orderNo = Long.parseLong(prefix + "00");
        else {
            orderNo = maxId + 1;
        }

        WxPayUnifiedOrderV3Request v3Request = new WxPayUnifiedOrderV3Request();
        final WxPayConfig config = wxPayService.getConfig();

        WxPayUnifiedOrderV3Request.Amount v3Amount = new WxPayUnifiedOrderV3Request.Amount();
        v3Amount.setTotal(totalPrice.multiply(new BigDecimal(100)).intValue());
        WxPayUnifiedOrderV3Request.Payer v3payer = new WxPayUnifiedOrderV3Request.Payer();
        v3payer.setOpenid(wxUser.getOpenId());

        v3Request.setAppid(config.getAppId()).setMchid(config.getMchId()).setNotifyUrl(config.getPayScoreNotifyUrl())
                .setDescription("packId: " + storePack.getPackId()).setOutTradeNo(String.valueOf(orderNo))
                .setAmount(v3Amount)
                .setPayer(v3payer)
                .setAttach(OfficeEnum.WxTradeType.PACK.getCode());

        WxPayUnifiedOrderV3Result.JsapiResult jsapiResult = null;
        try {
            jsapiResult = wxPayService.createOrderV3(TradeTypeEnum.valueOf("JSAPI"), v3Request);

            userPackage.setOrderNo(orderNo);
            userPackage.setPackId(storePackage.getId());
            userPackage.setPackageName(storePackage.getPackageName());
            userPackage.setPayAmount(storePackage.getPayAmount());
            userPackage.setGiftAmont(storePackage.getGiftAmont());
            userPackage.setMerchant(Long.parseLong(storePackage.getCreateBy()));
            userPackage.setStatus(OfficeEnum.PackageOrderStatus.TO_PAY.getCode());
            userPackage.setRemark(jsapiResult.toString());
            userPackage.setCreateBy(userId + "");
            userPackage.setCreateTime(new Date());
            tWxUserPackageMapper.insertTWxUserPackage(userPackage);
        } catch (WxPayException e) {
            e.printStackTrace();
            log.error("JSAPI 下单：" + e.getLocalizedMessage());
            throw new ServiceException(e.getLocalizedMessage());
        } catch (Exception ex) {
            throw new ServiceException(ex.getLocalizedMessage());
        }
        resp.setJsapiResult(jsapiResult);
        resp.setOrderId(userPackage.getId());
        return resp;
    }

    @Autowired
    ITWxUserAmountService userAmountService;

    @Override
    @Transactional
    public WxPayOrderQueryV3Result finish(PrepayResp vo, Long wxuserid) {
        TWxUserPackage order = tWxUserPackageMapper.selectTWxUserPackageById(vo.getOrderId());
        if (order.getStatus().equals(OfficeEnum.PackageOrderStatus.PAYED.getCode())) {
//            throw new ServiceException("订单已支付");

            log.debug("订单已支付");
            return null;
        }
        //查询支付状态；
        WxPayOrderQueryV3Result v3Result = null;
        try {
            v3Result = wxPayService.queryOrderV3("", String.valueOf(order.getOrderNo()));
        } catch (WxPayException e) {
            log.error("查询微信后台订单失败： " + e.getMessage());
            throw new ServiceException("查询微信后台订单失败");
        }
        // 根据业务需要，更新商户平台订单状态
        String tradState = v3Result.getTradeState();
        if (tradState.equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.SUCCESS)) {
            // 业务需求
            TWxUserPackage updateOrder = new TWxUserPackage();
            updateOrder.setId(order.getId());
            updateOrder.setStatus(OfficeEnum.PackageOrderStatus.PAYED.getCode());
            tWxUserPackageMapper.updateTWxUserPackage(updateOrder);

            // 对用户的商户余额进行增加操作；
            addWxUserAmount(wxuserid, order);

        } else if (tradState.equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.REFUND)) {
            throw new ServiceException("订单转入退款");
        } else if (tradState.equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.NOTPAY)) {
            throw new ServiceException("订单未支付或已支付未收到微信通知");
        } else if (tradState.equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.CLOSED)) {
            // 关闭订单API
            //最新更新时间：2020.05.26 版本说明
            //
            //以下情况需要调用关单接口：
            //1、商户订单支付失败需要生成新单号重新发起支付，要对原订单号调用关单，避免重复支付；
            //2、系统下单后，用户支付超时，系统退出不再受理，避免用户继续，请调用关单接口。
            //
            //注意：
            //• 关单没有时间限制，建议在订单生成后间隔几分钟（最短5分钟）再调用关单接口，避免出现订单状态同步不及时导致关单失败。
            throw new ServiceException("支付订单已关闭");
        } else if (tradState.equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.REVOKED)) {
            throw new ServiceException("已撤销（仅付款码支付会返回）");
        } else if (tradState.equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.USER_PAYING)) {
            throw new ServiceException("用户支付中（仅付款码支付会返回）");
        } else if (tradState.equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.PAY_ERROR)) {
            throw new ServiceException("支付失败（仅付款码支付会返回）");
        }/*else{
            throw new ServiceException(v3Result.getTradeStateDesc());
        }*/

        return v3Result;
    }

    @Override
    public void wxNotify(String outTradeNo, String openId, Integer total, String wxCallback) {

        // 处理订单状态
        TWxUserPackage userPackage = new TWxUserPackage();
        userPackage.setOrderNo(Long.parseLong(outTradeNo));
        final List<TWxUserPackage> userPackages = tWxUserPackageMapper.selectTWxUserPackageList(userPackage);
        if (userPackages.size() > 0)
            userPackage = userPackages.get(0);

        TWxUser wxUser = wxUserService.selectTWxUserById(Long.parseLong(userPackage.getCreateBy()));
        // 验证金额和 openid
        if (!openId.equalsIgnoreCase(wxUser.getOpenId()) || userPackage.getPayAmount().multiply(new BigDecimal(100)).compareTo(new BigDecimal(total)) != 0) {
            throw new ServiceException("FAIL:金额或用户不匹配");
        }
        // 注意：微信会通知多次，因此需判断此订单
        if (userPackage.getStatus().equals(OfficeEnum.RoomOrderStatus.ORDERED.getCode()))
            return;

        TWxUserPackage update = new TWxUserPackage();
        update.setId(userPackage.getId());
        update.setStatus(OfficeEnum.RoomOrderStatus.ORDERED.getCode());// 已预约??
        update.setRemark(wxCallback);
        tWxUserPackageMapper.updateTWxUserPackage(update);
        // 对用户的商户余额进行增加操作；
        addWxUserAmount(Long.parseLong(openId), userPackage);
    }

    /**
     * 批量删除用户套餐购买记录
     *
     * @param wxUserId wxUserId
     * @param order    充值订单
     * @return 结果
     */
    public void addWxUserAmount(Long wxUserId, TWxUserPackage order) {
        TWxUserAmount wxUserAmount = new TWxUserAmount();
        wxUserAmount.setWxUserId(wxUserId);
        wxUserAmount.setUserId(order.getMerchant());
        wxUserAmount.setAmount(order.getPayAmount().add(order.getGiftAmont()));//余额
        wxUserAmount.setCashAmount(order.getPayAmount());//本金余额
        wxUserAmount.setWelfareAmount(order.getGiftAmont());//赠送余额

        userAmountService.insertTWxUserAmount(wxUserAmount);

    }
}
