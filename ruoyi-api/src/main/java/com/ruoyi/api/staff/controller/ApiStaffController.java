package com.ruoyi.api.staff.controller;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.api.staff.model.dto.ApiPageStaffInfoDto;
import com.ruoyi.api.staff.model.dto.ApiStaffInfoDto;
import com.ruoyi.api.staff.model.vo.*;
import com.ruoyi.api.staff.service.ApiStaffService;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.SysYesNoEnums;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.staff.domain.StaffInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LAM
 * @date 2023/9/20 16:05
 */
@Slf4j
@Anonymous
@RestController
@RequiredArgsConstructor
@Api("api-员工信息相关接口")
@RequestMapping("/api/staff")
public class ApiStaffController extends BaseController {

    private final ApiStaffService service;

    @ApiOperation("获取员工等级配置")
    @GetMapping("/selectStaffLevelConfig")
    public R<List<ApiStaffLevelConfigVo>> selectStaffLevelConfig() {
        return R.ok(service.selectStaffLevelConfig());
    }

    @ApiOperation("根据用户标识查询员工信息")
    @GetMapping("/selectByUserId")
    public R<ApiStaffInfoVo> selectByUserId() {
        return R.ok(service.selectByUserId());
    }

    @ApiOperation("根据用户标识查询员工相册")
    @GetMapping("/selectPhotoByUserId")
    public R<List<ApiStaffPhotoVo>> selectPhotoByUserId() {
        return R.ok(service.selectPhotoByUserId());
    }

    @ApiOperation("根据店员标识查询数据")
    @GetMapping("/selectByStaffId")
    public R<ApiStaffInfoVo> selectByStaffId(@RequestParam("staffId") Long staffId) {
        return R.ok(service.selectByStaffId(staffId));
    }

    @ApiOperation("申请成为店员")
    @PostMapping("/apply")
    @RepeatSubmit(interval = 1000, message = "请求过于频繁")
    public R<Boolean> apply(@Valid ApiStaffInfoDto dto) {
        return service.apply(dto);
    }

    @ApiOperation("修改数据")
    @PostMapping("/update")
    @RepeatSubmit(interval = 1000, message = "请求过于频繁")
    public R<Boolean> update(@Valid ApiStaffInfoDto dto) {
        return service.update(dto);
    }

    @ApiOperation("获取店员配置接单服务id")
    @GetMapping("/selectServiceConfigIds")
    public R<List<Long>> selectServiceConfigIds() {
        return R.ok(service.selectServiceConfigIds());
    }

    @ApiOperation("处理店员服务数据")
    @GetMapping("/handleServiceId")
    @RepeatSubmit(interval = 1000, message = "请求过于频繁")
    public R<List<Long>> handleServiceId(@RequestParam("serviceId") Long serviceId) {
        return R.ok(service.handleServiceId(serviceId));
    }

    @ApiOperation("查询我的组员")
    @GetMapping("/selectTeamMembers")
    public R<List<ApiStaffInfoVo>> selectTeamMembers(){
        return R.ok(service.selectTeamMembers());
    }

    @ApiOperation("店员信息分页")
    @GetMapping("/page")
    public R<ApiPageStaffInfoVo> page(ApiPageStaffInfoDto dto) {
        log.info("店员信息分页：开始，参数：{}", dto);

        ApiPageStaffInfoVo vo = new ApiPageStaffInfoVo();
        List<ApiStaffInfoVo> voList = new ArrayList<>();

        // 拿到过滤店员的标识集合
        List<Long> filterStaffIdList = service.selectFilterIdByServiceIds(dto);

        // 开始分页查看数据
        startPage();
        // 封装数据
        StaffInfo select = new StaffInfo();
        BeanUtils.copyBeanProp(select, dto);
        if (ObjectUtil.isNotEmpty(filterStaffIdList)) {
            select.setFilterIdList(filterStaffIdList);
        }
        if (StringUtils.isNotBlank(dto.getStaffLevel())) {
            select.setStaffLevel(Long.parseLong(dto.getStaffLevel()));
        }
        // 开始查询
        List<StaffInfo> staffInfos = service.page(select);
        // 数据处理
        TableDataInfo dataTable = getDataTable(staffInfos);
        // 开始处理对应业务
        if (ObjectUtil.isNotEmpty(dataTable)) {
            List<StaffInfo> newStaffInfoList = new ArrayList<>();
            // 置顶数据
            List<StaffInfo> topList = staffInfos.stream().filter(item -> item.getIfTop().equals(SysYesNoEnums.YES.getCode())).collect(Collectors.toList());
            if (ObjectUtil.isNotEmpty(topList)) {
                Collections.shuffle(topList);
                newStaffInfoList.addAll(topList);
            }
            // 需要打乱的数据
            List<StaffInfo> randomList = staffInfos.stream().filter(item -> item.getIfTop().equals(SysYesNoEnums.NO.getCode())).collect(Collectors.toList());
            if (ObjectUtil.isNotEmpty(randomList) && !"4".equals(dto.getSortType())) {
                Collections.shuffle(randomList);
                topList.addAll(randomList);
            }
            if (ObjectUtil.isNotEmpty(randomList)){
                newStaffInfoList.addAll(randomList);
            }
            // 遍历数据并插入返回集合中
            newStaffInfoList.forEach(item -> {
                ApiStaffInfoVo staffInfoVo = new ApiStaffInfoVo();
                BeanUtils.copyBeanProp(staffInfoVo, item);
                voList.add(staffInfoVo);
            });
        }
        vo.setTotal(dataTable.getTotal())
                .setData(voList);
        log.info("店员信息分页：完成，返回数据：{}", vo);
        return R.ok(vo);
    }

    @ApiOperation("获取店员总礼物数据")
    @GetMapping("/selectStaffGiftRecordId")
    public R<List<ApiStaffGiftRecordVo>> selectStaffGiftRecord(@RequestParam("staffId") Long staffId) {
        return R.ok(service.selectStaffGiftRecord(staffId));
    }

    @ApiOperation("周排名前三")
    @GetMapping("/weeklyRankingTopThree")
    public R<List<ApiStaffInfoVo>> weeklyRankingTopThree() {
        return R.ok(service.weeklyRankingTopThree());
    }
}
