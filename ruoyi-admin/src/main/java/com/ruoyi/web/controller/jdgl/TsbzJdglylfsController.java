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
import com.ruoyi.jdgl.domain.TsbzJdglylfs;
import com.ruoyi.jdgl.service.ITsbzJdglylfsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 基地管理引领辐射Controller
 * 
 * @author tsbz
 * @date 2021-05-17
 */
@RestController
@RequestMapping("/jdgl/jdglylfs")
public class TsbzJdglylfsController extends BaseController
{
    @Autowired
    private ITsbzJdglylfsService tsbzJdglylfsService;

    /**
     * 查询基地管理引领辐射列表
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglylfs:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJdglylfs tsbzJdglylfs)
    {
        tsbzJdglylfs.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        startPage();
        List<TsbzJdglylfs> list = tsbzJdglylfsService.selectTsbzJdglylfsList(tsbzJdglylfs);
        return getDataTable(list);
    }

    /**
     * 导出基地管理引领辐射列表
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglylfs:export')")
    @Log(title = "基地管理引领辐射", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJdglylfs tsbzJdglylfs)
    {
        List<TsbzJdglylfs> list = tsbzJdglylfsService.selectTsbzJdglylfsList(tsbzJdglylfs);
        ExcelUtil<TsbzJdglylfs> util = new ExcelUtil<TsbzJdglylfs>(TsbzJdglylfs.class);
        return util.exportExcel(list, "jdglylfs");
    }

    /**
     * 获取基地管理引领辐射详细信息
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglylfs:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tsbzJdglylfsService.selectTsbzJdglylfsById(id));
    }

    /**
     * 新增基地管理引领辐射
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglylfs:add')")
    @Log(title = "基地管理引领辐射", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJdglylfs tsbzJdglylfs)
    {
        tsbzJdglylfs.setSctime(new Date());
        tsbzJdglylfs.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        tsbzJdglylfs.setCreateTime(new Date());
        return toAjax(tsbzJdglylfsService.insertTsbzJdglylfs(tsbzJdglylfs));
    }

    /**
     * 修改基地管理引领辐射
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglylfs:edit')")
    @Log(title = "基地管理引领辐射", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJdglylfs tsbzJdglylfs)
    {
        tsbzJdglylfs.setSctime(new Date());
        return toAjax(tsbzJdglylfsService.updateTsbzJdglylfs(tsbzJdglylfs));
    }

    /**
     * 删除基地管理引领辐射
     */
    @PreAuthorize("@ss.hasPermi('jdgl:jdglylfs:remove')")
    @Log(title = "基地管理引领辐射", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tsbzJdglylfsService.deleteTsbzJdglylfsByIds(ids));
    }
}
