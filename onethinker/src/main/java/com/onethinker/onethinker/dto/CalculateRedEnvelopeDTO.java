package com.onethinker.onethinker.dto;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.ruoyi.common.utils.MathUtils;
import lombok.Data;

/**
 * @Title: RedEnvelopeView
 * @Author itmei
 * @Package com.cloud.model.view.activity
 * @Date 2023/7/23 20:53
 * @description: 红包金额计算处理
 */
@Data
public class CalculateRedEnvelopeDTO {

    // 总金额
    private Long totalMoney;
    // 总数量
    private Integer totalSum;
    // 总批次
    private Integer totalBatch;
    // 每一批金额
    private Long batchMoney;
    // 每一批奖品数
    private Long batchNum;

    // 总幸运奖金额
    private Long totalLuckyAwardMoney;
    // 总幸运奖品数
    private Long totalLuckyAwardSum;
    // 每一批幸运奖奖金额
    private Long batchluckyAwardMoney;
    // 每一批幸运奖数
    private Integer batchLuckyAwardNum;

    // 总金额 % 总数量 是否为0
    private Integer existsSurplus;


    /**
     * 不存在幸运奖时，通关总金额、总数量、总批次计算每一批金额和奖品数
     */
    public static CalculateRedEnvelopeDTO calculateRedEnvelopeBatchBaseInfo(Long totalMoney, Integer totalSum, Integer totalBatch) {
        CalculateRedEnvelopeDTO calculateRedEnvelopeDTO = new CalculateRedEnvelopeDTO();
        // 基本信息
        calculateRedEnvelopeDTO.setTotalMoney(totalMoney);
        calculateRedEnvelopeDTO.setTotalSum(totalSum);
        calculateRedEnvelopeDTO.setTotalBatch(totalBatch);

        // 每一个红包多少钱
        Long batchMoney = MathUtils.divide(totalMoney,totalSum);
        // 每一批由多少个
        Long batchNum = MathUtils.divide(totalSum,totalBatch);
        // 求余校验
        Integer existsSurplus = MathUtils.remainder(totalMoney,totalSum);
        calculateRedEnvelopeDTO.setBatchMoney(batchMoney);
        calculateRedEnvelopeDTO.setBatchNum(batchNum);
        calculateRedEnvelopeDTO.setExistsSurplus(existsSurplus);
        return calculateRedEnvelopeDTO;
    }


    /**
     * 存在幸运奖时，通过总金额、总数量、总批次、幸运奖金额、幸运奖批次计算每一批金额和奖品数
     */
    public static CalculateRedEnvelopeDTO calculateRedEnvelopeBatchBaseInfo(Long totalMoney, Integer totalSum, Integer totalBatch,
                                                                            Long batchluckyAwardMoney, Integer batchluckyAwardNum) {
        CalculateRedEnvelopeDTO calculateRedEnvelopeDTO = new CalculateRedEnvelopeDTO();
        // 基本信息
        calculateRedEnvelopeDTO.setTotalMoney(totalMoney);
        calculateRedEnvelopeDTO.setTotalSum(totalSum);
        calculateRedEnvelopeDTO.setTotalBatch(totalBatch);
        calculateRedEnvelopeDTO.setBatchluckyAwardMoney(batchluckyAwardMoney);
        calculateRedEnvelopeDTO.setBatchLuckyAwardNum(batchluckyAwardNum);

        // 幸运奖总数
        long totalLuckyAwardSum = MathUtils.multiply(totalBatch , batchluckyAwardNum);
        // 幸运奖总金额
        long totalLuckyAwardMoney = MathUtils.multiply(totalLuckyAwardSum , batchluckyAwardMoney);
        // 总剩余数量： 总数量 - 幸运奖数
        long surplusSum = MathUtils.subtract(totalSum , totalLuckyAwardSum);
        if (surplusSum < 0) {
            throw new RuntimeException("红包分配有误，幸运奖数大于红包总数");
        }
        // 总剩余金额： 总金额 - 幸运奖总金额
        long surplusMoney = MathUtils.subtract(totalMoney , totalLuckyAwardMoney);
        if (surplusMoney < 0) {
            throw new RuntimeException("红包分配有误，幸运奖金额大于红包总金额");
        }
        // 每一批有多少个(去幸运奖品数)
        Long batchNum = MathUtils.divide(surplusSum,totalBatch);
        // 每一个可分配到的金额(去幸运奖品金额)
        Long batchMoney = MathUtils.divide(surplusMoney,surplusSum);
        // 求余校验 (去幸运奖)
        Integer existsSurplus = MathUtils.remainder(surplusMoney,surplusSum);

        calculateRedEnvelopeDTO.setBatchMoney(batchMoney);
        calculateRedEnvelopeDTO.setBatchNum(batchNum);
        calculateRedEnvelopeDTO.setTotalLuckyAwardMoney(totalLuckyAwardMoney);
        calculateRedEnvelopeDTO.setTotalLuckyAwardSum(totalLuckyAwardSum);
        calculateRedEnvelopeDTO.setExistsSurplus(existsSurplus);

        return calculateRedEnvelopeDTO;
    }

    /**
     * 计算随机数据
     * @param totalMoney
     * @param totalSum
     * @param totalBatch
     * @param batchluckyAwardMoney
     * @param batchluckyAwardNum
     * @return
     */
    public static CalculateRedEnvelopeDTO CalculateRandomRedEnvelopView(Long totalMoney, Integer totalSum, Integer totalBatch, Long batchluckyAwardMoney, Integer batchluckyAwardNum) {

        CalculateRedEnvelopeDTO calculateRedEnvelopeDTO = new CalculateRedEnvelopeDTO();
        // 基本信息
        calculateRedEnvelopeDTO.setTotalMoney(totalMoney);
        calculateRedEnvelopeDTO.setTotalSum(totalSum);
        calculateRedEnvelopeDTO.setTotalBatch(totalBatch);
        calculateRedEnvelopeDTO.setBatchluckyAwardMoney(batchluckyAwardMoney);
        calculateRedEnvelopeDTO.setBatchLuckyAwardNum(batchluckyAwardNum);
        // 幸运奖总数
        long totalLuckyAwardSum = MathUtils.multiply(totalBatch , batchluckyAwardNum);
        // 幸运奖总金额
        long totalLuckyAwardMoney = MathUtils.multiply(totalLuckyAwardSum , batchluckyAwardMoney);
        // 总剩余数量： 总数量 - 幸运奖数
        long surplusSum = MathUtils.subtract(totalSum , totalLuckyAwardSum);
        if (surplusSum < 0) {
            throw new RuntimeException("红包分配有误，幸运奖数大于红包总数");
        }
        // 总剩余金额： 总金额 - 幸运奖总金额
        long surplusMoney = MathUtils.subtract(totalMoney , totalLuckyAwardMoney);
        if (surplusMoney < 0) {
            throw new RuntimeException("红包分配有误，幸运奖金额大于红包总金额");
        }
        // 每一批有多少个(去幸运奖品数)
        Long batchNum = MathUtils.divide(surplusSum,totalBatch);
        // 这里只要校验下扣除了幸运奖后的金额 大于 总数量即可
        Integer existsSurplus = MathUtils.subtract(surplusMoney,surplusSum) > 0 ? 0 : 1;

        // 这里是随机生成，所以这里就不赋值操作了
//        calculateRedEnvelopeView.setBatchMoney(batchMoney);
        calculateRedEnvelopeDTO.setBatchNum(batchNum);
        calculateRedEnvelopeDTO.setTotalLuckyAwardMoney(totalLuckyAwardMoney);
        calculateRedEnvelopeDTO.setTotalLuckyAwardSum(totalLuckyAwardSum);
        calculateRedEnvelopeDTO.setExistsSurplus(existsSurplus);


        return calculateRedEnvelopeDTO;
    }

    /**
     *
     * 计算幸运奖数
     * @param batchNum : 批次数
     * @param luckyAwardCount: 幸运数
     * @return
     */
    public static Set<Integer> calculatedLuckyPosition(Long batchNum, Integer luckyAwardCount) {
        Set<Integer> luckyPositions = new HashSet<>();

        if (luckyAwardCount >= batchNum) {
            // 幸运数大于等于批次数的话，那么当前批次所有数据都是幸运奖
            for (Integer i = 0; i < batchNum; i++) {
                luckyPositions.add(i + 1);
            }
        } else {
            for (Integer i = 0; i < luckyAwardCount; i++) {
                Random random = new Random();
                int value = random.nextInt(Integer.parseInt(String.valueOf(batchNum)));
                if (luckyPositions.contains(value)) {
                    // 加入随机到0 或者原数据存在相关数据的话，那么需要重新循环处理
                    luckyAwardCount = luckyAwardCount + 1;
                    continue;
                }
                luckyPositions.add(value);
            }
        }
        return luckyPositions;
    }

    public static void main(String[] args) {

        System.out.println(calculatedLuckyPosition(4L,3));
    }
}
