package com.ruoyi.api.staff.controller;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.api.staff.model.dto.ApiSelectStaffTrendsDto;
import com.ruoyi.api.staff.model.dto.ApiStaffTrendsFormDto;
import com.ruoyi.api.staff.model.vo.ApiPageStaffTrendsVo;
import com.ruoyi.api.staff.model.vo.ApiStaffInfoVo;
import com.ruoyi.api.staff.model.vo.ApiStaffTrendsVo;
import com.ruoyi.api.staff.service.ApiStaffTrendsService;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.staff.domain.StaffTrends;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LAM
 * @date 2023/10/3 16:42
 */
@Anonymous
@Api("api-员工动态相关接口")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/staffTrends")
public class ApiStaffTrendsController extends BaseController {

    private final ApiStaffTrendsService service;

    @ApiOperation("查询店员动态")
    @GetMapping("/select")
    public R<ApiPageStaffTrendsVo> select(ApiSelectStaffTrendsDto dto){
        log.info("查询店员动态：开始");
        ApiPageStaffTrendsVo vo = new ApiPageStaffTrendsVo();
        List<ApiStaffTrendsVo> voList = new ArrayList<>();
        startPage();
        List<StaffTrends> staffTrendsList = service.select(dto);
        TableDataInfo dataTable = getDataTable(staffTrendsList);
        if (ObjectUtil.isNotEmpty(dataTable)){
            staffTrendsList.forEach(item -> {
                ApiStaffTrendsVo staffTrendsVo = new ApiStaffTrendsVo();
                BeanUtils.copyBeanProp(staffTrendsVo, item);
                ApiStaffInfoVo staffInfoVo = new ApiStaffInfoVo();
                BeanUtils.copyBeanProp(staffInfoVo, item.getStaffInfo());
                staffTrendsVo.setStaffInfoVo(staffInfoVo);
                voList.add(staffTrendsVo);
            });
        }
        vo.setTotal(dataTable.getTotal())
                .setData(voList);
        log.info("查询店员动态：完成，返回数据：{}", vo);
        return R.ok(vo);
    }


    @ApiOperation("获取店员动态数据")
    @GetMapping("/selectStaffTrends")
    public R<ApiPageStaffTrendsVo> selectStaffTrends(){
        log.info("获取店员动态数据：开始");
        ApiPageStaffTrendsVo vo = new ApiPageStaffTrendsVo();
        List<ApiStaffTrendsVo> voList = new ArrayList<>();
        startPage();
        List<StaffTrends> staffTrends = service.selectStaffTrends();
        TableDataInfo dataTable = getDataTable(staffTrends);
        if (ObjectUtil.isNotEmpty(dataTable)){
            staffTrends.forEach(item -> {
               ApiStaffTrendsVo staffTrendsVo = new ApiStaffTrendsVo();
                BeanUtils.copyBeanProp(staffTrendsVo, item);
                voList.add(staffTrendsVo);
            });
        }
        vo.setTotal(dataTable.getTotal())
                .setData(voList);
        log.info("获取店员动态数据：完成，返回数据：{}", vo);
        return R.ok(vo);
    }

    @ApiOperation("发布动态")
    @PostMapping("/submit")
    public R<Boolean> submit(ApiStaffTrendsFormDto dto){
        return service.submit(dto);
    }

    @ApiOperation("根据主键删除动态记录")
    @GetMapping("/deleteById")
    public R<Boolean> deleteById(@RequestParam("id") Long id){
        return R.ok(service.deleteById(id));
    }

    @RepeatSubmit(interval = 1000)
    @ApiOperation("点赞店员动态")
    @GetMapping("/likeStaffTrends")
    public R<Boolean> likeStaff(@RequestParam("staffTrendsId") Long staffTrendsId){
        return R.ok(service.likeStaffTrends(staffTrendsId));
    }

    @RepeatSubmit(interval = 1000)
    @ApiOperation("取消点赞店员动态")
    @GetMapping("/cancelLikeStaffTrends")
    public R<Boolean> cancelLikeStaffTrends(@RequestParam("staffTrendsId") Long staffTrendsId){
        return R.ok(service.cancelLikeStaffTrends(staffTrendsId));
    }
}