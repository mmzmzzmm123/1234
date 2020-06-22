package com.ruoyi.project.data.price.service;

import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.price.domain.ComputeResidenceRentBasePrice;
import com.ruoyi.project.data.price.domain.UltimateOfficeBasePrice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Service接口
 *
 * @author ruoyi
 * @date 2020-05-20
 */
public interface IComputeResidenceRentPriceService {

    /**
     * 查询列表
     *
     * @param officeBasePriceUltimate 
     * @return 集合
     */
    List<ComputeResidenceRentBasePrice> selectPageList(ComputeResidenceRentBasePrice officeBasePriceUltimate);

    /**
     * @param officeBasePriceUltimate
     * @return
     */
    int selectPageCount(ComputeResidenceRentBasePrice officeBasePriceUltimate);

    /**
     * @param id
     * @return
     */
    ComputeResidenceRentBasePrice selectById(String id);

    /**
     * 获取表名
     *
     * @return
     */
    List<VueSelectModel> getYearMonth();
}


