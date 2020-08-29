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
import com.ruoyi.jxjs.domain.TsbzJzxzkhsh;
import com.ruoyi.jxjs.service.ITsbzJzxzkhshService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 考核审核过程Controller
 *
 * @author ruoyi
 * @date 2020-08-29
 */
@RestController
@RequestMapping("/jxjs/jzxzkhsh")
public class TsbzJzxzkhshController extends BaseController {
    @Autowired
    private ITsbzJzxzkhshService tsbzJzxzkhshService;

    /**
     * 查询考核审核过程列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jzxzkhsh:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJzxzkhsh tsbzJzxzkhsh) {
        startPage();
        List<TsbzJzxzkhsh> list = tsbzJzxzkhshService.selectTsbzJzxzkhshList(tsbzJzxzkhsh);
        return getDataTable(list);
    }

    /**
     * 导出考核审核过程列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jzxzkhsh:export')")
    @Log(title = "考核审核过程", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJzxzkhsh tsbzJzxzkhsh) {
        List<TsbzJzxzkhsh> list = tsbzJzxzkhshService.selectTsbzJzxzkhshList(tsbzJzxzkhsh);
        ExcelUtil<TsbzJzxzkhsh> util = new ExcelUtil<TsbzJzxzkhsh>(TsbzJzxzkhsh.class);
        return util.exportExcel(list, "jzxzkhsh");
    }

    /**
     * 获取考核审核过程详细信息
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jzxzkhsh:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzJzxzkhshService.selectTsbzJzxzkhshById(id));
    }

    /**
     * 新增考核审核过程
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jzxzkhsh:add')")
    @Log(title = "考核审核过程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJzxzkhsh tsbzJzxzkhsh) {
        return toAjax(tsbzJzxzkhshService.insertTsbzJzxzkhsh(tsbzJzxzkhsh));
    }

    /**
     * 修改考核审核过程
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jzxzkhsh:edit')")
    @Log(title = "考核审核过程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJzxzkhsh tsbzJzxzkhsh) {
        return toAjax(tsbzJzxzkhshService.updateTsbzJzxzkhsh(tsbzJzxzkhsh));
    }

    /**
     * 删除考核审核过程
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jzxzkhsh:remove')")
    @Log(title = "考核审核过程", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzJzxzkhshService.deleteTsbzJzxzkhshByIds(ids));
    }
}
