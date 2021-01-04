package com.stdiet.custom.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 客户数据对象 sys_customer_physical_signs
 *
 * @author xzj
 * @date 2020-12-31
 */
public class SysCustomerPhysicalSigns extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 客户id */
    @Excel(name = "客户id")
    private Long customerId;

    /** 客户性别  0男 1女 */
    @Excel(name = "客户性别  0男 1女")
    private Integer sex;

    /** 客户年龄（岁） */
    @Excel(name = "客户年龄", readConverterExp = "岁=")
    private Integer age;

    /** 客户身高（厘米） */
    @Excel(name = "客户身高", readConverterExp = "厘=米")
    private Integer tall;

    /** 客户体重（斤） */
    @Excel(name = "客户体重", readConverterExp = "斤=")
    private Integer weight;

    /** 客户病史体征id */
    @Excel(name = "客户病史体征id")
    private String physicalSignsId;

    /** 客户忌口不爱吃食材id */
    @Excel(name = "客户忌口不爱吃食材id")
    private String dishesIngredientId;

    /** 客户照片 */
    @Excel(name = "客户照片")
    private String photo;

    /** 是否便秘  0是 1否 */
    @Excel(name = "是否便秘  0是 1否")
    private Integer constipation;

    /** 是否熬夜、失眠 0是 1否 */
    @Excel(name = "是否熬夜、失眠 0是 1否")
    private Integer staylate;

    /** 是否经常运动 0是 1否 */
    @Excel(name = "是否经常运动 0是 1否")
    private Integer motion;

    /** 饮食方式  0自己做 1外面吃 */
    @Excel(name = "饮食方式  0自己做 1外面吃")
    private Integer makeFoodType;

    /** 饮食特点 0清淡 1重口味 */
    @Excel(name = "饮食特点 0清淡 1重口味")
    private Integer makeFoodTaste;

    /** 平时是否久坐  0久坐多 1走动多 */
    @Excel(name = "平时是否久坐  0久坐多 1走动多")
    private Integer walk;

    /** 减脂过程遇到的困难 */
    @Excel(name = "减脂过程遇到的困难")
    private String difficulty;

    /** 是否浑身乏力 0是 1否 */
    @Excel(name = "是否浑身乏力 0是 1否")
    private Integer weakness;

    /** 是否减脂反弹 0是 1否 */
    @Excel(name = "是否减脂反弹 0是 1否")
    private Integer rebound;

    /** 能否认识到生活习惯的改善才是减脂的关键 0是 1否 */
    @Excel(name = "能否认识到生活习惯的改善才是减脂的关键 0是 1否")
    private Integer crux;

    /** 南方人还是北方人 0南方 1北方 */
    @Excel(name = "南方人还是北方人 0南方 1北方")
    private Integer position;

    /** 睡觉时间（24小时制） */
    @Excel(name = "睡觉时间", readConverterExp = "2=4小时制")
    private Integer sleepTime;

    /** 起床时间（24小时制） */
    @Excel(name = "起床时间", readConverterExp = "2=4小时制")
    private Integer getupTime;

    /** 联系沟通时间（24小时制） */
    @Excel(name = "联系沟通时间", readConverterExp = "2=4小时制")
    private Integer connectTime;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 湿气数据 */
    @Excel(name = "湿气数据")
    private String bloodData;

    /** 气血数据 */
    @Excel(name = "气血数据")
    private String moistureDate;

    /** 工作职业 */
    @Excel(name = "工作职业")
    private String vocation;

    /** 是否上夜班 */
    @Excel(name = "是否上夜班")
    private Integer night;

    /** 减脂经历 */
    @Excel(name = "减脂经历")
    private String experience;

    /** 体征对象集合 **/
    private List<SysPhysicalSigns> signList;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setCustomerId(Long customerId)
    {
        this.customerId = customerId;
    }

    public Long getCustomerId()
    {
        return customerId;
    }
    public void setSex(Integer sex)
    {
        this.sex = sex;
    }

    public Integer getSex()
    {
        return sex;
    }
    public void setAge(Integer age)
    {
        this.age = age;
    }

    public Integer getAge()
    {
        return age;
    }
    public void setTall(Integer tall)
    {
        this.tall = tall;
    }

    public Integer getTall()
    {
        return tall;
    }
    public void setWeight(Integer weight)
    {
        this.weight = weight;
    }

    public Integer getWeight()
    {
        return weight;
    }
    public void setPhysicalSignsId(String physicalSignsId)
    {
        this.physicalSignsId = physicalSignsId;
    }

    public String getPhysicalSignsId()
    {
        return physicalSignsId;
    }
    public void setDishesIngredientId(String dishesIngredientId)
    {
        this.dishesIngredientId = dishesIngredientId;
    }

    public String getDishesIngredientId()
    {
        return dishesIngredientId;
    }
    public void setPhoto(String photo)
    {
        this.photo = photo;
    }

    public String getPhoto()
    {
        return photo;
    }
    public void setConstipation(Integer constipation)
    {
        this.constipation = constipation;
    }

    public Integer getConstipation()
    {
        return constipation;
    }
    public void setStaylate(Integer staylate)
    {
        this.staylate = staylate;
    }

    public Integer getStaylate()
    {
        return staylate;
    }
    public void setMotion(Integer motion)
    {
        this.motion = motion;
    }

    public Integer getMotion()
    {
        return motion;
    }
    public void setMakeFoodType(Integer makeFoodType)
    {
        this.makeFoodType = makeFoodType;
    }

    public Integer getMakeFoodType()
    {
        return makeFoodType;
    }
    public void setMakeFoodTaste(Integer makeFoodTaste)
    {
        this.makeFoodTaste = makeFoodTaste;
    }

    public Integer getMakeFoodTaste()
    {
        return makeFoodTaste;
    }
    public void setWalk(Integer walk)
    {
        this.walk = walk;
    }

    public Integer getWalk()
    {
        return walk;
    }
    public void setDifficulty(String difficulty)
    {
        this.difficulty = difficulty;
    }

    public String getDifficulty()
    {
        return difficulty;
    }
    public void setWeakness(Integer weakness)
    {
        this.weakness = weakness;
    }

    public Integer getWeakness()
    {
        return weakness;
    }
    public void setRebound(Integer rebound)
    {
        this.rebound = rebound;
    }

    public Integer getRebound()
    {
        return rebound;
    }
    public void setCrux(Integer crux)
    {
        this.crux = crux;
    }

    public Integer getCrux()
    {
        return crux;
    }
    public void setPosition(Integer position)
    {
        this.position = position;
    }

    public Integer getPosition()
    {
        return position;
    }
    public void setSleepTime(Integer sleepTime)
    {
        this.sleepTime = sleepTime;
    }

    public Integer getSleepTime()
    {
        return sleepTime;
    }
    public void setGetupTime(Integer getupTime)
    {
        this.getupTime = getupTime;
    }

    public Integer getGetupTime()
    {
        return getupTime;
    }
    public void setConnectTime(Integer connectTime)
    {
        this.connectTime = connectTime;
    }

    public Integer getConnectTime()
    {
        return connectTime;
    }
    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    public String getRemarks()
    {
        return remarks;
    }
    public void setBloodData(String bloodData)
    {
        this.bloodData = bloodData;
    }

    public String getBloodData()
    {
        return bloodData;
    }
    public void setMoistureDate(String moistureDate)
    {
        this.moistureDate = moistureDate;
    }

    public String getMoistureDate()
    {
        return moistureDate;
    }

    public String getVocation() {
        return vocation;
    }

    public void setVocation(String vocation) {
        this.vocation = vocation;
    }

    public Integer getNight() {
        return night;
    }

    public void setNight(Integer night) {
        this.night = night;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public List<SysPhysicalSigns> getSignList() {
        return signList;
    }

    public void setSignList(List<SysPhysicalSigns> signList) {
        this.signList = signList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("customerId", getCustomerId())
                .append("sex", getSex())
                .append("age", getAge())
                .append("tall", getTall())
                .append("weight", getWeight())
                .append("physicalSignsId", getPhysicalSignsId())
                .append("dishesIngredientId", getDishesIngredientId())
                .append("photo", getPhoto())
                .append("constipation", getConstipation())
                .append("staylate", getStaylate())
                .append("motion", getMotion())
                .append("makeFoodType", getMakeFoodType())
                .append("makeFoodTaste", getMakeFoodTaste())
                .append("walk", getWalk())
                .append("difficulty", getDifficulty())
                .append("weakness", getWeakness())
                .append("rebound", getRebound())
                .append("crux", getCrux())
                .append("position", getPosition())
                .append("sleepTime", getSleepTime())
                .append("getupTime", getGetupTime())
                .append("connectTime", getConnectTime())
                .append("remarks", getRemarks())
                .append("bloodData", getBloodData())
                .append("moistureDate", getMoistureDate())
                .append("vocation", getVocation())
                .append(" night", getNight())
                .append("experience", getExperience())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .toString();
    }

}