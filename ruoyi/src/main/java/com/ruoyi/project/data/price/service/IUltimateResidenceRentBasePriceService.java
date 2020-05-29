package com.ruoyi.project.data.price.service;

import com.ruoyi.project.data.price.domain.UltimateResidenceRentBasePrice;

import java.util.List;

/**
 * @author ruoyi
 * @date 2020-05-20
 */
public interface IUltimateResidenceRentBasePriceService {

    List<UltimateResidenceRentBasePrice> selectList(UltimateResidenceRentBasePrice ultimateResidenceRentBasePrice);

    int selectCount(UltimateResidenceRentBasePrice ultimateResidenceRentBasePrice);

    UltimateResidenceRentBasePrice selectById(Integer id);

    int update(UltimateResidenceRentBasePrice ultimateResidenceRentBasePrice);

    String batchImport(List<UltimateResidenceRentBasePrice> ultimateResidenceRentBasePrices, String operName);

}


