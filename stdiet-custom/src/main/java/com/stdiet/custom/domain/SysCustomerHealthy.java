package com.stdiet.custom.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;

/**
 * 客户健康对象 sys_customer_healthy
 *
 * @author xzj
 * @date 2021-01-23
 */
public class SysCustomerHealthy extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 客户ID */
    //@Excel(name = "客户ID")
    private Long customerId;

    /** 客户姓名,非持久化字段 */
    @Excel(name = "客户姓名")
    private String name;

    /** 客户手机号,非持久化字段 */
    @Excel(name = "客户手机号")
    private String phone;

    /** 调理项目id */
    @Excel(name = "调理项目id")
    private Long conditioningProjectId;

    /** 0男 1女 2未知，默认2 */
    @Excel(name = "0男 1女 2未知，默认2")
    private Long sex;

    /** 年龄 */
    @Excel(name = "年龄")
    private Long age;

    /** 调味品种类，使用 , 隔开 */
    @Excel(name = "调味品种类，使用 , 隔开")
    private String condiment;

    /** 其他调味品种类 */
    @Excel(name = "其他调味品种类")
    private String otherCondiment;

    /** 喜好的烹调方式，使用 , 隔开 */
    @Excel(name = "喜好的烹调方式，使用 , 隔开")
    private String cookingStyle;

    /** 烹调方式对应频次，每周几次，使用 , 隔开 */
    @Excel(name = "烹调方式对应频次，每周几次，使用 , 隔开")
    private String cookingStyleRate;

    /** 洗菜方式，使用 , 隔开 */
    @Excel(name = "洗菜方式，使用 , 隔开")
    private String washVegetablesStyle;

    /** 其他洗菜方式 */
    @Excel(name = "其他洗菜方式")
    private String otherWashVegetablesStyle;

    /** 早餐习惯 */
    @Excel(name = "早餐习惯")
    private String breakfastType;

    /** 早餐吃的食物 */
    @Excel(name = "早餐吃的食物")
    private String breakfastFood;

    /** 午餐习惯，使用 , 隔开 */
    @Excel(name = "午餐习惯，使用 , 隔开")
    private String lunchType;

    /** 晚餐习惯，使用 , 隔开 */
    @Excel(name = "晚餐习惯，使用 , 隔开")
    private String dinner;

    /** 早餐当中素菜占比 */
    @Excel(name = "早餐当中素菜占比")
    private Long vegetableRate;

    /** 最常吃的肉类 */
    @Excel(name = "最常吃的肉类")
    private String commonMeat;

    /** 晚餐时间（24小时制） */
    @Excel(name = "晚餐时间", readConverterExp = "2=4小时制")
    private String dinnerTime;

    /** 每周吃夜宵次数，默认0 */
    @Excel(name = "每周吃夜宵次数，默认0")
    private Long supperNum;

    /** 夜宵通常吃的食物 */
    @Excel(name = "夜宵通常吃的食物")
    private String supperFood;

    /** 食物的冷热偏好 */
    @Excel(name = "食物的冷热偏好")
    private String dietHotAndCold;

    /** 食物的口味偏好，使用 , 隔开 */
    @Excel(name = "食物的口味偏好，使用 , 隔开")
    private String dietFlavor;

    /** 平均每周吃生菜几次 */
    @Excel(name = "平均每周吃生菜几次")
    private Long vegetablesNum;

    /** 每周吃生菜的频次类型 */
    @Excel(name = "每周吃生菜的频次类型")
    private String vegetablesRateType;

    /** 平均每天吃水果次数，默认0 */
    @Excel(name = "平均每天吃水果次数，默认0")
    private Long fruitsNum;

    /** 吃水果的时间段 */
    @Excel(name = "吃水果的时间段")
    private String fruitsTime;

    /** 平时吃水果的频次 */
    @Excel(name = "平时吃水果的频次")
    private String fruitsRate;

    /** 一餐吃几碗饭 */
    @Excel(name = "一餐吃几碗饭")
    private Long riceNum;

    /** 吃几成饱 */
    @Excel(name = "吃几成饱")
    private Long riceFull;

    /** 吃饭速度 */
    @Excel(name = "吃饭速度")
    private String eatingSpeed;

    /** 常吃的零食，使用 , 隔开 */
    @Excel(name = "常吃的零食，使用 , 隔开")
    private String snacks;

    /** 其他零食 */
    @Excel(name = "其他零食")
    private String otherSnacks;

    /** 有无服用营养保健品，0无 1有 */
    @Excel(name = "有无服用营养保健品，0无 1有")
    private Long healthProductsFlag;

    /** 营养保健品品牌名 */
    @Excel(name = "营养保健品品牌名")
    private String healthProductsBrand;

    /** 营养保健品产品名 */
    @Excel(name = "营养保健品产品名")
    private String healthProductsName;

    /** 服用营养保健品频次，每周几次 */
    @Excel(name = "服用营养保健品频次，每周几次")
    private Long healthProductsWeekRate;

    /** 服用营养保健品频次，每天几次 */
    @Excel(name = "服用营养保健品频次，每天几次")
    private Long healthProductsDayRate;

    /** 每天的饮水量，单位：毫升 */
    @Excel(name = "每天的饮水量，单位：毫升")
    private Long waterNum;

    /** 喜欢喝什么水，使用 , 隔开 */
    @Excel(name = "喜欢喝什么水，使用 , 隔开")
    private String waterType;

    /** 喝水习惯，使用 , 隔开 */
    @Excel(name = "喝水习惯，使用 , 隔开")
    private String waterHabit;

    /** 常喝的饮品的每周频次，使用，隔开 */
    @Excel(name = "常喝的饮品的每周频次，使用，隔开")
    private String drinksNum;

    /** 是否喝酒 */
    @Excel(name = "是否喝酒")
    private String drinkWineFlag;

    /** 喝酒种类，使用，隔开 */
    @Excel(name = "喝酒种类，使用，隔开")
    private String drinkWineClassify;

    /** 其他酒种类 */
    @Excel(name = "其他酒种类")
    private String otherWineClassify;

    /** 对应酒的量 */
    @Excel(name = "对应酒的量")
    private String drinkWineAmount;

    /** 是否抽烟，0否 1是，默认0 */
    @Excel(name = "是否抽烟，0否 1是，默认0")
    private Long smokeFlag;

    /** 抽烟频次和烟龄，戒烟几年，使用，隔开 */
    @Excel(name = "抽烟频次和烟龄，戒烟几年，使用，隔开")
    private String smokeRate;

    /** 是否经常抽二手烟 0否 1是，默认0 */
    @Excel(name = "是否经常抽二手烟 0否 1是，默认0")
    private Long secondSmoke;

    /** 工作行业 */
    @Excel(name = "工作行业")
    private String workIndustry;

    /** 工作性质，使用，隔开 */
    @Excel(name = "工作性质，使用，隔开")
    private String workType;

    /** 排便次数 */
    @Excel(name = "排便次数")
    private String defecationNum;

    /** 其他手动输入的排便次数 */
    @Excel(name = "其他手动输入的排便次数")
    private Long otherDefecationNum;

    /** 排便时间段，使用，隔开 */
    @Excel(name = "排便时间段，使用，隔开")
    private String defecationTime;

    /** 排便的形状 */
    @Excel(name = "排便的形状")
    private String defecationShape;

    /** 排便的气味 */
    @Excel(name = "排便的气味")
    private String defecationSmell;

    /** 排便的速度 */
    @Excel(name = "排便的速度")
    private String defecationSpeed;

    /** 排便的颜色 */
    @Excel(name = "排便的颜色")
    private String defecationColor;

    /** 每周运动次数 */
    @Excel(name = "每周运动次数")
    private Long motionNum;

    /** 每次运动的时长，分钟 */
    @Excel(name = "每次运动的时长，分钟")
    private Long motionDuration;

    /** 每天运动的时间，24小时制 */
    @Excel(name = "每天运动的时间，24小时制")
    private String motionTime;

    /** 有氧运动项目，使用，隔开 */
    @Excel(name = "有氧运动项目，使用，隔开")
    private String aerobicMotionClassify;

    /** 无氧运动项目，使用，隔开 */
    @Excel(name = "无氧运动项目，使用，隔开")
    private String anaerobicMotionClassify;

    /** 无氧有氧项目，使用，隔开 */
    @Excel(name = "无氧有氧项目，使用，隔开")
    private String anaerobicAerobicMotionClassify;

    /** 其他运动项目，使用，隔开 */
    @Excel(name = "其他运动项目，使用，隔开")
    private String otherMotionClassify;

    /** 运动场地，使用，隔开 */
    @Excel(name = "运动场地，使用，隔开")
    private String motionField;

    /** 其他运动场地 */
    @Excel(name = "其他运动场地")
    private String otherMotionField;

    /** 睡觉时间，24小时制 */
    @Excel(name = "睡觉时间，24小时制")
    private String sleepTime;

    /** 睡眠质量 */
    @Excel(name = "睡眠质量")
    private String sleepQuality;

    /** 是否有辅助入睡药物，0否 1是，默认0 */
    @Excel(name = "是否有辅助入睡药物，0否 1是，默认0")
    private Long sleepDrugFlag;

    /** 辅助睡眠类药物名称 */
    @Excel(name = "辅助睡眠类药物名称")
    private String sleepDrug;

    /** 是否经常熬夜（超过11点）0否 1是，默认0 */
    @Excel(name = "是否经常熬夜", readConverterExp = "超=过11点")
    private Long stayupLateFlag;

    /** 熬夜频次，每周几次 */
    @Excel(name = "熬夜频次，每周几次")
    private Long stayupLateWeekNum;

    /** 家族疾病史，使用，隔开 */
    @Excel(name = "家族疾病史，使用，隔开")
    private String familyIllnessHistory;

    /** 其他家族病史 */
    @Excel(name = "其他家族病史")
    private String otherFamilyIllnessHistory;

    /** 手术史，使用，隔开 */
    @Excel(name = "手术史，使用，隔开")
    private String operationHistory;

    /** 其他手术史 */
    @Excel(name = "其他手术史")
    private String otherOperationHistory;

    /** 近期是否做过手术，0否 1是，默认0 */
    @Excel(name = "近期是否做过手术，0否 1是，默认0")
    private Long nearOperationFlag;

    /** 手术恢复情况 */
    @Excel(name = "手术恢复情况")
    private String recoveryeSituation;

    /** 是否长期服用药物，0否 1是，默认0 */
    @Excel(name = "是否长期服用药物，0否 1是，默认0")
    private Long longEatDrugFlag;

    /** 长期服用的药物，使用，隔开 */
    @Excel(name = "长期服用的药物，使用，隔开")
    private String longEatDrugClassify;

    /** 其他长期服用的药物 */
    @Excel(name = "其他长期服用的药物")
    private String otherLongEatDrugClassify;

    /** 是否出现过过敏症状，0否 1是，默认0 */
    @Excel(name = "是否出现过过敏症状，0否 1是，默认0")
    private Long allergyFlag;

    /** 过敏症状 */
    @Excel(name = "过敏症状")
    private String allergySituation;

    /** 过敏源，使用，隔开 */
    @Excel(name = "过敏源，使用，隔开")
    private String allergen;

    /** 其他过敏源 */
    @Excel(name = "其他过敏源")
    private String otherAllergen;

    /** 体检报告 */
    @Excel(name = "体检报告")
    private String medicalReport;

    /** 删除标识 0未删除 1已删除。默认0 */
    private Long delFlag;

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
    public void setConditioningProjectId(Long conditioningProjectId)
    {
        this.conditioningProjectId = conditioningProjectId;
    }

    public Long getConditioningProjectId()
    {
        return conditioningProjectId;
    }
    public void setSex(Long sex)
    {
        this.sex = sex;
    }

    public Long getSex()
    {
        return sex;
    }
    public void setAge(Long age)
    {
        this.age = age;
    }

    public Long getAge()
    {
        return age;
    }
    public void setCondiment(String condiment)
    {
        this.condiment = condiment;
    }

    public String getCondiment()
    {
        return condiment;
    }
    public void setOtherCondiment(String otherCondiment)
    {
        this.otherCondiment = otherCondiment;
    }

    public String getOtherCondiment()
    {
        return otherCondiment;
    }
    public void setCookingStyle(String cookingStyle)
    {
        this.cookingStyle = cookingStyle;
    }

    public String getCookingStyle()
    {
        return cookingStyle;
    }
    public void setCookingStyleRate(String cookingStyleRate)
    {
        this.cookingStyleRate = cookingStyleRate;
    }

    public String getCookingStyleRate()
    {
        return cookingStyleRate;
    }
    public void setWashVegetablesStyle(String washVegetablesStyle)
    {
        this.washVegetablesStyle = washVegetablesStyle;
    }

    public String getWashVegetablesStyle()
    {
        return washVegetablesStyle;
    }
    public void setOtherWashVegetablesStyle(String otherWashVegetablesStyle)
    {
        this.otherWashVegetablesStyle = otherWashVegetablesStyle;
    }

    public String getOtherWashVegetablesStyle()
    {
        return otherWashVegetablesStyle;
    }
    public void setBreakfastType(String breakfastType)
    {
        this.breakfastType = breakfastType;
    }

    public String getBreakfastType()
    {
        return breakfastType;
    }
    public void setBreakfastFood(String breakfastFood)
    {
        this.breakfastFood = breakfastFood;
    }

    public String getBreakfastFood()
    {
        return breakfastFood;
    }
    public void setLunchType(String lunchType)
    {
        this.lunchType = lunchType;
    }

    public String getLunchType()
    {
        return lunchType;
    }
    public void setDinner(String dinner)
    {
        this.dinner = dinner;
    }

    public String getDinner()
    {
        return dinner;
    }
    public void setVegetableRate(Long vegetableRate)
    {
        this.vegetableRate = vegetableRate;
    }

    public Long getVegetableRate()
    {
        return vegetableRate;
    }
    public void setCommonMeat(String commonMeat)
    {
        this.commonMeat = commonMeat;
    }

    public String getCommonMeat()
    {
        return commonMeat;
    }
    public void setDinnerTime(String dinnerTime)
    {
        this.dinnerTime = dinnerTime;
    }

    public String getDinnerTime()
    {
        return dinnerTime;
    }
    public void setSupperNum(Long supperNum)
    {
        this.supperNum = supperNum;
    }

    public Long getSupperNum()
    {
        return supperNum;
    }
    public void setSupperFood(String supperFood)
    {
        this.supperFood = supperFood;
    }

    public String getSupperFood()
    {
        return supperFood;
    }
    public void setDietHotAndCold(String dietHotAndCold)
    {
        this.dietHotAndCold = dietHotAndCold;
    }

    public String getDietHotAndCold()
    {
        return dietHotAndCold;
    }
    public void setDietFlavor(String dietFlavor)
    {
        this.dietFlavor = dietFlavor;
    }

    public String getDietFlavor()
    {
        return dietFlavor;
    }
    public void setVegetablesNum(Long vegetablesNum)
    {
        this.vegetablesNum = vegetablesNum;
    }

    public Long getVegetablesNum()
    {
        return vegetablesNum;
    }
    public void setVegetablesRateType(String vegetablesRateType)
    {
        this.vegetablesRateType = vegetablesRateType;
    }

    public String getVegetablesRateType()
    {
        return vegetablesRateType;
    }
    public void setFruitsNum(Long fruitsNum)
    {
        this.fruitsNum = fruitsNum;
    }

    public Long getFruitsNum()
    {
        return fruitsNum;
    }
    public void setFruitsTime(String fruitsTime)
    {
        this.fruitsTime = fruitsTime;
    }

    public String getFruitsTime()
    {
        return fruitsTime;
    }
    public void setFruitsRate(String fruitsRate)
    {
        this.fruitsRate = fruitsRate;
    }

    public String getFruitsRate()
    {
        return fruitsRate;
    }
    public void setRiceNum(Long riceNum)
    {
        this.riceNum = riceNum;
    }

    public Long getRiceNum()
    {
        return riceNum;
    }
    public void setRiceFull(Long riceFull)
    {
        this.riceFull = riceFull;
    }

    public Long getRiceFull()
    {
        return riceFull;
    }
    public void setEatingSpeed(String eatingSpeed)
    {
        this.eatingSpeed = eatingSpeed;
    }

    public String getEatingSpeed()
    {
        return eatingSpeed;
    }
    public void setSnacks(String snacks)
    {
        this.snacks = snacks;
    }

    public String getSnacks()
    {
        return snacks;
    }
    public void setOtherSnacks(String otherSnacks)
    {
        this.otherSnacks = otherSnacks;
    }

    public String getOtherSnacks()
    {
        return otherSnacks;
    }
    public void setHealthProductsFlag(Long healthProductsFlag)
    {
        this.healthProductsFlag = healthProductsFlag;
    }

    public Long getHealthProductsFlag()
    {
        return healthProductsFlag;
    }
    public void setHealthProductsBrand(String healthProductsBrand)
    {
        this.healthProductsBrand = healthProductsBrand;
    }

    public String getHealthProductsBrand()
    {
        return healthProductsBrand;
    }
    public void setHealthProductsName(String healthProductsName)
    {
        this.healthProductsName = healthProductsName;
    }

    public String getHealthProductsName()
    {
        return healthProductsName;
    }
    public void setHealthProductsWeekRate(Long healthProductsWeekRate)
    {
        this.healthProductsWeekRate = healthProductsWeekRate;
    }

    public Long getHealthProductsWeekRate()
    {
        return healthProductsWeekRate;
    }
    public void setHealthProductsDayRate(Long healthProductsDayRate)
    {
        this.healthProductsDayRate = healthProductsDayRate;
    }

    public Long getHealthProductsDayRate()
    {
        return healthProductsDayRate;
    }
    public void setWaterNum(Long waterNum)
    {
        this.waterNum = waterNum;
    }

    public Long getWaterNum()
    {
        return waterNum;
    }
    public void setWaterType(String waterType)
    {
        this.waterType = waterType;
    }

    public String getWaterType()
    {
        return waterType;
    }
    public void setWaterHabit(String waterHabit)
    {
        this.waterHabit = waterHabit;
    }

    public String getWaterHabit()
    {
        return waterHabit;
    }
    public void setDrinksNum(String drinksNum)
    {
        this.drinksNum = drinksNum;
    }

    public String getDrinksNum()
    {
        return drinksNum;
    }
    public void setDrinkWineFlag(String drinkWineFlag)
    {
        this.drinkWineFlag = drinkWineFlag;
    }

    public String getDrinkWineFlag()
    {
        return drinkWineFlag;
    }
    public void setDrinkWineClassify(String drinkWineClassify)
    {
        this.drinkWineClassify = drinkWineClassify;
    }

    public String getDrinkWineClassify()
    {
        return drinkWineClassify;
    }
    public void setOtherWineClassify(String otherWineClassify)
    {
        this.otherWineClassify = otherWineClassify;
    }

    public String getOtherWineClassify()
    {
        return otherWineClassify;
    }
    public void setDrinkWineAmount(String drinkWineAmount)
    {
        this.drinkWineAmount = drinkWineAmount;
    }

    public String getDrinkWineAmount()
    {
        return drinkWineAmount;
    }
    public void setSmokeFlag(Long smokeFlag)
    {
        this.smokeFlag = smokeFlag;
    }

    public Long getSmokeFlag()
    {
        return smokeFlag;
    }
    public void setSmokeRate(String smokeRate)
    {
        this.smokeRate = smokeRate;
    }

    public String getSmokeRate()
    {
        return smokeRate;
    }
    public void setSecondSmoke(Long secondSmoke)
    {
        this.secondSmoke = secondSmoke;
    }

    public Long getSecondSmoke()
    {
        return secondSmoke;
    }
    public void setWorkIndustry(String workIndustry)
    {
        this.workIndustry = workIndustry;
    }

    public String getWorkIndustry()
    {
        return workIndustry;
    }
    public void setWorkType(String workType)
    {
        this.workType = workType;
    }

    public String getWorkType()
    {
        return workType;
    }
    public void setDefecationNum(String defecationNum)
    {
        this.defecationNum = defecationNum;
    }

    public String getDefecationNum()
    {
        return defecationNum;
    }
    public void setOtherDefecationNum(Long otherDefecationNum)
    {
        this.otherDefecationNum = otherDefecationNum;
    }

    public Long getOtherDefecationNum()
    {
        return otherDefecationNum;
    }
    public void setDefecationTime(String defecationTime)
    {
        this.defecationTime = defecationTime;
    }

    public String getDefecationTime()
    {
        return defecationTime;
    }
    public void setDefecationShape(String defecationShape)
    {
        this.defecationShape = defecationShape;
    }

    public String getDefecationShape()
    {
        return defecationShape;
    }
    public void setDefecationSmell(String defecationSmell)
    {
        this.defecationSmell = defecationSmell;
    }

    public String getDefecationSmell()
    {
        return defecationSmell;
    }
    public void setDefecationSpeed(String defecationSpeed)
    {
        this.defecationSpeed = defecationSpeed;
    }

    public String getDefecationSpeed()
    {
        return defecationSpeed;
    }
    public void setDefecationColor(String defecationColor)
    {
        this.defecationColor = defecationColor;
    }

    public String getDefecationColor()
    {
        return defecationColor;
    }
    public void setMotionNum(Long motionNum)
    {
        this.motionNum = motionNum;
    }

    public Long getMotionNum()
    {
        return motionNum;
    }
    public void setMotionDuration(Long motionDuration)
    {
        this.motionDuration = motionDuration;
    }

    public Long getMotionDuration()
    {
        return motionDuration;
    }
    public void setMotionTime(String motionTime)
    {
        this.motionTime = motionTime;
    }

    public String getMotionTime()
    {
        return motionTime;
    }
    public void setAerobicMotionClassify(String aerobicMotionClassify)
    {
        this.aerobicMotionClassify = aerobicMotionClassify;
    }

    public String getAerobicMotionClassify()
    {
        return aerobicMotionClassify;
    }
    public void setAnaerobicMotionClassify(String anaerobicMotionClassify)
    {
        this.anaerobicMotionClassify = anaerobicMotionClassify;
    }

    public String getAnaerobicMotionClassify()
    {
        return anaerobicMotionClassify;
    }
    public void setAnaerobicAerobicMotionClassify(String anaerobicAerobicMotionClassify)
    {
        this.anaerobicAerobicMotionClassify = anaerobicAerobicMotionClassify;
    }

    public String getAnaerobicAerobicMotionClassify()
    {
        return anaerobicAerobicMotionClassify;
    }
    public void setOtherMotionClassify(String otherMotionClassify)
    {
        this.otherMotionClassify = otherMotionClassify;
    }

    public String getOtherMotionClassify()
    {
        return otherMotionClassify;
    }
    public void setMotionField(String motionField)
    {
        this.motionField = motionField;
    }

    public String getMotionField()
    {
        return motionField;
    }
    public void setOtherMotionField(String otherMotionField)
    {
        this.otherMotionField = otherMotionField;
    }

    public String getOtherMotionField()
    {
        return otherMotionField;
    }
    public void setSleepTime(String sleepTime)
    {
        this.sleepTime = sleepTime;
    }

    public String getSleepTime()
    {
        return sleepTime;
    }
    public void setSleepQuality(String sleepQuality)
    {
        this.sleepQuality = sleepQuality;
    }

    public String getSleepQuality()
    {
        return sleepQuality;
    }
    public void setSleepDrugFlag(Long sleepDrugFlag)
    {
        this.sleepDrugFlag = sleepDrugFlag;
    }

    public Long getSleepDrugFlag()
    {
        return sleepDrugFlag;
    }
    public void setSleepDrug(String sleepDrug)
    {
        this.sleepDrug = sleepDrug;
    }

    public String getSleepDrug()
    {
        return sleepDrug;
    }
    public void setStayupLateFlag(Long stayupLateFlag)
    {
        this.stayupLateFlag = stayupLateFlag;
    }

    public Long getStayupLateFlag()
    {
        return stayupLateFlag;
    }
    public void setStayupLateWeekNum(Long stayupLateWeekNum)
    {
        this.stayupLateWeekNum = stayupLateWeekNum;
    }

    public Long getStayupLateWeekNum()
    {
        return stayupLateWeekNum;
    }
    public void setFamilyIllnessHistory(String familyIllnessHistory)
    {
        this.familyIllnessHistory = familyIllnessHistory;
    }

    public String getFamilyIllnessHistory()
    {
        return familyIllnessHistory;
    }
    public void setOtherFamilyIllnessHistory(String otherFamilyIllnessHistory)
    {
        this.otherFamilyIllnessHistory = otherFamilyIllnessHistory;
    }

    public String getOtherFamilyIllnessHistory()
    {
        return otherFamilyIllnessHistory;
    }
    public void setOperationHistory(String operationHistory)
    {
        this.operationHistory = operationHistory;
    }

    public String getOperationHistory()
    {
        return operationHistory;
    }
    public void setOtherOperationHistory(String otherOperationHistory)
    {
        this.otherOperationHistory = otherOperationHistory;
    }

    public String getOtherOperationHistory()
    {
        return otherOperationHistory;
    }
    public void setNearOperationFlag(Long nearOperationFlag)
    {
        this.nearOperationFlag = nearOperationFlag;
    }

    public Long getNearOperationFlag()
    {
        return nearOperationFlag;
    }
    public void setRecoveryeSituation(String recoveryeSituation)
    {
        this.recoveryeSituation = recoveryeSituation;
    }

    public String getRecoveryeSituation()
    {
        return recoveryeSituation;
    }
    public void setLongEatDrugFlag(Long longEatDrugFlag)
    {
        this.longEatDrugFlag = longEatDrugFlag;
    }

    public Long getLongEatDrugFlag()
    {
        return longEatDrugFlag;
    }
    public void setLongEatDrugClassify(String longEatDrugClassify)
    {
        this.longEatDrugClassify = longEatDrugClassify;
    }

    public String getLongEatDrugClassify()
    {
        return longEatDrugClassify;
    }
    public void setOtherLongEatDrugClassify(String otherLongEatDrugClassify)
    {
        this.otherLongEatDrugClassify = otherLongEatDrugClassify;
    }

    public String getOtherLongEatDrugClassify()
    {
        return otherLongEatDrugClassify;
    }
    public void setAllergyFlag(Long allergyFlag)
    {
        this.allergyFlag = allergyFlag;
    }

    public Long getAllergyFlag()
    {
        return allergyFlag;
    }
    public void setAllergySituation(String allergySituation)
    {
        this.allergySituation = allergySituation;
    }

    public String getAllergySituation()
    {
        return allergySituation;
    }
    public void setAllergen(String allergen)
    {
        this.allergen = allergen;
    }

    public String getAllergen()
    {
        return allergen;
    }
    public void setOtherAllergen(String otherAllergen)
    {
        this.otherAllergen = otherAllergen;
    }

    public String getOtherAllergen()
    {
        return otherAllergen;
    }
    public void setMedicalReport(String medicalReport)
    {
        this.medicalReport = medicalReport;
    }

    public String getMedicalReport()
    {
        return medicalReport;
    }
    public void setDelFlag(Long delFlag)
    {
        this.delFlag = delFlag;
    }

    public Long getDelFlag()
    {
        return delFlag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("customerId", getCustomerId())
                .append("conditioningProjectId", getConditioningProjectId())
                .append("sex", getSex())
                .append("age", getAge())
                .append("condiment", getCondiment())
                .append("otherCondiment", getOtherCondiment())
                .append("cookingStyle", getCookingStyle())
                .append("cookingStyleRate", getCookingStyleRate())
                .append("washVegetablesStyle", getWashVegetablesStyle())
                .append("otherWashVegetablesStyle", getOtherWashVegetablesStyle())
                .append("breakfastType", getBreakfastType())
                .append("breakfastFood", getBreakfastFood())
                .append("lunchType", getLunchType())
                .append("dinner", getDinner())
                .append("vegetableRate", getVegetableRate())
                .append("commonMeat", getCommonMeat())
                .append("dinnerTime", getDinnerTime())
                .append("supperNum", getSupperNum())
                .append("supperFood", getSupperFood())
                .append("dietHotAndCold", getDietHotAndCold())
                .append("dietFlavor", getDietFlavor())
                .append("vegetablesNum", getVegetablesNum())
                .append("vegetablesRateType", getVegetablesRateType())
                .append("fruitsNum", getFruitsNum())
                .append("fruitsTime", getFruitsTime())
                .append("fruitsRate", getFruitsRate())
                .append("riceNum", getRiceNum())
                .append("riceFull", getRiceFull())
                .append("eatingSpeed", getEatingSpeed())
                .append("snacks", getSnacks())
                .append("otherSnacks", getOtherSnacks())
                .append("healthProductsFlag", getHealthProductsFlag())
                .append("healthProductsBrand", getHealthProductsBrand())
                .append("healthProductsName", getHealthProductsName())
                .append("healthProductsWeekRate", getHealthProductsWeekRate())
                .append("healthProductsDayRate", getHealthProductsDayRate())
                .append("waterNum", getWaterNum())
                .append("waterType", getWaterType())
                .append("waterHabit", getWaterHabit())
                .append("drinksNum", getDrinksNum())
                .append("drinkWineFlag", getDrinkWineFlag())
                .append("drinkWineClassify", getDrinkWineClassify())
                .append("otherWineClassify", getOtherWineClassify())
                .append("drinkWineAmount", getDrinkWineAmount())
                .append("smokeFlag", getSmokeFlag())
                .append("smokeRate", getSmokeRate())
                .append("secondSmoke", getSecondSmoke())
                .append("workIndustry", getWorkIndustry())
                .append("workType", getWorkType())
                .append("defecationNum", getDefecationNum())
                .append("otherDefecationNum", getOtherDefecationNum())
                .append("defecationTime", getDefecationTime())
                .append("defecationShape", getDefecationShape())
                .append("defecationSmell", getDefecationSmell())
                .append("defecationSpeed", getDefecationSpeed())
                .append("defecationColor", getDefecationColor())
                .append("motionNum", getMotionNum())
                .append("motionDuration", getMotionDuration())
                .append("motionTime", getMotionTime())
                .append("aerobicMotionClassify", getAerobicMotionClassify())
                .append("anaerobicMotionClassify", getAnaerobicMotionClassify())
                .append("anaerobicAerobicMotionClassify", getAnaerobicAerobicMotionClassify())
                .append("otherMotionClassify", getOtherMotionClassify())
                .append("motionField", getMotionField())
                .append("otherMotionField", getOtherMotionField())
                .append("sleepTime", getSleepTime())
                .append("sleepQuality", getSleepQuality())
                .append("sleepDrugFlag", getSleepDrugFlag())
                .append("sleepDrug", getSleepDrug())
                .append("stayupLateFlag", getStayupLateFlag())
                .append("stayupLateWeekNum", getStayupLateWeekNum())
                .append("familyIllnessHistory", getFamilyIllnessHistory())
                .append("otherFamilyIllnessHistory", getOtherFamilyIllnessHistory())
                .append("operationHistory", getOperationHistory())
                .append("otherOperationHistory", getOtherOperationHistory())
                .append("nearOperationFlag", getNearOperationFlag())
                .append("recoveryeSituation", getRecoveryeSituation())
                .append("longEatDrugFlag", getLongEatDrugFlag())
                .append("longEatDrugClassify", getLongEatDrugClassify())
                .append("otherLongEatDrugClassify", getOtherLongEatDrugClassify())
                .append("allergyFlag", getAllergyFlag())
                .append("allergySituation", getAllergySituation())
                .append("allergen", getAllergen())
                .append("otherAllergen", getOtherAllergen())
                .append("medicalReport", getMedicalReport())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .append("delFlag", getDelFlag())
                .toString();
    }
}