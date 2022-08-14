package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Stock;

import java.util.List;

/**
 * @author wjp
 * @date 2022/8/2 11:03
 * @description
 */
public interface StockMapper {

    List<Stock> selectByStockType(Integer stockType);
}
