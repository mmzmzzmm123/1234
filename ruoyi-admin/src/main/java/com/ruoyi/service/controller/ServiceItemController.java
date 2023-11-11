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
import com.ruoyi.service.domain.ServiceItem;
import com.ruoyi.service.service.IServiceItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 服务子项目Controller
 *
 * @author Lam
 * @date 2023-09-14
 */
@Api(tags = "admin-服务子项目")
@RestController
@RequiredArgsConstructor
@RequestMapping("/service/serviceItem")
public class ServiceItemController extends BaseController {

    private final IServiceItemService serviceItemService;


    @ApiOperation("查询服务子项目列表")
    @PreAuthorize("@ss.hasPermi('service:serviceItem:list')")
    @GetMapping("/list")
    public TableDataInfo list(ServiceItem serviceItem) {
        startPage();
        List<ServiceItem> list = serviceItemService.selectJoinServiceInfo(serviceItem);
        return getDataTable(list);
    }


    @ApiOperation("导出服务子项目列表")
    @PreAuthorize("@ss.hasPermi('service:serviceItem:export')")
    @Log(title = "服务子项目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ServiceItem serviceItem) {
        List<ServiceItem> list = serviceItemService.selectServiceItemList(serviceItem);
        ExcelUtil<ServiceItem> util = new ExcelUtil<ServiceItem>(ServiceItem.class);
        util.exportExcel(response, list, "服务子项目数据");
    }


    @ApiOperation("获取服务子项目详细信息")
    @PreAuthorize("@ss.hasPermi('service:serviceItem:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(serviceItemService.selectServiceItemById(id));
    }


    @ApiOperation("新增服务子项目")
    @PreAuthorize("@ss.hasPermi('service:serviceItem:add')")
    @Log(title = "服务子项目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ServiceItem serviceItem) {
        return toAjax(serviceItemService.insertServiceItem(serviceItem));
    }


    @ApiOperation("修改服务子项目")
    @PreAuthorize("@ss.hasPermi('service:serviceItem:edit')")
    @Log(title = "服务子项目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ServiceItem serviceItem) {
        return toAjax(serviceItemService.updateServiceItem(serviceItem));
    }


    @ApiOperation("删除服务子项目")
    @PreAuthorize("@ss.hasPermi('service:serviceItem:remove')")
    @Log(title = "服务子项目", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(serviceItemService.deleteServiceItemByIds(ids));
    }
}
