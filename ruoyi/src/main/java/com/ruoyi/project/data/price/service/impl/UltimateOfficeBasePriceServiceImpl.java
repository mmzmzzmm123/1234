package com.ruoyi.project.data.price.service.impl;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.microsoft.sqlserver.jdbc.SQLServerCallableStatement;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.LoadUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.cases.domain.OfficeAggregationCase;
import com.ruoyi.project.data.cases.mapper.OriginalOfficeCaseMapper;
import com.ruoyi.project.data.cases.mapper.sync.SyncOfficeAggregationCaseMapper;
import com.ruoyi.project.data.price.domain.UltimateOfficeBasePrice;
import com.ruoyi.project.data.price.mapper.UltimateOfficeBasePriceMapper;
import com.ruoyi.project.data.price.service.IUltimateOfficeBasePriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class UltimateOfficeBasePriceServiceImpl implements IUltimateOfficeBasePriceService {

    private static final Logger log = LoggerFactory.getLogger(UltimateOfficeBasePriceServiceImpl.class);

    @Value("${spring.datasource.dynamic.datasource.compute.url}")
    private String computeConn;
    @Value("${spring.datasource.dynamic.datasource.compute.username}")
    private String computeUsername;
    @Value("${spring.datasource.dynamic.datasource.compute.password}")
    private String computePassword;

    @Autowired
    private UltimateOfficeBasePriceMapper ultimateOfficeBasePriceMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private OriginalOfficeCaseMapper originalOfficeCaseMapper;
    @Autowired
    private SyncOfficeAggregationCaseMapper syncOfficeAggregationCaseMapper;

    private static Integer getLastYearMonth(Integer yearMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(yearMonth / 100, yearMonth % 100, 1);
        return new Integer(String.format("%d%02d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) - 1));
    }

    /**
     * 查询办公基价列表
     *
     * @param officeBasePriceUltimate 办公基价
     * @return 办公基价
     */
    @Override
    public List<UltimateOfficeBasePrice> selectOfficeBasePriceUltimateList(UltimateOfficeBasePrice officeBasePriceUltimate) {
        officeBasePriceUltimate.setLastYearMonth(getLastYearMonth(officeBasePriceUltimate.getYearMonth()));
        return ultimateOfficeBasePriceMapper.getList(officeBasePriceUltimate);
    }

    @Override
    public int selectOfficeBasePriceUltimateListCount(UltimateOfficeBasePrice officeBasePriceUltimate) {
        officeBasePriceUltimate.setLastYearMonth(getLastYearMonth(officeBasePriceUltimate.getYearMonth()));
        return ultimateOfficeBasePriceMapper.getCount(officeBasePriceUltimate);
    }

    @Override
    public UltimateOfficeBasePrice getById(Integer yearMonth, String id) {
        Integer lastYearMonth = getLastYearMonth(yearMonth);
        return ultimateOfficeBasePriceMapper.getById(yearMonth, lastYearMonth, id);
    }

    @Override
    public String batchImport(Integer yearMonth, List<UltimateOfficeBasePrice> officeBasePriceUltimates,
                              String operName) {
        if (StringUtils.isNull(officeBasePriceUltimates) || officeBasePriceUltimates.size() == 0) {
            throw new CustomException("导入办公数据不能为空！");
        }
        int successNum = officeBasePriceUltimates.size();
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        Integer lastYearMonth = getLastYearMonth(yearMonth);

        ultimateOfficeBasePriceMapper.initProcedure();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String operateDate = simpleDateFormat.format(calendar.getTime());
        ultimateOfficeBasePriceMapper.dumpTable(yearMonth, operateDate);
        ultimateOfficeBasePriceMapper.clearArtificialTable(yearMonth);
        ultimateOfficeBasePriceMapper.prepareBachImport(yearMonth);

        try {
            // 声明变量
            // 构造一个
            String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            Class.forName(driverName);
            Connection conn = DriverManager.getConnection(computeConn, computeUsername, computePassword);
            SQLServerDataTable sourceDataTable = new SQLServerDataTable();

            sourceDataTable.addColumnMetadata("id", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("BuildingID_P", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("ProjectID_P", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("ProjectName", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("ProjectAddr", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("BuildingAddr", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("County", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("Loop", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("Block", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("Street", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("Year", java.sql.Types.INTEGER);
            sourceDataTable.addColumnMetadata("AvgArea", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("TotalFloorSum", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("UpperFloorSum", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("OfficeClass", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("Grade", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("MainPrice_1", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("MainPriceRent_1", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("MainPrice", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("MainPriceRent", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("MainPricePst", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("MainPriceRentPst", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("MainPriceType", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("MainPriceRentType", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("AreaCoff", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("YearCoff", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("BuildingCoff", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("BuildingStd", java.sql.Types.BIT);
            sourceDataTable.addColumnMetadata("AdjEvd", java.sql.Types.NVARCHAR);


            officeBasePriceUltimates.forEach(x -> {
                try {
                    sourceDataTable.addRow(
                            x.getId(),
                            x.getBuildingId(),
                            x.getCommunityId(),
                            x.getCommunityName(),
                            x.getCommunityAddress(),
                            x.getBuildingAddress(),
                            x.getCountyName(),
                            x.getLoopName(),
                            x.getBlockName(),
                            x.getStreetName(),
                            x.getYear(),
                            x.getAvgArea(),
                            x.getTotalFloorSum(),
                            x.getUpperFloorSum(),
                            x.getOfficeClass(),
                            x.getOfficeLevel(),
                            x.getMainPrice_1(),
                            x.getMainPriceRent_1(),
                            x.getMainPrice(),
                            x.getMainPriceRent(),
                            x.getMainPricePst(),
                            x.getMainPriceRentPst(),
                            x.getMainPriceType(),
                            x.getMainPriceRentType(),
                            x.getAreaCoefficient(),
                            x.getYearCoefficient(),
                            x.getBuildingCoefficient(),
                            x.getStandardBuilding(),
                            x.getAdjustPriceComment()
                    );

                } catch (SQLServerException e) {
                    e.printStackTrace();
                }
            });

            try (CallableStatement cs = conn.prepareCall("{CALL BatchImportOfArtificialOfficePrice (?) }")) {
                ((SQLServerCallableStatement) cs).setStructured(1, "DWA_PROJECTBASEPRICE_OFFICE_MANU_Table",
                        sourceDataTable);
                boolean resultSetReturned = cs.execute();
                if (resultSetReturned) {
                    try (ResultSet rs = cs.getResultSet()) {
                        rs.next();
                        System.out.println(rs.getInt(1));
                    }
                }
            }
            // 删除存储过程，还原环境
            ultimateOfficeBasePriceMapper.initProcedure();
            conn.close();

            int year = yearMonth / 100;
            int month = yearMonth % 100;
            calendar.set(year, month, 1);
            Date lastValuePoint = calendar.getTime();
            calendar.add(Calendar.MONTH, -1);
            Date valuePoint = calendar.getTime();

            after(yearMonth, lastYearMonth, valuePoint, lastValuePoint);

        } catch (Exception e) {
            log.error("办公导入异常", e);
            e.printStackTrace();
        }

        successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条");
        return successMsg.toString();
    }

    @Override
    public List<VueSelectModel> getYearMonthList() {
        return ultimateOfficeBasePriceMapper.getYearMonthList();
    }

    /**
     * @param yearMonth
     * @param lastYearMonth
     * @param valuePoint
     * @param lastValuePoint
     */
    private void after(Integer yearMonth, Integer lastYearMonth, Date valuePoint, Date lastValuePoint) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String priceDate = simpleDateFormat.format(valuePoint);
        String lastPriceDate = simpleDateFormat.format(lastValuePoint);
        String rawSql = LoadUtil.loadContent("sql-template/update_office_price.sql");

        String sql = rawSql.replace("#yearMonth#", yearMonth.toString())
                .replace("#lastYearMonth#", lastYearMonth.toString())
                .replace("#today#", priceDate)
                .replace("#lastMonth#", lastPriceDate);
        jdbcTemplate.update(sql);
    }

    /**
     * 数据推送
     *
     * @param yearMonth              作价表
     * @param currentPriceTableRoute 当期表
     * @param lastPriceTableRoute    上期表
     */
    @Async
    public void pushData(Integer yearMonth, Integer currentPriceTableRoute, Integer lastPriceTableRoute) {
        // 案例同步
        syncOfficeAggregationCaseMapper.createAggregationCaseTable(currentPriceTableRoute);
        List<OfficeAggregationCase> list = originalOfficeCaseMapper.getOfficeAggregationCases(yearMonth);
        list.parallelStream().forEach(officeAggregationCase -> {
            syncOfficeAggregationCaseMapper.insertAggregationCase(officeAggregationCase);
        });

        // 当期价格同步
        syncOfficeAggregationCaseMapper.createUltimatePriceTable(currentPriceTableRoute);
        List<UltimateOfficeBasePrice> ultimateOfficeBasePrices =
                ultimateOfficeBasePriceMapper.getUltimateOfficeBasePrices(yearMonth);
        ultimateOfficeBasePrices.parallelStream().forEach(ultimateOfficeBasePrice -> {
            syncOfficeAggregationCaseMapper.insertUltimatePriceTable(ultimateOfficeBasePrice);
        });

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String operateDate = simpleDateFormat.format(calendar.getTime());

        // 上期价格同步
        syncOfficeAggregationCaseMapper.dumpPriceTable(lastPriceTableRoute, operateDate);
        syncOfficeAggregationCaseMapper.clearPriceTable(lastPriceTableRoute);
        List<UltimateOfficeBasePrice> lastUltimateOfficeBasePrices =
                ultimateOfficeBasePriceMapper.getUltimateOfficeBasePrices(lastPriceTableRoute);
        lastUltimateOfficeBasePrices.parallelStream().forEach(ultimateOfficeBasePrice -> {
            syncOfficeAggregationCaseMapper.insertUltimatePriceTable(ultimateOfficeBasePrice);
        });
    }
}
