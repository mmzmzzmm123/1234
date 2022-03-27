package com.jlt.csa.controller;

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
import com.jlt.csa.domain.GardenLogAttach;
import com.jlt.csa.service.IGardenLogAttachService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 菜地日志附件Controller
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
@RestController
@RequestMapping("/csa/attach")
public class GardenLogAttachController extends BaseController
{
    @Autowired
    private IGardenLogAttachService gardenLogAttachService;

    /**
     * 查询菜地日志附件列表
     */
    @PreAuthorize("@ss.hasPermi('csa:attach:list')")
    @GetMapping("/list")
    public TableDataInfo list(GardenLogAttach gardenLogAttach)
    {
        startPage();
        List<GardenLogAttach> list = gardenLogAttachService.selectGardenLogAttachList(gardenLogAttach);
        return getDataTable(list);
    }

    /**
     * 导出菜地日志附件列表
     */
    @PreAuthorize("@ss.hasPermi('csa:attach:export')")
    @Log(title = "菜地日志附件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GardenLogAttach gardenLogAttach)
    {
        List<GardenLogAttach> list = gardenLogAttachService.selectGardenLogAttachList(gardenLogAttach);
        ExcelUtil<GardenLogAttach> util = new ExcelUtil<GardenLogAttach>(GardenLogAttach.class);
        util.exportExcel(response, list, "菜地日志附件数据");
    }

    /**
     * 获取菜地日志附件详细信息
     */
    @PreAuthorize("@ss.hasPermi('csa:attach:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(gardenLogAttachService.selectGardenLogAttachById(id));
    }

    /**
     * 新增菜地日志附件
     */
    @PreAuthorize("@ss.hasPermi('csa:attach:add')")
    @Log(title = "菜地日志附件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GardenLogAttach gardenLogAttach)
    {
        return toAjax(gardenLogAttachService.insertGardenLogAttach(gardenLogAttach));
    }

    /**
     * 修改菜地日志附件
     */
    @PreAuthorize("@ss.hasPermi('csa:attach:edit')")
    @Log(title = "菜地日志附件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GardenLogAttach gardenLogAttach)
    {
        return toAjax(gardenLogAttachService.updateGardenLogAttach(gardenLogAttach));
    }

    /**
     * 删除菜地日志附件
     */
    @PreAuthorize("@ss.hasPermi('csa:attach:remove')")
    @Log(title = "菜地日志附件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(gardenLogAttachService.deleteGardenLogAttachByIds(ids));
    }
}
