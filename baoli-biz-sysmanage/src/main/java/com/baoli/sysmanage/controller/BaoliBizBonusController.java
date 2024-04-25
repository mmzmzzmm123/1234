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
import com.baoli.sysmanage.domain.BaoliBizBonus;
import com.baoli.sysmanage.service.IBaoliBizBonusService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 提成Controller
 * 
 * @author niujs
 * @date 2024-04-25
 */
@RestController
@RequestMapping("/sysmanage/bonus")
public class BaoliBizBonusController extends BaseController
{
    @Autowired
    private IBaoliBizBonusService baoliBizBonusService;

    /**
     * 查询提成列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bonus:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizBonus baoliBizBonus)
    {
        startPage();
        List<BaoliBizBonus> list = baoliBizBonusService.selectBaoliBizBonusList(baoliBizBonus);
        return getDataTable(list);
    }

    /**
     * 导出提成列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bonus:export')")
    @Log(title = "提成", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizBonus baoliBizBonus)
    {
        List<BaoliBizBonus> list = baoliBizBonusService.selectBaoliBizBonusList(baoliBizBonus);
        ExcelUtil<BaoliBizBonus> util = new ExcelUtil<BaoliBizBonus>(BaoliBizBonus.class);
        util.exportExcel(response, list, "提成数据");
    }

    /**
     * 获取提成详细信息
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bonus:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizBonusService.selectBaoliBizBonusById(id));
    }

    /**
     * 新增提成
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bonus:add')")
    @Log(title = "提成", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizBonus baoliBizBonus)
    {
        return toAjax(baoliBizBonusService.insertBaoliBizBonus(baoliBizBonus));
    }

    /**
     * 修改提成
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bonus:edit')")
    @Log(title = "提成", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizBonus baoliBizBonus)
    {
        return toAjax(baoliBizBonusService.updateBaoliBizBonus(baoliBizBonus));
    }

    /**
     * 删除提成
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bonus:remove')")
    @Log(title = "提成", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizBonusService.deleteBaoliBizBonusByIds(ids));
    }
}
