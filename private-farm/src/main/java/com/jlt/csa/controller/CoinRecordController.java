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
import com.jlt.csa.domain.CoinRecord;
import com.jlt.csa.service.ICoinRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 金币记录Controller
 * 
 * @author JiaLeitao
 * @date 2022-03-27
 */
@RestController
@RequestMapping("/csa/coinrecord")
public class CoinRecordController extends BaseController
{
    @Autowired
    private ICoinRecordService coinRecordService;

    /**
     * 查询金币记录列表
     */
    @PreAuthorize("@ss.hasPermi('csa:coinrecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(CoinRecord coinRecord)
    {
        startPage();
        List<CoinRecord> list = coinRecordService.selectCoinRecordList(coinRecord);
        return getDataTable(list);
    }

    /**
     * 导出金币记录列表
     */
    @PreAuthorize("@ss.hasPermi('csa:coinrecord:export')")
    @Log(title = "金币记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CoinRecord coinRecord)
    {
        List<CoinRecord> list = coinRecordService.selectCoinRecordList(coinRecord);
        ExcelUtil<CoinRecord> util = new ExcelUtil<CoinRecord>(CoinRecord.class);
        util.exportExcel(response, list, "金币记录数据");
    }

    /**
     * 获取金币记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('csa:coinrecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(coinRecordService.selectCoinRecordById(id));
    }

    /**
     * 新增金币记录
     */
    @PreAuthorize("@ss.hasPermi('csa:coinrecord:add')")
    @Log(title = "金币记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CoinRecord coinRecord)
    {
        return toAjax(coinRecordService.insertCoinRecord(coinRecord));
    }

    /**
     * 修改金币记录
     */
    @PreAuthorize("@ss.hasPermi('csa:coinrecord:edit')")
    @Log(title = "金币记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CoinRecord coinRecord)
    {
        return toAjax(coinRecordService.updateCoinRecord(coinRecord));
    }

    /**
     * 删除金币记录
     */
    @PreAuthorize("@ss.hasPermi('csa:coinrecord:remove')")
    @Log(title = "金币记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(coinRecordService.deleteCoinRecordByIds(ids));
    }
}
