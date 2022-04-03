package com.jlt.csa.domain;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 农场合约测试类
 */
public class FarmContractTests {

    /**
     * 计算农场合约根据生效日期、终止日期（含）计算天数的方法
     * @throws ParseException
     */
    @Test
    public void testCountDays() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        FarmerContract c = new FarmerContract();
        c.setJoinDate(simpleDateFormat.parse("2021-03-01"));
        c.setDueDate(simpleDateFormat.parse("2022-03-01"));
        Long days = c.countDays();
        System.out.println("Days: " + days);
        assert 366 == c.countDays();
    }
}
