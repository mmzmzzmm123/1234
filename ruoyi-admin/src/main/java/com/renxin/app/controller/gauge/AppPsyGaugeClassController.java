package com.renxin.app.controller.gauge;

import com.renxin.common.annotation.RateLimiter;
import com.renxin.common.core.controller.BaseController;
import com.renxin.common.core.page.TableDataInfo;
import com.renxin.gauge.domain.PsyGaugeClass;
import com.renxin.gauge.service.IPsyGaugeClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测评分类Controller
 * 
 * @author renxin
 * @date 2022-08-30
 */
@RestController
@RequestMapping("/app/home/gauge/class")
@Api(value = "AppPsyGaugeClassController" ,tags = {"测评分类api"})
public class AppPsyGaugeClassController extends BaseController
{
    @Autowired
    private IPsyGaugeClassService psyGaugeClassService;

    /**
     * 查询测评分类列表
     */
//    @PreAuthorize("@ss.hasPermi('psychology:gaugeClass:list')")
    @GetMapping("/list")
    @ApiOperation("查询测评分类列表")
    @RateLimiter
    public TableDataInfo list(PsyGaugeClass psyGaugeClass)
    {
        startPage();
        List<PsyGaugeClass> list = psyGaugeClassService.selectPsyGaugeClassList(psyGaugeClass);
        return getDataTable(list);
    }


}
