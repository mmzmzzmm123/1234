package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.Stock;
import com.ruoyi.system.mapper.StockMapper;
import com.ruoyi.system.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wjp
 * @date 2022/8/2 10:50
 * @description
 */
@Service
public class StockServiceImpl implements IStockService {

    @Autowired
    private StockMapper stockMapper;

    @Override
    public List<Stock> selectByStockType(Integer stockType) {
        return stockMapper.selectByStockType(stockType);
    }
}
