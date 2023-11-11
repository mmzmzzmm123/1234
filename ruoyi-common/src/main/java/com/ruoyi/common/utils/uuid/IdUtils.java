package com.ruoyi.common.utils.uuid;

import java.util.Random;

/**
 * ID生成器工具类
 *
 * @author ruoyi
 */
public class IdUtils {

    /**
     * 获取随机UUID
     *
     * @return 随机UUID
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String simpleUUID() {
        return UUID.randomUUID().toString(true);
    }

    /**
     * 获取随机UUID，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 随机UUID
     */
    public static String fastUUID() {
        return UUID.fastUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String fastSimpleUUID() {
        return UUID.fastUUID().toString(true);
    }

    /**
     * 随机生成32位纯数字id
     *
     * @return 结果
     * */
    public static String randomId() {
        String str = "";
        Random random = new Random();
        for (int i = 0; i < 32; i++){
            int temp = random.nextInt(9);
            if (i != 0){
                str += temp;
            }else {
                str += temp + 1;
            }
        }
        return str;
    }

}
