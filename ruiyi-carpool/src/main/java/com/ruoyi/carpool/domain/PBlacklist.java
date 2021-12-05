package com.ruoyi.carpool.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 黑名单对象 p_blacklist
 * 
 * @author ruoyi
 * @date 2021-12-03
 */
public class PBlacklist extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** autoID */
    private Long id;

    /** 平台唯一ID */
    @Excel(name = "平台唯一ID")
    private String userId;

    /** 用户名 */
    @Excel(name = "用户名")
    private String name;

    /** 电话 */
    @Excel(name = "电话")
    private String phone;

    /** 年龄 */
    @Excel(name = "年龄")
    private Integer age;

    /** 所在城市 */
    @Excel(name = "所在城市")
    private String city;

    /** 用户类型：1乘客；2：司机 */
    @Excel(name = "用户类型")
    private String type;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setAge(Integer age) 
    {
        this.age = age;
    }

    public Integer getAge() 
    {
        return age;
    }
    public void setCity(String city) 
    {
        this.city = city;
    }

    public String getCity() 
    {
        return city;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("name", getName())
            .append("phone", getPhone())
            .append("age", getAge())
            .append("city", getCity())
            .append("type", getType())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
