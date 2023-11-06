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
import com.ruoyi.distribution.domain.DistributionCommissionRate;
import com.ruoyi.distribution.service.IDistributionCommissionRateService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 分销佣金比例Controller
 *
 * @author Lam
 * @date 2023-11-02
 */
@Api(tags = "admin-分销佣金比例")
@RestController
@RequiredArgsConstructor
@RequestMapping("/distribution/distributionCommissionRate")
public class DistributionCommissionRateController extends BaseController {

    private final IDistributionCommissionRateService distributionCommissionRateService;


    @ApiOperation("查询分销佣金比例列表")
    @PreAuthorize("@ss.hasPermi('distribution:distributionCommissionRate:list')")
    @GetMapping("/list")
    public TableDataInfo list(DistributionCommissionRate distributionCommissionRate) {
        startPage();
        List<DistributionCommissionRate> list = distributionCommissionRateService.selectDistributionCommissionRateList(distributionCommissionRate);
        return getDataTable(list);
    }


    @ApiOperation("导出分销佣金比例列表")
    @PreAuthorize("@ss.hasPermi('distribution:distributionCommissionRate:export')")
    @Log(title = "分销佣金比例", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DistributionCommissionRate distributionCommissionRate) {
        List<DistributionCommissionRate> list = distributionCommissionRateService.selectDistributionCommissionRateList(distributionCommissionRate);
        ExcelUtil<DistributionCommissionRate> util = new ExcelUtil<DistributionCommissionRate>(DistributionCommissionRate.class);
        util.exportExcel(response, list, "分销佣金比例数据");
    }


    @ApiOperation("获取分销佣金比例详细信息")
    @PreAuthorize("@ss.hasPermi('distribution:distributionCommissionRate:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(distributionCommissionRateService.selectDistributionCommissionRateById(id));
    }


    @ApiOperation("新增分销佣金比例")
    @PreAuthorize("@ss.hasPermi('distribution:distributionCommissionRate:add')")
    @Log(title = "分销佣金比例", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DistributionCommissionRate distributionCommissionRate) {
        return toAjax(distributionCommissionRateService.insertDistributionCommissionRate(distributionCommissionRate));
    }


    @ApiOperation("修改分销佣金比例")
    @PreAuthorize("@ss.hasPermi('distribution:distributionCommissionRate:edit')")
    @Log(title = "分销佣金比例", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DistributionCommissionRate distributionCommissionRate) {
        return toAjax(distributionCommissionRateService.updateDistributionCommissionRate(distributionCommissionRate));
    }


    @ApiOperation("删除分销佣金比例")
    @PreAuthorize("@ss.hasPermi('distribution:distributionCommissionRate:remove')")
    @Log(title = "分销佣金比例", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(distributionCommissionRateService.deleteDistributionCommissionRateByIds(ids));
    }
}
