package com.uvaluation.compute;

import com.ruoyi.project.data.cases.domain.OriginalOfficeCase;
import com.ruoyi.project.data.price.domain.ComputeResidenceRentBasePrice;
import com.ruoyi.project.data.price.domain.UltimateResidenceRentBasePrice;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.*;

public class MapperXmlGeneratorTests {

    @Test
    public void test1(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 15);
        Date startDate = calendar.getTime();
        System.out.println(startDate.toString());
        calendar.add(Calendar.MONTH, 1);
        Date endDate = calendar.getTime();
        System.out.println(endDate.toString());
    }

    @Test
    public void generateBatchInsertSqL() {
        Class targetClass = UltimateResidenceRentBasePrice .class;
        List<Field> fieldList = new ArrayList<>();
        fieldList.addAll(new ArrayList<>(Arrays.asList(targetClass.getDeclaredFields())));
//        while (targetClass != null) {
//            fieldList.addAll(new ArrayList<>(Arrays.asList(targetClass.getDeclaredFields())));
//            targetClass = targetClass.getSuperclass();
//        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            String name = fields[i].getName();
            sb.append("<result property=\""+name+"\" column=\""+name+"\"/>\r\n");
        }
        System.out.println(sb.toString());
    }

    @Test
    public void testDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(202006 / 100, 202006 % 100, 1);
        Integer lastYearMonth =  new Integer(String.format("%d%02d", calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH)-1));
        Assert.assertTrue(Objects.equals(lastYearMonth,202005));
    }

    @Test
    public void testDemo(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Integer computeTableRoute = new Integer(String.format("%d%02d", calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1));
        System.out.println(computeTableRoute);
    }

    @Test
    public void generateBatchInsertMaoHaoSqL() {
        Class targetClass = OriginalOfficeCase .class;

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
}
