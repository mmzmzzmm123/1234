package com.ruoyi.project.data.price.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.price.domain.ComputeResidenceRentBasePrice;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author ruoyi
 * @date 2020-05-20
 */
@DS("compute")
public interface ComputeResidenceRentPriceMapper {

    /**
     * @param id
     * @return
     */
    ComputeResidenceRentBasePrice selectComputeResidenceRentBasePriceById(String id);

    /**
     * 查询列表
     */
    List<ComputeResidenceRentBasePrice> selectComputeResidenceRentBasePriceList(ComputeResidenceRentBasePrice ComputeResidenceRentBasePrice);

    /**
     * 求和
     *
     * @param ComputeResidenceRentBasePrice
     * @return
     */
    Integer selectComputeResidenceRentBasePriceListCount(ComputeResidenceRentBasePrice ComputeResidenceRentBasePrice);

    /**
     * 更新
     *
     * @param ComputeResidenceRentBasePrice
     * @return
     */
    int updateComputeResidenceRentBasePrice(ComputeResidenceRentBasePrice ComputeResidenceRentBasePrice);
}
