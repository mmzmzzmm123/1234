package com.ruoyi.web.controller.jxjs;

import java.util.List;

import com.ruoyi.web.controller.common.SchoolCommonController;
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
import com.ruoyi.jxjs.domain.TsbzJdx;
import com.ruoyi.jxjs.service.ITsbzJdxService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 基地校Controller
 * 
 * @author ruoyi
 * @date 2020-08-20
 */
@RestController
@RequestMapping("/jxjs/jdx")
public class TsbzJdxController extends BaseController
{
    @Autowired
    private ITsbzJdxService tsbzJdxService;
    @Autowired
    private SchoolCommonController schoolCommonController;

    /**
     * 查询基地校列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jdx:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJdx tsbzJdx)
    {
        startPage();
        List<TsbzJdx> list = tsbzJdxService.selectTsbzJdxList(tsbzJdx);
        return getDataTable(list);
    }

    /**
     * 导出基地校列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jdx:export')")
    @Log(title = "基地校", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJdx tsbzJdx)
    {
        List<TsbzJdx> list = tsbzJdxService.selectTsbzJdxList(tsbzJdx);
        ExcelUtil<TsbzJdx> util = new ExcelUtil<TsbzJdx>(TsbzJdx.class);
        return util.exportExcel(list, "jdx");
    }

    /**
     * 获取基地校详细信息
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jdx:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(tsbzJdxService.selectTsbzJdxById(id));
    }

    /**
     * 新增基地校
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jdx:add')")
    @Log(title = "基地校", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJdx tsbzJdx)
    {
        tsbzJdx.setId(schoolCommonController.getUuid());
        return toAjax(tsbzJdxService.insertTsbzJdx(tsbzJdx));
    }

    /**
     * 修改基地校
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jdx:edit')")
    @Log(title = "基地校", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJdx tsbzJdx)
    {
        return toAjax(tsbzJdxService.updateTsbzJdx(tsbzJdx));
    }

    /**
     * 删除基地校
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jdx:remove')")
    @Log(title = "基地校", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(tsbzJdxService.deleteTsbzJdxByIds(ids));
    }
}
