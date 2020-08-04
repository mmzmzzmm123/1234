package com.ruoyi.project.data.cases.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.cases.domain.OriginalResidenceSaleClosingCase;
import com.ruoyi.project.data.cases.domain.OriginalResidenceSaleOpeningCase;
import com.ruoyi.project.data.cases.domain.OriginalResidenceSaleOpeningCaseQueryModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 住宅住宅销售成交案例DAO
 *
 * @author lihe
 * @date 2020年7月10日
 */
@DS("compute")
public interface OriginalResidenceSaleClosingCaseMapper {

    /**
     * 创建住宅销售成交案例表
     *
     * @param yearMonth
     * @return
     */
    int createRawTable(@Param("yearMonth") Integer yearMonth);



    /**
     * 插入住宅销售成交案例
     *
     * @param originalResidenceSaleClosingCase
     * @return
     */
    int insertRawTable(OriginalResidenceSaleClosingCase originalResidenceSaleClosingCase);

    /**
     * 创建清洗完成之后的表ODS_HOUSINGCASELISTED_LJ_YYYYMM
     *
     * @param yearMonth
     * @return
     */
    int createCleanTable(@Param("yearMonth") Integer yearMonth);

    /**
     * 分页总数
     *
     * @param originalResidenceSaleOpeningCase
     * @return
     */
    Integer pageCount(OriginalResidenceSaleOpeningCase originalResidenceSaleOpeningCase);

    /**
     * @param queryModel
     * @return
     */
    List<OriginalResidenceSaleOpeningCase> pageList(OriginalResidenceSaleOpeningCaseQueryModel queryModel);


}
