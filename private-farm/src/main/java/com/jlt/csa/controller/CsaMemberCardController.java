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
import com.jlt.csa.domain.CsaMemberCard;
import com.jlt.csa.service.ICsaMemberCardService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 会员卡Controller
 * 
 * @author JiaLeitao
 * @date 2022-03-26
 */
@RestController
@RequestMapping("/csa/card")
public class CsaMemberCardController extends BaseController
{
    @Autowired
    private ICsaMemberCardService csaMemberCardService;

    /**
     * 查询会员卡列表
     */
    @PreAuthorize("@ss.hasPermi('csa:card:list')")
    @GetMapping("/list")
    public TableDataInfo list(CsaMemberCard csaMemberCard)
    {
        startPage();
        List<CsaMemberCard> list = csaMemberCardService.selectCsaMemberCardList(csaMemberCard);
        return getDataTable(list);
    }

    /**
     * 导出会员卡列表
     */
    @PreAuthorize("@ss.hasPermi('csa:card:export')")
    @Log(title = "会员卡", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CsaMemberCard csaMemberCard)
    {
        List<CsaMemberCard> list = csaMemberCardService.selectCsaMemberCardList(csaMemberCard);
        ExcelUtil<CsaMemberCard> util = new ExcelUtil<CsaMemberCard>(CsaMemberCard.class);
        util.exportExcel(response, list, "会员卡数据");
    }

    /**
     * 获取会员卡详细信息
     */
    @PreAuthorize("@ss.hasPermi('csa:card:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(csaMemberCardService.selectCsaMemberCardById(id));
    }

    /**
     * 新增会员卡
     */
    @PreAuthorize("@ss.hasPermi('csa:card:add')")
    @Log(title = "会员卡", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CsaMemberCard csaMemberCard)
    {
        return toAjax(csaMemberCardService.insertCsaMemberCard(csaMemberCard));
    }

    /**
     * 修改会员卡
     */
    @PreAuthorize("@ss.hasPermi('csa:card:edit')")
    @Log(title = "会员卡", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CsaMemberCard csaMemberCard)
    {
        return toAjax(csaMemberCardService.updateCsaMemberCard(csaMemberCard));
    }

    /**
     * 删除会员卡
     */
    @PreAuthorize("@ss.hasPermi('csa:card:remove')")
    @Log(title = "会员卡", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(csaMemberCardService.deleteCsaMemberCardByIds(ids));
    }
}
