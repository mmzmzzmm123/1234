package com.ruoyi.web.controller.gbxxgl;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
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
import com.ruoyi.gbxxgl.domain.TsbzGbryjl;
import com.ruoyi.gbxxgl.service.ITsbzGbryjlService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 荣誉奖励(干部管理)Controller
 *
 * @author tsbz
 * @date 2020-12-21
 */
@RestController
@RequestMapping("/gbxxgl/gbryjl")
public class TsbzGbryjlController extends BaseController {
    @Autowired
    private ITsbzGbryjlService tsbzGbryjlService;

    /**
     * 查询荣誉奖励(干部管理)列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbryjl:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzGbryjl tsbzGbryjl) {
        startPage();
        List<TsbzGbryjl> list = tsbzGbryjlService.selectTsbzGbryjlList(tsbzGbryjl);
        return getDataTable(list);
    }

    /**
     * 导出荣誉奖励(干部管理)列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbryjl:export')")
    @Log(title = "荣誉奖励(干部管理)", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzGbryjl tsbzGbryjl) {
        List<TsbzGbryjl> list = tsbzGbryjlService.selectTsbzGbryjlList(tsbzGbryjl);
        ExcelUtil<TsbzGbryjl> util = new ExcelUtil<TsbzGbryjl>(TsbzGbryjl.class);
        return util.exportExcel(list, "gbryjl");
    }

    /**
     * 获取荣誉奖励(干部管理)详细信息
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbryjl:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzGbryjlService.selectTsbzGbryjlById(id));
    }

    /**
     * 新增荣誉奖励(干部管理)
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbryjl:add')")
    @Log(title = "荣誉奖励(干部管理)", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzGbryjl tsbzGbryjl) {
        tsbzGbryjl.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(tsbzGbryjlService.insertTsbzGbryjl(tsbzGbryjl));
    }

    /**
     * 修改荣誉奖励(干部管理)
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbryjl:edit')")
    @Log(title = "荣誉奖励(干部管理)", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzGbryjl tsbzGbryjl) {
        return toAjax(tsbzGbryjlService.updateTsbzGbryjl(tsbzGbryjl));
    }

    /**
     * 删除荣誉奖励(干部管理)
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbryjl:remove')")
    @Log(title = "荣誉奖励(干部管理)", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzGbryjlService.deleteTsbzGbryjlByIds(ids));
    }
}
