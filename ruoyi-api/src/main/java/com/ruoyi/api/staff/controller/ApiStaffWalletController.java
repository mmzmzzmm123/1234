package com.ruoyi.api.staff.controller;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.api.staff.model.vo.*;
import com.ruoyi.api.staff.service.ApiStaffWalletService;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.staff.domain.StaffSettlementRecords;
import com.ruoyi.staff.domain.StaffWalletRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LAM
 * @date 2023/10/18 15:20
 */
@Slf4j
@Anonymous
@RestController
@RequiredArgsConstructor
@Api("api-员工钱包相关接口")
@RequestMapping("/api/staffWallet")
public class ApiStaffWalletController extends BaseController {

    private final ApiStaffWalletService service;

    @ApiOperation("查询员工钱包数据")
    @GetMapping("/selectStaffWallet")
    public R<ApiStaffWalletVo> selectStaffWallet() {
        return R.ok(service.selectStaffWallet());
    }

    @ApiOperation("分页查询员工钱包记录数据")
    @GetMapping("/pageWalletRecord")
    public R<ApiPageStaffWalletRecordVo> pageWalletRecord() {
        log.info("分页查询员工钱包记录数据：开始");
        ApiPageStaffWalletRecordVo vo = new ApiPageStaffWalletRecordVo();
        List<ApiStaffWalletRecordVo> voList = new ArrayList<>();
        startPage();
        List<StaffWalletRecord> staffWalletRecords = service.selectStaffWallRecord();
        TableDataInfo dataTable = getDataTable(staffWalletRecords);
        if (ObjectUtil.isNotEmpty(dataTable)) {
            staffWalletRecords.forEach(item -> {
                ApiStaffWalletRecordVo staffWalletRecordVo = new ApiStaffWalletRecordVo();
                BeanUtils.copyBeanProp(staffWalletRecordVo, item);
                voList.add(staffWalletRecordVo);
            });
        }
        vo.setData(voList).setTotal(dataTable.getTotal());
        return R.ok(vo);
    }

    @ApiOperation("分页查询员工结算记录")
    @GetMapping("/pageSettlementRecord")
    public R<ApiPageStaffSettlementRecordsVo> pageSettlementRecord() {
        log.info("分页查询员工结算记录：开始");
        ApiPageStaffSettlementRecordsVo vo = new ApiPageStaffSettlementRecordsVo();
        List<ApiStaffSettlementRecordsVo> voList = new ArrayList<>();
        startPage();
        List<StaffSettlementRecords> staffSettlementRecordsList = service.selectStaffSettlementRecords();
        TableDataInfo dataTable = getDataTable(staffSettlementRecordsList);
        if (ObjectUtil.isNotEmpty(dataTable)) {
            staffSettlementRecordsList.forEach(item -> {
                ApiStaffSettlementRecordsVo settlementRecordsVo = new ApiStaffSettlementRecordsVo();
                BeanUtils.copyBeanProp(settlementRecordsVo, item);
                voList.add(settlementRecordsVo);
            });
        }
        vo.setData(voList).setTotal(dataTable.getTotal());
        log.info("分页查询员工结算记录：完成，返回数据：{}", vo);
        return R.ok(vo);
    }
}
