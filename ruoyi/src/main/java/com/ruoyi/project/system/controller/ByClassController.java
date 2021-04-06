package com.ruoyi.project.system.controller;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.benyi.domain.ByChild;
import com.ruoyi.project.benyi.service.IByChildService;
import com.ruoyi.project.common.SchoolCommon;
import com.ruoyi.project.system.domain.ByClass;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.service.IByClassService;
import com.ruoyi.project.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 班级信息Controller
 *
 * @author tsbz
 * @date 2020-04-14
 */
@RestController
@RequestMapping("/system/class")
public class ByClassController extends BaseController {
    @Autowired
    private IByClassService byClassService;
    @Autowired
    private SchoolCommon schoolCommon;
    @Autowired
    private IByChildService byChildService;
    @Autowired
    private ISysUserService userService;


    /**
     * 查询班级信息列表
     */
    //@PreAuthorize("@ss.hasPermi('system:class:list')"+ "||@ss.hasPermi('system:school:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByClass byClass) {
        List<ByClass> list = null;
        String classId = schoolCommon.getClassId();
        //如果是幼儿园教师 只显示当前班级
        if (schoolCommon.isSchool() && !schoolCommon.isStringEmpty(classId)) {
            byClass.setBjbh(classId);
        } else {
        }
        startPage();
        list = byClassService.selectByClassList(byClass);
        return getDataTable(list);
    }

    /**
     * 导出班级信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:class:export')")
    @Log(title = "班级信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByClass byClass) {
        List<ByClass> list = byClassService.selectByClassList(byClass);
        ExcelUtil<ByClass> util = new ExcelUtil<ByClass>(ByClass.class);
        return util.exportExcel(list, "class");
    }

    /**
     * 获取班级信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:class:query')")
    @GetMapping(value = "/{bjbh}")
    public AjaxResult getInfo(@PathVariable("bjbh") String bjbh) {
        return AjaxResult.success(byClassService.selectByClassById(bjbh));
    }

    /**
     * 新增班级信息
     */
    @PreAuthorize("@ss.hasPermi('system:class:add')")
    @Log(title = "班级信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByClass byClass) {

        //首先判断 当前用户是否为学校
        if (schoolCommon.isSchool()) {

            //首先检查教师是否已经是班长、配班、助教;为了限制一个账号多种班级权限
            if (byClass.getZbjs() != null) {
                ByClass byClassNew = new ByClass();
                byClassNew.setZbjs(byClass.getZbjs());
                byClassNew.setPbjs(byClass.getZbjs());
                byClassNew.setZljs(byClass.getZbjs());
                byClassNew = byClassService.selectByClassByUserId(byClassNew);
                if (byClassNew != null && !schoolCommon.isStringEmpty(byClassNew.getBjbh())) {
                    return AjaxResult.error("当前主班教师已设置为其他班级教师，无法重复，创建班级失败");
                }
            }
            if (byClass.getPbjs() != null) {
                ByClass byClassNew = new ByClass();
                byClassNew.setZbjs(byClass.getPbjs());
                byClassNew.setPbjs(byClass.getPbjs());
                byClassNew.setZljs(byClass.getPbjs());
                byClassNew = byClassService.selectByClassByUserId(byClassNew);
                if (byClassNew != null && !schoolCommon.isStringEmpty(byClassNew.getBjbh())) {
                    return AjaxResult.error("当前配班教师已设置为其他班级教师，无法重复，创建班级失败");
                }
            }
            if (byClass.getZljs() != null) {
                ByClass byClassNew = new ByClass();
                byClassNew.setZbjs(byClass.getZljs());
                byClassNew.setPbjs(byClass.getZljs());
                byClassNew.setZljs(byClass.getZljs());
                byClassNew = byClassService.selectByClassByUserId(byClassNew);
                if (byClassNew != null && !schoolCommon.isStringEmpty(byClassNew.getBjbh())) {
                    return AjaxResult.error("当前助理教师已设置为其他班级教师，无法重复，创建班级失败");
                }
            }

            String strBjbh = UUID.randomUUID().toString().replace("-", "");
            System.out.println("bjbh:==" + strBjbh);
            byClass.setBjbh(strBjbh);
            byClass.setDeptId(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
            byClass.setXn(schoolCommon.getCurrentXn());
            byClass.setCreatetime(new Date());
            return toAjax(byClassService.insertByClass(byClass));
        } else {
            return AjaxResult.error("当前用户非幼儿园，无法创建班级");
        }
    }

    /**
     * 修改班级信息
     */
    @PreAuthorize("@ss.hasPermi('system:class:edit')")
    @Log(title = "班级信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByClass byClass) {
        //首先判断 当前用户是否为学校
        if (schoolCommon.isSchool()) {

            //判断主班教师、配班教师、助理教师的值是否有变化
            ByClass byClassNew = byClassService.selectByClassById(byClass.getBjbh());
            if (byClass.getZbjs() != null) {
                if (byClassNew.getZbjs() == null || !byClassNew.getZbjs().equals(byClass.getZbjs())) {
                    ByClass byClassInfoNew = new ByClass();
                    byClassInfoNew.setZbjs(byClass.getZbjs());
                    byClassInfoNew.setPbjs(byClass.getZbjs());
                    byClassInfoNew.setZljs(byClass.getZbjs());
                    byClassInfoNew = byClassService.selectByClassByUserId(byClassInfoNew);
                    if (byClassInfoNew != null && !schoolCommon.isStringEmpty(byClassInfoNew.getBjbh())) {
                        return AjaxResult.error("当前主班教师已设置为其他班级教师，无法重复，修改班级信息失败");
                    }
                }
            }
            if (byClass.getPbjs() != null) {
                if (byClassNew.getPbjs() == null || !byClassNew.getPbjs().equals(byClass.getPbjs())) {
                    ByClass byClassInfoNew = new ByClass();
                    byClassInfoNew.setZbjs(byClass.getPbjs());
                    byClassInfoNew.setPbjs(byClass.getPbjs());
                    byClassInfoNew.setZljs(byClass.getPbjs());
                    byClassInfoNew = byClassService.selectByClassByUserId(byClassInfoNew);
                    if (byClassInfoNew != null && !schoolCommon.isStringEmpty(byClassInfoNew.getBjbh())) {
                        return AjaxResult.error("当前配班教师已设置为其他班级教师，无法重复，修改班级信息失败");
                    }
                }
            }
            if (byClass.getZljs() != null) {
                if (byClassNew.getZljs() == null || !byClassNew.getZljs().equals(byClass.getZljs())) {
                    ByClass byClassInfoNew = new ByClass();
                    byClassInfoNew.setZbjs(byClass.getZljs());
                    byClassInfoNew.setPbjs(byClass.getZljs());
                    byClassInfoNew.setZljs(byClass.getZljs());
                    byClassInfoNew = byClassService.selectByClassByUserId(byClassInfoNew);
                    if (byClassInfoNew != null && !schoolCommon.isStringEmpty(byClassInfoNew.getBjbh())) {
                        return AjaxResult.error("当前助理教师已设置为其他班级教师，无法重复，修改班级信息失败");
                    }
                }
            }


            return toAjax(byClassService.updateByClass(byClass));
        }
        return AjaxResult.error("当前用户非幼儿园，无法编辑班级");
    }

    /**
     * 清空班级教师信息
     */
    @PreAuthorize("@ss.hasPermi('system:class:edit')")
    @Log(title = "班级信息", businessType = BusinessType.UPDATE)
    @DeleteMapping("/deljs/{bjbhs}")
    public AjaxResult deljs(@PathVariable String bjbhs) {
        //首先判断 当前用户是否为学校
        if (schoolCommon.isSchool()) {
            ByClass byClass = byClassService.selectByClassById(bjbhs);
            byClass.setZbjs(null);
            byClass.setPbjs(null);
            byClass.setZljs(null);
            return toAjax(byClassService.updateByClass(byClass));
        }
        return AjaxResult.error("当前用户非幼儿园，无法删除班级");

    }

    /**
     * 删除班级信息
     */
    @PreAuthorize("@ss.hasPermi('system:class:remove')")
    @Log(title = "班级信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{bjbhs}")
    public AjaxResult remove(@PathVariable String[] bjbhs) {
        //首先判断 当前用户是否为学校
        if (schoolCommon.isSchool()) {

            //判断班级下 是否存在幼儿信息
            if (bjbhs.length > 0) {
                for (int i = 0; i < bjbhs.length; i++) {
                    ByChild byChild = new ByChild();
                    byChild.setClassid(bjbhs[i]);
                    List<ByChild> list = byChildService.selectByChildList(byChild);
                    if (list != null && list.size() > 0) {
                        return AjaxResult.error("当前班级存在幼儿信息，无法删除班级");
                    }
                }
            }

            return toAjax(byClassService.deleteByClassByIds(bjbhs));
        }
        return AjaxResult.error("当前用户非幼儿园，无法删除班级");

    }

    /**
     * 查询班级信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:class:list')" + "||@ss.hasPermi('benyi:checkindetail:list')")
    @GetMapping("/checklist")
    public TableDataInfo checklist(ByClass byClass) {
        startPage();
        List<ByClass> list = byClassService.selectststicstSchoolList(byClass);
        return getDataTable(list);
    }

    /**
     * 根据班级编号查询教师列表
     */
    @GetMapping("/getUserList/{bjbh}")
    public TableDataInfo getUserList(@PathVariable String bjbh) {
        ByClass byClass = byClassService.selectByClassById(bjbh);
        List<SysUser> list = new ArrayList<>();
        if (byClass.getZbjs() != null) {
            SysUser sysUser = userService.selectUserById(byClass.getZbjs());
            list.add(sysUser);
        }
        if (byClass.getPbjs() != null) {
            SysUser sysUser = userService.selectUserById(byClass.getPbjs());
            list.add(sysUser);
        }
        if (byClass.getZljs() != null) {
            SysUser sysUser = userService.selectUserById(byClass.getZljs());
            list.add(sysUser);
        }

        return getDataTable(list);
    }


}