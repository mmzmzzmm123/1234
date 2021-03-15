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
import com.ruoyi.jyykhgl.domain.TsbzJyyjhzj;
import com.ruoyi.jyykhgl.service.ITsbzJyyjhzjService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 计划总结（教研员）Controller
 *
 * @author tsbz
 * @date 2021-01-15
 */
@RestController
@RequestMapping("/jyykhgl/jyyjhzj")
public class TsbzJyyjhzjController extends BaseController {
    @Autowired
    private ITsbzJyyjhzjService tsbzJyyjhzjService;

    /**
     * 查询计划总结（教研员）列表
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyyjhzj:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJyyjhzj tsbzJyyjhzj) {
        tsbzJyyjhzj.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        startPage();
        List<TsbzJyyjhzj> list = tsbzJyyjhzjService.selectTsbzJyyjhzjList(tsbzJyyjhzj);
        return getDataTable(list);
    }

    /**
     * 查询计划总结（教研员）列表
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyyjhzj:list')")
    @GetMapping("/liststatistics")
    public TableDataInfo liststatistics(TsbzJyyjhzj tsbzJyyjhzj) {
        System.out.println(tsbzJyyjhzj.getCreateUserid());
        startPage();
        List<TsbzJyyjhzj> list = tsbzJyyjhzjService.selectTsbzJyyjhzjList(tsbzJyyjhzj);
        return getDataTable(list);
    }

    /**
     * 导出计划总结（教研员）列表
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyyjhzj:export')")
    @Log(title = "计划总结（教研员）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJyyjhzj tsbzJyyjhzj) {
        List<TsbzJyyjhzj> list = tsbzJyyjhzjService.selectTsbzJyyjhzjList(tsbzJyyjhzj);
        ExcelUtil<TsbzJyyjhzj> util = new ExcelUtil<TsbzJyyjhzj>(TsbzJyyjhzj.class);
        return util.exportExcel(list, "jyyjhzj");
    }

    /**
     * 获取计划总结（教研员）详细信息
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyyjhzj:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzJyyjhzjService.selectTsbzJyyjhzjById(id));
    }

    /**
     * 新增计划总结（教研员）
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyyjhzj:add')")
    @Log(title = "计划总结（教研员）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJyyjhzj tsbzJyyjhzj) {
        tsbzJyyjhzj.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        tsbzJyyjhzj.setDeptId(SecurityUtils.getLoginUser().getUser().getDeptId());
        return toAjax(tsbzJyyjhzjService.insertTsbzJyyjhzj(tsbzJyyjhzj));
    }

    /**
     * 修改计划总结（教研员）
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyyjhzj:edit')")
    @Log(title = "计划总结（教研员）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJyyjhzj tsbzJyyjhzj) {
        return toAjax(tsbzJyyjhzjService.updateTsbzJyyjhzj(tsbzJyyjhzj));
    }

    /**
     * 删除计划总结（教研员）
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyyjhzj:remove')")
    @Log(title = "计划总结（教研员）", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzJyyjhzjService.deleteTsbzJyyjhzjByIds(ids));
    }
}
