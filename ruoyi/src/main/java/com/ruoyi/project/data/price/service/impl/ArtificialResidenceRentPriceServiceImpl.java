package com.ruoyi.project.data.price.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.microsoft.sqlserver.jdbc.SQLServerCallableStatement;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.ruoyi.common.utils.LoadUtil;
import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.cases.mapper.sync.SyncResidenceRentCaseMapper;
import com.ruoyi.project.data.price.domain.ArtificialResidenceRentBasePrice;
import com.ruoyi.project.data.price.domain.ComputeResidenceRentBasePrice;
import com.ruoyi.project.data.price.domain.UltimateResidenceRentBasePrice;
import com.ruoyi.project.data.price.mapper.ArtificialResidenceRentPriceMapper;
import com.ruoyi.project.data.price.mapper.UltimateResidenceRentPriceMapper;
import com.ruoyi.project.data.price.service.IArtificialResidenceRentPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 */
@Service
@DS("compute")
public class ArtificialResidenceRentPriceServiceImpl implements IArtificialResidenceRentPriceService {

    private static final Logger log = LoggerFactory.getLogger(ArtificialResidenceRentPriceServiceImpl.class);

    @Value("${spring.datasource.dynamic.datasource.compute.url}")
    private String computeConn;
    @Value("${spring.datasource.dynamic.datasource.compute.username}")
    private String computeUsername;
    @Value("${spring.datasource.dynamic.datasource.compute.password}")
    private String computePassword;
    @Autowired
    private ArtificialResidenceRentPriceMapper artificialResidenceRentPriceMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SyncResidenceRentCaseMapper syncResidenceRentCaseMapper;
    @Autowired
    private UltimateResidenceRentPriceMapper ultimateResidenceRentPriceMapper;

    @Override
    public ArtificialResidenceRentBasePrice selectById(Integer yearMonth, String id) {
        ArtificialResidenceRentBasePrice residenceRentBasePrice =
                artificialResidenceRentPriceMapper.selectById(yearMonth, id);
        residenceRentBasePrice.setYearMonth(yearMonth);
        return residenceRentBasePrice;
    }

    @Override
    public List<ArtificialResidenceRentBasePrice> selectList(ArtificialResidenceRentBasePrice
                                                                     ArtificialResidenceRentPrice) {
        return artificialResidenceRentPriceMapper.selectPageList
                (ArtificialResidenceRentPrice);
    }

    @Override
    public int selectCount(ArtificialResidenceRentBasePrice ArtificialResidenceRentPrice) {
        return artificialResidenceRentPriceMapper.selectPageCount
                (ArtificialResidenceRentPrice);
    }

    @Override
    public List<VueSelectModel> getYearMonth() {
        return artificialResidenceRentPriceMapper.yearMonthList();
    }

    @Override
    @Transactional
    public void importBySync(Integer yearMonth) {
        artificialResidenceRentPriceMapper.clearData(yearMonth);
        artificialResidenceRentPriceMapper.importBySync(yearMonth);
    }

    @Override
    public int update(ArtificialResidenceRentBasePrice officeBasePriceUltimate) {
        return artificialResidenceRentPriceMapper.update(officeBasePriceUltimate);
    }

    /**
     * 批量导入
     *
     * @param yearMonth
     * @param computeResidenceRentBasePrices
     * @return
     */
    @Override
    public String batchImport(Integer yearMonth, List<ComputeResidenceRentBasePrice> computeResidenceRentBasePrices) {
        int successNum = computeResidenceRentBasePrices.size();
        int failureNum = 0;
        StringBuilder failureMsg = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        calendar.set(yearMonth / 100, (yearMonth % 100) - 1, 1);
        Date valuePoint = calendar.getTime();
        calendar.add(Calendar.MONTH, -1);
        Date lastValuePoint = calendar.getTime();
        Integer lastYearMonth = new Integer(String.format("%d%02d", calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String priceDate = simpleDateFormat.format(valuePoint);
        String lastPriceDate = simpleDateFormat.format(lastValuePoint);

        artificialResidenceRentPriceMapper.initProcedure();
        jdbcTemplate.update("truncate table DWA_PROJECTBASEPRICE_RENT_MANU_" + yearMonth);
        jdbcTemplate.update("truncate table ODS_PROJECT_RENT_PRICE_INFO_" + yearMonth);
        artificialResidenceRentPriceMapper.prepareBachImport(yearMonth);
        CopyOnWriteArrayList<ArtificialResidenceRentBasePrice> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        computeResidenceRentBasePrices.parallelStream().forEach(inputModel -> {
            ArtificialResidenceRentBasePrice artificialResidenceRentBasePrice =
                    new ArtificialResidenceRentBasePrice();
            BeanUtils.copyProperties(inputModel, artificialResidenceRentBasePrice);
            artificialResidenceRentBasePrice.setRentPrice(inputModel.getRentPriceDft());
            artificialResidenceRentBasePrice.setMainRentPrice(inputModel.getMainRentPriceDft());
            artificialResidenceRentBasePrice.setMainCoefficientRent(inputModel.getMainCoefficientRent());

            copyOnWriteArrayList.add(artificialResidenceRentBasePrice);
        });
        try {
            // 声明变量
            // 构造一个
            String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            Class.forName(driverName);
            Connection conn = DriverManager.getConnection(computeConn, computeUsername, computePassword);
            SQLServerDataTable sourceDataTable = new SQLServerDataTable();
            sourceDataTable.addColumnMetadata("ID", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("ProjectID", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("MainCoff_Rent", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("RentPrice", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("MainRentPrice", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("RentPrice_1", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("VOPPAT", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("VOPPA", java.sql.Types.DECIMAL);

            copyOnWriteArrayList.forEach(x -> {
                try {
                    sourceDataTable.addRow(
                            x.getId(),
                            x.getCommunityId(),
                            x.getMainCoefficientRent(),
                            x.getRentPrice(),
                            x.getMainRentPrice(),
                            x.getRentPrice_1(),
                            x.getVoppat(),
                            x.getVoppa()
                    );
                } catch (SQLServerException e) {
                    e.printStackTrace();
                }
            });
            Statement statement = conn.createStatement();


            try (CallableStatement cs = conn.prepareCall("{CALL dbo.BatchImportOfArtificialResidenceRent (?)}")) {
                ((SQLServerCallableStatement) cs).setStructured(1, "DWA_PROJECTBASEPRICE_RENT_MANU_Table",
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
            statement.execute("drop procedure BatchImportOfArtificialResidenceRent");
            conn.close();
            // 人工修正导入
            String rawSql = LoadUtil.loadContent("sql-template/update_rent_price.sql");
            String sql = rawSql.replace("#yearMonth#", yearMonth.toString())
                    .replace("#lastYearMonth#", lastYearMonth.toString())
                    .replace("#priceDate#", priceDate)
                    .replace("#lastPriceDate#", lastPriceDate);
            jdbcTemplate.update(sql);
            artificialResidenceRentPriceMapper.initProcedure();
            calendar.setTime(lastValuePoint);
            calendar.add(Calendar.MONTH, -1);
            Integer lastPriceTableRoute = new Integer(String.format("%d%02d", calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1));

            pushData(yearMonth, lastYearMonth, lastPriceTableRoute);
        } catch (Exception e) {
            log.error("住宅租赁基价人工修正异常", e);
        }

        StringBuilder successMsg = new StringBuilder("恭喜您，数据已全部导入成功！共 " + (successNum - failureNum) + " 条");
        return successMsg.toString();
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
        try {
            // 当期价格同步
            syncResidenceRentCaseMapper.createUltimatePriceTable(currentPriceTableRoute);
            List<UltimateResidenceRentBasePrice> ultimateResidenceRentBasePrices =
                    ultimateResidenceRentPriceMapper.getMonthly(yearMonth);
            ultimateResidenceRentBasePrices.parallelStream().forEach(ultimateResidenceRentBasePrice -> {
                ultimateResidenceRentBasePrice.setYearMonth(currentPriceTableRoute);
                syncResidenceRentCaseMapper.insertUltimatePriceTable(ultimateResidenceRentBasePrice);
            });

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String operateDate = simpleDateFormat.format(calendar.getTime());

            // 上期价格同步
            syncResidenceRentCaseMapper.dumpPriceTable(lastPriceTableRoute, operateDate);
            syncResidenceRentCaseMapper.clearPriceTable(lastPriceTableRoute);
            List<UltimateResidenceRentBasePrice> lastUltimateResidenceRentBasePrices =
                    ultimateResidenceRentPriceMapper.getMonthly(lastPriceTableRoute);
            lastUltimateResidenceRentBasePrices.parallelStream().forEach(ultimateResidenceRentBasePrice -> {
                ultimateResidenceRentBasePrice.setYearMonth(lastPriceTableRoute);
                syncResidenceRentCaseMapper.insertUltimatePriceTable(ultimateResidenceRentBasePrice);
            });
            // 生成视图
            syncResidenceRentCaseMapper.createView(currentPriceTableRoute, lastPriceTableRoute);
        } catch (Exception e) {
            log.error("推送住宅租赁数据失败", e);
        }
    }

}