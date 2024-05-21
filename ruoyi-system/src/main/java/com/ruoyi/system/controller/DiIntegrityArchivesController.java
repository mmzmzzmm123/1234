package com.ruoyi.system.controller;

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
import com.ruoyi.system.domain.DiIntegrityArchives;
import com.ruoyi.system.service.IDiIntegrityArchivesService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 廉政档案Controller
 * 
 * @author ruoyi
 * @date 2024-05-17
 */
@RestController
@RequestMapping("/system/archives")
public class DiIntegrityArchivesController extends BaseController
{
    @Autowired
    private IDiIntegrityArchivesService diIntegrityArchivesService;

    /**
     * 查询廉政档案列表
     */
    @PreAuthorize("@ss.hasPermi('system:archives:list')")
    @GetMapping("/list")
    public TableDataInfo list(DiIntegrityArchives diIntegrityArchives)
    {
        startPage();
        List<DiIntegrityArchives> list = diIntegrityArchivesService.selectDiIntegrityArchivesList(diIntegrityArchives);
        return getDataTable(list);
    }

    /**
     * 导出廉政档案列表
     */
    @PreAuthorize("@ss.hasPermi('system:archives:export')")
    @Log(title = "廉政档案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DiIntegrityArchives diIntegrityArchives)
    {
        List<DiIntegrityArchives> list = diIntegrityArchivesService.selectDiIntegrityArchivesList(diIntegrityArchives);
        ExcelUtil<DiIntegrityArchives> util = new ExcelUtil<DiIntegrityArchives>(DiIntegrityArchives.class);
        util.exportExcel(response, list, "廉政档案数据");
    }

    /**
     * 获取廉政档案详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:archives:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(diIntegrityArchivesService.selectDiIntegrityArchivesById(id));
    }

    /**
     * 新增廉政档案
     */
    @PreAuthorize("@ss.hasPermi('system:archives:add')")
    @Log(title = "廉政档案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DiIntegrityArchives diIntegrityArchives)
    {
        return toAjax(diIntegrityArchivesService.insertDiIntegrityArchives(diIntegrityArchives));
    }

    /**
     * 修改廉政档案
     */
    @PreAuthorize("@ss.hasPermi('system:archives:edit')")
    @Log(title = "廉政档案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DiIntegrityArchives diIntegrityArchives)
    {
        return toAjax(diIntegrityArchivesService.updateDiIntegrityArchives(diIntegrityArchives));
    }

    /**
     * 删除廉政档案
     */
    @PreAuthorize("@ss.hasPermi('system:archives:remove')")
    @Log(title = "廉政档案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(diIntegrityArchivesService.deleteDiIntegrityArchivesByIds(ids));
    }
}
