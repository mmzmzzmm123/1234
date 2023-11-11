package com.ruoyi.staff.mapper;

import java.util.List;
import com.ruoyi.staff.domain.StaffWalletRecord;

/**
 * 钱包佣金记录Mapper接口
 *
 * @author Lam
 * @date 2023-10-10
 */
public interface StaffWalletRecordMapper {

    /**
     * 查询钱包佣金记录
     *
     * @param id 钱包佣金记录主键
     * @return 钱包佣金记录
     */
    public StaffWalletRecord selectStaffWalletRecordById(Long id);

    /**
     * 查询钱包佣金记录列表
     *
     * @param staffWalletRecord 钱包佣金记录
     * @return 钱包佣金记录集合
     */
    public List<StaffWalletRecord> selectStaffWalletRecordList(StaffWalletRecord staffWalletRecord);

    /**
     * 新增钱包佣金记录
     *
     * @param staffWalletRecord 钱包佣金记录
     * @return 结果
     */
    public int insertStaffWalletRecord(StaffWalletRecord staffWalletRecord);

    /**
     * 修改钱包佣金记录
     *
     * @param staffWalletRecord 钱包佣金记录
     * @return 结果
     */
    public int updateStaffWalletRecord(StaffWalletRecord staffWalletRecord);

    /**
     * 删除钱包佣金记录
     *
     * @param id 钱包佣金记录主键
     * @return 结果
     */
    public int deleteStaffWalletRecordById(Long id);

    /**
     * 批量删除钱包佣金记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStaffWalletRecordByIds(Long[] ids);
}
