package com.ruoyi.app.controller.gauge;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.gauge.domain.PsyConsultantManagement;
import com.ruoyi.gauge.service.IPsyConsultantManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 咨询师管理Controller
 * 
 * @author yangchuang
 * @date 2023-01-29
 */
@RestController
@RequestMapping("/app/consultant/management")
@Api(value = "PsyConsultantManagementController" ,tags = {"咨询师管理Capi"})
public class PsyConsultantManagementController extends BaseController
{
    @Autowired
    private IPsyConsultantManagementService psyConsultantManagementService;

    /**
     * 查询咨询师管理列表
     */
    //@PreAuthorize("@ss.hasPermi('system:management:list')")
    @GetMapping("/list")
    @ApiOperation("查询咨询师管理列表")
    public TableDataInfo list(PsyConsultantManagement psyConsultantManagement)
    {
        startPage();
        List<PsyConsultantManagement> list = psyConsultantManagementService.selectPsyConsultantManagementList(psyConsultantManagement);
        return getDataTable(list);
    }



    /**
     * 获取咨询师管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:management:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取咨询师管理详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultantManagementService.selectPsyConsultantManagementById(id));
    }

    /**
     * 新增咨询师管理
     */
    //@PreAuthorize("@ss.hasPermi('system:management:add')")
    @PostMapping("/add")
    @ApiOperation("新增咨询师管理")
    public AjaxResult add(@RequestBody PsyConsultantManagement psyConsultantManagement)
    {
        return toAjax(psyConsultantManagementService.insertPsyConsultantManagement(psyConsultantManagement));
    }

    /**
     * 修改咨询师管理
     */
    //@PreAuthorize("@ss.hasPermi('system:management:edit')")
    @PostMapping("/edit")
    @ApiOperation("修改咨询师管理")
    public AjaxResult edit(@RequestBody PsyConsultantManagement psyConsultantManagement)
    {
        return toAjax(psyConsultantManagementService.updatePsyConsultantManagement(psyConsultantManagement));
    }

    /**
     * 删除咨询师管理
     */
    //@PreAuthorize("@ss.hasPermi('system:management:remove')")
	@PostMapping("/remove/{ids}")
    @ApiOperation("删除咨询师管理")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(psyConsultantManagementService.deletePsyConsultantManagementByIds(ids));
    }
}
