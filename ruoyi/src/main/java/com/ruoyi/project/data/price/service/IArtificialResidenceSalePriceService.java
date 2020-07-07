package com.ruoyi.project.data.price.service;

import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.price.domain.ArtificialResidenceSaleBasePrice;
import com.ruoyi.project.data.price.domain.ComputeResidenceSaleBasePrice;

import java.util.List;

/**
 * 人工修正住宅基价
 */
public interface IArtificialResidenceSalePriceService {

    /**
     * 单个
     *
     * @param id
     * @return
     */
    ArtificialResidenceSaleBasePrice selectById(String id);

    /**
     * 分页列表
     *
     * @param artificialResidenceSaleBasePrice
     * @return 集合
     */
    List<ArtificialResidenceSaleBasePrice> selectList(ArtificialResidenceSaleBasePrice artificialResidenceSaleBasePrice);

    /**
     * 分页数量
     *
     * @param artificialResidenceSaleBasePrice
     * @return
     */
    int selectCount(ArtificialResidenceSaleBasePrice artificialResidenceSaleBasePrice);

    /**
     * 获取表名
     *
     * @return
     */
    List<VueSelectModel> getYearMonth();

    /**
     * 更新
     *
     * @param artificialResidenceSaleBasePrice
     * @return
     */
    int updateEntity(ArtificialResidenceSaleBasePrice artificialResidenceSaleBasePrice);

    /**
     * 导入
     *
     * @param yearMonth
     * @param list
     * @return
     */
    String batchImport(Integer yearMonth, List<ComputeResidenceSaleBasePrice> list);

}


