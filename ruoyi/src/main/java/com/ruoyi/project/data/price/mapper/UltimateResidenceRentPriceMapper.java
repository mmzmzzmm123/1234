package com.ruoyi.project.data.price.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.common.VueSelectModel;
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

    List<UltimateResidenceRentBasePrice> selectPageList(UltimateResidenceRentBasePrice ultimateResidenceRentBasePrice);

    Integer selectPageCount(UltimateResidenceRentBasePrice ultimateResidenceRentBasePrice);

    /**
     * @return
     */
    List<VueSelectModel> yearMonthList();

}
