package com.renxin.app.controller.psychology;

import com.renxin.common.annotation.RateLimiter;
import com.renxin.common.core.controller.BaseController;
import com.renxin.common.core.domain.AjaxResult;
import com.renxin.framework.web.service.AppTokenService;
import com.renxin.psychology.domain.PsyConsultPartner;
import com.renxin.psychology.domain.PsyConsultPartnerItem;
import com.renxin.psychology.service.IPsyConsultPartnerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 心理咨询师Controller
 * 
 * @author renxin
 * @date 2022-08-26
 */
@RestController
@RequestMapping("/app/consult/partner")
public class AppPsyConsultPartnerController extends BaseController
{

    @Resource
    private AppTokenService appTokenService;

    @Resource
    private IPsyConsultPartnerService partnerService;

    @PostMapping(value = "/addItem")
    @RateLimiter
    public AjaxResult addItem(@RequestBody PsyConsultPartnerItem item)
    {
        return AjaxResult.success(partnerService.addItem(item));
    }

    @PostMapping(value = "/editItem")
    @RateLimiter
    public AjaxResult editItem(@RequestBody PsyConsultPartnerItem item)
    {
        return AjaxResult.success(partnerService.editItem(item));
    }

    @PostMapping(value = "/delItem/{id}")
    @RateLimiter
    public AjaxResult delItem(@PathVariable("id") Long id)
    {
        return AjaxResult.success(partnerService.delItem(id));
    }

    @PostMapping(value = "/draft")
    @RateLimiter
    public AjaxResult draft(HttpServletRequest request)
    {
        Integer userId = appTokenService.getUserId(request);
        partnerService.draft(userId);
        return AjaxResult.success();
    }

    @PostMapping(value = "/save")
    @RateLimiter
    public AjaxResult save(@RequestBody PsyConsultPartner entity, HttpServletRequest request)
    {
        Integer userId = appTokenService.getUserId(request);
        entity.setUserId(userId);
        return AjaxResult.success(partnerService.save(entity));
    }

    @PostMapping(value = "/getInfo")
    @RateLimiter
    public AjaxResult getInfo(HttpServletRequest request)
    {
        Integer userId = appTokenService.getUserId(request);
        return AjaxResult.success(partnerService.getInfoByUserId(userId));
    }

}
