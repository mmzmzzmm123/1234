package com.ruoyi.app.controller.gauge;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.gauge.domain.PsyGaugeClass;
import com.ruoyi.gauge.service.IPsyGaugeClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 测评分类Controller
 * 
 * @author ruoyi
 * @date 2022-08-30
 */
@RestController
@RequestMapping("/app/gauge/class")
public class AppPsyGaugeClassController extends BaseController
{
    @Autowired
    private IPsyGaugeClassService psyGaugeClassService;

    /**
     * 查询测评分类列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:gaugeClass:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsyGaugeClass psyGaugeClass)
    {
        startPage();
        List<PsyGaugeClass> list = psyGaugeClassService.selectPsyGaugeClassList(psyGaugeClass);
        return getDataTable(list);
    }


}
