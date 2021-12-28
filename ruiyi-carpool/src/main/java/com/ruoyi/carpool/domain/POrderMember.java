package com.ruoyi.carpool.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 【请填写功能名称】对象 p_order_member
 *
 * @author ruoyi
 * @date 2021-12-29
 */
public class POrderMember extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增ID */
    private Long id;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderNum;

    /** 微信openId */
    @Excel(name = "微信openId")
    private String openId;

    /** 用户名 */
    @Excel(name = "用户名")
    private String custName;

    /** 手机号 */
    @Excel(name = "手机号")
    private String custPhone;

    /** 乘客ID */
    @Excel(name = "乘客ID")
    private String custId;

    /** 乘客人数 */
    @Excel(name = "乘客人数")
    private Integer num;

    /** 顺序 */
    @Excel(name = "顺序")
    private Integer index;

    /** 当前状态，0：已经下单 1：取消下单 */
    @Excel(name = "当前状态，0：已经下单 1：取消下单")
    private Integer state;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setOrderNum(String orderNum)
    {
        this.orderNum = orderNum;
    }

    public String getOrderNum()
    {
        return orderNum;
    }
    public void setOpenId(String openId)
    {
        this.openId = openId;
    }

    public String getOpenId()
    {
        return openId;
    }
    public void setCustName(String custName)
    {
        this.custName = custName;
    }

    public String getCustName()
    {
        return custName;
    }
    public void setCustPhone(String custPhone)
    {
        this.custPhone = custPhone;
    }

    public String getCustPhone()
    {
        return custPhone;
    }
    public void setCustId(String custId)
    {
        this.custId = custId;
    }

    public String getCustId()
    {
        return custId;
    }
    public void setNum(Integer num)
    {
        this.num = num;
    }

    public Integer getNum()
    {
        return num;
    }
    public void setIndex(Integer index)
    {
        this.index = index;
    }

    public Integer getIndex()
    {
        return index;
    }
    public void setState(Integer state)
    {
        this.state = state;
    }

    public Integer getState()
    {
        return state;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("orderNum", getOrderNum())
                .append("openId", getOpenId())
                .append("custName", getCustName())
                .append("custPhone", getCustPhone())
                .append("custId", getCustId())
                .append("num", getNum())
                .append("index", getIndex())
                .append("state", getState())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}