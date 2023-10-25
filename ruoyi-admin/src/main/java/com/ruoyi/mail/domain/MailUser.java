package com.ruoyi.mail.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 邮件用户列表对象 mail_user
 * 
 * @author ruoyi
 * @date 2023-10-21
 */
public class MailUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String mailAccount;

    /** 1 发送晚安消息 */
    @Excel(name = "1 发送晚安消息")
    private Long mailType;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String name;

    /** 0 无问题   1 删除 */
    @Excel(name = "0 无问题   1 删除")
    private Long isDelete;

    /** 学习计划人员 */
    @Excel(name = "学习计划人员")
    private Long type;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMailAccount(String mailAccount) 
    {
        this.mailAccount = mailAccount;
    }

    public String getMailAccount() 
    {
        return mailAccount;
    }
    public void setMailType(Long mailType) 
    {
        this.mailType = mailType;
    }

    public Long getMailType() 
    {
        return mailType;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setIsDelete(Long isDelete) 
    {
        this.isDelete = isDelete;
    }

    public Long getIsDelete() 
    {
        return isDelete;
    }
    public void setType(Long type) 
    {
        this.type = type;
    }

    public Long getType() 
    {
        return type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("mailAccount", getMailAccount())
            .append("mailType", getMailType())
            .append("name", getName())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("isDelete", getIsDelete())
            .append("type", getType())
            .toString();
    }
}
