package com.uvaluation.project.data.price.service;

import com.uvaluation.project.common.VueSelectModel;
import com.uvaluation.project.data.price.domain.UltimateResidenceSaleBasePrice;

import java.util.List;

/**
 * 住宅销售最终基价
 */
public interface IUltimateResidenceSalePriceService {

    /**
     * 分页列表
     *
     * @param ultimateResidenceSaleBasePrice
     * @return 集合
     */
    List<UltimateResidenceSaleBasePrice> selectList(UltimateResidenceSaleBasePrice ultimateResidenceSaleBasePrice);

    /**
     * 分页数量
     *
     * @param ultimateResidenceSaleBasePrice
     * @return
     */
    int selectCount(UltimateResidenceSaleBasePrice ultimateResidenceSaleBasePrice);

    /**
     * 获取表名
     *
     * @return
     */
    List<VueSelectModel> getYearMonth();

}


