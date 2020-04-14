package com.ruoyi.project.system.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import com.ruoyi.project.system.domain.ByClass;
import com.ruoyi.project.system.service.IByClassService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 班级信息Controller
 *
 * @author tsbz
 * @date 2020-04-14
 */
@RestController
@RequestMapping("/system/class")
public class ByClassController extends BaseController {
    @Autowired
    private IByClassService byClassService;

    /**
     * 查询班级信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:class:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByClass byClass) {
        startPage();
        List<ByClass> list = byClassService.selectByClassList(byClass);
        return getDataTable(list);
    }

    /**
     * 导出班级信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:class:export')")
    @Log(title = "班级信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByClass byClass) {
        List<ByClass> list = byClassService.selectByClassList(byClass);
        ExcelUtil<ByClass> util = new ExcelUtil<ByClass>(ByClass.class);
        return util.exportExcel(list, "class");
    }

    /**
     * 获取班级信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:class:query')")
    @GetMapping(value = "/{bjbh}")
    public AjaxResult getInfo(@PathVariable("bjbh") String bjbh) {
        return AjaxResult.success(byClassService.selectByClassById(bjbh));
    }

    /**
     * 新增班级信息
     */
    @PreAuthorize("@ss.hasPermi('system:class:add')")
    @Log(title = "班级信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByClass byClass) {
        String strBjbh = UUID.randomUUID().toString().replace("-","");
        System.out.println("bjbh:==" + strBjbh);
        byClass.setBjbh(strBjbh);
        byClass.setCreatetime(new Date());
        return toAjax(byClassService.insertByClass(byClass));
    }

    /**
     * 修改班级信息
     */
    @PreAuthorize("@ss.hasPermi('system:class:edit')")
    @Log(title = "班级信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByClass byClass) {
        return toAjax(byClassService.updateByClass(byClass));
    }

    /**
     * 删除班级信息
     */
    @PreAuthorize("@ss.hasPermi('system:class:remove')")
    @Log(title = "班级信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{bjbhs}")
    public AjaxResult remove(@PathVariable String[] bjbhs) {
        return toAjax(byClassService.deleteByClassByIds(bjbhs));
    }
}