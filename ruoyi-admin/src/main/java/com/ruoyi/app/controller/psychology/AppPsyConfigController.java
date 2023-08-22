package com.ruoyi.app.controller.psychology;

import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.psychology.service.IPsyConsultConfigService;
import com.ruoyi.psychology.service.IPsyConsultTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 心理咨询师Controller
 * 
 * @author ruoyi
 * @date 2022-08-26
 */
@RestController
@RequestMapping("/app/consult/config")
public class AppPsyConfigController extends BaseController
{
    @Resource
    private IPsyConsultConfigService psyConsultConfigService;

    @Resource
    private IPsyConsultTypeService psyConsultTypeService;

    @GetMapping("/getDateNum/{num}")
    @RateLimiter
    public AjaxResult getConfigByType(@PathVariable("num") Integer num)
    {
        return AjaxResult.success(psyConsultConfigService.getDateNum(num));
    }

    /**
     * 根据类型查询字典
     */
    @GetMapping("/getConfigByType/{dictType}")
    @RateLimiter
    public AjaxResult getConfigByType(@PathVariable("dictType") String dictType)
    {
        return AjaxResult.success(psyConsultConfigService.getConfigByType(dictType));
    }

    @GetMapping("/getConfigByTypes/{dictTypes}")
    @RateLimiter
    public AjaxResult getConfigByTypes(@PathVariable(value = "dictTypes") String[] dictTypes)
    {
        return AjaxResult.success(psyConsultConfigService.getConfigByTypes(dictTypes));
    }

    @GetMapping("/getTrees")
    @RateLimiter
    public AjaxResult getTrees()
    {
        return AjaxResult.success(psyConsultTypeService.getTrees());
    }

    @GetMapping("/getNotices")
    @RateLimiter
    public AjaxResult getNotices()
    {
        return AjaxResult.success(psyConsultConfigService.getNotices());
    }


}
