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

    int selectOfficeBasePriceUltimateListCount(UltimateOfficeBasePrice officeBasePriceUltimate);

    /**
     *
     * @param id
     * @return
     */
    UltimateOfficeBasePrice getById(Integer yearMonth, Integer id);

//    /**
//     *
//     * @param officeBasePriceUltimate
//     * @return
//     */
//    int updateOfficeBasePriceUltimate(UltimateOfficeBasePrice officeBasePriceUltimate);

    /**
     *
     * @param officeBasePriceUltimates
     * @param operName
     * @return
     */
    String batchImport(Integer yearMonth, List<UltimateOfficeBasePrice> officeBasePriceUltimates,String operName);

    /**
     *
     * @return
     */
    List<VueSelectModel> getYearMonthList();

}


