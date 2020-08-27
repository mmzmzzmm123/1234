package com.ruoyi.project.benyi.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.benyi.domain.ByThemeTermplan;
import com.ruoyi.project.benyi.domain.ByThemeTermplanitem;
import com.ruoyi.project.benyi.domain.ByThemeMonthplanitem;
import com.ruoyi.project.benyi.service.IByThemeTermplanService;
import com.ruoyi.project.benyi.service.IByThemeTermplanitemService;
import com.ruoyi.project.benyi.service.IByThemeMonthplanitemService;
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
import com.ruoyi.project.benyi.domain.ByThemeMonthplan;
import com.ruoyi.project.benyi.service.IByThemeMonthplanService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 主题整合月计划Controller
 *
 * @author tsbz
 * @date 2020-08-17
 */
@RestController
@RequestMapping("/benyi/thememonthplan")
public class ByThemeMonthplanController extends BaseController {
    @Autowired
    private IByThemeMonthplanService byThemeMonthplanService;
    @Autowired
    private SchoolCommon schoolCommon;
    @Autowired
    private IByClassService byClassService;
    @Autowired
    private IByThemeTermplanService byThemeTermplanService;
    @Autowired
    private IByThemeTermplanitemService byThemeTermplanitemService;
    @Autowired
    private IByThemeMonthplanitemService byThemeonthplanitemService;

    /**
     * 查询主题整合月计划列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:thememonthplan:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByThemeMonthplan byThemeMonthplan) {
        startPage();
        byThemeMonthplan.setSchoolid(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
        String classId = schoolCommon.getClassId();
        List<ByThemeMonthplan> list = null;
        //首先判断当前账户是否为幼儿园账号
        if (schoolCommon.isSchool() && !schoolCommon.isStringEmpty(classId)) {
            byThemeMonthplan.setClassid(classId);
        }
        list = byThemeMonthplanService.selectByThemeMonthplanList(byThemeMonthplan);
        return getDataTable(list);
    }

    /**
     * 导出主题整合月计划列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:thememonthplan:export')")
    @Log(title = "主题整合月计划", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByThemeMonthplan byThemeMonthplan) {
        List<ByThemeMonthplan> list = byThemeMonthplanService.selectByThemeMonthplanList(byThemeMonthplan);
        ExcelUtil<ByThemeMonthplan> util = new ExcelUtil<ByThemeMonthplan>(ByThemeMonthplan.class);
        return util.exportExcel(list, "thememonthplan");
    }

    /**
     * 获取主题整合月计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:thememonthplan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(byThemeMonthplanService.selectByThemeMonthplanById(id));
    }

    /**
     * 新增主题整合月计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:thememonthplan:add')")
    @Log(title = "主题整合月计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByThemeMonthplan byThemeMonthplan) {
        String classId = schoolCommon.getClassId();
        //首先判断当前账户是否为幼儿园账号
        if (schoolCommon.isSchool() && !schoolCommon.isStringEmpty(classId)) {

            //根据当前月份 查找学期计划的主题
            ByThemeTermplan byThemeTermplan = new ByThemeTermplan();
            byThemeTermplan.setSchoolid(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
            byThemeTermplan.setClassid(classId);
            byThemeTermplan.setStatus("2");
            byThemeTermplan.setXnxq(byThemeMonthplan.getXnxq());
            List<ByThemeTermplan> list = byThemeTermplanService.selectByThemeTermplanList(byThemeTermplan);
            String strThemeIds = "";
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    String themeId = list.get(i).getId();
                    ByThemeTermplanitem byThemeTermplanitem = new ByThemeTermplanitem();
                    byThemeTermplanitem.setTpid(themeId);
                    byThemeTermplanitem.setMonth(byThemeMonthplan.getMonth());
                    List<ByThemeTermplanitem> listItem = byThemeTermplanitemService.selectByThemeTermplanitemList(byThemeTermplanitem);
                    if (listItem != null && listItem.size() > 0) {
                        for (int j = 0; j < listItem.size(); j++) {
                            String themeIds = listItem.get(j).getThemeconent();
                            if (!schoolCommon.isStringEmpty(themeIds)) {
                                strThemeIds = strThemeIds + themeIds;
                            }
                        }
                    }
                }
            } else {
                return AjaxResult.error("当前班级未制定学期计划或学期计划未审批，无法创建月计划");
            }

            if (schoolCommon.isStringEmpty(strThemeIds)) {
                return AjaxResult.error("当前班级制定的学期计划未设置月份主题，无法创建月计划");
            }

            String uuid = schoolCommon.getUuid();
            byThemeMonthplan.setId(uuid);
            byThemeMonthplan.setSchoolid(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
            byThemeMonthplan.setClassid(classId);
            byThemeMonthplan.setThemes(strThemeIds);//主题id
            byThemeMonthplan.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            byThemeMonthplan.setName(byClassService.selectByClassById(classId).getBjmc() + "-主题整合月计划" + "(" + sdf.format(byThemeMonthplan.getMonth()) + ")");
            return toAjax(byThemeMonthplanService.insertByThemeMonthplan(byThemeMonthplan));
        } else {
            return AjaxResult.error("当前用户非幼儿园教师，无法创建月计划");
        }
    }

    /**
     * 修改主题整合月计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:thememonthplan:edit')")
    @Log(title = "主题整合月计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByThemeMonthplan byThemeMonthplan) {
        return toAjax(byThemeMonthplanService.updateByThemeMonthplan(byThemeMonthplan));
    }

    /**
     * 删除主题整合月计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:thememonthplan:remove')")
    @Log(title = "主题整合月计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        //首先判断当前id下是否存在子目录
        for (int i = 0; i < ids.length; i++) {
            ByThemeMonthplanitem byThemeMonthplanitem = new ByThemeMonthplanitem();
            byThemeMonthplanitem.setMpid(ids[i]);
            List<ByThemeMonthplanitem> list = byThemeonthplanitemService.selectByThemeMonthplanitemList(byThemeMonthplanitem);
            if (list != null && list.size() > 0) {
                return AjaxResult.error("选中的计划下存在子计划，无法删除");
            }
        }
        return toAjax(byThemeMonthplanService.deleteByThemeMonthplanByIds(ids));
    }

    /**
     * 提交主题整合学期计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:thememonthplan:edit')")
    @Log(title = "主题整合学期计划", businessType = BusinessType.UPDATE)
    @PostMapping("/check/{id}")
    public AjaxResult check(@PathVariable String id) {
        ByThemeMonthplan byThemeMonthplan = new ByThemeMonthplan();
        byThemeMonthplan.setId(id);
        byThemeMonthplan.setStatus("1");
        return toAjax(byThemeMonthplanService.updateByThemeMonthplan(byThemeMonthplan));
    }
}
