package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 【请填写功能名称】对象 srchecksheet
 * 
 * @author ruoyi
 * @date 2023-12-20
 */
public class Srchecksheet extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** 供货单位 */
    @Excel(name = "供货单位")
    private String supplier;

    /** 票据号 */
    @Excel(name = "票据号")
    private String billnumber;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    /** 人民币 */
    @Excel(name = "人民币")
    private BigDecimal rmb;

    /** 付款方式:
1.现金
2.挂账
3.电汇
4.冲电汇 */
    @Excel(name = "付款方式:")
    private Integer paymentmethod;

    /** 分管经理审批人 */
    @Excel(name = "分管经理审批人")
    private Integer examine;

    /** 部门审批人 */
    @Excel(name = "部门审批人")
    private Integer section;

    /** 报账部门 */
    @Excel(name = "报账部门")
    private String reimbursement;

    /** 报账人 */
    @Excel(name = "报账人")
    private String reimburser;

    @Excel(name = "创建时间")
    private Date createtime;

    @Excel(name = "修改时间")
    private Timestamp updatetime;

    /** 状态0:正常1:删除 */
    @Excel(name = "状态0:正常1:删除")
    private Integer status;

    /** 预留字段1 */
    @Excel(name = "预留字段1")
    private String reserve1;

    /** 预留字段2 */
    @Excel(name = "预留字段2")
    private String reserve2;

    /** 预留字段3 */
    @Excel(name = "预留字段3")
    private Long reserve3;

    /** 预留字段4 */
    @Excel(name = "预留字段4")
    private Long reserve4;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSupplier(String supplier) 
    {
        this.supplier = supplier;
    }

    public String getSupplier() 
    {
        return supplier;
    }
    public void setBillnumber(String billnumber)
    {
        this.billnumber = billnumber;
    }

    public String getBillnumber()
    {
        return billnumber;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setRmb(BigDecimal rmb) 
    {
        this.rmb = rmb;
    }

    public BigDecimal getRmb() 
    {
        return rmb;
    }
    public void setPaymentmethod(Integer paymentmethod) 
    {
        this.paymentmethod = paymentmethod;
    }

    public Integer getPaymentmethod() 
    {
        return paymentmethod;
    }
    public void setExamine(Integer examine) 
    {
        this.examine = examine;
    }

    public Integer getExamine() 
    {
        return examine;
    }
    public void setSection(Integer section) 
    {
        this.section = section;
    }

    public Integer getSection() 
    {
        return section;
    }
    public void setReimbursement(String reimbursement) 
    {
        this.reimbursement = reimbursement;
    }

    public String getReimbursement() 
    {
        return reimbursement;
    }
    public void setReimburser(String reimburser) 
    {
        this.reimburser = reimburser;
    }

    public String getReimburser() 
    {
        return reimburser;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setReserve1(String reserve1) 
    {
        this.reserve1 = reserve1;
    }

    public String getReserve1() 
    {
        return reserve1;
    }
    public void setReserve2(String reserve2) 
    {
        this.reserve2 = reserve2;
    }

    public String getReserve2() 
    {
        return reserve2;
    }
    public void setReserve3(Long reserve3) 
    {
        this.reserve3 = reserve3;
    }

    public Long getReserve3() 
    {
        return reserve3;
    }
    public void setReserve4(Long reserve4) 
    {
        this.reserve4 = reserve4;
    }

    public Long getReserve4() 
    {
        return reserve4;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Timestamp getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("supplier", getSupplier())
            .append("billnumber", getBillnumber())
            .append("content", getContent())
            .append("rmb", getRmb())
            .append("paymentmethod", getPaymentmethod())
            .append("remark", getRemark())
            .append("examine", getExamine())
            .append("section", getSection())
            .append("reimbursement", getReimbursement())
            .append("reimburser", getReimburser())
            .append("createtime", getCreateTime())
            .append("status", getStatus())
            .append("updatetime", getUpdateTime())
            .append("reserve1", getReserve1())
            .append("reserve2", getReserve2())
            .append("reserve3", getReserve3())
            .append("reserve4", getReserve4())
            .toString();
    }
}
