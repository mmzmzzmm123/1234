package com.ruoyi.project.benyi.controller;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
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
public class ByMathTermplanController extends BaseController
{
    @Autowired
    private IByMathTermplanService byMathTermplanService;
    @Autowired
    private SchoolCommon schoolCommon;
    @Autowired
    private IByClassService byClassService;

/**
 * 查询游戏数学学期计划列表
 */
@PreAuthorize("@ss.hasPermi('benyi:mathtermplan:list')")
@GetMapping("/list")
        public TableDataInfo list(ByMathTermplan byMathTermplan)
    {
        startPage();
        List<ByMathTermplan> list = byMathTermplanService.selectByMathTermplanList(byMathTermplan);
        return getDataTable(list);
    }
    
    /**
     * 导出游戏数学学期计划列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:mathtermplan:export')")
    @Log(title = "游戏数学学期计划", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByMathTermplan byMathTermplan)
    {
        List<ByMathTermplan> list = byMathTermplanService.selectByMathTermplanList(byMathTermplan);
        ExcelUtil<ByMathTermplan> util = new ExcelUtil<ByMathTermplan>(ByMathTermplan.class);
        return util.exportExcel(list, "mathtermplan");
    }

    /**
     * 获取游戏数学学期计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:mathtermplan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(byMathTermplanService.selectByMathTermplanById(id));
    }

    /**
     * 新增游戏数学学期计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:mathtermplan:add')")
    @Log(title = "游戏数学学期计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByMathTermplan byMathTermplan)
    {
        String classId = schoolCommon.getClassId();

        int iCount = schoolCommon.getDifMonth(byMathTermplan.getStartmonth(), byMathTermplan.getEndmonth());
        System.out.println("月份差=" + iCount);
        String uuid = schoolCommon.getUuid();
        byMathTermplan.setId(uuid);
        byMathTermplan.setSchoolid(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
        byMathTermplan.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
        byMathTermplan.setClassid(classId);
        byMathTermplan.setName(byClassService.selectByClassById(classId).getBjmc() + "-主题整合学期计划");
        return toAjax(byMathTermplanService.insertByMathTermplan(byMathTermplan));
    }

    /**
     * 修改游戏数学学期计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:mathtermplan:edit')")
    @Log(title = "游戏数学学期计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByMathTermplan byMathTermplan)
    {
        return toAjax(byMathTermplanService.updateByMathTermplan(byMathTermplan));
    }

    /**
     * 删除游戏数学学期计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:mathtermplan:remove')")
    @Log(title = "游戏数学学期计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(byMathTermplanService.deleteByMathTermplanByIds(ids));
    }

    /**
     * 提交主题整合学期计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:themetermplan:edit')")
    @Log(title = "主题整合学期计划", businessType = BusinessType.UPDATE)
    @PostMapping("/check/{id}")
    public AjaxResult check(@PathVariable String id) {
        ByMathTermplan byMathTermplan = new ByMathTermplan();
        byMathTermplan.setId(id);
        byMathTermplan.setStatus("1");
        return toAjax(byMathTermplanService.updateByMathTermplan(byMathTermplan));
    }
}
