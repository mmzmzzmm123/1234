package com.ruoyi.api.wechat.controller;

import com.ruoyi.api.wechat.model.dto.ApiWechatAuthGetDto;
import com.ruoyi.api.wechat.model.dto.ApiWechatPostDto;
import com.ruoyi.api.wechat.service.ApiWechatService;
import com.ruoyi.common.annotation.Anonymous;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author LAM
 * @date 2023/10/15 19:32
 */
@Anonymous
@Api("api-微信服务相关接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wechat")
public class ApiWechatController {

    private final ApiWechatService service;


    @ApiOperation("微信GET验证")
    @GetMapping(produces = "text/plain;charset=utf-8")
    public String authGet(@Valid ApiWechatAuthGetDto dto) {
        return service.authGet(dto);
    }

    @PostMapping(produces = "application/xml; charset=UTF-8")
    @ApiOperation(value = "微信POST请求", notes = "公众号接入开发模式后才有效")
    public String post(@Valid ApiWechatPostDto dto, @RequestBody String requestBody) {
        return service.post(dto, requestBody);
    }
}
