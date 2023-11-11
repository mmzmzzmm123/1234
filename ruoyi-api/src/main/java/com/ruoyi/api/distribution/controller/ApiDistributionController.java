package com.ruoyi.api.distribution.controller;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.api.distribution.model.dto.ApiPageDistributionRelationDto;
import com.ruoyi.api.distribution.model.vo.ApiDistributionCommissionRecordVo;
import com.ruoyi.api.distribution.model.vo.ApiDistributionRelationVo;
import com.ruoyi.api.distribution.model.vo.ApiPageDistributionCommissionRecordVo;
import com.ruoyi.api.distribution.model.vo.ApiPageDistributionRelationVo;
import com.ruoyi.api.distribution.service.ApiDistributionService;
import com.ruoyi.api.user.model.vo.ApiUserVo;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.distribution.domain.DistributionCommissionRecord;
import com.ruoyi.distribution.domain.DistributionRelation;
import com.ruoyi.user.domain.UserInfo;
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
 * @date 2023/11/4 20:00
 */
@Anonymous
@Slf4j
@Api("api-分销相关接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/distribution")
public class ApiDistributionController extends BaseController {

    private final ApiDistributionService service;


    @ApiOperation("分销关系分页")
    @GetMapping("/pageDistributionRelation")
    public R<ApiPageDistributionRelationVo> pageDistributionRelation(ApiPageDistributionRelationDto dto) {
        log.info("分销关系分页：开始，参数：{}", dto);
        ApiPageDistributionRelationVo vo = new ApiPageDistributionRelationVo();
        List<ApiDistributionRelationVo> voList = new ArrayList<>();
        startPage();
        List<DistributionRelation> distributionRelationList = service.selectRelation(dto);
        TableDataInfo dataTable = getDataTable(distributionRelationList);
        if (ObjectUtil.isNotEmpty(dataTable)) {
            distributionRelationList.forEach(item -> {
                ApiDistributionRelationVo relationVo = new ApiDistributionRelationVo();
                BeanUtils.copyBeanProp(relationVo, item);
                if (ObjectUtil.isNotNull(item.getChildUserInfo())){
                    UserInfo userInfo = item.getChildUserInfo();
                    ApiUserVo userVo = new ApiUserVo();
                    userVo.setNickName(userInfo.getNickName())
                            .setAvatarUrl(userInfo.getAvatarUrl());
                    relationVo.setChildUserVo(userVo);
                }
                voList.add(relationVo);
            });
        }
        vo.setData(voList)
                .setTotal(dataTable.getTotal());
        log.info("分销关系分页：完成，返回数据：{}", vo);
        return R.ok(vo);
    }

    @ApiOperation("分销佣金记录分页")
    @GetMapping("/pageDistributionCommissionRecord")
    public R<ApiPageDistributionCommissionRecordVo> pageDistributionCommissionRecord(){
        ApiPageDistributionCommissionRecordVo vo = new ApiPageDistributionCommissionRecordVo();
        List<ApiDistributionCommissionRecordVo> voList = new ArrayList<>();
        startPage();
        List<DistributionCommissionRecord> distributionCommissionRecords = service.selectCommissionRecord();
        TableDataInfo dataTable = getDataTable(distributionCommissionRecords);
        if (ObjectUtil.isNotEmpty(dataTable)){
            distributionCommissionRecords.forEach(item -> {
                ApiDistributionCommissionRecordVo recordVo = new ApiDistributionCommissionRecordVo();
                BeanUtils.copyBeanProp(recordVo, item);
                if (ObjectUtil.isNotNull(item.getDistributionUserInfo())){
                    UserInfo userInfo = item.getDistributionUserInfo();
                    ApiUserVo userVo = new ApiUserVo();
                    userVo.setNickName(userInfo.getNickName())
                            .setAvatarUrl(userInfo.getAvatarUrl());
                    recordVo.setDistributionUserInfo(userVo);
                }
                voList.add(recordVo);
            });
        }
        vo.setData(voList)
                .setTotal(dataTable.getTotal());
        return R.ok(vo);
    }
}
