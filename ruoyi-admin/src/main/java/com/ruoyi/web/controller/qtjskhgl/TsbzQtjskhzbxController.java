package com.ruoyi.web.controller.qtjskhgl;

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
import com.ruoyi.qtjskhgl.domain.TsbzQtjskhzbx;
import com.ruoyi.qtjskhgl.service.ITsbzQtjskhzbxService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 群体教师考核指标项Controller
 * 
 * @author ruoyi
 * @date 2020-09-17
 */
@RestController
@RequestMapping("/qtjskhgl/qtjskhzbx")
public class TsbzQtjskhzbxController extends BaseController
{
    @Autowired
    private ITsbzQtjskhzbxService tsbzQtjskhzbxService;

    /**
     * 查询群体教师考核指标项列表
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhzbx:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzQtjskhzbx tsbzQtjskhzbx)
    {
        startPage();
        List<TsbzQtjskhzbx> list = tsbzQtjskhzbxService.selectTsbzQtjskhzbxList(tsbzQtjskhzbx);
        return getDataTable(list);
    }

    /**
     * 导出群体教师考核指标项列表
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhzbx:export')")
    @Log(title = "群体教师考核指标项", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzQtjskhzbx tsbzQtjskhzbx)
    {
        List<TsbzQtjskhzbx> list = tsbzQtjskhzbxService.selectTsbzQtjskhzbxList(tsbzQtjskhzbx);
        ExcelUtil<TsbzQtjskhzbx> util = new ExcelUtil<TsbzQtjskhzbx>(TsbzQtjskhzbx.class);
        return util.exportExcel(list, "qtjskhzbx");
    }

    /**
     * 获取群体教师考核指标项详细信息
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhzbx:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzQtjskhzbxService.selectTsbzQtjskhzbxById(id));
    }

    /**
     * 新增群体教师考核指标项
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhzbx:add')")
    @Log(title = "群体教师考核指标项", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzQtjskhzbx tsbzQtjskhzbx)
    {
        return toAjax(tsbzQtjskhzbxService.insertTsbzQtjskhzbx(tsbzQtjskhzbx));
    }

    /**
     * 修改群体教师考核指标项
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhzbx:edit')")
    @Log(title = "群体教师考核指标项", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzQtjskhzbx tsbzQtjskhzbx)
    {
        return toAjax(tsbzQtjskhzbxService.updateTsbzQtjskhzbx(tsbzQtjskhzbx));
    }

    /**
     * 删除群体教师考核指标项
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhzbx:remove')")
    @Log(title = "群体教师考核指标项", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzQtjskhzbxService.deleteTsbzQtjskhzbxByIds(ids));
    }
}
