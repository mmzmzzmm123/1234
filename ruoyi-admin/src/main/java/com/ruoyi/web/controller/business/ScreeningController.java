package com.ruoyi.web.controller.business;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.dto.ScreeningTaskExportDTO;
import com.ruoyi.system.domain.dto.ScreeningTaskPageDTO;
import com.ruoyi.system.domain.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 朱少波
 * @date 2024/1/25/025 9:57
 */
@Api(tags = "群管理")
@RestController
@RequestMapping("/screening")
@Slf4j
public class ScreeningController {

    @ApiOperation(value = "新增任务")
    @PostMapping("/add")
    public R<Void> add(@RequestParam(value = "file") @ApiParam("手机文件txt格式") MultipartFile file,
                       @RequestParam(value = "taskName") @ApiParam("任务名") String name,
                       @RequestParam(value = "CountryCode") @ApiParam("国家代码") Integer countryCode) {
        try {
            return R.ok();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, name, e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }


    @ApiOperation(value = "追加手机号")
    @PostMapping("/addTarget")
    public R<Void> addTarget(@RequestParam(value = "file") @ApiParam("手机文件txt格式") MultipartFile file,
                             @RequestParam(value = "taskId") @ApiParam("追加的任务id") String taskId) {
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

    @ApiOperation(value = "查询任务列表")
    @PostMapping("/page")
    public R<Page<ScreeningTaskVO>> page(@RequestBody ScreeningTaskPageDTO dto) {
        try {
            return R.ok();
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
            return R.ok();
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
            return R.ok();
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

    }

    @ApiOperation(value = "任务批次列表")
    @PostMapping("/batchInfo/{taskId}")
    public R<List<ScreeningBatchVO>> batchInfo(@RequestBody @PathVariable String taskId) {
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

    @ApiOperation(value = "明细")
    @PostMapping("/detail")
    public R<Page<ScreeningTaskDetailVO>> detail(@RequestBody ScreeningTaskExportDTO dto) {
        try {
            return R.ok();
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
    public void detailExport(@RequestBody ScreeningTaskExportDTO dto, HttpServletResponse response) {

    }
}
