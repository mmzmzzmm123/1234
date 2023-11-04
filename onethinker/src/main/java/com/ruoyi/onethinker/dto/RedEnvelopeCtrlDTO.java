package com.ruoyi.onethinker.dto;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.constant.AwardConstants;
import com.ruoyi.common.utils.MathUtils;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

/**
 * @author : yangyouqi
 * @date : 2023/11/3 0003 14:24
 * 活动明细数据
 */
@Data
@Log4j2
public class RedEnvelopeCtrlDTO {

    private Long id;

    /**
     * 是否可见，0为禁用，1为启用 2：归档 4:删除
     */
    @Excel(name = "是否可见，0为禁用，1为启用 2：归档 4:删除")
    private Long enabled;

    /**
     * 权重
     */
    @Excel(name = "权重")
    private Long weight;

    /**
     * 活动批次号
     */
    @Excel(name = "活动批次号")
    private String batchNo;

    /**
     * 生成总金额
     */
    @Excel(name = "生成总金额")
    private Long totalMoney;

    /**
     * 生成总数量
     */
    @Excel(name = "生成总数量")
    private Integer totalSum;

    /**
     * 生成类型（1：固定生成、2:随机生成）
     */
    @Excel(name = "生成类型", readConverterExp = "1=：固定生成、2:随机生成")
    private Integer type;

    /**
     * 领取人数
     */
    @Excel(name = "领取人数")
    private Long drawCount;

    /**
     * 领取金额
     */
    @Excel(name = "领取金额")
    private Long drawMoney;

    /**
     * 生成批次管理员id
     */
    @Excel(name = "生成批次管理员id")
    private Long createUserId;

    /**
     * 生成批次数
     */
    @Excel(name = "生成批次数")
    private Integer batchSum;

    /**
     * 是否生成幸运奖 0：不设置 1：设置
     */
    @Excel(name = "是否生成幸运奖 0：不设置 1：设置")
    private Integer luckyAward;

    /**
     * 每个批次幸运奖数
     */
    @Excel(name = "每个批次幸运奖数")
    private Integer luckyAwardCount;

    /**
     * 幸运奖金额
     */
    @Excel(name = "幸运奖金额")
    private Long luckyAwardMoney;

    /**
     * 当前批次创建明细状态 0：未生成明细数据 1：生成明细数据中 2：已生成明细数据
     */
    @Excel(name = "当前批次创建明细状态 0：未生成明细数据 1：生成明细数据中 2：已生成明细数据")
    private Integer status;

    /** 原更新状态 **/
    private Integer orgStatus;

    /** 备注**/
    private String remark;

    public void existsReqParams() {
        // 校验数据有效性
        if (StringUtils.isEmpty(batchNo)) {
            throw new RuntimeException("批次号不能为空");
        }
        if (StringUtils.isEmpty(batchSum) || batchSum <= 0) {
            throw new RuntimeException("生成批次数最小为1");
        }
        if (StringUtils.isEmpty(createUserId)) {
            throw new RuntimeException("生成管理员Id不能为空");
        }
        if (ObjectUtils.isEmpty(totalMoney) || ObjectUtils.isEmpty(totalSum) || totalMoney <= 0 || totalSum <= 0) {
            throw new RuntimeException("奖品总金额或奖品总数量不能为空");
        }
        // 做下限制，每个批次最多生成200笔的红包记录
        if (MathUtils.divide(totalSum, batchSum) > AwardConstants.AWARD_BATCH_CREATE_SUM) {
            throw new RuntimeException("分配有误，每个批次最多生成"+AwardConstants.AWARD_BATCH_CREATE_SUM+"个红包");
        }
        if (totalSum > totalMoney) {
            throw new RuntimeException("奖品金额分配不均");
        }
        if (StringUtils.isEmpty(luckyAward)) {
            luckyAward = 0;
        }
        if (AwardConstants.LUCKY_AWARD_TYPE_YES.equals(luckyAward)) {
            if (ObjectUtils.isEmpty(luckyAwardCount) || ObjectUtils.isEmpty(luckyAwardMoney)) {
                throw new RuntimeException("生成幸运奖数和幸运金额不能为空");
            }
            if (luckyAwardCount <= 0 || luckyAwardMoney <= 0) {
                throw new RuntimeException("幸运奖金额不能为空或幸运奖数不能为空");
            }
            // 校验下幸运奖总金额大于总金额
            long totalLuckyCount = MathUtils.multiply(batchSum, luckyAwardCount);
            long totalLuckyMoney = MathUtils.multiply(totalLuckyCount, luckyAwardMoney);
            if (luckyAwardMoney >= totalMoney || luckyAwardCount >= totalSum || totalLuckyCount >= totalSum || totalLuckyMoney >= totalMoney) {
                throw new RuntimeException("分配有误，幸运奖金额不能大于等于总金额或幸运奖数不能大于等于总数");
            }
        }
        if (!(AwardConstants.TYPE_FIXED.equals(type) || AwardConstants.TYPE_RANDOM.equals(type))) {
            throw new RuntimeException("请求非法，奖品生成类型有误");
        }

        // 这里只需要校验最终生成类型是否合理即可
        CalculateRedEnvelopeDTO calculateRedEnvelopeDTO = null;
        // 固定生成
        if (AwardConstants.TYPE_FIXED.equals(type)) {
            if (luckyAward.equals(AwardConstants.LUCKY_AWARD_TYPE_NO)) {
                calculateRedEnvelopeDTO = CalculateRedEnvelopeDTO.calculateRedEnvelopeBatchBaseInfo(totalMoney, totalSum, batchSum);
            } else {
                calculateRedEnvelopeDTO = CalculateRedEnvelopeDTO.calculateRedEnvelopeBatchBaseInfo(totalMoney, totalSum, batchSum, luckyAwardMoney, luckyAwardCount);
            }
        } else {
            // 随机生成
            calculateRedEnvelopeDTO = CalculateRedEnvelopeDTO.CalculateRandomRedEnvelopView(totalMoney, totalSum, batchSum, luckyAwardMoney, luckyAwardCount);
        }
        if (StringUtils.isEmpty(calculateRedEnvelopeDTO.getExistsSurplus()) || calculateRedEnvelopeDTO.getExistsSurplus() != 0) {
            log.info("最终分配的数据：{}", calculateRedEnvelopeDTO.getExistsSurplus());
            throw new RuntimeException("奖品金额分配不均");
        }
    }
}
