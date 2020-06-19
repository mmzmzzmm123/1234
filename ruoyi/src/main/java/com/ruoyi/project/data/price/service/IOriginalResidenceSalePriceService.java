package com.ruoyi.project.data.price.service;

import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.price.domain.ComputeResidenceSaleBasePrice;

import java.util.List;

/**
 * 计算住宅基价
 */
public interface IOriginalResidenceSalePriceService {

    /**
     * 查询列表
     *
     * @param computeResidenceSaleBasePrice
     * @return 集合
     */
    List<ComputeResidenceSaleBasePrice> selectList(ComputeResidenceSaleBasePrice computeResidenceSaleBasePrice);

    /**
     * @param computeResidenceSaleBasePrice
     * @return
     */
    int selectCount(ComputeResidenceSaleBasePrice computeResidenceSaleBasePrice);

    /**
     * 获取表名
     *
     * @return
     */
    List<VueSelectModel> getYearMonth();




}


