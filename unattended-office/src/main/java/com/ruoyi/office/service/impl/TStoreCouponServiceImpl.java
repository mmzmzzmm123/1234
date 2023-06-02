package com.ruoyi.office.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TStoreCouponMapper;
import com.ruoyi.office.domain.TStoreCoupon;
import com.ruoyi.office.service.ITStoreCouponService;

/**
 * 卡券Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-31
 */
@Service
public class TStoreCouponServiceImpl extends ServiceImpl<TStoreCouponMapper, TStoreCoupon> implements ITStoreCouponService
{
    @Autowired
    private TStoreCouponMapper tStoreCouponMapper;

    /**
     * 查询卡券
     * 
     * @param id 卡券主键
     * @return 卡券
     */
    @Override
    public TStoreCoupon selectTStoreCouponById(Long id)
    {
        return tStoreCouponMapper.selectTStoreCouponById(id);
    }

    /**
     * 查询卡券列表
     * 
     * @param tStoreCoupon 卡券
     * @return 卡券
     */
    @Override
    public List<TStoreCoupon> selectTStoreCouponList(TStoreCoupon tStoreCoupon)
    {
        return tStoreCouponMapper.selectTStoreCouponList(tStoreCoupon);
    }

    /**
     * 新增卡券
     * 
     * @param tStoreCoupon 卡券
     * @return 结果
     */
    @Override
    public int insertTStoreCoupon(TStoreCoupon tStoreCoupon)
    {
        tStoreCoupon.setCreateTime(DateUtils.getNowDate());
        return tStoreCouponMapper.insertTStoreCoupon(tStoreCoupon);
    }

    /**
     * 修改卡券
     * 
     * @param tStoreCoupon 卡券
     * @return 结果
     */
    @Override
    public int updateTStoreCoupon(TStoreCoupon tStoreCoupon)
    {
        tStoreCoupon.setUpdateTime(DateUtils.getNowDate());
        return tStoreCouponMapper.updateTStoreCoupon(tStoreCoupon);
    }

    /**
     * 批量删除卡券
     * 
     * @param ids 需要删除的卡券主键
     * @return 结果
     */
    @Override
    public int deleteTStoreCouponByIds(Long[] ids)
    {
        return tStoreCouponMapper.deleteTStoreCouponByIds(ids);
    }

    /**
     * 删除卡券信息
     * 
     * @param id 卡券主键
     * @return 结果
     */
    @Override
    public int deleteTStoreCouponById(Long id)
    {
        return tStoreCouponMapper.deleteTStoreCouponById(id);
    }
}
