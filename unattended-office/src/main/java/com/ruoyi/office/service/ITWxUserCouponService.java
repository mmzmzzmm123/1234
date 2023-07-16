package com.ruoyi.office.service;

import java.util.List;
import com.ruoyi.office.domain.TWxUserCoupon;
import com.ruoyi.office.domain.vo.BuyCouponReq;
import com.ruoyi.office.domain.vo.WxUserCouponReq;
import com.ruoyi.office.domain.vo.WxUserCouponResp;

/**
 * 用户优惠券Service接口
 * 
 * @author ruoyi
 * @date 2023-06-05
 */
public interface ITWxUserCouponService 
{
    /**
     * 查询用户优惠券
     * 
     * @param id 用户优惠券主键
     * @return 用户优惠券
     */
    public TWxUserCoupon selectTWxUserCouponById(Long id);

    /**
     * 查询用户优惠券列表
     * 
     * @param tWxUserCoupon 用户优惠券
     * @return 用户优惠券集合
     */
    public List<TWxUserCoupon> selectTWxUserCouponList(TWxUserCoupon tWxUserCoupon);

    /**
     * 新增用户优惠券
     * 
     * @param tWxUserCoupon 用户优惠券
     * @return 结果
     */
    public int insertTWxUserCoupon(TWxUserCoupon tWxUserCoupon);

    /**
     * 修改用户优惠券
     * 
     * @param tWxUserCoupon 用户优惠券
     * @return 结果
     */
    public int updateTWxUserCoupon(TWxUserCoupon tWxUserCoupon);

    /**
     * 批量删除用户优惠券
     * 
     * @param ids 需要删除的用户优惠券主键集合
     * @return 结果
     */
    public int deleteTWxUserCouponByIds(Long[] ids);

    /**
     * 删除用户优惠券信息
     * 
     * @param id 用户优惠券主键
     * @return 结果
     */
    public int deleteTWxUserCouponById(Long id);

    String buy(BuyCouponReq tWxUserCoupon);

    List<WxUserCouponResp> validlist(WxUserCouponReq roomId, Long id);

    List<WxUserCouponResp> usedlist(WxUserCouponReq roomId, Long id);

    List<WxUserCouponResp> invalid(WxUserCouponReq req, Long id);
}
