package com.uvaluation.project.data.basis.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.uvaluation.project.data.basis.domain.UVBasePrice;
import com.uvaluation.project.data.basis.domain.UVBasePriceQueryModel;

import java.util.List;

@DS("teemlink")
public interface UVBasePriceMapper {
    /**
     * 分页总数
     *
     * @param uvBasePrice
     * @return
     */
    Integer pageCount(UVBasePrice uvBasePrice);

    /**
     * @param queryModel
     * @return
     */
    List<UVBasePrice> pageList(UVBasePriceQueryModel queryModel);
}
