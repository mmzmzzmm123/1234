package com.uvaluation.longtime;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.RuoYiApplication;
import com.ruoyi.project.data.price.domain.UltimateOfficeBasePrice;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
@DS("master")
public class SqlTests {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DS("compute")
    public void testSpendTime() {

        Integer coutn = jdbcTemplate.queryForObject("select count(1) from ODS_OFFICE_BUILDING_PRICE_INFO_202006 ",
                Integer.class);

        List<UltimateOfficeBasePrice> list = jdbcTemplate.query("SELECT a.ID ,a.BuildingID_P ,a.ProjectID_P ,a" +
                ".MainPrice ,a.MainPriceRent ,a" +
                ".MainPricePst " +
                ",a.MainPriceRentPst ,a.MainPriceType ,a.MainPriceRentType ,a.ModifyDate ,a.Status ,a.BuildingStd ,a" +
                ".AdjEvd ,b.AreaCoff ,b.YearCoff ,b.BuildingCoff ,b.ProjectName ,b.ProjectAddr ,b.BuildingAddr ,b" +
                ".County ,b.Loop ,b.Block ,b.Street ,b.Year ,b.AvgArea ,b.TotalFloorSum ,b.UpperFloorSum ,b" +
                ".OfficeClass ,b.Grade ,c.MainPrice AS mainPrice_1 ,c.MainPriceRent as mainPriceRent_1 , 202006 as " +
                "yearMonth FROM ODS_OFFICE_BUILDING_PRICE_INFO_202006 a left join DIM_OFFICE_PROJECT_BUILDING_201909 " +
                "b on a.BuildingID_P=b.BuildingID_P left join ODS_OFFICE_BUILDING_PRICE_INFO_202005 c on a" +
                ".BuildingID_P = c.BuildingID_P WHERE b.valid=1 AND c.Status=1 order by a.id desc OFFSET 18570 rows " +
                " fetch next 10 rows only;", new TestTempMapper());
        Assert.assertEquals(list.size(), 2);
    }

    class TestTempMapper implements RowMapper<UltimateOfficeBasePrice> {

        @Override
        public UltimateOfficeBasePrice mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            UltimateOfficeBasePrice result = new UltimateOfficeBasePrice();
            result.setId(resultSet.getString("id"));
            result.setBuildingId(resultSet.getString("BuildingID_P"));
            return result;
        }
    }
}
