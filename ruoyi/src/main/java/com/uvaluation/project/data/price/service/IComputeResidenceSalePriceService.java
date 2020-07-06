package com.uvaluation.project.data.price.service;

import com.uvaluation.project.common.VueSelectModel;
import com.uvaluation.project.data.price.domain.DistrictBlockChange;
import com.uvaluation.project.data.price.domain.ComputeResidenceSaleBasePrice;

import java.util.List;

/**
 * 计算住宅基价
 */
public interface IComputeResidenceSalePriceService {

    /**
     * 板块涨跌幅
     *
     * @param yearMonth
     * @return
     */
    List<DistrictBlockChange> getBlockChange(Integer yearMonth);

    /**
     * 区域涨跌幅
     * @param yearMonth
     * @return
     */
    List<DistrictBlockChange> getCountyChange(Integer yearMonth);

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


