package com.ruoyi.web.controller.qtjs;

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
import com.ruoyi.qtjs.domain.TsbzXxjbxx;
import com.ruoyi.qtjs.service.ITsbzXxjbxxService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 学校信息Controller
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
@RestController
@RequestMapping("/qtjs/xxjbxx")
public class TsbzXxjbxxController extends BaseController
{
    @Autowired
    private ITsbzXxjbxxService tsbzXxjbxxService;

    /**
     * 查询学校信息列表
     */
    @PreAuthorize("@ss.hasPermi('qtjs:xxjbxx:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzXxjbxx tsbzXxjbxx)
    {
        startPage();
        List<TsbzXxjbxx> list = tsbzXxjbxxService.selectTsbzXxjbxxList(tsbzXxjbxx);
        return getDataTable(list);
    }

    /**
     * 导出学校信息列表
     */
    @PreAuthorize("@ss.hasPermi('qtjs:xxjbxx:export')")
    @Log(title = "学校信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzXxjbxx tsbzXxjbxx)
    {
        List<TsbzXxjbxx> list = tsbzXxjbxxService.selectTsbzXxjbxxList(tsbzXxjbxx);
        ExcelUtil<TsbzXxjbxx> util = new ExcelUtil<TsbzXxjbxx>(TsbzXxjbxx.class);
        return util.exportExcel(list, "xxjbxx");
    }

    /**
     * 获取学校信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('qtjs:xxjbxx:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(tsbzXxjbxxService.selectTsbzXxjbxxById(id));
    }

    /**
     * 新增学校信息
     */
    @PreAuthorize("@ss.hasPermi('qtjs:xxjbxx:add')")
    @Log(title = "学校信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzXxjbxx tsbzXxjbxx)
    {
        return toAjax(tsbzXxjbxxService.insertTsbzXxjbxx(tsbzXxjbxx));
    }

    /**
     * 修改学校信息
     */
    @PreAuthorize("@ss.hasPermi('qtjs:xxjbxx:edit')")
    @Log(title = "学校信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzXxjbxx tsbzXxjbxx)
    {
        return toAjax(tsbzXxjbxxService.updateTsbzXxjbxx(tsbzXxjbxx));
    }

    /**
     * 删除学校信息
     */
    @PreAuthorize("@ss.hasPermi('qtjs:xxjbxx:remove')")
    @Log(title = "学校信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(tsbzXxjbxxService.deleteTsbzXxjbxxByIds(ids));
    }
}
