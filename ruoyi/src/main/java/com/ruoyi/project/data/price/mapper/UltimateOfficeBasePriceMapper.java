package com.ruoyi.project.data.price.mapper;

import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.price.domain.UltimateOfficeBasePrice;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2020-05-20
 */
@DS("teemlink")
public interface UltimateOfficeBasePriceMapper
{

    /**
     *
     * @param id
     * @return
     */
    UltimateOfficeBasePrice selectOfficeBasePriceUltimateById(String id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param officeBasePriceUltimate 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    List<UltimateOfficeBasePrice> selectOfficeBasePriceUltimateList(UltimateOfficeBasePrice officeBasePriceUltimate);

    /**
     * 求和
     * @param officeBasePriceUltimate
     * @return
     */
    Integer selectOfficeBasePriceUltimateListCount(UltimateOfficeBasePrice officeBasePriceUltimate);

    /**
     * 更新
     * @param officeBasePriceUltimate
     * @return
     */
    int updateOfficeBasePriceUltimate(UltimateOfficeBasePrice officeBasePriceUltimate);
}
