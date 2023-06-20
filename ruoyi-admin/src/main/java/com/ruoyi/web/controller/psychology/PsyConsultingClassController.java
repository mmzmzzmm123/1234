package com.ruoyi.web.controller.psychology;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.psychology.domain.PsyConsultingClass;
import com.ruoyi.psychology.service.IPsyConsultingClassService;
import com.ruoyi.psychology.vo.PsyConsultingClassVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 咨询类型Controller
 * 
 * @author ruoyi
 * @date 2023-06-16
 */
@RestController
@RequestMapping("/psychology/psyClass")
public class PsyConsultingClassController extends BaseController
{
    @Autowired
    private IPsyConsultingClassService psyConsultingClassService;

    /**
     * 查询咨询类型列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:class:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsyConsultingClassVO req)
    {
        startPage();
        List<PsyConsultingClass> list = psyConsultingClassService.getList(req);
        return getDataTable(list);
    }

    /**
     * 导出咨询类型列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:class:export')")
    @Log(title = "咨询类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsyConsultingClassVO req)
    {
        List<PsyConsultingClass> list = psyConsultingClassService.getList(req);
        ExcelUtil<PsyConsultingClass> util = new ExcelUtil<PsyConsultingClass>(PsyConsultingClass.class);
        util.exportExcel(response, list, "咨询类型数据");
    }

    /**
     * 获取咨询类型详细信息
     */
    @PreAuthorize("@ss.hasPermi('psychology:class:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultingClassService.getOne(id));
    }

    /**
     * 新增咨询类型
     */
    @PreAuthorize("@ss.hasPermi('psychology:class:add')")
    @Log(title = "咨询类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PsyConsultingClassVO req)
    {
        return toAjax(psyConsultingClassService.add(req));
    }

    /**
     * 修改咨询类型
     */
    @PreAuthorize("@ss.hasPermi('psychology:class:edit')")
    @Log(title = "咨询类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PsyConsultingClassVO req)
    {
        return toAjax(psyConsultingClassService.update(req));
    }

    /**
     * 删除咨询类型
     */
    @PreAuthorize("@ss.hasPermi('psychology:class:remove')")
    @Log(title = "咨询类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteAll")
    public AjaxResult remove(@RequestBody List<Long> ids)
    {
        return toAjax(psyConsultingClassService.deleteAll(ids));
    }

    @PreAuthorize("@ss.hasPermi('psychology:banner:remove')")
    @Log(title = "咨询类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete")
    public AjaxResult delete(@RequestBody Long id)
    {
        return toAjax(psyConsultingClassService.delete(id));
    }
}
