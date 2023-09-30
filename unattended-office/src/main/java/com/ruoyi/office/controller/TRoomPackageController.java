package com.ruoyi.office.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.office.domain.TRoomPackage;
import com.ruoyi.office.service.ITRoomPackageService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 房间套餐价格Controller
 * 
 * @author ruoyi
 * @date 2023-09-14
 */
@RestController
@RequestMapping("/office/roompackage")
public class TRoomPackageController extends BaseController
{
    @Autowired
    private ITRoomPackageService tRoomPackageService;

    /**
     * 查询房间套餐价格列表
     */
    @PreAuthorize("@ss.hasPermi('office:roompackage:list')")
    @GetMapping("/list")
    public TableDataInfo list(TRoomPackage tRoomPackage)
    {
        startPage();
        List<TRoomPackage> list = tRoomPackageService.selectTRoomPackageList(tRoomPackage);
        return getDataTable(list);
    }

    /**
     * 导出房间套餐价格列表
     */
    @PreAuthorize("@ss.hasPermi('office:roompackage:export')")
    @Log(title = "房间套餐价格", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TRoomPackage tRoomPackage)
    {
        List<TRoomPackage> list = tRoomPackageService.selectTRoomPackageList(tRoomPackage);
        ExcelUtil<TRoomPackage> util = new ExcelUtil<TRoomPackage>(TRoomPackage.class);
        util.exportExcel(response, list, "房间套餐价格数据");
    }

    /**
     * 获取房间套餐价格详细信息
     */
    @PreAuthorize("@ss.hasPermi('office:roompackage:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
//        return error("xxx");
        return success(tRoomPackageService.selectTRoomPackageById(id));
    }

    /**
     * 新增房间套餐价格
     */
    @PreAuthorize("@ss.hasPermi('office:roompackage:add')")
    @Log(title = "房间套餐价格", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TRoomPackage tRoomPackage)
    {
        return toAjax(tRoomPackageService.insertTRoomPackage(tRoomPackage));
    }

    /**
     * 修改房间套餐价格
     */
    @PreAuthorize("@ss.hasPermi('office:roompackage:edit')")
    @Log(title = "房间套餐价格", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TRoomPackage tRoomPackage)
    {
        return toAjax(tRoomPackageService.updateTRoomPackage(tRoomPackage));
    }

    /**
     * 删除房间套餐价格
     */
    @PreAuthorize("@ss.hasPermi('office:roompackage:remove')")
    @Log(title = "房间套餐价格", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tRoomPackageService.deleteTRoomPackageByIds(ids));
    }
}
