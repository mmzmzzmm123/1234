package com.onethinker.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onethinker.user.domain.PlatformUser;

import java.util.List;

/**
 * 平台用户详情信息Mapper接口
 *
 * @author yangyouqi
 * @date 2023-10-23
 */
public interface PlatformUserMapper extends BaseMapper<PlatformUser> {
    /**
     * 查询平台用户详情信息
     *
     * @param id 平台用户详情信息主键
     * @return 平台用户详情信息
     */
    public PlatformUser selectPlatformUserDetailById(Long id);

    /**
     * 查询平台用户详情信息列表
     *
     * @param platformUser 平台用户详情信息
     * @return 平台用户详情信息集合
     */
    public List<PlatformUser> selectPlatformUserDetailList(PlatformUser platformUser);

    /**
     * 新增平台用户详情信息
     *
     * @param platformUser 平台用户详情信息
     * @return 结果
     */
    public int insertPlatformUserDetail(PlatformUser platformUser);

    /**
     * 修改平台用户详情信息
     *
     * @param platformUser 平台用户详情信息
     * @return 结果
     */
    public int updatePlatformUserDetail(PlatformUser platformUser);

    /**
     * 删除平台用户详情信息
     *
     * @param id 平台用户详情信息主键
     * @return 结果
     */
    public int deletePlatformUserDetailById(Long id);

    /**
     * 批量删除平台用户详情信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlatformUserDetailByIds(Long[] ids);
}
