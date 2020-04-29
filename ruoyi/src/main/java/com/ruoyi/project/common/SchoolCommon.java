package com.ruoyi.project.common;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.project.system.domain.BySchool;
import com.ruoyi.project.system.domain.SysDept;
import com.ruoyi.project.system.service.IBySchoolService;
import com.ruoyi.project.system.service.ISysDeptService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class SchoolCommon {
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private IBySchoolService schoolService;

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
        String year=String.format("%tY", date);
        Integer iYear = Integer.parseInt(year);
        System.out.println("当前年======:" + iYear);
        String mon=String.format("%tm", date);
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
       return  getCurrentXnXq().substring(0,9);
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
}
