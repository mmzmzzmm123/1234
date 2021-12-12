package com.ruoyi.carpool.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.omg.CORBA.INTERNAL;

/**
 * 乘客信息对象 p_passenger
 * 
 * @author ruoyi
 * @date 2021-12-03
 */
public class PPassenger extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** autoID */
    private Long id;

    /** 用户平台唯一ID */
    @Excel(name = "用户平台唯一ID")
    private String custId;

    /** 用户名 */
    @Excel(name = "用户名")
    private String custName;

    /** 微信昵称 */
    @Excel(name = "微信昵称")
    private String nickName;

    /** 微信用户唯一标识 */
    @Excel(name = "微信用户唯一标识")
    private String openId;

    /** 用户手机号码 */
    @Excel(name = "用户手机号码")
    private String custPhone;

    /** 性别：1男性，0女性 */
    @Excel(name = "性别：1男性，0女性")
    private String sex;

    /** 身份证号码 */
    @Excel(name = "身份证号码")
    private String idCard;

    /** 生日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthday;

    /** 所在城市 */
    @Excel(name = "所在城市")
    private String city;

    /** 年龄 */
    @Excel(name = "年龄")
    private Integer age;

    /** 所在省份 */
    @Excel(name = "所在省份")
    private String province;

    /** 是否黑名单用户：0否，1是s */
    @Excel(name = "是否黑名单用户：0否，1是s")
    private String isBlacklist;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
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
    public void setOpenId(String openId) 
    {
        this.openId = openId;
    }

    public String getOpenId() 
    {
        return openId;
    }
    public void setCustPhone(String custPhone) 
    {
        this.custPhone = custPhone;
    }

    public String getCustPhone() 
    {
        return custPhone;
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
    public void setIsBlacklist(String isBlacklist)
    {
        this.isBlacklist = isBlacklist;
    }

    public String getIsBlacklist()
    {
        return isBlacklist;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("custId", getCustId())
            .append("custName", getCustName())
            .append("nickName", getNickName())
            .append("openId", getOpenId())
            .append("custPhone", getCustPhone())
            .append("sex", getSex())
            .append("birthday", getBirthday())
            .append("city", getCity())
            .append("province", getProvince())
            .append("isBlacklist", getIsBlacklist())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("age", getAge())
            .append("idCard", getIdCard())
            .toString();
    }
}
