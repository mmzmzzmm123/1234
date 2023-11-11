package com.ruoyi.user.service.impl;

import java.util.Date;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.user.mapper.UserLevelMapper;
import com.ruoyi.user.domain.UserLevel;
import com.ruoyi.user.service.IUserLevelService;

/**
 * 用户等级记录Service业务层处理
 *
 * @author Lam
 * @date 2023-09-13
 */
@Service
@RequiredArgsConstructor
public class UserLevelServiceImpl implements IUserLevelService {

    private final UserLevelMapper userLevelMapper;

    /**
     * 查询用户等级记录
     *
     * @param id 用户等级记录主键
     * @return 用户等级记录
     */
    @Override
    public UserLevel selectUserLevelById(Long id) {
        return userLevelMapper.selectUserLevelById(id);
    }

    /**
     * 查询用户等级记录列表
     *
     * @param userLevel 用户等级记录
     * @return 用户等级记录
     */
    @Override
    public List<UserLevel> selectUserLevelList(UserLevel userLevel) {
        return userLevelMapper.selectUserLevelList(userLevel);
    }

    /**
     * 新增用户等级记录
     *
     * @param userLevel 用户等级记录
     * @return 结果
     */
    @Override
    public int insertUserLevel(UserLevel userLevel) {
        Date now = DateUtils.getNowDate();
        userLevel.setCreateTime(now)
                .setUpdateTime(now);
        return userLevelMapper.insertUserLevel(userLevel);
    }

    /**
     * 修改用户等级记录
     *
     * @param userLevel 用户等级记录
     * @return 结果
     */
    @Override
    public int updateUserLevel(UserLevel userLevel) {
        userLevel.setUpdateTime(DateUtils.getNowDate());
        return userLevelMapper.updateUserLevel(userLevel);
    }

    /**
     * 批量删除用户等级记录
     *
     * @param ids 需要删除的用户等级记录主键
     * @return 结果
     */
    @Override
    public int deleteUserLevelByIds(Long[] ids) {
        return userLevelMapper.deleteUserLevelByIds(ids);
    }

    /**
     * 删除用户等级记录信息
     *
     * @param id 用户等级记录主键
     * @return 结果
     */
    @Override
    public int deleteUserLevelById(Long id) {
        return userLevelMapper.deleteUserLevelById(id);
    }
}
