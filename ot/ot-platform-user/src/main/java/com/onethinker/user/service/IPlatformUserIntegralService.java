package com.onethinker.user.service;

import com.onethinker.common.enums.IntegralTypeEnum;
import com.onethinker.user.domain.PlatformUserIntegral;
import com.onethinker.user.domain.PlatformUserIntegralHistory;

/**
 * @author yangyouqi
 * @date 2024/5/28
 */
public interface IPlatformUserIntegralService {

    /**
     * 添加积分
     * @param dataId 用户dataId
     * @param integral 积分
     * @param integralType 处理类型
     * @return 是否添加成功
     */
    boolean addIntegral(String dataId, Long integral, IntegralTypeEnum integralType);

    /**
     * 添加积分
     * @param dataId 用户dataId
     * @param integral 积分
     * @param activityId 活动id
     * @param integralType 处理类型
     * @return 是否添加成功
     */
    boolean addIntegral(String dataId,Long integral,String activityId,IntegralTypeEnum integralType);

    /**
     * 扣除积分
     * @param dataId 用户dataId
     * @param integral 积分
     * @param integralType 处理类型
     * @return 是否扣除积分成功
     */
    boolean deductIntegral(String dataId,Long integral, IntegralTypeEnum integralType);

    /**
     * 扣除积分
     * @param dataId 用户dataId
     * @param integral 积分
     * @param activityId 活动id
     * @param integralType 处理类型
     * @return 是否扣除积分成功
     */
    boolean deductIntegral(String dataId,Long integral,String activityId,IntegralTypeEnum integralType);

    /**
     * 获取用户积分信息
     * @param dataId 用户dataId
     * @return
     */
    PlatformUserIntegral getIntegral(String dataId);

    /**
     * 获取用户对应活动积分
     * @param dataId 用户dataId
     * @param activityId 活动id
     * @return
     */
    PlatformUserIntegral getIntegral(String dataId,String activityId);

    /**
     * 查看明细
     * @param dataId 用户dataId
     * @return
     */
    PlatformUserIntegralHistory getIntegralHistory(String dataId);

    /**
     * 查看明细
     * @param dataId 用户dataId
     * @param activityId 活动id
     * @return
     */
    PlatformUserIntegralHistory getIntegralHistory(String dataId,String activityId);
}
