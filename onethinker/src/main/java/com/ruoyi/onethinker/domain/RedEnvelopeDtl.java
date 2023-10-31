package com.ruoyi.onethinker.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
/**
 * 红包明细对象 t_red_envelope_dtl
 *
 * @author yangyouqi
 * @date 2023-10-31
 */
@Data
public class RedEnvelopeDtl extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 表主键 */
    private Long id;

    /** 是否可见，0为未领取，1为已领取 2：归档 */
    @Excel(name = "是否可见，0为未领取，1为已领取 2：归档")
    private Long enabled;

    /** 权重 */
    @Excel(name = "权重")
    private Long weight;

    /** 活动批次号 */
    @Excel(name = "活动批次号")
    private String batchNo;

    /** 金额 */
    @Excel(name = "金额")
    private Long money;

    /** 是否为幸运奖 */
    @Excel(name = "是否为幸运奖")
    private Long luckyAwardType;

    /** 当前批数 */
    @Excel(name = "当前批数")
    private Long batch;

    /** 二维码地址 */
    @Excel(name = "二维码地址")
    private String qrCodeUrl;

    /** 创建二维码状态  0：初始化 1：正在执行 2:创建成功 3：创建失败 */
    @Excel(name = "创建二维码状态  0：初始化 1：正在执行 2:创建成功 3：创建失败")
    private Long qrCodeStatus;


}
