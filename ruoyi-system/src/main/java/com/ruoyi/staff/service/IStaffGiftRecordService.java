package com.ruoyi.staff.service;

import java.util.List;
import com.ruoyi.staff.domain.StaffGiftRecord;

/**
 * 礼物记录Service接口
 *
 * @author Lam
 * @date 2023-10-09
 */
public interface IStaffGiftRecordService {

    /**
     * 查询礼物记录
     *
     * @param id 礼物记录主键
     * @return 礼物记录
     */
    public StaffGiftRecord selectStaffGiftRecordById(Long id);

    /**
     * 查询礼物记录列表
     *
     * @param staffGiftRecord 礼物记录
     * @return 礼物记录集合
     */
    public List<StaffGiftRecord> selectStaffGiftRecordList(StaffGiftRecord staffGiftRecord);

    /**
     * 新增礼物记录
     *
     * @param staffGiftRecord 礼物记录
     * @return 结果
     */
    public int insertStaffGiftRecord(StaffGiftRecord staffGiftRecord);

    /**
     * 修改礼物记录
     *
     * @param staffGiftRecord 礼物记录
     * @return 结果
     */
    public int updateStaffGiftRecord(StaffGiftRecord staffGiftRecord);

    /**
     * 批量删除礼物记录
     *
     * @param ids 需要删除的礼物记录主键集合
     * @return 结果
     */
    public int deleteStaffGiftRecordByIds(Long[] ids);

    /**
     * 删除礼物记录信息
     *
     * @param id 礼物记录主键
     * @return 结果
     */
    public int deleteStaffGiftRecordById(Long id);
}
