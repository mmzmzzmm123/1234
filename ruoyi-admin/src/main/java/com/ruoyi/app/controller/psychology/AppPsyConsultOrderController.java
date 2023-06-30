package com.ruoyi.app.controller.psychology;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.psychology.service.IPsyConsultServeService;
import com.ruoyi.psychology.service.IPsyConsultService;
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
@RequestMapping("/app/consult/order")
public class AppPsyConsultOrderController extends BaseController
{
    @Resource
    private IPsyConsultService psyConsultService;

    @Resource
    private IPsyConsultServeService psyConsultServeService;

    /**
     * 获取心理咨询师详细信息
     */
    @GetMapping(value = "/getConsultInfoByServe/{id}")
    public AjaxResult getConsultInfoByServe(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultService.getConsultInfoByServe(id));
    }


}
