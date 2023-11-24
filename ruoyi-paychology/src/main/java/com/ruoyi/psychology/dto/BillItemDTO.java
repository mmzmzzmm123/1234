package com.ruoyi.psychology.dto;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseValObj;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 核销记录
 *
 * @author ruoyi
 * @date 2023-06-26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BillItemDTO extends BaseValObj implements Serializable
{

    private static final long serialVersionUID = 5580739612034932774L;

    /** 核销时间 */
    @Excel(name = "核销时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date useTime;

    /** 所属咨询师 */
    @Excel(name = "所属咨询师")
    private String consultName;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderNo;

    /** 下单时间 */
    @Excel(name = "下单时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderTime;

    @Excel(name = "订单状态")
    private String status;

    @Excel(name = "订单结算状态")
    private String billStatus;

    /** 服务名称 */
    @Excel(name = "服务名称")
    private String serveName;

    /** 服务类型 */
    @Excel(name = "服务类型")
    private String serveType;

    /** 咨询次数 */
    @Excel(name = "咨询次数")
    private String buyNumStr;

    /** 服务总次数 */
    @Excel(name = "服务总次数")
    private Integer orderNum;

    /** 剩余次数 */
    @Excel(name = "剩余次数")
    private Integer num;

    /** 用户昵称 */
    @Excel(name = "用户昵称")
    private String nickName;

}
