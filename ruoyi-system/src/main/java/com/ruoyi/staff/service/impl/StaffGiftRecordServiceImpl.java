package com.ruoyi.staff.service.impl;

import java.util.Date;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.staff.mapper.StaffGiftRecordMapper;
import com.ruoyi.staff.domain.StaffGiftRecord;
import com.ruoyi.staff.service.IStaffGiftRecordService;

/**
 * 员工礼物记录Service业务层处理
 *
 * @author Lam
 * @date 2023-09-14
 */
@Service
@RequiredArgsConstructor
public class StaffGiftRecordServiceImpl implements IStaffGiftRecordService {

    private final StaffGiftRecordMapper staffGiftRecordMapper;

    /**
     * 查询员工礼物记录
     *
     * @param id 员工礼物记录主键
     * @return 员工礼物记录
     */
    @Override
    public StaffGiftRecord selectStaffGiftRecordById(Long id) {
        return staffGiftRecordMapper.selectStaffGiftRecordById(id);
    }

    /**
     * 查询员工礼物记录列表
     *
     * @param staffGiftRecord 员工礼物记录
     * @return 员工礼物记录
     */
    @Override
    public List<StaffGiftRecord> selectStaffGiftRecordList(StaffGiftRecord staffGiftRecord) {
        return staffGiftRecordMapper.selectStaffGiftRecordList(staffGiftRecord);
    }

    /**
     * 新增员工礼物记录
     *
     * @param staffGiftRecord 员工礼物记录
     * @return 结果
     */
    @Override
    public int insertStaffGiftRecord(StaffGiftRecord staffGiftRecord) {
        Date now = DateUtils.getNowDate();
        staffGiftRecord.setCreateTime(now).setUpdateTime(now);
        return staffGiftRecordMapper.insertStaffGiftRecord(staffGiftRecord);
    }

    /**
     * 修改员工礼物记录
     *
     * @param staffGiftRecord 员工礼物记录
     * @return 结果
     */
    @Override
    public int updateStaffGiftRecord(StaffGiftRecord staffGiftRecord) {
        staffGiftRecord.setUpdateTime(DateUtils.getNowDate());
        return staffGiftRecordMapper.updateStaffGiftRecord(staffGiftRecord);
    }

    /**
     * 批量删除员工礼物记录
     *
     * @param ids 需要删除的员工礼物记录主键
     * @return 结果
     */
    @Override
    public int deleteStaffGiftRecordByIds(Long[] ids) {
        return staffGiftRecordMapper.deleteStaffGiftRecordByIds(ids);
    }

    /**
     * 删除员工礼物记录信息
     *
     * @param id 员工礼物记录主键
     * @return 结果
     */
    @Override
    public int deleteStaffGiftRecordById(Long id) {
        return staffGiftRecordMapper.deleteStaffGiftRecordById(id);
    }
}
