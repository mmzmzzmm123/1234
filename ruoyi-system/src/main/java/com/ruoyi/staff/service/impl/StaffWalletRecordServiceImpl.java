package com.ruoyi.staff.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.staff.mapper.StaffWalletRecordMapper;
import com.ruoyi.staff.domain.StaffWalletRecord;
import com.ruoyi.staff.service.IStaffWalletRecordService;

/**
 * 钱包佣金记录Service业务层处理
 *
 * @author Lam
 * @date 2023-10-10
 */
@Service
@RequiredArgsConstructor
public class StaffWalletRecordServiceImpl implements IStaffWalletRecordService {

    private final StaffWalletRecordMapper staffWalletRecordMapper;

    /**
     * 查询钱包佣金记录
     *
     * @param id 钱包佣金记录主键
     * @return 钱包佣金记录
     */
    @Override
    public StaffWalletRecord selectStaffWalletRecordById(Long id) {
        return staffWalletRecordMapper.selectStaffWalletRecordById(id);
    }

    /**
     * 查询钱包佣金记录列表
     *
     * @param staffWalletRecord 钱包佣金记录
     * @return 钱包佣金记录
     */
    @Override
    public List<StaffWalletRecord> selectStaffWalletRecordList(StaffWalletRecord staffWalletRecord) {
        return staffWalletRecordMapper.selectStaffWalletRecordList(staffWalletRecord);
    }

    /**
     * 新增钱包佣金记录
     *
     * @param staffWalletRecord 钱包佣金记录
     * @return 结果
     */
    @Override
    public int insertStaffWalletRecord(StaffWalletRecord staffWalletRecord) {
        staffWalletRecord.setCreateTime(DateUtils.getNowDate());
        return staffWalletRecordMapper.insertStaffWalletRecord(staffWalletRecord);
    }

    /**
     * 修改钱包佣金记录
     *
     * @param staffWalletRecord 钱包佣金记录
     * @return 结果
     */
    @Override
    public int updateStaffWalletRecord(StaffWalletRecord staffWalletRecord) {
        staffWalletRecord.setUpdateBy(SecurityUtils.getUsername());
        staffWalletRecord.setUpdateTime(DateUtils.getNowDate());
        return staffWalletRecordMapper.updateStaffWalletRecord(staffWalletRecord);
    }

    /**
     * 批量删除钱包佣金记录
     *
     * @param ids 需要删除的钱包佣金记录主键
     * @return 结果
     */
    @Override
    public int deleteStaffWalletRecordByIds(Long[] ids) {
        return staffWalletRecordMapper.deleteStaffWalletRecordByIds(ids);
    }

    /**
     * 删除钱包佣金记录信息
     *
     * @param id 钱包佣金记录主键
     * @return 结果
     */
    @Override
    public int deleteStaffWalletRecordById(Long id) {
        return staffWalletRecordMapper.deleteStaffWalletRecordById(id);
    }
}
