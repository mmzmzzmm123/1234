package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.PlatformRechargeConfig;

/**
 * 平台充值配置Mapper接口
 *
 * @author Lam
 * @date 2023-10-04
 */
public interface PlatformRechargeConfigMapper {

    /**
     * 查询平台充值配置
     *
     * @param id 平台充值配置主键
     * @return 平台充值配置
     */
    public PlatformRechargeConfig selectPlatformRechargeConfigById(Long id);

    /**
     * 查询平台充值配置列表
     *
     * @param platformRechargeConfig 平台充值配置
     * @return 平台充值配置集合
     */
    public List<PlatformRechargeConfig> selectPlatformRechargeConfigList(PlatformRechargeConfig platformRechargeConfig);

    /**
     * 新增平台充值配置
     *
     * @param platformRechargeConfig 平台充值配置
     * @return 结果
     */
    public int insertPlatformRechargeConfig(PlatformRechargeConfig platformRechargeConfig);

    /**
     * 修改平台充值配置
     *
     * @param platformRechargeConfig 平台充值配置
     * @return 结果
     */
    public int updatePlatformRechargeConfig(PlatformRechargeConfig platformRechargeConfig);

    /**
     * 删除平台充值配置
     *
     * @param id 平台充值配置主键
     * @return 结果
     */
    public int deletePlatformRechargeConfigById(Long id);

    /**
     * 批量删除平台充值配置
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlatformRechargeConfigByIds(Long[] ids);
}
