package com.ruoyi.app.controller;

import com.ruoyi.common.annotation.TokenValidation;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.service.IMasterplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 5g快签相关接口
 */
@RestController
@Api(value = "5g快签")
@RequestMapping("/app/masterplate")
public class AppMasterplateController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(AppMasterplateController.class);
    @Autowired
    private IMasterplateService masterplateService;

    @PostMapping("/route")
    @ApiOperation(value = "5g快签路由转发")
    @TokenValidation
    public AjaxResult addAuthorizationRecord(@RequestBody Object body) {
        log.info("5g快签路由转发 param:{}",body);
        return masterplateService.sendMasterplate(body);
    }

}
