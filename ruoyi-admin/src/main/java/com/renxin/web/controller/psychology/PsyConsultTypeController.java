package com.renxin.web.controller.psychology;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.renxin.psychology.vo.PsyConsultTypeVO;
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
import com.renxin.psychology.domain.PsyConsultType;
import com.renxin.psychology.service.IPsyConsultTypeService;
import com.renxin.common.utils.poi.ExcelUtil;

/**
 * 咨询类型Controller
 * 
 * @author renxin
 * @date 2023-07-14
 */
@RestController
@RequestMapping("/psychology/type")
public class PsyConsultTypeController extends BaseController
{
    @Autowired
    private IPsyConsultTypeService psyConsultTypeService;

    /**
     * 查询咨询类型列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:type:list')")
    @GetMapping("/list")
    public AjaxResult list(PsyConsultType psyConsultType)
    {
        List<PsyConsultType> list = psyConsultTypeService.selectPsyConsultTypeList(psyConsultType);
        return AjaxResult.success(list);
    }

    @GetMapping("/getTrees")
    public AjaxResult getTrees()
    {
        return AjaxResult.success(psyConsultTypeService.getTrees());
    }

    /**
     * 导出咨询类型列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:type:export')")
    @Log(title = "咨询类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsyConsultType psyConsultType)
    {
        List<PsyConsultType> list = psyConsultTypeService.selectPsyConsultTypeList(psyConsultType);
        ExcelUtil<PsyConsultType> util = new ExcelUtil<PsyConsultType>(PsyConsultType.class);
        util.exportExcel(response, list, "咨询类型数据");
    }

    /**
     * 获取咨询类型详细信息
     */
    @PreAuthorize("@ss.hasPermi('psychology:type:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultTypeService.selectPsyConsultTypeById(id));
    }

    /**
     * 新增咨询类型
     */
    @PreAuthorize("@ss.hasPermi('psychology:type:add')")
    @Log(title = "咨询类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PsyConsultTypeVO req)
    {
        return toAjax(psyConsultTypeService.add(req));
    }

    /**
     * 修改咨询类型
     */
    @PreAuthorize("@ss.hasPermi('psychology:type:edit')")
    @Log(title = "咨询类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PsyConsultTypeVO req)
    {
        return toAjax(psyConsultTypeService.update(req));
    }

    /**
     * 删除咨询类型
     */
    @PreAuthorize("@ss.hasPermi('psychology:type:remove')")
    @Log(title = "咨询类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(psyConsultTypeService.deleteAll(ids));
    }
}
