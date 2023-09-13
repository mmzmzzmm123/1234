package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.PlatformNightFeeConfigure;

/**
 * 夜间费用配置Mapper接口
 *
 * @author Lam
 * @date 2023-09-13
 */
public interface PlatformNightFeeConfigureMapper {

    /**
     * 查询夜间费用配置
     *
     * @param id 夜间费用配置主键
     * @return 夜间费用配置
     */
    public PlatformNightFeeConfigure selectPlatformNightFeeConfigureById(Long id);

    /**
     * 查询夜间费用配置列表
     *
     * @param platformNightFeeConfigure 夜间费用配置
     * @return 夜间费用配置集合
     */
    public List<PlatformNightFeeConfigure> selectPlatformNightFeeConfigureList(PlatformNightFeeConfigure platformNightFeeConfigure);

    /**
     * 新增夜间费用配置
     *
     * @param platformNightFeeConfigure 夜间费用配置
     * @return 结果
     */
    public int insertPlatformNightFeeConfigure(PlatformNightFeeConfigure platformNightFeeConfigure);

    /**
     * 修改夜间费用配置
     *
     * @param platformNightFeeConfigure 夜间费用配置
     * @return 结果
     */
    public int updatePlatformNightFeeConfigure(PlatformNightFeeConfigure platformNightFeeConfigure);

    /**
     * 删除夜间费用配置
     *
     * @param id 夜间费用配置主键
     * @return 结果
     */
    public int deletePlatformNightFeeConfigureById(Long id);

    /**
     * 批量删除夜间费用配置
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlatformNightFeeConfigureByIds(Long[] ids);
}
