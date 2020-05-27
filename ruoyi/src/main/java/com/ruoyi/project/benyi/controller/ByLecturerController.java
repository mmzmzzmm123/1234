package com.ruoyi.project.benyi.controller;

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
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.benyi.domain.ByLecturer;
import com.ruoyi.project.benyi.service.IByLecturerService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 讲师Controller
 * 
 * @author tsbz
 * @date 2020-05-25
 */
@RestController
@RequestMapping("/benyi/lecturer")
public class ByLecturerController extends BaseController
{
    @Autowired
    private IByLecturerService byLecturerService;

    /**
     * 查询讲师列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:lecturer:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByLecturer byLecturer)
    {
        startPage();
        List<ByLecturer> list = byLecturerService.selectByLecturerList(byLecturer);
        return getDataTable(list);
    }

    /**
     * 根据岗位id获取用户信息列表
     */
    @GetMapping("/listAll")
    public AjaxResult getAllLecturerList(ByLecturer byLecturer) {
        AjaxResult ajax = AjaxResult.success();
        List<ByLecturer> list = byLecturerService.selectByLecturerList(byLecturer);
        ajax.put("lecturer", list);

        return ajax;
    }

    /**
     * 导出讲师列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:lecturer:export')")
    @Log(title = "讲师", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByLecturer byLecturer)
    {
        List<ByLecturer> list = byLecturerService.selectByLecturerList(byLecturer);
        ExcelUtil<ByLecturer> util = new ExcelUtil<ByLecturer>(ByLecturer.class);
        return util.exportExcel(list, "lecturer");
    }

    /**
     * 获取讲师详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:lecturer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(byLecturerService.selectByLecturerById(id));
    }

    /**
     * 新增讲师
     */
    @PreAuthorize("@ss.hasPermi('benyi:lecturer:add')")
    @Log(title = "讲师", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByLecturer byLecturer)
    {
        byLecturer.setCreatetime(new Date());
        byLecturer.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(byLecturerService.insertByLecturer(byLecturer));
    }

    /**
     * 修改讲师
     */
    @PreAuthorize("@ss.hasPermi('benyi:lecturer:edit')")
    @Log(title = "讲师", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByLecturer byLecturer)
    {
        return toAjax(byLecturerService.updateByLecturer(byLecturer));
    }

    /**
     * 删除讲师
     */
    @PreAuthorize("@ss.hasPermi('benyi:lecturer:remove')")
    @Log(title = "讲师", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(byLecturerService.deleteByLecturerByIds(ids));
    }
}
