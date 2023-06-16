package com.ruoyi.office.service.impl;

import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.office.domain.TStorePromotion;
import com.ruoyi.office.service.ITStorePromotionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TWxUserPromotionMapper;
import com.ruoyi.office.domain.TWxUserPromotion;
import com.ruoyi.office.service.ITWxUserPromotionService;

/**
 * 用户优惠券 t_wx_user_promotionService业务层处理
 *
 * @author ruoyi
 * @date 2023-06-14
 */
@Service
public class TWxUserPromotionServiceImpl extends ServiceImpl<TWxUserPromotionMapper, TWxUserPromotion> implements ITWxUserPromotionService {
    @Autowired
    private TWxUserPromotionMapper tWxUserPromotionMapper;

    /**
     * 查询用户优惠券 t_wx_user_promotion
     *
     * @param id 用户优惠券 t_wx_user_promotion主键
     * @return 用户优惠券 t_wx_user_promotion
     */
    @Override
    public TWxUserPromotion selectTWxUserPromotionById(Long id) {
        return tWxUserPromotionMapper.selectTWxUserPromotionById(id);
    }

    /**
     * 查询用户优惠券 t_wx_user_promotion列表
     *
     * @param tWxUserPromotion 用户优惠券 t_wx_user_promotion
     * @return 用户优惠券 t_wx_user_promotion
     */
    @Override
    public List<TWxUserPromotion> selectTWxUserPromotionList(TWxUserPromotion tWxUserPromotion) {
        return tWxUserPromotionMapper.selectTWxUserPromotionList(tWxUserPromotion);
    }

    /**
     * 新增用户优惠券 t_wx_user_promotion
     *
     * @param tWxUserPromotion 用户优惠券 t_wx_user_promotion
     * @return 结果
     */
    @Override
    public int insertTWxUserPromotion(TWxUserPromotion tWxUserPromotion) {
        tWxUserPromotion.setCreateTime(DateUtils.getNowDate());
        return tWxUserPromotionMapper.insertTWxUserPromotion(tWxUserPromotion);
    }

    /**
     * 修改用户优惠券 t_wx_user_promotion
     *
     * @param tWxUserPromotion 用户优惠券 t_wx_user_promotion
     * @return 结果
     */
    @Override
    public int updateTWxUserPromotion(TWxUserPromotion tWxUserPromotion) {
        tWxUserPromotion.setUpdateTime(DateUtils.getNowDate());
        return tWxUserPromotionMapper.updateTWxUserPromotion(tWxUserPromotion);
    }

    /**
     * 批量删除用户优惠券 t_wx_user_promotion
     *
     * @param ids 需要删除的用户优惠券 t_wx_user_promotion主键
     * @return 结果
     */
    @Override
    public int deleteTWxUserPromotionByIds(Long[] ids) {
        return tWxUserPromotionMapper.deleteTWxUserPromotionByIds(ids);
    }

    /**
     * 删除用户优惠券 t_wx_user_promotion信息
     *
     * @param id 用户优惠券 t_wx_user_promotion主键
     * @return 结果
     */
    @Override
    public int deleteTWxUserPromotionById(Long id) {
        return tWxUserPromotionMapper.deleteTWxUserPromotionById(id);
    }

    @Autowired
    ITStorePromotionService storePromotionService;

    @Override
    public void receivePromotion(long userId, long promotionId) {
        // 校验是否已经领取过了
        TWxUserPromotion userPromotion = new TWxUserPromotion();
        userPromotion.setCouponId(promotionId);
        userPromotion.setWxId(userId);
        List<TWxUserPromotion> userPromotions = tWxUserPromotionMapper.selectTWxUserPromotionList(userPromotion);
        if (userPromotions.size() > 0) {
            throw new ServiceException("已经领取过该优惠券");
        }
        // copyBeanProperties
        TStorePromotion storePromotion = storePromotionService.selectTStorePromotionById(promotionId);
        BeanUtils.copyProperties(storePromotion, userPromotion);

        // 插入 userPromotion
        tWxUserPromotionMapper.insert(userPromotion);

    }

    @Override
    public List<TWxUserPromotion> selectPayAvailableList(TWxUserPromotion promotion) {
        return tWxUserPromotionMapper.selectPayAvailableList(promotion);
    }
}
