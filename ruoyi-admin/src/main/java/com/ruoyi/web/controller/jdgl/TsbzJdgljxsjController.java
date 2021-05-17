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
import com.ruoyi.jdgl.domain.TsbzJdgljxsj;
import com.ruoyi.jdgl.service.ITsbzJdgljxsjService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 基地管理教学实践Controller
 * 
 * @author tsbz
 * @date 2021-05-17
 */
@RestController
@RequestMapping("/jdgl/jdgljxsj")
public class TsbzJdgljxsjController extends BaseController
{
    @Autowired
    private ITsbzJdgljxsjService tsbzJdgljxsjService;

    /**
     * 查询基地管理教学实践列表
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdgljxsj:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJdgljxsj tsbzJdgljxsj)
    {
        startPage();
        List<TsbzJdgljxsj> list = tsbzJdgljxsjService.selectTsbzJdgljxsjList(tsbzJdgljxsj);
        return getDataTable(list);
    }

    /**
     * 导出基地管理教学实践列表
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdgljxsj:export')")
    @Log(title = "基地管理教学实践", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJdgljxsj tsbzJdgljxsj)
    {
        List<TsbzJdgljxsj> list = tsbzJdgljxsjService.selectTsbzJdgljxsjList(tsbzJdgljxsj);
        ExcelUtil<TsbzJdgljxsj> util = new ExcelUtil<TsbzJdgljxsj>(TsbzJdgljxsj.class);
        return util.exportExcel(list, "jdgljxsj");
    }

    /**
     * 获取基地管理教学实践详细信息
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdgljxsj:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzJdgljxsjService.selectTsbzJdgljxsjById(id));
    }

    /**
     * 新增基地管理教学实践
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdgljxsj:add')")
    @Log(title = "基地管理教学实践", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJdgljxsj tsbzJdgljxsj)
    {
        tsbzJdgljxsj.setCreateUser(SecurityUtils.getLoginUser().getUser().getUserId());
        tsbzJdgljxsj.setCreateTime(new Date());
        return toAjax(tsbzJdgljxsjService.insertTsbzJdgljxsj(tsbzJdgljxsj));
    }

    /**
     * 修改基地管理教学实践
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdgljxsj:edit')")
    @Log(title = "基地管理教学实践", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJdgljxsj tsbzJdgljxsj)
    {
        return toAjax(tsbzJdgljxsjService.updateTsbzJdgljxsj(tsbzJdgljxsj));
    }

    /**
     * 删除基地管理教学实践
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdgljxsj:remove')")
    @Log(title = "基地管理教学实践", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzJdgljxsjService.deleteTsbzJdgljxsjByIds(ids));
    }
}
