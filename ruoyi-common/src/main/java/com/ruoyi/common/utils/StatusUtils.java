package com.ruoyi.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 状态机工具类 支持32位的大小
 * 一个整数二进制表示一条数据的状态 如果位为1 说明数据持有这个状态 如果是0 说明数据并没有持有这个状态
 * 2:00010 拥有2   这个状态
 * 7:00111 拥有123 三个状态
 */
public class StatusUtils {


    /**
     * 判断是否存在这个状态
     * 即某个位是否位1
     * @param curStatus
     * @param status
     * @return
     */
    public static boolean hasStatus(Integer curStatus, Integer status) {
        return (curStatus & (1 << (status - 1))) != 0;
    }


    /**
     * 统一的更新状态方法
     * 把某个位上的状态变成1
     * @param curStatus 当前
     * @param status    枚举状态
     * @return
     */
    public static Integer updateStatus(Integer curStatus, Integer status) {
        return curStatus | 1 << (status - 1); // 使用按位或操作符将状态更新到当前状态中
    }


    /**
     * 回滚方法
     * 把某个位上的状态变成0
     * @param curStatus 当前
     * @param status    枚举状态 二进制位数
     * @return
     */
    public static Integer rollBackStatus(Integer curStatus, Integer status) {
        return curStatus & ~(1 << (status - 1)); // 使用按位或操作符将状态更新到当前状态中
    }

    /**
     * 获取指定位数都是1的值
     * <p>
     * 在连续性的业务中可以使用这个函数来获取状态 并且可以有效过滤掉错误数据
     * 即你的业务 前三个状态是连续的111 可能因为一些未知的错误 变成了不连续101
     * 用这个函数获取的状态就可以有效过滤掉异常的数据
     *
     * @param bit
     * @return
     */
    public static Integer getFullBitStatus(Integer bit) {
        return (1 << bit) - 1;
    }


    /**
     * 根据位来获取int实际的值
     *
     * @param bit
     * @return
     */
    public static Integer getIntByBit(Integer bit) {
        return 1 << (bit - 1);
    }

    /**
     * 返回不包含指定状态的所有值
     *
     * @param bit              位数 一般都是枚举状态的长度
     * @param excludedStatuses 需要排除的状态
     * @return
     */
    public static List<Integer> getStatusListExcluded(int bit, Integer... excludedStatuses) {
        return getStatusListExcluded(getStatusList(bit), excludedStatuses);
    }

    /**
     * 返回不包含指定状态的所有值
     *
     * @param statusList       状态列表
     * @param excludedStatuses 不包含的状态值
     * @return
     */
    public static List<Integer> getStatusListExcluded(List<Integer> statusList, Integer... excludedStatuses) {
        for (Integer exclude : excludedStatuses) {
            statusList = filterStatusListExcluded(statusList, exclude);
        }
        return statusList;
    }

    public static List<Integer> filterStatusListExcluded(List<Integer> statusList, Integer exclude) {
        ArrayList<Integer> ans = new ArrayList<>();
        for (Integer status : statusList) {
            if (!hasStatus(status, exclude)) {
                ans.add(status);
            }
        }
        return ans;
    }

    /**
     * 与getStatusListExcluded 雷同 这里是返回包含状态的列表
     *
     * @param bit
     * @param includes
     * @return
     */
    public static List<Integer> getStatusListWithIncluded(int bit, Integer... includes) {
        return getStatusListWithIncluded(getStatusList(bit), includes);
    }


    /**
     * 返回包含指定状态的所有值
     *
     * @param includes
     * @return
     */
    public static List<Integer> getStatusListWithIncluded(List<Integer> statusList, Integer... includes) {
        for (Integer include : includes) {
            statusList = filterStatusListWithIncluded(statusList, include);
        }
        return statusList;
    }


    public static List<Integer> filterStatusListWithIncluded(List<Integer> statusList, Integer include) {
        ArrayList<Integer> ans = new ArrayList<>();
        for (Integer status : statusList) {
            if (hasStatus(status, include)) {
                ans.add(status);
            }

        }
        return ans;
    }

    /**
     * 枚举所有的可能
     *
     * @return
     */
    private static List<Integer> getStatusList(int bit) {
        ArrayList<Integer> statsList = new ArrayList<>();
        //首先枚举所有的可能
        for (int i = 0; i < (1 << bit) - 1; i++) {
            statsList.add(i + 1);
        }
        return statsList;
    }

}
