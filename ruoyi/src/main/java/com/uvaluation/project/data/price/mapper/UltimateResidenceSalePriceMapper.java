package com.uvaluation.project.data.price.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.uvaluation.project.common.VueSelectModel;
import com.uvaluation.project.data.price.domain.UltimateResidenceSaleBasePrice;

import java.util.List;

@DS("compute")
public interface UltimateResidenceSalePriceMapper {

    /**
     * 分页列表
     *
     * @param ultimateResidenceSaleBasePrice
     * @return
     */
    List<UltimateResidenceSaleBasePrice> selectPageList(UltimateResidenceSaleBasePrice ultimateResidenceSaleBasePrice);

    /**
     * 分页总数
     *
     * @param ultimateResidenceSaleBasePrice
     * @return
     */
    Integer selectPageCount(UltimateResidenceSaleBasePrice ultimateResidenceSaleBasePrice);

    /**
     * @return
     */
    List<VueSelectModel> yearMonthList();
}
