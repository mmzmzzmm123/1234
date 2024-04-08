package com.baoli.store.controller;

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
import com.baoli.store.domain.BaoliBizStore;
import com.baoli.store.service.IBaoliBizStoreService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 商户管理Controller
 * 
 * @author niujs
 * @date 2024-04-08
 */
@RestController
@RequestMapping("/store/store")
public class BaoliBizStoreController extends BaseController
{
    @Autowired
    private IBaoliBizStoreService baoliBizStoreService;

    /**
     * 查询商户管理列表
     */
    @PreAuthorize("@ss.hasPermi('store:store:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizStore baoliBizStore)
    {
        startPage();
        List<BaoliBizStore> list = baoliBizStoreService.selectBaoliBizStoreList(baoliBizStore);
        return getDataTable(list);
    }

    /**
     * 导出商户管理列表
     */
    @PreAuthorize("@ss.hasPermi('store:store:export')")
    @Log(title = "商户管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizStore baoliBizStore)
    {
        List<BaoliBizStore> list = baoliBizStoreService.selectBaoliBizStoreList(baoliBizStore);
        ExcelUtil<BaoliBizStore> util = new ExcelUtil<BaoliBizStore>(BaoliBizStore.class);
        util.exportExcel(response, list, "商户管理数据");
    }

    /**
     * 获取商户管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('store:store:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizStoreService.selectBaoliBizStoreById(id));
    }

    /**
     * 新增商户管理
     */
    @PreAuthorize("@ss.hasPermi('store:store:add')")
    @Log(title = "商户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizStore baoliBizStore)
    {
        return toAjax(baoliBizStoreService.insertBaoliBizStore(baoliBizStore));
    }

    /**
     * 修改商户管理
     */
    @PreAuthorize("@ss.hasPermi('store:store:edit')")
    @Log(title = "商户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizStore baoliBizStore)
    {
        return toAjax(baoliBizStoreService.updateBaoliBizStore(baoliBizStore));
    }

    /**
     * 删除商户管理
     */
    @PreAuthorize("@ss.hasPermi('store:store:remove')")
    @Log(title = "商户管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizStoreService.deleteBaoliBizStoreByIds(ids));
    }
}
