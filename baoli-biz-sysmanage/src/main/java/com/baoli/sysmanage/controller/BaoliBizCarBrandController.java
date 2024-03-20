package com.baoli.sysmanage.controller;

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
import com.baoli.sysmanage.domain.BaoliBizCarBrand;
import com.baoli.sysmanage.service.IBaoliBizCarBrandService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 汽车品牌Controller
 * 
 * @author niujs
 * @date 2024-03-18
 */
@RestController
@RequestMapping("/sysmanage/carbrand")
public class BaoliBizCarBrandController extends BaseController
{
    @Autowired
    private IBaoliBizCarBrandService baoliBizCarBrandService;

    /**
     * 查询汽车品牌列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carbrand:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizCarBrand baoliBizCarBrand)
    {
        if(baoliBizCarBrand.getPageable() == null || baoliBizCarBrand.getPageable()== 1){
            startPage();
        }
        List<BaoliBizCarBrand> list = baoliBizCarBrandService.selectBaoliBizCarBrandList(baoliBizCarBrand);
        return getDataTable(list);
    }

    /**
     * 导出汽车品牌列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carbrand:export')")
    @Log(title = "汽车品牌", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizCarBrand baoliBizCarBrand)
    {
        List<BaoliBizCarBrand> list = baoliBizCarBrandService.selectBaoliBizCarBrandList(baoliBizCarBrand);
        ExcelUtil<BaoliBizCarBrand> util = new ExcelUtil<BaoliBizCarBrand>(BaoliBizCarBrand.class);
        util.exportExcel(response, list, "汽车品牌数据");
    }

    /**
     * 获取汽车品牌详细信息
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carbrand:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizCarBrandService.selectBaoliBizCarBrandById(id));
    }

    /**
     * 新增汽车品牌
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carbrand:add')")
    @Log(title = "汽车品牌", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizCarBrand baoliBizCarBrand)
    {
        return toAjax(baoliBizCarBrandService.insertBaoliBizCarBrand(baoliBizCarBrand));
    }

    /**
     * 修改汽车品牌
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carbrand:edit')")
    @Log(title = "汽车品牌", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizCarBrand baoliBizCarBrand)
    {
        return toAjax(baoliBizCarBrandService.updateBaoliBizCarBrand(baoliBizCarBrand));
    }

    /**
     * 删除汽车品牌
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carbrand:remove')")
    @Log(title = "汽车品牌", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizCarBrandService.deleteBaoliBizCarBrandByIds(ids));
    }
}
