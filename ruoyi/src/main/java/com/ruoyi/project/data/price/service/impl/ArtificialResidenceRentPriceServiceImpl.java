package com.ruoyi.project.data.price.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.microsoft.sqlserver.jdbc.SQLServerCallableStatement;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.ruoyi.common.utils.LoadUtil;
import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.price.domain.ArtificialResidenceRentBasePrice;
import com.ruoyi.project.data.price.domain.ArtificialResidenceSaleBasePrice;
import com.ruoyi.project.data.price.domain.ComputeResidenceRentBasePrice;
import com.ruoyi.project.data.price.mapper.ArtificialResidenceRentPriceMapper;
import com.ruoyi.project.data.price.service.IArtificialResidenceRentPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Service业务层处理
 *
 * @author ruoyi
 * @date 2020-05-20
 */
@Service
@DS("compute")
public class ArtificialResidenceRentPriceServiceImpl implements IArtificialResidenceRentPriceService {

    private static final Logger log = LoggerFactory.getLogger(ArtificialResidenceRentPriceServiceImpl.class);

    @Autowired
    private ArtificialResidenceRentPriceMapper artificialResidenceRentPriceMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
        String lastYearMonth = String.format("%d%02d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String priceDate = simpleDateFormat.format(valuePoint);
        String lastPriceDate = simpleDateFormat.format(lastValuePoint);

        artificialResidenceRentPriceMapper.prepareBachImport(yearMonth);
        CopyOnWriteArrayList<ArtificialResidenceRentBasePrice> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        computeResidenceRentBasePrices.parallelStream().forEach(inputModel -> {
            ArtificialResidenceRentBasePrice artificialResidenceSaleBasePrice =
                    new ArtificialResidenceRentBasePrice();
            BeanUtils.copyProperties(inputModel,
                    artificialResidenceSaleBasePrice);

            artificialResidenceSaleBasePrice.setYearMonth(yearMonth);
            artificialResidenceSaleBasePrice.setPriceId(UUID.randomUUID().toString());
            artificialResidenceSaleBasePrice.setValuePoint(valuePoint);
            artificialResidenceSaleBasePrice.setLastValuePoint(lastValuePoint);
            copyOnWriteArrayList.add(artificialResidenceSaleBasePrice);
        });
        Date today = new Date();
        try {
            // 声明变量
            // 构造一个
            String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String dbURL = "jdbc:sqlserver://172.16.30.233:1433;DatabaseName=uv_compute";
            String name = "sa";
            String pwd = "Lcdatacenter_888";
            Class.forName(driverName);
            Connection conn = DriverManager.getConnection(dbURL, name, pwd);
            SQLServerDataTable sourceDataTable = new SQLServerDataTable();
            sourceDataTable.addColumnMetadata("ProjectID", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("MainCoff_Rent", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("RentPrice", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("MainRentPrice", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("RentPrice_1", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("VOPPAT", java.sql.Types.NVARCHAR);
            sourceDataTable.addColumnMetadata("VOPPA", java.sql.Types.DECIMAL);
            sourceDataTable.addColumnMetadata("ModifyDate", java.sql.Types.DATE);

            copyOnWriteArrayList.forEach(x -> {
                try {
                    sourceDataTable.addRow(
                            x.getCommunityId(),
                            x.getMainRentCoefficient(),
                            x.getRentPrice(),
                            x.getMainRentPrice(),
                            x.getRentPrice_1(),
                            x.getVoppat(),
                            x.getVoppa(),
                            today
                    );
                } catch (SQLServerException e) {
                    e.printStackTrace();
                }
            });
            Statement statement = conn.createStatement();


            try (CallableStatement cs = conn.prepareCall("{CALL dbo.BatchImportOfArtificialResidenceRent (?)}")) {
                ((SQLServerCallableStatement) cs).setStructured(1, "dbo.DWA_PROJECTBASEPRICE_RENT_MANU_Table",
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
            // yearMonth, lastYearMonth, lastPriceDate, priceDate
            String rawSql = LoadUtil.loadContent("sql-template/update_rent_price.sql");
            String sql = rawSql.replace("#yearMonth#", yearMonth.toString())
                    .replace("#lastYearMonth#", lastYearMonth)
                    .replace("#priceDate#", priceDate)
                    .replace("#lastPriceDate#", lastPriceDate);
            jdbcTemplate.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuilder successMsg = new StringBuilder("恭喜您，数据已全部导入成功！共 " + (successNum - failureNum) + " 条");
        return successMsg.toString();
    }
}