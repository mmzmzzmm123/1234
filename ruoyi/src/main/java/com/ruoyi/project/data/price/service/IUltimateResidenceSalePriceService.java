package com.ruoyi.project.data.price.service;

import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.price.domain.ArtificialResidenceSaleBasePrice;
import com.ruoyi.project.data.price.domain.ComputeResidenceSaleBasePrice;
import com.ruoyi.project.data.price.domain.UltimateResidenceSaleBasePrice;

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


