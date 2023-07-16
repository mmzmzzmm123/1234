package com.ruoyi.office.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.ruoyi.office.domain.TWxUserCoupon;
import com.ruoyi.office.domain.vo.WxUserCouponQryReq;
import com.ruoyi.office.domain.vo.WxUserCouponResp;

/**
 * 用户优惠券Mapper接口
 *
 * @author ruoyi
 * @date 2023-06-05
 */
public interface TWxUserCouponMapper extends BaseMapper<TWxUserCoupon> {
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
     * 删除用户优惠券
     *
     * @param id 用户优惠券主键
     * @return 结果
     */
    public int deleteTWxUserCouponById(Long id);

    /**
     * 批量删除用户优惠券
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTWxUserCouponByIds(Long[] ids);

    List<WxUserCouponResp> selectWxUserInvalidCouponList(WxUserCouponQryReq qry);
    List<WxUserCouponResp> selectWxUserValidCouponList(WxUserCouponQryReq qry);
}
