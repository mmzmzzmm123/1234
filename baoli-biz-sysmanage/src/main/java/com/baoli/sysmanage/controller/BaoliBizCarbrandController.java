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
import com.baoli.sysmanage.domain.BaoliBizCarbrand;
import com.baoli.sysmanage.service.IBaoliBizCarbrandService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 汽车品牌Controller
 * 
 * @author niujs
 * @date 2024-03-12
 */
@RestController
@RequestMapping("/sysmanage/carbrand")
public class BaoliBizCarbrandController extends BaseController
{
    @Autowired
    private IBaoliBizCarbrandService baoliBizCarbrandService;

    /**
     * 查询汽车品牌列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carbrand:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizCarbrand baoliBizCarbrand)
    {
        if(baoliBizCarbrand.isPageAble()){
            startPage();
        }
        List<BaoliBizCarbrand> list = baoliBizCarbrandService.selectBaoliBizCarbrandList(baoliBizCarbrand);
        return getDataTable(list);
    }

    /**
     * 导出汽车品牌列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carbrand:export')")
    @Log(title = "汽车品牌", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizCarbrand baoliBizCarbrand)
    {
        List<BaoliBizCarbrand> list = baoliBizCarbrandService.selectBaoliBizCarbrandList(baoliBizCarbrand);
        ExcelUtil<BaoliBizCarbrand> util = new ExcelUtil<BaoliBizCarbrand>(BaoliBizCarbrand.class);
        util.exportExcel(response, list, "汽车品牌数据");
    }

    /**
     * 获取汽车品牌详细信息
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carbrand:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizCarbrandService.selectBaoliBizCarbrandById(id));
    }

    /**
     * 新增汽车品牌
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carbrand:add')")
    @Log(title = "汽车品牌", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizCarbrand baoliBizCarbrand)
    {
        return toAjax(baoliBizCarbrandService.insertBaoliBizCarbrand(baoliBizCarbrand));
    }

    /**
     * 修改汽车品牌
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carbrand:edit')")
    @Log(title = "汽车品牌", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizCarbrand baoliBizCarbrand)
    {
        return toAjax(baoliBizCarbrandService.updateBaoliBizCarbrand(baoliBizCarbrand));
    }

    /**
     * 删除汽车品牌
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carbrand:remove')")
    @Log(title = "汽车品牌", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizCarbrandService.deleteBaoliBizCarbrandByIds(ids));
    }
}
