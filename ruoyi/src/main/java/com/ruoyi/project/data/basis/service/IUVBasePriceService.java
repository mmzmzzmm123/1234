package com.ruoyi.project.data.basis.service;

import com.ruoyi.project.data.basis.domain.UVBasePrice;
import com.ruoyi.project.data.basis.domain.UVBasePriceQueryModel;

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
