package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 生日溥对象 qz_brithday_pu
 * 
 * @author zlx
 * @date 2021-08-10
 */
public class QzBrithdayPu extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 生日薄ID */
    private Long id;

    /** ta的名字 */
    @Excel(name = "ta的名字")
    private String name;

    /** 用户的id */
    @Excel(name = "用户的id")
    private Long userId;

    /** 阳历生日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "阳历生日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date solarBirthday;

    /** 阴历生日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "阴历生日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthday;

    /** 是否阳历生日（0阳历，1阴历） */
    @Excel(name = "是否阳历生日", readConverterExp = "0=阳历，1阴历")
    private String solarFlag;

    /** 是否提醒（0提醒，1不提醒） */
    @Excel(name = "是否提醒", readConverterExp = "0=提醒，1不提醒")
    private String notifyFlag;

    /** 星座 */
    @Excel(name = "星座")
    private String constellation;

    /** 生肖 */
    @Excel(name = "生肖")
    private String zodiac;

    /** 年重复提醒 */
    @Excel(name = "年重复提醒")
    private String yearRepeat;

    /** 提前几天通知，复选框数据 */
    @Excel(name = "提前几天通知，复选框数据")
    private String dayRepeat;

    /** 生日溥状态（0正常 1停用） */
    private String status;

    /** 扩展字段1 */
    private String ext1;

    /** 扩展字段2 */
    private String ext2;

    /** 扩展字段3 */
    private String ext3;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setSolarBirthday(Date solarBirthday) 
    {
        this.solarBirthday = solarBirthday;
    }

    public Date getSolarBirthday() 
    {
        return solarBirthday;
    }
    public void setBirthday(Date birthday) 
    {
        this.birthday = birthday;
    }

    public Date getBirthday() 
    {
        return birthday;
    }
    public void setSolarFlag(String solarFlag) 
    {
        this.solarFlag = solarFlag;
    }

    public String getSolarFlag() 
    {
        return solarFlag;
    }
    public void setNotifyFlag(String notifyFlag) 
    {
        this.notifyFlag = notifyFlag;
    }

    public String getNotifyFlag() 
    {
        return notifyFlag;
    }
    public void setConstellation(String constellation) 
    {
        this.constellation = constellation;
    }

    public String getConstellation() 
    {
        return constellation;
    }
    public void setZodiac(String zodiac) 
    {
        this.zodiac = zodiac;
    }

    public String getZodiac() 
    {
        return zodiac;
    }
    public void setYearRepeat(String yearRepeat) 
    {
        this.yearRepeat = yearRepeat;
    }

    public String getYearRepeat() 
    {
        return yearRepeat;
    }
    public void setDayRepeat(String dayRepeat) 
    {
        this.dayRepeat = dayRepeat;
    }

    public String getDayRepeat() 
    {
        return dayRepeat;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setExt1(String ext1) 
    {
        this.ext1 = ext1;
    }

    public String getExt1() 
    {
        return ext1;
    }
    public void setExt2(String ext2) 
    {
        this.ext2 = ext2;
    }

    public String getExt2() 
    {
        return ext2;
    }
    public void setExt3(String ext3) 
    {
        this.ext3 = ext3;
    }

    public String getExt3() 
    {
        return ext3;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("userId", getUserId())
            .append("solarBirthday", getSolarBirthday())
            .append("birthday", getBirthday())
            .append("solarFlag", getSolarFlag())
            .append("notifyFlag", getNotifyFlag())
            .append("constellation", getConstellation())
            .append("zodiac", getZodiac())
            .append("yearRepeat", getYearRepeat())
            .append("dayRepeat", getDayRepeat())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("ext1", getExt1())
            .append("ext2", getExt2())
            .append("ext3", getExt3())
            .toString();
    }
}
