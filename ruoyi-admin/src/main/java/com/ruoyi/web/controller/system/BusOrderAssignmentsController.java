package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.BusOrderAssignments;
import com.ruoyi.system.service.IBusOrderAssignmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 记录用户接单的信息Controller
 * 
 * @author ruoyi
 * @date 2024-06-18
 */
@RestController
@RequestMapping("/system/assignments")
public class BusOrderAssignmentsController extends BaseController
{
    @Autowired
    private IBusOrderAssignmentsService busOrderAssignmentsService;

    /**
     * 查询记录用户接单的信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:assignments:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusOrderAssignments busOrderAssignments)
    {
        startPage();
        List<BusOrderAssignments> list = busOrderAssignmentsService.selectBusOrderAssignmentsList(busOrderAssignments);
        return getDataTable(list);
    }

    /**
     * 导出记录用户接单的信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:assignments:export')")
    @Log(title = "记录用户接单的信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusOrderAssignments busOrderAssignments)
    {
        List<BusOrderAssignments> list = busOrderAssignmentsService.selectBusOrderAssignmentsList(busOrderAssignments);
        ExcelUtil<BusOrderAssignments> util = new ExcelUtil<BusOrderAssignments>(BusOrderAssignments.class);
        util.exportExcel(response, list, "记录用户接单的信息数据");
    }

    /**
     * 获取记录用户接单的信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:assignments:query')")
    @GetMapping(value = "/{assignmentId}")
    public AjaxResult getInfo(@PathVariable("assignmentId") Long assignmentId)
    {
        return success(busOrderAssignmentsService.selectBusOrderAssignmentsByAssignmentId(assignmentId));
    }

    /**
     * 新增记录用户接单的信息
     */
    @PreAuthorize("@ss.hasPermi('system:assignments:add')")
    @Log(title = "记录用户接单的信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusOrderAssignments busOrderAssignments)
    {
        return toAjax(busOrderAssignmentsService.insertBusOrderAssignments(busOrderAssignments));
    }

    /**
     * 修改记录用户接单的信息
     */
    @PreAuthorize("@ss.hasPermi('system:assignments:edit')")
    @Log(title = "记录用户接单的信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusOrderAssignments busOrderAssignments)
    {
        return toAjax(busOrderAssignmentsService.updateBusOrderAssignments(busOrderAssignments));
    }

    /**
     * 删除记录用户接单的信息
     */
    @PreAuthorize("@ss.hasPermi('system:assignments:remove')")
    @Log(title = "记录用户接单的信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{assignmentIds}")
    public AjaxResult remove(@PathVariable Long[] assignmentIds)
    {
        return toAjax(busOrderAssignmentsService.deleteBusOrderAssignmentsByAssignmentIds(assignmentIds));
    }
}
