package com.ruoyi.staff.service.impl;

import java.util.Date;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.staff.mapper.StaffLevelConfigMapper;
import com.ruoyi.staff.domain.StaffLevelConfig;
import com.ruoyi.staff.service.IStaffLevelConfigService;

/**
 * 员工等级配置Service业务层处理
 *
 * @author Lam
 * @date 2023-09-14
 */
@Service
@RequiredArgsConstructor
public class StaffLevelConfigServiceImpl implements IStaffLevelConfigService {

    private final StaffLevelConfigMapper staffLevelConfigMapper;

    /**
     * 查询员工等级配置
     *
     * @param id 员工等级配置主键
     * @return 员工等级配置
     */
    @Override
    public StaffLevelConfig selectStaffLevelConfigById(Long id) {
        return staffLevelConfigMapper.selectStaffLevelConfigById(id);
    }

    /**
     * 查询员工等级配置列表
     *
     * @param staffLevelConfig 员工等级配置
     * @return 员工等级配置
     */
    @Override
    public List<StaffLevelConfig> selectStaffLevelConfigList(StaffLevelConfig staffLevelConfig) {
        return staffLevelConfigMapper.selectStaffLevelConfigList(staffLevelConfig);
    }

    /**
     * 新增员工等级配置
     *
     * @param staffLevelConfig 员工等级配置
     * @return 结果
     */
    @Override
    public int insertStaffLevelConfig(StaffLevelConfig staffLevelConfig) {
        Date now = DateUtils.getNowDate();
        String loginName = SecurityUtils.getUsername();
        staffLevelConfig.setCreateTime(now)
                .setUpdateTime(now)
                .setCreateBy(loginName)
                .setUpdateBy(loginName);
        return staffLevelConfigMapper.insertStaffLevelConfig(staffLevelConfig);
    }

    /**
     * 修改员工等级配置
     *
     * @param staffLevelConfig 员工等级配置
     * @return 结果
     */
    @Override
    public int updateStaffLevelConfig(StaffLevelConfig staffLevelConfig) {
        staffLevelConfig.setUpdateBy(SecurityUtils.getUsername())
                .setUpdateTime(DateUtils.getNowDate());
        return staffLevelConfigMapper.updateStaffLevelConfig(staffLevelConfig);
    }

    /**
     * 批量删除员工等级配置
     *
     * @param ids 需要删除的员工等级配置主键
     * @return 结果
     */
    @Override
    public int deleteStaffLevelConfigByIds(Long[] ids) {
        return staffLevelConfigMapper.deleteStaffLevelConfigByIds(ids);
    }

    /**
     * 删除员工等级配置信息
     *
     * @param id 员工等级配置主键
     * @return 结果
     */
    @Override
    public int deleteStaffLevelConfigById(Long id) {
        return staffLevelConfigMapper.deleteStaffLevelConfigById(id);
    }
}
