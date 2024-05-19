//package com.onethinker.web.controller.user;
//
//import com.onethinker.user.domain.PlatformUserIntegralHistory;
//import com.onethinker.user.service.IPlatformUserIntegralHistoryService;
//import com.onethinker.common.annotation.Log;
//import com.onethinker.common.core.controller.BaseController;
//import com.onethinker.common.core.domain.AjaxResult;
//import com.onethinker.common.core.page.TableDataInfo;
//import com.onethinker.common.enums.BusinessType;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * 平台用户积分明细Controller
// *
// * @author yangyouqi
// * @date 2023-10-27
// */
//@RestController
//@RequestMapping("/onethinker/history")
//public class PlatformUserIntegralHistroyController extends BaseController {
//
//    @Autowired
//    private IPlatformUserIntegralHistoryService platformUserIntegralHistoryService;
//
//    /**
//     * 查询积分提现申请列表
//     */
//    @PreAuthorize("@ss.hasPermi('onethinker:integral:list')")
//    @Log(title = "查询积分提现申请列表", businessType = BusinessType.QUERY)
//    @GetMapping("/withdrawalIntegralList")
//    public TableDataInfo withdrawalIntegralList(PlatformUserIntegralHistory platformUserIntegralHistory) {
//        startPage();
//        List<PlatformUserIntegralHistory> list = platformUserIntegralHistoryService.withdrawalIntegralList(platformUserIntegralHistory);
//        return getDataTable(list);
//    }
//
//    /**
//     * 积分提现审核
//     */
//    @PreAuthorize("@ss.hasPermi('onethinker:integral:update')")
//    @Log(title = "积分提现审核", businessType = BusinessType.UPDATE)
//    @PostMapping("/updateWithdrawalIntegral")
//    public AjaxResult updateWithdrawalIntegral(@RequestBody PlatformUserIntegralHistory platformUserIntegralHistory) {
//        return toAjax(platformUserIntegralHistoryService.updateWithdrawalIntegral(platformUserIntegralHistory));
//    }
//}
