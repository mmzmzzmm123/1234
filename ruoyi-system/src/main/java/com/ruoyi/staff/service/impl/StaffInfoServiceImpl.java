package com.ruoyi.staff.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.staff.mapper.StaffInfoMapper;
import com.ruoyi.staff.domain.StaffInfo;
import com.ruoyi.staff.service.IStaffInfoService;

/**
 * 员工信息Service业务层处理
 *
 * @author Lam
 * @date 2023-09-14
 */
@Service
@RequiredArgsConstructor
public class StaffInfoServiceImpl implements IStaffInfoService {

    private final StaffInfoMapper staffInfoMapper;

    /**
     * 查询员工信息
     *
     * @param userId 员工信息主键
     * @return 员工信息
     */
    @Override
    public StaffInfo selectStaffInfoByUserId(Long userId) {
        return staffInfoMapper.selectStaffInfoByUserId(userId);
    }

    /**
     * 查询员工信息列表
     *
     * @param staffInfo 员工信息
     * @return 员工信息
     */
    @Override
    public List<StaffInfo> selectStaffInfoList(StaffInfo staffInfo) {
        return staffInfoMapper.selectStaffInfoList(staffInfo);
    }

    /**
     * 新增员工信息
     *
     * @param staffInfo 员工信息
     * @return 结果
     */
    @Override
    public int insertStaffInfo(StaffInfo staffInfo) {
        Date now = DateUtils.getNowDate();
        staffInfo.setCreateTime(now)
                .setUpdateTime(now);
        return staffInfoMapper.insertStaffInfo(staffInfo);
    }

    /**
     * 修改员工信息
     *
     * @param staffInfo 员工信息
     * @return 结果
     */
    @Override
    public int updateStaffInfo(StaffInfo staffInfo) {
        staffInfo.setUpdateTime(DateUtils.getNowDate());
        return staffInfoMapper.updateStaffInfo(staffInfo);
    }

    /**
     * 批量删除员工信息
     *
     * @param userIds 需要删除的员工信息主键
     * @return 结果
     */
    @Override
    public int deleteStaffInfoByUserIds(Long[] userIds) {
        return staffInfoMapper.deleteStaffInfoByUserIds(userIds);
    }

    /**
     * 删除员工信息信息
     *
     * @param userId 员工信息主键
     * @return 结果
     */
    @Override
    public int deleteStaffInfoByUserId(Long userId) {
        return staffInfoMapper.deleteStaffInfoByUserId(userId);
    }
}
