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
import com.ruoyi.distribution.domain.DistributionRelation;
import com.ruoyi.distribution.service.IDistributionRelationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 分销关系Controller
 *
 * @author Lam
 * @date 2023-11-02
 */
@Api(tags = "admin-分销关系")
@RestController
@RequiredArgsConstructor
@RequestMapping("/distribution/distributionRelation")
public class DistributionRelationController extends BaseController {

    private final IDistributionRelationService distributionRelationService;


    @ApiOperation("查询分销关系列表")
    @PreAuthorize("@ss.hasPermi('distribution:distributionRelation:list')")
    @GetMapping("/list")
    public TableDataInfo list(DistributionRelation distributionRelation) {
        startPage();
        List<DistributionRelation> list = distributionRelationService.selectDistributionRelationList(distributionRelation);
        return getDataTable(list);
    }


    @ApiOperation("导出分销关系列表")
    @PreAuthorize("@ss.hasPermi('distribution:distributionRelation:export')")
    @Log(title = "分销关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DistributionRelation distributionRelation) {
        List<DistributionRelation> list = distributionRelationService.selectDistributionRelationList(distributionRelation);
        ExcelUtil<DistributionRelation> util = new ExcelUtil<DistributionRelation>(DistributionRelation.class);
        util.exportExcel(response, list, "分销关系数据");
    }


    @ApiOperation("获取分销关系详细信息")
    @PreAuthorize("@ss.hasPermi('distribution:distributionRelation:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(distributionRelationService.selectDistributionRelationById(id));
    }


    @ApiOperation("新增分销关系")
    @PreAuthorize("@ss.hasPermi('distribution:distributionRelation:add')")
    @Log(title = "分销关系", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DistributionRelation distributionRelation) {
        return toAjax(distributionRelationService.insertDistributionRelation(distributionRelation));
    }


    @ApiOperation("修改分销关系")
    @PreAuthorize("@ss.hasPermi('distribution:distributionRelation:edit')")
    @Log(title = "分销关系", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DistributionRelation distributionRelation) {
        return toAjax(distributionRelationService.updateDistributionRelation(distributionRelation));
    }


    @ApiOperation("删除分销关系")
    @PreAuthorize("@ss.hasPermi('distribution:distributionRelation:remove')")
    @Log(title = "分销关系", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(distributionRelationService.deleteDistributionRelationByIds(ids));
    }
}
