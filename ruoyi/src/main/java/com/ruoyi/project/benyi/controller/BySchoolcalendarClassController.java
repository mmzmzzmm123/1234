package com.ruoyi.project.benyi.controller;

import java.util.ArrayList;
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
import com.ruoyi.project.benyi.domain.BySchoolcalendarClass;
import com.ruoyi.project.benyi.service.IBySchoolcalendarClassService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 园历管理(班级)Controller
 * 
 * @author tsbz
 * @date 2020-05-06
 */
@RestController
@RequestMapping("/benyi/schoolcalendarclass")
public class BySchoolcalendarClassController extends BaseController
{
    @Autowired
    private IBySchoolcalendarClassService bySchoolcalendarClassService;

    //导入学校公用类
    @Autowired
    private SchoolCommon schoolCommon;

    /**
     * 查询园历管理(班级)列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:schoolcalendarclass:list')")
    @GetMapping("/list")
    public TableDataInfo list(BySchoolcalendarClass bySchoolcalendarClass)
    {
        String strClassId = schoolCommon.getClassId();
        if(!schoolCommon.isStringEmpty(strClassId)){
            startPage();
            bySchoolcalendarClass.setClassid(strClassId);
            List<BySchoolcalendarClass> list = bySchoolcalendarClassService.selectBySchoolcalendarClassList(bySchoolcalendarClass);
            //System.out.println("---------------------分页"+list);
            return getDataTable(list);
        }else{
            List<BySchoolcalendarClass> list =new ArrayList<>();
            return getDataTable(list);
        }

    }

    /**
     * 不分页查询园历管理(班级)列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:schoolcalendarclass:list')")
    @GetMapping("/listAll")
    public TableDataInfo listAll(BySchoolcalendarClass bySchoolcalendarClass)
    {
        List<BySchoolcalendarClass> listAll = bySchoolcalendarClassService.selectBySchoolcalendarClassList(bySchoolcalendarClass);
        //System.out.println("---------------------没有分页"+listAll);
        return getDataTable(listAll);
    }

    /**
     * 导出园历管理(班级)列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:schoolcalendarclass:export')")
    @Log(title = "园历管理(班级)", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BySchoolcalendarClass bySchoolcalendarClass)
    {
        List<BySchoolcalendarClass> list = bySchoolcalendarClassService.selectBySchoolcalendarClassList(bySchoolcalendarClass);
        ExcelUtil<BySchoolcalendarClass> util = new ExcelUtil<BySchoolcalendarClass>(BySchoolcalendarClass.class);
        return util.exportExcel(list, "schoolcalendarclass");
    }

    /**
     * 获取园历管理(班级)详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:schoolcalendarclass:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bySchoolcalendarClassService.selectBySchoolcalendarClassById(id));
    }

    /**
     * 新增园历管理(班级)
     */
    @PreAuthorize("@ss.hasPermi('benyi:schoolcalendarclass:add')")
    @Log(title = "园历管理(班级)", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BySchoolcalendarClass bySchoolcalendarClass)
    {
        String strClassId = schoolCommon.getClassId();
        //判断当前用户是否有班级
        if(!schoolCommon.isStringEmpty(strClassId)) {
            //设置创建时间为当前时间
            bySchoolcalendarClass.setCreatetime(new Date());
            //设置学年信息  从学校公共类调取方法 传入活动时间进行判断
            bySchoolcalendarClass.setXnxq(schoolCommon.getCurrentXnXq(bySchoolcalendarClass.getActivitytime()));
            //设置创建人id
            bySchoolcalendarClass.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
            //设置部门id
            bySchoolcalendarClass.setDeptid(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
            bySchoolcalendarClass.setClassid(strClassId);
            return toAjax(bySchoolcalendarClassService.insertBySchoolcalendarClass(bySchoolcalendarClass));
        }else {
            return AjaxResult.error("当前用户下没有班级，无法创建园历");
        }

    }

    /**
     * 修改园历管理(班级)
     */
    @PreAuthorize("@ss.hasPermi('benyi:schoolcalendarclass:edit')")
    @Log(title = "园历管理(班级)", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BySchoolcalendarClass bySchoolcalendarClass)
    {
        if(!schoolCommon.isStringEmpty(schoolCommon.getClassId())){
            return toAjax(bySchoolcalendarClassService.updateBySchoolcalendarClass(bySchoolcalendarClass));
        }else{
            return AjaxResult.error("当前用户下没有班级，无法修改园历");
        }
    }

    /**
     * 删除园历管理(班级)
     */
    @PreAuthorize("@ss.hasPermi('benyi:schoolcalendarclass:remove')")
    @Log(title = "园历管理(班级)", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        //判断当前用户下是否有班级
        if(!schoolCommon.isStringEmpty(schoolCommon.getClassId())) {
            return toAjax(bySchoolcalendarClassService.deleteBySchoolcalendarClassByIds(ids));
        }else {
            return AjaxResult.error("当前用户下没有班级,无法删除园历");
        }

    }
}
