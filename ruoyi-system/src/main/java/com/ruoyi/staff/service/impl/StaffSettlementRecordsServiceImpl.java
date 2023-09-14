package com.ruoyi.staff.service.impl;

import java.util.Date;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.staff.mapper.StaffSettlementRecordsMapper;
import com.ruoyi.staff.domain.StaffSettlementRecords;
import com.ruoyi.staff.service.IStaffSettlementRecordsService;

/**
 * 员工结算记录Service业务层处理
 *
 * @author Lam
 * @date 2023-09-14
 */
@Service
@RequiredArgsConstructor
public class StaffSettlementRecordsServiceImpl implements IStaffSettlementRecordsService {

    private final StaffSettlementRecordsMapper staffSettlementRecordsMapper;

    /**
     * 查询员工结算记录
     *
     * @param id 员工结算记录主键
     * @return 员工结算记录
     */
    @Override
    public StaffSettlementRecords selectStaffSettlementRecordsById(Long id) {
        return staffSettlementRecordsMapper.selectStaffSettlementRecordsById(id);
    }

    /**
     * 查询员工结算记录列表
     *
     * @param staffSettlementRecords 员工结算记录
     * @return 员工结算记录
     */
    @Override
    public List<StaffSettlementRecords> selectStaffSettlementRecordsList(StaffSettlementRecords staffSettlementRecords) {
        return staffSettlementRecordsMapper.selectStaffSettlementRecordsList(staffSettlementRecords);
    }

    /**
     * 新增员工结算记录
     *
     * @param staffSettlementRecords 员工结算记录
     * @return 结果
     */
    @Override
    public int insertStaffSettlementRecords(StaffSettlementRecords staffSettlementRecords) {
        Date now = DateUtils.getNowDate();
        String loginName = SecurityUtils.getUsername();
        staffSettlementRecords.setCreateTime(now)
                .setUpdateTime(now)
                .setCreateBy(loginName)
                .setUpdateBy(loginName);
        return staffSettlementRecordsMapper.insertStaffSettlementRecords(staffSettlementRecords);
    }

    /**
     * 修改员工结算记录
     *
     * @param staffSettlementRecords 员工结算记录
     * @return 结果
     */
    @Override
    public int updateStaffSettlementRecords(StaffSettlementRecords staffSettlementRecords) {
        staffSettlementRecords.setUpdateBy(SecurityUtils.getUsername());
        staffSettlementRecords.setUpdateTime(DateUtils.getNowDate());
        return staffSettlementRecordsMapper.updateStaffSettlementRecords(staffSettlementRecords);
    }

    /**
     * 批量删除员工结算记录
     *
     * @param ids 需要删除的员工结算记录主键
     * @return 结果
     */
    @Override
    public int deleteStaffSettlementRecordsByIds(Long[] ids) {
        return staffSettlementRecordsMapper.deleteStaffSettlementRecordsByIds(ids);
    }

    /**
     * 删除员工结算记录信息
     *
     * @param id 员工结算记录主键
     * @return 结果
     */
    @Override
    public int deleteStaffSettlementRecordsById(Long id) {
        return staffSettlementRecordsMapper.deleteStaffSettlementRecordsById(id);
    }
}
