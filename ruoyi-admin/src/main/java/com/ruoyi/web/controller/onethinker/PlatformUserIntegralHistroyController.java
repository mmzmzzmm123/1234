package com.ruoyi.web.controller.onethinker;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.onethinker.domain.PlatformUserIntegralHistory;
import com.ruoyi.onethinker.service.IPlatformUserIntegralHistoryService;

/**
 * 平台用户积分明细Controller
 *
 * @author yangyouqi
 * @date 2023-10-27
 */
@RestController
@RequestMapping("/onethinker/integralHistroy")
public class PlatformUserIntegralHistroyController extends BaseController {

    @Autowired
    private IPlatformUserIntegralHistoryService platformUserIntegralHistoryService;

    /**
     * 查询积分提现申请列表
     */
    @PreAuthorize("@ss.hasPermi('onethinker:integral:list')")
    @Log(title = "查询积分提现申请列表", businessType = BusinessType.QUERY)
    @GetMapping("/withdrawalIntegralList")
    public TableDataInfo withdrawalIntegralList(@RequestBody PlatformUserIntegralHistory platformUserIntegralHistory) {
        startPage();
        List<PlatformUserIntegralHistory> list = platformUserIntegralHistoryService.withdrawalIntegralList(platformUserIntegralHistory);
        return getDataTable(list);
    }

    /**
     * 积分提现审核
     */
    @PreAuthorize("@ss.hasPermi('onethinker:integral:update')")
    @Log(title = "积分提现审核", businessType = BusinessType.UPDATE)
    @PostMapping("/updateWithdrawalIntegral")
    public AjaxResult updateWithdrawalIntegral(@RequestBody PlatformUserIntegralHistory platformUserIntegralHistory) {
        return toAjax(platformUserIntegralHistoryService.updateWithdrawalIntegral(platformUserIntegralHistory));
    }
}
