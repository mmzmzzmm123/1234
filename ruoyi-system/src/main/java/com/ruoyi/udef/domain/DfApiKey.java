package com.ruoyi.udef.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * API对象 df_api_key
 * 
 * @author ruoyi
 * @date 2022-06-09
 */
public class DfApiKey extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** platform */
    @Excel(name = "platform")
    private String platform;

    /** api名称 */
    @Excel(name = "api名称")
    private String apiName;

    /** apikey */
    @Excel(name = "apikey")
    private String apiKey;

    /** api秘钥 */
    @Excel(name = "api秘钥")
    private String apiSecret;

    /** passphrase */
    @Excel(name = "passphrase")
    private String passphrase;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPlatform(String platform) 
    {
        this.platform = platform;
    }

    public String getPlatform() 
    {
        return platform;
    }
    public void setApiName(String apiName) 
    {
        this.apiName = apiName;
    }

    public String getApiName() 
    {
        return apiName;
    }
    public void setApiKey(String apiKey) 
    {
        this.apiKey = apiKey;
    }

    public String getApiKey() 
    {
        return apiKey;
    }
    public void setApiSecret(String apiSecret) 
    {
        this.apiSecret = apiSecret;
    }

    public String getApiSecret() 
    {
        return apiSecret;
    }
    public void setPassphrase(String passphrase) 
    {
        this.passphrase = passphrase;
    }

    public String getPassphrase() 
    {
        return passphrase;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("platform", getPlatform())
            .append("apiName", getApiName())
            .append("apiKey", getApiKey())
            .append("apiSecret", getApiSecret())
            .append("passphrase", getPassphrase())
            .append("createTime", getCreateTime())
            .toString();
    }
}
