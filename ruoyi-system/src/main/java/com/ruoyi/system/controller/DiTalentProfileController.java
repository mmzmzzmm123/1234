package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.DiTalentProfile;
import com.ruoyi.system.service.IDiTalentProfileService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 人才档案管理Controller
 * 
 * @author ruoyi
 * @date 2024-05-11
 */
@RestController
@RequestMapping("/system/profile")
public class DiTalentProfileController extends BaseController
{
    @Autowired
    private IDiTalentProfileService diTalentProfileService;

    /**
     * 查询人才档案管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:profile:list')")
    @GetMapping("/list")
    public TableDataInfo list(DiTalentProfile diTalentProfile)
    {
        startPage();
        List<DiTalentProfile> list = diTalentProfileService.selectDiTalentProfileList(diTalentProfile);
        return getDataTable(list);
    }

    /**
     * 导出人才档案管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:profile:export')")
    @Log(title = "人才档案管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DiTalentProfile diTalentProfile)
    {
        List<DiTalentProfile> list = diTalentProfileService.selectDiTalentProfileList(diTalentProfile);
        ExcelUtil<DiTalentProfile> util = new ExcelUtil<DiTalentProfile>(DiTalentProfile.class);
        util.exportExcel(response, list, "人才档案管理数据");
    }

    /**
     * 获取人才档案管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:profile:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(diTalentProfileService.selectDiTalentProfileById(id));
    }

    /**
     * 新增人才档案管理
     */
    @PreAuthorize("@ss.hasPermi('system:profile:add')")
    @Log(title = "人才档案管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DiTalentProfile diTalentProfile)
    {
        return toAjax(diTalentProfileService.insertDiTalentProfile(diTalentProfile));
    }

    /**
     * 修改人才档案管理
     */
    @PreAuthorize("@ss.hasPermi('system:profile:edit')")
    @Log(title = "人才档案管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DiTalentProfile diTalentProfile)
    {
        return toAjax(diTalentProfileService.updateDiTalentProfile(diTalentProfile));
    }

    /**
     * 删除人才档案管理
     */
    @PreAuthorize("@ss.hasPermi('system:profile:remove')")
    @Log(title = "人才档案管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(diTalentProfileService.deleteDiTalentProfileByIds(ids));
    }
}
