package com.ruoyi.consult.controller;

import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.ConsultDTO;
import com.ruoyi.framework.web.service.ConsultantTokenService;
import com.ruoyi.psychology.request.PsyWorkReq;
import com.ruoyi.psychology.service.IPsyConsultWorkService;
import com.ruoyi.psychology.vo.PsyConsultWorkVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/consult/class")
@Api(value = "ConsultWorkController" ,tags = {" 排班api"})
public class ConsultWorkController extends BaseController {

    @Autowired
    private ConsultantTokenService consultantTokenService;

    @Resource
    private IPsyConsultWorkService psyConsultWorkService;

    @ApiOperation(value = "咨询师排版计划表")
    @PostMapping("/todo")
    @RateLimiter
    public AjaxResult list(@RequestBody PsyWorkReq req,HttpServletRequest request)
    {
        ConsultDTO loginUser = consultantTokenService.getLoginUser(request);
        if(loginUser != null){
            req.setIds(Collections.singletonList(loginUser.getConsultId()));
        }
        startPage();
        List<HashMap<String, String>>  list = psyConsultWorkService.getWorks(req);
        return AjaxResult.success(getDataTable(list));
    }

    @ApiOperation(value = "咨询师排班详情")
    @PostMapping("/detail")
    public AjaxResult detail(@RequestBody PsyWorkReq req,HttpServletRequest request)
    {
        ConsultDTO loginUser = consultantTokenService.getLoginUser(request);
        if(loginUser != null){
            req.setIds(Collections.singletonList(loginUser.getConsultId()));
        }
        HashMap<String, String> list = psyConsultWorkService.getWorkDetail(req);
        return AjaxResult.success(list);
    }

    @ApiOperation(value = "咨询师排班新增")
    @GetMapping("/create")
    public AjaxResult getWorks(@RequestBody PsyConsultWorkVO req,HttpServletRequest request)
    {
        ConsultDTO loginUser = consultantTokenService.getLoginUser(request);
        psyConsultWorkService.add(req);
        return AjaxResult.success();
    }
}
