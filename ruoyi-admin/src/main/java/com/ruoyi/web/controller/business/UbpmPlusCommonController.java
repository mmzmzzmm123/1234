package com.ruoyi.web.controller.business;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.extend.ApiInterfaceVO;
import com.ruoyi.system.extend.UtTouchApiEnum;
import com.ruoyi.system.extend.UtTouchClient;
import com.ruoyi.system.extend.UtTouchResult;
import com.ruoyi.system.extend.data.CommonCallVO;
import com.ruoyi.system.extend.data.UtTouchCountryData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jing.Zhang
 */
@Api(tags = {"基础接口"})
@RestController
@AllArgsConstructor
@RequestMapping("common")
public class UbpmPlusCommonController {



    @Anonymous
    @GetMapping("getAllCountries")
    @ApiOperation("获取UT-TOUCH所有国家")
    public R<List<UtTouchCountryData>> getAllCountries() {
        UtTouchResult<List<UtTouchCountryData>> result = UtTouchClient.getAllCountries();
        return R.ok(result.getDataOrThrow());
    }

    @GetMapping("getAllInterfaces")
    @ApiOperation("获取所有接口")
    public R<List<ApiInterfaceVO>> getAllInterfaces() {
         return R.ok(Arrays.stream(UtTouchApiEnum.values()).map(ApiInterfaceVO::new).collect(Collectors.toList()));
    }


    @PostMapping("commonCall")
    @ApiOperation("通用接口调用")
    public R commonCall(@RequestBody CommonCallVO input) {
        UtTouchResult<JSONObject> result = UtTouchClient.post(input.getApi(), input.getRequestParams(), JSONObject.class);
        JSONObject resultData = result.getDataOrThrow();
        return R.ok(resultData);
    }

}
