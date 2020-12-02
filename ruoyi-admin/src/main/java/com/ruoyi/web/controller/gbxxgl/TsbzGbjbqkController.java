package com.ruoyi.web.controller.gbxxgl;

import java.util.List;
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
import com.ruoyi.gbxxgl.domain.TsbzGbjbqk;
import com.ruoyi.gbxxgl.service.ITsbzGbjbqkService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 干部基本情况Controller
 * 
 * @author ruoyi
 * @date 2020-12-02
 */
@RestController
@RequestMapping("/gbxxgl/gbjbqk")
public class TsbzGbjbqkController extends BaseController
{
    @Autowired
    private ITsbzGbjbqkService tsbzGbjbqkService;

    /**
     * 查询干部基本情况列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbjbqk:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzGbjbqk tsbzGbjbqk)
    {
        startPage();
        List<TsbzGbjbqk> list = tsbzGbjbqkService.selectTsbzGbjbqkList(tsbzGbjbqk);
        return getDataTable(list);
    }

    /**
     * 导出干部基本情况列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbjbqk:export')")
    @Log(title = "干部基本情况", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzGbjbqk tsbzGbjbqk)
    {
        List<TsbzGbjbqk> list = tsbzGbjbqkService.selectTsbzGbjbqkList(tsbzGbjbqk);
        ExcelUtil<TsbzGbjbqk> util = new ExcelUtil<TsbzGbjbqk>(TsbzGbjbqk.class);
        return util.exportExcel(list, "gbjbqk");
    }

    /**
     * 获取干部基本情况详细信息
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbjbqk:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzGbjbqkService.selectTsbzGbjbqkById(id));
    }

    /**
     * 新增干部基本情况
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbjbqk:add')")
    @Log(title = "干部基本情况", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzGbjbqk tsbzGbjbqk)
    {
        return toAjax(tsbzGbjbqkService.insertTsbzGbjbqk(tsbzGbjbqk));
    }

    /**
     * 修改干部基本情况
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbjbqk:edit')")
    @Log(title = "干部基本情况", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzGbjbqk tsbzGbjbqk)
    {
        return toAjax(tsbzGbjbqkService.updateTsbzGbjbqk(tsbzGbjbqk));
    }

    /**
     * 删除干部基本情况
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbjbqk:remove')")
    @Log(title = "干部基本情况", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzGbjbqkService.deleteTsbzGbjbqkByIds(ids));
    }
}
