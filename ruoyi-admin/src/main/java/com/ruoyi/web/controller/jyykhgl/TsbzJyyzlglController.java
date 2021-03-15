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
import com.ruoyi.jyykhgl.domain.TsbzJyyzlgl;
import com.ruoyi.jyykhgl.service.ITsbzJyyzlglService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 质量管理（教研员）Controller
 *
 * @author tsbz
 * @date 2021-01-16
 */
@RestController
@RequestMapping("/jyykhgl/jyyzlgl")
public class TsbzJyyzlglController extends BaseController {
    @Autowired
    private ITsbzJyyzlglService tsbzJyyzlglService;

    /**
     * 查询质量管理（教研员）列表
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyyzlgl:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJyyzlgl tsbzJyyzlgl) {
        tsbzJyyzlgl.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        startPage();
        List<TsbzJyyzlgl> list = tsbzJyyzlglService.selectTsbzJyyzlglList(tsbzJyyzlgl);
        return getDataTable(list);
    }

    /**
     * 查询质量管理（教研员）列表
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyyzlgl:list')")
    @GetMapping("/liststatistics")
    public TableDataInfo liststatistics(TsbzJyyzlgl tsbzJyyzlgl) {
        startPage();
        List<TsbzJyyzlgl> list = tsbzJyyzlglService.selectTsbzJyyzlglList(tsbzJyyzlgl);
        return getDataTable(list);
    }

    /**
     * 导出质量管理（教研员）列表
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyyzlgl:export')")
    @Log(title = "质量管理（教研员）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJyyzlgl tsbzJyyzlgl) {
        List<TsbzJyyzlgl> list = tsbzJyyzlglService.selectTsbzJyyzlglList(tsbzJyyzlgl);
        ExcelUtil<TsbzJyyzlgl> util = new ExcelUtil<TsbzJyyzlgl>(TsbzJyyzlgl.class);
        return util.exportExcel(list, "jyyzlgl");
    }

    /**
     * 获取质量管理（教研员）详细信息
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyyzlgl:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzJyyzlglService.selectTsbzJyyzlglById(id));
    }

    /**
     * 新增质量管理（教研员）
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyyzlgl:add')")
    @Log(title = "质量管理（教研员）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJyyzlgl tsbzJyyzlgl) {
        tsbzJyyzlgl.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        tsbzJyyzlgl.setDeptId(SecurityUtils.getLoginUser().getUser().getDeptId());
        return toAjax(tsbzJyyzlglService.insertTsbzJyyzlgl(tsbzJyyzlgl));
    }

    /**
     * 修改质量管理（教研员）
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyyzlgl:edit')")
    @Log(title = "质量管理（教研员）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJyyzlgl tsbzJyyzlgl) {
        return toAjax(tsbzJyyzlglService.updateTsbzJyyzlgl(tsbzJyyzlgl));
    }

    /**
     * 删除质量管理（教研员）
     */
    @PreAuthorize("@ss.hasPermi('jyykhgl:jyyzlgl:remove')")
    @Log(title = "质量管理（教研员）", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzJyyzlglService.deleteTsbzJyyzlglByIds(ids));
    }
}
