package com.ruoyi.staff.service.impl;

import java.util.Date;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.staff.mapper.StaffWalletMapper;
import com.ruoyi.staff.domain.StaffWallet;
import com.ruoyi.staff.service.IStaffWalletService;

/**
 * 员工钱包Service业务层处理
 *
 * @author Lam
 * @date 2023-09-14
 */
@Service
@RequiredArgsConstructor
public class StaffWalletServiceImpl implements IStaffWalletService {

    private final StaffWalletMapper staffWalletMapper;

    /**
     * 查询员工钱包
     *
     * @param id 员工钱包主键
     * @return 员工钱包
     */
    @Override
    public StaffWallet selectStaffWalletById(Long id) {
        return staffWalletMapper.selectStaffWalletById(id);
    }

    /**
     * 查询员工钱包列表
     *
     * @param staffWallet 员工钱包
     * @return 员工钱包
     */
    @Override
    public List<StaffWallet> selectStaffWalletList(StaffWallet staffWallet) {
        return staffWalletMapper.selectStaffWalletList(staffWallet);
    }

    /**
     * 新增员工钱包
     *
     * @param staffWallet 员工钱包
     * @return 结果
     */
    @Override
    public int insertStaffWallet(StaffWallet staffWallet) {
        Date now = DateUtils.getNowDate();
        staffWallet.setCreateTime(now)
                .setUpdateTime(now);
        return staffWalletMapper.insertStaffWallet(staffWallet);
    }

    /**
     * 修改员工钱包
     *
     * @param staffWallet 员工钱包
     * @return 结果
     */
    @Override
    public int updateStaffWallet(StaffWallet staffWallet) {
        staffWallet.setUpdateBy(SecurityUtils.getUsername()).setUpdateTime(DateUtils.getNowDate());
        return staffWalletMapper.updateStaffWallet(staffWallet);
    }

    /**
     * 批量删除员工钱包
     *
     * @param ids 需要删除的员工钱包主键
     * @return 结果
     */
    @Override
    public int deleteStaffWalletByIds(Long[] ids) {
        return staffWalletMapper.deleteStaffWalletByIds(ids);
    }

    /**
     * 删除员工钱包信息
     *
     * @param id 员工钱包主键
     * @return 结果
     */
    @Override
    public int deleteStaffWalletById(Long id) {
        return staffWalletMapper.deleteStaffWalletById(id);
    }
}
