package com.ruoyi.wxapi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 小程序设置对象 wx_app
 * 
 * @author tgq
 * @date 2021-09-01
 */
public class WxApp extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 小程序代号 */
    @Excel(name = "小程序代号")
    private String appCode;

    /** 小程序名称 */
    @Excel(name = "小程序名称")
    private String appName;

    /** 小程序id */
    @Excel(name = "小程序id")
    private String appId;

    /** 小程序secret */
    private String secret;

    /** 服务器域名 */
    @Excel(name = "服务器域名")
    private String apiDomain;

    /** 状态(1:正常,2:停用) */
    @Excel(name = "状态(1:正常,2:停用)")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setAppCode(String appCode) 
    {
        this.appCode = appCode;
    }

    public String getAppCode() 
    {
        return appCode;
    }
    public void setAppName(String appName) 
    {
        this.appName = appName;
    }

    public String getAppName() 
    {
        return appName;
    }
    public void setAppId(String appId) 
    {
        this.appId = appId;
    }

    public String getAppId() 
    {
        return appId;
    }
    public void setSecret(String secret) 
    {
        this.secret = secret;
    }

    public String getSecret() 
    {
        return secret;
    }
    public void setApiDomain(String apiDomain) 
    {
        this.apiDomain = apiDomain;
    }

    public String getApiDomain() 
    {
        return apiDomain;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("appCode", getAppCode())
            .append("appName", getAppName())
            .append("appId", getAppId())
            .append("secret", getSecret())
            .append("apiDomain", getApiDomain())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
