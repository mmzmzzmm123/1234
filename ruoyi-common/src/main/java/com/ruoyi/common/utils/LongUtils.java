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

    /**
     * 根据提供的参数随机生成一个随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 结果
     * */
    public static Long generateRandomNumber(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("最小值不能大于最大值");
        }
        Random random = new Random();
        return Long.parseLong(random.nextInt(max - min + 1) + min+"");
    }
}
