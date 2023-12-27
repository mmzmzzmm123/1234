package com.ruoyi.web.controller.business;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.extend.UtTouchClient;
import com.ruoyi.system.extend.UtTouchResult;
import com.ruoyi.system.extend.data.UtTouchCountryData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jing.Zhang
 */
@Api(tags = {"基础接口"})
@RestController
@AllArgsConstructor
@RequestMapping("common")
public class UbpmPlusCommonController {


    @GetMapping("getAllCountries")
    @ApiOperation("获取UT-TOUCH所有国家")
    public R<List<UtTouchCountryData>> getAllCountries() {
        UtTouchResult<List<UtTouchCountryData>> result = UtTouchClient.getAllCountries();
        return R.ok(result.getDataOrThrow());
    }

}
