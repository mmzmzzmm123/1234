package com.ruoyi.office.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TStorePromotionMapper;
import com.ruoyi.office.domain.TStorePromotion;
import com.ruoyi.office.service.ITStorePromotionService;

/**
 * 优惠券 t_store_promotionService业务层处理
 *
 * @author ruoyi
 * @date 2023-06-14
 */
@Service
public class TStorePromotionServiceImpl extends ServiceImpl<TStorePromotionMapper, TStorePromotion> implements ITStorePromotionService {
    @Autowired
    private TStorePromotionMapper tStorePromotionMapper;

    /**
     * 查询优惠券 t_store_promotion
     *
     * @param id 优惠券 t_store_promotion主键
     * @return 优惠券 t_store_promotion
     */
    @Override
    public TStorePromotion selectTStorePromotionById(Long id) {
        return tStorePromotionMapper.selectTStorePromotionById(id);
    }

    /**
     * 查询优惠券 t_store_promotion列表
     *
     * @param tStorePromotion 优惠券 t_store_promotion
     * @return 优惠券 t_store_promotion
     */
    @Override
    public List<TStorePromotion> selectTStorePromotionList(TStorePromotion tStorePromotion) {
        return tStorePromotionMapper.selectTStorePromotionList(tStorePromotion);
    }

    /**
     * 新增优惠券 t_store_promotion
     *
     * @param tStorePromotion 优惠券 t_store_promotion
     * @return 结果
     */
    @Override
    public int insertTStorePromotion(TStorePromotion tStorePromotion) {
        //SecurityUtils.getUserId()
        tStorePromotion.setMerchantId(SecurityUtils.getUserId());
        tStorePromotion.setCreateBy(SecurityUtils.getUserId().toString());
        tStorePromotion.setCreateTime(DateUtils.getNowDate());
        return tStorePromotionMapper.insertTStorePromotion(tStorePromotion);
    }

    /**
     * 修改优惠券 t_store_promotion
     *
     * @param tStorePromotion 优惠券 t_store_promotion
     * @return 结果
     */
    @Override
    public int updateTStorePromotion(TStorePromotion tStorePromotion) {
        tStorePromotion.setUpdateTime(DateUtils.getNowDate());
        return tStorePromotionMapper.updateTStorePromotion(tStorePromotion);
    }

    /**
     * 批量删除优惠券 t_store_promotion
     *
     * @param ids 需要删除的优惠券 t_store_promotion主键
     * @return 结果
     */
    @Override
    public int deleteTStorePromotionByIds(Long[] ids) {
        return tStorePromotionMapper.deleteTStorePromotionByIds(ids);
    }

    /**
     * 删除优惠券 t_store_promotion信息
     *
     * @param id 优惠券 t_store_promotion主键
     * @return 结果
     */
    @Override
    public int deleteTStorePromotionById(Long id) {
        return tStorePromotionMapper.deleteTStorePromotionById(id);
    }
}
