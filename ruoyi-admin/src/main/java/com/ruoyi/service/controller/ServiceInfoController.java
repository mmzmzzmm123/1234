package com.ruoyi.service.controller;

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
import com.ruoyi.service.domain.ServiceInfo;
import com.ruoyi.service.service.IServiceInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 服务信息Controller
 *
 * @author Lam
 * @date 2023-09-14
 */
@Api(tags = "admin-服务信息")
@RestController
@RequiredArgsConstructor
@RequestMapping("/service/serviceInfo")
public class ServiceInfoController extends BaseController {

    private final IServiceInfoService serviceInfoService;


    @ApiOperation("查询服务信息列表")
    @PreAuthorize("@ss.hasPermi('service:serviceInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(ServiceInfo serviceInfo) {
        startPage();
        List<ServiceInfo> list = serviceInfoService.selectServiceInfoList(serviceInfo);
        return getDataTable(list);
    }


    @ApiOperation("导出服务信息列表")
    @PreAuthorize("@ss.hasPermi('service:serviceInfo:export')")
    @Log(title = "服务信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ServiceInfo serviceInfo) {
        List<ServiceInfo> list = serviceInfoService.selectServiceInfoList(serviceInfo);
        ExcelUtil<ServiceInfo> util = new ExcelUtil<ServiceInfo>(ServiceInfo.class);
        util.exportExcel(response, list, "服务信息数据");
    }


    @ApiOperation("获取服务信息详细信息")
    @PreAuthorize("@ss.hasPermi('service:serviceInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(serviceInfoService.selectServiceInfoById(id));
    }


    @ApiOperation("新增服务信息")
    @PreAuthorize("@ss.hasPermi('service:serviceInfo:add')")
    @Log(title = "服务信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ServiceInfo serviceInfo) {
        return toAjax(serviceInfoService.insertServiceInfo(serviceInfo));
    }


    @ApiOperation("修改服务信息")
    @PreAuthorize("@ss.hasPermi('service:serviceInfo:edit')")
    @Log(title = "服务信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ServiceInfo serviceInfo) {
        return toAjax(serviceInfoService.updateServiceInfo(serviceInfo));
    }


    @ApiOperation("删除服务信息")
    @PreAuthorize("@ss.hasPermi('service:serviceInfo:remove')")
    @Log(title = "服务信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(serviceInfoService.deleteServiceInfoByIds(ids));
    }
}
