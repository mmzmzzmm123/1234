package com.ruoyi.project.data.price.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.price.domain.ArtificialResidenceRentBasePrice;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author ruoyi
 * @date 2020-05-20
 */
@DS("compute")
public interface ArtificialResidenceRentPriceMapper {

    List<ArtificialResidenceRentBasePrice> selectArtificialResidenceRentBasePriceList(ArtificialResidenceRentBasePrice artificialResidenceRentBasePrice);

    Integer selectArtificialResidenceRentBasePriceListCount(ArtificialResidenceRentBasePrice artificialResidenceRentBasePrice);
}
