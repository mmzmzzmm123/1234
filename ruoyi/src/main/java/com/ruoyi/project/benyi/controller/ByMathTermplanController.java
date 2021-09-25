package com.ruoyi.project.benyi.controller;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.benyi.domain.ByMathTermplanitem;
import com.ruoyi.project.benyi.service.IByMathTermplanitemService;
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
import com.ruoyi.project.benyi.domain.ByMathTermplan;
import com.ruoyi.project.benyi.service.IByMathTermplanService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 游戏数学学期计划Controller
 *
 * @author tsbz
 * @date 2020-10-29
 */
@RestController
@RequestMapping("/benyi/mathtermplan")
public class ByMathTermplanController extends BaseController {
    @Autowired
    private IByMathTermplanService byMathTermplanService;
    @Autowired
    private SchoolCommon schoolCommon;
    @Autowired
    private IByClassService byClassService;
    @Autowired
    private IByMathTermplanitemService byMathTermplanitemService;
    @Autowired
    private ISysUserService userService;

    /**
     * 查询游戏数学学期计划列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:mathtermplan:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByMathTermplan byMathTermplan) {
        byMathTermplan.setSchoolid(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
        String classId = schoolCommon.getClassId();
        List<ByMathTermplan> list = null;
        // 判断当前是否为幼儿园账号
        if (schoolCommon.isSchool() && !schoolCommon.isStringEmpty(classId)) {
            byMathTermplan.setClassid(classId);
        }
        startPage();
        list = byMathTermplanService.selectByMathTermplanList(byMathTermplan);
        return getDataTable(list);
    }

    /**
     * 查询游戏数学学期计划列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:mathtermplan:list')")
    @GetMapping("/listsp")
    public TableDataInfo listsp(ByMathTermplan byMathTermplan) {
        byMathTermplan.setSchoolid(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
        List<ByMathTermplan> list = null;
        startPage();
        list = byMathTermplanService.selectByMathTermplanList(byMathTermplan);
        return getDataTable(list);
    }

    /**
     * 导出游戏数学学期计划列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:mathtermplan:export')")
    @Log(title = "游戏数学学期计划", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByMathTermplan byMathTermplan) {
        List<ByMathTermplan> list = byMathTermplanService.selectByMathTermplanList(byMathTermplan);
        ExcelUtil<ByMathTermplan> util = new ExcelUtil<ByMathTermplan>(ByMathTermplan.class);
        return util.exportExcel(list, "mathtermplan");
    }

    /**
     * 获取游戏数学学期计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:mathtermplan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        AjaxResult ajax = AjaxResult.success();
        ByMathTermplan byMathTermplan = byMathTermplanService.selectByMathTermplanById(id);
        ajax.put(AjaxResult.DATA_TAG, byMathTermplan);
        ajax.put("classname", byClassService.selectByClassById(byMathTermplan.getClassid()).getBjmc());
        ajax.put("createusername", userService.selectUserById(byMathTermplan.getCreateuserid()).getNickName());
        return ajax;
    }

    /**
     * 新增游戏数学学期计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:mathtermplan:add')")
    @Log(title = "游戏数学学期计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByMathTermplan byMathTermplan) {
        String classId = schoolCommon.getClassId();

        if (schoolCommon.isSchool() && !schoolCommon.isStringEmpty(classId)) {
            // 判断当前班级是否为托班
            String bjtypeNew = byClassService.selectByClassById(classId).getBjtype();
            if (bjtypeNew.equals("1")) {
                return AjaxResult.error("当前班级为托班，无法创建计划");
            } else {
                int iCount = schoolCommon.getDifMonth(byMathTermplan.getStartmonth(), byMathTermplan.getEndmonth());
                System.out.println("月份差=" + iCount);
                String uuid = schoolCommon.getUuid();
                byMathTermplan.setId(uuid);
                byMathTermplan.setSchoolid(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
                byMathTermplan.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
                byMathTermplan.setClassid(classId);
                byMathTermplan.setName(byClassService.selectByClassById(classId).getBjmc() + "-游戏数学学期计划");

                // 自动创建游戏数学学期计划明细
                ByMathTermplanitem byMathTermplanitem = null;
                for (int i = 0; i <= iCount; i++) {
                    byMathTermplanitem = new ByMathTermplanitem();
                    byMathTermplanitem.setTpid(uuid);
                    byMathTermplanitem.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
                    byMathTermplanitem.setMonth(schoolCommon.DateAddMonths(i, byMathTermplan.getStartmonth()));
                    //创建时间
                    byMathTermplanitem.setCreateTime(new Date());
                    //新增每月计划
                    byMathTermplanitemService.insertByMathTermplanitem(byMathTermplanitem);
                }
                return toAjax(byMathTermplanService.insertByMathTermplan(byMathTermplan));
            }
        } else {
            return AjaxResult.error("当前用户非幼儿园班级教师，无法创建计划");
        }
    }

    /**
     * 修改游戏数学学期计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:mathtermplan:edit')")
    @Log(title = "游戏数学学期计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByMathTermplan byMathTermplan) {
        return toAjax(byMathTermplanService.updateByMathTermplan(byMathTermplan));
    }

    /**
     * 删除游戏数学学期计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:mathtermplan:remove')")
    @Log(title = "游戏数学学期计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        //首先判断当前id下是否存在子目录
        for (int i = 0; i < ids.length; i++) {
            ByMathTermplanitem byMathTermplanitem = new ByMathTermplanitem();
            byMathTermplanitem.setTpid(ids[i]);
            List<ByMathTermplanitem> list = byMathTermplanitemService.selectByMathTermplanitemList(byMathTermplanitem);
            if (list != null && list.size() > 0) {
                //return AjaxResult.error("选中的计划下存在计划明细，无法删除");
                for (int j = 0; j < list.size(); j++) {
                    byMathTermplanitemService.deleteByMathTermplanitemById(list.get(j).getId());
                }
            }
        }
        return toAjax(byMathTermplanService.deleteByMathTermplanByIds(ids));
    }

    /**
     * 提交游戏数学学期计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:themetermplan:edit')")
    @Log(title = "游戏数学学期计划", businessType = BusinessType.UPDATE)
    @PostMapping("/check/{id}")
    public AjaxResult check(@PathVariable String id) {
        ByMathTermplan byMathTermplan = new ByMathTermplan();
        byMathTermplan.setId(id);
        byMathTermplan.setStatus("1");
        return toAjax(byMathTermplanService.updateByMathTermplan(byMathTermplan));
    }
}
