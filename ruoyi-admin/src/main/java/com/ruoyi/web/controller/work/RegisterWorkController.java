package com.ruoyi.web.controller.work;

import com.ruoyi.common.core.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wjp
 * @date 2022/8/1 10:26
 * @description
 */
@Api("登记反应管理")
@RestController
@RequestMapping("/work/register")
public class RegisterWorkController extends BaseController {

    @GetMapping()
    @ApiOperation("获取用户列表")
    public String config()
    {
        return "2231";
    }
}
