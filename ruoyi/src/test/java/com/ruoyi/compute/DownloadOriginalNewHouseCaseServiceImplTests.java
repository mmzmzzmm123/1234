package com.ruoyi.compute;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.project.data.cases.service.impl.DownloadOriginalNewHouseCaseServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
@ActiveProfiles("dev")
public class DownloadOriginalNewHouseCaseServiceImplTests {
    @Autowired
    private DownloadOriginalNewHouseCaseServiceImpl downloadOriginalNewHouseCaseService;

    /**
     * 一手案例初始化
     */
    @Test
    public void testInitCase() {
        testDownloadFirstBatch();

        testDownloadSecondBatch();
    }

//    /**
//     * 下载第一批一手房案例
//     */
//    @Test
//    public void testDownloadFirst() {
//        downloadOriginalNewHouseCaseService.downloadFirst();
//    }
//
//    /**
//     * 下载第二批一手房案例
//     */
//    @Test
//    public void testDownloadSecond() {
//        downloadOriginalNewHouseCaseService.downloadSecond();
//    }

    /**
     * 第一批一手成交案例
     */
    @Test
    public void testDownloadFirstBatch() {
        // 时间要是线性的
        // 2017第一批一手成交案例
        for (int i = 201701; i <= 201712; i++) {
            downloadOriginalNewHouseCaseService.downloadFirst(i);
        }
        // 2018第一批一手成交案例
        for (int i = 201801; i <= 201812; i++) {
            downloadOriginalNewHouseCaseService.downloadFirst(i);
        }
        // 2019第一批一手成交案例
        for (int i = 201901; i <= 201912; i++) {
            downloadOriginalNewHouseCaseService.downloadFirst(i);
        }
        // 2020第一批一手成交案例
        for (int i = 202001; i <= 202006; i++) {
            downloadOriginalNewHouseCaseService.downloadFirst(i);
        }
    }

    @Test
    public void testDownload201709FirstBatch() {
        downloadOriginalNewHouseCaseService.downloadFirst(201709);
    }

    /**
     * 2017第二批一手成交案例
     */
    @Test
    public void testDownload2017SecondBatch() {
        // 一手房成交数据201701_201704_update
//        downloadOriginalNewHouseCaseService.downloadSecond(201704);
        // 一手房成交数据201707_update
        downloadOriginalNewHouseCaseService.downloadSecond(201707);
        // 一手房成交数据201708_update
        downloadOriginalNewHouseCaseService.downloadSecond(201708);
        // 一手房成交数据201711_update
        downloadOriginalNewHouseCaseService.downloadSecond(201711);
        // 一手房成交数据201712_update
        downloadOriginalNewHouseCaseService.downloadSecond(201712);
    }

    /**
     * 第二批一手成交案例
     */
    @Test
    public void testDownloadSecondBatch() {
        // 2018第二批一手成交案例
        for (int i = 201801; i <= 201812; i++) {
            downloadOriginalNewHouseCaseService.downloadSecond(i);
        }
        // 2019第二批一手成交案例
        for (int i = 201901; i <= 201912; i++) {
            downloadOriginalNewHouseCaseService.downloadSecond(i);
        }
        // 2020第二批一手成交案例
        Arrays.asList(202001, 202002, 202003, 202004, 202005, 202006).parallelStream().forEach(i -> {
            downloadOriginalNewHouseCaseService.downloadSecond(i);
        });
    }

    // 年表
    @Test
    public void printInsert2017AggregationCaseTable() {
        StringBuilder sb = new StringBuilder("truncate table original_new_house_case_2017;\n");
        for (int i = 201701; i <= 201712; i++) {
            sb.append("insert into dbo.original_new_house_case_2017(case_id ,case_county ,case_block ,case_loop ," +
                    "case_community_name ,case_address ,case_area ,case_unit_price ,case_total_price ,case_house_type" +
                    " ,case_signing_date ,case_floor ,case_house_property ,case_apartment_layout ,compute_unit_price " +
                    ",compute_total_price ,reference_unit_price ,reference_total_price ,case_label ,create_time) \n" +
                    "SELECT case_id ,case_county ,case_block ,case_loop ,case_community_name ,case_address ,case_area" +
                    " ,case_unit_price ,case_total_price ,case_house_type ,case_signing_date ,case_floor ," +
                    "case_house_property ,case_apartment_layout ,compute_unit_price ,compute_total_price ," +
                    "reference_unit_price ,reference_total_price ,case_label ,create_time FROM dbo" +
                    ".original_new_house_case_" + i + ";\n");
        }
        sb.append("insert into dbo.original_new_house_case_2017(case_id ,case_county ,case_block ,case_loop ," +
                "case_community_name ,case_address ,case_area ,case_unit_price ,case_total_price ,case_house_type" +
                " ,case_signing_date ,case_floor ,case_house_property ,case_apartment_layout ,compute_unit_price " +
                ",compute_total_price ,reference_unit_price ,reference_total_price ,case_label ,create_time) \n" +
                "SELECT case_id ,case_county ,case_block ,case_loop ,case_community_name ,case_address ,case_area" +
                " ,case_unit_price ,case_total_price ,case_house_type ,case_signing_date ,case_floor ," +
                "case_house_property ,case_apartment_layout ,compute_unit_price ,compute_total_price ," +
                "reference_unit_price ,reference_total_price ,case_label ,create_time FROM dbo" +
                ".original_new_house_case_201701_201704_update;\n");
        sb.append("insert into dbo.original_new_house_case_2017(case_id ,case_county ,case_block ,case_loop ," +
                "case_community_name ,case_address ,case_area ,case_unit_price ,case_total_price ,case_house_type" +
                " ,case_signing_date ,case_floor ,case_house_property ,case_apartment_layout ,compute_unit_price " +
                ",compute_total_price ,reference_unit_price ,reference_total_price ,case_label ,create_time) \n" +
                "SELECT case_id ,case_county ,case_block ,case_loop ,case_community_name ,case_address ,case_area" +
                " ,case_unit_price ,case_total_price ,case_house_type ,case_signing_date ,case_floor ," +
                "case_house_property ,case_apartment_layout ,compute_unit_price ,compute_total_price ," +
                "reference_unit_price ,reference_total_price ,case_label ,create_time FROM dbo" +
                ".original_new_house_case_201707_update;\n");
        sb.append("insert into dbo.original_new_house_case_2017(case_id ,case_county ,case_block ,case_loop ," +
                "case_community_name ,case_address ,case_area ,case_unit_price ,case_total_price ,case_house_type" +
                " ,case_signing_date ,case_floor ,case_house_property ,case_apartment_layout ,compute_unit_price " +
                ",compute_total_price ,reference_unit_price ,reference_total_price ,case_label ,create_time) \n" +
                "SELECT case_id ,case_county ,case_block ,case_loop ,case_community_name ,case_address ,case_area" +
                " ,case_unit_price ,case_total_price ,case_house_type ,case_signing_date ,case_floor ," +
                "case_house_property ,case_apartment_layout ,compute_unit_price ,compute_total_price ," +
                "reference_unit_price ,reference_total_price ,case_label ,create_time FROM dbo" +
                ".original_new_house_case_201708_update;\n");
        sb.append("insert into dbo.original_new_house_case_2017(case_id ,case_county ,case_block ,case_loop ," +
                "case_community_name ,case_address ,case_area ,case_unit_price ,case_total_price ,case_house_type" +
                " ,case_signing_date ,case_floor ,case_house_property ,case_apartment_layout ,compute_unit_price " +
                ",compute_total_price ,reference_unit_price ,reference_total_price ,case_label ,create_time) \n" +
                "SELECT case_id ,case_county ,case_block ,case_loop ,case_community_name ,case_address ,case_area" +
                " ,case_unit_price ,case_total_price ,case_house_type ,case_signing_date ,case_floor ," +
                "case_house_property ,case_apartment_layout ,compute_unit_price ,compute_total_price ," +
                "reference_unit_price ,reference_total_price ,case_label ,create_time FROM dbo" +
                ".original_new_house_case_201711_update;\n");
        sb.append("insert into dbo.original_new_house_case_2017(case_id ,case_county ,case_block ,case_loop ," +
                "case_community_name ,case_address ,case_area ,case_unit_price ,case_total_price ,case_house_type" +
                " ,case_signing_date ,case_floor ,case_house_property ,case_apartment_layout ,compute_unit_price " +
                ",compute_total_price ,reference_unit_price ,reference_total_price ,case_label ,create_time) \n" +
                "SELECT case_id ,case_county ,case_block ,case_loop ,case_community_name ,case_address ,case_area" +
                " ,case_unit_price ,case_total_price ,case_house_type ,case_signing_date ,case_floor ," +
                "case_house_property ,case_apartment_layout ,compute_unit_price ,compute_total_price ," +
                "reference_unit_price ,reference_total_price ,case_label ,create_time FROM dbo" +
                ".original_new_house_case_201712_update;\n");
        System.out.println(sb.toString());
    }

    /**
     * 2018年表
     */
    @Test
    public void printInsert2018AggregationCaseTable() {
        StringBuilder sb = new StringBuilder("truncate table original_new_house_case_2018;\n");
        for (int i = 201801; i <= 201812; i++) {
            sb.append("insert into dbo.original_new_house_case_2018(case_id ,case_county ,case_block ,case_loop ," +
                    "case_community_name ,case_address ,case_area ,case_unit_price ,case_total_price ,case_house_type" +
                    " ,case_signing_date ,case_floor ,case_house_property ,case_apartment_layout ,compute_unit_price " +
                    ",compute_total_price ,reference_unit_price ,reference_total_price ,case_label ,create_time) \n" +
                    "SELECT case_id ,case_county ,case_block ,case_loop ,case_community_name ,case_address ,case_area" +
                    " ,case_unit_price ,case_total_price ,case_house_type ,case_signing_date ,case_floor ," +
                    "case_house_property ,case_apartment_layout ,compute_unit_price ,compute_total_price ," +
                    "reference_unit_price ,reference_total_price ,case_label ,create_time FROM dbo" +
                    ".original_new_house_case_" + i + ";\n");
            sb.append("insert into dbo.original_new_house_case_2018(case_id ,case_county ,case_block ,case_loop ," +
                    "case_community_name ,case_address ,case_area ,case_unit_price ,case_total_price ,case_house_type" +
                    " ,case_signing_date ,case_floor ,case_house_property ,case_apartment_layout ,compute_unit_price " +
                    ",compute_total_price ,reference_unit_price ,reference_total_price ,case_label ,create_time) \n" +
                    "SELECT case_id ,case_county ,case_block ,case_loop ,case_community_name ,case_address ,case_area" +
                    " ,case_unit_price ,case_total_price ,case_house_type ,case_signing_date ,case_floor ," +
                    "case_house_property ,case_apartment_layout ,compute_unit_price ,compute_total_price ," +
                    "reference_unit_price ,reference_total_price ,case_label ,create_time FROM dbo" +
                    ".original_new_house_case_" + i + "_update;\n");
        }
        System.out.println(sb.toString());
    }

    /**
     * 2019年表
     */
    @Test
    public void printInsert2019AggregationCaseTable() {
        StringBuilder sb = new StringBuilder("truncate table original_new_house_case_2019;\n");
        for (int i = 201901; i <= 201912; i++) {
            sb.append("insert into dbo.original_new_house_case_2019(case_id ,case_county ,case_block ,case_loop ," +
                    "case_community_name ,case_address ,case_area ,case_unit_price ,case_total_price ,case_house_type" +
                    " ,case_signing_date ,case_floor ,case_house_property ,case_apartment_layout ,compute_unit_price " +
                    ",compute_total_price ,reference_unit_price ,reference_total_price ,case_label ,create_time) \n" +
                    "SELECT case_id ,case_county ,case_block ,case_loop ,case_community_name ,case_address ,case_area" +
                    " ,case_unit_price ,case_total_price ,case_house_type ,case_signing_date ,case_floor ," +
                    "case_house_property ,case_apartment_layout ,compute_unit_price ,compute_total_price ," +
                    "reference_unit_price ,reference_total_price ,case_label ,create_time FROM dbo" +
                    ".original_new_house_case_" + i + ";\n");
            sb.append("insert into dbo.original_new_house_case_2019(case_id ,case_county ,case_block ,case_loop ," +
                    "case_community_name ,case_address ,case_area ,case_unit_price ,case_total_price ,case_house_type" +
                    " ,case_signing_date ,case_floor ,case_house_property ,case_apartment_layout ,compute_unit_price " +
                    ",compute_total_price ,reference_unit_price ,reference_total_price ,case_label ,create_time) \n" +
                    "SELECT case_id ,case_county ,case_block ,case_loop ,case_community_name ,case_address ,case_area" +
                    " ,case_unit_price ,case_total_price ,case_house_type ,case_signing_date ,case_floor ," +
                    "case_house_property ,case_apartment_layout ,compute_unit_price ,compute_total_price ," +
                    "reference_unit_price ,reference_total_price ,case_label ,create_time FROM dbo" +
                    ".original_new_house_case_" + i + "_update;\n");
        }
        System.out.println(sb.toString());
    }

    /**
     * 2020年表
     */
    @Test
    public void printInsert2020AggregationCaseTable() {
        StringBuilder sb = new StringBuilder("truncate table original_new_house_case_2020;\n");
        for (int i = 202001; i <= 202006; i++) {
            sb.append("insert into dbo.original_new_house_case_2020(case_id ,case_county ,case_block ,case_loop ," +
                    "case_community_name ,case_address ,case_area ,case_unit_price ,case_total_price ,case_house_type" +
                    " ,case_signing_date ,case_floor ,case_house_property ,case_apartment_layout ,compute_unit_price " +
                    ",compute_total_price ,reference_unit_price ,reference_total_price ,case_label ,create_time) \n" +
                    "SELECT case_id ,case_county ,case_block ,case_loop ,case_community_name ,case_address ,case_area" +
                    " ,case_unit_price ,case_total_price ,case_house_type ,case_signing_date ,case_floor ," +
                    "case_house_property ,case_apartment_layout ,compute_unit_price ,compute_total_price ," +
                    "reference_unit_price ,reference_total_price ,case_label ,create_time FROM dbo" +
                    ".original_new_house_case_" + i + ";\n");
            sb.append("insert into dbo.original_new_house_case_2020(case_id ,case_county ,case_block ,case_loop ," +
                    "case_community_name ,case_address ,case_area ,case_unit_price ,case_total_price ,case_house_type" +
                    " ,case_signing_date ,case_floor ,case_house_property ,case_apartment_layout ,compute_unit_price " +
                    ",compute_total_price ,reference_unit_price ,reference_total_price ,case_label ,create_time) \n" +
                    "SELECT case_id ,case_county ,case_block ,case_loop ,case_community_name ,case_address ,case_area" +
                    " ,case_unit_price ,case_total_price ,case_house_type ,case_signing_date ,case_floor ," +
                    "case_house_property ,case_apartment_layout ,compute_unit_price ,compute_total_price ," +
                    "reference_unit_price ,reference_total_price ,case_label ,create_time FROM dbo" +
                    ".original_new_house_case_" + i + "_update;\n");
        }
        System.out.println(sb.toString());
    }

    @Test
    public void printLoop() {
        StringBuilder sb = new StringBuilder();
        for (int i = 202001; i <= 202006; i++) {
            sb.append("insert into dbo.TLK_成交案例\n" +
                    "(\n" +
                    "    ID\n" +
                    "  , ITEM_DEALID\n" +
                    "  , ITEM_SECTOR\n" +
                    "  , ITEM_CIRCLEPOSITION\n" +
                    "  , ITEM_DISTRICT\n" +
                    "  , ITEM_RANAME\n" +
                    "  , ITEM_HOUSEADDRESS\n" +
                    "  , ITEM_AREA\n" +
                    "  , ITEM_SUMPRICE\n" +
                    "  , ITEM_UNITPRICE\n" +
                    "  , ITEM_SIGNINGDATA\n" +
                    "  , ITEM_FLOOR\n" +
                    "  , ITEM_ROOMNATURE\n" +
                    "  , ITEM_APARTMENT\n" +
                    "  , ITEM_DEALTYPE\n" +
                    "  , ITEM_CALCULATIONUNITPRICE\n" +
                    "  , ITEM_CONSULTUNITPRICE\n" +
                    "  , ITEM_CONSULTTOTALPRICE\n" +
                    "  , ITEM_YEARMONTH\n" +
                    "  , ITEM_CALCULATIONTOTALPRICE\n" +
                    "  , ITEM_HOUSETYPE\n" +
                    "  , case_id\n" +
                    ")\n" +
                    "select newid()\n" +
                    "     , replace(newid(), '-', '')\n" +
                    "     , case_block\n" +
                    "     , case_loop\n" +
                    "     , case_county\n" +
                    "     , case_community_name\n" +
                    "     , case_address\n" +
                    "     , case_area\n" +
                    "     , case_total_price\n" +
                    "     , case_unit_price\n" +
                    "     , case_signing_date\n" +
                    "     , case_floor\n" +
                    "     , case_house_property\n" +
                    "     , case_apartment_layout\n" +
                    "     , '一手'\n" +
                    "     , compute_unit_price\n" +
                    "     , reference_unit_price\n" +
                    "     , reference_total_price\n" +
                    "     , '" + (i / 100) + "-" + String.format("%02d",(i % 100)) + "'\n" +
                    "     , compute_total_price\n" +
                    "     , case_house_type\n" +
                    "     , case_id\n" +
                    "from uv_compute.dbo.original_new_house_case_" + i + ";\n");
            sb.append("insert into dbo.TLK_成交案例\n" +
                    "(\n" +
                    "    ID\n" +
                    "  , ITEM_DEALID\n" +
                    "  , ITEM_SECTOR\n" +
                    "  , ITEM_CIRCLEPOSITION\n" +
                    "  , ITEM_DISTRICT\n" +
                    "  , ITEM_RANAME\n" +
                    "  , ITEM_HOUSEADDRESS\n" +
                    "  , ITEM_AREA\n" +
                    "  , ITEM_SUMPRICE\n" +
                    "  , ITEM_UNITPRICE\n" +
                    "  , ITEM_SIGNINGDATA\n" +
                    "  , ITEM_FLOOR\n" +
                    "  , ITEM_ROOMNATURE\n" +
                    "  , ITEM_APARTMENT\n" +
                    "  , ITEM_DEALTYPE\n" +
                    "  , ITEM_CALCULATIONUNITPRICE\n" +
                    "  , ITEM_CONSULTUNITPRICE\n" +
                    "  , ITEM_CONSULTTOTALPRICE\n" +
                    "  , ITEM_YEARMONTH\n" +
                    "  , ITEM_CALCULATIONTOTALPRICE\n" +
                    "  , ITEM_HOUSETYPE\n" +
                    "  , case_id\n" +
                    ")\n" +
                    "select newid()\n" +
                    "     , replace(newid(), '-', '')\n" +
                    "     , case_block\n" +
                    "     , case_loop\n" +
                    "     , case_county\n" +
                    "     , case_community_name\n" +
                    "     , case_address\n" +
                    "     , case_area\n" +
                    "     , case_total_price\n" +
                    "     , case_unit_price\n" +
                    "     , case_signing_date\n" +
                    "     , case_floor\n" +
                    "     , case_house_property\n" +
                    "     , case_apartment_layout\n" +
                    "     , '一手'\n" +
                    "     , compute_unit_price\n" +
                    "     , reference_unit_price\n" +
                    "     , reference_total_price\n" +
                    "     , '" + (i / 100) + "-" + String.format("%02d",(i % 100)) + "'\n" +
                    "     , compute_total_price\n" +
                    "     , case_house_type\n" +
                    "     , case_id\n" +
                    "from uv_compute.dbo.original_new_house_case_" + i + "_update;\n");
        }
        System.out.println(sb.toString());
    }
}
