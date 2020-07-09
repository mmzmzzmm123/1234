package com.ruoyi.project.data.price.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.price.domain.UltimateResidenceRentBasePrice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 住宅租赁最终基价DAO
 *
 * @author purple
 * @date 2020-05-20
 */
@DS("compute")
public interface UltimateResidenceRentPriceMapper {

    /**
     * 获取分页列表
     *
     * @param ultimateResidenceRentBasePrice
     * @return
     */
    List<UltimateResidenceRentBasePrice> selectPageList(UltimateResidenceRentBasePrice ultimateResidenceRentBasePrice);

    /**
     * 获取分页总数
     *
     * @param ultimateResidenceRentBasePrice
     * @return
     */
    Integer selectPageCount(UltimateResidenceRentBasePrice ultimateResidenceRentBasePrice);

    /**
     * 获取有效价格表
     *
     * @return
     */
    List<VueSelectModel> yearMonthList();

    /**
     * 获取某个月的数据
     *
     * @param yearMonth
     * @return
     */
    List<UltimateResidenceRentBasePrice> getMonthly(@Param("yearMonth") Integer yearMonth);

}
