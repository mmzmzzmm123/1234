package com.ruoyi.project.axsystem.domain;

import com.ruoyi.common.core.lang.UUID;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 用户基础信息对象 user_base
 * 
 * @author joy
 * @date 2020-05-18
 */
public class UserBase extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private String uid;

    /** 2正常用户 3禁言用户 4虚拟用户 5运营 */
    @Excel(name = "2正常用户 3禁言用户 4虚拟用户 5运营")
    private Integer userRole;

    /** 注册：1手机号 2邮箱 3用户名 4qq 5微信 6腾讯微博 7新浪微博 */
    @Excel(name = "注册：1手机号 2邮箱 3用户名 4qq 5微信 6腾讯微博 7新浪微博")
    private Integer registerSource;

    /** 账号 */
    @Excel(name = "账号")
    private String userName;

    /** 昵称 */
    @Excel(name = "昵称")
    private String nickName;

    /** 性别 0男1女 */
    @Excel(name = "性别 0男1女")
    private Integer gender;

    /** 生日 */
    @Excel(name = "生日")
    private Long birthday;

    /** 个人签名 */
    @Excel(name = "个人签名")
    private String signature;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String mobile;

    /** 手机号码绑定时间 */
    @Excel(name = "手机号码绑定时间")
    private Long mobileBindTime;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;

    /** 邮箱绑定时间 */
    @Excel(name = "邮箱绑定时间")
    private Long emailBindTime;

    /** 头像 */
    @Excel(name = "头像")
    private String face;

    /** 头像 200x200x80 */
    @Excel(name = "头像 200x200x80")
    private String face200;

    /** 原图头像 */
    @Excel(name = "原图头像")
    private String srcface;

    /** 用户设备 */
    @Excel(name = "用户设备")
    private String pushToken;

    public void setUid(String uid)
    {
        this.uid = uid;
    }

    public String getUid()
    {
        return uid;
    }
    public void setUserRole(Integer userRole) 
    {
        this.userRole = userRole;
    }

    public Integer getUserRole() 
    {
        return userRole;
    }
    public void setRegisterSource(Integer registerSource) 
    {
        this.registerSource = registerSource;
    }

    public Integer getRegisterSource() 
    {
        return registerSource;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setNickName(String nickName) 
    {
        this.nickName = nickName;
    }

    public String getNickName() 
    {
        return nickName;
    }
    public void setGender(Integer gender) 
    {
        this.gender = gender;
    }

    public Integer getGender() 
    {
        return gender;
    }
    public void setBirthday(Long birthday) 
    {
        this.birthday = birthday;
    }

    public Long getBirthday() 
    {
        return birthday;
    }
    public void setSignature(String signature) 
    {
        this.signature = signature;
    }

    public String getSignature() 
    {
        return signature;
    }
    public void setMobile(String mobile) 
    {
        this.mobile = mobile;
    }

    public String getMobile() 
    {
        return mobile;
    }
    public void setMobileBindTime(Long mobileBindTime) 
    {
        this.mobileBindTime = mobileBindTime;
    }

    public Long getMobileBindTime() 
    {
        return mobileBindTime;
    }
    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }
    public void setEmailBindTime(Long emailBindTime) 
    {
        this.emailBindTime = emailBindTime;
    }

    public Long getEmailBindTime() 
    {
        return emailBindTime;
    }
    public void setFace(String face) 
    {
        this.face = face;
    }

    public String getFace() 
    {
        return face;
    }
    public void setFace200(String face200) 
    {
        this.face200 = face200;
    }

    public String getFace200() 
    {
        return face200;
    }
    public void setSrcface(String srcface) 
    {
        this.srcface = srcface;
    }

    public String getSrcface() 
    {
        return srcface;
    }
    public void setPushToken(String pushToken) 
    {
        this.pushToken = pushToken;
    }

    public String getPushToken() 
    {
        return pushToken;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("uid", getUid())
            .append("userRole", getUserRole())
            .append("registerSource", getRegisterSource())
            .append("userName", getUserName())
            .append("nickName", getNickName())
            .append("gender", getGender())
            .append("birthday", getBirthday())
            .append("signature", getSignature())
            .append("mobile", getMobile())
            .append("mobileBindTime", getMobileBindTime())
            .append("email", getEmail())
            .append("emailBindTime", getEmailBindTime())
            .append("face", getFace())
            .append("face200", getFace200())
            .append("srcface", getSrcface())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("pushToken", getPushToken())
            .toString();
    }
}
