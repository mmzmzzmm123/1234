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
     * 根据服务id集合查询对应的数据
     *
     * @param serviceIdList 服务标识集合
     * @return 结果
     * */
    public List<StaffServiceConfig> selectByServiceIds(List<Long> serviceIdList);

    /**
     * 根据店员id查询服务id集合
     *
     * @param staffId 店员标识
     * @return 结果
     * */
    public List<Long> selectServiceIdByStaffId(Long staffId);

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
