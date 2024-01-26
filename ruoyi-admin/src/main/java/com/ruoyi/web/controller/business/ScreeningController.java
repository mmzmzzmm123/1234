package com.ruoyi.web.controller.business;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.MerchantInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.dto.ScreeningTaskDetailDTO;
import com.ruoyi.system.domain.dto.ScreeningTaskDetailExportDTO;
import com.ruoyi.system.domain.dto.ScreeningTaskExportDTO;
import com.ruoyi.system.domain.dto.ScreeningTaskPageDTO;
import com.ruoyi.system.domain.vo.*;
import com.ruoyi.system.service.ScreeningTaskBatchService;
import com.ruoyi.system.service.ScreeningTaskService;
import com.ruoyi.system.service.business.ScreeningService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 朱少波
 * @date 2024/1/25/025 9:57
 */
@Api(tags = "筛查任务")
@RestController
@RequestMapping("/screening")
@Slf4j
public class ScreeningController extends BaseController {

    @Resource
    private ScreeningService screeningService;

    @Resource
    private ScreeningTaskService screeningTaskService;

    @Resource
    private ScreeningTaskBatchService screeningTaskBatchService;

    @RepeatSubmit(interval = 1000, message = "请求过于频繁")
    @ApiOperation(value = "新增任务")
    @PostMapping("/add")
    public R<Void> add(@RequestParam(value = "file") @ApiParam("手机文件txt格式") MultipartFile file,
                       @RequestParam(value = "taskName") @ApiParam("任务名") String taskName,
                       @RequestParam(value = "CountryCode") @ApiParam("国家代码") String countryCode,
                       @RequestParam(value = "productId") @ApiParam("商品id") Long productId) {
        try {
            String merchantId = "";
            MerchantInfo merchantInfo = getMerchantInfo();
            if (merchantInfo != null) {
                if (!merchantInfo.getMerchantType().equals(2)) {
                    merchantId = merchantInfo.getMerchantId();
                }
            }
            screeningService.addTask(file, merchantId, getUserId(), taskName, countryCode, productId);
            return R.ok();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, taskName, e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }


    @ApiOperation(value = "追加手机号")
    @PostMapping("/addTarget")
    public R<Void> addTarget(@RequestParam(value = "file") @ApiParam("手机文件txt格式") MultipartFile file,
                             @RequestParam(value = "taskId") @ApiParam("追加的任务id") String taskId) {
        try {
            screeningService.addTask(file, taskId);
            return R.ok();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, taskId, e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }

    @ApiOperation(value = "查询任务列表")
    @PostMapping("/page")
    public R<Page<ScreeningTaskVO>> page(@RequestBody ScreeningTaskPageDTO dto) {
        try {
            String merchantId = "";
            MerchantInfo merchantInfo = getMerchantInfo();
            if (merchantInfo != null) {
                if (!merchantInfo.getMerchantType().equals(2)) {
                    merchantId = merchantInfo.getMerchantId();
                }
            }
            return R.ok(screeningTaskService.taskPage(merchantId, dto));
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, JSON.toJSONString(dto), e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }


    @ApiOperation(value = "暂停")
    @PostMapping("/stop/{taskId}")
    public R<Void> stop(@PathVariable String taskId) {
        try {
            return R.ok();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, taskId, e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }

    @ApiOperation(value = "重启")
    @PostMapping("/restart/{taskId}")
    public R<Void> restart(@PathVariable String taskId) {
        try {
            return R.ok();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, taskId, e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }


    @ApiOperation(value = "取消")
    @PostMapping("/cancel/{taskId}")
    public R<Void> cancel(@PathVariable String taskId) {
        try {
            return R.ok();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, taskId, e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }


    @ApiOperation(value = "任务进度")
    @PostMapping("/taskProgress/{taskId}")
    public R<TaskProgressVO> taskProgress(@PathVariable String taskId) {
        try {

            return R.ok(screeningTaskService.taskProgress(taskId));
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, taskId, e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }

    @ApiOperation(value = "批次进度")
    @PostMapping("/batchProgress/{taskId}")
    public R<TaskBatchProgressVO> batchProgress(@PathVariable String taskId) {
        try {
            return R.ok(screeningTaskService.batchProgress(taskId));
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, taskId, e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }

    @ApiOperation(value = "导出")
    @PostMapping("/export")
    public void export(@RequestBody ScreeningTaskExportDTO dto, HttpServletResponse response) {
        if (StrUtil.isBlank(dto.getTaskId())) {
            return;
        }
        List<ScreeningTaskResultVO> result = screeningTaskService.taskResult(dto);
        ExcelUtil<ScreeningTaskResultVO> util = new ExcelUtil<>(ScreeningTaskResultVO.class);
        util.exportExcel(response, result, "检查结果");
    }

    @ApiOperation(value = "任务批次列表")
    @PostMapping("/batchInfo/{taskId}")
    public R<List<ScreeningBatchVO>> batchInfo(@RequestBody @PathVariable String taskId) {
        try {
            return R.ok(screeningTaskBatchService.batchInfo(taskId));
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, taskId, e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }

    @ApiOperation(value = "明细")
    @PostMapping("/detail")
    public R<Page<ScreeningTaskDetailVO>> detail(@RequestBody ScreeningTaskDetailDTO dto) {
        try {
            Assert.notEmpty(dto.getTaskId(),"任务id不能为空");
            return R.ok(screeningTaskService.taskDetail(dto));
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, JSON.toJSONString(dto), e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }


    @ApiOperation(value = "明细导出")
    @PostMapping("/detailExport")
    public void detailExport(@RequestBody ScreeningTaskDetailExportDTO dto, HttpServletResponse response) {
        List<ScreeningTaskDetailVO> list = new ArrayList<>();
        int page = 1;
        int limit = 500;
        while (true) {
            try {
                dto.setPage(page++);
                dto.setLimit(limit);
                List<ScreeningTaskDetailVO> records =screeningTaskService.taskDetail(dto).getRecords();
                list.addAll(records);
                if (records.size() < limit) {
                    break;
                }
            } catch (Exception e) {
                break;
            }
        }
        ExcelUtil<ScreeningTaskDetailVO> util = new ExcelUtil<>(ScreeningTaskDetailVO.class);
        util.exportExcel(response, list, "筛查明细数据");
    }
}
