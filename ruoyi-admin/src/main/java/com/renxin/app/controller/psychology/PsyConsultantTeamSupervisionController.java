package com.renxin.app.controller.psychology;

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
import com.renxin.common.annotation.Log;
import com.renxin.common.core.controller.BaseController;
import com.renxin.common.core.domain.AjaxResult;
import com.renxin.common.enums.BusinessType;
import com.renxin.psychology.domain.PsyConsultantTeamSupervision;
import com.renxin.psychology.service.IPsyConsultantTeamSupervisionService;
import com.renxin.common.utils.poi.ExcelUtil;
import com.renxin.common.core.page.TableDataInfo;

/**
 * 咨询师团体Controller
 * 
 * @author renxin
 * @date 2024-06-26
 */
@RestController
@RequestMapping("/system/supervision")
public class PsyConsultantTeamSupervisionController extends BaseController
{
    @Autowired
    private IPsyConsultantTeamSupervisionService psyConsultantTeamSupervisionService;

    /**
     * 查询咨询师团体列表
     */
    @PreAuthorize("@ss.hasPermi('system:supervision:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsyConsultantTeamSupervision psyConsultantTeamSupervision)
    {
        startPage();
        List<PsyConsultantTeamSupervision> list = psyConsultantTeamSupervisionService.selectPsyConsultantTeamSupervisionList(psyConsultantTeamSupervision);
        return getDataTable(list);
    }

    /**
     * 导出咨询师团体列表
     */
    @PreAuthorize("@ss.hasPermi('system:supervision:export')")
    @Log(title = "咨询师团体", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsyConsultantTeamSupervision psyConsultantTeamSupervision)
    {
        List<PsyConsultantTeamSupervision> list = psyConsultantTeamSupervisionService.selectPsyConsultantTeamSupervisionList(psyConsultantTeamSupervision);
        ExcelUtil<PsyConsultantTeamSupervision> util = new ExcelUtil<PsyConsultantTeamSupervision>(PsyConsultantTeamSupervision.class);
        util.exportExcel(response, list, "咨询师团体数据");
    }

    /**
     * 获取咨询师团体详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:supervision:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultantTeamSupervisionService.selectPsyConsultantTeamSupervisionById(id));
    }

    /**
     * 新增咨询师团体
     */
    @PreAuthorize("@ss.hasPermi('system:supervision:add')")
    @Log(title = "咨询师团体", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PsyConsultantTeamSupervision psyConsultantTeamSupervision)
    {
        return toAjax(psyConsultantTeamSupervisionService.insertPsyConsultantTeamSupervision(psyConsultantTeamSupervision));
    }

    /**
     * 修改咨询师团体
     */
    @PreAuthorize("@ss.hasPermi('system:supervision:edit')")
    @Log(title = "咨询师团体", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PsyConsultantTeamSupervision psyConsultantTeamSupervision)
    {
        return toAjax(psyConsultantTeamSupervisionService.updatePsyConsultantTeamSupervision(psyConsultantTeamSupervision));
    }

    /**
     * 删除咨询师团体
     */
    @PreAuthorize("@ss.hasPermi('system:supervision:remove')")
    @Log(title = "咨询师团体", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(psyConsultantTeamSupervisionService.deletePsyConsultantTeamSupervisionByIds(ids));
    }
}
