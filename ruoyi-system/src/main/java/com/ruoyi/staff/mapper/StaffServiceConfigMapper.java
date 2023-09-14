package com.ruoyi.staff.mapper;

import java.util.List;
import com.ruoyi.staff.domain.StaffServiceConfig;

/**
 * 员工服务配置Mapper接口
 *
 * @author Lam
 * @date 2023-09-14
 */
public interface StaffServiceConfigMapper {

    /**
     * 查询员工服务配置
     *
     * @param id 员工服务配置主键
     * @return 员工服务配置
     */
    public StaffServiceConfig selectStaffServiceConfigById(Long id);

    /**
     * 查询员工服务配置列表
     *
     * @param staffServiceConfig 员工服务配置
     * @return 员工服务配置集合
     */
    public List<StaffServiceConfig> selectStaffServiceConfigList(StaffServiceConfig staffServiceConfig);

    /**
     * 新增员工服务配置
     *
     * @param staffServiceConfig 员工服务配置
     * @return 结果
     */
    public int insertStaffServiceConfig(StaffServiceConfig staffServiceConfig);

    /**
     * 修改员工服务配置
     *
     * @param staffServiceConfig 员工服务配置
     * @return 结果
     */
    public int updateStaffServiceConfig(StaffServiceConfig staffServiceConfig);

    /**
     * 删除员工服务配置
     *
     * @param id 员工服务配置主键
     * @return 结果
     */
    public int deleteStaffServiceConfigById(Long id);

    /**
     * 批量删除员工服务配置
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStaffServiceConfigByIds(Long[] ids);
}
