package com.ruoyi.app.controller.psychology;

import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.psychology.domain.PsyConsult;
import com.ruoyi.psychology.request.PsyConsultReq;
import com.ruoyi.psychology.request.PsyConsultServeConfigReq;
import com.ruoyi.psychology.service.IPsyConsultServeConfigService;
import com.ruoyi.psychology.service.IPsyConsultService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 心理咨询师Controller
 * 
 * @author ruoyi
 * @date 2022-08-26
 */
@RestController
@RequestMapping("/app/consult")
public class AppPsyConsultController extends BaseController
{
    @Resource
    private IPsyConsultService psyConsultService;

    @Resource
    private IPsyConsultServeConfigService psyConsultServeConfigService;

    /**
     * 查询心理咨询师列表
     */
    @PostMapping("/search")
    @RateLimiter
    public TableDataInfo list(@RequestBody PsyConsultReq req)
    {
        startPage();
        List<PsyConsult> list = psyConsultService.search(req);
        return getDataTable(list);
    }

    @GetMapping(value = "/getConsultWorksById/{id}")
    @RateLimiter
    public AjaxResult getConsultWorksById(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultService.getConsultWorksById(id));
    }


    /**
     * 获取心理咨询师详细信息
     */
    @GetMapping(value = "/{id}")
    @RateLimiter
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultService.getOne(id));
    }

    @GetMapping(value = "/serve/{id}")
    @RateLimiter
    public AjaxResult getServe(@PathVariable("id") Long id)
    {
        PsyConsultServeConfigReq req = new PsyConsultServeConfigReq();
        req.setCId(id);
        req.setStatus("0");
        return AjaxResult.success(psyConsultServeConfigService.getList(req));
    }



}
