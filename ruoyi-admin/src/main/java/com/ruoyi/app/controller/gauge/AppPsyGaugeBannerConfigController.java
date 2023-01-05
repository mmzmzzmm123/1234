package com.ruoyi.app.controller.gauge;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.gauge.domain.PsyGaugeBannerConfig;
import com.ruoyi.gauge.service.IPsyGaugeBannerConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 测评banner配置Controller
 * 
 * @author ruoyi
 * @date 2022-10-18
 */
@RestController
@RequestMapping("/app/home/banner/config")
@Api(value = "AppPsyGaugeBannerConfigController" ,tags = {"测评banner页配置控制器"})
public class AppPsyGaugeBannerConfigController extends BaseController
{
    @Resource
    private IPsyGaugeBannerConfigService psyGaugeBannerConfigService;

    /**
     * 查询测评banner配置列表
     */
//    @PreAuthorize("@ss.hasPermi('banner:config:list')")
    @GetMapping("/list")
    @ApiOperation("查询测评banner配置列表")
    public TableDataInfo list(PsyGaugeBannerConfig psyGaugeBannerConfig)
    {
        startPage();
        List<PsyGaugeBannerConfig> list = psyGaugeBannerConfigService.selectPsyGaugeBannerConfigList(psyGaugeBannerConfig);
        return getDataTable(list);
    }
}
