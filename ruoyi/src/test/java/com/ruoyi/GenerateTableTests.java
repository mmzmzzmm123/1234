package com.ruoyi;


import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LoadUtil;
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

        Integer lastYearMonth = DateUtils.getLastYearMonth();
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

    @Test
    public void testAppend() {
        StringBuffer sb = new StringBuffer();
        sb.append(String.valueOf('a'), 0, 1);
        sb.insert(0, "b");
        sb.insert(0, "c");
        System.out.println(sb.toString());
        Assert.assertEquals("cba", sb.toString());
    }

    @Test
    public void testDemo() {
        String s = "Let's take LeetCode contest";
        int length = s.length();
        StringBuffer sb = new StringBuffer(s.length());
        int startIndex = 0;
        for (int i = 0; i < length; i++) {
            char ch = s.charAt(i);
            if (ch == ' ') {
                startIndex = i + 1;
                sb.append(" ");
            } else {
                sb.insert(startIndex, ch);
            }
        }

        Assert.assertEquals("s'teL ekat edoCteeL tsetnoc", sb.toString());

    }

    @Test
    public void testSplit() {
        String s = "";

        String[] ss = s.split(" ");
        StringBuffer sb = new StringBuffer();

        for (String item :
                ss) {
            sb.append(reverseString(item.toCharArray()));
            sb.append(" ");
        }
        sb.substring(0, sb.length() - 1);
    }

    private String reverseString(char[] chs) {
        String str = "";

        for (int i = 0; i < chs.length; i++) {
            str = chs[i] + str;
        }
        return str;
    }



    /**
     *
     */
    @Test
    public void testPrintLoop() {

        /**
         * 成交案例总数
         * 成交套数
         * 成交价格
         */
        List<String> list = new LinkedList<>();
        list.add("425280");
        list.add("428770");
        list.add("427490");
        list.add("428790");
        list.add("427780");
        list.add("430050");
        list.add("425028");
        list.add("429710");
        list.add("425700");
        for (int j = 0; j < list.size(); j++) {

            System.out.println("select " + list.get(j) + " as community_id , sum(count) as count,sum(total_price) as " +
                    "total_price," +
                    "avg" +
                    "(avg_price) as avg_price ,sum(total_area) as total_area,avg(avg_area) as avg_area from (");

            for (int i = 201901; i <= 201906; i++) {
                System.out.println("select COUNT(1) " +
                        "as count" +
                        " ,sum" +
                        "(PriceTotal) as total_price ,avg" +
                        "(PriceTotal) as avg_price," +
                        "sum(Area) as total_area ,avg" +
                        "(Area) as avg_area from " +
                        "ODS_HOUSINGCASE_DEAL_" + i + " where ProjectID=" + list.get(j));
                if (i != 201906)
                    System.out.println("union");
            }
            System.out.println(") as t;\n");
        }

    }

    @Test
    public void printUUID() {
        System.out.println(UUID.randomUUID().toString().replace("-", ""));
    }

    @Test
    public void appendDouHao() {
        List<String> list = LoadUtil.loadList("temp.dict");

        list.forEach(x -> {
            int index = x.indexOf("大道");
            if(-1 != index) {
                System.out.println(x.substring(0,index+2));
            }
        });

    }

}
