package com.ruoyi.office.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.office.domain.TStoreCoupon;
import com.ruoyi.office.domain.vo.BuyCouponReq;
import com.ruoyi.office.service.ITStoreCouponService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TWxUserCouponMapper;
import com.ruoyi.office.domain.TWxUserCoupon;
import com.ruoyi.office.service.ITWxUserCouponService;

/**
 * 用户优惠券Service业务层处理
 *
 * @author ruoyi
 * @date 2023-06-05
 */
@Service
public class TWxUserCouponServiceImpl extends ServiceImpl<TWxUserCouponMapper, TWxUserCoupon> implements ITWxUserCouponService {
    @Autowired
    private TWxUserCouponMapper tWxUserCouponMapper;

    /**
     * 查询用户优惠券
     *
     * @param id 用户优惠券主键
     * @return 用户优惠券
     */
    @Override
    public TWxUserCoupon selectTWxUserCouponById(Long id) {
        return tWxUserCouponMapper.selectTWxUserCouponById(id);
    }

    /**
     * 查询用户优惠券列表
     *
     * @param tWxUserCoupon 用户优惠券
     * @return 用户优惠券
     */
    @Override
    public List<TWxUserCoupon> selectTWxUserCouponList(TWxUserCoupon tWxUserCoupon) {
        return tWxUserCouponMapper.selectTWxUserCouponList(tWxUserCoupon);
    }

    /**
     * 新增用户优惠券
     *
     * @param tWxUserCoupon 用户优惠券
     * @return 结果
     */
    @Override
    public int insertTWxUserCoupon(TWxUserCoupon tWxUserCoupon) {
        tWxUserCoupon.setCreateTime(DateUtils.getNowDate());
        return tWxUserCouponMapper.insertTWxUserCoupon(tWxUserCoupon);
    }

    /**
     * 修改用户优惠券
     *
     * @param tWxUserCoupon 用户优惠券
     * @return 结果
     */
    @Override
    public int updateTWxUserCoupon(TWxUserCoupon tWxUserCoupon) {
        tWxUserCoupon.setUpdateTime(DateUtils.getNowDate());
        return tWxUserCouponMapper.updateTWxUserCoupon(tWxUserCoupon);
    }

    /**
     * 批量删除用户优惠券
     *
     * @param ids 需要删除的用户优惠券主键
     * @return 结果
     */
    @Override
    public int deleteTWxUserCouponByIds(Long[] ids) {
        return tWxUserCouponMapper.deleteTWxUserCouponByIds(ids);
    }

    /**
     * 删除用户优惠券信息
     *
     * @param id 用户优惠券主键
     * @return 结果
     */
    @Override
    public int deleteTWxUserCouponById(Long id) {
        return tWxUserCouponMapper.deleteTWxUserCouponById(id);
    }

    @Autowired
    ITStoreCouponService storeCouponService;

    @Override
    public String buy(BuyCouponReq tWxUserCoupon) {
        final TStoreCoupon tStoreCoupon = storeCouponService.selectTStoreCouponById(tWxUserCoupon.getCounponId());
        if (tStoreCoupon.getPrice().compareTo(tWxUserCoupon.getPrice()) != 0) {
            throw new ServiceException("卡券价格变动，请刷新后重新购买");
        }
        // 发起支付
        String wxPayReturn = "";// 微信生成订单返回字符串，把该字符串返回给小程序，小程序用这个发起支付，支付完成wx会带着这个异步返回成功。
        // 写待支付购买记录；
        TWxUserCoupon userCoupon = new TWxUserCoupon();
        BeanUtils.copyProperties(tStoreCoupon, userCoupon);
        BeanUtils.copyProperties(tWxUserCoupon, userCoupon);
        userCoupon.setMinuteLeft(tStoreCoupon.getMaxMinuteOnce());
        if (tStoreCoupon.getValidType() == 1) { // 自购买日起
            userCoupon.setStartDate(new Date());
            userCoupon.setEndDate(DateUtils.addDays(new Date(), tStoreCoupon.getValidDays()));
        }
        return wxPayReturn;
    }

    @Override
    public List<TWxUserCoupon> validlist(Long storeId, Long id) {
        TWxUserCoupon qry = new TWxUserCoupon();
        qry.setStoreId(0l);
        qry.setUserId(id);
        List<TWxUserCoupon> userCoupons = tWxUserCouponMapper.selectTWxUserCouponList(qry);

        qry.setStoreId(storeId);
        List<TWxUserCoupon> userStoreCoupons = tWxUserCouponMapper.selectTWxUserCouponList(qry);

        return null;
    }
}
