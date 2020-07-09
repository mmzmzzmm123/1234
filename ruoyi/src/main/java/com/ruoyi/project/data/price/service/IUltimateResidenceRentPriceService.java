package com.ruoyi.project.data.price.service;

import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.price.domain.UltimateResidenceRentBasePrice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 住宅租赁最终基价
 *
 * @author lihe
 */
public interface IUltimateResidenceRentPriceService {

    /**
     * 获取分页列表
     *
     * @param ultimateResidenceRentBasePrice
     * @return
     */
    List<UltimateResidenceRentBasePrice> selectList(UltimateResidenceRentBasePrice ultimateResidenceRentBasePrice);



    /**
     * 获取分页总数
     *
     * @param ultimateResidenceRentBasePrice
     * @return
     */
    int selectCount(UltimateResidenceRentBasePrice ultimateResidenceRentBasePrice);

    /**
     * 获取表名
     *
     * @return
     */
    List<VueSelectModel> getYearMonth();


}


