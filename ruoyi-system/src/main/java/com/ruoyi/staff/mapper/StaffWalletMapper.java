package com.ruoyi.staff.mapper;

import java.util.List;
import com.ruoyi.staff.domain.StaffWallet;
import com.ruoyi.staff.domain.StaffWalletRecord;

/**
 * 员工钱包Mapper接口
 *
 * @author Lam
 * @date 2023-09-14
 */
public interface StaffWalletMapper {

    /**
     * 查询员工钱包
     *
     * @param id 员工钱包主键
     * @return 员工钱包
     */
    public StaffWallet selectStaffWalletById(Long id);

    /**
     * 根据用户标识查询记录
     * @param userId 用户标识
     * @return 结果
     * */
    public StaffWallet selectByUserIdForUpdate(Long userId);

    /**
     * 查询员工钱包列表
     *
     * @param staffWallet 员工钱包
     * @return 员工钱包集合
     */
    public List<StaffWallet> selectStaffWalletList(StaffWallet staffWallet);

    /**
     * 新增员工钱包
     *
     * @param staffWallet 员工钱包
     * @return 结果
     */
    public int insertStaffWallet(StaffWallet staffWallet);

    /**
     * 修改员工钱包
     *
     * @param staffWallet 员工钱包
     * @return 结果
     */
    public int updateStaffWallet(StaffWallet staffWallet);

    /**
     * 删除员工钱包
     *
     * @param id 员工钱包主键
     * @return 结果
     */
    public int deleteStaffWalletById(Long id);

    /**
     * 批量删除员工钱包
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStaffWalletByIds(Long[] ids);
}
