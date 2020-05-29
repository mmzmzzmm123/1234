package com.ruoyi.project.data.price.service;

import com.ruoyi.project.data.price.domain.ArtificialResidenceRentBasePrice;

import java.util.List;

/**
 * 人工修正住宅租赁Service接口
 *
 * @author ruoyi
 * @date 2020-05-20
 */
public interface IArtificialResidenceRentPriceService {

    /**
     * 查询人工修正住宅租赁列表
     *
     * @param officeBasePriceUltimate 人工修正住宅租赁
     * @return 人工修正住宅租赁集合
     */
    List<ArtificialResidenceRentBasePrice> selectList(ArtificialResidenceRentBasePrice officeBasePriceUltimate);

    /**
     * @param officeBasePriceUltimate
     * @return
     */
    int selectCount(ArtificialResidenceRentBasePrice officeBasePriceUltimate);


}


