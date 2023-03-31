package com.geek.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.geek.common.annotation.Excel;
import com.geek.common.core.domain.BaseEntity;

/**
 * 系统APP账号对象 sys_app_account
 * 
 * @author xuek
 * @date 2023-03-29
 */
public class SysAppAccount extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 系统账号 */
    @Excel(name = "系统账号")
    private String appid;

    /** 系统名称 */
    @Excel(name = "系统名称")
    private String name;

    /** 系统秘钥 */
    @Excel(name = "系统秘钥")
    private String secret;

    /** token */
    @Excel(name = "token")
    private String token;

    /** aesKey */
    @Excel(name = "aesKey")
    private String aesKey;

    public void setId(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setAppid(String appid){
        this.appid = appid;
    }

    public String getAppid() {
        return appid;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setSecret(String secret){
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }
    public void setToken(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }
    public void setAesKey(String aesKey){
        this.aesKey = aesKey;
    }

    public String getAesKey() {
        return aesKey;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("appid", getAppid())
            .append("name", getName())
            .append("secret", getSecret())
            .append("token", getToken())
            .append("aesKey", getAesKey())
            .append("remark", getRemark())
            .toString();
    }
}
