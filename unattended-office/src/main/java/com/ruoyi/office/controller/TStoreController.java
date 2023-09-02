package com.ruoyi.office.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import io.swagger.annotations.ApiOperation;
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
import com.ruoyi.office.domain.TStore;
import com.ruoyi.office.service.ITStoreService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 商家用户店铺Controller
 *
 * @author ruoyi
 * @date 2023-05-29
 */
@RestController
@RequestMapping("/office/store")
public class TStoreController extends BaseController {
    @Autowired
    private ITStoreService tStoreService;

    /**
     * 查询商家用户店铺列表
     */
    @ApiOperation("门店列表")
    @PreAuthorize("@ss.hasPermi('office:store:list')")
    @GetMapping("/list")
    public TableDataInfo list(TStore tStore) {
        if (!SecurityUtils.getUsername().equalsIgnoreCase("admin"))
            tStore.setCreateBy(SecurityUtils.getUserId() + "");
        startPage();
        List<TStore> list = tStoreService.selectTStoreList(tStore);
        return getDataTable(list);
    }

    /**
     * 导出商家用户店铺列表
     */
    @PreAuthorize("@ss.hasPermi('office:store:export')")
    @Log(title = "商家用户店铺", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TStore tStore) {
        List<TStore> list = tStoreService.selectTStoreList(tStore);
        ExcelUtil<TStore> util = new ExcelUtil<TStore>(TStore.class);
        util.exportExcel(response, list, "商家用户店铺数据");
    }

    /**
     * 获取商家用户店铺详细信息
     */
    @ApiOperation("门店详情")
    @PreAuthorize("@ss.hasPermi('office:store:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(tStoreService.selectTStoreById(id));
    }

    /**
     * 新增商家用户店铺
     */
    @PreAuthorize("@ss.hasPermi('office:store:add')")
    @Log(title = "商家用户店铺", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TStore tStore) {
        tStore.setCreateBy(SecurityUtils.getUserId() + "");
        return toAjax(tStoreService.insertTStore(tStore));
    }

    /**
     * 修改商家用户店铺
     */
    @PreAuthorize("@ss.hasPermi('office:store:edit')")
    @Log(title = "商家用户店铺", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TStore tStore) {
        tStore.setUpdateBy(SecurityUtils.getUserId() + "");
        return toAjax(tStoreService.updateTStore(tStore));
    }

    /**
     * 删除商家用户店铺
     */
    @PreAuthorize("@ss.hasPermi('office:store:remove')")
    @Log(title = "商家用户店铺", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tStoreService.deleteTStoreByIds(ids));
    }
}
