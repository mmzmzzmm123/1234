package com.ruoyi.api.staff.service;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.api.staff.model.vo.ApiStaffWalletVo;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.TokenUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.staff.domain.StaffSettlementRecords;
import com.ruoyi.staff.domain.StaffWallet;
import com.ruoyi.staff.domain.StaffWalletRecord;
import com.ruoyi.staff.mapper.StaffSettlementRecordsMapper;
import com.ruoyi.staff.mapper.StaffWalletMapper;
import com.ruoyi.staff.mapper.StaffWalletRecordMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LAM
 * @date 2023/10/18 15:20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiStaffWalletService {

    private final StaffWalletMapper staffWalletMapper;
    private final StaffWalletRecordMapper staffWalletRecordMapper;
    private final StaffSettlementRecordsMapper staffSettlementRecordsMapper;

    /**
     * 查询员工钱包数据
     *
     * @return 结果
     * */
    public ApiStaffWalletVo selectStaffWallet() {
        log.info("查询员工钱包数据：开始");
        ApiStaffWalletVo vo = new ApiStaffWalletVo();
        Long userId = TokenUtils.getUserId();
        StaffWallet staffWallet = staffWalletMapper.selectStaffWalletByUserId(userId);
        if (ObjectUtil.isNull(staffWallet)){
            log.warn("查询员工钱包数据：失败，无法找到对应的店员钱包数据");
            throw new ServiceException("亲 服务拥挤，请稍后重试", HttpStatus.WARN_WX);
        }
        BeanUtils.copyBeanProp(vo, staffWallet);
        log.info("查询员工钱包数据：完成，返回数据：{}", vo);
        return vo;
    }

    /**
     * 查询店员钱包记录
     *
     * @return 结果
     * */
    public List<StaffWalletRecord> selectStaffWallRecord(){
        StaffWalletRecord staffWalletRecord = new StaffWalletRecord();
        staffWalletRecord.setStaffUserId(TokenUtils.getUserId());
        return staffWalletRecordMapper.selectStaffWalletRecordList(staffWalletRecord);
    }

    /**
     * 查询店员结算记录
     *
     * @return 结果
     * */
    public List<StaffSettlementRecords> selectStaffSettlementRecords(){
        StaffSettlementRecords select = new StaffSettlementRecords();
        select.setUserId(TokenUtils.getUserId());
        return staffSettlementRecordsMapper.selectStaffSettlementRecordsList(select);
    }
}
