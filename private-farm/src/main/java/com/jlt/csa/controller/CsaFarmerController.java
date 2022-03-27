package com.jlt.csa.controller;

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
import com.jlt.csa.domain.CsaFarmer;
import com.jlt.csa.service.ICsaFarmerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 私家农场会员Controller
 * 
 * @author JiaLeitao
 * @date 2022-03-26
 */
@RestController
@RequestMapping("/csa/farmer")
public class CsaFarmerController extends BaseController
{
    @Autowired
    private ICsaFarmerService csaFarmerService;

    /**
     * 查询私家农场会员列表
     */
    @PreAuthorize("@ss.hasPermi('csa:farmer:list')")
    @GetMapping("/list")
    public TableDataInfo list(CsaFarmer csaFarmer)
    {
        startPage();
        List<CsaFarmer> list = csaFarmerService.selectCsaFarmerList(csaFarmer);
        return getDataTable(list);
    }

    /**
     * 导出私家农场会员列表
     */
    @PreAuthorize("@ss.hasPermi('csa:farmer:export')")
    @Log(title = "私家农场会员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CsaFarmer csaFarmer)
    {
        List<CsaFarmer> list = csaFarmerService.selectCsaFarmerList(csaFarmer);
        ExcelUtil<CsaFarmer> util = new ExcelUtil<CsaFarmer>(CsaFarmer.class);
        util.exportExcel(response, list, "私家农场会员数据");
    }

    /**
     * 获取私家农场会员详细信息
     */
    @PreAuthorize("@ss.hasPermi('csa:farmer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(csaFarmerService.selectCsaFarmerById(id));
    }

    /**
     * 新增私家农场会员
     */
    @PreAuthorize("@ss.hasPermi('csa:farmer:add')")
    @Log(title = "私家农场会员", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CsaFarmer csaFarmer)
    {
        return toAjax(csaFarmerService.insertCsaFarmer(csaFarmer));
    }

    /**
     * 修改私家农场会员
     */
    @PreAuthorize("@ss.hasPermi('csa:farmer:edit')")
    @Log(title = "私家农场会员", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CsaFarmer csaFarmer)
    {
        return toAjax(csaFarmerService.updateCsaFarmer(csaFarmer));
    }

    /**
     * 删除私家农场会员
     */
    @PreAuthorize("@ss.hasPermi('csa:farmer:remove')")
    @Log(title = "私家农场会员", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(csaFarmerService.deleteCsaFarmerByIds(ids));
    }
}
