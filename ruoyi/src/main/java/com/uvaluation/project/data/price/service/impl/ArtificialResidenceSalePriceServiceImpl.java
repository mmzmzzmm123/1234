package com.uvaluation.project.data.price.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.microsoft.sqlserver.jdbc.*;
import com.uvaluation.common.utils.LoadUtil;
import com.uvaluation.project.common.VueSelectModel;
import com.uvaluation.project.data.price.domain.ArtificialResidenceSaleBasePrice;
import com.uvaluation.project.data.price.domain.ComputeResidenceSaleBasePrice;
import com.uvaluation.project.data.price.mapper.ArtificialResidenceSalePriceMapper;
import com.uvaluation.project.data.price.service.IArtificialResidenceSalePriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 */
@Service
@DS("compute")
public class ArtificialResidenceSalePriceServiceImpl implements IArtificialResidenceSalePriceService {

    private static final Logger log = LoggerFactory.getLogger(ArtificialResidenceSalePriceServiceImpl.class);

    @Autowired
    private ArtificialResidenceSalePriceMapper artificialResidenceSalePriceMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Value("${spring.datasource.dynamic.datasource.compute.url}")
    private String computeConn;
    @Value("${spring.datasource.dynamic.datasource.compute.username}")
    private String computeUsername;
    @Value("${spring.datasource.dynamic.datasource.compute.password}")
    private String computePassword;

    @Override
    public List<ArtificialResidenceSaleBasePrice> selectList(ArtificialResidenceSaleBasePrice computeResidenceSaleBasePrice) {
        return artificialResidenceSalePriceMapper.selectPageList(computeResidenceSaleBasePrice);
    }

    @Override
    public int selectCount(ArtificialResidenceSaleBasePrice computeResidenceSaleBasePrice) {
        return artificialResidenceSalePriceMapper.selectPageCount(computeResidenceSaleBasePrice);
    }

    @Override
    public List<VueSelectModel> getYearMonth() {
        return artificialResidenceSalePriceMapper.yearMonthList();
    }

    @Override
    public ArtificialResidenceSaleBasePrice selectById(String id) {
        return artificialResidenceSalePriceMapper.selectById(id);
    }

    @Override
    public int updateEntity(ArtificialResidenceSaleBasePrice artificialResidenceSaleBasePrice) {
        return artificialResidenceSalePriceMapper.updateEntity(artificialResidenceSaleBasePrice);
    }

    @Override
    public String batchImport(Integer yearMonth, List<ComputeResidenceSaleBasePrice> list) {

        int successNum = list.size();
        int failureNum = 0;
        StringBuilder failureMsg = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        calendar.set(yearMonth / 100, (yearMonth % 100) - 1, 1);
        Date valuePoint = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        calendar.add(Calendar.MONTH, -1);
        Integer lastYearMonth = new Integer(String.format("%d%02d", calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1));
        Date lastValuePoint = calendar.getTime();

        artificialResidenceSalePriceMapper.initProcedure();
        artificialResidenceSalePriceMapper.prepareBachImport(yearMonth);
        CopyOnWriteArrayList<ArtificialResidenceSaleBasePrice> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        list.parallelStream().forEach(inputModel -> {
            ArtificialResidenceSaleBasePrice artificialResidenceSaleBasePrice =
                    new ArtificialResidenceSaleBasePrice();
            BeanUtils.copyProperties(inputModel,
                    artificialResidenceSaleBasePrice);
            artificialResidenceSaleBasePrice.setYearMonth(yearMonth);
            artificialResidenceSaleBasePrice.setPriceId(UUID.randomUUID().toString());
            artificialResidenceSaleBasePrice.setValuePoint(valuePoint);
            artificialResidenceSaleBasePrice.setLastValuePoint(lastValuePoint);
            copyOnWriteArrayList.add(artificialResidenceSaleBasePrice);
        });

        try {
            // 声明变量
            // 构造一个
            String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            Class.forName(driverName);
            Connection conn = DriverManager.getConnection(computeConn, computeUsername, computePassword);
            SQLServerDataTable sourceDataTable = new SQLServerDataTable();
            sourceDataTable.addColumnMetadata("communityId", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("communityName", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("communityAddress", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("countyName", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("blockName", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("loopName", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("hasIndex", java.sql.Types.INTEGER);
            sourceDataTable.addColumnMetadata("isPstCalc", java.sql.Types.INTEGER);
            sourceDataTable.addColumnMetadata("status", java.sql.Types.INTEGER);
            sourceDataTable.addColumnMetadata("label", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("propertyType", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("projectType", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("projectTypeDtl", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("propertyLevel", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("year", java.sql.Types.INTEGER);
            sourceDataTable.addColumnMetadata("area", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("areaCoefficient", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("yearCoefficient", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("comment", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("basePriceDraft", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("mainPriceDraft", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("basePrice_1", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("voppat", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("voppa", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("priceUnitAdj", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("visitedNum", java.sql.Types.INTEGER);
            sourceDataTable.addColumnMetadata("firstVisitTime", java.sql.Types.DATE);
            sourceDataTable.addColumnMetadata("visitedNum15", java.sql.Types.INTEGER);
            sourceDataTable.addColumnMetadata("visitedNum30", java.sql.Types.INTEGER);
            sourceDataTable.addColumnMetadata("priceDealMean_1", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("priceDealMax_1", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("sumDeal_1", java.sql.Types.INTEGER);
            sourceDataTable.addColumnMetadata("priceDealMean", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("priceDealMax", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("sumDeal", java.sql.Types.INTEGER);
            sourceDataTable.addColumnMetadata("priceListedMin", java.sql.Types.INTEGER);
            sourceDataTable.addColumnMetadata("priceCase1_ToAI_Pst", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("priceCase2_ToAI_Pst", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("priceCase1_ToLst_Pst", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("priceCase2_ToLst_Pst", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("priceCase1", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("priceCase1AdjPst", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("sumCase1", java.sql.Types.INTEGER);
            sourceDataTable.addColumnMetadata("priceCase2", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("priceCase2AdjPst", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("sumCase2", java.sql.Types.INTEGER);
            sourceDataTable.addColumnMetadata("voppbt", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("voppb", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("bindCommunityId", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("bindCommunityPst", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("bindBlockClass", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("bindBlockClassPst", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("bindBlockPropertyLevel", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("bindBlockPropertyLevelPst", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("bindBlockProjectType", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("bindBlockProjectTypePst", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("bindCountyProjectType", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("bindCountyProjectTypePst", java.sql.Types.DECIMAL);

            copyOnWriteArrayList.forEach(x -> {
                try {
                    sourceDataTable.addRow(
                            x.getCommunityId(),
                            x.getCommunityName(),
                            x.getCommunityAddress(),
                            x.getCountyName(),
                            x.getBlockName(),
                            x.getLoopName(),
                            x.getHasIndex(),
                            x.getIsPstCalc(),
                            x.getStatus(),
                            x.getLabel(),
                            x.getPropertyType(),
                            x.getProjectType(),
                            x.getProjectTypeDtl(),
                            x.getPropertyLevel(),
                            x.getYear(),
                            x.getArea(),
                            x.getYearCoefficient(),
                            x.getAreaCoefficient(),
                            x.getComment(),
                            x.getBasePriceDraft(),
                            x.getMainPriceDraft(),
                            x.getBasePrice_1(),
                            x.getVoppat(),
                            x.getVoppa(),
                            x.getPriceUnitAdj(),
                            x.getVisitedNum(),
                            x.getFirstVisitTime(),
                            x.getVisitedNum15(),
                            x.getVisitedNum30(),
                            x.getPriceDealMean_1(),
                            x.getPriceDealMax_1(),
                            x.getSumDeal_1(),
                            x.getPriceDealMean(),
                            x.getPriceDealMax(),
                            x.getSumDeal(),

                            x.getPriceListedMin(),
                            x.getPriceCase1_ToAI_Pst(),
                            x.getPriceCase2_ToAI_Pst(),
                            x.getPriceCase1_ToLst_Pst(),
                            x.getPriceCase2_ToLst_Pst(),
                            x.getPriceCase1(),
                            x.getPriceCase1AdjPst(),
                            x.getSumCase1(),
                            x.getPriceCase2(),
                            x.getPriceCase2AdjPst(),
                            x.getSumCase2(),
                            x.getVoppbt(),
                            x.getVoppb(),
                            x.getBindCommunityId(),
                            x.getBindCommunityPst(),
                            x.getBindBlockClass(),
                            x.getBindBlockClassPst(),
                            x.getBindBlockPropertyLevel(),
                            x.getBindBlockPropertyLevelPst(),
                            x.getBindBlockProjectType(),
                            x.getBindBlockProjectTypePst(),
                            x.getBindCountyProjectType(),
                            x.getBindCountyProjectTypePst()
                    );
                } catch (SQLServerException e) {
                    e.printStackTrace();
                }
            });
            Statement statement = conn.createStatement();


            try (CallableStatement cs = conn.prepareCall("{CALL BatchImportOfArtificialResidenceSale (?) }")) {
                ((SQLServerCallableStatement) cs).setStructured(1, "DWA_PROJECTBASEPRICE_MANU_Table",
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
            statement.execute("IF OBJECT_ID('BatchImportOfArtificialResidenceSale', 'U') IS NOT NULL " +
                    " drop procedure BatchImportOfArtificialResidenceSale;");
            conn.close();
            artificialResidenceSalePriceMapper.initProcedure();
            after(yearMonth, lastYearMonth, valuePoint, lastValuePoint);

        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuilder successMsg = new StringBuilder("恭喜您，数据已全部导入成功！共 " + (successNum - failureNum) + " 条");
        return successMsg.toString();
    }

    /**
     * @param yearMonth
     * @param lastYearMonth
     */
    private void after(Integer yearMonth, Integer lastYearMonth, Date priceDate, Date lastPriceDate) {
        String rawSql = LoadUtil.loadContent("sql-template/update_sale_price.sql");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String sql =
                rawSql.replace("#yearMonth#", yearMonth.toString())
                        .replace("#lastYearMonth#", lastYearMonth.toString())
                        .replace("#lastPriceDate#", simpleDateFormat.format(lastPriceDate))
                        .replace("#priceDate#", simpleDateFormat.format(priceDate));
        jdbcTemplate.update(sql);

    }
}
