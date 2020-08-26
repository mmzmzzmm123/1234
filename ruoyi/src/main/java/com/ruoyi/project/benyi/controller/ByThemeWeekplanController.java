package com.ruoyi.project.benyi.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.benyi.domain.ByThemeMonthplan;
import com.ruoyi.project.benyi.domain.ByThemeMonthplanitem;
import com.ruoyi.project.benyi.domain.ByThemeWeekplanitem;
import com.ruoyi.project.benyi.service.IByThemeMonthplanService;
import com.ruoyi.project.benyi.service.IByThemeMonthplanitemService;
import com.ruoyi.project.benyi.service.IByThemeWeekplanitemService;
import com.ruoyi.project.common.SchoolCommon;
import com.ruoyi.project.system.service.IByClassService;
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
import com.ruoyi.project.benyi.domain.ByThemeWeekplan;
import com.ruoyi.project.benyi.service.IByThemeWeekplanService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 主题整合周计划（根据月计划明细）Controller
 *
 * @author tsbz
 * @date 2020-08-26
 */
@RestController
@RequestMapping("/benyi/themeweekplan")
public class ByThemeWeekplanController extends BaseController {
    @Autowired
    private IByThemeWeekplanService byThemeWeekplanService;
    @Autowired
    private SchoolCommon schoolCommon;
    @Autowired
    private IByClassService byClassService;
    @Autowired
    private IByThemeMonthplanService byThemeMonthplanService;
    @Autowired
    private IByThemeMonthplanitemService byThemeonthplanitemService;
    @Autowired
    private IByThemeWeekplanitemService byThemeWeekplanitemService;

    /**
     * 查询主题整合周计划（根据月计划明细）列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:themeweekplan:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByThemeWeekplan byThemeWeekplan) {
        startPage();
        List<ByThemeWeekplan> list = byThemeWeekplanService.selectByThemeWeekplanList(byThemeWeekplan);
        return getDataTable(list);
    }

    /**
     * 导出主题整合周计划（根据月计划明细）列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:themeweekplan:export')")
    @Log(title = "主题整合周计划（根据月计划明细）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByThemeWeekplan byThemeWeekplan) {
        List<ByThemeWeekplan> list = byThemeWeekplanService.selectByThemeWeekplanList(byThemeWeekplan);
        ExcelUtil<ByThemeWeekplan> util = new ExcelUtil<ByThemeWeekplan>(ByThemeWeekplan.class);
        return util.exportExcel(list, "weekplan");
    }

    /**
     * 获取主题整合周计划（根据月计划明细）详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:themeweekplan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(byThemeWeekplanService.selectByThemeWeekplanById(id));
    }

    /**
     * 新增主题整合周计划（根据月计划明细）
     */
    @PreAuthorize("@ss.hasPermi('benyi:themeweekplan:add')")
    @Log(title = "主题整合周计划（根据月计划明细）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByThemeWeekplan byThemeWeekplan) {
        String classId = schoolCommon.getClassId();
        //首先判断当前账户是否为幼儿园账号
        if (schoolCommon.isSchool() && !schoolCommon.isStringEmpty(classId)) {

            //判断当前班级是否创建月计划
            ByThemeMonthplan byThemeMonthplan = new ByThemeMonthplan();
            byThemeMonthplan.setSchoolid(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
            byThemeMonthplan.setClassid(classId);
            byThemeMonthplan.setXnxq(byThemeWeekplan.getXnxq());
            byThemeMonthplan.setMonth(byThemeWeekplan.getMonth());
            byThemeMonthplan.setStatus("2");
            List<ByThemeMonthplan> list = byThemeMonthplanService.selectByThemeMonthplanList(byThemeMonthplan);
            int iCount = 0;
            if (list != null && list.size() > 0) {
                //循环月计划，查找周详情
                for (int i = 0; i < list.size(); i++) {
                    String mpid = list.get(i).getId();
                    ByThemeMonthplanitem byThemeMonthplanitem = new ByThemeMonthplanitem();
                    byThemeMonthplanitem.setMpid(mpid);
                    byThemeMonthplanitem.setZc(byThemeWeekplan.getZc());

                    List<ByThemeMonthplanitem> listItem = byThemeonthplanitemService.selectByThemeMonthplanitemList(byThemeMonthplanitem);
                    for (int j = 0; j < listItem.size(); j++) {
                        iCount = iCount + (j + 1);
                    }
                }
            } else {
                return AjaxResult.error("当前班级未制定月计划或月计划未审批，无法创建周计划");
            }

            if (iCount <= 0) {
                return AjaxResult.error("当前班级未设置月份明细计划，无法创建周计划");
            }

            String uuid = schoolCommon.getUuid();
            byThemeWeekplan.setId(uuid);
            byThemeWeekplan.setName(byClassService.selectByClassById(classId).getBjmc() + "-主题整合周计划" + "(第" + byThemeWeekplan.getZc() + "周)");
            byThemeWeekplan.setSchoolid(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
            byThemeWeekplan.setClassid(classId);
            byThemeWeekplan.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());

            if (list != null && list.size() > 0) {
                //循环月计划，查找周详情
                for (int i = 0; i < list.size(); i++) {
                    String mpid = list.get(i).getId();
                    ByThemeMonthplanitem byThemeMonthplanitem = new ByThemeMonthplanitem();
                    byThemeMonthplanitem.setMpid(mpid);
                    byThemeMonthplanitem.setZc(byThemeWeekplan.getZc());

                    List<ByThemeMonthplanitem> listItem = byThemeonthplanitemService.selectByThemeMonthplanitemList(byThemeMonthplanitem);
                    for (int j = 0; j < listItem.size(); j++) {
                        SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
                        Date dtStart = listItem.get(j).getStarttime();
                        Date dtEnd = listItem.get(j).getEndtime();
                        long day = (dtEnd.getTime() - dtStart.getTime()) / (24 * 60 * 60 * 1000) + 1;
                        System.out.println("day=" + day);
                        ByThemeWeekplanitem byThemeWeekplanitem = null;
                        for (int g = 0; g < day; g++) {
                            byThemeWeekplanitem = new ByThemeWeekplanitem();
                            byThemeWeekplanitem.setDaytime(schoolCommon.DateAddDays(g, listItem.get(j).getStarttime()));
                            byThemeWeekplanitem.setZhou(Long.valueOf(schoolCommon.dateToWeek(byThemeWeekplanitem.getDaytime())));
                            byThemeWeekplanitem.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
                            byThemeWeekplanitem.setWpid(uuid);
                            byThemeWeekplanitemService.insertByThemeWeekplanitem(byThemeWeekplanitem);
                        }

                    }
                }
            }

            return toAjax(byThemeWeekplanService.insertByThemeWeekplan(byThemeWeekplan));
        } else {
            return AjaxResult.error("当前用户非幼儿园教师，无法创建周计划");
        }
    }

    /**
     * 修改主题整合周计划（根据月计划明细）
     */
    @PreAuthorize("@ss.hasPermi('benyi:themeweekplan:edit')")
    @Log(title = "主题整合周计划（根据月计划明细）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByThemeWeekplan byThemeWeekplan) {
        return toAjax(byThemeWeekplanService.updateByThemeWeekplan(byThemeWeekplan));
    }

    /**
     * 删除主题整合周计划（根据月计划明细）
     */
    @PreAuthorize("@ss.hasPermi('benyi:themeweekplan:remove')")
    @Log(title = "主题整合周计划（根据月计划明细）", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(byThemeWeekplanService.deleteByThemeWeekplanByIds(ids));
    }
}
