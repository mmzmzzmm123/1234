package com.ruoyi.project.data.price.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.price.domain.ArtificialResidenceRentBasePrice;
import com.ruoyi.project.data.price.domain.UltimateOfficeBasePrice;
import com.ruoyi.project.data.price.domain.UltimateResidenceRentBasePrice;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author ruoyi
 * @date 2020-05-20
 */
@DS("compute")
public interface UltimateResidenceRentPriceMapper {


    List<UltimateResidenceRentBasePrice> selectListByRoute(UltimateResidenceRentBasePrice ultimateResidenceRentBasePrice);

    Integer selectCountByRoute(UltimateResidenceRentBasePrice ultimateResidenceRentBasePrice);

    /**
     * @param id
     * @return
     */
    UltimateResidenceRentBasePrice selectByRoute(Integer id);

    /**
     * @param ultimateResidenceRentBasePrice
     * @return
     */
    int updateByRoute(UltimateResidenceRentBasePrice ultimateResidenceRentBasePrice);
}
