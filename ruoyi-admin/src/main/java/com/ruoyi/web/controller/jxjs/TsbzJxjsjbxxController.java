package com.ruoyi.web.controller.jxjs;

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
import com.ruoyi.jxjs.domain.TsbzJxjsjbxx;
import com.ruoyi.jxjs.service.ITsbzJxjsjbxxService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 见习教师基本信息Controller
 * 
 * @author ruoyi
 * @date 2020-08-20
 */
@RestController
@RequestMapping("/jxjs/jxjsjbxx")
public class TsbzJxjsjbxxController extends BaseController
{
    @Autowired
    private ITsbzJxjsjbxxService tsbzJxjsjbxxService;

    /**
     * 查询见习教师基本信息列表，没有基地校的教师列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jdx:list')")
    @GetMapping("/listnotjdx")
    public TableDataInfo listnotjdx(TsbzJxjsjbxx tsbzJxjsjbxx)
    {
        startPage();
        List<TsbzJxjsjbxx> list = tsbzJxjsjbxxService.selectTsbzJxjsjbxxListnotjdx(tsbzJxjsjbxx);
        return getDataTable(list);
    }

    /**
     * 查询见习教师基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxjsjbxx:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJxjsjbxx tsbzJxjsjbxx)
    {
        startPage();
        List<TsbzJxjsjbxx> list = tsbzJxjsjbxxService.selectTsbzJxjsjbxxList(tsbzJxjsjbxx);
        return getDataTable(list);
    }

    /**
     * 导出见习教师基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxjsjbxx:export')")
    @Log(title = "见习教师基本信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJxjsjbxx tsbzJxjsjbxx)
    {
        List<TsbzJxjsjbxx> list = tsbzJxjsjbxxService.selectTsbzJxjsjbxxList(tsbzJxjsjbxx);
        ExcelUtil<TsbzJxjsjbxx> util = new ExcelUtil<TsbzJxjsjbxx>(TsbzJxjsjbxx.class);
        return util.exportExcel(list, "jxjsjbxx");
    }

    /**
     * 获取见习教师基本信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxjsjbxx:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzJxjsjbxxService.selectTsbzJxjsjbxxById(id));
    }

    /**
     * 新增见习教师基本信息
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxjsjbxx:add')")
    @Log(title = "见习教师基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJxjsjbxx tsbzJxjsjbxx)
    {
        return toAjax(tsbzJxjsjbxxService.insertTsbzJxjsjbxx(tsbzJxjsjbxx));
    }

    /**
     * 修改见习教师基本信息
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxjsjbxx:edit')")
    @Log(title = "见习教师基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJxjsjbxx tsbzJxjsjbxx)
    {
        return toAjax(tsbzJxjsjbxxService.updateTsbzJxjsjbxx(tsbzJxjsjbxx));
    }

    /**
     * 删除见习教师基本信息
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxjsjbxx:remove')")
    @Log(title = "见习教师基本信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzJxjsjbxxService.deleteTsbzJxjsjbxxByIds(ids));
    }
}
