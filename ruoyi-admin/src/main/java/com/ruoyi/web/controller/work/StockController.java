package com.ruoyi.web.controller.work;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.Stock;
import com.ruoyi.system.service.IStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wjp
 * @date 2022/8/2 10:48
 * @description
 */
@Api("库存管理")
@Anonymous
@RestController
@RequestMapping("/work/stock")
public class StockController extends BaseController {


    @Autowired
    private IStockService stockService;

    @GetMapping("/{stockType}")
    @ApiOperation("获取库存列表")
    public TableDataInfo getStock(@PathVariable Integer stockType)
    {
        startPage();
        List<Stock> stocks = stockService.selectByStockType(stockType);
        return getDataTable(stocks);
    }

}
