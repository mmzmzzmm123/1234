package com.ruoyi.project.data.price.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.price.domain.ComputeResidenceRentBasePrice;

import java.util.List;


@DS("compute")
public interface ComputeResidenceRentPriceMapper {

    /**
     * @param id
     * @return
     */
    ComputeResidenceRentBasePrice selectById(String id);

    /**
     * 查询列表
     * @param ComputeResidenceRentBasePrice
     * @return
     */
    List<ComputeResidenceRentBasePrice> selectPageList(ComputeResidenceRentBasePrice ComputeResidenceRentBasePrice);

    /**
     * 求和
     *
     * @param ComputeResidenceRentBasePrice
     * @return
     */
    Integer selectPageCount(ComputeResidenceRentBasePrice ComputeResidenceRentBasePrice);

    /**
     * 更新
     *
     * @param ComputeResidenceRentBasePrice
     * @return
     */
    int updateComputeResidenceRentBasePrice(ComputeResidenceRentBasePrice ComputeResidenceRentBasePrice);

    /**
     * @return
     */
    List<VueSelectModel> yearMonthList();
}
