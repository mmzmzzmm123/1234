package com.ruoyi.office.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 微信支付配置对象 t_wx_pay
 * 
 * @author ruoyi
 * @date 2023-10-25
 */
public class TWxPay extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 商户id */
    @Excel(name = "商户id")
    private Long userId;

    /** appid */
    @Excel(name = "appid")
    private String appid;

    /** 商户号 */
    @Excel(name = "商户号")
    private String mchId;

    /** 密钥 */
    @Excel(name = "密钥")
    private String apiV3Key;

    /** 序列号 */
    @Excel(name = "序列号")
    private String certSerialNo;

    /** 私钥路径 */
    @Excel(name = "私钥路径")
    private String privateKeyPath;

    /** 私钥证书 */
    @Excel(name = "私钥证书")
    private String privateCertPath;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setAppid(String appid) 
    {
        this.appid = appid;
    }

    public String getAppid() 
    {
        return appid;
    }
    public void setMchId(String mchId) 
    {
        this.mchId = mchId;
    }

    public String getMchId() 
    {
        return mchId;
    }
    public void setApiV3Key(String apiV3Key) 
    {
        this.apiV3Key = apiV3Key;
    }

    public String getApiV3Key() 
    {
        return apiV3Key;
    }
    public void setCertSerialNo(String certSerialNo) 
    {
        this.certSerialNo = certSerialNo;
    }

    public String getCertSerialNo() 
    {
        return certSerialNo;
    }
    public void setPrivateKeyPath(String privateKeyPath) 
    {
        this.privateKeyPath = privateKeyPath;
    }

    public String getPrivateKeyPath() 
    {
        return privateKeyPath;
    }
    public void setPrivateCertPath(String privateCertPath) 
    {
        this.privateCertPath = privateCertPath;
    }

    public String getPrivateCertPath() 
    {
        return privateCertPath;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("appid", getAppid())
            .append("mchId", getMchId())
            .append("apiV3Key", getApiV3Key())
            .append("certSerialNo", getCertSerialNo())
            .append("privateKeyPath", getPrivateKeyPath())
            .append("privateCertPath", getPrivateCertPath())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
