package com.ruoyi.udef.controller;

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
import com.ruoyi.udef.domain.DfApiKey;
import com.ruoyi.udef.service.IDfApiKeyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * APIController
 * 
 * @author ruoyi
 * @date 2022-06-08
 */
@RestController
@RequestMapping("/udef/key")
public class DfApiKeyController extends BaseController
{
    @Autowired
    private IDfApiKeyService dfApiKeyService;

    /**
     * 查询API列表
     */
    @PreAuthorize("@ss.hasPermi('udef:key:list')")
    @GetMapping("/list")
    public TableDataInfo list(DfApiKey dfApiKey)
    {
        startPage();
        List<DfApiKey> list = dfApiKeyService.selectDfApiKeyList(dfApiKey);
        return getDataTable(list);
    }

    /**
     * 导出API列表
     */
    @PreAuthorize("@ss.hasPermi('udef:key:export')")
    @Log(title = "API", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DfApiKey dfApiKey)
    {
        List<DfApiKey> list = dfApiKeyService.selectDfApiKeyList(dfApiKey);
        ExcelUtil<DfApiKey> util = new ExcelUtil<DfApiKey>(DfApiKey.class);
        util.exportExcel(response, list, "API数据");
    }

    /**
     * 获取API详细信息
     */
    @PreAuthorize("@ss.hasPermi('udef:key:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(dfApiKeyService.selectDfApiKeyById(id));
    }

    /**
     * 新增API
     */
    @PreAuthorize("@ss.hasPermi('udef:key:add')")
    @Log(title = "API", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DfApiKey dfApiKey)
    {
        return toAjax(dfApiKeyService.insertDfApiKey(dfApiKey));
    }

    /**
     * 修改API
     */
    @PreAuthorize("@ss.hasPermi('udef:key:edit')")
    @Log(title = "API", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DfApiKey dfApiKey)
    {
        return toAjax(dfApiKeyService.updateDfApiKey(dfApiKey));
    }

    /**
     * 删除API
     */
    @PreAuthorize("@ss.hasPermi('udef:key:remove')")
    @Log(title = "API", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dfApiKeyService.deleteDfApiKeyByIds(ids));
    }
}
