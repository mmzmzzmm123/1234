package com.ruoyi.project.data.price.service;

import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.price.domain.UltimateOfficeBasePrice;

import java.util.List;

/**
 * 办公基价Service
 *
 * @author purple
 */
public interface IUltimateOfficeBasePriceService {

    /**
     * 查询分页每页数据
     *
     * @param officeBasePriceUltimate 办公基价
     * @return 办公基价集合
     */
    List<UltimateOfficeBasePrice> selectOfficeBasePriceUltimateList(UltimateOfficeBasePrice officeBasePriceUltimate);

    /**
     * 查询分页共条数
     *
     * @param officeBasePriceUltimate
     * @return
     */
    int selectOfficeBasePriceUltimateListCount(UltimateOfficeBasePrice officeBasePriceUltimate);

    /**
     * 获取单个实例
     *
     * @param yearMonth
     * @param id
     * @return
     */
    UltimateOfficeBasePrice getById(Integer yearMonth, String id);

//    /**
//     * 可能修改当期的价格、也可能修改往期
//     *
//     * @param officeBasePriceUltimate
//     * @return
//     */
//    int update(UltimateOfficeBasePrice officeBasePriceUltimate);

    /**
     * @param officeBasePriceUltimates
     * @param operName
     * @return
     */
    String batchImport(Integer yearMonth, List<UltimateOfficeBasePrice> officeBasePriceUltimates, String operName);

    /**
     * 获取办公表
     *
     * @return
     */
    List<VueSelectModel> getYearMonthList();

}


