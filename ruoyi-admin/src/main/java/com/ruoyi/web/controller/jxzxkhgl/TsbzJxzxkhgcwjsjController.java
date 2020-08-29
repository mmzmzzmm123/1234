package com.ruoyi.web.controller.jxzxkhgl;

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
import com.ruoyi.jxzxkhgl.domain.TsbzJxzxkhgcwjsj;
import com.ruoyi.jxzxkhgl.service.ITsbzJxzxkhgcwjsjService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 考核过程文件数据Controller
 *
 * @author ruoyi
 * @date 2020-08-29
 */
@RestController
@RequestMapping("/jxzxkhgl/jxzxkhgcwjsj")
public class TsbzJxzxkhgcwjsjController extends BaseController {
    @Autowired
    private ITsbzJxzxkhgcwjsjService tsbzJxzxkhgcwjsjService;

    /**
     * 查询考核过程文件数据列表
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhgcwjsj:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJxzxkhgcwjsj tsbzJxzxkhgcwjsj) {
        startPage();
        List<TsbzJxzxkhgcwjsj> list = tsbzJxzxkhgcwjsjService.selectTsbzJxzxkhgcwjsjList(tsbzJxzxkhgcwjsj);
        return getDataTable(list);
    }

    /**
     * 导出考核过程文件数据列表
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhgcwjsj:export')")
    @Log(title = "考核过程文件数据", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJxzxkhgcwjsj tsbzJxzxkhgcwjsj) {
        List<TsbzJxzxkhgcwjsj> list = tsbzJxzxkhgcwjsjService.selectTsbzJxzxkhgcwjsjList(tsbzJxzxkhgcwjsj);
        ExcelUtil<TsbzJxzxkhgcwjsj> util = new ExcelUtil<TsbzJxzxkhgcwjsj>(TsbzJxzxkhgcwjsj.class);
        return util.exportExcel(list, "jxzxkhgcwjsj");
    }

    /**
     * 获取考核过程文件数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhgcwjsj:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzJxzxkhgcwjsjService.selectTsbzJxzxkhgcwjsjById(id));
    }

    /**
     * 新增考核过程文件数据
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhgcwjsj:add')")
    @Log(title = "考核过程文件数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJxzxkhgcwjsj tsbzJxzxkhgcwjsj) {
        return toAjax(tsbzJxzxkhgcwjsjService.insertTsbzJxzxkhgcwjsj(tsbzJxzxkhgcwjsj));
    }

    /**
     * 修改考核过程文件数据
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhgcwjsj:edit')")
    @Log(title = "考核过程文件数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJxzxkhgcwjsj tsbzJxzxkhgcwjsj) {
        return toAjax(tsbzJxzxkhgcwjsjService.updateTsbzJxzxkhgcwjsj(tsbzJxzxkhgcwjsj));
    }

    /**
     * 删除考核过程文件数据
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhgcwjsj:remove')")
    @Log(title = "考核过程文件数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzJxzxkhgcwjsjService.deleteTsbzJxzxkhgcwjsjByIds(ids));
    }
}
