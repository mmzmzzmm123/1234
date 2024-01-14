package com.ruoyi.web.controller.circuit;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.domain.Sdhcircuit;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.service.ISdhcircuitService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 电路信息Controller
 *
 * @author ruoyi
 * @date 2023-12-23
 */

@Api(tags = "电路信息对象功能接口")
@RestController
@RequestMapping("/system/sdhcircuit")
public class SdhcircuitController extends BaseController {
    @Autowired
    private ISdhcircuitService sdhcircuitService;

    /**
     * 查询电路信息列表
     */
    @ApiOperation("分页查询")
    @PreAuthorize("@ss.hasPermi('system:sdhcircuit:list')")
    @GetMapping("/list")
    public TableDataInfo list(Sdhcircuit sdhcircuit) {
        startPage();
        List<Sdhcircuit> list = sdhcircuitService.selectSdhcircuitList(sdhcircuit);
        return getDataTable(list);
    }

    /**
     * 导出电路信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:sdhcircuit:export')")
    @Log(title = "电路信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Sdhcircuit sdhcircuit) {
//        List<Sdhcircuit> list = sdhcircuitService.selectSdhcircuitList(sdhcircuit);
        List<Sdhcircuit> list = sdhcircuitService.exportSdhcircuitList(sdhcircuit);
        ExcelUtil<Sdhcircuit> util = new ExcelUtil<Sdhcircuit>(Sdhcircuit.class);
        util.exportExcel(response, list, "电路信息数据");
    }

    /**
     * 获取电路信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:sdhcircuit:query')")
    @ApiOperation("通过ID查询单条数据")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(sdhcircuitService.selectSdhcircuitById(id));
    }

    /**
     * 新增电路信息
     */
    @PreAuthorize("@ss.hasPermi('system:sdhcircuit:add')")
    @ApiOperation("新增数据")
    @Log(title = "电路信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Sdhcircuit sdhcircuit) {
        return toAjax(sdhcircuitService.insertSdhcircuit(sdhcircuit));
    }

    /**
     * 修改电路信息
     */
    @PreAuthorize("@ss.hasPermi('system:sdhcircuit:edit')")
    @ApiOperation("更新数据")
    @Log(title = "电路信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Sdhcircuit sdhcircuit) {
//        sdhcircuit.setUpdateTime();
        return toAjax(sdhcircuitService.updateSdhcircuit(sdhcircuit));
    }

    /**
     * 删除电路信息
     */
    @PreAuthorize("@ss.hasPermi('system:sdhcircuit:remove')")
    @ApiOperation("通过主键删除数据")
    @Log(title = "电路信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sdhcircuitService.deleteSdhcircuitByIds(ids));
    }
}
