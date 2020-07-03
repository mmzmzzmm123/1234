package com.ruoyi.project.data.price.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.price.domain.OfficeBasePriceModifyModel;
import com.ruoyi.project.data.price.domain.UltimateOfficeBasePrice;
import org.apache.ibatis.annotations.Param;

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
    UltimateOfficeBasePrice getById(Integer yearMonth, Integer lastYearMonth, String id);

    /**
     * @param yearMonth
     * @param id
     * @return
     */
    UltimateOfficeBasePrice getByRouteId(Integer yearMonth, String id);

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
    int updateBasePriceStatus(OfficeBasePriceModifyModel officeBasePriceModifyModel);

    /**
     * 修改状态
     *
     * @param officeBasePriceModifyModel
     * @return
     */
    int updateStatus(OfficeBasePriceModifyModel officeBasePriceModifyModel);

    /**
     * 新增状态
     *
     * @param officeBasePriceModifyModel
     * @return
     */
    int insertNewRecord(OfficeBasePriceModifyModel officeBasePriceModifyModel);

    /**
     * 更新当前基价
     *
     * @param officeBasePriceModifyModel
     * @return
     */
    int updateBasePriceCopyNew(OfficeBasePriceModifyModel officeBasePriceModifyModel);

    /**
     * 年月
     *
     * @return
     */
    List<VueSelectModel> getYearMonthList();

    /**
     * 准备批量执行环境
     */
    void initImport();

    /**
     * @param yearMonth
     * @return
     */
    int prepareBachImport(@Param("yearMonth") Integer yearMonth);

    /**
     * 插入人工修正的办公基价
     *
     * @param ultimateOfficeBasePrice
     * @return
     */
    int insertArtificialOfficeBasePrice(UltimateOfficeBasePrice ultimateOfficeBasePrice);


    /**
     * 批量插入人工修正办公基价
     *
     * @param ultimateOfficeBasePrices
     * @return
     */
    int batchInsertArtificialOfficeBase(@Param("yearMonth") Integer yearMonth,
                                        @Param("list") List<UltimateOfficeBasePrice> ultimateOfficeBasePrices);
}
