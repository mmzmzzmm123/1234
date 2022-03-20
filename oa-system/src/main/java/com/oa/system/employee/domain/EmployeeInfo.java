package com.oa.system.employee.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 员工信息对象 oa_employee_info
 * 
 * @author ruoyi
 * @date 2022-03-17
 */
public class EmployeeInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户id */
    private Long userId;

    /** 部门id */
    @Excel(name = "部门id")
    private Long deptId;

    /** 职位id */
    @Excel(name = "职位id")
    private Long postId;

    /** 电话 */
    @Excel(name = "电话")
    private String phone;

    /** 身份证 */
    @Excel(name = "身份证")
    private String idCard;

    /** 银行卡 */
    @Excel(name = "银行卡")
    private String bankCard;

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }
    public void setPostId(Long postId) 
    {
        this.postId = postId;
    }

    public Long getPostId() 
    {
        return postId;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setIdCard(String idCard) 
    {
        this.idCard = idCard;
    }

    public String getIdCard() 
    {
        return idCard;
    }
    public void setBankCard(String bankCard) 
    {
        this.bankCard = bankCard;
    }

    public String getBankCard() 
    {
        return bankCard;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("deptId", getDeptId())
            .append("postId", getPostId())
            .append("phone", getPhone())
            .append("idCard", getIdCard())
            .append("bankCard", getBankCard())
            .toString();
    }
}
