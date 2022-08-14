package com.ruoyi.system.service;

import com.ruoyi.system.domain.Stock;

import java.util.List;

/**
 * @author wjp
 * @date 2022/8/2 10:50
 * @description
 */
public interface IStockService {

    List<Stock> selectByStockType(Integer stockType);

}
