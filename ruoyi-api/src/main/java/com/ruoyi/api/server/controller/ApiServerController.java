package com.ruoyi.api.server.controller;

import com.ruoyi.api.server.service.ApiServerService;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.service.domain.ServiceInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author LAM
 * @date 2023/9/22 18:43
 */
@Anonymous
@Api("api-服务相关接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/server")
public class ApiServerController {

    private final ApiServerService service;

    @ApiOperation("查询服务全数据")
    @GetMapping("/select")
    public R<List<ServiceInfo>> select(){
        return R.ok(service.select());
    }
}
