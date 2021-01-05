package com.stdiet.custom.dto.request;

import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;

/**
 * 客户体征信息请求DTO
 *
 * @author xzj
 * @date 2020-12-31
 */
public class CustomerInvestigateRequest extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    //基础信息

    /** $column.columnComment */
    private Long id;

    /** 名字 */
    @Excel(name = "名字")
    private String name;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    //体征信息

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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getTall() {
        return tall;
    }

    public void setTall(Integer tall) {
        this.tall = tall;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getPhysicalSignsId() {
        return physicalSignsId;
    }

    public void setPhysicalSignsId(String physicalSignsId) {
        this.physicalSignsId = physicalSignsId;
    }

    public String getDishesIngredientId() {
        return dishesIngredientId;
    }

    public void setDishesIngredientId(String dishesIngredientId) {
        this.dishesIngredientId = dishesIngredientId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getConstipation() {
        return constipation;
    }

    public void setConstipation(Integer constipation) {
        this.constipation = constipation;
    }

    public Integer getStaylate() {
        return staylate;
    }

    public void setStaylate(Integer staylate) {
        this.staylate = staylate;
    }

    public Integer getMotion() {
        return motion;
    }

    public void setMotion(Integer motion) {
        this.motion = motion;
    }

    public Integer getMakeFoodType() {
        return makeFoodType;
    }

    public void setMakeFoodType(Integer makeFoodType) {
        this.makeFoodType = makeFoodType;
    }

    public Integer getMakeFoodTaste() {
        return makeFoodTaste;
    }

    public void setMakeFoodTaste(Integer makeFoodTaste) {
        this.makeFoodTaste = makeFoodTaste;
    }

    public Integer getWalk() {
        return walk;
    }

    public void setWalk(Integer walk) {
        this.walk = walk;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getWeakness() {
        return weakness;
    }

    public void setWeakness(Integer weakness) {
        this.weakness = weakness;
    }

    public Integer getRebound() {
        return rebound;
    }

    public void setRebound(Integer rebound) {
        this.rebound = rebound;
    }

    public Integer getCrux() {
        return crux;
    }

    public void setCrux(Integer crux) {
        this.crux = crux;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(Integer sleepTime) {
        this.sleepTime = sleepTime;
    }

    public Integer getGetupTime() {
        return getupTime;
    }

    public void setGetupTime(Integer getupTime) {
        this.getupTime = getupTime;
    }

    public Integer getConnectTime() {
        return connectTime;
    }

    public void setConnectTime(Integer connectTime) {
        this.connectTime = connectTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getBloodData() {
        return bloodData;
    }

    public void setBloodData(String bloodData) {
        this.bloodData = bloodData;
    }

    public String getMoistureDate() {
        return moistureDate;
    }

    public void setMoistureDate(String moistureDate) {
        this.moistureDate = moistureDate;
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
}