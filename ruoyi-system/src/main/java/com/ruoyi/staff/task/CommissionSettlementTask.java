package com.ruoyi.staff.task;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.enums.StaffWalletRecordTypeEnums;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.staff.domain.StaffSettlementRecords;
import com.ruoyi.staff.domain.StaffWallet;
import com.ruoyi.staff.domain.StaffWalletRecord;
import com.ruoyi.staff.mapper.StaffSettlementRecordsMapper;
import com.ruoyi.staff.mapper.StaffWalletMapper;
import com.ruoyi.staff.mapper.StaffWalletRecordMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author LAM
 * @date 2023/10/25 1:06
 */
@Slf4j
@Component("commissionSettlementTask")
@RequiredArgsConstructor
public class CommissionSettlementTask {

    private final StaffWalletMapper staffWalletMapper;
    private final StaffWalletRecordMapper staffWalletRecordMapper;
    private final StaffSettlementRecordsMapper staffSettlementRecordsMapper;

    /**
     * 开始结算（每周一的凌晨（即周日过渡到周一）的第一秒执行任务，cron：1 0 * * 1）
     * */
    @Transactional(rollbackFor = Exception.class)
    public void commissionSettlement(){
        log.info("每周店员薪资结算：开始");
        String sys = Constants.SYS_NAME;
        Date now = DateUtils.getNowDate();
        Date lastMonday = DateUtils.getLastMonday();
        Date lastSunday = DateUtils.getLastSunday();
        // 结算的数据
        List<StaffWallet> staffWallets = staffWalletMapper.selectWaitCommissionGreaterThanZeroForUpdate();
        for (StaffWallet staffWallet : staffWallets){
            // 更新店员钱包数据
            StaffWallet updateSw = new StaffWallet();
            updateSw.setId(staffWallet.getId())
                    .setWaitCommission(BigDecimal.ZERO)
                    .setCommissionSettled(staffWallet.getCommissionSettled().add(staffWallet.getWaitCommission()))
                    .setUpdateTime(now);
            staffWalletMapper.updateStaffWallet(updateSw);
            // 钱包记录
            StaffWalletRecord insertSwr = new StaffWalletRecord();
            insertSwr.setStaffUserId(staffWallet.getUserId())
                    .setStaffWalletRecordType(StaffWalletRecordTypeEnums.SETTLEMENT.getCode())
                    .setAmount(staffWallet.getWaitCommission())
                    .setCreateBy(sys)
                    .setCreateTime(now)
                    .setUpdateBy(sys)
                    .setUpdateTime(now);
            staffWalletRecordMapper.insertStaffWalletRecord(insertSwr);
            // 薪资结算记录
            StaffSettlementRecords insertSsr = new StaffSettlementRecords();
            insertSsr.setUserId(staffWallet.getUserId())
                    .setSettlementAmount(staffWallet.getWaitCommission())
                    .setStartTime(lastMonday)
                    .setEndTime(lastSunday)
                    .setCreateBy(sys)
                    .setCreateTime(now)
                    .setUpdateBy(sys)
                    .setUpdateTime(now);
            staffSettlementRecordsMapper.insertStaffSettlementRecords(insertSsr);
        }
        log.info("每周店员薪资结算：完成");
    }
}

