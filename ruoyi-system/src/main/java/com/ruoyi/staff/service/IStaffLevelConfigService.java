package com.ruoyi.staff.service;

import java.util.List;
import com.ruoyi.staff.domain.StaffLevelConfig;

/**
 * 员工等级配置Service接口
 *
 * @author Lam
 * @date 2023-09-14
 */
public interface IStaffLevelConfigService {

    /**
     * 查询员工等级配置
     *
     * @param id 员工等级配置主键
     * @return 员工等级配置
     */
    public StaffLevelConfig selectStaffLevelConfigById(Long id);

    /**
     * 查询员工等级配置列表
     *
     * @param staffLevelConfig 员工等级配置
     * @return 员工等级配置集合
     */
    public List<StaffLevelConfig> selectStaffLevelConfigList(StaffLevelConfig staffLevelConfig);

    /**
     * 新增员工等级配置
     *
     * @param staffLevelConfig 员工等级配置
     * @return 结果
     */
    public int insertStaffLevelConfig(StaffLevelConfig staffLevelConfig);

    /**
     * 修改员工等级配置
     *
     * @param staffLevelConfig 员工等级配置
     * @return 结果
     */
    public int updateStaffLevelConfig(StaffLevelConfig staffLevelConfig);

    /**
     * 批量删除员工等级配置
     *
     * @param ids 需要删除的员工等级配置主键集合
     * @return 结果
     */
    public int deleteStaffLevelConfigByIds(Long[] ids);

    /**
     * 删除员工等级配置信息
     *
     * @param id 员工等级配置主键
     * @return 结果
     */
    public int deleteStaffLevelConfigById(Long id);
}
