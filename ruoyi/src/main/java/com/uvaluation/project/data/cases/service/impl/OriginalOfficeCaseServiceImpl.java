package com.uvaluation.project.data.cases.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.uvaluation.common.utils.LoadUtil;
import com.uvaluation.project.data.cases.domain.OriginalOfficeCase;
import com.uvaluation.project.data.cases.mapper.OriginalOfficeCaseMapper;
import com.uvaluation.project.data.cases.mapper.sync.DownloadOriginalOfficeCaseMapper;
import com.uvaluation.project.data.cases.service.IOriginalOfficeCaseService;
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
public class OriginalOfficeCaseServiceImpl implements IOriginalOfficeCaseService {

    private static Logger logger = LoggerFactory.getLogger(OriginalOfficeCaseServiceImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private DownloadOriginalOfficeCaseMapper downloadOriginalOfficeCaseMapper;
    @Autowired
    private OriginalOfficeCaseMapper originalOfficeCaseMapper;

    /**
     * 计算
     */
    @Override
    @Scheduled(cron = "0 0 17 28 * ?")
    public void compute() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 15);
        Date startDate = calendar.getTime();
        Integer lastYearMonth = new Integer(String.format("%d%02d", calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1));
        calendar.add(Calendar.MONTH, 1);
        Date endDate = calendar.getTime();
        Integer yearMonth = new Integer(String.format("%d%02d", calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1));

//        yearMonth = 202007;
//        lastYearMonth = 202006;
//        calendar.set(2020, 5, 16);
//        startDate = calendar.getTime();
//        calendar.set(2020, 6, 16);
//        endDate = calendar.getTime();

        before(yearMonth, startDate, endDate);
        running(yearMonth, lastYearMonth);
    }

    /**
     * 创建表
     *
     * @param yearMonth
     */
    private void before(Integer yearMonth, Date startDate, Date endDate) {
        // 创建表
        originalOfficeCaseMapper.createTable(yearMonth);
        originalOfficeCaseMapper.createArtificialTable(yearMonth);
        originalOfficeCaseMapper.initAssembleTable(yearMonth);
        originalOfficeCaseMapper.initPriceTable(yearMonth);

        // 下载列表
        List<OriginalOfficeCase> downloadList = downloadOriginalOfficeCaseMapper.download(startDate, endDate);
        SqlParameterSource[] batchParams = SqlParameterSourceUtils.createBatch(downloadList.toArray());
        namedParameterJdbcTemplate.batchUpdate("insert into dbo.ODS_OFFICECASELISTED_" + yearMonth.toString() + "_RAW" +
                "(case_id,url, title,容积率,总价售,均价售, 楼盘名称, 楼盘名称_M, 楼层, 面积, 物业费, 工位数, 地址, 地铁, 发布时间, 房源编号, 百度lng, 百度lat, " +
                "区域, 分类, " +
                "来源, 等级, 楼盘网址, 装修,类型, 板块, 挂牌中介, 月租金租, 标准租金租, 得房率, 总价, 单价,更新时间) values(replace(newid(),'-',''),:url," +
                ":title," +
                ":floorAreaRatio," +
                ":caseTotalPrice," +
                ":caseUnitPrice,:name,:name_m,:caseFloor,:area,:managementFee,:seatCount,:address,:metro," +
                ":publishDate,:sourceNo,:lng,:lat,:county,:catalog,:source,:level,:homePageUrl,:decoration," +
                ":type,:block,:agency,:rentOfMonthly,:rentOfStandard,:score,:totalPrice,:unitPrice,:updateDate) ",
                batchParams);
    }

    private void running(Integer yearMonth, Integer lastYearMonth) {

        String rawSql = LoadUtil.loadContent("sql-template/compute_office_price.sql");
        String sql = rawSql.replace("#yearMonth#", yearMonth.toString())
                .replace("#lastYearMonth#", lastYearMonth.toString());

        jdbcTemplate.execute(sql);
        logger.debug(sql);
    }
}
