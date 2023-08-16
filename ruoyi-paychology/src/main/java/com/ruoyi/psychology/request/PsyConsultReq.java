package com.ruoyi.psychology.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseValObj;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 咨询订单对象 psy_consult_order
 * 
 * @author ruoyi
 * @date 2023-06-26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PsyConsultReq extends BaseValObj implements Serializable
{

    private static final long serialVersionUID = 7980447039917688534L;

    /** 登录名 */
    private String userName;

    /** 用户性别（0男 1女 2未知） */
    private String sex;

    /** 城市 */
    private String city;

    /** 省份 */
    private String province;

    /** 咨询方向 */
    private Set<String> way;

    /** 咨询价格 */
    private BigDecimal lowPrice;
    private BigDecimal highPrice;

    /** 咨询服务 */
    private String serve;

    /** 咨询时段 */
    private List<String> days;

    /** 可选时段 */
    private String type;

    private String time;

    /** 服务 */
    private Long serveId;

    /** 今日可约 */
    private String buy;

    /** 0-and 1-or */
    private String nand;

}
