package com.ruoyi.onethinker.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
/**
 * 红包批控制对象 t_red_envelope_ctrl
 *
 * @author yangyouqi
 * @date 2023-10-31
 */
@Data
public class RedEnvelopeCtrl extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 表主键 */
    private Long id;

    /** 是否可见，0为禁用，1为启用 2：归档 4:删除 */
    @Excel(name = "是否可见，0为禁用，1为启用 2：归档 4:删除")
    private Long enabled;

    /** 权重 */
    @Excel(name = "权重")
    private Long weight;

    /** 活动批次号 */
    @Excel(name = "活动批次号")
    private String batchNo;

    /** 生成总金额 */
    @Excel(name = "生成总金额")
    private Long totalMoney;

    /** 生成总数量 */
    @Excel(name = "生成总数量")
    private Long totalSum;

    /** 生成类型（1：固定生成、2:随机生成） */
    @Excel(name = "生成类型", readConverterExp = "1=：固定生成、2:随机生成")
    private Long type;

    /** 领取人数 */
    @Excel(name = "领取人数")
    private Long drawCount;

    /** 领取金额 */
    @Excel(name = "领取金额")
    private Long drawMoney;

    /** 生成批次管理员id */
    @Excel(name = "生成批次管理员id")
    private Long createUserId;

    /** 生成批次数 */
    @Excel(name = "生成批次数")
    private Long batchSum;

    /** 是否生成幸运奖 0：不设置 1：设置 */
    @Excel(name = "是否生成幸运奖 0：不设置 1：设置")
    private Long luckyAward;

    /** 每个批次幸运奖数 */
    @Excel(name = "每个批次幸运奖数")
    private Long luckyAwardCount;

    /** 幸运奖金额 */
    @Excel(name = "幸运奖金额")
    private Long luckyAwardMoney;

    /** 创建二维码状态 0：未创建 1：创建中 2：已创建 3：创建失败 */
    @Excel(name = "创建二维码状态 0：未创建 1：创建中 2：已创建 3：创建失败")
    private Long crQrCodeStatus;


}
