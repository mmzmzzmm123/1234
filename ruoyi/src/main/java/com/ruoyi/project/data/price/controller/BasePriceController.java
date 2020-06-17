package com.ruoyi.project.data.price.controller;

import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.framework.web.page.TableSupport;
import com.ruoyi.project.data.basis.domain.UVBasePrice;
import com.ruoyi.project.data.basis.domain.UVBasePriceQueryModel;
import com.ruoyi.project.data.basis.service.IUVBasePriceService;
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
        int pageIndex = ServletUtils.getParameterToInt(TableSupport.PAGE_NUM);
        int pageSize = ServletUtils.getParameterToInt(TableSupport.PAGE_SIZE);
        queryModel.setPageIndex(pageIndex <= 1 ? 0 : (pageIndex - 1) * pageSize);
        queryModel.setPageSize(pageSize);
        int total = basePriceService.pageCount(queryModel);
        List<UVBasePrice> list = basePriceService.pageList(queryModel);
        return getDataTable(list, total);
    }
}
