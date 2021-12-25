package com.ruoyi.carpool.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 微信注册用户信息对象 p_member
 * 
 * @author ruoyi
 * @date 2021-12-25
 */
public class PMember extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增id */
    private Integer id;

    /** 平台编号 */
    @Excel(name = "平台编号")
    private String custId;

    /** 用户类型 */
    @Excel(name = "用户类型")
    private String custType;

    /** 微信唯一标识 */
    @Excel(name = "微信唯一标识")
    private String openId;

    /** 微信unionId */
    @Excel(name = "微信unionId")
    private Long unionId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String custName;

    /** 微信昵称 */
    @Excel(name = "微信昵称")
    private String nickName;

    /** 手机号1 */
    @Excel(name = "手机号1")
    private String custPhoneA;

    /** 手机号2 */
    @Excel(name = "手机号2")
    private String custPhoneB;

    /** 性别 */
    @Excel(name = "性别")
    private String sex;

    /** 生日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthday;

    /** 所在城市 */
    @Excel(name = "所在城市")
    private String city;

    /** 所在省份 */
    @Excel(name = "所在省份")
    private String province;

    /** 所在国家 */
    @Excel(name = "所在国家")
    private String country;

    /** 用户头像 */
    @Excel(name = "用户头像")
    private String headimgurl;

    /** 证件类型 */
    @Excel(name = "证件类型")
    private String idType;

    /** 证件号 */
    @Excel(name = "证件号")
    private String idNo;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setCustId(String custId) 
    {
        this.custId = custId;
    }

    public String getCustId() 
    {
        return custId;
    }
    public void setCustType(String custType) 
    {
        this.custType = custType;
    }

    public String getCustType() 
    {
        return custType;
    }
    public void setOpenId(String openId) 
    {
        this.openId = openId;
    }

    public String getOpenId() 
    {
        return openId;
    }
    public void setUnionId(Long unionId) 
    {
        this.unionId = unionId;
    }

    public Long getUnionId() 
    {
        return unionId;
    }
    public void setCustName(String custName) 
    {
        this.custName = custName;
    }

    public String getCustName() 
    {
        return custName;
    }
    public void setNickName(String nickName) 
    {
        this.nickName = nickName;
    }

    public String getNickName() 
    {
        return nickName;
    }
    public void setCustPhoneA(String custPhoneA) 
    {
        this.custPhoneA = custPhoneA;
    }

    public String getCustPhoneA() 
    {
        return custPhoneA;
    }
    public void setCustPhoneB(String custPhoneB) 
    {
        this.custPhoneB = custPhoneB;
    }

    public String getCustPhoneB() 
    {
        return custPhoneB;
    }
    public void setSex(String sex) 
    {
        this.sex = sex;
    }

    public String getSex() 
    {
        return sex;
    }
    public void setBirthday(Date birthday) 
    {
        this.birthday = birthday;
    }

    public Date getBirthday() 
    {
        return birthday;
    }
    public void setCity(String city) 
    {
        this.city = city;
    }

    public String getCity() 
    {
        return city;
    }
    public void setProvince(String province) 
    {
        this.province = province;
    }

    public String getProvince() 
    {
        return province;
    }
    public void setCountry(String country) 
    {
        this.country = country;
    }

    public String getCountry() 
    {
        return country;
    }
    public void setHeadimgurl(String headimgurl) 
    {
        this.headimgurl = headimgurl;
    }

    public String getHeadimgurl() 
    {
        return headimgurl;
    }
    public void setIdType(String idType) 
    {
        this.idType = idType;
    }

    public String getIdType() 
    {
        return idType;
    }
    public void setIdNo(String idNo) 
    {
        this.idNo = idNo;
    }

    public String getIdNo() 
    {
        return idNo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("custId", getCustId())
            .append("custType", getCustType())
            .append("openId", getOpenId())
            .append("unionId", getUnionId())
            .append("custName", getCustName())
            .append("nickName", getNickName())
            .append("custPhoneA", getCustPhoneA())
            .append("custPhoneB", getCustPhoneB())
            .append("sex", getSex())
            .append("birthday", getBirthday())
            .append("city", getCity())
            .append("province", getProvince())
            .append("country", getCountry())
            .append("headimgurl", getHeadimgurl())
            .append("idType", getIdType())
            .append("idNo", getIdNo())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
