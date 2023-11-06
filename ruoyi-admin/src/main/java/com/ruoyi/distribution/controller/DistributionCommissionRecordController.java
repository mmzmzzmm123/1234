package com.ruoyi.distribution.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.distribution.domain.DistributionCommissionRecord;
import com.ruoyi.distribution.service.IDistributionCommissionRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 分销佣金记录Controller
 *
 * @author Lam
 * @date 2023-11-02
 */
@Api(tags = "admin-分销佣金记录")
@RestController
@RequiredArgsConstructor
@RequestMapping("/distribution/distributionCommissionRecord")
public class DistributionCommissionRecordController extends BaseController {

    private final IDistributionCommissionRecordService distributionCommissionRecordService;


    @ApiOperation("查询分销佣金记录列表")
    @PreAuthorize("@ss.hasPermi('distribution:distributionCommissionRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(DistributionCommissionRecord distributionCommissionRecord) {
        startPage();
        List<DistributionCommissionRecord> list = distributionCommissionRecordService.selectDistributionCommissionRecordList(distributionCommissionRecord);
        return getDataTable(list);
    }


    @ApiOperation("导出分销佣金记录列表")
    @PreAuthorize("@ss.hasPermi('distribution:distributionCommissionRecord:export')")
    @Log(title = "分销佣金记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DistributionCommissionRecord distributionCommissionRecord) {
        List<DistributionCommissionRecord> list = distributionCommissionRecordService.selectDistributionCommissionRecordList(distributionCommissionRecord);
        ExcelUtil<DistributionCommissionRecord> util = new ExcelUtil<DistributionCommissionRecord>(DistributionCommissionRecord.class);
        util.exportExcel(response, list, "分销佣金记录数据");
    }


    @ApiOperation("获取分销佣金记录详细信息")
    @PreAuthorize("@ss.hasPermi('distribution:distributionCommissionRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(distributionCommissionRecordService.selectDistributionCommissionRecordById(id));
    }


    @ApiOperation("新增分销佣金记录")
    @PreAuthorize("@ss.hasPermi('distribution:distributionCommissionRecord:add')")
    @Log(title = "分销佣金记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DistributionCommissionRecord distributionCommissionRecord) {
        return toAjax(distributionCommissionRecordService.insertDistributionCommissionRecord(distributionCommissionRecord));
    }


    @ApiOperation("修改分销佣金记录")
    @PreAuthorize("@ss.hasPermi('distribution:distributionCommissionRecord:edit')")
    @Log(title = "分销佣金记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DistributionCommissionRecord distributionCommissionRecord) {
        return toAjax(distributionCommissionRecordService.updateDistributionCommissionRecord(distributionCommissionRecord));
    }


    @ApiOperation("删除分销佣金记录")
    @PreAuthorize("@ss.hasPermi('distribution:distributionCommissionRecord:remove')")
    @Log(title = "分销佣金记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(distributionCommissionRecordService.deleteDistributionCommissionRecordByIds(ids));
    }
}
