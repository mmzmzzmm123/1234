package com.uvaluation;


import com.ruoyi.project.data.price.domain.ComputeResidenceSaleBasePrice;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenerateTableTests {

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
}
