package com.ruoyi.project.data.price.service;

import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.price.domain.UltimateResidenceRentBasePrice;

import java.util.List;

/**
 * 住宅租赁基价
 */
public interface IUltimateResidenceRentBasePriceService {

    /**
     * @param ultimateResidenceRentBasePrice
     * @return
     */
    List<UltimateResidenceRentBasePrice> selectList(UltimateResidenceRentBasePrice ultimateResidenceRentBasePrice);

    /**
     * @param ultimateResidenceRentBasePrice
     * @return
     */
    int selectCount(UltimateResidenceRentBasePrice ultimateResidenceRentBasePrice);

    /**
     * 获取表名
     *
     * @return
     */
    List<VueSelectModel> getYearMonth();


}


