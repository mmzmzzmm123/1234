package com.stdiet.custom.mapper;

import java.util.List;
import com.stdiet.custom.domain.SysSalaryComposeConfig;

/**
 * 工资比例配置Mapper接口
 *
 * @author xiezhijun
 * @date 2021-03-13
 */
public interface SysSalaryComposeConfigMapper
{
    /**
     * 查询工资比例配置
     *
     * @param id 工资比例配置ID
     * @return 工资比例配置
     */
    public SysSalaryComposeConfig selectSysSalaryComposeConfigById(Long id);

    /**
     * 查询工资比例配置列表
     *
     * @param sysSalaryComposeConfig 工资比例配置
     * @return 工资比例配置集合
     */
    public List<SysSalaryComposeConfig> selectSysSalaryComposeConfigList(SysSalaryComposeConfig sysSalaryComposeConfig);

    /**
     * 新增工资比例配置
     *
     * @param sysSalaryComposeConfig 工资比例配置
     * @return 结果
     */
    public int insertSysSalaryComposeConfig(SysSalaryComposeConfig sysSalaryComposeConfig);

    /**
     * 修改工资比例配置
     *
     * @param sysSalaryComposeConfig 工资比例配置
     * @return 结果
     */
    public int updateSysSalaryComposeConfig(SysSalaryComposeConfig sysSalaryComposeConfig);

    /**
     * 删除工资比例配置
     *
     * @param id 工资比例配置ID
     * @return 结果
     */
    public int deleteSysSalaryComposeConfigById(Long id);

    /**
     * 批量删除工资比例配置
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysSalaryComposeConfigByIds(Long[] ids);
}