package com.ruoyi.psychology.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseValObj;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 咨询订单对象 psy_consult_order
 * 
 * @author ruoyi
 * @date 2023-06-26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PsyConsultOrderVO extends BaseValObj implements Serializable
{

    private static final long serialVersionUID = 7123849582002656723L;

    /** 订单号 */
    private String orderNo;

    /** 咨询师 */
    private Long consultId;

    /** 咨询师 */
    private String consultName;

    /** 服务 */
    private Long serveId;

    /** 服务 */
    private String serveName;

    /** 客户id */
    private Long userId;
    private String userName;

    /** 排班 */
    private Long workId;

    /** 可选班次0全天 1上午 2下午 3晚上 */
    private String type;

    /** 天 */
    private String day;

    /** 周 */
    private String week;

    /** 咨询时段 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeStart;

    /** 咨询时段 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeEnd;

    /** 咨询时长(分钟) */
    private Integer time;

    /** 可预约数量 */
    private Integer num;

    /** 已预约数量 */
    private Integer buyNum;

    /** 应付费用 */
    private BigDecimal amount;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 订单状态0-待付款 1-待预约 2-待咨询 3-已完成 4-已取消 */
    private Integer status;

}
