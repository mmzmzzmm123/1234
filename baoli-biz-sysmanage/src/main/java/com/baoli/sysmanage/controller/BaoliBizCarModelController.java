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
import com.baoli.sysmanage.domain.BaoliBizCarModel;
import com.baoli.sysmanage.service.IBaoliBizCarModelService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 汽车型号Controller
 * 
 * @author niujs
 * @date 2024-03-20
 */
@RestController
@RequestMapping("/sysmanage/carModel")
public class BaoliBizCarModelController extends BaseController
{
    @Autowired
    private IBaoliBizCarModelService baoliBizCarModelService;

    /**
     * 查询汽车型号列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carModel:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizCarModel baoliBizCarModel)
    {
        startPage();
        List<BaoliBizCarModel> list = baoliBizCarModelService.selectBaoliBizCarModelList(baoliBizCarModel);
        return getDataTable(list);
    }

    /**
     * 导出汽车型号列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carModel:export')")
    @Log(title = "汽车型号", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizCarModel baoliBizCarModel)
    {
        List<BaoliBizCarModel> list = baoliBizCarModelService.selectBaoliBizCarModelList(baoliBizCarModel);
        ExcelUtil<BaoliBizCarModel> util = new ExcelUtil<BaoliBizCarModel>(BaoliBizCarModel.class);
        util.exportExcel(response, list, "汽车型号数据");
    }

    /**
     * 获取汽车型号详细信息
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carModel:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizCarModelService.selectBaoliBizCarModelById(id));
    }

    /**
     * 新增汽车型号
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carModel:add')")
    @Log(title = "汽车型号", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizCarModel baoliBizCarModel)
    {
        return toAjax(baoliBizCarModelService.insertBaoliBizCarModel(baoliBizCarModel));
    }

    /**
     * 修改汽车型号
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carModel:edit')")
    @Log(title = "汽车型号", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizCarModel baoliBizCarModel)
    {
        return toAjax(baoliBizCarModelService.updateBaoliBizCarModel(baoliBizCarModel));
    }

    /**
     * 删除汽车型号
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:carModel:remove')")
    @Log(title = "汽车型号", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizCarModelService.deleteBaoliBizCarModelByIds(ids));
    }
}
