package com.ruoyi.user.service;

import java.util.List;
import com.ruoyi.user.domain.UserLevel;

/**
 * 用户等级记录Service接口
 *
 * @author Lam
 * @date 2023-09-13
 */
public interface IUserLevelService {

    /**
     * 查询用户等级记录
     *
     * @param id 用户等级记录主键
     * @return 用户等级记录
     */
    public UserLevel selectUserLevelById(Long id);

    /**
     * 查询用户等级记录列表
     *
     * @param userLevel 用户等级记录
     * @return 用户等级记录集合
     */
    public List<UserLevel> selectUserLevelList(UserLevel userLevel);

    /**
     * 新增用户等级记录
     *
     * @param userLevel 用户等级记录
     * @return 结果
     */
    public int insertUserLevel(UserLevel userLevel);

    /**
     * 修改用户等级记录
     *
     * @param userLevel 用户等级记录
     * @return 结果
     */
    public int updateUserLevel(UserLevel userLevel);

    /**
     * 批量删除用户等级记录
     *
     * @param ids 需要删除的用户等级记录主键集合
     * @return 结果
     */
    public int deleteUserLevelByIds(Long[] ids);

    /**
     * 删除用户等级记录信息
     *
     * @param id 用户等级记录主键
     * @return 结果
     */
    public int deleteUserLevelById(Long id);
}
