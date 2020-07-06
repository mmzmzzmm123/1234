package com.uvaluation.project.data.price.controller;

import com.uvaluation.common.utils.poi.ExcelUtil;
import com.uvaluation.framework.aspectj.lang.annotation.Log;
import com.uvaluation.framework.aspectj.lang.enums.BusinessType;
import com.uvaluation.framework.web.controller.BaseController;
import com.uvaluation.framework.web.domain.AjaxResult;
import com.uvaluation.framework.web.page.TableDataInfo;
import com.uvaluation.project.data.basis.domain.UVBasePrice;
import com.uvaluation.project.data.basis.domain.UVBasePriceQueryModel;
import com.uvaluation.project.data.basis.service.IUVBasePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/data/base/price")
public class BasePriceController extends BaseController {

    @Autowired
    private IUVBasePriceService basePriceService;

    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(UVBasePriceQueryModel queryModel) {
        int pageIndex = queryModel.getPageIndex();
        int pageSize = queryModel.getPageSize();
        queryModel.setPageIndex(pageIndex <= 1 ? 0 : (pageIndex - 1) * pageSize);
        queryModel.setPageSize(pageSize);
        int total = basePriceService.pageCount(queryModel);
        List<UVBasePrice> list = basePriceService.pageList(queryModel);
        return getDataTable(list, total);
    }


    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @Log(title = "物业基价", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(UVBasePriceQueryModel queryModel) {
        int total = basePriceService.pageCount(queryModel);
        queryModel.setPageIndex(0);
        queryModel.setPageSize(total);

        List<UVBasePrice> list = basePriceService.pageList(queryModel);
        ExcelUtil<UVBasePrice> util = new ExcelUtil<>(UVBasePrice.class);
        return util.exportExcel(list, "物业基价");
    }
}
