package com.ruoyi.carpool.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 司机信息对象 p_driver
 * 
 * @author ruoyi
 * @date 2021-12-03
 */
public class PDriver extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** autoID */
    private Long id;

    /** 平台唯一ID */
    @Excel(name = "平台唯一ID")
    private String driverId;

    /** 司机姓名 */
    @Excel(name = "司机姓名")
    private String name;

    /** 性别：0女性1男性 */
    @Excel(name = "性别：0女性1男性")
    private String sex;

    /** 身份证号码 */
    @Excel(name = "身份证号码")
    private String idCard;

    /** 年龄 */
    @Excel(name = "年龄")
    private Integer age;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String numberPlate;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String phone;

    /** 所在城市 */
    @Excel(name = "所在城市")
    private String city;

    /** 所在省份 */
    @Excel(name = "所在省份")
    private String province;

    /** 所在国家 */
    @Excel(name = "所在国家")
    private String country;

    /** 是否黑名单用户：0否，1是 */
    @Excel(name = "是否黑名单用户：0否，1是")
    private String isBlacklist;

    /** 当前状态 */
    @Excel(name = "用户当前的状态：0：审核中，1：审核通过，2：禁用")
    private String state ;

    /** 当前状态 */
    @Excel(name = "微信openId")
    private String openId ;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setDriverId(String driverId) 
    {
        this.driverId = driverId;
    }

    public String getDriverId() 
    {
        return driverId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getSex()
    {
        return sex;
    }
    public void setIdCard(String idCard) 
    {
        this.idCard = idCard;
    }

    public String getIdCard() 
    {
        return idCard;
    }
    public void setAge(Integer age) 
    {
        this.age = age;
    }

    public Integer getAge() 
    {
        return age;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
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
    public void setIsBlacklist(String isBlacklist)
    {
        this.isBlacklist = isBlacklist;
    }

    public String getIsBlacklist()
    {
        return isBlacklist;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("driverId", getDriverId())
            .append("name", getName())
            .append("sex", getSex())
            .append("idCard", getIdCard())
            .append("age", getAge())
            .append("phone", getPhone())
            .append("city", getCity())
            .append("province", getProvince())
            .append("country", getCountry())
            .append("isBlacklist", getIsBlacklist())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("state", getState())
            .append("numberPlate", getNumberPlate())
            .append("OpenId", getOpenId())
            .toString();
    }
}
