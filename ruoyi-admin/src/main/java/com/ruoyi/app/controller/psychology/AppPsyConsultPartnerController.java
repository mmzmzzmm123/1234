package com.ruoyi.app.controller.psychology;

import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.framework.web.service.AppTokenService;
import com.ruoyi.psychology.domain.PsyConsultPartner;
import com.ruoyi.psychology.domain.PsyConsultPartnerItem;
import com.ruoyi.psychology.dto.PartnerDTO;
import com.ruoyi.psychology.request.PartnerReq;
import com.ruoyi.psychology.service.IPsyConsultPartnerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 心理咨询师Controller
 * 
 * @author ruoyi
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
        return AjaxResult.success(partnerService.getInfo(userId));
    }

}
