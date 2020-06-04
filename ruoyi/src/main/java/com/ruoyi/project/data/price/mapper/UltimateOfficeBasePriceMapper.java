package com.ruoyi.project.data.price.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.price.domain.OfficeBasePriceModifyModel;
import com.ruoyi.project.data.price.domain.UltimateOfficeBasePrice;

/**
 * 办公基价Mapper接口
 *
 * @author ruoyi
 * @date 2020-05-20
 */
@DS("compute")
public interface UltimateOfficeBasePriceMapper {

    /**
     * @param yearMonth
     * @param id
     * @return
     */
    UltimateOfficeBasePrice getById(Integer yearMonth, Integer lastYearMonth, Integer id);

    /**
     * @param yearMonth
     * @param id
     * @return
     */
    UltimateOfficeBasePrice getByRouteId(Integer yearMonth, Integer id);

    /**
     * @param yearMonth
     * @param buildingId
     * @return
     */
    UltimateOfficeBasePrice getByBuildingId(Integer yearMonth, String buildingId);

    /**
     * 查询办公基价列表
     *
     * @param officeBasePriceUltimate 办公基价
     * @return 办公基价集合
     */
    List<UltimateOfficeBasePrice> getList(UltimateOfficeBasePrice officeBasePriceUltimate);

    /**
     * 求和
     *
     * @param officeBasePriceUltimate
     * @return
     */
    Integer getCount(UltimateOfficeBasePrice officeBasePriceUltimate);

    /**
     * 更新当期基价
     *
     * @param officeBasePriceModifyModel
     * @return
     */
    int updateBasePrice(OfficeBasePriceModifyModel officeBasePriceModifyModel);

    /**
     * 年月
     *
     * @return
     */
    List<VueSelectModel> getYearMonthList();

}
