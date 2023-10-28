package com.ruoyi.onethinker.mapper;

import java.util.List;

import com.ruoyi.onethinker.domain.PlatformUser;

/**
 * 平台用户Mapper接口
 *
 * @author yangyouqi
 * @date 2023-10-23
 */
public interface PlatformUserMapper {
    /**
     * 查询平台用户
     *
     * @param id 平台用户主键
     * @return 平台用户
     */
    public PlatformUser selectPlatformUserById(Long id);

    /**
     * 查询平台用户列表
     *
     * @param platformUser 平台用户
     * @return 平台用户集合
     */
    public List<PlatformUser> selectPlatformUserList(PlatformUser platformUser);

    /**
     * 新增平台用户
     *
     * @param platformUser 平台用户
     * @return 结果
     */
    public int insertPlatformUser(PlatformUser platformUser);

    /**
     * 修改平台用户
     *
     * @param platformUser 平台用户
     * @return 结果
     */
    public int updatePlatformUser(PlatformUser platformUser);

    /**
     * 删除平台用户
     *
     * @param id 平台用户主键
     * @return 结果
     */
    public int deletePlatformUserById(Long id);

    /**
     * 批量删除平台用户
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlatformUserByIds(Long[] ids);

    /**
     * 批量查询用户信息
     * @param puUserIds
     * @return
     */
    List<PlatformUser> selectPlatformUserByIds(List<Long> puUserIds);
}
