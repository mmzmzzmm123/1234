package com.ruoyi.web.controller.jdgl;

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
import com.ruoyi.jdgl.domain.TsbzJdglhdgl;
import com.ruoyi.jdgl.service.ITsbzJdglhdglService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 基地管理活动管理Controller
 * 
 * @author tsbz
 * @date 2021-05-13
 */
@RestController
@RequestMapping("/jdgl/jdglhdgl")
public class TsbzJdglhdglController extends BaseController
{
    @Autowired
    private ITsbzJdglhdglService tsbzJdglhdglService;

    /**
     * 查询基地管理活动管理列表
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglhdgl:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJdglhdgl tsbzJdglhdgl)
    {
        startPage();
        List<TsbzJdglhdgl> list = tsbzJdglhdglService.selectTsbzJdglhdglList(tsbzJdglhdgl);
        return getDataTable(list);
    }

    /**
     * 导出基地管理活动管理列表
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglhdgl:export')")
    @Log(title = "基地管理活动管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJdglhdgl tsbzJdglhdgl)
    {
        List<TsbzJdglhdgl> list = tsbzJdglhdglService.selectTsbzJdglhdglList(tsbzJdglhdgl);
        ExcelUtil<TsbzJdglhdgl> util = new ExcelUtil<TsbzJdglhdgl>(TsbzJdglhdgl.class);
        return util.exportExcel(list, "jdglhdgl");
    }

    /**
     * 获取基地管理活动管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglhdgl:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzJdglhdglService.selectTsbzJdglhdglById(id));
    }

    /**
     * 新增基地管理活动管理
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglhdgl:add')")
    @Log(title = "基地管理活动管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJdglhdgl tsbzJdglhdgl)
    {
        return toAjax(tsbzJdglhdglService.insertTsbzJdglhdgl(tsbzJdglhdgl));
    }

    /**
     * 修改基地管理活动管理
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglhdgl:edit')")
    @Log(title = "基地管理活动管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJdglhdgl tsbzJdglhdgl)
    {
        return toAjax(tsbzJdglhdglService.updateTsbzJdglhdgl(tsbzJdglhdgl));
    }

    /**
     * 删除基地管理活动管理
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglhdgl:remove')")
    @Log(title = "基地管理活动管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzJdglhdglService.deleteTsbzJdglhdglByIds(ids));
    }
}
