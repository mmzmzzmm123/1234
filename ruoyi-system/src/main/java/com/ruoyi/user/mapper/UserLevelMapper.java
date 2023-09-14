package com.ruoyi.user.mapper;

import java.util.List;
import com.ruoyi.user.domain.UserLevel;

/**
 * 用户等级记录Mapper接口
 *
 * @author Lam
 * @date 2023-09-13
 */
public interface UserLevelMapper {

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
     * 删除用户等级记录
     *
     * @param id 用户等级记录主键
     * @return 结果
     */
    public int deleteUserLevelById(Long id);

    /**
     * 批量删除用户等级记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserLevelByIds(Long[] ids);
}
