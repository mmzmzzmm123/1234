package com.ruoyi.project.data.cases.controller;

import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.framework.web.page.TableSupport;
import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.cases.domain.AggregateResidenceRentCase;
import com.ruoyi.project.data.cases.domain.OriginalNewHouseCase;
import com.ruoyi.project.data.cases.service.IAggregateResidenceRentCaseService;
import com.ruoyi.project.data.cases.service.IOriginalNewHouseCaseService;
import com.ruoyi.project.data.price.domain.ComputeResidenceRentBasePrice;
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
    @Autowired
    private IAggregateResidenceRentCaseService aggregateResidenceRentCaseService;

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

    /**
     * 住宅租赁案例汇总
     *
     * @param queryModel
     * @return
     */
    @PreAuthorize("@ss.hasPermi('cases:aggregateResidenceRentCase:list')")
    @GetMapping("/residence/aggregate-rent-case/list")
    public TableDataInfo aggregateResidenceRentCaseList(AggregateResidenceRentCase queryModel) {
        int pageIndex = ServletUtils.getParameterToInt("pageIndex");
        int pageSize = ServletUtils.getParameterToInt(TableSupport.PAGE_SIZE);
        queryModel.setOffset(pageIndex <= 1 ? 0 : (pageIndex - 1) * pageSize);
        queryModel.setLimit(pageSize);

        int total = aggregateResidenceRentCaseService.pageCount(queryModel);
        List<AggregateResidenceRentCase> list =
                aggregateResidenceRentCaseService.pageList(queryModel);
        return getDataTable(list, total);
    }

    @PreAuthorize("@ss.hasPermi('cases:aggregateResidenceRentCase:list')")
    @GetMapping("/residence/aggregate-rent-case/yearmonth")
    public AjaxResult yearMonthList() {
        List<VueSelectModel> list = aggregateResidenceRentCaseService.yearMonthList();
        return AjaxResult.success(list);
    }

    /**
     * 导出 住宅租赁基价列表
     */
    @PreAuthorize("@ss.hasPermi('cases:aggregateResidenceRentCase:export')")
    @Log(title = "住宅租赁汇总案例", businessType = BusinessType.EXPORT)
    @GetMapping("/residence/aggregate-rent-case/export")
    public AjaxResult export(AggregateResidenceRentCase queryModel) {
        int total = aggregateResidenceRentCaseService.pageCount(queryModel);
        queryModel.setOffset(0);
        queryModel.setLimit(total);
        List<AggregateResidenceRentCase> list =
                aggregateResidenceRentCaseService.pageList(queryModel);
        ExcelUtil<AggregateResidenceRentCase> util = new ExcelUtil<>(AggregateResidenceRentCase.class);
        return util.exportExcel(list, "住宅租赁汇总案例" + queryModel.getYearMonth());
    }
}
