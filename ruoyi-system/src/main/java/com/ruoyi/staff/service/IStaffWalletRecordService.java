package com.ruoyi.staff.service;

import java.util.List;
import com.ruoyi.staff.domain.StaffWalletRecord;

/**
 * 钱包佣金记录Service接口
 *
 * @author Lam
 * @date 2023-10-10
 */
public interface IStaffWalletRecordService {

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
     * 批量删除钱包佣金记录
     *
     * @param ids 需要删除的钱包佣金记录主键集合
     * @return 结果
     */
    public int deleteStaffWalletRecordByIds(Long[] ids);

    /**
     * 删除钱包佣金记录信息
     *
     * @param id 钱包佣金记录主键
     * @return 结果
     */
    public int deleteStaffWalletRecordById(Long id);
}
