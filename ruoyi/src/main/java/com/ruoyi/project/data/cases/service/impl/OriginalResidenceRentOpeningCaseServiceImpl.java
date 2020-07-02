package com.ruoyi.project.data.cases.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.common.utils.LoadUtil;
import com.ruoyi.project.data.cases.domain.OriginalResidenceRentOpeningCase;
import com.ruoyi.project.data.cases.domain.OriginalResidenceSaleOpeningCase;
import com.ruoyi.project.data.cases.mapper.OriginalResidenceRentOpeningCaseMapper;
import com.ruoyi.project.data.cases.mapper.OriginalResidenceSaleOpeningCaseMapper;
import com.ruoyi.project.data.cases.mapper.sync.DownloadOriginalResidenceRentOpeningCaseMapper;
import com.ruoyi.project.data.cases.mapper.sync.DownloadOriginalResidenceSaleOpeningCaseMapper;
import com.ruoyi.project.data.cases.mapper.sync.SyncOriginalResidenceRentOpeningCaseMapper;
import com.ruoyi.project.data.cases.mapper.sync.SyncOriginalResidenceSaleOpeningCaseMapper;
import com.ruoyi.project.data.cases.service.IOriginalResidenceRentOpeningCaseService;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@DS("compute")
public class OriginalResidenceRentOpeningCaseServiceImpl implements IOriginalResidenceRentOpeningCaseService {

    private static Logger logger = LoggerFactory.getLogger(OriginalResidenceRentOpeningCaseServiceImpl.class);

    @Autowired
    private OriginalResidenceRentOpeningCaseMapper originalResidenceRentOpeningCaseMapper;
    @Autowired
    private DownloadOriginalResidenceRentOpeningCaseMapper downloadOriginalResidenceRentOpeningCaseMapper;
    @Autowired
    private SyncOriginalResidenceRentOpeningCaseMapper syncOriginalResidenceRentOpeningCaseMapper;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 29号拉取挂牌案例
     */
    @Scheduled(cron = "0 0 5 29 * ?")
    @Override
    public void pullData() {
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

        prepare(computeTableRoute, syncTableRoute);
        List<OriginalResidenceRentOpeningCase> list = downloadOriginalResidenceRentOpeningCaseMapper.download();
        list.parallelStream().forEach(originalResidenceOpeningCase -> {
//            originalResidenceOpeningCase.clear();
            originalResidenceOpeningCase.setYearMonth(syncTableRoute);
            syncOriginalResidenceRentOpeningCaseMapper.insert(originalResidenceOpeningCase);
        });

        // 两种数据，分别插入不同的表当中
        running(computeTableRoute, list);
        after(computeTableRoute, lastYearMonth, valuePoint);
    }

    /**
     * 准备工作 创建表
     *
     * @param computeTableRoute
     * @param syncTableRoute
     */
    public void prepare(Integer computeTableRoute, Integer syncTableRoute) {
        originalResidenceRentOpeningCaseMapper.createTable(computeTableRoute);
        originalResidenceRentOpeningCaseMapper.createCleanTable(computeTableRoute);
        originalResidenceRentOpeningCaseMapper.createAssembleTable(computeTableRoute);
        originalResidenceRentOpeningCaseMapper.createComputePriceTable(computeTableRoute);
        originalResidenceRentOpeningCaseMapper.createArtificialPriceTable(computeTableRoute);
        originalResidenceRentOpeningCaseMapper.createUltimatePriceTable(computeTableRoute);

        syncOriginalResidenceRentOpeningCaseMapper.createTable(syncTableRoute);
    }

    /**
     * 批量入库
     *
     * @param computeTableRoute
     * @param list
     */
    public void running(Integer computeTableRoute, List<OriginalResidenceRentOpeningCase> list) {
        SqlParameterSource[] batchParams = SqlParameterSourceUtils.createBatch(list.toArray());
        int[] updateCounts = namedParameterJdbcTemplate.batchUpdate("insert into dbo" +
                        ".original_residence_rent_opening_case_" + computeTableRoute + "(case_id,case_lianjia_id," +
                        "case_lianjia_community_id,case_title,case_apartment_layout,case_area,case_total_area," +
                        "case_rent_type,case_status,case_hall_num,case_room_num,case_toward,case_storey," +
                        "case_decoration,case_address,case_price,case_community_name,case_visited_num," +
                        "case_visited_num_15,case_visited_num_30," +
                        "case_first_visited_date,case_url,case_get_date,case_provider,create_time) values " +
                        "(:newCaseId,:caseLianJiaId,:caseLianJiaCommunityId,:caseTitle," +
                        ":caseApartmentLayout,:caseArea,:caseTotalArea,:caseRentType,:caseStatus,:caseHallNum," +
                        ":caseRoomNum,:caseToward,:caseStorey,:caseDecoration,:caseAddress,:casePrice,:caseCommunityName," +
                        ":caseVisitedNum,:caseVisitedNum15,:caseVisitedNum30,:caseFirstVisitTime,:caseUrl," +
                        ":caseGetDate,:caseProvider,GETDATE());",
                batchParams);
    }

    /**
     * 匹配数据
     * 计算基价
     *
     * @param yearMonth
     * @param lastYearMonth
     * @param valuePoint
     */
    public void after(Integer yearMonth, Integer lastYearMonth, Date valuePoint) {
        // 清洗挂牌案例
        String rawSql = LoadUtil.loadContent("sql-template/clear_residence_sale_opening_case.sql");
        String sql = rawSql.replace("#yearMonth#", yearMonth.toString())
                .replace("#lastYearMonth#", lastYearMonth.toString());
        jdbcTemplate.update(sql);

        // 作价
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        rawSql = LoadUtil.loadContent("sql-template/compute_residence_sale_base_price.sql");
        sql = rawSql.replace("#yearMonth#", yearMonth.toString())
                .replace("#lastYearMonth#", lastYearMonth.toString())
                .replace("#valuePoint#", simpleDateFormat.format(valuePoint));
        jdbcTemplate.update(sql);

        logger.debug("#作价完成#");
    }
}
