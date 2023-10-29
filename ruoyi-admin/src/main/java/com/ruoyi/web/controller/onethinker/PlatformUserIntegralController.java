package com.ruoyi.web.controller.onethinker;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.ruoyi.onethinker.dto.PlatformUserIntegralResDTO;
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

import com.ruoyi.onethinker.domain.PlatformUserIntegral;
import com.ruoyi.onethinker.dto.PlatformUserIntegralReqDTO;
import com.ruoyi.onethinker.service.IPlatformUserIntegralService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

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
     * 新增平台用户积分
     */
    @PreAuthorize("@ss.hasPermi('onethinker:integral:add')")
    @Log(title = "添加用户积分", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody PlatformUserIntegralReqDTO reqDTO) {
        return toAjax(platformUserIntegralService.insertPlatformUserIntegral(reqDTO));
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
     * 查询用户积分明细
     */
    @PreAuthorize("@ss.hasPermi('onethinker:integral:update')")
    @Log(title = "积分提现", businessType = BusinessType.UPDATE)
    @PostMapping("/withdrawalIntegral")
    public AjaxResult withdrawalIntegral(@RequestBody PlatformUserIntegralReqDTO reqDTO) {
        return toAjax(platformUserIntegralService.withdrawalIntegral(reqDTO));
    }
}
