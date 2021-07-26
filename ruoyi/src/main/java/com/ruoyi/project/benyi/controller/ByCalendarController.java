package com.ruoyi.project.benyi.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.benyi.domain.*;
import com.ruoyi.project.benyi.service.*;
import com.ruoyi.project.common.SchoolCommon;
import com.ruoyi.project.system.domain.ByClass;
import com.ruoyi.project.system.domain.ByTeacherJbxx;
import com.ruoyi.project.system.domain.SysDictData;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.service.IByClassService;
import com.ruoyi.project.system.service.IByTeacherJbxxService;
import com.ruoyi.project.system.service.ISysDictDataService;
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
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 园历管理(本一)Controller
 *
 * @author tsbz
 * @date 2020-05-10
 */
@RestController
@RequestMapping("/benyi/calendar")
public class ByCalendarController extends BaseController {
    @Autowired
    private IByCalendarService byCalendarService;
    @Autowired
    private IBySchoolcalendarService bySchoolcalendarService;
    @Autowired
    private IBySchoolcalendarClassService bySchoolcalendarClassService;
    @Autowired
    private ISysDictDataService dictDataService;
    @Autowired
    private SchoolCommon schoolCommon;
    @Autowired
    private IByTeacherJbxxService byTeacherJbxxService;
    @Autowired
    private IByThemeWeekplanService byThemeWeekplanService;
    @Autowired
    private IByThemeWeekplanitemService byThemeWeekplanitemService;
    @Autowired
    private IByThemeActivityService byThemeActivityService;
    @Autowired
    private IByChildService byChildService;
    @Autowired
    private IByClassService byClassService;
    @Autowired
    private IByChildCheckinDetailService byChildCheckinDetailService;
    @Autowired
    private IByDayflowassessmentplanService byDayflowassessmentplanService;
    @Autowired
    private IByDayflowassessmentService byDayflowassessmentService;
    @Autowired
    private IByDayFlowDetailService byDayFlowDetailService;

    /**
     * 查询园历管理(本一)列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:calendar:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByCalendar byCalendar) {
        startPage();
        List<ByCalendar> list = byCalendarService.selectByCalendarList(byCalendar);
        return getDataTable(list);
    }

    /**
     * 导出园历管理(本一)列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:calendar:export')")
    @Log(title = "园历管理(本一)", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByCalendar byCalendar) {
        List<ByCalendar> list = byCalendarService.selectByCalendarList(byCalendar);
        ExcelUtil<ByCalendar> util = new ExcelUtil<ByCalendar>(ByCalendar.class);
        return util.exportExcel(list, "calendar");
    }

    /**
     * 获取园历管理(本一)详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:calendar:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byCalendarService.selectByCalendarById(id));
    }

    /**
     * 新增园历管理(本一)
     */
    @PreAuthorize("@ss.hasPermi('benyi:calendar:add')")
    @Log(title = "园历管理(本一)", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByCalendar byCalendar) {
        byCalendar.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
        byCalendar.setStylecolor("#eb2f96");
        byCalendar.setCreatetime(new Date());
        return toAjax(byCalendarService.insertByCalendar(byCalendar));
    }

    /**
     * 修改园历管理(本一)
     */
    @PreAuthorize("@ss.hasPermi('benyi:calendar:edit')")
    @Log(title = "园历管理(本一)", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByCalendar byCalendar) {
        return toAjax(byCalendarService.updateByCalendar(byCalendar));
    }

    /**
     * 删除园历管理(本一)
     */
    @PreAuthorize("@ss.hasPermi('benyi:calendar:remove')")
    @Log(title = "园历管理(本一)", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byCalendarService.deleteByCalendarByIds(ids));
    }

    /**
     * 园历展示(本一)
     */
    //@PreAuthorize("@ss.hasPermi('benyi:schoolcalendar:list')")
    @GetMapping("/getAllSchoolCalendars")
    public AjaxResult getAllSchoolCalendars(ByCalendar byCalendar) {
        //将类型颜色样式加载到字典
        HashMap<String, String> hashMap = new HashMap<>();
        for (SysDictData calendartype : dictDataService.selectDictDataByType("sys_schoolcalendartype")) {
            System.out.println("====sys_yebjlx.getDictValue()" + calendartype.getDictValue());
            System.out.println("=====calendartype.getCssClass()" + calendartype.getCssClass());
            hashMap.put(calendartype.getDictValue(), calendartype.getCssClass());
        }
        //定义返回列表
        List<ByCalendarShow> listvi = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //加载本一园历
        List<ByCalendar> list = byCalendarService.selectByCalendarList(byCalendar);
        if (list.size() > 0) {
            for (ByCalendar calendar : list) {
                ByCalendarShow by = new ByCalendarShow();
                by.setId(calendar.getId());
                by.setTitle(calendar.getName());
                System.out.println("title:" + calendar.getName());
                by.setStart(formatter.format(calendar.getActivitytime()));
                by.setEnd(formatter.format(calendar.getActivityendtime()));
                by.setColor(hashMap.get(calendar.getType()));
                listvi.add(by);
            }
        }

        //幼儿园登陆展示
        if (schoolCommon.isSchool() == true) {
            Long schoolId = SecurityUtils.getLoginUser().getUser().getDeptId();
            //根据不同的幼儿园加载幼儿园园历
            //(long)207
            listvi.addAll(getbySchoolcalendars(schoolId, formatter, hashMap));
        }
//        listvi.addAll(getbySchoolcalendars((long)207,formatter,hashMap));
//        listvi.addAll(getbyteacherBiths((long)207,formatter,hashMap));

        //根据不同的班级记载班级园历
        String classId = schoolCommon.getClassId();
        if (!schoolCommon.isStringEmpty(classId)) {
            //根据不同的班级加载班级园历
            listvi.addAll(getbyclasses(classId, formatter, hashMap));
            //加载主题整合活动
            listvi.addAll(getbyclassthemes(classId, formatter, hashMap));
            //根据班级加载幼儿生日
            listvi.addAll(getbychilds(classId, formatter));
        }else{
            Long schoolId = SecurityUtils.getLoginUser().getUser().getDeptId();
            //幼儿一日流程评估计划
            listvi.addAll(getdayflowdata(classId, formatter));
            //根据不同的幼儿园加载教职工
            listvi.addAll(getbyteacherBiths(schoolId, formatter, hashMap));
        }

        //幼儿出勤人数
        listvi.addAll(getbychildcheckdata(classId, formatter));

        //幼儿园一日流程评估结果
        Long userId = SecurityUtils.getLoginUser().getUser().getUserId();
        listvi.addAll(getdayflowresult(userId, formatter));

        AjaxResult ajax = AjaxResult.success();
        ajax.put("calendarData", listvi);
        return ajax;

    }

    /**
     * 根据不同的幼儿园加载幼儿园园历
     */
    private List<ByCalendarShow> getbySchoolcalendars(Long schoolId, SimpleDateFormat formatter, HashMap<String, String> hashMap) {
        List<ByCalendarShow> listvi = new ArrayList<>();

        BySchoolcalendar bySchoolcalendar = new BySchoolcalendar();
        //设置幼儿园
        bySchoolcalendar.setDeptid(schoolId);
        //班级类型
        String classId = schoolCommon.getClassId();
        if (!schoolCommon.isStringEmpty(classId)) {
            ByClass byClass = new ByClass();
            byClass = byClassService.selectByClassById(classId);
            bySchoolcalendar.setScope(byClass.getBjtype());
        }


        List<BySchoolcalendar> bySchoolcalendarList = bySchoolcalendarService.selectBySchoolcalendarList(bySchoolcalendar);
        if (bySchoolcalendarList.size() > 0) {
            for (BySchoolcalendar bsc : bySchoolcalendarList) {
                ByCalendarShow by = new ByCalendarShow();
                by.setId(bsc.getId());
                by.setTitle(bsc.getName());
                by.setStart(formatter.format(bsc.getActivitytime()));
                by.setEnd(formatter.format(bsc.getActivityendtime()));
                by.setColor(hashMap.get(bsc.getType()));
                listvi.add(by);
            }
        }
        return listvi;
    }

    /**
     * 根据不同的幼儿园加载教师生日
     */
    private List<ByCalendarShow> getbyteacherBiths(Long schoolId, SimpleDateFormat formatter, HashMap<String, String> hashMap) {
        SysUser sysUser = new SysUser();
        sysUser.setDeptId(schoolId);

        ByTeacherJbxx byTeacherJbxx = new ByTeacherJbxx();
        byTeacherJbxx.setUser(sysUser);
        String classId = schoolCommon.getClassId();
        if (!schoolCommon.isStringEmpty(classId)) {
            byTeacherJbxx.setUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        }
        List<ByTeacherJbxx> listTeacherBirth = byTeacherJbxxService.selectByTeacherJbxxList(byTeacherJbxx);

        List<ByCalendarShow> listvi = new ArrayList<>();
        //系统内员工的生日、入职日期信息
        System.out.println("listTeacherBirth.size()===" + listTeacherBirth.size());
        if (listTeacherBirth != null && listTeacherBirth.size() > 0) {
            for (int i = 0; i < listTeacherBirth.size(); i++) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                //创建一个教师实体类，并赋值
                byTeacherJbxx = listTeacherBirth.get(i);
                String strCurrentYear = schoolCommon.getCurrentYear();
                System.out.println("当前年===" + strCurrentYear);

                //参加工作日期
                if (byTeacherJbxx.getCjgzrq() != null) {
                    String timefor = formatter.format(byTeacherJbxx.getCjgzrq());
                    timefor = timefor.replaceAll(timefor.split("-")[0], strCurrentYear);

                    ByCalendarShow by = new ByCalendarShow();
                    //by.setId((long)000);
                    by.setTitle(listTeacherBirth.get(i).getUser().getNickName() + "-合同满年期限");
                    by.setStart(timefor);
                    by.setEnd(timefor);
                    //教师生日颜色
                    by.setColor("#13c2c2");
                    listvi.add(by);
                    System.out.println("当前年工作日期timefor===" + timefor + "=====" + listTeacherBirth.get(i).getUser().getNickName() + "-合同满年期限");
                }
                //生日
                if (byTeacherJbxx.getCsrq() != null) {
                    String timefor = formatter.format(byTeacherJbxx.getCsrq());
                    timefor = timefor.replaceAll(timefor.split("-")[0], strCurrentYear);

                    ByCalendarShow by = new ByCalendarShow();
                    //by.setId((long)000);
                    by.setTitle(listTeacherBirth.get(i).getUser().getNickName() + "-生日");
                    by.setStart(timefor);
                    by.setEnd(timefor);
                    //教师生日颜色
                    by.setColor("#722ed1");
                    listvi.add(by);
                    System.out.println("当前年生日timefor===" + timefor + "=====" + listTeacherBirth.get(i).getUser().getNickName() + "-生日");
                }
            }
        }
        return listvi;
    }

    /**
     * 根据不同的班级加载班级园历
     */
    private List<ByCalendarShow> getbyclasses(String classId, SimpleDateFormat formatter, HashMap<String, String> hashMap) {
        List<ByCalendarShow> listvi = new ArrayList<>();

        BySchoolcalendarClass bySchoolcalendarClass = new BySchoolcalendarClass();
        //设置班级
        bySchoolcalendarClass.setClassid(classId);
        List<BySchoolcalendarClass> bySchoolcalendarClassList = bySchoolcalendarClassService.selectBySchoolcalendarClassList(bySchoolcalendarClass);
        if (bySchoolcalendarClassList.size() > 0) {
            for (BySchoolcalendarClass bscc : bySchoolcalendarClassList) {
                ByCalendarShow by = new ByCalendarShow();
                by.setId(bscc.getId());
                by.setTitle(bscc.getName());
                by.setStart(formatter.format(bscc.getActivitytime()));
                by.setEnd(formatter.format(bscc.getActivityendtime()));
                by.setColor(hashMap.get(bscc.getType()));
                listvi.add(by);
            }
        }

        //班级课程

        return listvi;
    }

    //获取班级主题整合活动
    private List<ByCalendarShow> getbyclassthemes(String classId, SimpleDateFormat formatter, HashMap<String, String> hashMap) {

        List<ByCalendarShow> listvi = new ArrayList<>();
        Long deptId = SecurityUtils.getLoginUser().getUser().getDept().getDeptId();
        ByThemeWeekplan byThemeWeekplan = new ByThemeWeekplan();
        byThemeWeekplan.setSchoolid(deptId);
        byThemeWeekplan.setClassid(classId);
        byThemeWeekplan.setStatus("2");
        //学年学期？

        List<ByThemeWeekplan> listByThemeWeekplan = byThemeWeekplanService.selectByThemeWeekplanList(byThemeWeekplan);
        if (listByThemeWeekplan != null && listByThemeWeekplan.size() > 0) {
            ByThemeWeekplanitem byThemeWeekplanitem = null;
            for (int i = 0; i < listByThemeWeekplan.size(); i++) {
                byThemeWeekplanitem = new ByThemeWeekplanitem();
                String wpId = listByThemeWeekplan.get(i).getId();

                byThemeWeekplanitem.setWpid(wpId);
                List<ByThemeWeekplanitem> listByThemeWeekplanitem = byThemeWeekplanitemService.selectByThemeWeekplanitemList(byThemeWeekplanitem);
                if (listByThemeWeekplanitem != null && listByThemeWeekplanitem.size() > 0) {
                    for (int j = 0; j < listByThemeWeekplanitem.size(); j++) {
                        if (!schoolCommon.isStringEmpty(listByThemeWeekplanitem.get(j).getActivityid())) {
                            ByCalendarShow by = new ByCalendarShow();
                            by.setId(listByThemeWeekplanitem.get(j).getId());

                            String[] strArr = listByThemeWeekplanitem.get(j).getActivityid().split(";");
                            String activityName = "";
                            if (strArr.length > 0) {
                                for (int g = 0; g < strArr.length; g++) {
                                    if (!schoolCommon.isStringEmpty(strArr[g])) {
                                        activityName = activityName + byThemeActivityService.selectByThemeActivityById(Long.valueOf(strArr[g])).getName() + ";";
                                    }
                                }
                            }

                            by.setTitle("主题整合(" + activityName + ")");
                            by.setStart(formatter.format(listByThemeWeekplanitem.get(j).getDaytime()));
                            by.setEnd(formatter.format(listByThemeWeekplanitem.get(j).getDaytime()));
                            by.setColor(hashMap.get("03"));
                            listvi.add(by);
                        }
                    }
                }

            }
        }

        return listvi;
    }

    //获取班级幼儿生日列表
    private List<ByCalendarShow> getbychilds(String classId, SimpleDateFormat formatter) {
        List<ByCalendarShow> listvi = new ArrayList<>();

        Long deptId = SecurityUtils.getLoginUser().getUser().getDept().getDeptId();
        ByChild byChild = new ByChild();
        byChild.setSchoolid(deptId);
        byChild.setClassid(classId);
        byChild.setStatus("0");
        List<ByChild> list = byChildService.selectByChildList(byChild);
        if (list != null && list.size() > 0) {
            String strCurrentYear = schoolCommon.getCurrentYear();
            ByCalendarShow by = null;
            for (int i = 0; i < list.size(); i++) {
                by = new ByCalendarShow();
                ByChild byNewChild = list.get(i);
                if (byNewChild.getCsrq() != null) {
                    by.setId(byNewChild.getId());
                    by.setTitle(byNewChild.getName() + "-生日");

                    String timefor = formatter.format(byNewChild.getCsrq());
                    timefor = timefor.replaceAll(timefor.split("-")[0], strCurrentYear);

                    by.setStart(timefor);
                    by.setEnd(timefor);
                    by.setColor("#b37feb");
                    listvi.add(by);
                }
            }
        }
        return listvi;
    }

    //获取学校或班级考勤数据列表
    private List<ByCalendarShow> getbychildcheckdata(String classId, SimpleDateFormat formatter) {
        List<ByCalendarShow> listvi = new ArrayList<>();

        Long deptId = SecurityUtils.getLoginUser().getUser().getDept().getDeptId();
        ByChildCheckinDetail byChildCheckinDetail = new ByChildCheckinDetail();
        byChildCheckinDetail.setSchoolid(deptId);
        byChildCheckinDetail.setClassid(classId);
        List<ByChildCheckinDetail> list = byChildCheckinDetailService.selectByChildCheckinGroupDaysList(byChildCheckinDetail);
        if (list != null && list.size() > 0) {
            ByCalendarShow by = null;
            for (int i = 0; i < list.size(); i++) {
                by = new ByCalendarShow();
                ByChildCheckinDetail byNewChildCheckinDetail = list.get(i);

                by.setTitle("幼儿出勤人数：" + byNewChildCheckinDetail.getCount());
                String timefor = formatter.format(byNewChildCheckinDetail.getCreateTime());
                by.setStart(timefor);
                by.setEnd(timefor);
                by.setColor("#85a5ff");
                listvi.add(by);
            }
        }
        return listvi;
    }

    //获取学校或班级一日流程评估
    private List<ByCalendarShow> getdayflowdata(String classId, SimpleDateFormat formatter) {
        List<ByCalendarShow> listvi = new ArrayList<>();

        Long deptId = SecurityUtils.getLoginUser().getUser().getDept().getDeptId();
        ByDayflowassessmentplan byDayflowassessmentplan = new ByDayflowassessmentplan();
        byDayflowassessmentplan.setDeptId(deptId);
        byDayflowassessmentplan.setClassid(classId);
        List<ByDayflowassessmentplan> list = byDayflowassessmentplanService.selectByDayflowassessmentplanList(byDayflowassessmentplan);
        if (list != null && list.size() > 0) {
            ByCalendarShow by = null;
            for (int i = 0; i < list.size(); i++) {
                by = new ByCalendarShow();
                ByDayflowassessmentplan byNewDayflowassessmentplan = list.get(i);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");//设置日期格式
                by.setTitle(sdf.format(byNewDayflowassessmentplan.getStarttime()) + "评估" + byClassService.selectByClassById(byNewDayflowassessmentplan.getClassid()).getBjmc() + byDayFlowDetailService.selectByDayFlowDetailById(Long.valueOf(byNewDayflowassessmentplan.getConnent())).getName());
                String timefor = formatter.format(byNewDayflowassessmentplan.getStarttime());
                by.setStart(timefor);
                by.setEnd(timefor);
                by.setColor("#1890ff");
                listvi.add(by);
            }
        }
        return listvi;
    }

    //获取教师一日流程评估结果
    private List<ByCalendarShow> getdayflowresult(Long userId, SimpleDateFormat formatter) {
        List<ByCalendarShow> listvi = new ArrayList<>();

        Long deptId = SecurityUtils.getLoginUser().getUser().getDept().getDeptId();
        ByDayflowassessment byDayflowassessment = new ByDayflowassessment();
        byDayflowassessment.setDeptId(deptId);
        byDayflowassessment.setPgdx(userId);
        List<ByDayflowassessment> list = byDayflowassessmentService.selectByDayflowassessmentList(byDayflowassessment);
        if (list != null && list.size() > 0) {
            ByCalendarShow by = null;
            for (int i = 0; i < list.size(); i++) {
                by = new ByCalendarShow();
                ByDayflowassessment byNewDayflowassessment = list.get(i);
                by.setTitle("一日流程评估：" + byNewDayflowassessment.getZzdf());
                String timefor = formatter.format(byNewDayflowassessment.getCreateTime());
                by.setStart(timefor);
                by.setEnd(timefor);
                by.setColor("#1890ff");
                listvi.add(by);
            }
        }
        return listvi;
    }

}
