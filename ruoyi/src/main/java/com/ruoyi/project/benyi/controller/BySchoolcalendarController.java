package com.ruoyi.project.benyi.controller;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.common.SchoolCommon;
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
import com.ruoyi.project.benyi.domain.BySchoolcalendar;
import com.ruoyi.project.benyi.service.IBySchoolcalendarService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 园历管理Controller
 *
 * @author tsbz
 * @date 2020-04-20
 */
@RestController
@RequestMapping("/benyi/schoolcalendar")
public class BySchoolcalendarController extends BaseController {
    @Autowired
    private IBySchoolcalendarService bySchoolcalendarService;
    @Autowired
    private SchoolCommon schoolCommon;

    /**
     * 查询园历管理列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:schoolcalendar:list')")
    @GetMapping("/list")
    public TableDataInfo list(BySchoolcalendar bySchoolcalendar) {
        startPage();
        List<BySchoolcalendar> list = bySchoolcalendarService.selectBySchoolcalendarList(bySchoolcalendar);
        return getDataTable(list);
    }

    /**
     * 导出园历管理列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:schoolcalendar:export')")
    @Log(title = "园历管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BySchoolcalendar bySchoolcalendar) {
        List<BySchoolcalendar> list = bySchoolcalendarService.selectBySchoolcalendarList(bySchoolcalendar);
        ExcelUtil<BySchoolcalendar> util = new ExcelUtil<BySchoolcalendar>(BySchoolcalendar.class);
        return util.exportExcel(list, "schoolcalendar");
    }

    /**
     * 获取园历管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:schoolcalendar:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(bySchoolcalendarService.selectBySchoolcalendarById(id));
    }

    /**
     * 新增园历管理
     */
    @PreAuthorize("@ss.hasPermi('benyi:schoolcalendar:add')")
    @Log(title = "园历管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BySchoolcalendar bySchoolcalendar) {
        //首先判断 当前用户是否为学校
        if (schoolCommon.isSchool()) {
            bySchoolcalendar.setCreatetime(new Date());
            String strXnxq = schoolCommon.getCurrentXnXq(bySchoolcalendar.getActivitytime());
            bySchoolcalendar.setXnxq(strXnxq);
            bySchoolcalendar.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
            bySchoolcalendar.setDeptid(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
            return toAjax(bySchoolcalendarService.insertBySchoolcalendar(bySchoolcalendar));
        } else {
            return AjaxResult.error("当前用户非幼儿园，无法创建园历");
        }
    }

    /**
     * 修改园历管理
     */
    @PreAuthorize("@ss.hasPermi('benyi:schoolcalendar:edit')")
    @Log(title = "园历管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BySchoolcalendar bySchoolcalendar) {
        //首先判断 当前用户是否为学校
        if (schoolCommon.isSchool()) {
            return toAjax(bySchoolcalendarService.updateBySchoolcalendar(bySchoolcalendar));
        } else {
            return AjaxResult.error("当前用户非幼儿园，无法编辑园历");
        }
    }

    /**
     * 删除园历管理
     */
    @PreAuthorize("@ss.hasPermi('benyi:schoolcalendar:remove')")
    @Log(title = "园历管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        //首先判断 当前用户是否为学校
        if (schoolCommon.isSchool()) {
            return toAjax(bySchoolcalendarService.deleteBySchoolcalendarByIds(ids));
        } else {
            return AjaxResult.error("当前用户非幼儿园，无法删除园历");
        }
    }
}
