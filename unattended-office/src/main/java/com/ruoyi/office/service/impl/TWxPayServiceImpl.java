package com.ruoyi.office.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.office.domain.TRoom;
import com.ruoyi.office.domain.TStore;
import com.ruoyi.office.domain.TWxPay;
import com.ruoyi.office.mapper.TWxPayMapper;
import com.ruoyi.office.service.ITRoomService;
import com.ruoyi.office.service.ITStoreService;
import com.ruoyi.office.service.ITWxPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 微信支付配置Service业务层处理
 *
 * @author ruoyi
 * @date 2023-10-25
 */
@Service
public class TWxPayServiceImpl extends ServiceImpl<TWxPayMapper, TWxPay> implements ITWxPayService {
    @Autowired
    private TWxPayMapper tWxPayMapper;

    /**
     * 查询微信支付配置
     *
     * @param id 微信支付配置主键
     * @return 微信支付配置
     */
    @Override
    public TWxPay selectTWxPayById(Long id) {
        return tWxPayMapper.selectTWxPayById(id);
    }

    /**
     * 查询微信支付配置列表
     *
     * @param tWxPay 微信支付配置
     * @return 微信支付配置
     */
    @Override
    public List<TWxPay> selectTWxPayList(TWxPay tWxPay) {
        return tWxPayMapper.selectTWxPayList(tWxPay);
    }

    /**
     * 新增微信支付配置
     *
     * @param tWxPay 微信支付配置
     * @return 结果
     */
    @Override
    public int insertTWxPay(TWxPay tWxPay) {
        tWxPay.setCreateTime(DateUtils.getNowDate());
        return tWxPayMapper.insertTWxPay(tWxPay);
    }

    /**
     * 修改微信支付配置
     *
     * @param tWxPay 微信支付配置
     * @return 结果
     */
    @Override
    public int updateTWxPay(TWxPay tWxPay) {
        tWxPay.setUpdateTime(DateUtils.getNowDate());
        return tWxPayMapper.updateTWxPay(tWxPay);
    }

    /**
     * 批量删除微信支付配置
     *
     * @param ids 需要删除的微信支付配置主键
     * @return 结果
     */
    @Override
    public int deleteTWxPayByIds(Long[] ids) {
        return tWxPayMapper.deleteTWxPayByIds(ids);
    }

    /**
     * 删除微信支付配置信息
     *
     * @param id 微信支付配置主键
     * @return 结果
     */
    @Override
    public int deleteTWxPayById(Long id) {
        return tWxPayMapper.deleteTWxPayById(id);
    }

    @Override
    public WxPayService getConfigByUserId(Long userId) {
        TWxPay qry = new TWxPay();
        qry.setUserId(userId);
        final List<TWxPay> tWxPays = tWxPayMapper.selectTWxPayList(qry);
        if (tWxPays.size() == 0) {
            throw new ServiceException("未找到商户配置");
        }
        return getWxPayService(tWxPays.get(0));
    }

    @Autowired
    ITStoreService storeService;

    @Override
    public WxPayService getConfigByStore(Long storeId) {
        final TStore store = storeService.selectTStoreById(storeId);
        if (store == null) {
            throw new ServiceException("门店信息有误");
        }
        return getConfigByUserId(store.getUserId());
    }

    @Autowired
    ITRoomService roomService;

    @Override
    public WxPayService getConfigByRoom(Long roomId) {
        TRoom room = roomService.selectTRoomById(roomId);
        if (room == null) {
            throw new ServiceException("房间信息有误");
        }
        return getConfigByStore(room.getStoreId());
    }

    public WxPayService getWxPayService(TWxPay wxPay) {
        final WxPayServiceImpl wxPayService = new WxPayServiceImpl();
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(StringUtils.trimToNull(wxPay.getAppid()));
        payConfig.setMchId(StringUtils.trimToNull(wxPay.getMchId()));
        payConfig.setMchKey(StringUtils.trimToNull(wxPay.getMchId()));
//        payConfig.setSubAppId(StringUtils.trimToNull(this.properties.getSubAppId()));
//        payConfig.setSubMchId(StringUtils.trimToNull(this.properties.getSubMchId()));
//        payConfig.setKeyPath(StringUtils.trimToNull(this.properties.getPrivateKeyPath()));
        //以下是apiv3以及支付分相关
//        payConfig.setServiceId(StringUtils.trimToNull(this.properties.getServiceId()));
        payConfig.setPayScoreNotifyUrl("https://foreverjade.cn/prod-api/office/api/wxnotify/"+wxPay.getUserId());
        payConfig.setPrivateKeyPath(StringUtils.trimToNull(wxPay.getPrivateKeyPath()));
        payConfig.setPrivateCertPath(StringUtils.trimToNull(wxPay.getPrivateCertPath()));
        payConfig.setCertSerialNo(StringUtils.trimToNull(wxPay.getCertSerialNo()));
        payConfig.setApiV3Key(StringUtils.trimToNull(wxPay.getApiV3Key()));

        wxPayService.setConfig(payConfig);
        return wxPayService;
    }
}
