package com.ruoyi.app.controller.gauge;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.gauge.domain.PsyConsultingService;
import com.ruoyi.gauge.service.IPsyConsultingServiceService;
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
 * 咨询服务Controller
 * 
 * @author yangchuang
 * @date 2023-01-31
 */
@RestController
@RequestMapping("/app/consulting/service")
@Api(value = "PsyConsultingServiceController" ,tags = {"咨询服务"})
public class PsyConsultingServiceController extends BaseController
{
    @Autowired
    private IPsyConsultingServiceService psyConsultingServiceService;

    /**
     * 查询咨询服务列表
     */
    //@PreAuthorize("@ss.hasPermi('system:service:list')")
    @GetMapping("/list")
    @ApiOperation("查询咨询服务列表")
    public TableDataInfo list(PsyConsultingService psyConsultingService)
    {
        startPage();
        List<PsyConsultingService> list = psyConsultingServiceService.selectPsyConsultingServiceList(psyConsultingService);
        return getDataTable(list);
    }

    /**
     * 获取咨询服务详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:service:query')")
    @GetMapping(value = "/{id}")
    @ApiOperation("获取咨询服务详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultingServiceService.selectPsyConsultingServiceById(id));
    }

    /**
     * 新增咨询服务
     */
    /*@PreAuthorize("@ss.hasPermi('system:service:add')")
    @Log(title = "咨询服务", businessType = BusinessType.INSERT)*/
    @PostMapping("/add")
    @ApiOperation("新增咨询服务")
    public AjaxResult add(@RequestBody PsyConsultingService psyConsultingService)
    {
        return toAjax(psyConsultingServiceService.insertPsyConsultingService(psyConsultingService));
    }

    /**
     * 修改咨询服务
     */
    /*@PreAuthorize("@ss.hasPermi('system:service:edit')")
    @Log(title = "咨询服务", businessType = BusinessType.UPDATE)*/
    @PostMapping("/edit")
    @ApiOperation("修改咨询服务")
    public AjaxResult edit(@RequestBody PsyConsultingService psyConsultingService)
    {
        return toAjax(psyConsultingServiceService.updatePsyConsultingService(psyConsultingService));
    }

    /**
     * 删除咨询服务
     */
    /*@PreAuthorize("@ss.hasPermi('system:service:remove')")
    @Log(title = "咨询服务", businessType = BusinessType.DELETE)*/
    @PostMapping("/remove/{ids}")
    @ApiOperation("删除咨询服务")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(psyConsultingServiceService.deletePsyConsultingServiceByIds(ids));
    }
}
