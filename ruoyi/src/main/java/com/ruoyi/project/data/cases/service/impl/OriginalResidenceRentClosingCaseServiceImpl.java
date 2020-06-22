package com.ruoyi.project.data.cases.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.common.utils.LoadUtil;
import com.ruoyi.project.data.cases.domain.OriginalResidenceSaleClosingCase;
import com.ruoyi.project.data.cases.mapper.OriginalResidenceSaleClosingCaseMapper;
import com.ruoyi.project.data.cases.mapper.sync.DownloadOriginalResidenceSaleClosingCaseMapper;
import com.ruoyi.project.data.cases.service.IOriginalResidenceRentClosingCaseService;
import com.ruoyi.project.data.cases.service.IOriginalResidenceSaleClosingCaseService;
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
public class OriginalResidenceRentClosingCaseServiceImpl implements IOriginalResidenceRentClosingCaseService  {

    private static Logger logger = LoggerFactory.getLogger(OriginalResidenceRentClosingCaseServiceImpl.class);

    @Autowired
    private OriginalResidenceSaleClosingCaseMapper originalResidenceSaleClosingCaseMapper;
    @Autowired
    private DownloadOriginalResidenceSaleClosingCaseMapper downloadOriginalResidenceSaleClosingCaseMapper;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     *
     */
    @Scheduled(cron = "0 0 5 9 * ?")
    @Override
    public void pullData() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Integer targetTableRoute = new Integer(String.format("%d%02d", calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH)));
        calendar.add(Calendar.MONTH, 1);
        Integer computeTableRoute = new Integer(String.format("%d%02d", calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1));

        prepare(computeTableRoute);
        List<OriginalResidenceSaleClosingCase> list =
                downloadOriginalResidenceSaleClosingCaseMapper.download(targetTableRoute);
        list.parallelStream().forEach(originalResidenceSaleClosingCase -> {
            originalResidenceSaleClosingCase.setCaseId(originalResidenceSaleClosingCase.generateCaseId());
            originalResidenceSaleClosingCase.setCleanPropertyType(originalResidenceSaleClosingCase.refinePropertyType());
//            originalResidenceSaleClosingCase.setCleanCurrentFloor(originalResidenceSaleClosingCase
//            .refineCurrentFloor());
//            originalResidenceSaleClosingCase.setCleanBuildingAddress(originalResidenceSaleClosingCase
//            .refineBuildingAddress());
        });
        running(computeTableRoute, list);
        after(computeTableRoute);
    }

    /**
     * 准备工作 创建表
     *
     * @param computeTableRoute
     */
    public void prepare(Integer computeTableRoute) {
        originalResidenceSaleClosingCaseMapper.createTable(computeTableRoute);
        originalResidenceSaleClosingCaseMapper.createCleanTable(computeTableRoute);
    }

    /**
     * 批量入库
     *
     * @param computeTableRoute
     * @param list
     */
    public void running(Integer computeTableRoute, List<OriginalResidenceSaleClosingCase> list) {
        SqlParameterSource[] batchParams = SqlParameterSourceUtils.createBatch(list.toArray());
        int[] updateCounts = namedParameterJdbcTemplate.batchUpdate("insert into dbo" +
                        ".original_residence_sale_closing_case_" + computeTableRoute + "(case_id,case_county_name," +
                        "case_block_name,case_loopline_name,case_community_name,case_address,case_area," +
                        "case_unit_price,case_total_price,case_house_type,case_signing_date,case_register_date," +
                        "case_agency_name,case_agency_type,case_seller_type,case_buyer_type,case_birthday," +
                        "case_deal_type,clean_property_type,create_time) " +
                        "values (:caseId,:caseCountyName,:caseBlockName,:caseLoopName,:caseCommunityName," +
                        ":caseAddress,:caseArea,:caseUnitPrice,:caseTotalPrice,:caseHouseType,:caseSigningDate," +
                        ":caseRegisterDate,:agencyName,:agencyType,:sellerType,:buyerType,:birthday," +
                        ":cleanCaseType,:cleanPropertyType,GETDATE());",
                batchParams);
    }

    /**
     * 匹配数据
     *
     * @param yearMonth
     */
    public void after(Integer yearMonth) {
        // 清洗挂牌案例
        String rawSql = LoadUtil.loadContent("sql-template/clear_residence_sale_closing_case.sql");
        String sql = rawSql.replace("#yearMonth#", yearMonth.toString());
        jdbcTemplate.update(sql);
    }
}
