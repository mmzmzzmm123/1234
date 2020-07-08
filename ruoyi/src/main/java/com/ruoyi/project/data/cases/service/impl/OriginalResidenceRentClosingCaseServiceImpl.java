package com.ruoyi.project.data.cases.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.common.utils.LoadUtil;
import com.ruoyi.project.data.cases.domain.OriginalResidenceRentClosingCase;
import com.ruoyi.project.data.cases.mapper.OriginalResidenceRentClosingCaseMapper;
import com.ruoyi.project.data.cases.mapper.sync.DownloadOriginalResidenceRentClosingCaseMapper;
import com.ruoyi.project.data.cases.service.IOriginalResidenceRentClosingCaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
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
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     *
     */
    @Scheduled(cron = "0 0 15 29 * ?")
    @Override
    public void pullData() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Integer targetTableRoute = new Integer(String.format("%d%02d", calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH)));
        Integer lastYearMonth = new Integer(String.format("%d%02d", calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1));
        calendar.add(Calendar.MONTH, 1);
        Integer computeTableRoute = new Integer(String.format("%d%02d", calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1));

//        targetTableRoute = 202005;
//        computeTableRoute = 202007;

        prepare(computeTableRoute);
        List<OriginalResidenceRentClosingCase> list =
                downloadOriginalResidenceRentClosingCaseMapper.download(targetTableRoute);
        running(computeTableRoute, list);
        after(computeTableRoute, lastYearMonth);
    }

    /**
     * 准备工作 创建表
     *
     * @param computeTableRoute
     */
    public void prepare(Integer computeTableRoute) {
        originalResidenceRentClosingCaseMapper.createRawTable(computeTableRoute);
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
    public void running(Integer computeTableRoute, List<OriginalResidenceRentClosingCase> list) {
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
        // 计算
        rawSql = LoadUtil.loadContent("sql-template/compute_rent_price.sql");
        sql = rawSql.replace("#yearMonth#", yearMonth.toString())
                .replace("#lastYearMonth#", lastYearMonth.toString());
        jdbcTemplate.update(sql);
    }
}
