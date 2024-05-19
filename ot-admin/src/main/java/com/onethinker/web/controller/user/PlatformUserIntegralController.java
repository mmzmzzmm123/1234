package com.onethinker.web.controller.user;

import com.onethinker.user.domain.PlatformUserIntegral;
import com.onethinker.user.dto.PlatformUserIntegralReqDTO;
import com.onethinker.user.dto.PlatformUserIntegralResDTO;
import com.onethinker.user.service.IPlatformUserIntegralService;
import com.onethinker.common.annotation.Log;
import com.onethinker.common.core.controller.BaseController;
import com.onethinker.common.core.domain.AjaxResult;
import com.onethinker.common.core.page.TableDataInfo;
import com.onethinker.common.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 平台用户积分Controller
 *
 * @author yangyouqi
 * @date 2023-10-27
 */
@RestController
@RequestMapping("/onethinker/integral")
public class PlatformUserIntegralController extends BaseController {
    @Autowired
    private IPlatformUserIntegralService platformUserIntegralService;

    /**
     * 查询平台用户积分列表
     */
    @PreAuthorize("@ss.hasPermi('onethinker:integral:list')")
    @Log(title = "查询用户积分列表", businessType = BusinessType.QUERY)
    @GetMapping("/list")
    public TableDataInfo list(PlatformUserIntegral platformUserIntegral) {
        startPage();
        List<PlatformUserIntegralResDTO> list = platformUserIntegralService.selectPlatformUserIntegralList(platformUserIntegral);
        return getDataTable(list);
    }

    /**
     * 查询用户积分明细
     */
    @PreAuthorize("@ss.hasPermi('onethinker:integral:list')")
    @Log(title = "查询用户积分明细", businessType = BusinessType.QUERY)
    @PostMapping("/queryIntegralDetail")
    public TableDataInfo queryIntegralDetail(@RequestBody PlatformUserIntegralReqDTO reqDTO) {
        startPage();
        return getDataTable(platformUserIntegralService.queryIntegralDetail(reqDTO));
    }

    /**
     * 新增平台用户积分
     */
    @PreAuthorize("@ss.hasPermi('onethinker:integral:add')")
    @Log(title = "添加用户积分", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody PlatformUserIntegralReqDTO reqDTO) {
        return toAjax(platformUserIntegralService.insertPlatformUserIntegral(reqDTO));
    }

    /**
     * 积分提现申请
     */
    @PreAuthorize("@ss.hasPermi('onethinker:integral:update')")
    @Log(title = "积分提现", businessType = BusinessType.UPDATE)
    @PostMapping("/withdrawalIntegral")
    public AjaxResult withdrawalIntegral(@RequestBody PlatformUserIntegralReqDTO reqDTO) {
        return toAjax(platformUserIntegralService.withdrawalIntegral(reqDTO));
    }
}
