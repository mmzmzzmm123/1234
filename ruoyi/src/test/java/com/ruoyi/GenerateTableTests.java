package com.ruoyi;


import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.data.cases.domain.*;
import com.ruoyi.project.data.price.domain.ComputeResidenceSaleBasePrice;
import com.ruoyi.project.data.price.domain.UltimateOfficeBasePrice;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

public class GenerateTableTests {

    @Test
    public void testLastYearMonth() {

        Integer lastYearMonth =  DateUtils.getLastYearMonth();
        Assert.assertTrue(202007 == lastYearMonth);

    }

    @Test
    public void getELTableColumn() {
        Class<?> targetClass = ComputeResidenceSaleBasePrice.class;

        Stream.of(targetClass.getDeclaredFields()).forEach(x -> {
            if (x.isAnnotationPresent(com.ruoyi.framework.aspectj.lang.annotation.Excel.class)) {
                com.ruoyi.framework.aspectj.lang.annotation.Excel excel =
                        x.getAnnotation(com.ruoyi.framework.aspectj.lang.annotation.Excel.class);
                System.out.println(String.format("<el-table-column label=\"%s\" align=\"center\" " +
                        "prop=\"%s\" />", excel.name(), x.getName()));
            }
        });
    }

    @Test
    public void generateUserTableType() {
        Class<?> targetClass = ComputeResidenceSaleBasePrice.class;
        Stream.of(targetClass.getDeclaredFields()).forEach(x -> {
            if (x.isAnnotationPresent(com.ruoyi.framework.aspectj.lang.annotation.Excel.class)) {
                if ("java.lang.String".equals(x.getType().getName()))
                    System.out.println(String.format("sourceDataTable.addColumnMetadata(\"%s\", java.sql.Types" +
                            ".NVARCHAR);", x.getName()));
                else if ("java.lang.Integer".equals(x.getType().getName()))
                    System.out.println(String.format("sourceDataTable.addColumnMetadata(\"%s\", java.sql.Types" +
                            ".INT);", x.getName()));
                else if ("java.math.BigDecimal".equals(x.getType().getName()))
                    System.out.println(String.format("sourceDataTable.addColumnMetadata(\"%s\", java.sql.Types" +
                            ".DECIMAL);", x.getName()));
                else if ("java.util.Date".equals(x.getType().getName()))
                    System.out.println(String.format("sourceDataTable.addColumnMetadata(\"%s\", java.sql.Types" +
                            ".DATE);", x.getName()));
            }
        });
    }

    @Test
    public void generateUserTableScript() {
        Class<?> targetClass = ComputeResidenceSaleBasePrice.class;
        Stream.of(targetClass.getDeclaredFields()).forEach(x -> {
            if (x.isAnnotationPresent(com.ruoyi.framework.aspectj.lang.annotation.Excel.class)) {
                System.out.println(x.getName() + ",");
//                System.out.println(x.getName() + " nvarchar(200) null ,");
//                if ("java.lang.String".equals(x.getType().getName()))
//                    System.out.println(String.format("sourceDataTable.addColumnMetadata(\"%s\", java.sql.Types" +
//                            ".NVARCHAR);", x.getName()));
//                else if ("java.lang.Integer".equals(x.getType().getName()))
//                    System.out.println(String.format("sourceDataTable.addColumnMetadata(\"%s\", java.sql.Types" +
//                            ".INT);", x.getName()));
//                else if ("java.math.BigDecimal".equals(x.getType().getName()))
//                    System.out.println(String.format("sourceDataTable.addColumnMetadata(\"%s\", java.sql.Types" +
//                            ".DECIMAL);", x.getName()));
//                else if ("java.util.Date".equals(x.getType().getName()))
//                    System.out.println(String.format("sourceDataTable.addColumnMetadata(\"%s\", java.sql.Types" +
//                            ".DATE);", x.getName()));
            }
        });
    }

    @Test
    public void generateBatchInsertSqL() {
        Class targetClass = OriginalResidenceRentClosingCase.class;

        List<Field> fieldList = new ArrayList<>();
        while (targetClass != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(targetClass.getDeclaredFields())));
            targetClass = targetClass.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            sb.append(":");
            sb.append(fields[i].getName());
            sb.append(",");
        }
        System.out.println(sb.toString());
    }

    @Test
    public void generateBatchGuoHaoInsertSqL() {
        Class targetClass = OriginalResidenceSaleOpeningCase.class;

        List<Field> fieldList = new ArrayList<>();
        while (targetClass != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(targetClass.getDeclaredFields())));
            targetClass = targetClass.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            sb.append("#{" + fields[i].getName() + "},");
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    @Test
    public void testContains() {
        OriginalNewHouseCase originalNewHouseCase = new OriginalNewHouseCase();
        originalNewHouseCase.setCaseAddress("明兴路18弄124号地下1层车位288室");
        if (originalNewHouseCase.getCaseAddress().contains("地下")) {
            originalNewHouseCase.setCaseLabel("车位");
        }

        Assert.assertTrue(Objects.equals("车位", originalNewHouseCase.getCaseLabel()));
    }

    @Test
    public void printLoop() {
        OriginalNewHouseCase originalNewHouseCase = new OriginalNewHouseCase();
        originalNewHouseCase.setCaseCounty("浦东");
        originalNewHouseCase.setCaseAddress("昌邑路55弄164室");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2005, 10, 16);
        originalNewHouseCase.setCaseSigningDate(calendar.getTime());
        Assert.assertEquals("4868c51874ba68c7fa2c96eeac02cde7", originalNewHouseCase.getCaseId());
    }

    @Test
    public void print2019Query() {
        for (int i = 201901; i <= 201912; i++) {
            System.out.println("SELECT * FROM dbo.一手房成交数据" + i + " where 房屋地址='联茂路80号1层';");
            System.out.println("SELECT * FROM dbo.一手房成交数据" + i + "_update where 房屋地址='联茂路80号1层';");
        }
    }

    @Test
    public void print2020Query() {
        for (int i = 202001; i <= 202006; i++) {
            System.out.println("SELECT * FROM dbo.一手房成交数据" + i + " where 房屋地址='联茂路80号1层';");
            System.out.println("SELECT * FROM dbo.一手房成交数据" + i + "_update where 房屋地址='联茂路80号1层';");
        }
    }

    @Test
    public void printLoop2() {
        for (int i = 201701; i <= 201712; i++) {
            System.out.println("SELECT * FROM dbo.original_new_house_case_" + i + " where " +
                    "case_id='52de6447d8d55974cae721c13711768b'");
        }
        for (int i = 201801; i <= 201812; i++) {
            System.out.println("SELECT * FROM dbo.original_new_house_case_" + i + " where " +
                    "case_id='52de6447d8d55974cae721c13711768b'");
        }
        for (int i = 201901; i <= 201912; i++) {
            System.out.println("SELECT * FROM dbo.original_new_house_case_" + i + " where " +
                    "case_id='52de6447d8d55974cae721c13711768b'");
        }
    }

    @Test
    public void testLastDate() {
        Integer lastYearMonth = DateUtils.getLastYearMonth();
        Assert.assertTrue(Objects.equals(202006, lastYearMonth));
    }

}
