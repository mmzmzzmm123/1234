package com.ruoyi.project.data.price.service;

import com.ruoyi.project.data.price.domain.UltimateOfficeBasePrice;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author ruoyi
 * @date 2020-05-20
 */
public interface IUltimateOfficeBasePriceService {

    /**
     * 查询【请填写功能名称】列表
     *
     * @param officeBasePriceUltimate 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    List<UltimateOfficeBasePrice> selectOfficeBasePriceUltimateList(UltimateOfficeBasePrice officeBasePriceUltimate);

    int selectOfficeBasePriceUltimateListCount(UltimateOfficeBasePrice officeBasePriceUltimate);


    UltimateOfficeBasePrice selectOfficeBasePriceUltimateById(String id);

    int updateOfficeBasePriceUltimate(UltimateOfficeBasePrice officeBasePriceUltimate);

    /**
     *
     * @param officeBasePriceUltimates
     * @param operName
     * @return
     */
    String batchImport(List<UltimateOfficeBasePrice> officeBasePriceUltimates,String operName);

}


