package com.ruoyi.user.service.impl;

import java.util.Date;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.user.mapper.UserLevelConfigMapper;
import com.ruoyi.user.domain.UserLevelConfig;
import com.ruoyi.user.service.IUserLevelConfigService;

/**
 * 用户等级配置Service业务层处理
 *
 * @author Lam
 * @date 2023-09-13
 */
@Service
@RequiredArgsConstructor
public class UserLevelConfigServiceImpl implements IUserLevelConfigService {

    private final UserLevelConfigMapper userLevelConfigMapper;

    /**
     * 查询用户等级配置
     *
     * @param id 用户等级配置主键
     * @return 用户等级配置
     */
    @Override
    public UserLevelConfig selectUserLevelConfigById(Long id) {
        return userLevelConfigMapper.selectUserLevelConfigById(id);
    }

    /**
     * 查询用户等级配置列表
     *
     * @param userLevelConfig 用户等级配置
     * @return 用户等级配置
     */
    @Override
    public List<UserLevelConfig> selectUserLevelConfigList(UserLevelConfig userLevelConfig) {
        return userLevelConfigMapper.selectUserLevelConfigList(userLevelConfig);
    }

    /**
     * 新增用户等级配置
     *
     * @param userLevelConfig 用户等级配置
     * @return 结果
     */
    @Override
    public int insertUserLevelConfig(UserLevelConfig userLevelConfig) {
        String loginUser = SecurityUtils.getUsername();
        Date now = DateUtils.getNowDate();
        userLevelConfig.setCreateTime(now)
                .setUpdateTime(now)
                .setUpdateBy(loginUser)
                .setCreateBy(loginUser);
        return userLevelConfigMapper.insertUserLevelConfig(userLevelConfig);
    }

    /**
     * 修改用户等级配置
     *
     * @param userLevelConfig 用户等级配置
     * @return 结果
     */
    @Override
    public int updateUserLevelConfig(UserLevelConfig userLevelConfig) {
        userLevelConfig.setUpdateBy(SecurityUtils.getUsername());
        userLevelConfig.setUpdateTime(DateUtils.getNowDate());
        return userLevelConfigMapper.updateUserLevelConfig(userLevelConfig);
    }

    /**
     * 批量删除用户等级配置
     *
     * @param ids 需要删除的用户等级配置主键
     * @return 结果
     */
    @Override
    public int deleteUserLevelConfigByIds(Long[] ids) {
        return userLevelConfigMapper.deleteUserLevelConfigByIds(ids);
    }

    /**
     * 删除用户等级配置信息
     *
     * @param id 用户等级配置主键
     * @return 结果
     */
    @Override
    public int deleteUserLevelConfigById(Long id) {
        return userLevelConfigMapper.deleteUserLevelConfigById(id);
    }
}
