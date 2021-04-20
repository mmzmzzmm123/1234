package com.stdiet.custom.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;

/**
 * 客户信息对象 sys_customer
 *
 * @author xzj
 * @date 2020-12-31
 */
@Data
public class SysCustomer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    //加密ID
    private String encId;

    /** 名字 */
    @Excel(name = "名字")
    private String name;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 进粉日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "进粉日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date fansTime;

    private Integer fansChannel;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 付款日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "付款日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date payDate;

    /** 开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 购买月数 */
    @Excel(name = "购买月数")
    private Long purchaseNum;

    /** 累计总金额 */
    @Excel(name = "累计总金额")
    private BigDecimal payTotal;

    /** 主营养师 */
    @Excel(name = "主营养师")
    private Long mainDietitian;

    /** 营养师助理 */
    @Excel(name = "营养师助理")
    private Long assistantDietitian;

    /** 售后营养师 */
    @Excel(name = "售后营养师")
    private Long afterDietitian;

    /** 销售人员 */
    @Excel(name = "销售人员")
    private Long salesman;

    /** 负责人 */
    @Excel(name = "负责人")
    private Long chargePerson;

    /** 跟进状态 */
    @Excel(name = "跟进状态")
    private Long followStatus;

    /** 删除标识(0 未删除 1已删除) */
    private Integer delFlag;

    /** 体征数据，非持久化字段 */
    private SysCustomerPhysicalSigns sign;

    @Excel(name = "进粉渠道")
    private Long channelId;

    private String[] channels;

}