package com.ruoyi.web.controller.jyykhgl;

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
import com.ruoyi.jyykhgl.domain.TsbzJyyqtkh;
import com.ruoyi.jyykhgl.service.ITsbzJyyqtkhService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 其他考核（教研员）Controller
 *
 * @author tsbz
 * @date 2021-01-16
 */
@RestController
@RequestMapping("/jyykhgl/jyyqtkh")
public class TsbzJyyqtkhController extends BaseController {
    @Autowired
    private ITsbzJyyqtkhService tsbzJyyqtkhService;

    /**
     * 查询其他考核（教研员）列表
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyyqtkh:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJyyqtkh tsbzJyyqtkh) {
        tsbzJyyqtkh.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        startPage();
        List<TsbzJyyqtkh> list = tsbzJyyqtkhService.selectTsbzJyyqtkhList(tsbzJyyqtkh);
        return getDataTable(list);
    }

    /**
     * 查询其他考核（教研员）列表
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyyqtkh:list')")
    @GetMapping("/liststatistics")
    public TableDataInfo liststatistics(TsbzJyyqtkh tsbzJyyqtkh) {
        startPage();
        List<TsbzJyyqtkh> list = tsbzJyyqtkhService.selectTsbzJyyqtkhList(tsbzJyyqtkh);
        return getDataTable(list);
    }

    /**
     * 导出其他考核（教研员）列表
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyyqtkh:export')")
    @Log(title = "其他考核（教研员）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJyyqtkh tsbzJyyqtkh) {
        List<TsbzJyyqtkh> list = tsbzJyyqtkhService.selectTsbzJyyqtkhList(tsbzJyyqtkh);
        ExcelUtil<TsbzJyyqtkh> util = new ExcelUtil<TsbzJyyqtkh>(TsbzJyyqtkh.class);
        return util.exportExcel(list, "jyyqtkh");
    }

    /**
     * 获取其他考核（教研员）详细信息
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyyqtkh:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzJyyqtkhService.selectTsbzJyyqtkhById(id));
    }

    /**
     * 新增其他考核（教研员）
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyyqtkh:add')")
    @Log(title = "其他考核（教研员）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJyyqtkh tsbzJyyqtkh) {
        tsbzJyyqtkh.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        tsbzJyyqtkh.setDeptId(SecurityUtils.getLoginUser().getUser().getDeptId());
        return toAjax(tsbzJyyqtkhService.insertTsbzJyyqtkh(tsbzJyyqtkh));
    }

    /**
     * 修改其他考核（教研员）
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyyqtkh:edit')")
    @Log(title = "其他考核（教研员）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJyyqtkh tsbzJyyqtkh) {
        return toAjax(tsbzJyyqtkhService.updateTsbzJyyqtkh(tsbzJyyqtkh));
    }

    /**
     * 删除其他考核（教研员）
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyyqtkh:remove')")
    @Log(title = "其他考核（教研员）", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzJyyqtkhService.deleteTsbzJyyqtkhByIds(ids));
    }
}
