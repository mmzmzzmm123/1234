package com.ruoyi.web.controller.jdgl;

import java.util.Date;
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
import com.ruoyi.jdgl.domain.TsbzJdglzdjs;
import com.ruoyi.jdgl.service.ITsbzJdglzdjsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 基地管理制度建设Controller
 * 
 * @author tsbz
 * @date 2021-05-11
 */
@RestController
@RequestMapping("/jdgl/jdglzdjs")
public class TsbzJdglzdjsController extends BaseController
{
    @Autowired
    private ITsbzJdglzdjsService tsbzJdglzdjsService;

    /**
     * 查询基地管理制度建设列表
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglzdjs:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJdglzdjs tsbzJdglzdjs)
    {
        startPage();
        List<TsbzJdglzdjs> list = tsbzJdglzdjsService.selectTsbzJdglzdjsList(tsbzJdglzdjs);
        return getDataTable(list);
    }

    /**
     * 导出基地管理制度建设列表
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglzdjs:export')")
    @Log(title = "基地管理制度建设", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJdglzdjs tsbzJdglzdjs)
    {
        List<TsbzJdglzdjs> list = tsbzJdglzdjsService.selectTsbzJdglzdjsList(tsbzJdglzdjs);
        ExcelUtil<TsbzJdglzdjs> util = new ExcelUtil<TsbzJdglzdjs>(TsbzJdglzdjs.class);
        return util.exportExcel(list, "jdglzdjs");
    }

    /**
     * 获取基地管理制度建设详细信息
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglzdjs:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzJdglzdjsService.selectTsbzJdglzdjsById(id));
    }

    /**
     * 新增基地管理制度建设
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglzdjs:add')")
    @Log(title = "基地管理制度建设", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJdglzdjs tsbzJdglzdjs)
    {
        tsbzJdglzdjs.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        tsbzJdglzdjs.setSctime(new Date());
        tsbzJdglzdjs.setCreateTime(new Date());
        return toAjax(tsbzJdglzdjsService.insertTsbzJdglzdjs(tsbzJdglzdjs));
    }

    /**
     * 修改基地管理制度建设
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglzdjs:edit')")
    @Log(title = "基地管理制度建设", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJdglzdjs tsbzJdglzdjs)
    {
        return toAjax(tsbzJdglzdjsService.updateTsbzJdglzdjs(tsbzJdglzdjs));
    }

    /**
     * 删除基地管理制度建设
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglzdjs:remove')")
    @Log(title = "基地管理制度建设", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzJdglzdjsService.deleteTsbzJdglzdjsByIds(ids));
    }
}
