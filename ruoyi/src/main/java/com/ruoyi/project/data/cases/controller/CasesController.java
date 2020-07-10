package com.ruoyi.project.data.cases.controller;

import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.framework.web.page.TableSupport;
import com.ruoyi.project.data.cases.domain.OriginalNewHouseCase;
import com.ruoyi.project.data.cases.service.IOriginalNewHouseCaseService;
import com.ruoyi.project.data.price.domain.UltimateOfficeBasePrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 案例Controller
 *
 * @author lihe
 */
@RestController
@RequestMapping("/data/cases")
public class CasesController extends BaseController {

    @Autowired
    private IOriginalNewHouseCaseService originalNewHouseCaseService;

    /**
     * 一手房成交案例
     */
    @PreAuthorize("@ss.hasPermi('cases:newHouse:list')")
    @GetMapping("/newHouse/list")
    public TableDataInfo list(OriginalNewHouseCase originalNewHouseCase) {
        int total = originalNewHouseCaseService.selectCount(originalNewHouseCase);
        List<OriginalNewHouseCase> list =
                originalNewHouseCaseService.selectList(originalNewHouseCase);
        return getDataTable(list, total);
    }
}
