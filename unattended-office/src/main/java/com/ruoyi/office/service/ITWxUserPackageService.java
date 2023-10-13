package com.ruoyi.office.service;

import java.util.List;

import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryV3Result;
import com.ruoyi.office.domain.TWxUserPackage;
import com.ruoyi.office.domain.vo.BuyStorePackReq;
import com.ruoyi.office.domain.vo.PrepayResp;

/**
 * 用户套餐购买记录Service接口
 *
 * @author ruoyi
 * @date 2023-05-30
 */
public interface ITWxUserPackageService
{
    /**
     * 查询用户套餐购买记录
     *
     * @param id 用户套餐购买记录主键
     * @return 用户套餐购买记录
     */
    public TWxUserPackage selectTWxUserPackageById(Long id);

    /**
     * 查询用户套餐购买记录列表
     *
     * @param tWxUserPackage 用户套餐购买记录
     * @return 用户套餐购买记录集合
     */
    public List<TWxUserPackage> selectTWxUserPackageList(TWxUserPackage tWxUserPackage);

    /**
     * 新增用户套餐购买记录
     *
     * @param tWxUserPackage 用户套餐购买记录
     * @return 结果
     */
    public int insertTWxUserPackage(TWxUserPackage tWxUserPackage);

    /**
     * 修改用户套餐购买记录
     *
     * @param tWxUserPackage 用户套餐购买记录
     * @return 结果
     */
    public int updateTWxUserPackage(TWxUserPackage tWxUserPackage);

    /**
     * 批量删除用户套餐购买记录
     *
     * @param ids 需要删除的用户套餐购买记录主键集合
     * @return 结果
     */
    public int deleteTWxUserPackageByIds(Long[] ids);

    /**
     * 删除用户套餐购买记录信息
     *
     * @param id 用户套餐购买记录主键
     * @return 结果
     */
    public int deleteTWxUserPackageById(Long id);

    PrepayResp buy(BuyStorePackReq tWxUserPackage, Long id);

    WxPayOrderQueryV3Result finish(PrepayResp vo, Long wxuserid);

    void wxNotify(String outTradeNo, String openid, WxPayOrderNotifyV3Result.Amount total, String s);
}
