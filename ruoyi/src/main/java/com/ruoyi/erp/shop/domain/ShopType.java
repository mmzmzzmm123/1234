package com.ruoyi.erp.shop.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 电商平台对象 erp_shop_type
 * 
 * @author ruoyi
 * @date 2020-06-26
 */
public class ShopType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 平台名称 */
    @Excel(name = "平台名称")
    private String name;

    /** 平台简称 */
    @Excel(name = "平台简称")
    private String shortName;

    /** 授权url */
    @Excel(name = "授权url")
    private String authUrl;

    /** 客户ID */
    @Excel(name = "客户ID")
    private String clientId;

    /** 客户密钥 */
    @Excel(name = "客户密钥")
    private String clientSecret;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /** 修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifyDate;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setShortName(String shortName) 
    {
        this.shortName = shortName;
    }

    public String getShortName() 
    {
        return shortName;
    }
    public void setCreateDate(Date createDate) 
    {
        this.createDate = createDate;
    }
    public Date getCreateDate()
    {
        return createDate;
    }

    public String getAuthUrl()
    {
        return authUrl;
    }
    public void setAuthUrl(String authUrl)
    {
        this.authUrl = authUrl;
    }

    public String getClientId()
    {
        return clientId;
    }
    public void setClientId(String clientId)
    {
        this.clientId = clientId;
    }

    public String getClientSecret() { return clientSecret; }
    public void setClientSecret(String clientSecret)
    {
        this.clientSecret = clientSecret;
    }

    public void setModifyDate(Date modifyDate) 
    {
        this.modifyDate = modifyDate;
    }

    public Date getModifyDate() 
    {
        return modifyDate;
    }

    public ShopType()
    {

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("shortName", getShortName())
            .append("authUrl", getAuthUrl())
            .append("clientId", getClientId())
            .append("clientSecret", getClientSecret())
            .append("createDate", getCreateDate())
            .append("modifyDate", getModifyDate())
            .toString();
    }
}
