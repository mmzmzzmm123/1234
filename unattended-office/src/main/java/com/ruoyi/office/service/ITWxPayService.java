package com.ruoyi.office.service;

import java.util.List;

import com.github.binarywang.wxpay.service.WxPayService;
import com.ruoyi.office.domain.TWxPay;

/**
 * 微信支付配置Service接口
 * 
 * @author ruoyi
 * @date 2023-10-25
 */
public interface ITWxPayService 
{
    /**
     * 查询微信支付配置
     * 
     * @param id 微信支付配置主键
     * @return 微信支付配置
     */
    public TWxPay selectTWxPayById(Long id);

    /**
     * 查询微信支付配置列表
     * 
     * @param tWxPay 微信支付配置
     * @return 微信支付配置集合
     */
    public List<TWxPay> selectTWxPayList(TWxPay tWxPay);

    /**
     * 新增微信支付配置
     * 
     * @param tWxPay 微信支付配置
     * @return 结果
     */
    public int insertTWxPay(TWxPay tWxPay);

    /**
     * 修改微信支付配置
     * 
     * @param tWxPay 微信支付配置
     * @return 结果
     */
    public int updateTWxPay(TWxPay tWxPay);

    /**
     * 批量删除微信支付配置
     * 
     * @param ids 需要删除的微信支付配置主键集合
     * @return 结果
     */
    public int deleteTWxPayByIds(Long[] ids);

    /**
     * 删除微信支付配置信息
     * 
     * @param id 微信支付配置主键
     * @return 结果
     */
    public int deleteTWxPayById(Long id);

    WxPayService getConfigByUserId(Long userId);

    WxPayService getConfigByStore(Long storeId);

    WxPayService getConfigByRoom(Long roomId);
}
