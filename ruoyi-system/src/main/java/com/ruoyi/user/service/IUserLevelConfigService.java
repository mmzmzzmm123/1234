package com.ruoyi.user.service;

import java.util.List;
import com.ruoyi.user.domain.UserLevelConfig;

/**
 * 用户等级配置Service接口
 *
 * @author Lam
 * @date 2023-09-13
 */
public interface IUserLevelConfigService {

    /**
     * 查询用户等级配置
     *
     * @param id 用户等级配置主键
     * @return 用户等级配置
     */
    public UserLevelConfig selectUserLevelConfigById(Long id);

    /**
     * 查询用户等级配置列表
     *
     * @param userLevelConfig 用户等级配置
     * @return 用户等级配置集合
     */
    public List<UserLevelConfig> selectUserLevelConfigList(UserLevelConfig userLevelConfig);

    /**
     * 新增用户等级配置
     *
     * @param userLevelConfig 用户等级配置
     * @return 结果
     */
    public int insertUserLevelConfig(UserLevelConfig userLevelConfig);

    /**
     * 修改用户等级配置
     *
     * @param userLevelConfig 用户等级配置
     * @return 结果
     */
    public int updateUserLevelConfig(UserLevelConfig userLevelConfig);

    /**
     * 批量删除用户等级配置
     *
     * @param ids 需要删除的用户等级配置主键集合
     * @return 结果
     */
    public int deleteUserLevelConfigByIds(Long[] ids);

    /**
     * 删除用户等级配置信息
     *
     * @param id 用户等级配置主键
     * @return 结果
     */
    public int deleteUserLevelConfigById(Long id);
}
