package com.ruoyi.project.system.controller;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.system.service.ISysUserService;
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
import com.ruoyi.project.system.domain.ByTeacherJbxx;
import com.ruoyi.project.system.service.IByTeacherJbxxService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 教师基本信息Controller
 * 
 * @author tsbz
 * @date 2020-04-21
 */
@RestController
@RequestMapping("/system/teacher")
public class ByTeacherJbxxController extends BaseController
{
    @Autowired
    private IByTeacherJbxxService byTeacherJbxxService;

    @Autowired
    private ISysUserService userService;

    /**
     * 查询教师基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:teacher:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByTeacherJbxx byTeacherJbxx)
    {
        startPage();
        List<ByTeacherJbxx> list = byTeacherJbxxService.selectByTeacherJbxxList(byTeacherJbxx);
        return getDataTable(list);
    }

    //不分页查询教师信息列表
    @PreAuthorize("@ss.hasPermi('system:teacher:list')")
    @GetMapping("/list2")
    public TableDataInfo list2(ByTeacherJbxx byTeacherJbxx)
    {
        List<ByTeacherJbxx> list2 = byTeacherJbxxService.selectByTeacherJbxxList(byTeacherJbxx);
        System.out.println("-------------------"+list2);
        return getDataTable(list2);
    }

    /**
     * 查询教师基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:teacher:list')"+ "||@ss.hasPermi('system:user:list')")
    @GetMapping("/listgroupxw")
    public TableDataInfo listGroupXw(ByTeacherJbxx byTeacherJbxx)
    {
        List<ByTeacherJbxx> list = byTeacherJbxxService.selectByTeacherJbGroupXw(byTeacherJbxx);
        return getDataTable(list);
    }

    /**
     * 导出教师基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:teacher:export')")
    @Log(title = "教师基本信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByTeacherJbxx byTeacherJbxx)
    {
        List<ByTeacherJbxx> list = byTeacherJbxxService.selectByTeacherJbxxList(byTeacherJbxx);
        ExcelUtil<ByTeacherJbxx> util = new ExcelUtil<ByTeacherJbxx>(ByTeacherJbxx.class);
        return util.exportExcel(list, "teacher");
    }

    /**
     * 获取教师基本信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:teacher:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(byTeacherJbxxService.selectByTeacherJbxxById(id));
    }

    /**
     * 新增教师基本信息
     */
    @PreAuthorize("@ss.hasPermi('system:teacher:add')")
    @Log(title = "教师基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByTeacherJbxx byTeacherJbxx)
    {
        byTeacherJbxx.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
        byTeacherJbxx.setCreatetime(new Date());
        return toAjax(byTeacherJbxxService.insertByTeacherJbxx(byTeacherJbxx));
    }

    /**
     * 修改教师基本信息
     */
    @PreAuthorize("@ss.hasPermi('system:teacher:edit')")
    @Log(title = "教师基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByTeacherJbxx byTeacherJbxx)
    {
        return toAjax(byTeacherJbxxService.updateByTeacherJbxx(byTeacherJbxx));
    }

    /**
     * 删除教师基本信息
     */
    @PreAuthorize("@ss.hasPermi('system:teacher:remove')")
    @Log(title = "教师基本信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(byTeacherJbxxService.deleteByTeacherJbxxByIds(ids));
    }
}
