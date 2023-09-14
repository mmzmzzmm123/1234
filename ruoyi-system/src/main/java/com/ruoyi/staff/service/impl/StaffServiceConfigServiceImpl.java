package com.ruoyi.staff.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.staff.mapper.StaffServiceConfigMapper;
import com.ruoyi.staff.domain.StaffServiceConfig;
import com.ruoyi.staff.service.IStaffServiceConfigService;

/**
 * 员工服务配置Service业务层处理
 *
 * @author Lam
 * @date 2023-09-14
 */
@Service
@RequiredArgsConstructor
public class StaffServiceConfigServiceImpl implements IStaffServiceConfigService {

    private final StaffServiceConfigMapper staffServiceConfigMapper;

    /**
     * 查询员工服务配置
     *
     * @param id 员工服务配置主键
     * @return 员工服务配置
     */
    @Override
    public StaffServiceConfig selectStaffServiceConfigById(Long id) {
        return staffServiceConfigMapper.selectStaffServiceConfigById(id);
    }

    /**
     * 查询员工服务配置列表
     *
     * @param staffServiceConfig 员工服务配置
     * @return 员工服务配置
     */
    @Override
    public List<StaffServiceConfig> selectStaffServiceConfigList(StaffServiceConfig staffServiceConfig) {
        return staffServiceConfigMapper.selectStaffServiceConfigList(staffServiceConfig);
    }

    /**
     * 新增员工服务配置
     *
     * @param staffServiceConfig 员工服务配置
     * @return 结果
     */
    @Override
    public int insertStaffServiceConfig(StaffServiceConfig staffServiceConfig) {
        staffServiceConfig.setCreateTime(DateUtils.getNowDate());
        return staffServiceConfigMapper.insertStaffServiceConfig(staffServiceConfig);
    }

    /**
     * 修改员工服务配置
     *
     * @param staffServiceConfig 员工服务配置
     * @return 结果
     */
    @Override
    public int updateStaffServiceConfig(StaffServiceConfig staffServiceConfig) {
        return staffServiceConfigMapper.updateStaffServiceConfig(staffServiceConfig);
    }

    /**
     * 批量删除员工服务配置
     *
     * @param ids 需要删除的员工服务配置主键
     * @return 结果
     */
    @Override
    public int deleteStaffServiceConfigByIds(Long[] ids) {
        return staffServiceConfigMapper.deleteStaffServiceConfigByIds(ids);
    }

    /**
     * 删除员工服务配置信息
     *
     * @param id 员工服务配置主键
     * @return 结果
     */
    @Override
    public int deleteStaffServiceConfigById(Long id) {
        return staffServiceConfigMapper.deleteStaffServiceConfigById(id);
    }
}
