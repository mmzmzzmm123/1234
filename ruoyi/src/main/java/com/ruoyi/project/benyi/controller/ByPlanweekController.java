package com.ruoyi.project.benyi.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.benyi.domain.ByPlanweekitem;
import com.ruoyi.project.benyi.domain.ByPlanweekitem;
import com.ruoyi.project.benyi.service.impl.ByPlanweekServiceImpl;
import com.ruoyi.project.benyi.service.IByPlanweekitemService;

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
import com.ruoyi.project.benyi.domain.ByPlanweek;
import com.ruoyi.project.benyi.service.IByPlanweekService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 周计划(家长和教育部门)Controller
 *
 * @author tsbz
 * @date 2020-08-24
 */
@RestController
@RequestMapping("/benyi/planweek")
public class ByPlanweekController extends BaseController {
    @Autowired
    private IByPlanweekService byPlanweekService;
    @Autowired
    private SchoolCommon schoolCommon;
    @Autowired
    private IByClassService byClassService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private IByPlanweekitemService byPlanweekItemService;

    /**
     * 查询周计划(家长和教育部门)列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:planweek:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByPlanweek byPlanweek) {
        startPage();
        byPlanweek.setSchoolid(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
        String classId = schoolCommon.getClassId();
        List<ByPlanweek> list =null;
        //首先判断当前账户是否为幼儿园账号
        if (schoolCommon.isSchool() && !schoolCommon.isStringEmpty(classId)) {
            byPlanweek.setClassid(classId);
            list = byPlanweekService.selectByPlanweekList(byPlanweek);
        }

        return getDataTable(list);
    }

    /**
     * 导出周计划(家长和教育部门)列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:planweek:export')")
    @Log(title = "周计划(家长和教育部门)", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByPlanweek byPlanweek) {
        List<ByPlanweek> list = byPlanweekService.selectByPlanweekList(byPlanweek);
        ExcelUtil<ByPlanweek> util = new ExcelUtil<ByPlanweek>(ByPlanweek.class);
        return util.exportExcel(list, "planweek");
    }

    /**
     * 获取周计划(家长和教育部门)详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:planweek:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        AjaxResult ajax=AjaxResult.success();
        ByPlanweek byPlanweek = byPlanweekService.selectByPlanweekById(id);
        ajax.put(AjaxResult.DATA_TAG, byPlanweek);
        ajax.put("classname", byClassService.selectByClassById(byPlanweek.getClassid()).getBjmc());
        ajax.put("createusername",userService.selectUserById(byPlanweek.getCreateuserid()).getNickName());
        return ajax;
    }

    /**
     * 新增周计划(家长和教育部门)
     */
    @PreAuthorize("@ss.hasPermi('benyi:planweek:add')")
    @Log(title = "周计划(家长和教育部门)", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByPlanweek byPlanweek) {
        String classId = schoolCommon.getClassId();
        // 首先判断当前账户是否为幼儿园账号
        if (schoolCommon.isSchool() && !schoolCommon.isStringEmpty(classId)) {
            String uuid = schoolCommon.getUuid();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            byPlanweek.setName(byPlanweek.getName()+"("+sdf.format(byPlanweek.getStarttime())+'-'+sdf.format(byPlanweek.getEndtime())+")");
            byPlanweek.setId(uuid);
            //学校id
            byPlanweek.setSchoolid(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
            //创建人id
            byPlanweek.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
            //班级id
            byPlanweek.setClassid(classId);
            byPlanweek.setStatus("0");
            return toAjax(byPlanweekService.insertByPlanweek(byPlanweek));
        } else {
            return AjaxResult.error("当前用户非幼儿园，无法添加计划");
        }
    }

    /**
     * 修改周计划(家长和教育部门)
     */
    @PreAuthorize("@ss.hasPermi('benyi:planweek:edit')")
    @Log(title = "周计划(家长和教育部门)", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByPlanweek byPlanweek) {
        return toAjax(byPlanweekService.updateByPlanweek(byPlanweek));
    }

    /**
     * 提交审核周计划(家长和教育部门)
     */
    @PreAuthorize("@ss.hasPermi('benyi:planweek:edit')")
    @Log(title = "周计划(家长和教育部门)", businessType = BusinessType.UPDATE)
    @PostMapping("/check/{ids}")
    public AjaxResult check(@PathVariable String[] ids) {
        int iCount = 0;
        ByPlanweek byPlanweek = null;
        for (int i = 0; i < ids.length; i++) {
            byPlanweek = new ByPlanweek();
            byPlanweek.setId(ids[i]);
            byPlanweek.setStatus("1");
            //byPlanweek.setShrid(SecurityUtils.getLoginUser().getUser().getUserId());

            iCount = iCount + byPlanweekService.updateByPlanweek(byPlanweek);
        }
        return toAjax(iCount);
    }

    /**
     * 删除周计划(家长和教育部门)
     */
    @PreAuthorize("@ss.hasPermi('benyi:planweek:remove')")
    @Log(title = "周计划(家长和教育部门)", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        //首先判断当前id下是否存在子目录
        for (int i = 0; i < ids.length; i++) {
            ByPlanweekitem byPlanweekitem = new ByPlanweekitem();
            byPlanweekitem.setWid(ids[i]);
            List<ByPlanweekitem> list = byPlanweekItemService.selectByPlanweekitemList(byPlanweekitem);
            if (list != null && list.size() > 0) {
                return AjaxResult.error("选中的计划下存在子计划，无法删除");
            }
        }
        return toAjax(byPlanweekService.deleteByPlanweekByIds(ids));
    }
}
