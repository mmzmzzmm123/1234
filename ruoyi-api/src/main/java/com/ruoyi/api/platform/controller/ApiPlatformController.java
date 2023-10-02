package com.ruoyi.api.platform.controller;

import com.ruoyi.api.platform.model.vo.ApiGiftVo;
import com.ruoyi.api.platform.service.ApiPlatformService;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author LAM
 * @date 2023/9/24 21:49
 */
@Anonymous
@Api("api-平台服务相关接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/platform")
public class ApiPlatformController {

    private final ApiPlatformService service;

    @ApiOperation("根据类型获取平台文本内容")
    @GetMapping("/selectTextContentByType")
    public R<String> selectTextContentByType(@RequestParam("type") String type){
        return R.ok(service.selectTextContentByType(type));
    }

    @ApiOperation("查询礼物列表")
    @GetMapping("/selectGiftList")
    public R<List<ApiGiftVo>> selectGiftList(){
        return R.ok(service.selectGiftList());
    }
}
