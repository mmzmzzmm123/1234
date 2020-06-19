package com.ruoyi.project.data.price.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.price.domain.ComputeResidenceSaleBasePrice;

import java.util.List;

@DS("compute")
public interface ComputeResidenceSalePriceMapper {

    /**
     * 分页列表
     *
     * @param computeResidenceSaleBasePrice
     * @return
     */
    List<ComputeResidenceSaleBasePrice> selectPageList(ComputeResidenceSaleBasePrice computeResidenceSaleBasePrice);

    /**
     * 分页总数
     *
     * @param computeResidenceSaleBasePrice
     * @return
     */
    Integer selectPageCount(ComputeResidenceSaleBasePrice computeResidenceSaleBasePrice);

    /**
     * @return
     */
    List<VueSelectModel> yearMonthList();
}
