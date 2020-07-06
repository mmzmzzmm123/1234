package com.uvaluation.project.data.price.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.uvaluation.project.common.VueSelectModel;
import com.uvaluation.project.data.price.domain.DistrictBlockChange;
import com.uvaluation.project.data.price.domain.ComputeResidenceSaleBasePrice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@DS("compute")
public interface ComputeResidenceSalePriceMapper {

    /**
     * 板块涨跌幅
     *
     * @param yearMonth
     * @return
     */
    List<DistrictBlockChange> getBlockChange(@Param("yearMonth") Integer yearMonth);

    /**
     * 区域涨跌幅
     *
     * @param yearMonth
     * @return
     */
    List<DistrictBlockChange> getCountyChange(@Param("yearMonth") Integer yearMonth);

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
