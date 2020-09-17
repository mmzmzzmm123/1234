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
import com.ruoyi.qtjskhgl.domain.TsbzQtjskhgcsj;
import com.ruoyi.qtjskhgl.service.ITsbzQtjskhgcsjService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 群体教师考核过程数据Controller
 *
 * @author ruoyi
 * @date 2020-09-17
 */
@RestController
@RequestMapping("/qtjskhgl/qtjskhgcsj")
public class TsbzQtjskhgcsjController extends BaseController {
    @Autowired
    private ITsbzQtjskhgcsjService tsbzQtjskhgcsjService;

    /**
     * 查询群体教师考核过程数据列表
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhgcsj:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzQtjskhgcsj tsbzQtjskhgcsj) {
        startPage();
        List<TsbzQtjskhgcsj> list = tsbzQtjskhgcsjService.selectTsbzQtjskhgcsjList(tsbzQtjskhgcsj);
        return getDataTable(list);
    }

    /**
     * 导出群体教师考核过程数据列表
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhgcsj:export')")
    @Log(title = "群体教师考核过程数据", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzQtjskhgcsj tsbzQtjskhgcsj) {
        List<TsbzQtjskhgcsj> list = tsbzQtjskhgcsjService.selectTsbzQtjskhgcsjList(tsbzQtjskhgcsj);
        ExcelUtil<TsbzQtjskhgcsj> util = new ExcelUtil<TsbzQtjskhgcsj>(TsbzQtjskhgcsj.class);
        return util.exportExcel(list, "qtjskhgcsj");
    }

    /**
     * 获取群体教师考核过程数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhgcsj:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(tsbzQtjskhgcsjService.selectTsbzQtjskhgcsjById(id));
    }

    /**
     * 新增群体教师考核过程数据
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhgcsj:edit')")
    @Log(title = "群体教师考核过程数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzQtjskhgcsj tsbzQtjskhgcsj) {
        return toAjax(tsbzQtjskhgcsjService.insertTsbzQtjskhgcsj(tsbzQtjskhgcsj));
    }

    /**
     * 修改群体教师考核过程数据
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhgcsj:edit')")
    @Log(title = "群体教师考核过程数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzQtjskhgcsj tsbzQtjskhgcsj) {
        return toAjax(tsbzQtjskhgcsjService.updateTsbzQtjskhgcsj(tsbzQtjskhgcsj));
    }

    /**
     * 删除群体教师考核过程数据
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhgcsj:remove')")
    @Log(title = "群体教师考核过程数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(tsbzQtjskhgcsjService.deleteTsbzQtjskhgcsjByIds(ids));
    }
}
