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
import com.ruoyi.qtjskhgl.domain.TsbzQtjskhgcwjsj;
import com.ruoyi.qtjskhgl.service.ITsbzQtjskhgcwjsjService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 群体教师考核过程文件数据Controller
 *
 * @author ruoyi
 * @date 2020-09-17
 */
@RestController
@RequestMapping("/qtjskhgl/qtjskhgcwjsj")
public class TsbzQtjskhgcwjsjController extends BaseController {
    @Autowired
    private ITsbzQtjskhgcwjsjService tsbzQtjskhgcwjsjService;

    /**
     * 查询群体教师考核过程文件数据列表
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhgcsj:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzQtjskhgcwjsj tsbzQtjskhgcwjsj) {
        startPage();
        List<TsbzQtjskhgcwjsj> list = tsbzQtjskhgcwjsjService.selectTsbzQtjskhgcwjsjList(tsbzQtjskhgcwjsj);
        return getDataTable(list);
    }

    /**
     * 导出群体教师考核过程文件数据列表
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhgcsj:export')")
    @Log(title = "群体教师考核过程文件数据", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzQtjskhgcwjsj tsbzQtjskhgcwjsj) {
        List<TsbzQtjskhgcwjsj> list = tsbzQtjskhgcwjsjService.selectTsbzQtjskhgcwjsjList(tsbzQtjskhgcwjsj);
        ExcelUtil<TsbzQtjskhgcwjsj> util = new ExcelUtil<TsbzQtjskhgcwjsj>(TsbzQtjskhgcwjsj.class);
        return util.exportExcel(list, "qtjskhgcwjsj");
    }

    /**
     * 获取群体教师考核过程文件数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhgcsj:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzQtjskhgcwjsjService.selectTsbzQtjskhgcwjsjById(id));
    }

    /**
     * 新增群体教师考核过程文件数据
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhgcsj:add')")
    @Log(title = "群体教师考核过程文件数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzQtjskhgcwjsj tsbzQtjskhgcwjsj) {
        return toAjax(tsbzQtjskhgcwjsjService.insertTsbzQtjskhgcwjsj(tsbzQtjskhgcwjsj));
    }

    /**
     * 修改群体教师考核过程文件数据
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhgcsj:edit')")
    @Log(title = "群体教师考核过程文件数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzQtjskhgcwjsj tsbzQtjskhgcwjsj) {
        return toAjax(tsbzQtjskhgcwjsjService.updateTsbzQtjskhgcwjsj(tsbzQtjskhgcwjsj));
    }

    /**
     * 删除群体教师考核过程文件数据
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhgcsj:remove')")
    @Log(title = "群体教师考核过程文件数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzQtjskhgcwjsjService.deleteTsbzQtjskhgcwjsjByIds(ids));
    }
}
