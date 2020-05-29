package com.ruoyi.project.data.price.service;

import com.ruoyi.project.data.price.domain.ComputeResidenceRentBasePrice;
import com.ruoyi.project.data.price.domain.UltimateOfficeBasePrice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author ruoyi
 * @date 2020-05-20
 */
public interface IComputeResidenceRentPriceService {

    /**
     * 查询【请填写功能名称】列表
     *
     * @param officeBasePriceUltimate 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    List<ComputeResidenceRentBasePrice> selectList(ComputeResidenceRentBasePrice officeBasePriceUltimate);

    /**
     * @param officeBasePriceUltimate
     * @return
     */
    int selectCount(ComputeResidenceRentBasePrice officeBasePriceUltimate);

    /**
     * @param id
     * @return
     */
    ComputeResidenceRentBasePrice selectById(String id);

    /**
     * @param officeBasePriceUltimate
     * @return
     */
    int update(ComputeResidenceRentBasePrice officeBasePriceUltimate);

    /**
     * @param officeBasePriceUltimates
     * @param operName
     * @return
     */
    String batchImport(List<ComputeResidenceRentBasePrice> officeBasePriceUltimates, String operName);

}


