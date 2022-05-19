package com.ruoyi.system.controller;

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
import com.ruoyi.system.domain.Getzichanxifen;
import com.ruoyi.system.service.IGetzichanxifenService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 资产细分Controller
 * 
 * @author gongyu
 * @date 2022-05-19
 */
@RestController
@RequestMapping("/invest/getzichanxifen")
public class GetzichanxifenController extends BaseController
{
    @Autowired
    private IGetzichanxifenService getzichanxifenService;

    /**
     * 查询资产细分列表
     */
    @PreAuthorize("@ss.hasPermi('invest:getzichanxifen:list')")
    @GetMapping("/list")
    public TableDataInfo list(Getzichanxifen getzichanxifen)
    {
        startPage();
        List<Getzichanxifen> list = getzichanxifenService.selectGetzichanxifenList(getzichanxifen);
        return getDataTable(list);
    }

    /**
     * 导出资产细分列表
     */
    @PreAuthorize("@ss.hasPermi('invest:getzichanxifen:export')")
    @Log(title = "资产细分", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Getzichanxifen getzichanxifen)
    {
        List<Getzichanxifen> list = getzichanxifenService.selectGetzichanxifenList(getzichanxifen);
        ExcelUtil<Getzichanxifen> util = new ExcelUtil<Getzichanxifen>(Getzichanxifen.class);
        util.exportExcel(response, list, "资产细分数据");
    }

    /**
     * 获取资产细分详细信息
     */
    @PreAuthorize("@ss.hasPermi('invest:getzichanxifen:query')")
    @GetMapping(value = "/{name}")
    public AjaxResult getInfo(@PathVariable("name") String name)
    {
        return AjaxResult.success(getzichanxifenService.selectGetzichanxifenByName(name));
    }

    /**
     * 新增资产细分
     */
    @PreAuthorize("@ss.hasPermi('invest:getzichanxifen:add')")
    @Log(title = "资产细分", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Getzichanxifen getzichanxifen)
    {
        return toAjax(getzichanxifenService.insertGetzichanxifen(getzichanxifen));
    }

    /**
     * 修改资产细分
     */
    @PreAuthorize("@ss.hasPermi('invest:getzichanxifen:edit')")
    @Log(title = "资产细分", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Getzichanxifen getzichanxifen)
    {
        return toAjax(getzichanxifenService.updateGetzichanxifen(getzichanxifen));
    }

    /**
     * 删除资产细分
     */
    @PreAuthorize("@ss.hasPermi('invest:getzichanxifen:remove')")
    @Log(title = "资产细分", businessType = BusinessType.DELETE)
	@DeleteMapping("/{names}")
    public AjaxResult remove(@PathVariable String[] names)
    {
        return toAjax(getzichanxifenService.deleteGetzichanxifenByNames(names));
    }
}
