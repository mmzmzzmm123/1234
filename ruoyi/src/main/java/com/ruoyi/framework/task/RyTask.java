package com.ruoyi.framework.task;

import com.ruoyi.common.service.impl.SMSService;
import com.ruoyi.project.benyi.domain.BySchoolNews;
import com.ruoyi.project.benyi.service.IBySchoolNewsService;
import com.ruoyi.project.bysite.domain.ByNews;
import com.ruoyi.project.bysite.service.IByNewsService;
import com.ruoyi.project.common.SchoolCommon;
import com.ruoyi.project.system.domain.BySchool;
import com.ruoyi.project.system.domain.ByTeacherJbxx;
import com.ruoyi.project.system.domain.SysDept;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.service.IBySchoolService;
import com.ruoyi.project.system.service.IByTeacherJbxxService;
import com.ruoyi.project.system.service.ISysDeptService;
import com.ruoyi.project.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ruoyi.common.utils.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 定时任务调度测试
 *
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask {

    @Autowired
    private IBySchoolService bySchoolService;
    @Autowired
    private SchoolCommon schoolCommon;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private IBySchoolNewsService iBySchoolNewsService;
    @Autowired
    private IByNewsService iByNewsService;
    @Autowired
    private IByTeacherJbxxService iByTeacherJbxxService;
    @Autowired
    private SMSService smsService;

    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params) {
        //jzsj 截止时间 过后禁止登录
        if (params.equals("jzsj")) {
            BySchool bySchool = new BySchool();
            BySchool bySchoolModel = null;
            bySchool.setStatus("0");
            bySchool.setIsDel("0");
            List<BySchool> list = bySchoolService.selectBySchoolList(bySchool);
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    bySchoolModel = list.get(i);
                    //如果当前时间大于截止时间，那么停用账号
                    Date dt = new Date();
                    //compareTo()方法的返回值，dt1小于dt2返回-1，dt1大于dt2返回1，相等返回0
                    int compareTo = dt.compareTo(bySchoolModel.getOpenDeadline());
                    if (compareTo > 0) {
                        //停用部门
                        //System.out.println("xxdm:"+bySchoolModel.getXxdm());
                        SysDept sysDept = schoolCommon.getDept(bySchoolModel.getXxdm());
                        sysDept.setStatus("1");
                        deptService.updateDept(sysDept);

                        //停用用户
                        SysUser sysUser = new SysUser();
                        sysUser.setDeptId(sysDept.getDeptId());
                        sysUser.setStatus("0");
                        sysUser.setDelFlag("0");
                        List<SysUser> listUser = userService.selectUserListAll(sysUser);
                        if (listUser != null && listUser.size() > 0) {
                            updateUserState(listUser);
                        }

                        //停用当前学校
                        bySchoolModel.setStatus("1");
                        bySchoolService.updateBySchool(bySchoolModel);
                    }
                }
            }
        } else if (params.equals("hyxd")) {
            System.out.println("1111");
            BySchoolNews bySchoolNews = new BySchoolNews();
            bySchoolNews.setIscheck("1");
            bySchoolNews.setIsdel("N");
            bySchoolNews.setIstb("0");
            List<BySchoolNews> list = iBySchoolNewsService.selectAllBySchoolNewsList(bySchoolNews);
            if (list != null && list.size() > 0) {
                ByNews byNews = null;
                BySchoolNews newBySchoolNews = null;
                for (int i = 0; i < list.size(); i++) {
                    byNews = new ByNews();
                    byNews.setTitle(list.get(i).getTitle());
                    byNews.setType("03");//会员心得
                    byNews.setContent(list.get(i).getContent());
                    byNews.setIsthirdparty("N");
                    byNews.setIsrelease("Y");
                    byNews.setIsdel("0");
                    byNews.setSchoolid(list.get(i).getDeptId());
                    byNews.setCreateTime(list.get(i).getCreateTime());
                    byNews.setAbstractcontent(list.get(i).getAbstractcontent());

                    iByNewsService.insertByNews(byNews);
                    newBySchoolNews = new BySchoolNews();
                    newBySchoolNews.setId(list.get(i).getId());
                    newBySchoolNews.setIstb("1");
                    iBySchoolNewsService.updateBySchoolNews(newBySchoolNews);
                }
            }
        } else if (params.equals("birthteacher")) {
            System.out.println("birthteacher");
            List<ByTeacherJbxx> list = iByTeacherJbxxService.selectByTeacherBrithList(null);
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    String phone = list.get(i).getUser().getUserName();
                    smsService.sendBirthTeacherSMS(phone);
                }
            }

        } else if (params.equals("warnopendeadline")) {
            System.out.println("warnopendeadline");
            List<BySchool> list = bySchoolService.selectBySchoolWarnList(null);
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    String tel = list.get(i).getTel();
                    Long days = list.get(i).getDays();
                    if (!schoolCommon.isStringEmpty(tel)) {
                        //发送给学校联系人
                        smsService.sendWarnSMS(tel, days.toString());
                    }


                    String emTel = list.get(i).getEmTel();
                    if (!schoolCommon.isStringEmpty(emTel)) {
                        //发送给学校紧急联系人
                        smsService.sendWarnSMS(emTel, days.toString());
                    }
                }
            }

        }
        System.out.println("执行有参方法：" + params);
    }

    //停用用户
    public void updateUserState(List<SysUser> list) {
        SysUser sysUser = null;
        for (int j = 0; j < list.size(); j++) {
            sysUser = list.get(j);
            sysUser.setStatus("1");
            userService.updateUser(sysUser);
        }
    }

    public void ryNoParams() {
        System.out.println("执行无参方法");
    }
}
