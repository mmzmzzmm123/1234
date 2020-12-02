package com.ruoyi.project.common;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.web.domain.server.Sys;
import com.ruoyi.project.system.domain.*;
import com.ruoyi.project.system.service.IByClassService;
import com.ruoyi.project.system.service.IBySchoolService;
import com.ruoyi.project.system.service.ISysDeptService;
import com.ruoyi.project.system.service.ISysUserService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class SchoolCommon {
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private IBySchoolService schoolService;
    @Autowired
    private IByClassService byClassService;

    /**
     * 部门id转学校id
     **/
    public Long deptIdToSchoolId() {
        Long schoolId = (long) 0;//转换值
        try {
            SysDept sysDept = getDept();
            BySchool SchoolInfo = new BySchool();
            System.out.println("schoolId is Empty======:" + isStringEmpty(sysDept.getSchoolId()));
            if (sysDept != null && !isStringEmpty(sysDept.getSchoolId())) {
                SchoolInfo.setXxdm(sysDept.getSchoolId());
                List<BySchool> list = schoolService.selectBySchoolList(SchoolInfo);
                if (list != null && list.size() > 0) {
                    schoolId = list.get(0).getId();
                    System.out.println("部门id转换学校id为======:" + schoolId);
                    return schoolId;
                }
            }
            return schoolId;
        } catch (Exception e) {
            //throw new CustomException("部门id转换学校id异常", HttpStatus.UNAUTHORIZED);
            return schoolId;
        }
    }

    public SysDept getDept() {
        LoginUser loginUser = SecurityUtils.getLoginUser();

        Long deptId = loginUser.getUser().getDept().getDeptId();//当前用户部门id
        System.out.println("部门id======:" + deptId);
        System.out.println("sys_user.deptid======:" + loginUser.getUser().getDeptId());
        SysDept sysDept = deptService.selectDeptById(deptId);

        return sysDept;
    }

    //获取用户信息
    public SysUser getUser() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getUser().getUserId();
        System.out.println("用户id======:" + userId);
        System.out.println("sys_user.user_id======:" + loginUser.getUser().getUserId());
        SysUser sysUser = userService.selectUserById(userId);
        return sysUser;
    }

    /**
     * 判断当前用户是否为学校
     **/
    public boolean isSchool() {
        SysDept sysDept = getDept();
        //如果schoolid不为空 暂且认为是学校用户
        String strSchoolId = sysDept.getSchoolId();
        if (!isStringEmpty(strSchoolId)) {
            //去school表验证 是否真的是学校数据
            BySchool SchoolInfo = new BySchool();
            SchoolInfo.setXxdm(strSchoolId);
            List<BySchool> list = schoolService.selectBySchoolList(SchoolInfo);
            if (list != null && list.size() > 0) {
                return true;
            }
        }
        return false;
    }



    /**
     * 判断当前用户是否拥有班级
     **/
    public String getClassId() {
//        String strClassId = "-1";
        SysUser sysUser = getUser();
        ByClass byClass = new ByClass();
        //根据用户id来设置主班教师,配班教师,助理教师的教师id
        byClass.setZbjs(sysUser.getUserId());
        byClass.setPbjs(sysUser.getUserId());
        byClass.setZljs(sysUser.getUserId());
        //新的返回byclassNew返回整条数据
        ByClass byClassNew = byClassService.selectByClassByUserId(byClass);
        System.out.println("--------------------" + byClassNew);
        if (byClassNew != null) {
            //如果实体byclassnew不为空,那么取出它的班级编号
            return byClassNew.getBjbh();
        } else {
//            //如果为空 确认当前用户的角色是否为班长、配班、助理、实习教师
//            List<SysRole> listRole = SecurityUtils.getLoginUser().getUser().getRoles();
//            if (listRole != null && listRole.size() > 0) {
//                for (int i = 0; i < listRole.size(); i++) {
//                    System.out.println(listRole.get(i).getRoleId() == (102));
//                    //为班长、配班、助理、实习教师
//                    if (listRole.get(i).getRoleId() == 102 || listRole.get(i).getRoleId() == 104 || listRole.get(i).getRoleId() == 105 || listRole.get(i).getRoleId() == 105) {
//                        System.out.println("未设置当前账户所在班级");
//                        strClassId = "-1";
//                    } else {
//                        strClassId = "";
//                        break;
//                    }
//                }
//            } else {
//                //没设置角色
//                strClassId = "-1";
//            }
        }
        return "";
    }

    public String getCurrentXnXq() {
        Calendar date = Calendar.getInstance();
        Integer iYear = date.get(Calendar.YEAR);
        System.out.println("当前年======:" + iYear);
        Integer iMonth = date.get(Calendar.MONTH) + 1;//Calendar.MONTH 少一个月
        System.out.println("当前月======:" + iMonth);
        String strNxNq = "";

        if (iMonth < 9) {
            strNxNq = (iYear - 1) + "" + iYear + "2";
        } else {
            strNxNq = iYear + "" + (iYear + 1) + "1";
        }
        return strNxNq;

    }

    //根据时间 生成学年学期
    public String getCurrentXnXq(Date date) {
        String year = String.format("%tY", date);
        Integer iYear = Integer.parseInt(year);
        System.out.println("当前年======:" + iYear);
        String mon = String.format("%tm", date);
        Integer iMonth = Integer.parseInt(mon);
        System.out.println("当前月======:" + iMonth);
        String strNxNq = "";

        if (iMonth < 9) {
            strNxNq = (iYear - 1) + "" + iYear + "2";
        } else {
            strNxNq = iYear + "" + (iYear + 1) + "1";
        }
        return strNxNq;

    }

    public String getCurrentXn() {
        return getCurrentXnXq().substring(0, 9);
    }

    public String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);

    }

    public boolean isStringEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        return false;
    }

    // 生成UUID
    public String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    //日期相减 获取月份
    public Integer getDifMonth(Date startDate, Date endDate) {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(startDate);
        end.setTime(endDate);
        int result = end.get(Calendar.MONTH) - start.get(Calendar.MONTH);
        int month = (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * 12;
        return Math.abs(month + result);
    }

    //日期加天数
    public Date DateAddDays(int iday, Date dt) {
        //天数加1
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        calendar.add(Calendar.DAY_OF_MONTH, iday);

        return calendar.getTime();
    }

    //月份加数
    public Date DateAddMonths(int iday, Date dt) {
        //天数加1
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        calendar.add(Calendar.MONTH, iday);

        return calendar.getTime();
    }

    /**
     * 日期转星期
     *
     * @param datetime
     * @return
     */
    public int dateToWeek(Date datetime) {
        int[] weekDays = {7, 1, 2, 3, 4, 5, 6};
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        cal.setTime(datetime);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 使用BigDecimal，保留小数点后两位
     */
    public String format1(double value) {

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.toString();
    }

}
