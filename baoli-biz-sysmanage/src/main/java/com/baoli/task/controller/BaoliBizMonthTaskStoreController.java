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
import com.baoli.task.domain.BaoliBizMonthTaskStore;
import com.baoli.task.service.IBaoliBizMonthTaskStoreService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 商户月度任务Controller
 * 
 * @author niujs
 * @date 2024-05-14
 */
@RestController
@RequestMapping("/task/storeMonthlyTask")
public class BaoliBizMonthTaskStoreController extends BaseController
{
    @Autowired
    private IBaoliBizMonthTaskStoreService baoliBizMonthTaskStoreService;

    /**
     * 查询商户月度任务列表
     */
    @PreAuthorize("@ss.hasPermi('task:storeMonthlyTask:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizMonthTaskStore baoliBizMonthTaskStore)
    {
        startPage();
        List<BaoliBizMonthTaskStore> list = baoliBizMonthTaskStoreService.selectBaoliBizMonthTaskStoreList(baoliBizMonthTaskStore);
        return getDataTable(list);
    }

    /**
     * 导出商户月度任务列表
     */
    @PreAuthorize("@ss.hasPermi('task:storeMonthlyTask:export')")
    @Log(title = "商户月度任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizMonthTaskStore baoliBizMonthTaskStore)
    {
        List<BaoliBizMonthTaskStore> list = baoliBizMonthTaskStoreService.selectBaoliBizMonthTaskStoreList(baoliBizMonthTaskStore);
        ExcelUtil<BaoliBizMonthTaskStore> util = new ExcelUtil<BaoliBizMonthTaskStore>(BaoliBizMonthTaskStore.class);
        util.exportExcel(response, list, "商户月度任务数据");
    }

    /**
     * 获取商户月度任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('task:storeMonthlyTask:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizMonthTaskStoreService.selectBaoliBizMonthTaskStoreById(id));
    }

    /**
     * 新增商户月度任务
     */
    @PreAuthorize("@ss.hasPermi('task:storeMonthlyTask:add')")
    @Log(title = "商户月度任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizMonthTaskStore baoliBizMonthTaskStore)
    {
        return toAjax(baoliBizMonthTaskStoreService.insertBaoliBizMonthTaskStore(baoliBizMonthTaskStore));
    }

    /**
     * 修改商户月度任务
     */
    @PreAuthorize("@ss.hasPermi('task:storeMonthlyTask:edit')")
    @Log(title = "商户月度任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizMonthTaskStore baoliBizMonthTaskStore)
    {
        return toAjax(baoliBizMonthTaskStoreService.updateBaoliBizMonthTaskStore(baoliBizMonthTaskStore));
    }

    /**
     * 删除商户月度任务
     */
    @PreAuthorize("@ss.hasPermi('task:storeMonthlyTask:remove')")
    @Log(title = "商户月度任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizMonthTaskStoreService.deleteBaoliBizMonthTaskStoreByIds(ids));
    }
}
