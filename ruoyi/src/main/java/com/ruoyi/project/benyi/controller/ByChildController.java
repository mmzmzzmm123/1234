package com.ruoyi.project.benyi.controller;

import java.util.Date;
import java.util.List;

import java.util.ArrayList;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.security.service.TokenService;
import com.ruoyi.project.benyi.domain.ByAssessmentchild;
import com.ruoyi.project.benyi.domain.ByChildContactpeople;
import com.ruoyi.project.benyi.service.IByAssessmentchildService;
import com.ruoyi.project.benyi.service.IByChildContactpeopleService;
import com.ruoyi.project.common.SchoolCommon;
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
import com.ruoyi.project.benyi.domain.ByChild;
import com.ruoyi.project.benyi.service.IByChildService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 幼儿信息Controller
 *
 * @author tsbz
 * @date 2020-07-20
 */
@RestController
@RequestMapping("/benyi/child")
public class ByChildController extends BaseController {
    @Autowired
    private IByChildService byChildService;
    @Autowired
    private SchoolCommon schoolCommon;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private IByChildContactpeopleService byChildContactpeopleService;
    @Autowired
    private ISysDictDataService sysDictDataService;
    @Autowired
    private IByAssessmentchildService byAssessmentchildService;

    /**
     * 查询幼儿信息列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:child:list')" + "||@ss.hasPermi('benyi:checkinstatisticsschool:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByChild byChild) {

        byChild.setSchoolid(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
        //判断是否为班主任
        String classId = schoolCommon.getClassId();
        if (!schoolCommon.isStringEmpty(classId)) {
            byChild.setClassid(classId);
        }
        startPage();
        List<ByChild> list = byChildService.selectByChildList(byChild);
        return getDataTable(list);
    }

    /**
     * 查询幼儿考勤列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:checkindetail:list')")
    @GetMapping("/listByCheck")
    public TableDataInfo listByCheck(ByChild byChild) {
        byChild.setSchoolid(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
        //判断是否为班主任
        //System.out.println(schoolCommon.getClassId());
        String classId = schoolCommon.getClassId();
        if (!schoolCommon.isStringEmpty(classId)) {
            byChild.setClassid(classId);
        }
        List<ByChild> list = byChildService.selectByCheckList(byChild);
        return getDataTable(list);
    }

    /**
     * 导出幼儿信息列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:child:export')")
    @Log(title = "幼儿信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByChild byChild) {
        List<ByChild> list = byChildService.selectByChildList(byChild);
        ExcelUtil<ByChild> util = new ExcelUtil<ByChild>(ByChild.class);
        return util.exportExcel(list, "child");
    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('benyi:child:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<ByChild> util = new ExcelUtil<ByChild>(ByChild.class);
        List<ByChild> childList = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        Long deptId = loginUser.getUser().getDeptId();
        String bjbh = schoolCommon.getClassId();
        String message = byChildService.importChild(childList, operName, deptId, bjbh);
        return AjaxResult.success(message);
    }

    @GetMapping("/importTemplate")
    public AjaxResult importTemplate() {
        ExcelUtil<ByChild> util = new ExcelUtil<ByChild>(ByChild.class);
        return util.importTemplateExcel("幼儿数据");
    }

    /**
     * 获取幼儿信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:child:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byChildService.selectByChildById(id));
    }

    /**
     * 获取幼儿信息详细信息-评估用
     */
    @PreAuthorize("@ss.hasPermi('benyi:child:query')")
    @GetMapping(value = "/assessmentchild/{id}")
    public AjaxResult getChildInfo(@PathVariable("id") Long id) {
        AjaxResult ajax = AjaxResult.success();
        ByChild byChild = byChildService.selectByChildById(id);
        ajax.put(AjaxResult.DATA_TAG, byChild);
        //根据生日判断评估适用项 36-48:1 48-60:2 60-72:3
        System.out.println("csrq:" + byChild.getCsrq());
        if (byChild.getCsrq() != null) {
            int iMonths = schoolCommon.getDifMonth(new Date(), byChild.getCsrq());
            if (iMonths >= 36 && iMonths <= 48) {
                ajax.put("isAssessment", 1);
            } else if (iMonths >= 48 && iMonths < 60) {
                ajax.put("isAssessment", 2);
            } else if (iMonths >= 60 && iMonths <= 72) {
                ajax.put("isAssessment", 3);
            } else {
                ajax.put("isAssessment", 0);
            }
            ajax.put("months", iMonths);
        } else {
            ajax.put("isAssessment", 0);
        }

        ajax.put("trem", schoolCommon.getCurrentXnXq());
        ByAssessmentchild byAssessmentchild = new ByAssessmentchild();
        byAssessmentchild.setChildid(id);
        byAssessmentchild.setXn(schoolCommon.getCurrentXnXq());
        List<ByAssessmentchild> list = byAssessmentchildService.selectByAssessmentchildList(byAssessmentchild);
        ajax.put("ByAssessmentchild", list);
        return ajax;
    }

    /**
     * 获取维护幼儿信息
     */
    @GetMapping("/getInfo")
    public AjaxResult getInfo_query(ByChild byChild) {
        AjaxResult ajax = AjaxResult.success();

        if (schoolCommon.isStringEmpty(byChild.getName())) {
            return AjaxResult.error("请输入幼儿姓名");
        }
        if (schoolCommon.isStringEmpty(byChild.getPhone())) {
            return AjaxResult.error("请输入家长联系方式");
        }

        List<ByChild> list = byChildService.selectByChildList(byChild);
        if (list == null || list.size() == 0) {
            return AjaxResult.error("未找到该幼儿的信息");
        }
        ajax.put(AjaxResult.DATA_TAG, list.get(0));
        ajax.put("sexAll", sysDictDataService.selectDictDataByType("sys_user_sex"));
        ajax.put("statusAll", sysDictDataService.selectDictDataByType("sys_normal_disable"));
        ajax.put("mzAll", sysDictDataService.selectDictDataByType("sys_dm_mz"));
        ajax.put("ynAll", sysDictDataService.selectDictDataByType("sys_yes_no"));
        ajax.put("sourceAll", sysDictDataService.selectDictDataByType("sys_dm_ryqd"));
        ajax.put("jtgxAll", sysDictDataService.selectDictDataByType("sys_dm_jtgx"));
        return ajax;
    }

    /**
     * 新增幼儿信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:child:add')")
    @Log(title = "幼儿信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByChild byChild, @RequestBody ByChildContactpeople byChildContactpeople) {
        //首先判断当前账户是否为幼儿园账号
        if (schoolCommon.isSchool()) {
            //学校id
            byChild.setSchoolid(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
            //创建人id
            byChild.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
            //班级id
            if (schoolCommon.isStringEmpty(byChild.getClassid())) {
                byChild.setClassid(schoolCommon.getClassId());
            }
            int i = byChildService.insertByChild(byChild);
            Long chilId = byChild.getId();
            if (i > 0) {
                chilId = byChild.getId();
//                System.out.println("newId:" + chilId);
//                System.out.println("byChildContactpeople:" + byChildContactpeople);
//                byChildContactpeople.setChildid(chilId);
//                byChildContactpeopleService.insertByChildContactpeople(byChildContactpeople);
                return AjaxResult.success(chilId.intValue());
            } else {
                return AjaxResult.error("创建失败，请联系管理员");
            }
        } else {
            return AjaxResult.error("当前用户非幼儿园，无法添加幼儿");
        }
    }

    /**
     * 修改幼儿信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:child:edit')")
    @Log(title = "幼儿信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByChild byChild) {
        return toAjax(byChildService.updateByChild(byChild));
    }

    @PutMapping("/edit")
    public AjaxResult editchild(@RequestBody ByChild byChild) {
        return toAjax(byChildService.updateByChild(byChild));
    }

    /**
     * 修改幼儿信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:child:edit')")
    @Log(title = "幼儿信息", businessType = BusinessType.UPDATE)
    @PutMapping("/{ids}")
    public AjaxResult edit(@RequestBody ByChild byChild, @PathVariable Long[] ids) {
        int iCount = 0;
        for (int i = 0; i < ids.length; i++) {
            Long id = ids[i];
            System.out.println("classid=" + byChild.getClassid() + "   " + "id=" + id);
            byChild.setId(id);
            iCount = iCount + byChildService.updateByChild(byChild);
        }
        return toAjax(iCount);
    }

    /**
     * 删除幼儿信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:child:remove')")
    @Log(title = "幼儿信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        //删除幼儿信息 同时删除幼儿联系人信息
        byChildContactpeopleService.deleteByChildContactpeopleByChildIds(ids);
        return toAjax(byChildService.deleteByChildByIds(ids));
    }

    /**
     * 查询幼儿考勤信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:child:list')" + "||@ss.hasPermi('benyi:checkindetail:list')")
    @GetMapping("/checklist")
    public TableDataInfo checklist(ByChild byClass) {
        String strClassId = schoolCommon.getClassId();
        if (!schoolCommon.isStringEmpty(strClassId)) {
            startPage();
            byClass.setClassid(strClassId);
            List<ByChild> list = byChildService.selectststicstClassList(byClass);
            return getDataTable(list);
        } else {
            List<ByChild> list = new ArrayList<>();
            return getDataTable(list);
        }

    }
}
