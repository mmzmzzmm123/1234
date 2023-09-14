package com.ruoyi.staff.mapper;

import java.util.List;
import com.ruoyi.staff.domain.StaffGiftRecord;

/**
 * 员工礼物记录Mapper接口
 *
 * @author Lam
 * @date 2023-09-14
 */
public interface StaffGiftRecordMapper {

    /**
     * 查询员工礼物记录
     *
     * @param id 员工礼物记录主键
     * @return 员工礼物记录
     */
    public StaffGiftRecord selectStaffGiftRecordById(Long id);

    /**
     * 查询员工礼物记录列表
     *
     * @param staffGiftRecord 员工礼物记录
     * @return 员工礼物记录集合
     */
    public List<StaffGiftRecord> selectStaffGiftRecordList(StaffGiftRecord staffGiftRecord);

    /**
     * 新增员工礼物记录
     *
     * @param staffGiftRecord 员工礼物记录
     * @return 结果
     */
    public int insertStaffGiftRecord(StaffGiftRecord staffGiftRecord);

    /**
     * 修改员工礼物记录
     *
     * @param staffGiftRecord 员工礼物记录
     * @return 结果
     */
    public int updateStaffGiftRecord(StaffGiftRecord staffGiftRecord);

    /**
     * 删除员工礼物记录
     *
     * @param id 员工礼物记录主键
     * @return 结果
     */
    public int deleteStaffGiftRecordById(Long id);

    /**
     * 批量删除员工礼物记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStaffGiftRecordByIds(Long[] ids);
}
