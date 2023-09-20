package com.ruoyi.api.user.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.api.user.model.vo.ApiUserLevelConfigVo;
import com.ruoyi.api.user.service.ApiUserLevelService;
import com.ruoyi.common.annotation.Anonymous;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author LAM
 * @date 2023/9/20 14:26
 */
@Anonymous
@Api("api-用户等级相关接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/userLevel")
public class ApiUserLevelController {

    private final ApiUserLevelService service;

    @ApiOperation("获取用户等级配置数据")
    @GetMapping("/selectUserLevelConfig")
    public R<List<ApiUserLevelConfigVo>> selectUserLevelConfig(){
        return R.ok(service.selectUserLevelConfig());
    }
}
