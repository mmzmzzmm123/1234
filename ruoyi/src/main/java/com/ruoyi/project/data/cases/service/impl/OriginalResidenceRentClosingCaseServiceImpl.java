package com.ruoyi.project.data.cases.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LoadUtil;
import com.ruoyi.project.data.cases.domain.CleanResidenceRentAggregationCase;
import com.ruoyi.project.data.cases.domain.OriginalResidenceRentClosingCase;
import com.ruoyi.project.data.cases.domain.OtherResidenceRentClosingCase;
import com.ruoyi.project.data.cases.mapper.OriginalResidenceRentClosingCaseMapper;
import com.ruoyi.project.data.cases.mapper.ResidenceRentAggregationCaseMapper;
import com.ruoyi.project.data.cases.mapper.sync.DownloadOriginalResidenceRentClosingCaseMapper;
import com.ruoyi.project.data.cases.mapper.sync.DownloadOtherResidenceRentClosingCaseMapper;
import com.ruoyi.project.data.cases.mapper.sync.SyncResidenceRentCaseMapper;
import com.ruoyi.project.data.cases.service.IOriginalResidenceRentClosingCaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@DS("compute")
public class OriginalResidenceRentClosingCaseServiceImpl implements IOriginalResidenceRentClosingCaseService {

    private static Logger logger = LoggerFactory.getLogger(OriginalResidenceRentClosingCaseServiceImpl.class);

    @Autowired
    private OriginalResidenceRentClosingCaseMapper originalResidenceRentClosingCaseMapper;
    @Autowired
    private DownloadOriginalResidenceRentClosingCaseMapper downloadOriginalResidenceRentClosingCaseMapper;
    @Autowired
    private DownloadOtherResidenceRentClosingCaseMapper downloadOtherResidenceRentClosingCaseMapper;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SyncResidenceRentCaseMapper syncResidenceRentCaseMapper;
    @Autowired
    private ResidenceRentAggregationCaseMapper residenceRentAggregationCaseMapper;

    /**
     *
     */
    @Override
    public void pullData() {
        Integer targetTableRoute = DateUtils.getLastYearMonth();
        Integer lastYearMonth = DateUtils.getYearMonth();
        Integer computeTableRoute = DateUtils.getNextYearMonth();

        prepare(computeTableRoute);
        List<OriginalResidenceRentClosingCase> list =
                downloadOriginalResidenceRentClosingCaseMapper.download(targetTableRoute);

        List<OtherResidenceRentClosingCase> otherResidenceRentClosingCaseList =
                downloadOtherResidenceRentClosingCaseMapper.download(targetTableRoute);
        running(computeTableRoute, list, otherResidenceRentClosingCaseList);
        after(computeTableRoute, lastYearMonth);
    }

    /**
     * 准备工作 创建表
     *
     * @param computeTableRoute
     */
    public void prepare(Integer computeTableRoute) {
        originalResidenceRentClosingCaseMapper.createRawTable(computeTableRoute);
        // 新增住宅租赁成交案例
        originalResidenceRentClosingCaseMapper.createOtherRawTable(computeTableRoute);
        originalResidenceRentClosingCaseMapper.createCleanTable(computeTableRoute);
        originalResidenceRentClosingCaseMapper.createAssembleTable(computeTableRoute);
        originalResidenceRentClosingCaseMapper.createComputeTable(computeTableRoute);
        originalResidenceRentClosingCaseMapper.createArtificialTable(computeTableRoute);
        originalResidenceRentClosingCaseMapper.createUltimateTable(computeTableRoute);
    }

    /**
     * 批量入库
     *
     * @param computeTableRoute
     * @param list
     */
    public void running(Integer computeTableRoute, List<OriginalResidenceRentClosingCase> list,
                        List<OtherResidenceRentClosingCase> otherResidenceRentClosingCaseList) {
        SqlParameterSource[] batchParams = SqlParameterSourceUtils.createBatch(list.toArray());
        int[] updateCounts = namedParameterJdbcTemplate.batchUpdate("insert into dbo" +
                        ".ODS_HOUSINGCASEDEAL_RENT_" + computeTableRoute + "_RAW(case_id, case_contract_no, " +
                        "case_community_name, case_opening_date, case_closing_date, case_rent_price, case_area, " +
                        "case_total_floor, case_toward, case_apartment_layout, case_decoration, clean_community_id, " +
                        "clean_building_id) " +
                        "values (:caseId,:caseContractNo,:caseCommunityName,:caseOpeningDate,:caseClosingDate," +
                        ":caseRentPrice,:caseTotalFloor,:caseArea,:caseToward,:caseApartmentLayout,:caseDecoration," +
                        ":cleanCommunityId,:cleanBuildingId);",
                batchParams);

        batchParams = SqlParameterSourceUtils.createBatch(otherResidenceRentClosingCaseList.toArray());
        updateCounts = namedParameterJdbcTemplate.batchUpdate("insert into dbo" +
                        ".TEMP_ODS_HOUSINGCASEDEAL_RENT_" + computeTableRoute + "_RAW(case_id,case_district," +
                        "case_community_name,case_address,case_area,case_closing_date,case_total_price," +
                        "case_decoration,case_total_floor,case_current_floor,case_toward,case_floor,uv_community_id," +
                        "uv_building_id) " +
                        "values (:caseId,:caseDistrict,:caseCommunityName,:caseAddress,:caseArea,:closingDate," +
                        ":caseTotalPrice,:caseDecoration,:caseTotalFloor,:caseCurrentFloor,:caseToward,:caseFloor," +
                        ":uvCommunityId,:uvBuildingId);",
                batchParams);

    }

    /**
     * 匹配数据
     *
     * @param yearMonth
     */
    public void after(Integer yearMonth, Integer lastYearMonth) {
        // 清洗成交案例
        String rawSql = LoadUtil.loadContent("sql-template/clear_rent_closing_case.sql");
        String sql = rawSql.replace("#yearMonth#", yearMonth.toString());
        jdbcTemplate.update(sql);

        // 其他住宅租赁成交案例
        rawSql = LoadUtil.loadContent("sql-template/clear_other_rent_closing_case.sql");
        sql = rawSql.replace("#yearMonth#", yearMonth.toString());
        jdbcTemplate.update(sql);

        // 计算
        rawSql = LoadUtil.loadContent("sql-template/compute_rent_price.sql");
        sql = rawSql.replace("#yearMonth#", yearMonth.toString())
                .replace("#lastYearMonth#", lastYearMonth.toString());
        jdbcTemplate.update(sql);

        pushAggregateCase(yearMonth, lastYearMonth);
    }

    @Async
    @Override
    public void pushAggregateCase(Integer yearMonth, Integer currentPriceTableRoute) {
        // 案例同步
        syncResidenceRentCaseMapper.createAggregationCaseTable(currentPriceTableRoute);
        List<CleanResidenceRentAggregationCase> list = residenceRentAggregationCaseMapper.getMonthly(yearMonth);
        list.parallelStream().forEach(cleanResidenceRentAggregationCase -> {
            cleanResidenceRentAggregationCase.setYearMonth(currentPriceTableRoute);
            syncResidenceRentCaseMapper.insertAggregationCaseTable(cleanResidenceRentAggregationCase);
        });
        logger.info("推送案例汇总数据完成");
    }
}
