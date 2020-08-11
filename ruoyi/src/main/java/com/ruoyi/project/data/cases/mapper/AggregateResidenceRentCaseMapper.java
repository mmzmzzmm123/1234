package com.ruoyi.project.data.cases.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.cases.domain.AggregateResidenceRentCase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 汇总案例查询
 *
 * @author lihe
 */
@DS("compute")
public interface AggregateResidenceRentCaseMapper {

    /**
     * 获取每月汇总案例列表
     *
     * @param yearMonth
     * @return
     */
    List<AggregateResidenceRentCase> getMonthly(@Param("yearMonth") Integer yearMonth);

    /**
     * 分页总数
     *
     * @param aggregateResidenceRentCase
     * @return
     */
    int selectPageCount(AggregateResidenceRentCase aggregateResidenceRentCase);

    /**
     * 分页列表
     *
     * @param aggregateResidenceRentCase
     * @return
     */
    List<AggregateResidenceRentCase> selectPageList(AggregateResidenceRentCase aggregateResidenceRentCase);

    /**
     * 表名
     *
     * @return
     */
    List<VueSelectModel> yearMonthList();
}
