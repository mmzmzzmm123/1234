package com.renxin.web.controller.psychology;

import com.renxin.common.annotation.Log;
import com.renxin.common.core.controller.BaseController;
import com.renxin.common.core.domain.AjaxResult;
import com.renxin.common.core.page.TableDataInfo;
import com.renxin.common.enums.BusinessType;
import com.renxin.common.utils.poi.ExcelUtil;
import com.renxin.psychology.domain.PsyConsultClass;
import com.renxin.psychology.service.IPsyConsultClassService;
import com.renxin.psychology.vo.PsyConsultClassVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 咨询类型Controller
 * 
 * @author renxin
 * @date 2023-06-16
 */
@RestController
@RequestMapping("/psychology/psyClass")
public class PsyConsultClassController extends BaseController
{
    @Autowired
    private IPsyConsultClassService psyConsultClassService;

    /**
     * 查询咨询类型列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:class:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsyConsultClassVO req)
    {
        startPage();
        List<PsyConsultClass> list = psyConsultClassService.getList(req);
        return getDataTable(list);
    }

    /**
     * 查询咨询类型列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:class:list')")
    @GetMapping("/getAll")
    public AjaxResult getAll(PsyConsultClassVO req)
    {
        List<PsyConsultClass> list = psyConsultClassService.getList(req);
        return AjaxResult.success(list);
    }

    /**
     * 导出咨询类型列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:class:export')")
    @Log(title = "咨询类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsyConsultClassVO req)
    {
        List<PsyConsultClass> list = psyConsultClassService.getList(req);
        ExcelUtil<PsyConsultClass> util = new ExcelUtil<PsyConsultClass>(PsyConsultClass.class);
        util.exportExcel(response, list, "咨询类型数据");
    }

    /**
     * 获取咨询类型详细信息
     */
    @PreAuthorize("@ss.hasPermi('psychology:class:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultClassService.getOne(id));
    }

    /**
     * 新增咨询类型
     */
    @PreAuthorize("@ss.hasPermi('psychology:class:add')")
    @Log(title = "咨询类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PsyConsultClassVO req)
    {
        return toAjax(psyConsultClassService.add(req));
    }

    /**
     * 修改咨询类型
     */
    @PreAuthorize("@ss.hasPermi('psychology:class:edit')")
    @Log(title = "咨询类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PsyConsultClassVO req)
    {
        return toAjax(psyConsultClassService.update(req));
    }

    /**
     * 删除咨询类型
     */
    @PreAuthorize("@ss.hasPermi('psychology:class:remove')")
    @Log(title = "咨询类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        if (ids == null || ids.length == 0) {
            return error("请选择数据进行删除");
        }
        return toAjax(psyConsultClassService.deleteAll(ids));
    }
}
