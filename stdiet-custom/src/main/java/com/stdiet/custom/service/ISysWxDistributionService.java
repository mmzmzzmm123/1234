package com.stdiet.custom.service;

import java.util.List;
import com.stdiet.custom.domain.SysWxDistribution;

/**
 * 微信分配管理Service接口
 *
 * @author xiezhijun
 * @date 2021-02-03
 */
public interface ISysWxDistributionService
{
    /**
     * 查询微信分配管理
     *
     * @param id 微信分配管理ID
     * @return 微信分配管理
     */
    public SysWxDistribution selectSysWxDistributionById(Long id);

    /**
     * 查询微信分配管理列表
     *
     * @param sysWxDistribution 微信分配管理
     * @return 微信分配管理集合
     */
    public List<SysWxDistribution> selectSysWxDistributionList(SysWxDistribution sysWxDistribution);

    /**
     * 新增微信分配管理
     *
     * @param sysWxDistribution 微信分配管理
     * @return 结果
     */
    public int insertSysWxDistribution(SysWxDistribution sysWxDistribution);

    /**
     * 修改微信分配管理
     *
     * @param sysWxDistribution 微信分配管理
     * @return 结果
     */
    public int updateSysWxDistribution(SysWxDistribution sysWxDistribution);

    /**
     * 批量删除微信分配管理
     *
     * @param ids 需要删除的微信分配管理ID
     * @return 结果
     */
    public int deleteSysWxDistributionByIds(Long[] ids);

    /**
     * 删除微信分配管理信息
     *
     * @param id 微信分配管理ID
     * @return 结果
     */
    public int deleteSysWxDistributionById(Long id);

    /**
     * 根据微信ID查询是否分配记录
     * @param wxId
     * @return
     */
    SysWxDistribution selectWxDistributionByWxId(Long wxId);

    /**
     * 根据用户ID查询该用户被分配的微信号
     * @param userId
     * @return
     */
    List<SysWxDistribution> selectDistributionWxByUserId(Long userId);
}