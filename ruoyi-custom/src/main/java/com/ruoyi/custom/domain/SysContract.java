package com.ruoyi.custom.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 合同对象 sys_contract
 *
 * @author wonder
 * @date 2020-10-23
 */
public class SysContract extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 合同编号
     */
    @Excel(name = "合同编号")
    private Long id;

    /**
     * 客户姓名
     */
    @Excel(name = "客户姓名")
    private String name;

    /**
     * 客户身份证
     */
    private String cusId;

    /**
     * 电话
     */
    @Excel(name = "电话")
    private String phone;

    /**
     * 服务时间
     */
    @Excel(name = "服务时间")
    private int serveTime;

    private String serveTimeStr;

    /**
     * 金额
     */
    @Excel(name = "金额")
    private BigDecimal amount;

    private String amountUpper;

    /**
     * 文件路径
     */
    @Excel(name = "文件路径")
    private String path;

    /**
     * 签约日期
     */
    private String signDate;

    /**
     * 合同状态
     * 0-未签订；1-已签订
     */
    private String Status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getServeTime() {
        return serveTime;
    }

    public void setServeTime(int serveTime) {
        this.serveTime = serveTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getAmountUpper() {
        return amountUpper;
    }

    public void setAmountUpper(String amountUpper) {
        this.amountUpper = amountUpper;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }


    public String getServeTimeStr() {
        return serveTimeStr;
    }

    public void setServeTimeStr(String serveTimeStr) {
        this.serveTimeStr = serveTimeStr;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("cusId", getCusId())
                .append("status", getStatus())
                .append("phone", getPhone())
                .append("serveTime", getServeTime())
                .append("amount", getAmount())
                .append("amountUpper", getAmountUpper())
                .append("signDate", getSignDate())
                .append("path", getPath())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
