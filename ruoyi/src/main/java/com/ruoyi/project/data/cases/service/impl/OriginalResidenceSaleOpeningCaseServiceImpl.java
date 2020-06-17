package com.ruoyi.project.data.cases.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.common.utils.LoadUtil;
import com.ruoyi.project.data.cases.domain.OriginalResidenceSaleOpeningCase;
import com.ruoyi.project.data.cases.mapper.OriginalResidenceSaleOpeningCaseMapper;
import com.ruoyi.project.data.cases.mapper.sync.DownloadOriginalResidenceSaleOpeningCaseMapper;
import com.ruoyi.project.data.cases.mapper.sync.SyncOriginalResidenceSaleOpeningCaseMapper;
import com.ruoyi.project.data.cases.service.IOriginalResidenceSaleOpeningCaseService;
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
    public void pullData() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Integer syncTableRoute = new Integer(String.format("%d%02d", calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH)));
        Integer lastYearMonth = new Integer(String.format("%d%02d", calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1));
        calendar.add(Calendar.MONTH, 1);
        Integer computeTableRoute = new Integer(String.format("%d%02d", calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1));

        prepare(computeTableRoute, syncTableRoute);
        List<OriginalResidenceSaleOpeningCase> list = downloadOriginalResidenceSaleOpeningCaseMapper.download();
        list.parallelStream().forEach(originalResidenceOpeningCase -> {
            originalResidenceOpeningCase.clear();
            originalResidenceOpeningCase.setYearMonth(syncTableRoute);
//            logger.debug(originalResidenceOpeningCase.toString());
            syncOriginalResidenceSaleOpeningCaseMapper.insert(originalResidenceOpeningCase);
        });
        running(computeTableRoute, syncTableRoute, list);
        after(computeTableRoute, lastYearMonth);
    }

    /**
     * 准备工作 创建表
     *
     * @param computeTableRoute
     * @param syncTableRoute
     */
    public void prepare(Integer computeTableRoute, Integer syncTableRoute) {
        originalResidenceSaleOpeningCaseMapper.createTable(computeTableRoute);
        originalResidenceSaleOpeningCaseMapper.createCleanTable(computeTableRoute);
        syncOriginalResidenceSaleOpeningCaseMapper.createTable(syncTableRoute);
    }

    /**
     * 批量入库
     *
     * @param computeTableRoute
     * @param list
     */
    public void running(Integer computeTableRoute, Integer syncTableRoute, List<OriginalResidenceSaleOpeningCase> list) {
        SqlParameterSource[] batchParams = SqlParameterSourceUtils.createBatch(list.toArray());
        int[] updateCounts = namedParameterJdbcTemplate.batchUpdate("insert into dbo" +
                        ".original_residence_sale_opening_case_" + computeTableRoute + "(case_id, case_lianjia_id, " +
                        "case_lianjia_community_id, case_title, clean_property_type, case_apartment_layout, " +
                        "case_house_structure, case_area, case_underground_area, case_toward, clean_toward, " +
                        "case_storey, clean_total_floor, clean_current_floor_desc, case_elevator, clean_elevator, " +
                        "case_tihu, case_decoration, clean_decoration, case_year, clean_year, case_address, " +
                        "case_price, clean_unit_price, clean_total_price, case_community_name, case_visited_num, " +
                        "case_visited_num_15, case_visited_num_30, case_latest_deal_date, case_latest_visited_date, " +
                        "case_first_visited_date, case_url, case_community_url, case_get_date, case_provider, " +
                        "create_time) values (:newCaseId,:caseLianJiaId,:caseLianJiaCommunityId,:caseTitle," +
                        ":cleanPropertyType," +
                        ":caseApartmentLayout,:caseHouseStructure,:caseArea,:caseUndergroundArea,:caseToward," +
                        ":cleanToward,:caseStorey,:cleanTotalFloor,:cleanCurrentFloorDesc,:caseElevator," +
                        ":cleanElevator,:caseTiHu,:caseDecoration,:cleanDecoration,:caseYear,:cleanYear,:caseAddress," +
                        ":casePrice,:cleanUnitPrice,:cleanTotalPrice,:caseCommunityName,:caseVisitedNum," +
                        ":caseVisitedNum15,:caseVisitedNum30,NULL,:caseLatestVisitTime,:caseFirstVisitTime,:caseUrl," +
                        ":caseCommunityUrl,:caseGetDate,:caseProvider,GETDATE());",
                batchParams);
    }

    /**
     * 匹配数据
     *
     * @param yearMonth
     */
    public void after(Integer yearMonth, Integer lastYearMonth) {
        // 清洗挂牌案例
        String rawSql = LoadUtil.loadContent("sql-template/clear_opening_case.sql");
        String sql = rawSql.replace("#yearMonth#", yearMonth.toString())
                .replace("#lastYearMonth#", lastYearMonth.toString());
        jdbcTemplate.update(sql);
        // 作价
    }
}
