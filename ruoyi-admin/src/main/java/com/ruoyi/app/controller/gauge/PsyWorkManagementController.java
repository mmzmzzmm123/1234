package com.ruoyi.app.controller.gauge;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.gauge.domain.PsyWorkManagement;
import com.ruoyi.gauge.service.IPsyWorkManagementService;
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
 * 排班管理Controller
 * 
 * @author ruoyi
 * @date 2023-01-30
 */
@RestController
@RequestMapping("/app/work/management")
@Api(value = "PsyWorkManagementController" ,tags = {"排班管理"})
public class PsyWorkManagementController extends BaseController
{
    @Autowired
    private IPsyWorkManagementService psyWorkManagementService;

    /**
     * 查询排班管理列表
     */
    //@PreAuthorize("@ss.hasPermi('system:management:list')")
    @GetMapping("/list")
    @ApiOperation("查询排班管理列表")
    public TableDataInfo list(PsyWorkManagement psyWorkManagement)
    {
        startPage();
        List<PsyWorkManagement> list = psyWorkManagementService.selectPsyWorkManagementList(psyWorkManagement);
        return getDataTable(list);
    }



    /**
     * 获取排班管理详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:management:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取排班管理详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyWorkManagementService.selectPsyWorkManagementById(id));
    }

    /**
     * 新增排班管理
     */
   /* @PreAuthorize("@ss.hasPermi('system:management:add')")
    @Log(title = "排班管理", businessType = BusinessType.INSERT)*/
    @PostMapping("/add")
    @ApiOperation("新增排班管理")
    public AjaxResult add(@RequestBody PsyWorkManagement psyWorkManagement)
    {
        return toAjax(psyWorkManagementService.insertPsyWorkManagement(psyWorkManagement));
    }

    /**
     * 修改排班管理
     */
    /*@PreAuthorize("@ss.hasPermi('system:management:edit')")
    @Log(title = "排班管理", businessType = BusinessType.UPDATE)*/
    @PostMapping("/edit")
    @ApiOperation("修改排班管理")
    public AjaxResult edit(@RequestBody PsyWorkManagement psyWorkManagement)
    {
        return toAjax(psyWorkManagementService.updatePsyWorkManagement(psyWorkManagement));
    }

    /**
     * 删除排班管理
     */
    /*@PreAuthorize("@ss.hasPermi('system:management:remove')")
    @Log(title = "排班管理", businessType = BusinessType.DELETE)*/
	@PostMapping("/remove/{ids}")
    @ApiOperation("删除排班管理")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(psyWorkManagementService.deletePsyWorkManagementByIds(ids));
    }
}
