package com.ruoyi.project.data.price.service.impl;

import java.math.BigDecimal;
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
import com.ruoyi.project.data.price.domain.OfficeBasePriceModifyModel;
import com.ruoyi.project.data.price.domain.UltimateOfficeBasePrice;
import com.ruoyi.project.data.price.mapper.UltimateOfficeBasePriceMapper;
import com.ruoyi.project.data.price.service.IUltimateOfficeBasePriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
@DS("compute")
public class UltimateOfficeBasePriceServiceImpl implements IUltimateOfficeBasePriceService {

    private static final Logger log = LoggerFactory.getLogger(UltimateOfficeBasePriceServiceImpl.class);

    @Value("${spring.datasource.dynamic.datasource.compute.url}")
    private String computeConn;
    @Value("${spring.datasource.dynamic.datasource.compute.username}")
    private String computeUsername;
    @Value("${spring.datasource.dynamic.datasource.compute.password}")
    private String computePassword;

    @Autowired
    private UltimateOfficeBasePriceMapper officeBasePriceUltimateMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

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
        return officeBasePriceUltimateMapper.getList(officeBasePriceUltimate);
    }

    @Override
    public int selectOfficeBasePriceUltimateListCount(UltimateOfficeBasePrice officeBasePriceUltimate) {
        officeBasePriceUltimate.setLastYearMonth(getLastYearMonth(officeBasePriceUltimate.getYearMonth()));
        return officeBasePriceUltimateMapper.getCount(officeBasePriceUltimate);
    }

    @Override
    public UltimateOfficeBasePrice getById(Integer yearMonth, String id) {
        Integer lastYearMonth = getLastYearMonth(yearMonth);
        return officeBasePriceUltimateMapper.getById(yearMonth, lastYearMonth, id);
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

        officeBasePriceUltimateMapper.initProcedure();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String operateDate = simpleDateFormat.format(calendar.getTime());
        officeBasePriceUltimateMapper.dumpTable(yearMonth, operateDate);
        officeBasePriceUltimateMapper.clearArtificialTable(yearMonth);
        officeBasePriceUltimateMapper.prepareBachImport(yearMonth);

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
            officeBasePriceUltimateMapper.initProcedure();
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
        return officeBasePriceUltimateMapper.getYearMonthList();
    }

    /**
     * 更新
     *
     * @param officeBasePriceUltimate
     * @return
     */
    @Override
    public int update(UltimateOfficeBasePrice officeBasePriceUltimate) {
        // 当期价格
        Integer lastYearMonth = getLastYearMonth(officeBasePriceUltimate.getYearMonth());
        UltimateOfficeBasePrice ultimateOfficeBasePrice =
                officeBasePriceUltimateMapper.getByRouteId(officeBasePriceUltimate.getYearMonth(),
                        officeBasePriceUltimate.getId());
        // 上期价格
        UltimateOfficeBasePrice lastUltimateOfficeBasePrice =
                officeBasePriceUltimateMapper.getByBuildingId(lastYearMonth,
                        officeBasePriceUltimate.getBuildingId());

        updateBasePrice(officeBasePriceUltimate, ultimateOfficeBasePrice, lastUltimateOfficeBasePrice);

        return 1;
    }

    /**
     * 基价更新
     *
     * @param inputModel
     * @param currentUltimateOfficeBasePrice
     * @param lastUltimateOfficeBasePrice
     */
    public void updateBasePrice(UltimateOfficeBasePrice inputModel,
                                UltimateOfficeBasePrice currentUltimateOfficeBasePrice,
                                UltimateOfficeBasePrice lastUltimateOfficeBasePrice) {

        OfficeBasePriceModifyModel officeBasePriceModifyModel = compareYearMonth(inputModel,
                currentUltimateOfficeBasePrice, lastUltimateOfficeBasePrice);
        if (null != officeBasePriceModifyModel) {
            officeBasePriceUltimateMapper.updateStatus(officeBasePriceModifyModel);
            officeBasePriceUltimateMapper.updateBasePriceCopyNew(officeBasePriceModifyModel);
        }

        officeBasePriceModifyModel = compareLastYearMonth(inputModel, lastUltimateOfficeBasePrice);
        if (null != officeBasePriceModifyModel) {
            officeBasePriceUltimateMapper.updateBasePriceStatus(officeBasePriceModifyModel);
            officeBasePriceUltimateMapper.updateBasePriceCopyNew(officeBasePriceModifyModel);
        }
    }

    /**
     * 上期价格
     *
     * @param inputModel
     * @param ultimateOfficeBasePrice
     * @return
     */
    private OfficeBasePriceModifyModel compareYearMonth(UltimateOfficeBasePrice inputModel,
                                                        UltimateOfficeBasePrice ultimateOfficeBasePrice,
                                                        UltimateOfficeBasePrice lastUltimateOfficeBasePrice) {
        BigDecimal inputMainPrice = inputModel.getMainPrice();
        BigDecimal mainPrice = ultimateOfficeBasePrice.getMainPrice();
        BigDecimal inputMainPriceRent = inputModel.getMainPriceRent();
        BigDecimal mainPriceRent = ultimateOfficeBasePrice.getMainPriceRent();
        if (Objects.equals(inputMainPrice, mainPrice) && Objects.equals(inputMainPriceRent, mainPriceRent)) {
            return null;
        }

        OfficeBasePriceModifyModel officeBasePriceModifyModel =
                new OfficeBasePriceModifyModel(inputModel.getId(),
                        inputModel.getYearMonth());
        officeBasePriceModifyModel.setMainPrice(inputModel.getMainPrice_1());
        if (null != lastUltimateOfficeBasePrice.getMainPrice() && lastUltimateOfficeBasePrice.getMainPrice().compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal mainPricePst =
                    inputModel.getMainPrice().divide(lastUltimateOfficeBasePrice.getMainPrice(), 4);
            officeBasePriceModifyModel.setMainPricePst(mainPricePst);
        }

        officeBasePriceModifyModel.setMainPriceRent(inputModel.getMainPriceRent_1());
        if (null != lastUltimateOfficeBasePrice.getMainPriceRent() && lastUltimateOfficeBasePrice.getMainPriceRent().compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal mainPriceRentPst =
                    inputModel.getMainPriceRent().divide(lastUltimateOfficeBasePrice.getMainPriceRent(), 4);
            officeBasePriceModifyModel.setMainPriceRentPst(mainPriceRentPst);
        }

        return officeBasePriceModifyModel;
    }

    /**
     * 上期价格
     *
     * @param inputModel
     * @param lastUltimateOfficeBasePrice
     * @return
     */
    private OfficeBasePriceModifyModel compareLastYearMonth(UltimateOfficeBasePrice inputModel,
                                                            UltimateOfficeBasePrice lastUltimateOfficeBasePrice) {
        BigDecimal inputMainPrice = inputModel.getMainPrice_1();
        BigDecimal mainPrice_1 = lastUltimateOfficeBasePrice.getMainPrice();
        BigDecimal inputMainPriceRent = inputModel.getMainPriceRent_1();
        BigDecimal mainPriceRent_1 = lastUltimateOfficeBasePrice.getMainPriceRent();
        if (Objects.equals(inputMainPrice, mainPrice_1) && Objects.equals(inputMainPriceRent, mainPriceRent_1)) {
            return null;
        }

        OfficeBasePriceModifyModel officeBasePriceModifyModel =
                new OfficeBasePriceModifyModel(lastUltimateOfficeBasePrice.getId(),
                        lastUltimateOfficeBasePrice.getYearMonth());
        officeBasePriceModifyModel.setMainPrice(inputModel.getMainPrice_1());
        officeBasePriceModifyModel.setMainPricePst(lastUltimateOfficeBasePrice.getMainPricePst());
        officeBasePriceModifyModel.setMainPriceRent(inputModel.getMainPriceRent_1());
        officeBasePriceModifyModel.setMainPriceRentPst(lastUltimateOfficeBasePrice.getMainPriceRentPst());

        return officeBasePriceModifyModel;
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
}
