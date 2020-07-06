package com.uvaluation.project.data.cases.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.uvaluation.common.utils.LoadUtil;
import com.uvaluation.project.data.cases.domain.OriginalResidenceSaleOpeningCase;
import com.uvaluation.project.data.cases.mapper.OriginalResidenceSaleOpeningCaseMapper;
import com.uvaluation.project.data.cases.mapper.sync.DownloadOriginalResidenceSaleOpeningCaseMapper;
import com.uvaluation.project.data.cases.mapper.sync.SyncOriginalResidenceSaleOpeningCaseMapper;
import com.uvaluation.project.data.cases.service.IOriginalResidenceSaleOpeningCaseService;
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
public class OriginalResidenceSaleOpeningCaseServiceImpl implements IOriginalResidenceSaleOpeningCaseService {

    private static Logger logger = LoggerFactory.getLogger(OriginalResidenceSaleOpeningCaseServiceImpl.class);

    @Autowired
    private OriginalResidenceSaleOpeningCaseMapper originalResidenceSaleOpeningCaseMapper;
    @Autowired
    private DownloadOriginalResidenceSaleOpeningCaseMapper downloadOriginalResidenceSaleOpeningCaseMapper;
    @Autowired
    private SyncOriginalResidenceSaleOpeningCaseMapper syncOriginalResidenceSaleOpeningCaseMapper;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     *
     */
    @Scheduled(cron = "0 0 5 25 * ?")
    @Override
    public void clear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Integer syncTableRoute = new Integer(String.format("%d%02d", calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH)));
        Integer lastYearMonth = new Integer(String.format("%d%02d", calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1));
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        Date valuePoint = calendar.getTime();
        calendar.add(Calendar.MONTH, 1);
        Integer computeTableRoute = new Integer(String.format("%d%02d", calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1));
//        computeTableRoute = 202007;
//        lastYearMonth = 202006;

        prepare(computeTableRoute, syncTableRoute);
        List<OriginalResidenceSaleOpeningCase> list = downloadOriginalResidenceSaleOpeningCaseMapper.download();
        list.parallelStream().forEach(originalResidenceOpeningCase -> {
//            originalResidenceOpeningCase.clear();
//            originalResidenceOpeningCase.setYearMonth(syncTableRoute);
//            syncOriginalResidenceSaleOpeningCaseMapper.insert(originalResidenceOpeningCase);
        });
        running(computeTableRoute, list);
        after(computeTableRoute, lastYearMonth);
    }

    /**
     * 准备工作 创建表
     *
     * @param computeTableRoute
     * @param syncTableRoute
     */
    public void prepare(Integer computeTableRoute, Integer syncTableRoute) {
        originalResidenceSaleOpeningCaseMapper.createOpeningCaseRawTable(computeTableRoute);
        originalResidenceSaleOpeningCaseMapper.createCleanTable(computeTableRoute);
        originalResidenceSaleOpeningCaseMapper.createAssembleTable(computeTableRoute);
        originalResidenceSaleOpeningCaseMapper.createComputePriceTable(computeTableRoute);
        originalResidenceSaleOpeningCaseMapper.createArtificialPriceTable(computeTableRoute);
        originalResidenceSaleOpeningCaseMapper.createUltimatePriceTable(computeTableRoute);
//        syncOriginalResidenceSaleOpeningCaseMapper.createTable(syncTableRoute);
    }

    /**
     * 批量入库
     *
     * @param computeTableRoute
     * @param list
     */
    public void running(Integer computeTableRoute, List<OriginalResidenceSaleOpeningCase> list) {
        SqlParameterSource[] batchParams = SqlParameterSourceUtils.createBatch(list.toArray());
        int[] updateCounts = namedParameterJdbcTemplate.batchUpdate("insert into dbo" +
                        ".ODS_HOUSINGCASELISTED_LJ_" + computeTableRoute + "_RAW(case_id, llid, lcid, Name, " +
                        "Roomtype, Area, Towards, Storey, Lastdeal, Condoelev, Decoration, Year, Address, Price, " +
                        "Cname, Visited_Num, First_Visit_Time, Visited_Num_15, Visited_Num_30, Url, Curl, CurlDate) " +
                        "values (:newCaseId,:caseLianJiaId,:caseLianJiaCommunityId,:caseTitle," +
                        ":caseApartmentLayout,:caseArea,:caseToward,:caseStorey,:caseLastDeal,:caseElevator" +
                        ",:caseDecoration,:caseYear,:caseAddress,:casePrice,:caseCommunityName,:caseVisitedNum," +
                        ":caseFirstVisitTime,:caseVisitedNum15,:caseVisitedNum30,:caseUrl,:caseCommunityUrl," +
                        ":caseGetDate);",
                batchParams);
    }

    /**
     * 匹配数据
     * 计算基价
     *
     * @param yearMonth
     * @param lastYearMonth
     */
    public void after(Integer yearMonth, Integer lastYearMonth) {
        // 清洗挂牌案例

        String rawSql = LoadUtil.loadContent("sql-template/clear_sale_opening_case.sql");
//        String rawSql = LoadUtil.loadContent("sql-template/clear_residence_sale_opening_case.sql");
        String sql = rawSql.replace("#yearMonth#", yearMonth.toString())
                .replace("#lastYearMonth#", lastYearMonth.toString());
        jdbcTemplate.update(sql);

        // 作价
        rawSql = LoadUtil.loadContent("sql-template/compute_sale_price.sql");
        sql = rawSql.replace("#yearMonth#", yearMonth.toString())
                .replace("#lastYearMonth#", lastYearMonth.toString());
        jdbcTemplate.update(sql);

        logger.debug("#作价完成#");
    }
}
