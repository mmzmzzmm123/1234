package com.ruoyi.project.data.price.service;

import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.price.domain.UltimateOfficeBasePrice;

import java.util.List;

/**
 * 办公基价Service接口
 *
 * @author ruoyi
 * @date 2020-05-20
 */
public interface IUltimateOfficeBasePriceService {

    /**
     * 查询办公基价列表
     *
     * @param officeBasePriceUltimate 办公基价
     * @return 办公基价集合
     */
    List<UltimateOfficeBasePrice> selectOfficeBasePriceUltimateList(UltimateOfficeBasePrice officeBasePriceUltimate);

    /**
     *
     * @param officeBasePriceUltimate
     * @return
     */
    int selectOfficeBasePriceUltimateListCount(UltimateOfficeBasePrice officeBasePriceUltimate);

    /**
     * @param id
     * @return
     */
    UltimateOfficeBasePrice getById(Integer yearMonth, String id);

    /**
     * 可能修改当期的价格、也可能修改往期
     *
     * @param officeBasePriceUltimate
     * @return
     */
    int update(UltimateOfficeBasePrice officeBasePriceUltimate);

    /**
     * @param officeBasePriceUltimates
     * @param operName
     * @return
     */
    String batchImport(Integer yearMonth, List<UltimateOfficeBasePrice> officeBasePriceUltimates, String operName);

    /**
     * @return
     */
    List<VueSelectModel> getYearMonthList();

}


