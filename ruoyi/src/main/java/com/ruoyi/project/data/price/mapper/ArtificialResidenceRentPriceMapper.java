package com.ruoyi.project.data.price.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.price.domain.ArtificialResidenceRentBasePrice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper接口
 *
 * @author ruoyi
 * @date 2020-05-20
 */
@DS("compute")
public interface ArtificialResidenceRentPriceMapper {

    /**
     * 单条记录
     *
     * @param id
     * @return
     */
    ArtificialResidenceRentBasePrice selectById(@Param("yearMonth") Integer yearMonth, @Param("id") String id);

    List<ArtificialResidenceRentBasePrice> selectPageList(ArtificialResidenceRentBasePrice artificialResidenceRentBasePrice);

    Integer selectPageCount(ArtificialResidenceRentBasePrice artificialResidenceRentBasePrice);

    /**
     * @return
     */
    List<VueSelectModel> yearMonthList();

    /**
     * 从作价数据拷贝数据到人工修正
     * 先删除原数据，然后再插入。
     *
     * @param yearMonth
     * @return
     */
    int importBySync(@Param("yearMonth") Integer yearMonth);

    /**
     * 清空数据
     *
     * @param yearMonth
     * @return
     */
    int clearData(@Param("yearMonth") Integer yearMonth);

    /**
     * 更新
     *
     * @param artificialResidenceRentBasePrice
     * @return
     */
    int update(ArtificialResidenceRentBasePrice artificialResidenceRentBasePrice);

    /**
     * 创建用于批量导入的存储过程
     *
     * @param yearMonth
     * @return
     */
    int prepareBachImport(@Param("yearMonth") Integer yearMonth);

    /**
     * 初始化存储过程
     * @return
     */
    int initProcedure();
}