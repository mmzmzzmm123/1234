package com.ruoyi.web.controller.common;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.jxjs.domain.TsbzJdx;
import com.ruoyi.jxjs.service.ITsbzJdxService;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class SchoolCommonController {

    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ITsbzJdxService tsbzJdxService;

    /**
     * 当前登录用户部门id转基地校id
     **/
    public String deptIdToJdxId() {
        String jdxId = "";//转换值
        try {
            SysDept sysDept = getDept();
            TsbzJdx tsbzJdx = new TsbzJdx();

            String strSchoolId = sysDept.getSchoolid();
            System.out.println("schoolId is Empty======:" + isStringEmpty(strSchoolId));
            if (sysDept != null && !isStringEmpty(strSchoolId)) {
                jdxId = strSchoolId;
                return jdxId;
            }
            return jdxId;
        } catch (Exception e) {
            //throw new CustomException("部门id转换学校id异常", HttpStatus.UNAUTHORIZED);
            return jdxId;
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
}
