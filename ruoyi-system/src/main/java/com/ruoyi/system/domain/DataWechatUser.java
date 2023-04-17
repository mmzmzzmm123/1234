package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import javax.validation.constraints.NotBlank;

/**
 * 微信用户信息对象 data_wechat_user
 * 
 * @author genius
 * @date 2023-04-17
 */
public class DataWechatUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long userId;

    /** 用户昵称 */
    @Excel(name = "用户昵称")
    private String nickName;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String phone;

    /** 头像地址 */
    @Excel(name = "头像地址")
    private String avatar;

    /** 微信openID */
    @Excel(name = "微信openID")
    @NotBlank(message = "openId不能为空")
    private String openId;

    /** 企业名称 */
    @NotBlank(message = "企业名称不能为空")
    @Excel(name = "企业名称")
    private String companyName;

    /** 统一社会信用代码 */
    @NotBlank(message = "统一社会信用代码不能为空")
    @Excel(name = "统一社会信用代码")
    private String tyshxydm;

    /** 法人姓名 */
    @NotBlank(message = "法人姓名不能为空")
    @Excel(name = "法人姓名")
    private String legalPersonName;

    /** 法人手机号码 */
    @NotBlank(message = "法人手机号码不能为空")
    @Excel(name = "法人手机号码")
    private String legalPersonPhone;

    /** 经办人姓名 */
    @NotBlank(message = "经办人姓名不能为空")
    @Excel(name = "经办人姓名")
    private String jbrName;

    /** 法人身份证号码 */
    @NotBlank(message = "法人身份证号码不能为空")
    @Excel(name = "法人身份证号码")
    private String legalPersonIdcard;

    /** 经办人身份证号码 */
    @NotBlank(message = "经办人身份证号码不能为空")
    @Excel(name = "经办人身份证号码")
    private String jbrIdcard;

    /** 经办人手机号码 */
    @NotBlank(message = "经办人手机号码不能为空")
    @Excel(name = "经办人手机号码")
    private String jbrPhone;

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setNickName(String nickName) 
    {
        this.nickName = nickName;
    }

    public String getNickName() 
    {
        return nickName;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setAvatar(String avatar) 
    {
        this.avatar = avatar;
    }

    public String getAvatar() 
    {
        return avatar;
    }
    public void setOpenId(String openId) 
    {
        this.openId = openId;
    }

    public String getOpenId() 
    {
        return openId;
    }
    public void setCompanyName(String companyName) 
    {
        this.companyName = companyName;
    }

    public String getCompanyName() 
    {
        return companyName;
    }
    public void setTyshxydm(String tyshxydm) 
    {
        this.tyshxydm = tyshxydm;
    }

    public String getTyshxydm() 
    {
        return tyshxydm;
    }
    public void setLegalPersonName(String legalPersonName) 
    {
        this.legalPersonName = legalPersonName;
    }

    public String getLegalPersonName() 
    {
        return legalPersonName;
    }
    public void setLegalPersonPhone(String legalPersonPhone) 
    {
        this.legalPersonPhone = legalPersonPhone;
    }

    public String getLegalPersonPhone() 
    {
        return legalPersonPhone;
    }
    public void setJbrName(String jbrName) 
    {
        this.jbrName = jbrName;
    }

    public String getJbrName() 
    {
        return jbrName;
    }
    public void setLegalPersonIdcard(String legalPersonIdcard) 
    {
        this.legalPersonIdcard = legalPersonIdcard;
    }

    public String getLegalPersonIdcard() 
    {
        return legalPersonIdcard;
    }
    public void setJbrIdcard(String jbrIdcard) 
    {
        this.jbrIdcard = jbrIdcard;
    }

    public String getJbrIdcard() 
    {
        return jbrIdcard;
    }
    public void setJbrPhone(String jbrPhone) 
    {
        this.jbrPhone = jbrPhone;
    }

    public String getJbrPhone() 
    {
        return jbrPhone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("nickName", getNickName())
            .append("phone", getPhone())
            .append("avatar", getAvatar())
            .append("remark", getRemark())
            .append("openId", getOpenId())
            .append("companyName", getCompanyName())
            .append("tyshxydm", getTyshxydm())
            .append("legalPersonName", getLegalPersonName())
            .append("legalPersonPhone", getLegalPersonPhone())
            .append("jbrName", getJbrName())
            .append("legalPersonIdcard", getLegalPersonIdcard())
            .append("jbrIdcard", getJbrIdcard())
            .append("jbrPhone", getJbrPhone())
            .toString();
    }
}
