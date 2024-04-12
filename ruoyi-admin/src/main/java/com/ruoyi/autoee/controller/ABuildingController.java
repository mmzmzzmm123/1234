package com.ruoyi.autoee.controller;

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
import com.ruoyi.autoee.domain.ABuilding;
import com.ruoyi.autoee.service.IABuildingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 建筑物管理Controller
 * 
 * @author ruoyi
 * @date 2024-04-12
 */
@RestController
@RequestMapping("/autoee/building")
public class ABuildingController extends BaseController
{
    @Autowired
    private IABuildingService aBuildingService;

    /**
     * 查询建筑物管理列表
     */
    @PreAuthorize("@ss.hasPermi('autoee:building:list')")
    @GetMapping("/list")
    public TableDataInfo list(ABuilding aBuilding)
    {
        startPage();
        List<ABuilding> list = aBuildingService.selectABuildingList(aBuilding);
        return getDataTable(list);
    }

    /**
     * 导出建筑物管理列表
     */
    @PreAuthorize("@ss.hasPermi('autoee:building:export')")
    @Log(title = "建筑物管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ABuilding aBuilding)
    {
        List<ABuilding> list = aBuildingService.selectABuildingList(aBuilding);
        ExcelUtil<ABuilding> util = new ExcelUtil<ABuilding>(ABuilding.class);
        util.exportExcel(response, list, "建筑物管理数据");
    }

    /**
     * 获取建筑物管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('autoee:building:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(aBuildingService.selectABuildingById(id));
    }

    /**
     * 新增建筑物管理
     */
    @PreAuthorize("@ss.hasPermi('autoee:building:add')")
    @Log(title = "建筑物管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ABuilding aBuilding)
    {
        return toAjax(aBuildingService.insertABuilding(aBuilding));
    }

    /**
     * 修改建筑物管理
     */
    @PreAuthorize("@ss.hasPermi('autoee:building:edit')")
    @Log(title = "建筑物管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ABuilding aBuilding)
    {
        return toAjax(aBuildingService.updateABuilding(aBuilding));
    }

    /**
     * 删除建筑物管理
     */
    @PreAuthorize("@ss.hasPermi('autoee:building:remove')")
    @Log(title = "建筑物管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(aBuildingService.deleteABuildingByIds(ids));
    }
}
