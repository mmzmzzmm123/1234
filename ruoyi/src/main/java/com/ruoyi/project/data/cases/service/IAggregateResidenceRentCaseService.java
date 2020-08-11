package com.ruoyi.project.data.cases.service;

import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.cases.domain.AggregateResidenceRentCase;

import java.util.List;

/**
 * 住宅租赁汇总案例Service
 *
 * @author lihe
 */
public interface IAggregateResidenceRentCaseService {

    /**
     * 分页总数
     *
     * @param aggregateResidenceRentCase
     * @return
     */
    int pageCount(AggregateResidenceRentCase aggregateResidenceRentCase);

    /**
     * 分页列表
     *
     * @param aggregateResidenceRentCase
     * @return
     */
    List<AggregateResidenceRentCase> pageList(AggregateResidenceRentCase aggregateResidenceRentCase);

    /**
     * 表名
     *
     * @return
     */
    List<VueSelectModel> yearMonthList();

}
