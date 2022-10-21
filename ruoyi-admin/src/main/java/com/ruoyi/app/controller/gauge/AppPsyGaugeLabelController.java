package com.ruoyi.app.controller.gauge;

import com.ruoyi.gauge.dto.GaugeLabelQueryDTO;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.gauge.domain.PsyGauge;
import com.ruoyi.gauge.service.IPsyGaugeLabelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @User hogan
 * @Time 2022/10/17 16:50
 * @e-mail hkcugwh@163.com
 **/
@RestController
@RequestMapping("/app/home/gauge/label")
@Api(value = "AppPsyGaugeLabelController" ,tags = {"测评标签api"})
public class AppPsyGaugeLabelController extends BaseController {

    @Autowired
    private IPsyGaugeLabelService psyGaugeLabelService;

    @GetMapping("/list")
    @ApiOperation("查询测评标签列表")
    public TableDataInfo list(GaugeLabelQueryDTO gaugeLabelQueryDTO)
    {
        startPage();
        List<PsyGauge> list = psyGaugeLabelService.queryGaugeByLabel(gaugeLabelQueryDTO.getLabelCode());
        return getDataTable(list);
    }

}
