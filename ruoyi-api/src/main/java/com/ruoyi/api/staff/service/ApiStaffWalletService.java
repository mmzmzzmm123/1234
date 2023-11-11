package com.ruoyi.api.staff.service;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.api.staff.model.dto.ApiStaffUserIdDto;
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
     * @param userId 店员用户标识
     * @return 结果
     */
    public ApiStaffWalletVo selectStaffWallet(Long userId) {
        log.info("查询员工钱包数据：开始");
        ApiStaffWalletVo vo = new ApiStaffWalletVo();
        if (ObjectUtil.isNull(userId)) {
            userId = TokenUtils.getUserId();
        }
        StaffWallet staffWallet = staffWalletMapper.selectStaffWalletByUserId(userId);
        if (ObjectUtil.isNull(staffWallet)) {
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
     * @param dto 查询条件
     * @return 结果
     */
    public List<StaffWalletRecord> selectStaffWallRecord(ApiStaffUserIdDto dto) {
        StaffWalletRecord staffWalletRecord = new StaffWalletRecord();
        Long staffUserId = TokenUtils.getUserId();
        if (ObjectUtil.isNotNull(dto.getStaffUserId())){
            staffUserId = dto.getStaffUserId();
        }
        staffWalletRecord.setStaffUserId(staffUserId);
        return staffWalletRecordMapper.selectStaffWalletRecordList(staffWalletRecord);
    }

    /**
     * 查询店员结算记录
     *
     * @param dto 查询条件
     * @return 结果
     */
    public List<StaffSettlementRecords> selectStaffSettlementRecords(ApiStaffUserIdDto dto) {
        Long staffUserId = TokenUtils.getUserId();
        if (ObjectUtil.isNotNull(dto.getStaffUserId())){
            staffUserId = dto.getStaffUserId();
        }
        StaffSettlementRecords select = new StaffSettlementRecords();
        select.setUserId(staffUserId);
        return staffSettlementRecordsMapper.selectStaffSettlementRecordsList(select);
    }
}
