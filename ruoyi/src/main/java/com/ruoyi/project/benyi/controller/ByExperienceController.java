package com.ruoyi.project.benyi.controller;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.common.SchoolCommon;
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
import com.ruoyi.project.benyi.domain.ByExperience;
import com.ruoyi.project.benyi.service.IByExperienceService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 入班体验申请Controller
 *
 * @author tsbz
 * @date 2020-07-26
 */
@RestController
@RequestMapping("/benyi/experience")
public class ByExperienceController extends BaseController {
    @Autowired
    private IByExperienceService byExperienceService;
    @Autowired
    private SchoolCommon schoolCommon;

    /**
     * 查询入班体验申请列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:experience:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByExperience byExperience) {
        startPage();
        byExperience.setSchoolid(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
        List<ByExperience> list = byExperienceService.selectByExperienceList(byExperience);
        return getDataTable(list);
    }

    /**
     * 导出入班体验申请列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:experience:export')")
    @Log(title = "入班体验申请", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByExperience byExperience) {
        List<ByExperience> list = byExperienceService.selectByExperienceList(byExperience);
        ExcelUtil<ByExperience> util = new ExcelUtil<ByExperience>(ByExperience.class);
        return util.exportExcel(list, "experience");
    }

    /**
     * 获取入班体验申请详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:experience:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byExperienceService.selectByExperienceById(id));
    }

    /**
     * 获取入班体验申请详细信息
     */
    @GetMapping("/getInfo")
    public AjaxResult getInfo_query(ByExperience byExperience) {
        if (schoolCommon.isStringEmpty(byExperience.getYexm())) {
            return AjaxResult.error("请输入幼儿姓名");
        }
        if (schoolCommon.isStringEmpty(byExperience.getLxfs())) {
            return AjaxResult.error("请输入家长联系方式");
        }

        List<ByExperience> list = byExperienceService.selectByExperienceList(byExperience);
        if (list == null || list.size() == 0) {
            return AjaxResult.error("未找到该幼儿的半日入园体验信息");
        }
        return AjaxResult.success(list.get(0));
    }

    /**
     * 新增入班体验申请
     */
//    @PreAuthorize("@ss.hasPermi('benyi:experience:add')")
    @Log(title = "入班体验申请", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody ByExperience byExperience) {
        //首先判断该幼儿是否已提交申请
        ByExperience byExperienceNew = new ByExperience();
        byExperienceNew.setYexm(byExperience.getYexm());
        byExperienceNew.setLxfs(byExperience.getLxfs());
        byExperienceNew.setSchoolid(byExperience.getSchoolid());

        List<ByExperience> list = byExperienceService.selectByExperienceList(byExperienceNew);
        if (list != null && list.size() > 0) {
            return AjaxResult.error("该幼儿已在当前幼儿园申请，不可重复提交");
        }

        return toAjax(byExperienceService.insertByExperience(byExperience));
    }

    /**
     * 修改入班体验申请
     */
    @PreAuthorize("@ss.hasPermi('benyi:experience:edit')")
    @Log(title = "入班体验申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByExperience byExperience) {
        return toAjax(byExperienceService.updateByExperience(byExperience));
    }

    /**
     * 删除入班体验申请
     */
    @PreAuthorize("@ss.hasPermi('benyi:experience:remove')")
    @Log(title = "入班体验申请", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byExperienceService.deleteByExperienceByIds(ids));
    }
}
