package com.ruoyi.api.common.controller;

import com.ruoyi.api.common.service.ApiDictService;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LAM
 * @date 2023/10/13 12:51
 */
@Anonymous
@Api("api-字典相关接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dict")
public class ApiDictController {

    private final ApiDictService service;

    @ApiOperation("是否隐藏隐藏项")
    @GetMapping("/getIfHidePrivacyItem")
    public R<String> getIfHidePrivacyItem(){
        return R.ok(service.getIfHidePrivacyItem());
    }
}
