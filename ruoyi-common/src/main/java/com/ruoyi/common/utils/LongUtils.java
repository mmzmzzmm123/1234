package com.ruoyi.common.utils;

import java.util.Random;

/**
 * @author LAM
 * @date 2023/9/19 20:26
 */
public class LongUtils {


    /**
     * 生成8位推荐码
     *
     * @return 结果
     * */
    public static Long generateReferralCode() {
        // 创建一个随机数生成器
        Random random = new Random();
        // 生成8位随机数字
        long min = 10000000L;
        long max = 99999999L;
        return min + ((long) (random.nextDouble() * (max - min + 1)));
    }
}
