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
import com.ruoyi.autoee.domain.AFloor;
import com.ruoyi.autoee.service.IAFloorService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 楼层管理Controller
 * 
 * @author ruoyi
 * @date 2024-04-12
 */
@RestController
@RequestMapping("/autoee/floor")
public class AFloorController extends BaseController
{
    @Autowired
    private IAFloorService aFloorService;

    /**
     * 查询楼层管理列表
     */
    @PreAuthorize("@ss.hasPermi('autoee:floor:list')")
    @GetMapping("/list")
    public TableDataInfo list(AFloor aFloor)
    {
        startPage();
        List<AFloor> list = aFloorService.selectAFloorList(aFloor);
        return getDataTable(list);
    }

    /**
     * 导出楼层管理列表
     */
    @PreAuthorize("@ss.hasPermi('autoee:floor:export')")
    @Log(title = "楼层管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AFloor aFloor)
    {
        List<AFloor> list = aFloorService.selectAFloorList(aFloor);
        ExcelUtil<AFloor> util = new ExcelUtil<AFloor>(AFloor.class);
        util.exportExcel(response, list, "楼层管理数据");
    }

    /**
     * 获取楼层管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('autoee:floor:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(aFloorService.selectAFloorById(id));
    }

    /**
     * 新增楼层管理
     */
    @PreAuthorize("@ss.hasPermi('autoee:floor:add')")
    @Log(title = "楼层管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AFloor aFloor)
    {
        return toAjax(aFloorService.insertAFloor(aFloor));
    }

    /**
     * 修改楼层管理
     */
    @PreAuthorize("@ss.hasPermi('autoee:floor:edit')")
    @Log(title = "楼层管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AFloor aFloor)
    {
        return toAjax(aFloorService.updateAFloor(aFloor));
    }

    /**
     * 删除楼层管理
     */
    @PreAuthorize("@ss.hasPermi('autoee:floor:remove')")
    @Log(title = "楼层管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(aFloorService.deleteAFloorByIds(ids));
    }
}
