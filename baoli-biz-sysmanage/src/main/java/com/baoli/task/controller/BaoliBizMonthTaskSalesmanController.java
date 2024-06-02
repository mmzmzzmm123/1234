package com.baoli.task.controller;

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
import com.baoli.task.domain.BaoliBizMonthTaskSalesman;
import com.baoli.task.service.IBaoliBizMonthTaskSalesmanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 驻店月度任务Controller
 * 
 * @author niujs
 * @date 2024-05-14
 */
@RestController
@RequestMapping("/task/inStoreMonthlyTask")
public class BaoliBizMonthTaskSalesmanController extends BaseController
{
    @Autowired
    private IBaoliBizMonthTaskSalesmanService baoliBizMonthTaskSalesmanService;

    /**
     * 查询驻店月度任务列表
     */
    @PreAuthorize("@ss.hasPermi('task:inStoreMonthlyTask:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizMonthTaskSalesman baoliBizMonthTaskSalesman)
    {
        startPage();
        List<BaoliBizMonthTaskSalesman> list = baoliBizMonthTaskSalesmanService.selectBaoliBizMonthTaskSalesmanList(baoliBizMonthTaskSalesman);
        return getDataTable(list);
    }

    /**
     * 导出驻店月度任务列表
     */
    @PreAuthorize("@ss.hasPermi('task:inStoreMonthlyTask:export')")
    @Log(title = "驻店月度任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizMonthTaskSalesman baoliBizMonthTaskSalesman)
    {
        List<BaoliBizMonthTaskSalesman> list = baoliBizMonthTaskSalesmanService.selectBaoliBizMonthTaskSalesmanList(baoliBizMonthTaskSalesman);
        ExcelUtil<BaoliBizMonthTaskSalesman> util = new ExcelUtil<BaoliBizMonthTaskSalesman>(BaoliBizMonthTaskSalesman.class);
        util.exportExcel(response, list, "驻店月度任务数据");
    }

    /**
     * 获取驻店月度任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('task:inStoreMonthlyTask:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizMonthTaskSalesmanService.selectBaoliBizMonthTaskSalesmanById(id));
    }

    /**
     * 新增驻店月度任务
     */
    @PreAuthorize("@ss.hasPermi('task:inStoreMonthlyTask:add')")
    @Log(title = "驻店月度任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizMonthTaskSalesman baoliBizMonthTaskSalesman)
    {
        return toAjax(baoliBizMonthTaskSalesmanService.insertBaoliBizMonthTaskSalesman(baoliBizMonthTaskSalesman));
    }

    /**
     * 修改驻店月度任务
     */
    @PreAuthorize("@ss.hasPermi('task:inStoreMonthlyTask:edit')")
    @Log(title = "驻店月度任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizMonthTaskSalesman baoliBizMonthTaskSalesman)
    {
        return toAjax(baoliBizMonthTaskSalesmanService.updateBaoliBizMonthTaskSalesman(baoliBizMonthTaskSalesman));
    }

    /**
     * 删除驻店月度任务
     */
    @PreAuthorize("@ss.hasPermi('task:inStoreMonthlyTask:remove')")
    @Log(title = "驻店月度任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizMonthTaskSalesmanService.deleteBaoliBizMonthTaskSalesmanByIds(ids));
    }
}
