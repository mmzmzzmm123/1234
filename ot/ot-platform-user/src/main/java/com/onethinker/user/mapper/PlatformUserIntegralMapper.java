package com.onethinker.user.mapper;

import com.onethinker.user.domain.PlatformUserIntegral;

import java.util.List;

/**
 * 平台用户积分Mapper接口
 *
 * @author yangyouqi
 * @date 2023-10-27
 */
public interface PlatformUserIntegralMapper {
    /**
     * 查询平台用户积分
     *
     * @param id 平台用户积分主键
     * @return 平台用户积分
     */
    public PlatformUserIntegral selectPlatformUserIntegralById(Long id);

    /**
     * 查询平台用户积分列表
     *
     * @param platformUserIntegral 平台用户积分
     * @return 平台用户积分集合
     */
    public List<PlatformUserIntegral> selectPlatformUserIntegralList(PlatformUserIntegral platformUserIntegral);

    /**
     * 新增平台用户积分
     *
     * @param platformUserIntegral 平台用户积分
     * @return 结果
     */
    public int insertPlatformUserIntegral(PlatformUserIntegral platformUserIntegral);

    /**
     * 修改平台用户积分
     *
     * @param platformUserIntegral 平台用户积分
     * @return 结果
     */
    public int updatePlatformUserIntegral(PlatformUserIntegral platformUserIntegral);

    /**
     * 删除平台用户积分
     *
     * @param id 平台用户积分主键
     * @return 结果
     */
    public int deletePlatformUserIntegralById(Long id);

    /**
     * 批量删除平台用户积分
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlatformUserIntegralByIds(Long[] ids);

    /**
     * 更新用户积分信息
     *
     * @param params         请求参数
     * @return 结果
     */
    int updateIntegralByResidualIntegralAndId(PlatformUserIntegral params);
}
