package com.ruoyi.app.controller.gauge;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.gauge.domain.PsyGauge;
import com.ruoyi.gauge.service.IPsyGaugeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 心理测评Controller
 * 
 * @author ruoyi
 * @date 2022-08-30
 */
@RestController
@RequestMapping("/app/gauge")
public class AppPsyGaugeController extends BaseController
{
    @Autowired
    private IPsyGaugeService psyGaugeService;

    /**
     * 查询心理测评列表
     */
//    @PreAuthorize("@ss.hasPermi('psychology:gauge:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsyGauge psyGauge)
    {
        startPage();
        List<PsyGauge> list = psyGaugeService.selectPsyGaugeList(psyGauge);
        return getDataTable(list);
    }

    /**
     * 获取心理测评详细信息
     */
//    @PreAuthorize("@ss.hasPermi('psychology:gauge:query')")
    @PostMapping(value = "/getInfo")
    public AjaxResult getInfo(@RequestParam("id") Long id)
    {
        return AjaxResult.success(psyGaugeService.selectPsyGaugeById(id));
    }

}
