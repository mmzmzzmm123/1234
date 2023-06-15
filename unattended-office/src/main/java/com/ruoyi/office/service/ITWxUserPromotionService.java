package com.ruoyi.office.service;

import java.util.List;
import com.ruoyi.office.domain.TWxUserPromotion;

/**
 * 用户优惠券 t_wx_user_promotionService接口
 * 
 * @author ruoyi
 * @date 2023-06-14
 */
public interface ITWxUserPromotionService 
{
    /**
     * 查询用户优惠券 t_wx_user_promotion
     * 
     * @param id 用户优惠券 t_wx_user_promotion主键
     * @return 用户优惠券 t_wx_user_promotion
     */
    public TWxUserPromotion selectTWxUserPromotionById(Long id);

    /**
     * 查询用户优惠券 t_wx_user_promotion列表
     * 
     * @param tWxUserPromotion 用户优惠券 t_wx_user_promotion
     * @return 用户优惠券 t_wx_user_promotion集合
     */
    public List<TWxUserPromotion> selectTWxUserPromotionList(TWxUserPromotion tWxUserPromotion);

    /**
     * 新增用户优惠券 t_wx_user_promotion
     * 
     * @param tWxUserPromotion 用户优惠券 t_wx_user_promotion
     * @return 结果
     */
    public int insertTWxUserPromotion(TWxUserPromotion tWxUserPromotion);

    /**
     * 修改用户优惠券 t_wx_user_promotion
     * 
     * @param tWxUserPromotion 用户优惠券 t_wx_user_promotion
     * @return 结果
     */
    public int updateTWxUserPromotion(TWxUserPromotion tWxUserPromotion);

    /**
     * 批量删除用户优惠券 t_wx_user_promotion
     * 
     * @param ids 需要删除的用户优惠券 t_wx_user_promotion主键集合
     * @return 结果
     */
    public int deleteTWxUserPromotionByIds(Long[] ids);

    /**
     * 删除用户优惠券 t_wx_user_promotion信息
     * 
     * @param id 用户优惠券 t_wx_user_promotion主键
     * @return 结果
     */
    public int deleteTWxUserPromotionById(Long id);

    void receivePromotion(long userId, long promotionId);
}
