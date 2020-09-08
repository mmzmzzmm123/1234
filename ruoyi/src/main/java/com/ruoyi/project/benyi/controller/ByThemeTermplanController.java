package com.ruoyi.project.benyi.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.benyi.domain.ByThemeTermplanitem;
import com.ruoyi.project.benyi.service.IByThemeTermplanitemService;
import com.ruoyi.project.common.SchoolCommon;
import com.ruoyi.project.system.service.IByClassService;
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
import com.ruoyi.project.benyi.domain.ByThemeTermplan;
import com.ruoyi.project.benyi.service.IByThemeTermplanService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 主题整合学期计划Controller
 *
 * @author tsbz
 * @date 2020-08-24
 */
@RestController
@RequestMapping("/benyi/themetermplan")
public class ByThemeTermplanController extends BaseController {
    @Autowired
    private IByThemeTermplanService byThemeTermplanService;
    @Autowired
    private SchoolCommon schoolCommon;
    @Autowired
    private IByClassService byClassService;
    @Autowired
    private IByThemeTermplanitemService byThemeTermplanitemService;
    @Autowired
    private ISysUserService userService;

    /**
     * 查询主题整合学期计划列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:themetermplan:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByThemeTermplan byThemeTermplan) {
        startPage();
        byThemeTermplan.setSchoolid(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
        String classId = schoolCommon.getClassId();
        List<ByThemeTermplan> list = null;
        //首先判断当前账户是否为幼儿园账号
        if (schoolCommon.isSchool() && !schoolCommon.isStringEmpty(classId)) {
            byThemeTermplan.setClassid(classId);
        }
        list = byThemeTermplanService.selectByThemeTermplanList(byThemeTermplan);
        return getDataTable(list);
    }

    /**
     * 导出主题整合学期计划列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:themetermplan:export')")
    @Log(title = "主题整合学期计划", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByThemeTermplan byThemeTermplan) {
        List<ByThemeTermplan> list = byThemeTermplanService.selectByThemeTermplanList(byThemeTermplan);
        ExcelUtil<ByThemeTermplan> util = new ExcelUtil<ByThemeTermplan>(ByThemeTermplan.class);
        return util.exportExcel(list, "themetermplan");
    }

    /**
     * 获取主题整合学期计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:themetermplan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        AjaxResult ajax = AjaxResult.success();
        ByThemeTermplan byThemeTermplan = byThemeTermplanService.selectByThemeTermplanById(id);
        ajax.put(AjaxResult.DATA_TAG, byThemeTermplan);
        ajax.put("classname", byClassService.selectByClassById(byThemeTermplan.getClassid()).getBjmc());
        ajax.put("createusername", userService.selectUserById(byThemeTermplan.getCreateuserid()).getNickName());
        return ajax;
    }

    /**
     * 新增主题整合学期计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:themetermplan:add')")
    @Log(title = "主题整合学期计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByThemeTermplan byThemeTermplan) {
        String classId = schoolCommon.getClassId();
        //首先判断当前账户是否为幼儿园账号
        if (schoolCommon.isSchool() && !schoolCommon.isStringEmpty(classId)) {
            int iCount = schoolCommon.getDifMonth(byThemeTermplan.getStartmonth(), byThemeTermplan.getEndmonth());
            System.out.println("月份差=" + iCount);
            String uuid = schoolCommon.getUuid();
            byThemeTermplan.setId(uuid);
            byThemeTermplan.setSchoolid(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
            byThemeTermplan.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
            byThemeTermplan.setClassid(classId);
            byThemeTermplan.setName(byClassService.selectByClassById(classId).getBjmc() + "-主题整合学期计划");

            ByThemeTermplanitem byThemeTermplanitem = null;
            for (int i = 0; i <= iCount; i++) {
                byThemeTermplanitem = new ByThemeTermplanitem();
                byThemeTermplanitem.setTpid(uuid);
                byThemeTermplanitem.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
//                //月份加1
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(byThemeTermplan.getStartmonth());
//                calendar.add(Calendar.MONTH, i);

                byThemeTermplanitem.setMonth(schoolCommon.DateAddMonths(i, byThemeTermplan.getStartmonth()));

                //创建时间
                byThemeTermplanitem.setCreateTime(new Date());

                //新增每月计划
                byThemeTermplanitemService.insertByThemeTermplanitem(byThemeTermplanitem);
            }

            return toAjax(byThemeTermplanService.insertByThemeTermplan(byThemeTermplan));
        } else {
            return AjaxResult.error("当前用户非幼儿园教师，无法创建计划");
        }

    }

    /**
     * 修改主题整合学期计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:themetermplan:edit')")
    @Log(title = "主题整合学期计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByThemeTermplan byThemeTermplan) {
        return toAjax(byThemeTermplanService.updateByThemeTermplan(byThemeTermplan));
    }

    /**
     * 删除主题整合学期计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:themetermplan:remove')")
    @Log(title = "主题整合学期计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        //首先判断当前id下是否存在子目录
        for (int i = 0; i < ids.length; i++) {
            ByThemeTermplanitem byThemeTermplanitem = new ByThemeTermplanitem();
            byThemeTermplanitem.setTpid(ids[i]);
            List<ByThemeTermplanitem> list = byThemeTermplanitemService.selectByThemeTermplanitemList(byThemeTermplanitem);
            if (list != null && list.size() > 0) {
                return AjaxResult.error("选中的计划下存在子计划，无法删除");
            }
        }
        return toAjax(byThemeTermplanService.deleteByThemeTermplanByIds(ids));
    }

    /**
     * 提交主题整合学期计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:themetermplan:edit')")
    @Log(title = "主题整合学期计划", businessType = BusinessType.UPDATE)
    @PostMapping("/check/{id}")
    public AjaxResult check(@PathVariable String id) {
        ByThemeTermplan byThemeTermplan = new ByThemeTermplan();
        byThemeTermplan.setId(id);
        byThemeTermplan.setStatus("1");
        return toAjax(byThemeTermplanService.updateByThemeTermplan(byThemeTermplan));
    }
}
