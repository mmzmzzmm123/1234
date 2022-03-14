package com.ruoyi.web.controller.info;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Size;

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
import com.ruoyi.info.domain.TradeInfo;
import com.ruoyi.info.service.ITradeInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 交易信息Controller
 * 
 * @author ruoyi
 * @date 2022-03-10
 */
@RestController
@RequestMapping("/info/trade")
public class TradeInfoController extends BaseController
{
    @Autowired
    private ITradeInfoService tradeInfoService;

    /**
     * 查询交易信息列表
     */
    @PreAuthorize("@ss.hasPermi('info:trade:list')")
    @GetMapping("/list")
    public TableDataInfo list(TradeInfo tradeInfo)
    {
        startPage();
        List<TradeInfo> list = tradeInfoService.selectTradeInfoList(tradeInfo);
        return getDataTable(list);
    }

    /**
     * 导出交易信息列表
     */
    @PreAuthorize("@ss.hasPermi('info:trade:export')")
    @Log(title = "交易信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TradeInfo tradeInfo)
    {
        List<TradeInfo> list = tradeInfoService.selectTradeInfoList(tradeInfo);
        ExcelUtil<TradeInfo> util = new ExcelUtil<TradeInfo>(TradeInfo.class);
        util.exportExcel(response, list, "交易信息数据");
    }

    /**
     * 获取交易信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('info:trade:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tradeInfoService.selectTradeInfoById(id));
    }

    /**
     * 新增交易信息
     */
    @PreAuthorize("@ss.hasPermi('info:trade:add')")
    @Log(title = "交易信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TradeInfo tradeInfo)
    {
        String nickName = getLoginUser().getUser().getNickName();
        tradeInfo.setOperatorName(nickName);
        return toAjax(tradeInfoService.insertTradeInfo(tradeInfo));
    }

    /**
     * 修改交易信息
     */
    @PreAuthorize("@ss.hasPermi('info:trade:edit')")
    @Log(title = "交易信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TradeInfo tradeInfo)
    {
        return toAjax(tradeInfoService.updateTradeInfo(tradeInfo));
    }

    /**
     * 删除交易信息
     */
    @PreAuthorize("@ss.hasPermi('info:trade:remove')")
    @Log(title = "交易信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tradeInfoService.deleteTradeInfoByIds(ids));
    }
}
