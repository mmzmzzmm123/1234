package com.ruoyi.web.controller.business;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.hglapi.HglApiClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = {"HGL业务"})
@RestController
@AllArgsConstructor
@RequestMapping("hgl")
public class HglConroller {


    @ApiOperation("登录获取token的接口")
    @PostMapping("getMerchantToken")
    public R<String> getMerchantToken() {
        return R.ok(HglApiClient.getToken());
    }
}
