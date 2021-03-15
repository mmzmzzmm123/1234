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
import com.ruoyi.jyykhgl.domain.TsbzJyydwjs;
import com.ruoyi.jyykhgl.service.ITsbzJyydwjsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 队伍建设（教研员）Controller
 *
 * @author tsbz
 * @date 2021-01-15
 */
@RestController
@RequestMapping("/jyykhgl/jyydwjs")
public class TsbzJyydwjsController extends BaseController {
    @Autowired
    private ITsbzJyydwjsService tsbzJyydwjsService;

    /**
     * 查询队伍建设（教研员）列表
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyydwjs:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJyydwjs tsbzJyydwjs) {
        tsbzJyydwjs.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        startPage();
        List<TsbzJyydwjs> list = tsbzJyydwjsService.selectTsbzJyydwjsList(tsbzJyydwjs);
        return getDataTable(list);
    }

    /**
     * 查询队伍建设（教研员）列表
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyydwjs:list')")
    @GetMapping("/liststatistics")
    public TableDataInfo liststatistics(TsbzJyydwjs tsbzJyydwjs) {
        startPage();
        List<TsbzJyydwjs> list = tsbzJyydwjsService.selectTsbzJyydwjsList(tsbzJyydwjs);
        return getDataTable(list);
    }

    /**
     * 导出队伍建设（教研员）列表
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyydwjs:export')")
    @Log(title = "队伍建设（教研员）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJyydwjs tsbzJyydwjs) {
        List<TsbzJyydwjs> list = tsbzJyydwjsService.selectTsbzJyydwjsList(tsbzJyydwjs);
        ExcelUtil<TsbzJyydwjs> util = new ExcelUtil<TsbzJyydwjs>(TsbzJyydwjs.class);
        return util.exportExcel(list, "jyydwjs");
    }

    /**
     * 获取队伍建设（教研员）详细信息
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyydwjs:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzJyydwjsService.selectTsbzJyydwjsById(id));
    }

    /**
     * 新增队伍建设（教研员）
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyydwjs:add')")
    @Log(title = "队伍建设（教研员）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJyydwjs tsbzJyydwjs) {
        tsbzJyydwjs.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        tsbzJyydwjs.setDeptId(SecurityUtils.getLoginUser().getUser().getDeptId());
        return toAjax(tsbzJyydwjsService.insertTsbzJyydwjs(tsbzJyydwjs));
    }

    /**
     * 修改队伍建设（教研员）
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyydwjs:edit')")
    @Log(title = "队伍建设（教研员）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJyydwjs tsbzJyydwjs) {
        return toAjax(tsbzJyydwjsService.updateTsbzJyydwjs(tsbzJyydwjs));
    }

    /**
     * 删除队伍建设（教研员）
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyydwjs:remove')")
    @Log(title = "队伍建设（教研员）", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzJyydwjsService.deleteTsbzJyydwjsByIds(ids));
    }
}
