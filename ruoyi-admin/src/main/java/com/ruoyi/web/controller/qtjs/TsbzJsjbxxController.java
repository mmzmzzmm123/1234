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
import com.ruoyi.qtjs.domain.TsbzJsjbxx;
import com.ruoyi.qtjs.service.ITsbzJsjbxxService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 教师基本信息Controller
 * 
 * @author ruoyi
 * @date 2020-09-14
 */
@RestController
@RequestMapping("/qtjs/jsjbxx")
public class TsbzJsjbxxController extends BaseController
{
    @Autowired
    private ITsbzJsjbxxService tsbzJsjbxxService;

    /**
     * 查询教师基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('qtjs:jsjbxx:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJsjbxx tsbzJsjbxx)
    {
        startPage();
        List<TsbzJsjbxx> list = tsbzJsjbxxService.selectTsbzJsjbxxList(tsbzJsjbxx);
        return getDataTable(list);
    }

    /**
     * 导出教师基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('qtjs:jsjbxx:export')")
    @Log(title = "教师基本信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJsjbxx tsbzJsjbxx)
    {
        List<TsbzJsjbxx> list = tsbzJsjbxxService.selectTsbzJsjbxxList(tsbzJsjbxx);
        ExcelUtil<TsbzJsjbxx> util = new ExcelUtil<TsbzJsjbxx>(TsbzJsjbxx.class);
        return util.exportExcel(list, "jsjbxx");
    }

    /**
     * 获取教师基本信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('qtjs:jsjbxx:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(tsbzJsjbxxService.selectTsbzJsjbxxById(id));
    }

    /**
     * 新增教师基本信息
     */
    @PreAuthorize("@ss.hasPermi('qtjs:jsjbxx:add')")
    @Log(title = "教师基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJsjbxx tsbzJsjbxx)
    {
        return toAjax(tsbzJsjbxxService.insertTsbzJsjbxx(tsbzJsjbxx));
    }

    /**
     * 修改教师基本信息
     */
    @PreAuthorize("@ss.hasPermi('qtjs:jsjbxx:edit')")
    @Log(title = "教师基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJsjbxx tsbzJsjbxx)
    {
        return toAjax(tsbzJsjbxxService.updateTsbzJsjbxx(tsbzJsjbxx));
    }

    /**
     * 删除教师基本信息
     */
    @PreAuthorize("@ss.hasPermi('qtjs:jsjbxx:remove')")
    @Log(title = "教师基本信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(tsbzJsjbxxService.deleteTsbzJsjbxxByIds(ids));
    }
}
