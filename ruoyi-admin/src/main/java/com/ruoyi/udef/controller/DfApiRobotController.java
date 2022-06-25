package com.ruoyi.udef.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.udef.domain.DfApiRobot;
import com.ruoyi.udef.service.IDfApiRobotService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 机器人Controller
 *
 * @author ruoyi
 * @date 2022-06-08
 */
@RestController
@RequestMapping("/udef/robot")
public class DfApiRobotController extends BaseController {
    @Autowired
    private IDfApiRobotService dfApiRobotService;

    /**
     * 查询机器人列表
     */
    @PreAuthorize("@ss.hasPermi('udef:robot:list')")
    @GetMapping("/list")
    public TableDataInfo list(DfApiRobot dfApiRobot) {
        startPage();
        List<DfApiRobot> list = dfApiRobotService.selectDfApiRobotList(dfApiRobot);
        return getDataTable(list);
    }

    /**
     * 导出机器人列表
     */
    @PreAuthorize("@ss.hasPermi('udef:robot:export')")
    @Log(title = "机器人", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DfApiRobot dfApiRobot) {
        List<DfApiRobot> list = dfApiRobotService.selectDfApiRobotList(dfApiRobot);
        ExcelUtil<DfApiRobot> util = new ExcelUtil<DfApiRobot>(DfApiRobot.class);
        util.exportExcel(response, list, "机器人数据");
    }

    /**
     * 获取机器人详细信息
     */
    @PreAuthorize("@ss.hasPermi('udef:robot:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(dfApiRobotService.selectDfApiRobotById(id));
    }

    /**
     * 新增机器人
     */
    @PreAuthorize("@ss.hasPermi('udef:robot:add')")
    @Log(title = "机器人", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DfApiRobot dfApiRobot) {
        return toAjax(dfApiRobotService.insertDfApiRobot(dfApiRobot));
    }

    /**
     * 修改机器人
     */
    @PreAuthorize("@ss.hasPermi('udef:robot:edit')")
    @Log(title = "机器人", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DfApiRobot dfApiRobot) {
        return toAjax(dfApiRobotService.updateDfApiRobot(dfApiRobot));
    }

    /**
     * 初始化机器人
     */
    @PreAuthorize("@ss.hasPermi('udef:robot:edit')")
    @Log(title = "机器人", businessType = BusinessType.UPDATE)
    @PutMapping("/action/{id}")
    public AjaxResult actionOnRobot(@PathVariable("id") Long id, @RequestParam String opt) {
        return toAjax(dfApiRobotService.optOnRobot(id, opt));
    }

    /**
     * 删除机器人
     */
    @PreAuthorize("@ss.hasPermi('udef:robot:remove')")
    @Log(title = "机器人", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(dfApiRobotService.deleteDfApiRobotByIds(ids));
    }
}
