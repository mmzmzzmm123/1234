package com.ruoyi.office.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.office.domain.TRoomOrder;
import com.ruoyi.office.domain.TStorePackage;
import com.ruoyi.office.domain.TWxUser;
import com.ruoyi.office.domain.enums.OfficeEnum;
import com.ruoyi.office.domain.vo.BuyStorePackReq;
import com.ruoyi.office.domain.vo.PrepayResp;
import com.ruoyi.office.service.ITStorePackageService;
import com.ruoyi.office.service.ITWxUserService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TWxUserPackageMapper;
import com.ruoyi.office.domain.TWxUserPackage;
import com.ruoyi.office.service.ITWxUserPackageService;

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
        BigDecimal totalPrice = storePackage.getPayAmount().add(storePackage.getGiftAmont());
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

        ArrayList<WxPayUnifiedOrderV3Request.GoodsDetail> goodsDetails = new ArrayList<>();
        goodsDetails.add(
                new WxPayUnifiedOrderV3Request.GoodsDetail() {
                }.setMerchantGoodsId("").setUnitPrice(0).setQuantity(0));

        v3Request.setAppid(config.getAppId()).setMchid(config.getMchId()).setNotifyUrl(config.getNotifyUrl())
                .setDescription("packId: " + storePack.getPackId()).setOutTradeNo(String.valueOf(orderNo))
                .setAmount(
                        new WxPayUnifiedOrderV3Request.Amount() {
                        }.setTotal(totalPrice.intValue() * 100))
                .setPayer(
                        new WxPayUnifiedOrderV3Request.Payer() {
                        }.setOpenid(wxUser.getOpenId()))
                .setAttach(OfficeEnum.WxTradeType.PACK.getCode());

        WxPayUnifiedOrderV3Result.JsapiResult jsapiResult = null;
        try {
            jsapiResult = wxPayService.createOrderV3(TradeTypeEnum.valueOf("JSAPI"), v3Request);

            userPackage.setOrderNo(String.valueOf(orderNo));
            userPackage.setPackId(storePackage.getId());
            userPackage.setPackageName(storePackage.getPackageName());
            userPackage.setPayAmount(storePackage.getPayAmount());
            userPackage.setGiftAmont(storePackage.getGiftAmont());
            userPackage.setMerchant(Long.parseLong(storePackage.getCreateBy()));
            tWxUserPackageMapper.insertTWxUserPackage(userPackage);
        } catch (WxPayException e) {
            e.printStackTrace();
            log.error("JSAPI 下单：" + e.getLocalizedMessage());
        }
        resp.setJsapiResult(jsapiResult);
        resp.setOrderId(userPackage.getId());
        return resp;
    }

    @Override
    public void wxNotify(String outTradeNo, String openId, Integer total) {

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
        update.setStatus(OfficeEnum.RoomOrderStatus.ORDERED.getCode());// 已预约
        tWxUserPackageMapper.updateTWxUserPackage(update);

    }
}
