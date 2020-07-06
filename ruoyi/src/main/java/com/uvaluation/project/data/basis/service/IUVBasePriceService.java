package com.uvaluation.project.data.basis.service;

import com.uvaluation.project.data.basis.domain.UVBasePrice;
import com.uvaluation.project.data.basis.domain.UVBasePriceQueryModel;

import java.util.List;

public interface IUVBasePriceService{

    /**
     * 分页查询
     *
     * @param queryModel
     * @return
     */
    List<UVBasePrice> pageList(UVBasePriceQueryModel queryModel);

    /**
     *
     * @param queryModel
     * @return
     */
    Integer pageCount(UVBasePriceQueryModel queryModel);


}
