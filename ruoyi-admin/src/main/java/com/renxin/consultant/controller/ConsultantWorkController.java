package com.renxin.consultant.controller;

import com.renxin.common.annotation.RateLimiter;
import com.renxin.common.core.controller.BaseController;
import com.renxin.common.core.domain.AjaxResult;
import com.renxin.common.core.domain.dto.ConsultDTO;
import com.renxin.common.core.page.TableDataInfo;
import com.renxin.framework.web.service.ConsultantTokenService;
import com.renxin.psychology.request.PsyWorkReq;
import com.renxin.psychology.service.IPsyConsultWorkService;
import com.renxin.psychology.vo.PsyConsultWorkVO;
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
@RequestMapping("/consultant/work")
@Api(value = "ConsultantWorkController" ,tags = {" 排班api"})
public class ConsultantWorkController extends BaseController {

    @Autowired
    private ConsultantTokenService consultantTokenService;

    @Resource
    private IPsyConsultWorkService psyConsultWorkService;

    @ApiOperation(value = "咨询师排版计划表")
    @PostMapping("/todo")
    @RateLimiter
    public TableDataInfo list(@RequestBody PsyWorkReq req, HttpServletRequest request)
    {
        ConsultDTO loginUser = consultantTokenService.getLoginUser(request);
        if(loginUser != null){
            req.setIds(Collections.singletonList(loginUser.getConsultId()));
        }
        startPage();
        List<HashMap<String, String>>  list = psyConsultWorkService.getWorks(req);
        return getDataTable(list);
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
        if(list != null && !list.isEmpty()){
            return AjaxResult.successData(list);
        }else{
            return AjaxResult.success();
        }

    }

    @ApiOperation(value = "咨询师排班新增")
    @PostMapping("/create")
    public AjaxResult getWorks(@RequestBody PsyConsultWorkVO req,HttpServletRequest request)
    {
        ConsultDTO loginUser = consultantTokenService.getLoginUser(request);
        if(loginUser != null){
            req.setConsultId(loginUser.getConsultId());
        }
        return AjaxResult.success(psyConsultWorkService.add(req));
    }
}