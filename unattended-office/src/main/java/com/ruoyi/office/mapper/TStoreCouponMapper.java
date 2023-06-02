package com.ruoyi.office.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.ruoyi.office.domain.TStoreCoupon;

/**
 * 卡券Mapper接口
 *
 * @author ruoyi
 * @date 2023-05-31
 */
public interface TStoreCouponMapper extends BaseMapper<TStoreCoupon> {
    /**
     * 查询卡券
     *
     * @param id 卡券主键
     * @return 卡券
     */
    public TStoreCoupon selectTStoreCouponById(Long id);

    /**
     * 查询卡券列表
     *
     * @param tStoreCoupon 卡券
     * @return 卡券集合
     */
    public List<TStoreCoupon> selectTStoreCouponList(TStoreCoupon tStoreCoupon);

    /**
     * 新增卡券
     *
     * @param tStoreCoupon 卡券
     * @return 结果
     */
    public int insertTStoreCoupon(TStoreCoupon tStoreCoupon);

    /**
     * 修改卡券
     *
     * @param tStoreCoupon 卡券
     * @return 结果
     */
    public int updateTStoreCoupon(TStoreCoupon tStoreCoupon);

    /**
     * 删除卡券
     *
     * @param id 卡券主键
     * @return 结果
     */
    public int deleteTStoreCouponById(Long id);

    /**
     * 批量删除卡券
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTStoreCouponByIds(Long[] ids);
}
